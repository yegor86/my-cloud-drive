package org.odesamama.mcd.controllers;

import org.odesamama.mcd.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by starnakin on 07.10.2015.
 */

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    FileService fileService;

    @RequestMapping(value="/upload", method= RequestMethod.POST)
    public @ResponseBody HttpStatus uploadFile(@RequestParam("name") String name, @RequestParam("file") MultipartFile file, @RequestParam("email") String email) throws IOException, URISyntaxException {

        fileService.uploadFileToHDFSServer(file.getBytes(), name, email);

        return HttpStatus.OK;
    }

    @RequestMapping(value="createfolder", method= RequestMethod.POST)
    public @ResponseBody HttpStatus createFolder(@RequestParam("folderName") String folderName,@RequestParam("path") String path, @RequestParam("email") String email) throws IOException, URISyntaxException {

        fileService.createFolder(folderName, path, email);

        return  HttpStatus.OK;
    }

}
