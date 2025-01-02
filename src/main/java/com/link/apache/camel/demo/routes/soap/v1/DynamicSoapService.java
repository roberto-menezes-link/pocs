package com.link.apache.camel.demo.routes.soap.v1;

import com.link.apache.camel.demo.routes.soap.v1.dto.SoapEnvelope;

public interface DynamicSoapService {
    SoapEnvelope processRequest();

    String sayHello();

    String sayGoodbye();
}