package com.example.first_camel_case;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.ServiceStatus;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.params.Test;
import org.apache.camel.test.spring.junit5.CamelSpringTest;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

@CamelSpringTest
@ContextConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CamelSpringPlainTest {

    @Autowired
    protected CamelContext camelContext;

    @EndpointInject("mock:a")
    protected MockEndpoint mockA;

    @EndpointInject("mock:b")
    protected MockEndpoint mockB;

    @Produce("direct:start")
    protected ProducerTemplate start;

    @Test
    public void testPositive() throws Exception {
        Assertions.assertEquals(ServiceStatus.Started, camelContext.getStatus());

        mockA.expectedBodiesReceived("David");
        mockB.expectedBodiesReceived("Hello David");

        start.sendBody("David");

        MockEndpoint.assertIsSatisfied(camelContext);
    }
}