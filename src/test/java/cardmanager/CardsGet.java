package cardmanager;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

public class CardsGet {
    String uri = "https://ardent-bulwark-315212.ue.r.appspot.com/api/v1/cards";

    @Test (priority = 6)
    public void consultUser(){
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
    public void consultUserEmptyEmailField(){
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
    public void consultUserWrongEmail(){
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
}
