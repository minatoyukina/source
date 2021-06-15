package com.example.cloud.gateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * @author ccq
 * @since 2021/6/2 9:56
 */
@Configuration
@Slf4j
@SuppressWarnings("all")
public class ResponseFilter implements GlobalFilter, Ordered {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpResponse response = exchange.getResponse();
        ServerHttpResponseDecorator decorator = new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (!(body instanceof Flux)) return super.writeWith(body);
                HttpStatus statusCode = getStatusCode();
                Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                return super.writeWith(fluxBody.map(dataBuffer -> {
                    byte[] content = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(content);
                    DataBufferUtils.release(dataBuffer);
                    if (statusCode.is5xxServerError()) {
                        ObjectNode objectNode = objectMapper.createObjectNode();
                        objectNode.put("code", 1001);
                        objectNode.put("msg", "服务器故障");
                        response.setStatusCode(HttpStatus.OK);
                        response.getHeaders().setContentLength(objectNode.toString().getBytes().length);
                        return response.bufferFactory().wrap(objectNode.toString().getBytes());
                    }
                    String s = new String(content, Charset.forName("UTF-8"));
                    byte[] uppedContent = new String(content, Charset.forName("UTF-8")).getBytes();
                    return response.bufferFactory().wrap(uppedContent);
                }));
            }
        };
        return chain.filter(exchange.mutate().response(decorator).build());
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

}
