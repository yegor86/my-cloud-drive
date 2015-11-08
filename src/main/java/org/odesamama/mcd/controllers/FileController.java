package org.odesamama.mcd.controllers;

import java.io.IOException;
import java.net.URISyntaxException;

import org.odesamama.mcd.domain.File;
import org.odesamama.mcd.repositories.FileRepository;
import org.odesamama.mcd.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by starnakin on 07.10.2015.
 */

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    FileService fileService;

    @Autowired
    private FileRepository fileRepository;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody HttpStatus uploadFile(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file, @RequestParam("email") String email)
                    throws IOException, URISyntaxException {
        fileService.uploadFileToHDFSServer(file.getBytes(), name, email);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<File> getFiles() {
        return fileRepository.findAll();
    }
}
