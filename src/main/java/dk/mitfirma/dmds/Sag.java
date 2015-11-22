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
public class Sag implements DmdsObject, Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String parentId;
    private String journalnummer;
    private String beskrivelse;
    private String sagstype;

    public Sag() {}
    
    @Override public Long getId() { return id; }
    @Override public void setId(Long x) { id = x; }
    @Override public void setId(String x) { setId(Long.parseLong(x)); }
        
    @Override public String getParentId() { return parentId; }
    @Override public void setParentId(String x) { parentId = x; }
    
    public String getJournalnummer() { return journalnummer; }
    public void setJournalnummer(String x) { journalnummer = x; }

    public String getBeskrivelse() { return beskrivelse; }
    public void setBeskrivelse(String x) { beskrivelse = x; }

    public String getSagstype() { return sagstype; }
    public void setSagstype(String x) { sagstype = x; }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sag)) {
            return false;
        }
        Sag other = (Sag) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sag[id=" + id + "]";
    }
    
    @Override
    public String toXML() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<sag id=\"" + id + "\" parentId=\"" + parentId + "\">" + 
                "<journalnummer>" + journalnummer + "</journalnummer>" +
                "<beskrivelse>" + beskrivelse + "</beskrivelse>" +
                "<sagstype>" + sagstype + "</sagstype>" +
                "</sag>";
    }
    
    @Override
    public Sag fromXML(String xml) {
        DmdsParser parser = new DmdsParser(xml, this);
        parser.readDmds();
        return this;
    }
}