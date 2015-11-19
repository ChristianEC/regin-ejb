/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.mitfirma.dmds;

import java.util.List;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author christian
 * @param <T>
 */

public abstract class DmdsObjectEJB<T extends DmdsObject> {
    @PersistenceContext(unitName = "ReginPU")
    protected EntityManager em;
    protected Class<T> entityClass;
    @EJB(name = "MessengerEJB")
    protected MessengerEJB messenger;

    @Logged
    public T findById(Long id) {
//        System.out.println("Testing");
//        messenger.sendMessage("Test test test");
        return (T) em.find(entityClass, id);
    }
    
    public T findById(String id) {
        return findById(Long.parseLong(id));
    }
    
    public List findAll() {
        return em.createQuery("select x from " + entityClass.getSimpleName() + " x").getResultList();
    }
    
    @Logged
    public T create(T x) {
        em.persist(x);
        return (T) x;
    }
    
    @Logged
    public T update(T x) {
        return (T) em.merge(x);
    }
    
    @Logged
    public T delete(String id) {
        T x = findById(id);
        if (x != null) em.remove(x);
        return x;
    }
}
