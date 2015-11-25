/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.mitfirma.dmds;

import java.io.StringReader;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import static javax.xml.stream.XMLStreamConstants.*;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author christian
 */
public class DmdsParser {
    private static final QName ID = QName.valueOf("id");
    private static final QName PARENT_ID = QName.valueOf("parentId");
    private XMLEvent event;
    private int eventType;
    private StartElement startElement;
    private String elementName;
    private String textData;
    private String id;
    private String parentId;
    private XMLInputFactory factory;
    private StringReader stringReader;
    private XMLEventReader reader;
    private DmdsObject dmdsObject;
    
    DmdsParser(String xml, DmdsObject dmdsObject) {
        try {
            factory = XMLInputFactory.newInstance();
            stringReader = new StringReader(xml);
            reader = factory.createXMLEventReader(stringReader);
            this.dmdsObject = dmdsObject;
        } catch (XMLStreamException e) {
            e.printStackTrace(System.out);
        }
    }
    
    private void nextToken() {
        Attribute a;
        if (reader.hasNext()) {
            try {
                event = reader.nextEvent();
                eventType = event.getEventType();
                switch (eventType) {
                    case (START_ELEMENT): {
                        startElement = event.asStartElement();
                        a = startElement.getAttributeByName(ID);
                        id = (a == null) ? null : a.getValue();
                        a = startElement.getAttributeByName(PARENT_ID);
                        parentId = (a == null) ? null : a.getValue();
                        elementName = startElement.getName().getLocalPart().toLowerCase();
                        break;
                    }
                    case (CHARACTERS): {
                        textData = event.asCharacters().getData().trim();
                        if (textData.isEmpty()) nextToken(); // ignore white space
                        break;
                    }
                }
            } catch (XMLStreamException e) {
                e.printStackTrace(System.out);
            }
        }
    }
    
    private void nextToken(String expected) {
        System.out.println("Expected: " + expected);
        nextToken();
        System.out.println("Got: (" + eventType + "," + elementName + "," + textData + ")");
    }
    
    public void readDmds() {        
        // Assume valid DMDS XML
        nextToken(); // <?xml>
        nextToken(); // <dmds> or a single object
        if (elementName.equals("dmds")) {
            nextToken(); // <dmdsObjekt>
            readDmdsObjects();
        } else
            readDmdsObject();
    }
    
    public void readDmdsObjects() {
        while (elementName.equals("dmdsobjekt")) {
            nextToken(); // <genstand>, <sag> or <fil>
            readDmdsObject();
            nextToken(); // <dmdsObjekt> or </dmds>
        }
    }   

    public void readDmdsObject() {
        dmdsObject.setParentId(parentId);
        switch (elementName) {
            case "genstand":
                readGenstand();
                break;
            case "sag":
                readSag();
                break;
            case "fil":
                readFil();
                break;
        }
        nextToken(); // EOF or </dmdsObjekt>
    }
    
    public void readGenstand() {
        Genstand genstand = (Genstand) dmdsObject;
        nextToken(); // first element under <genstand> or </genstand>
        while (eventType != END_ELEMENT) { // </genstand> if END_ELEMENT
            nextToken(); nextToken(); // textData + end element
            switch (elementName) {
                case "genstandsnummer":
                    genstand.setGenstandsnummer(textData);
                    break;
                case "navn":
                    genstand.setNavn(textData);
                    break;
                case "beskrivelse":
                    genstand.setBeskrivelse(textData);
                    break;
            }
            nextToken(); // next element or </genstand>
        }
    }

    public void readSag() {
        Sag sag = (Sag) dmdsObject;
        nextToken(); // first element under <fil> or </fil>
        while (eventType != END_ELEMENT) { // </fil> if END_ELEMENT
            nextToken(); nextToken(); // textData + end element
            switch (elementName) {
                case "journalnummer":
                    sag.setJournalnummer(textData);
                    break;
                case "beskrivelse":
                    sag.setBeskrivelse(textData);
                    break;
                case "sagstype":
                    sag.setSagstype(textData);
                    break;
            }
            nextToken(); // next element or </sag>
        }
    }

    public void readFil() {
        Fil fil = (Fil) dmdsObject;
        nextToken(); // first element under <fil> or </fil>
        while (eventType != END_ELEMENT) { // </fil> if END_ELEMENT
            nextToken(); nextToken(); // textData + end element
            switch (elementName) {
                case "filnavn":
                    fil.setFilnavn(textData);
                    break;
                case "mimetype":
                    fil.setMimetype(textData);
                    break;
                case "uri":
                    fil.setUri(textData);
                    break;
            }
            nextToken(); // next element or </fil>
        }
    }
}