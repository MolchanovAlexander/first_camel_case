package com.example.first_camel_case;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import com.example.first_camel_case.config.ActiveMQConfig;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;


/*
 *@author Ya
 * mvn clean test -Dtest=MulticastRouteTest
 */
@CamelSpringBootTest
@SpringBootTest
@Import({ActiveMQConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration({
        "/camel/two_brokers_context.xml",
        "/factory.xml"
})
public class TwoBrokersTest {

    @Autowired
    private CamelContext camelContext;

    @EndpointInject("mock:brokerComponentA:queue:ANALIZE.NUM")
    private MockEndpoint numEndpoint;

    @EndpointInject("mock:brokerComponentA:queue:ANALIZE.CHAR")
    private MockEndpoint charEndpoint;

    @Autowired
    private ProducerTemplate producerTemplate;

    @BeforeEach
    public void setup() throws InterruptedException {
        //Thread.sleep(1000);// only for real brokers not inner
        numEndpoint.reset();
        charEndpoint.reset();
    }

    @Test
    public void testTwoBrokerSimpleNUM() throws Exception {
        Map<String ,Object> headers = new HashMap<>();
        headers.put("JMSCorrelationID", "None");
        headers.put("MONITOR_OUT", "Y");

        AdviceWith.adviceWith(camelContext, "tst", routeBuilder -> {
            routeBuilder.weaveByToUri("brokerComponentA:queue:ANALIZE.NUM").replace().to("mock:brokerComponentA:queue:ANALIZE.NUM");
            routeBuilder.weaveByToUri("brokerComponentA:queue:ANALIZE.CHAR").replace().to("mock:brokerComponentA:queue:ANALIZE.CHAR");
        });
        numEndpoint.expectedMessageCount(1);
        numEndpoint.expectedBodiesReceived(List.of("4567"));
        charEndpoint.expectedMessageCount(0);
        //charEndpoint.await(4, TimeUnit.SECONDS); // only for real brokers not inner
        //getEndpoint("brokerComponentA:queue:TEST.TEST.IN") - name doesn't matter
        Exchange exchange = producerTemplate.getCamelContext().getEndpoint("brokerComponentA:queue:TEST.TEST.IN").createExchange();
        exchange.getIn().setBody("4567");
        exchange.getIn().setHeaders(headers);
        producerTemplate.send("brokerComponentA:queue:TEST.TEST.IN", exchange);

        MockEndpoint.assertIsSatisfied(camelContext);
    }

    @Test
    public void testTwoBrokerSimpleCHAR() throws Exception {

        AdviceWith.adviceWith(camelContext, "tst", routeBuilder -> {
            routeBuilder.weaveByToUri("brokerComponentA:queue:ANALIZE.NUM").replace().to("mock:brokerComponentA:queue:ANALIZE.NUM");
            routeBuilder.weaveByToUri("brokerComponentA:queue:ANALIZE.CHAR").replace().to("mock:brokerComponentA:queue:ANALIZE.CHAR");
        });
        numEndpoint.expectedMessageCount(0);
        charEndpoint.expectedBodiesReceived(List.of("kjh"));

        producerTemplate.sendBodyAndHeader("brokerComponentA:queue:TEST.TEST.IN", "kjh", "WAIT", "Y");

        MockEndpoint.assertIsSatisfied(camelContext);
    }
}
