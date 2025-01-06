package com.example.first_camel_case;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringTestSupport;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.AbstractApplicationContext;

public class SimpleMockTest extends CamelSpringTestSupport {

    @EndpointInject("mock:queue:DIGIT")
    protected MockEndpoint resultEndpoint;

    @Produce("direct:queue:DNO_1")
    protected ProducerTemplate template;

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("com.example.first_camel_case/test-context.xml");
    }

    @Test
    public void testMock() throws Exception {
        String expectedBody = "1";
        resultEndpoint.expectedBodiesReceived(expectedBody);
        template.sendBodyAndHeader(expectedBody, "foo", "bar");
        resultEndpoint.assertIsSatisfied();
    }
}