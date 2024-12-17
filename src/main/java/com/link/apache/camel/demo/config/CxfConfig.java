package com.link.apache.camel.demo.config;

import org.apache.camel.component.cxf.common.DataFormat;
import org.apache.camel.component.cxf.jaxws.CxfEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CxfConfig {

    @Bean
    public CxfEndpoint countryServiceEndpoint() {
        CxfEndpoint endpoint = new CxfEndpoint();
        endpoint.setAddress("/soap/countryService");
        endpoint.setServiceClass(com.baeldung.springsoap.gen.GetCountryRequest.class);
        endpoint.setDataFormat(DataFormat.POJO);
        return endpoint;
    }
}
