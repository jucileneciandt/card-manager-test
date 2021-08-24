package cardmanager;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;


public class Cards {
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

    @Test (priority = 6)
    public void consultCard(){
        String email = "cardmanager@teste.com";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "?" + "email" + "=" + email)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Card Manager"))
                .body("email", is("cardmanager@teste.com"))
                .body("cards.cardNumber", contains(4321432143214321L))
        ;
    }

    @Test (priority = 7)
    public void consultCardEmptyEmailField(){
        String email = "";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "?" + "email" + "=" + email)
        .then()
                .log().all()
                .statusCode(400)
        ;
    }

    @Test (priority = 8)
    public void consultCardWrongEmail(){
        String email = "cardmanager@testecom";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "?" + "email" + "=" + email)
        .then()
                .log().all()
                .statusCode(404)
        ;
    }

    @Test (priority = 9)
    public void deleteUser(){
        String email = "cardmanager@teste.com";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri + "?" + "email" + "=" + email)
        .then()
                .log().all()
                .statusCode(204)
        ;
    }

    @Test (priority = 10)
    public void deleteWrongUser(){
        String email = "cardmanager@testecom";

        given()
                .contentType("application/json")
                .log().all()
                .when()
                .delete(uri + "?" + "email" + "=" + email)
                .then()
                .log().all()
                .statusCode(204)
        ;
    }
}
