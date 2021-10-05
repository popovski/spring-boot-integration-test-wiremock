package com.labs.iw.conf;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
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
}
