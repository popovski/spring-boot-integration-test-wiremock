package com.labs.iw.service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.netty.handler.timeout.WriteTimeoutException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MockService {
	private final WebClient todoWebClient;

	public MockService(WebClient todoWebClient) {
		this.todoWebClient = todoWebClient;
	}

	public void normalCall() {
		Flux<HashMap> test = this.todoWebClient.get().uri("/read-from-file")
		.retrieve()
		.onStatus(HttpStatus::is4xxClientError, this::handleErrors)
		.bodyToFlux(HashMap.class);
	
		test.subscribe(x -> {
			System.out.println(x.get("response"));
			System.out.println("nikola subscribe");
		});
	}
	
	public void callWithDelay() {
		Flux<HashMap> test = this.todoWebClient.get().uri("/one")
		.retrieve()
		.onStatus(HttpStatus::is4xxClientError, this::handleErrors)
		.bodyToFlux(HashMap.class);
	
		test.subscribe(x -> {
			System.out.println("nikola subscribe");
		});
	}

	public void handleClientError() {
		Flux<HashMap> test = this.todoWebClient.get().uri("/fault")
		.retrieve()
		.onStatus(HttpStatus::is4xxClientError, this::handleErrors)
		.bodyToFlux(HashMap.class);
	
		test.subscribe(x -> {
			System.out.println("nikola subscribe");
		});
	}
	
	private Mono<Throwable> handleErrors(ClientResponse response ){
	    return response.bodyToMono(String.class).flatMap(body -> {
	   	 System.out.println("LOG ERROR");
	   	 
	        return Mono.error(new Exception());
	    });
	}
	
	public void getAllTodos2() {
		Map<String, String> test = this.todoWebClient.get().uri("/one")
		.retrieve()
		.bodyToMono(HashMap.class)
		.block();
	
		System.out.println("Map size " + test.size());
		if (test != null && test.size() > 0) {
			test.forEach((k, v) -> System.out.println("Key : " + k + ", Value : " + v));

		}
	}
}