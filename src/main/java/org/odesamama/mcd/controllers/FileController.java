package org.odesamama.mcd.controllers;

import org.odesamama.mcd.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by starnakin on 07.10.2015.
 */

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    FileService fileService;

    @RequestMapping(value="/upload", method= RequestMethod.POST)
    public @ResponseBody String uploadFile(@RequestParam("name") String name, @RequestParam("file") MultipartFile file){
        try {
            fileService.uploadFileToHDFSServer(file.getBytes(), name);
        } catch (Exception e) {
            return "Unable to write file";
        }

        return "Success";
    }

}
