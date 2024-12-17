package com.link.apache.camel.demo.routes.soap;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.link.apache.camel.demo.routes.soap.dto.BalanceResponse;
import com.link.apache.camel.demo.routes.soap.dto.SoapEnvelope;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Soap2SoapRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("direct:soap2soap")
            .routeId("soap2soap")
            .process(exchange -> {
                    String initialSoapRequest = buildSoapRequest("anyId");
                    exchange.getIn().setBody(initialSoapRequest);
                })
            .setHeader("Content-Type", constant("text/xml"))
            .to("http://eaa86977-f80b-4c94-bef4-335ca7a8d899.mock.pstmn.io/balance")
            .log("SOAP response 1 processed: ${body}")
            .process(exchange -> {
                    String xmlResponse = exchange.getIn().getBody(String.class);
                    XmlMapper xmlMapper = new XmlMapper();
                    SoapEnvelope envelope = xmlMapper.readValue(xmlResponse, SoapEnvelope.class);

                    BalanceResponse balance = envelope.getBody().getBalanceResponse();
                    String soapRequest = buildSoapRequest(balance.getName());
                    exchange.getIn().setBody(soapRequest);
                })
            .setHeader("Content-Type", constant("text/xml"))
            .to("http://eaa86977-f80b-4c94-bef4-335ca7a8d899.mock.pstmn.io/balance")
            .process(exchange -> {
                    String xmlResponse = exchange.getIn().getBody(String.class);
                    XmlMapper xmlMapper = new XmlMapper();
                    SoapEnvelope envelope = xmlMapper.readValue(xmlResponse, SoapEnvelope.class);

                    BalanceResponse balance = envelope.getBody().getBalanceResponse();
                    exchange.getIn().setBody("Balance: " + balance);
                })
            .log("SOAP response 2 processed: ${body}");
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
