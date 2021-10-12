package com.labs.iw;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.labs.iw.dto.StudentResponse;
import com.labs.iw.service.MockHttpService;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentTest {
	@Value("${mock_base_url}")
	String mockBaseUrl;
		
	@Autowired
	private MockHttpService mockHttpService;
// **** Wiremock setup Start
	@Autowired
	private static WireMockServer wireMockServer;

	@BeforeAll
	static void startWireMock() {
		wireMockServer = new WireMockServer(
				WireMockConfiguration.wireMockConfig()
				.port(9999)
				.extensions(new ResponseTemplateTransformer(true))
		);
		wireMockServer.start();
	}
	
	@AfterAll
	static void stopWireMock() {
		wireMockServer.stop();
	}
	
	@BeforeEach
	void clearWireMock() {
		System.out.println("Stored stubbings: " + wireMockServer.getStubMappings().size());
		wireMockServer.resetAll();
		System.out.println("Stored stubbings after reset: " + wireMockServer.getStubMappings().size());
	}

// **** Wiremock setup end	
	
	@Test
	void getStudentTest() {
		String urlPath = "/api/get-json-normal";
		wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo(urlPath))
				.willReturn(
						aResponse()
						.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						.withBodyFile("mock-api/student_response.json"))
				);
		
		StudentResponse response = mockHttpService.getStudent(urlPath);
		
		assertThat(response).isNotNull();
		assertThat(response.getData()).isNotNull();
		assertThat(response.getData().getUuid()).isNotNull();
		assertThat(response.getData().getCreationDate()).isNotNull();
		assertThat(response.getData().getFirstName()).isNotNull();
		assertThat(response.getData().getLastName()).isNotNull();
		
		assertThat(response.getData().getFirstName()).isEqualTo("Petko from file");
		assertThat(response.getData().getLastName()).isEqualTo("Petkov from file");
		
	}
	
	@Test
	void getStudentWithRandomDelayCall() {
		String url = "http://localhost:9999/api/get-json-random-delay";
		
		StudentResponse response =	mockHttpService.getStudent(url);
		
		assertThat(response).isNotNull();
		
	}
	
	@Test
	void getStudentWithFixedDelay() {
		String url = "http://localhost:9999/api/get-json-fix-delay";
		
		StudentResponse response =	mockHttpService.getStudent(url);
		
		assertThat(response).isNull();
		
	}
}
