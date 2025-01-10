package com.example.first_camel_case;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringTestSupport;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.AbstractApplicationContext;

public class RouteTest extends CamelSpringTestSupport {

    @EndpointInject("mock://queue:WAIT")
    protected MockEndpoint waitEndpoint;

    @EndpointInject("mock://queue:DIGIT")
    protected MockEndpoint digitEndpoint;

    @EndpointInject("mock://queue:CHAR")
    protected MockEndpoint charEndpoint;

    @Produce("direct:queue:DNO_1")
    protected ProducerTemplate template;

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("com.example.first_camel_case/test-context.xml");
    }

    @Test
    public void testRouteWaitHeader() throws Exception {
        String body = "2";
        waitEndpoint.expectedMessageCount(1);
        waitEndpoint.expectedHeaderReceived("WAIT", "Y");
        template.sendBody(body);
        waitEndpoint.assertIsSatisfied();
    }

    @Test
    public void testRouteDigit() throws Exception {
        String body = "123";
        digitEndpoint.expectedMessageCount(1);
        digitEndpoint.expectedBodiesReceived("123");
        digitEndpoint.expectedHeaderReceived("WAIT", "N");
        template.sendBody(body);
        digitEndpoint.assertIsSatisfied();
    }

    @Test
    public void testRouteChar() throws Exception {
        String body = "ABC";
        charEndpoint.expectedMessageCount(1);
        charEndpoint.expectedBodiesReceived(body);
        template.sendBody(body);
        charEndpoint.assertIsSatisfied();
    }
}
