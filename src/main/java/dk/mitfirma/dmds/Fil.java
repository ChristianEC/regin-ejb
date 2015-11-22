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
public class Fil implements DmdsObject, Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String parentId;
    private String filnavn;
    private String mimetype;
    private String uri;

    public Fil() {}
    
    @Override public Long getId() { return id; }
    @Override public void setId(Long x) { id = x; }
    @Override public void setId(String x) { setId(Long.parseLong(x)); }
           
    @Override public String getParentId() { return parentId; }
    @Override public void setParentId(String x) { parentId = x; }
    
    public String getFilnavn() { return filnavn; }
    public void setFilnavn(String x) { filnavn = x; }

    public String getMimetype() { return mimetype; }
    public void setMimetype(String x) { mimetype = x; }

    public String getUri() { return uri; }
    public void setUri(String x) { uri = x; }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fil)) {
            return false;
        }
        Fil other = (Fil) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fil[id=" + id + "]";
    }
    
    @Override
    public String toXML() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<fil id=\"" + id + "\" parentId=\"" + parentId + "\">" + 
                "<filnavn>" + filnavn + "</filnavn>" +
                "<mimetype>" + mimetype + "</mimetype>" +
                "<uri>" + uri + "</uri>" +
                "</fil>";
    }
    
    @Override
    public Fil fromXML(String xml) {
        DmdsParser parser = new DmdsParser(xml, this);
        parser.readDmds();
        return this;
    }
}