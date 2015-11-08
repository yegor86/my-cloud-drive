package integrational;

import com.jayway.restassured.RestAssured;
import org.apache.commons.httpclient.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.odesamama.mcd.Application;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;

/**
 * Created by starnakin on 30.10.2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:3030")
public class FileControllerTest {

    private static final int SERVER_PORT = 3030;

    private static final String UPLOAD_FILE = "/files/upload";

    private static final String GET_USER_FILES = "/users/files/%s";

    private String userEmail = "admin@mail.com";

    @Before
    public void setup(){
        RestAssured.port = SERVER_PORT;
    }

    @Test
    public void loadFileForUserAndCheckItIsInUserList() throws IOException {
        String fileName = "test.txt";
        File file = new File(fileName);

        if(!file.exists()){
            file.createNewFile();
        }


        try(FileWriter writer = new FileWriter(file.getName(), true)){
            writer.write("Test content");
        }


        given()
                .parameter("name", file.getName())
                .multiPart(file)
                .parameter("email",userEmail)
                .post(UPLOAD_FILE).then().statusCode(HttpStatus.SC_OK);

        Assert.assertTrue(when().get(String.format(GET_USER_FILES, userEmail)).asString().contains(file.getName()));


    }
}
