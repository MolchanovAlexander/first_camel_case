package com.example.first_camel_case;

import com.example.first_camel_case.config.ActiveMQConfig;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

@CamelSpringBootTest
@Import(ActiveMQConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = "classpath:camel/camel-context.xml")
public class MulticastRouteTest {

    @Autowired
    private CamelContext camelContext;

    @EndpointInject("mock:activemq:queue:DNO.CR")
    private MockEndpoint crEndpoint;

    @EndpointInject("mock:activemq:queue:DNO.ER")
    private MockEndpoint erEndpoint;

    @EndpointInject("mock:activemq:queue:DNO.THREE")
    private MockEndpoint threeEndpoint;

    @Autowired
    private ProducerTemplate producerTemplate;

    @BeforeEach
    public void setup() {
        crEndpoint.reset();
        erEndpoint.reset();
        threeEndpoint.reset();
    }

    @Test
    public void testMulticastWhenHeaderWaitIsY() throws Exception {

        AdviceWith.adviceWith(camelContext, "produce-route", routeBuilder -> {
            routeBuilder.weaveByToUri("activemq:queue:DNO.CR").replace().to("mock:activemq:queue:DNO.CR");
            routeBuilder.weaveByToUri("activemq:queue:DNO.ER").replace().to("mock:activemq:queue:DNO.ER");
        });
        crEndpoint.expectedMessageCount(1);
        erEndpoint.expectedMessageCount(1);
        threeEndpoint.expectedMessageCount(0); // Shouldn't receive any messages in this scenario
        // Send a message with WAIT header = 'Y'
        producerTemplate.sendBodyAndHeader("activemq:queue:DNO_1", "4566", "WAIT", "Y");

        MockEndpoint.assertIsSatisfied(camelContext);
    }

    @Test
    public void testRecipientListWhenHeaderWaitIsNotY() throws Exception {

        AdviceWith.adviceWith(camelContext, "produce-route", routeBuilder -> {
            routeBuilder.weaveByToUri("activemq:queue:DNO.CR").replace().to("mock:activemq:queue:DNO.CR");
            routeBuilder.weaveByToUri("activemq:queue:DNO.ER").replace().to("mock:activemq:queue:DNO.ER");
            routeBuilder.interceptSendToEndpoint("activemq:queue:DNO.THREE")
                    .to("mock:activemq:queue:DNO.THREE");

        });

        crEndpoint.expectedMessageCount(0); // Shouldn't receive messages
        erEndpoint.expectedMessageCount(0); // Shouldn't receive messages
        threeEndpoint.expectedMessageCount(1);
        // Send a message with WAIT header != 'Y'
        producerTemplate.sendBodyAndHeader("activemq:queue:DNO_1", "123", "WAIT", "N");

        MockEndpoint.assertIsSatisfied(camelContext);
    }
}
