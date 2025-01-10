package com.example.first_camel_case.processor;

import java.util.Map;
import org.apache.camel.Handler;
import org.apache.camel.Headers;

public class SomeHandler {

    @Handler
    public String execute(@Headers Map headers, String body) {
        System.out.println("headers.size " + headers.size());
        AnalizeBean ab = new AnalizeBean();
        if (!ab.isDigit(body)) {
            return body;
        }
        headers.put("DNIWE", "Y");
        headers.put("WAIT", Integer.parseInt(body)% 2 == 0 ? "Y" : "N");
        headers.put("BODY", body);
        return headers.get("WAIT").equals("Y") ? "handler do:" + headers.size() : body;
    }
}
