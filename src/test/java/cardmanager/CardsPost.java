package cardmanager;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class CardsPost {
    String uri = "https://ardent-bulwark-315212.ue.r.appspot.com/api/v1/cards";
    String wrongUri = "https://ardent-bulwark-315212.ue.r.appspot.com/api/v1/card";

    public String readJson(String acessJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(acessJson)));
    }

    @Test (priority = 1)
    public void cardRegistration() throws IOException {
        String jsonBody = readJson("db/newCard.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(201)
        ;
    }

    @Test (priority = 2)
    public void cardRegistrationWrongApi() throws IOException {
        String jsonBody = readJson("db/newCard.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post(wrongUri)
        .then()
                .log().all()
                .statusCode(404)
        ;
    }

    @Test (priority = 3)
    public void cardRegistrationAllEmptyField() throws IOException {
        String jsonBody = readJson("db/allEmptyFields.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(400)
        ;
    }

    @Test (priority = 4)
    public void registerExistingCard() throws IOException {
        String jsonBody = readJson("db/newCard.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(412)
        ;
    }

    @Test (priority = 5)
    public void internalError() throws IOException {
        String jsonBody = readJson("db/emptyEmailAndNameFields.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(500)
        ;
    }
}
