package org.odesamama.mcd.controllers;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.odesamama.mcd.domain.File;
import org.odesamama.mcd.exeptions.NoSuchResourceException;
import org.odesamama.mcd.repositories.FileRepository;
import org.odesamama.mcd.services.FileService;
import org.odesamama.mcd.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

/**
 * Created by starnakin on 07.10.2015.
 */

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private FileRepository fileRepository;

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

    @RequestMapping(value = "/createfolder", method = RequestMethod.POST)
    public @ResponseBody HttpStatus createFolder(@RequestParam("path") String path, @RequestParam("email") String email)
            throws IOException, URISyntaxException {

        fileService.createFolder(path, email);
        groupService.createGroup(email);

        return HttpStatus.OK;
    }

    @RequestMapping(value = "/download/{email:.+}/**", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String email, HttpServletRequest request)
            throws IOException, URISyntaxException {

        String filePath = exctractPathFromRequest(request, email);

        ServletContext context = request.getServletContext();

        String mimeType = context.getMimeType(filePath);
        // set to binary type if MIME mapping not found
        MediaType mediaType = mimeType == null ? MediaType.APPLICATION_OCTET_STREAM
                : MediaType.parseMediaType(mimeType);

        File file = fileRepository.getFileInfoByFilePathAndEmail(email, filePath);

        if (file == null) {
            throw new NoSuchResourceException();
        }

        String headerValue = String.format("attachment; filename=\"%s\"", file.getName());

        return ResponseEntity.ok().contentLength(file.getSize()).contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(new InputStreamResource(fileService.getFile(filePath, email)));

    }

    @RequestMapping(value = "/list/{email:.+}/**", method = RequestMethod.GET)
    private Iterable<File> getFileListForGivenPath(@PathVariable String email, HttpServletRequest request) {
        String filePath = exctractPathFromRequest(request, email);
        validateFolder(email, filePath);

        return fileRepository.getFilesListForGivenDirectoryPath(email, filePath);
    }

    private String exctractPathFromRequest(HttpServletRequest request, String email) {
        String filePath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

        AntPathMatcher apm = new AntPathMatcher();
        return "/" + apm.extractPathWithinPattern(bestMatchPattern, filePath);
    }

    private void validateFolder(String email, String filePath) {
        if (StringUtils.trimToNull(filePath) != null) {
            File file = fileRepository.getFileInfoByFilePathAndEmail(email, filePath);

            if (file == null || !file.isFolder()) {
                throw new NoSuchResourceException();
            }
        }
    }
}
