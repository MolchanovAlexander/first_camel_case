package com.example.first_camel_case;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringTestSupport;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.context.support.AbstractApplicationContext;

public class SimpleMockTest extends CamelSpringTestSupport {

    @EndpointInject("mock:queue:DIGIT")
    protected MockEndpoint digitEndpoint;

    @EndpointInject("mock:queue:CHAR")
    protected MockEndpoint charEndpoint;

    @Produce("direct:queue:DNO_1")
    protected ProducerTemplate template;

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("com.example.first_camel_case/test-context.xml");
    }

    @ParameterizedTest
    @CsvSource({
            "1, mock:queue:DIGIT",    // Test case for digits
            "a, mock:queue:CHAR"      // Test case for characters
    })
    public void testMock(String input, String expectedEndpoint) throws Exception {
        // Configure the expected endpoint
        MockEndpoint expectedMockEndpoint = expectedEndpoint.equals("mock:queue:DIGIT") ? digitEndpoint : charEndpoint;
        expectedMockEndpoint.expectedBodiesReceived(input);

        // Send the input to the route
        template.sendBody(input);

        // Assert the expected endpoint receives the input
        expectedMockEndpoint.assertIsSatisfied();
    }
}
