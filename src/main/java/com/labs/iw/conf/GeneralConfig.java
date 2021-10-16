package com.labs.iw.conf;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import java.time.Duration;

@Configuration
public class GeneralConfig {

	@Bean
	public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
		return builder.createXmlMapper(false).serializationInclusion(Include.NON_NULL).build();
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
   @Bean
   public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfiguration() {
       TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
               .timeoutDuration(Duration.ofSeconds(1))
               .build();
       
       CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
               .failureRateThreshold(50)
              // .waitDurationInOpenState(Duration.ofMillis(200))
               .slidingWindowSize(20)
               .build();

       return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
               .timeLimiterConfig(timeLimiterConfig)
               .circuitBreakerConfig(circuitBreakerConfig)
               .build());
   }
}
