package com.example.cloud.gateway.config;

import com.example.cloud.gateway.filter.CustomizedLimiter;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;

/**
 * @author ccq
 * @since 2021/5/18 17:00
 */
@Configuration
@Slf4j
public class GatewayConfig {

    @Bean
    public ReactiveResilience4JCircuitBreakerFactory reactiveResilience4JCircuitBreakerFactory(CircuitBreakerRegistry circuitBreakerRegistry, TimeLimiterRegistry timeLimiterRegistry) {
        ReactiveResilience4JCircuitBreakerFactory reactiveResilience4JCircuitBreakerFactory = new ReactiveResilience4JCircuitBreakerFactory();
        reactiveResilience4JCircuitBreakerFactory.configureCircuitBreakerRegistry(circuitBreakerRegistry);
        reactiveResilience4JCircuitBreakerFactory.configure(
                builder -> builder
                        .timeLimiterConfig(timeLimiterRegistry.getConfiguration("backendB")
                                .orElse(TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(300)).build()))
                        .circuitBreakerConfig(circuitBreakerRegistry.getConfiguration("backendB")
                                .orElse(circuitBreakerRegistry.getDefaultConfig())),
                "backendB");
        return reactiveResilience4JCircuitBreakerFactory;
    }

    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getHostName());
    }

    @Bean
    @Primary
    public RequestRateLimiterGatewayFilterFactory customizedLimiter(RedisRateLimiter rateLimiter, @Qualifier("ipKeyResolver") KeyResolver keyResolver) {
        return new CustomizedLimiter(rateLimiter, keyResolver);
    }
}
