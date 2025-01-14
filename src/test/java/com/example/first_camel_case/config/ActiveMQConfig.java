package com.example.first_camel_case.config;

import java.net.URI;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class ActiveMQConfig {

    @Bean
    public ActiveMQConnectionFactory activemqConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("vm://localhost");
        return connectionFactory;
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public BrokerService embeddedBroker() throws Exception {
        BrokerService brokerService = new BrokerService();
        brokerService.setPersistent(false);
        brokerService.setUseJmx(false);
        brokerService.setDataDirectory("target/activemq-data");
        brokerService.setSchedulerSupport(true);
        
        TransportConnector connector = new TransportConnector();
        connector.setUri(new URI("vm://localhost"));
        brokerService.addConnector(connector);
        
        return brokerService;
    }
}
