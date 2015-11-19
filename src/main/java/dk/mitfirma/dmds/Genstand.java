/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.mitfirma.dmds;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author christian
 */
@Entity
public class Genstand implements DmdsObject, Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String genstandsnummer;
    private String navn;
    private String beskrivelse;

    public Genstand() {}
    
    public Long getId() { return id; }
    public void setId(Long x) { id = x; }
    public void setId(String x) { setId(Long.parseLong(x)); }

    public String getGenstandsnummer() { return genstandsnummer; }
    public void setGenstandsnummer(String x) { genstandsnummer = x; }

    public String getNavn() { return navn; }
    public void setNavn(String x) { navn = x; }

    public String getBeskrivelse() { return beskrivelse; }
    public void setBeskrivelse(String x) { beskrivelse = x; }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Genstand)) {
            return false;
        }
        Genstand other = (Genstand) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DMDS.Genstand[ id=" + id + " ]";
    }
    
    @Override
    public String toXML() {
        return "<dmdsObjekt><genstand>" + 
                "<genstandsnummer>" + genstandsnummer + "</genstandsnummer>" +
                "<navn>" + navn + "</navn>" +
                "<beskrivelse>" + beskrivelse + "</beskrivelse>" +
                "</genstand></dmdsObjekt>";
    }
    
    @Override
    public DmdsObject fromXML(String xml) {
        DmdsParser parser = new DmdsParser(xml, this);
        parser.readDmds();
        return this;
    }
}