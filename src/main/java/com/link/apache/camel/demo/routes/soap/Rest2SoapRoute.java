package com.link.apache.camel.demo.routes.soap;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.link.apache.camel.demo.routes.soap.v1.dto.BalanceResponse;
import com.link.apache.camel.demo.routes.soap.v1.dto.SoapEnvelope;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Rest2SoapRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("direct:rest2Soap")
            .routeId("rest2Soap")
            .process(exchange -> {
                    String soapRequest = buildSoapRequest("test");
                    exchange.getIn().setBody(soapRequest);
                })
            .setHeader("Content-Type", constant("text/xml"))
            .log("Calling SOAP API: ${body}")
            .to("http://eaa86977-f80b-4c94-bef4-335ca7a8d899.mock.pstmn.io/balance")
            .process(exchange -> {
                    String xmlResponse = exchange.getIn().getBody(String.class);
                    XmlMapper xmlMapper = new XmlMapper();
                    SoapEnvelope envelope = xmlMapper.readValue(xmlResponse, SoapEnvelope.class);

                    BalanceResponse balance = envelope.getBody().getBalanceResponse();
                    exchange.getIn().setBody("Balance: " + balance);
                })
            .log("SOAP response processed: ${body}");
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
