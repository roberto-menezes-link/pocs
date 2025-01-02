package com.link.apache.camel.demo.routes.soap.v1.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class SoapEnvelope {

    @JacksonXmlProperty(localName = "Body")
    private SoapBody body;

    public SoapBody getBody() {
        return body;
    }

    public void setBody(SoapBody body) {
        this.body = body;
    }
}
