package com.link.apache.camel.demo.routes.parallel;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ParallelRestCallsRoute extends RouteBuilder {

    @Value("${rest.api2}")
    private String restApi2;

    @Override
    public void configure() throws Exception {

        final int threads = 2;
        final var executorService = getContext().getExecutorServiceManager()
                .newFixedThreadPool(this, "parallelCalls", threads);

        from("direct:parallel")
            .routeId("parallel")
            .multicast(new CombineResultsAggregationStrategy())
                .parallelProcessing()
                .executorService(executorService)
                .to("direct:parallelCalls-callApi1", "direct:parallelCalls-callApi2")
            .end()
            .log("Combined Result: ${body}");

        from("direct:parallelCalls-callApi1")
            .routeId("parallelCalls-callApi1")
            .to("https://linkmockapi.free.beeceptor.com/customers")
            .process(exchange -> {
                    String api1Response = exchange.getIn().getBody(String.class);
                    JsonMapper mapper = new JsonMapper();
                    Api1Response response = mapper.readValue(api1Response, (Api1Response.class));

                    exchange.getIn().setBody(response);
                })
            .log("API 1 Response: ${body}");

        from("direct:parallelCalls-callApi2")
            .routeId("parallelCalls-callApi2")
            .to(restApi2)
            .process(exchange -> {
                    String api2Response = exchange.getIn().getBody(String.class);
                    JsonMapper mapper = new JsonMapper();
                    Api2Response response = mapper.readValue(api2Response, (Api2Response.class));

                    exchange.getIn().setBody(response);
                })
            .log("API 2 Response: ${body}");
    }

    static class CombineResultsAggregationStrategy implements AggregationStrategy {
        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            if (oldExchange == null) {
                return newExchange;
            }
            Result result = new Result();

            Api1Response api1Response = oldExchange.getIn().getBody(Api1Response.class);
            result.setName(api1Response.getName());

            Api2Response api2Response = newExchange.getIn().getBody(Api2Response.class);
            result.setStatus(api2Response.getStatus());

            oldExchange.getIn().setBody(result);

            return oldExchange;
        }
    }
}
