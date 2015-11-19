/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.mitfirma.dmds;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author christian
 */

@Logged
@Interceptor
class LogInterceptor {
    @EJB(name = "MessengerEJB")
    private MessengerEJB messenger;

    @AroundInvoke
    public Object logInvocation(InvocationContext ctx) {
        DmdsObject obj;
        try {
            obj = (DmdsObject) ctx.proceed();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
        if (obj != null) messenger.sendMessage(obj.getId().toString() + ":" + ctx.getMethod().getName() + ":" + obj.toXML());
        return obj;
    }
}
