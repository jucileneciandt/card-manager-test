package cardmanager;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;


public class Cards {
    String uri = "https://ardent-bulwark-315212.ue.r.appspot.com/api/v1/cards";

    public String readJson(String acessJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(acessJson)));
    }

    @Test
    public void registerCard() throws IOException {
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
}
