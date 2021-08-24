package cardmanager;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CardDelete {
    String uri = "https://ardent-bulwark-315212.ue.r.appspot.com/api/v1/cards";

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
