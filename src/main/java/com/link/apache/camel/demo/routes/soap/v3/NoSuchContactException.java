package com.link.apache.camel.demo.routes.soap.v3;

import jakarta.xml.ws.WebFault;

@WebFault(name = "NoSuchContact")
public class NoSuchContactException extends Exception {

    private static final long serialVersionUID = 1L;

    private String faultInfo;

    public NoSuchContactException(String name) {
        super("Contact \"" + name + "\" does not exist.");
        this.faultInfo = "Contact \"" + name + "\" does not exist.";
    }

    public NoSuchContactException(String message, String faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    public NoSuchContactException(String message, String faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    public String getFaultInfo() {
        return this.faultInfo;
    }
}