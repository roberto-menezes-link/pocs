package com.link.apache.camel.demo.routes.soap.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class SoapBody {

    @JacksonXmlProperty(localName = "BalanceResponse")
    private BalanceResponse balanceResponse;

    public BalanceResponse getBalanceResponse() {
        return balanceResponse;
    }

    public void setBalanceResponse(BalanceResponse balanceResponse) {
        this.balanceResponse = balanceResponse;
    }
}