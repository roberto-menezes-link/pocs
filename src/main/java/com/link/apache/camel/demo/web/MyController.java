package com.link.apache.camel.demo.web;

import org.apache.camel.ProducerTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    private final ProducerTemplate producerTemplate;

    public MyController(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }

    @GetMapping("/simple")
    public String startSimpleProcess() {
        producerTemplate.sendBody("direct:callRoute1", null);
        return "Process started";
    }

    @GetMapping("/routes/{name}")
    public String restToSoap(@PathVariable String name) {
        producerTemplate.sendBody("direct:" + name, null);
        return "Process started " + name;
    }
}