/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.mitfirma.dmds;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

/**
 *
 * @author christian
 */
@Stateless(name = "SagEJB")
public class SagEJB extends DmdsObjectEJB<Sag> {
    @PostConstruct
    public void initialize() {
        entityClass = Sag.class;
    }
}