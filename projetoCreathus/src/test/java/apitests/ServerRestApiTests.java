package apitests;

import org.junit.Before;
import org.junit.Test;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Random;

import static io.restassured.RestAssured.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServerRestApiTests {
    String token = "";
    String bookingId = "";
    String firstName = "Thiago";
    String lastName = "Freitas";
    String checkinDate = "2024-01-16";
    String checkoutDate = "2024-01-24";
    Random rand = new Random();


    @Before
    public void setup(){
        baseURI = "https://restful-booker.herokuapp.com/";
    }

    @Test
    public void testPingAplication(){
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/ping")
                .then()
                .log().all()
                .assertThat().statusCode(201);
    }


    @Test
    public void testGetBookingIdsAll(){
        given()
                .contentType(ContentType.JSON)
                .when()
                    .get("/booking")
                .then()
                    .log().all().assertThat().statusCode(200);
    }

    @Test
    public void crudeBooking(){
        token = given()
                .body("{\n" +
                        "    \"username\": \"admin\",\n" +
                        "    \"password\": \"password123\"\n" +
                        "}")
                .contentType(ContentType.JSON)
                .when()
                .post("/auth")
                .then()
                .log().all().assertThat().statusCode(200)
                .extract().path("token").toString();

         bookingId = given()
                .body("{\n" +
                        "    \"firstname\" : \""+firstName+"\",\n" +
                        "    \"lastname\" : \""+lastName+"\",\n" +
                        "    \"totalprice\" : "+rand.nextInt(500)+",\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \""+checkinDate+"\",\n" +
                        "        \"checkout\" : \""+checkoutDate+"\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Cafe da manha\"\n" +
                        "}")
                .contentType(ContentType.JSON)
                .when()
                .post("/booking"+ bookingId)
                .then()
                .log().all().assertThat().statusCode(200)
                 .extract().path("bookingid").toString();

         System.out.println("O numero do token e:"+bookingId);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/booking/"+ bookingId)
                .then()
                .log().all().assertThat().statusCode(200);

        given()
                .header("Cookie", "token="+token)
                .body("{\n" +
                        "    \"firstname\" : \"ThiagoMod\",\n" +
                        "    \"lastname\" : \"FreitasMod\",\n" +
                        "    \"totalprice\" : "+rand.nextInt(500)+",\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \""+checkinDate+"\",\n" +
                        "        \"checkout\" : \""+checkoutDate+"\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Cafe da manha\"\n" +
                        "}")
                .contentType(ContentType.JSON)
                .when()
                .put("/booking/"+ bookingId)
                .then()
                .log().all().assertThat().statusCode(200);

        given()
                .header("Cookie", "token="+token)
                .body("{\n" +
                        "    \"firstname\" : \"Thiago2\",\n" +
                        "    \"lastname\" : \"Freitas2\"\n" +
                        "}")
                .contentType(ContentType.JSON)
                .when()
                .patch("/booking/"+ bookingId)
                .then()
                .log().all().assertThat().statusCode(200);

        given()
                .header("Cookie", "token="+token)
                .contentType(ContentType.JSON)
                .when()
                .delete("/booking/"+ bookingId)
                .then()
                .log().all().assertThat().statusCode(201);

    }


    @Test
    public void testGetBookingByName(){
        given()
                .body("{\n" +
                        "    \"firstname\" : \""+firstName+"\",\n" +
                        "    \"lastname\" : \""+lastName+"\",\n" +
                        "    \"totalprice\" : "+rand.nextInt(500)+",\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \""+checkinDate+"\",\n" +
                        "        \"checkout\" : \""+checkoutDate+"\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Cafe da manha\"\n" +
                        "}")
                .contentType(ContentType.JSON)
                .when()
                .post("/booking"+ bookingId)
                .then()
                .log().all().assertThat().statusCode(200)
                .extract().path("bookingid");

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/booking?firstname="+ firstName+"&lastname="+lastName)
                .then()
                .log().all().assertThat().statusCode(200);
    }

    @Test
    public void testGetBookingByDate(){

        given()
                .body("{\n" +
                        "    \"firstname\" : \""+firstName+"\",\n" +
                        "    \"lastname\" : \""+lastName+"\",\n" +
                        "    \"totalprice\" : "+rand.nextInt(500)+",\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \""+checkinDate+"\",\n" +
                        "        \"checkout\" : \""+checkoutDate+"\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Cafe da manha\"\n" +
                        "}")
                .contentType(ContentType.JSON)
                .when()
                .post("/booking"+ bookingId)
                .then()
                .log().all().assertThat().statusCode(200)
                .extract().path("bookingid");

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/booking?checkin="+ checkinDate+"&checkout="+checkoutDate)
                .then()
                .log().all().assertThat().statusCode(200);
    }

}
