package br.com.hashimoto1a.integrationtests.controllers.withjson;

import br.com.hashimoto1a.config.TestConfig;
import br.com.hashimoto1a.integrationtests.DTO.PersonDTO;
import br.com.hashimoto1a.integrationtests.testcontainers.AbstractIntegrationTests;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = "server.port=8888")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerTest extends AbstractIntegrationTests {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static PersonDTO person;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        person = new PersonDTO();
    }

    @Test
    @Order(1)
    void create() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder().addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_SEMERU)
                .setBasePath("/api/person/v1")
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification).contentType(MediaType.APPLICATION_JSON_VALUE).body(person).when().post().then()
                .statusCode(200).extract().body().asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Richard", createdPerson.getFirstName());
        assertEquals("Stallman", createdPerson.getLastName());
        assertEquals("New York City - New York - USA", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());

    }

    @Test
    @Order(2)
    void createWithWrongOrigins() throws JsonProcessingException {

        specification = new RequestSpecBuilder().addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_GOOGLE)
                .setBasePath("/api/person/v1")
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification).contentType(MediaType.APPLICATION_JSON_VALUE).body(person).when().post().then()
                .statusCode(403).extract().body().asString();

        assertEquals("Invalid CORS request", content);

    }
    @Test
    @Order(3)
    void findById() throws JsonProcessingException {

        specification = new RequestSpecBuilder().addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_LOCAL)
            .setBasePath("/api/person/v1")
            .setPort(TestConfig.SERVER_PORT)
            .addFilter(new RequestLoggingFilter(LogDetail.ALL))
            .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();

        var content = given(specification).contentType(MediaType.APPLICATION_JSON_VALUE).body(person).pathParam("id", person.getId())
                .when().get("{id}").then()
                .statusCode(200).extract().body().asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Richard", createdPerson.getFirstName());
        assertEquals("Stallman", createdPerson.getLastName());
        assertEquals("New York City - New York - USA", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
    }

    @Test
    @Order(4)
    void findByIdWithWrongOrigin() throws JsonProcessingException {

        specification = new RequestSpecBuilder().addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_GOOGLE)
            .setBasePath("/api/person/v1")
            .setPort(TestConfig.SERVER_PORT)
            .addFilter(new RequestLoggingFilter(LogDetail.ALL))
            .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();

        var content = given(specification).contentType(MediaType.APPLICATION_JSON_VALUE).body(person).pathParam("id", person.getId())
                .when().get("{id}").then()
                .statusCode(403).extract().body().asString();

        assertEquals("Invalid CORS request", content);

    }

    private void mockPerson() {
        person.setFirstName("Richard");
        person.setLastName("Stallman");
        person.setAddress("New York City - New York - USA");
        person.setGender("Male");
    }
}