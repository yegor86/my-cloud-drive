package org.odesamama.mcd.controllers;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.apache.commons.httpclient.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.odesamama.mcd.Application;
import org.odesamama.mcd.domain.User;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;

/**
 * Created by starnakin on 28.10.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:3030")
public class UserControllerTest {

    private static final int SERVER_PORT = 3030;

    private static final String CREATE_USER = "/users/create";

    private static final String GET_USER = "/users/%s";

    @Before
    public void setup(){
        RestAssured.port = SERVER_PORT;
    }

    @Test
    public void createUserAndCheckThatItsOnUserListResponseTest() throws IOException, URISyntaxException {
        User user = new User();
        String userEmail = System.nanoTime() + "@test222.com";
        user.setUserEmail(userEmail);
        user.setLastName("lastName");
        user.setUserName("firstName");

        given().body(user).contentType(ContentType.JSON).
        when().post(CREATE_USER).then().statusCode(HttpStatus.SC_OK);

        Assert.assertTrue(when().get(String.format(GET_USER, userEmail)).asString().contains(userEmail));
    }
}
