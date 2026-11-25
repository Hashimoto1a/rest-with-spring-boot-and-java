package br.com.hashimoto1a.integrationtests.swagger;

import br.com.hashimoto1a.config.TestConfig;
import br.com.hashimoto1a.integrationtests.testcontainers.AbstractIntegrationTests;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = "server.port=8888")
class SwaggerIntegrationTests extends AbstractIntegrationTests {

	@Test
	void shouldDisplaySwaggerUIPage() {
		var content = given().basePath("/swagger-ui/index.html").port(TestConfig.SERVER_PORT).when().get().then()
				.statusCode(200).extract().body().asString();

		assertTrue(content.contains("Swagger UI"));
	}

}
