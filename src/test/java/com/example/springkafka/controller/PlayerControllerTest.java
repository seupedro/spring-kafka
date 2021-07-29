package com.example.springkafka.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class PlayerControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void givenMultiplePlayers_thenVerifyResponse() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"players\": [ " +
                        "{ \"name\": \"Sub zero\", \"type\": \"expert\" }, " +
                        "{ \"name\": \"Scorpion\", \"type\": \"novice\" }, " +
                        "{ \"name\": \"Reptile\", \"type\": \"meh\" } ] }")
                .post("/players")
                .then()
                .statusCode(201)
                .assertThat().body("result[0]", equalTo("player Sub zero stored in DB"))
                             .body("result[1]", equalTo("player Scorpion sent to Kafka topic"))
                             .body("result[2]", equalTo("player Reptile did not fit"));
    }

    @Test
    public void givenBodyWithUnknownProperties_thenVerifyResponse() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"players\": [ " +
                        "{ \"foo\": \"f\", \"bar\": \"b\" }, " +
                        "{ \"name\": \"Scorpion\", \"type\": \"novice\" }, " +
                        "{ \"name\": \"Reptile\", \"type\": \"meh\" } ] }")
                .post("/players")
                .then()
                .statusCode(400)
                .assertThat().body("error", equalTo("Bad Request"));
    }

}