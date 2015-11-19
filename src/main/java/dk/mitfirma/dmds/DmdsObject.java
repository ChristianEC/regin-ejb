/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.mitfirma.dmds;

/**
 *
 * @author christian
 */
public interface DmdsObject {
    public Long getId();
    public String toXML();
    public DmdsObject fromXML(String xml);
}
