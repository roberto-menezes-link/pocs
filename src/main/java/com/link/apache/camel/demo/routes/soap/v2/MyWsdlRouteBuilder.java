package com.link.apache.camel.demo.routes.soap.v2;

import com.example.customerservice.Customer;
import com.example.customerservice.CustomerService;
import com.example.customerservice.NoSuchCustomer;
import com.example.customerservice.NoSuchCustomerException;
import com.link.apache.camel.demo.routes.soap.v2.repository.CustomerRepository;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.jaxws.CxfEndpoint;
import org.apache.cxf.message.MessageContentsList;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * This class demonstrate how to expose a SOAP endpoint starting from a CustomerService.wsdl, using the cxf-codegen-plugin
 */
@Component
public class MyWsdlRouteBuilder extends RouteBuilder {

    private final CustomerRepository customerRepository;

    public MyWsdlRouteBuilder(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Bean
    CxfEndpoint customers() {
        CxfEndpoint customersEndpoint = new CxfEndpoint();
        customersEndpoint.setWsdlURL("wsdl/CustomerService.wsdl");
        customersEndpoint.setServiceClass(CustomerService.class);
        customersEndpoint.setAddress("/customers");
        customersEndpoint.setProperties(new HashMap<>());
        // Request validation will be executed, in particular the name validation in getCustomersByName
        customersEndpoint.getProperties().put("schema-validation-enabled", "true");

        return customersEndpoint;
    }

    @Override
    public void configure() {
        // CustomerService is generated with cxf-codegen-plugin during the build
        from("cxf:bean:customers")
            .routeId("customers")
            .recipientList(simple("direct:${header.operationName}"));

        from("direct:getCustomersByName")
            .routeId("getCustomersByName")
            .recipientList(simple("direct:${header.routeName}"))
            .process(exchange -> {
                    String name = exchange.getIn().getBody(String.class);

                    MessageContentsList resultList = new MessageContentsList();
                    List<Customer> customersByName = customerRepository.getCustomersByName(name);

                    if (customersByName.isEmpty()) {
                        NoSuchCustomer noSuchCustomer = new NoSuchCustomer();
                        noSuchCustomer.setCustomerName(name);

                        throw new NoSuchCustomerException("Customer not found", noSuchCustomer);
                    }

                    resultList.add(customersByName);
                    exchange.getMessage().setBody(resultList);
                });

        from("direct:updateCustomer")
            .routeId("updateCustomer")
            .process(exchange ->
                        customerRepository.updateCustomer(exchange.getIn().getBody(Customer.class)));
    }
}