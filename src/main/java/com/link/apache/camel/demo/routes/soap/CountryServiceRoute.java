package com.link.apache.camel.demo.routes.soap;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CountryServiceRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("cxf:bean:countryServiceEndpoint")
            .routeId("countryServiceRoute")
            .process(exchange -> {
                    com.baeldung.springsoap.gen.GetCountryRequest request =
                            exchange.getIn().getBody(com.baeldung.springsoap.gen.GetCountryRequest.class);

                    com.baeldung.springsoap.gen.GetCountryResponse response = new com.baeldung.springsoap.gen.GetCountryResponse();

                    com.baeldung.springsoap.gen.Country country = new com.baeldung.springsoap.gen.Country();
                    country.setName(request.getName());
                    country.setCapital("Example City");
                    country.setPopulation(1000000);
                    country.setCurrency(com.baeldung.springsoap.gen.Currency.EUR);

                    response.setCountry(country);

                    exchange.getMessage().setBody(response);
                });
    }
}
