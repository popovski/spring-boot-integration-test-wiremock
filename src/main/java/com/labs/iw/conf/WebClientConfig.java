package com.labs.iw.conf;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

  @Bean
  public WebClient todoWebClient(
    @Value("${mock_base_url}") String mockBaseUrl,
    WebClient.Builder webClientBuilder) {

	//  HttpClient client = HttpClient.create()
//			  .responseTimeout(Duration.ofSeconds(5)); 
	  
    return webClientBuilder
   //		 .clientConnector(new ReactorClientHttpConnector(client))
      .baseUrl(mockBaseUrl)
      .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
      .build();
  }
  
	@Bean
	public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
		return builder.createXmlMapper(false).serializationInclusion(Include.NON_NULL).build();
	}
}
