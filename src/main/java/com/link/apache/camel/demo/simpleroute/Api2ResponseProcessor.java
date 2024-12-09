package com.link.apache.camel.demo.simpleroute;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.link.apache.camel.demo.model.Customer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Api2ResponseProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String transformedData = exchange.getIn().getBody(String.class);
        JsonMapper mapper = new JsonMapper();
        Customer customer = mapper.readValue(transformedData, (Customer.class));
        customer.setBalance(new BigDecimal(150));
        String jsonString = mapper.writeValueAsString(customer);
        exchange.getIn().setBody(jsonString);
    }
}