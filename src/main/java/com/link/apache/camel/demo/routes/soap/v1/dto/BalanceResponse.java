package com.link.apache.camel.demo.routes.soap.v1.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class BalanceResponse {

    @JacksonXmlProperty(localName = "Balance")
    private double balance;

    @JacksonXmlProperty(localName = "Id")
    private long id;

    @JacksonXmlProperty(localName = "Name")
    private String name;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BalanceResponse{" +
                "balance=" + balance +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}