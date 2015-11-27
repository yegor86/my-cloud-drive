package org.odesamama.mcd.controllers;

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
@IntegrationTest("server.port:3040")
public class FileControllerTest {

    private static final int SERVER_PORT = 3040;

    private static final String UPLOAD_FILE = "/files/upload";

    private static final String GET_USER_FILES = "/users/files/%s";

    private static final String LOAD_FILE = "/files/download/%s/%s";

    private static final String USER_EMAIL = "admin@mail.com";

    private static final String FILE_NAME = "test.jpg";

    private static final String CREATE_FOLDER = "/files/createfolder";

    public static final String GET_FILE_LIST_BY_PATH = "/files/list/%s/%s";

    @Before
    public void setup(){
        RestAssured.port = SERVER_PORT;
    }

    @Test
    public void createFolderAndLoadFilesToIt() throws IOException {

        File file = new File(FILE_NAME);

        if(!file.exists()){
            file.createNewFile();
        }


        try(FileWriter writer = new FileWriter(file.getName(), true)){
            writer.write("Test content");
        }

        String folderName = System.nanoTime() +"";

        given().param("email",USER_EMAIL).param("path", folderName).post(CREATE_FOLDER).then().statusCode(HttpStatus.SC_OK);

        for(int i = 0; i < 5; i ++){
            given()
                    .parameter("filePath", folderName+ "/" + System.nanoTime() + file.getName())
                    .multiPart(file)
                    .parameter("email",USER_EMAIL)
                    .post(UPLOAD_FILE).then().statusCode(HttpStatus.SC_OK);

            Assert.assertTrue(when().get(String.format(GET_USER_FILES, USER_EMAIL)).asString().contains(file.getName()));

        }

    }

    @Test
    public void loadFileTest() {
        try {
            when().get(String.format(LOAD_FILE, USER_EMAIL, FILE_NAME)).asInputStream().close();
        }catch (Exception e){
            Assert.assertTrue("Method thrown unexpected exception" + e.getStackTrace(),false);
        }
    }

    @Test
    public void loadFilesByPath(){
        System.out.println(when().get(String.format(GET_FILE_LIST_BY_PATH, USER_EMAIL,"")).asString());
    }
}


