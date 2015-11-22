/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.mitfirma.dmds;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 *
 * @author christian
 */
@Stateless(name = "MessengerEJB")
public class MessengerEJB {
    private CamelContext myCamelContext;
    private ProducerTemplate myTemplate;

    @PostConstruct
    public void postConstruct() {
        myCamelContext = new DefaultCamelContext();
        myTemplate = myCamelContext.createProducerTemplate();
        RouteBuilder builder = new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:reginEvent").to("activemq:queue:reginEventQ");
            }
        };
        try {
            myCamelContext.addRoutes(builder);
            myCamelContext.start();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    @PreDestroy
    public void preDestroy() {
        try {
            myCamelContext.stop();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public void sendMessage(String message) {
        myTemplate.sendBody("direct:reginEvent", message);
    }
}
