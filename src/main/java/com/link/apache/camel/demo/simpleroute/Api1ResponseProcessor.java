package com.link.apache.camel.demo.simpleroute;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.link.apache.camel.demo.model.Customer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Api1ResponseProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String api1Response = exchange.getIn().getBody(String.class);
        JsonMapper mapper = new JsonMapper();
        Customer customer = mapper.readValue(api1Response, (Customer.class));
        customer.setBalance(new BigDecimal(50));
        String jsonString = mapper.writeValueAsString(customer);
        exchange.getIn().setBody(jsonString);
        exchange.getIn().setHeader("API1Result", jsonString);
    }
}