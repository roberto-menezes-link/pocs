package com.link.apache.camel.demo.routes.soap;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.link.apache.camel.demo.model.Customer;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Soap2RestRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("direct:soap2rest")
            .routeId("soap2rest")
            .process(exchange -> {
                    String initialSoapRequest = buildSoapRequest("anyId");
                    exchange.getIn().setBody(initialSoapRequest);
                })
            .setHeader("Content-Type", constant("text/xml"))
            .to("http://eaa86977-f80b-4c94-bef4-335ca7a8d899.mock.pstmn.io/balance")
            .log("SOAP response processed: ${body}")
            .process(exchange -> exchange.getIn().setBody(null))
            .to("https://linkmockapi.free.beeceptor.com/customers")
            .log("body from REST: ${body}")
            .process(exchange -> {
                    String api1Response = exchange.getIn().getBody(String.class);
                    JsonMapper mapper = new JsonMapper();
                    Customer customer = mapper.readValue(api1Response, (Customer.class));

                    String soapRequest = buildSoapRequest(customer.getId());
                    exchange.getIn().setBody(soapRequest);
                });
    }

    private String buildSoapRequest(String id) {
        return """
                    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="http://tempuri.org/">
                          <soapenv:Header/>
                          <soapenv:Body>
                             <tem:Balance>
                                <tem:id>%s</tem:id>
                             </tem:Balance>
                          </soapenv:Body>
                       </soapenv:Envelope>
                """.formatted(id);
    }
}
