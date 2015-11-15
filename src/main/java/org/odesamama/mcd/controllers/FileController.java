package org.odesamama.mcd.controllers;

import org.apache.commons.lang3.StringUtils;
import org.odesamama.mcd.domain.File;
import org.odesamama.mcd.exeptions.NoSuchResourceException;
import org.odesamama.mcd.repositories.FileRepository;
import org.odesamama.mcd.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private FileRepository fileRepository;

    private static final int BUFFER_SIZE = 4096;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody HttpStatus uploadFile(@RequestParam("filePath") String filePath,
            @RequestParam("file") MultipartFile file, @RequestParam("email") String email)
                    throws IOException, URISyntaxException {
        fileService.uploadFileToHDFSServer(file.getBytes(), filePath, email);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<File> getFiles() {
        return fileRepository.findAll();
    }

    @RequestMapping(value="/createfolder", method= RequestMethod.POST)
    public @ResponseBody HttpStatus createFolder(@RequestParam("path") String path, @RequestParam("email") String email) throws IOException, URISyntaxException {

        fileService.createFolder(path, email);

        return  HttpStatus.OK;
    }

    @RequestMapping(value = "/download/{email:.+}/**", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String email, HttpServletRequest request)
            throws IOException, URISyntaxException {

        String filePath = getFilePathFromRequest(request,email);

        ServletContext context = request.getServletContext();

        MediaType mediaType;
        String mimeType = context.getMimeType(filePath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }
        else{
            mediaType = MediaType.parseMediaType(mimeType);
        }

        File file = fileRepository.getFileInfoByFilePathAndEmali(email, filePath);

        if(file == null){
            throw new NoSuchResourceException();
        }

        String headerValue = String.format("attachment; filename=\"%s\"", file.getFileName());

        return ResponseEntity
                .ok()
                .contentLength(file.getFileSize())
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(new InputStreamResource(fileService.getFile(filePath, email)));

    }

    @RequestMapping(value = "/list/{email:.+}/**", method = RequestMethod.GET)
    private Iterable<File> getFileListForGivenPath(@PathVariable String email, HttpServletRequest request){
        String filePath = getFilePathFromRequest(request,email);

        //if file is not set return files from root folder
        if(StringUtils.trimToNull(filePath) != null) {
            File file = fileRepository.getFileInfoByFilePathAndEmali(email, filePath);

            if (!file.isFolder()) {
                throw new NoSuchResourceException();
            }
        }

        return fileRepository.getFilesListForGivenDirectoryPath(email, filePath);
    }

    private String getFilePathFromRequest(HttpServletRequest request, String email){
        String filePath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

        String bestMatchPattern = (String ) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

        AntPathMatcher apm = new AntPathMatcher();
        filePath = apm.extractPathWithinPattern(bestMatchPattern, filePath);

        return filePath.replace(email,"");
    }
}

