package com.example.cloud.gateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

/**
 * @author ccq
 * @since 2021/6/10 16:19
 */
public class CustomizedLimiter extends RequestRateLimiterGatewayFilterFactory {

    private KeyResolver resolver;
    private RateLimiter limiter;

    private static final String EMPTY_KEY = "____EMPTY_KEY__";

    public CustomizedLimiter(RateLimiter defaultRateLimiter, KeyResolver keyResolver) {
        super(defaultRateLimiter, keyResolver);
        resolver = keyResolver;
        limiter = defaultRateLimiter;
    }

    @Override
    @SuppressWarnings("unchecked")
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> resolver.resolve(exchange).defaultIfEmpty(EMPTY_KEY)
                .flatMap(key -> {
                    ServerHttpResponse httpResponse = exchange.getResponse();
                    if (EMPTY_KEY.equals(key)) {
                        return chain.filter(exchange);
                    }
                    String routeId = config.getRouteId();
                    if (routeId == null) {
                        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
                        routeId = Objects.requireNonNull(route).getId();
                    }
                    return limiter.isAllowed(routeId, key).flatMap(response -> {
                        RateLimiter.Response res = (RateLimiter.Response) response;
                        for (Map.Entry<String, String> header : res.getHeaders()
                                .entrySet()) {
                            httpResponse.getHeaders().add(header.getKey(),
                                    header.getValue());
                        }

                        if (res.isAllowed()) {
                            return chain.filter(exchange);
                        }

                        ObjectNode objectNode = new ObjectMapper().createObjectNode();
                        objectNode.put("code", 1002);
                        httpResponse.setStatusCode(HttpStatus.OK);
                        httpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                        httpResponse.getHeaders().setContentLength(objectNode.toString().getBytes().length);
                        return httpResponse.writeWith(Mono.just(httpResponse.bufferFactory().wrap(objectNode.toString().getBytes())));
                    });
                });
    }
}
