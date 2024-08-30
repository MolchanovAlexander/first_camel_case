package com.example.first_camel_case.route;

import com.example.first_camel_case.processor.FirstProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//@Component
public class MyFirstRouteBuilder extends RouteBuilder {

    @Value("${app.queue.consume}")
    private String CONSUMER;
    @Value("${app.queue.produce}")
    private String PRODUCER;

    @Autowired
    FirstProcessor firstProcessor;

    @Override
    public void configure() throws Exception {
        from(CONSUMER)
                .log("---Received msg: ${header.CamelFileName}")
                .to("direct:processFile");

        from("direct:processFile")
                .log("<<Processing Message - Content: ${body}")
                .process(firstProcessor)
                .log("Content after processing: ${body}")
                .to(PRODUCER)
                .stop();
    }
}
