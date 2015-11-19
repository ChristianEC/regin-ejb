/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.mitfirma.dmds;

import java.io.StringReader;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import static javax.xml.stream.XMLStreamConstants.*;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author christian
 */
public class DmdsParser {
    private XMLEvent event;
    private int eventType;
    private StartElement startElement;
    private String elementName;
    private String textData;
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
    
    public void test() {
        int i = 0;
        while (reader.hasNext()) {
            nextToken();
            i++;
            if (i > 20) break;
        }
    }
    
    private void nextToken() {
        try {
            event = reader.nextEvent();
            eventType = event.getEventType();
            switch (eventType) {
                case (START_ELEMENT): {
                    startElement = event.asStartElement();
                    elementName = startElement.getName().getLocalPart().toLowerCase();
                    break;
                }
                case (CHARACTERS): {
                    textData = event.asCharacters().getData().trim();
                    if (textData.isEmpty() && reader.hasNext()) nextToken(); // ignore white space
                    break;
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace(System.out);
        }
    }
    
    public void readDmds() {        
        // Assume valid DMDS XML
        nextToken(); // <?xml>
        nextToken(); // <dmds>
        nextToken(); // <dmdsobjekt>
        readDmdsObjects();       
    }
    
    public void readDmdsObjects() {
        while (elementName.equals("dmdsobjekt")) {
            nextToken();
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
        }
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
        nextToken();
    }

    public void readFil() {
        nextToken();
    }
}