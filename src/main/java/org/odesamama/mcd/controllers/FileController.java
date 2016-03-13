package org.odesamama.mcd.controllers;

import org.apache.commons.lang3.StringUtils;
import org.odesamama.mcd.domain.File;
import org.odesamama.mcd.domain.Group;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.domain.enums.Permissions;
import org.odesamama.mcd.exeptions.NoSuchResourceException;
import org.odesamama.mcd.exeptions.UserNotExistsException;
import org.odesamama.mcd.repositories.FileRepository;
import org.odesamama.mcd.repositories.UserRepository;
import org.odesamama.mcd.services.FileService;
import org.odesamama.mcd.services.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private FileService fileService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody HttpStatus uploadFile(@RequestParam("filePath") String filePath,
            @RequestParam("file") MultipartFile file, @RequestParam("email") String email)
                    throws IOException, URISyntaxException {
        fileService.uploadFileToHDFSServer(file.getBytes(), filePath, email);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/createfolder", method = RequestMethod.POST)
    public @ResponseBody HttpStatus createFolder(@RequestParam("path") String path, @RequestParam("email") String email)
            throws IOException, URISyntaxException {

        fileService.createFolder(path, email);

        return HttpStatus.OK;
    }

    @RequestMapping(value = "/sharefolder", method = RequestMethod.POST)
    public void shareFolder(@RequestParam("path") String filePath, @RequestParam("email") String email,
            @RequestParam("userUid") String userUid, @RequestParam("permissions") String permissions) {
        validateFolder(email, filePath);

        User user = userRepository.findByEmail(userUid);
        if (user == null) {
            throw new UserNotExistsException("User: " + userUid);
        }

        File file = fileRepository.getFileInfoByFilePathAndEmail(email, filePath);
        if (file == null) {
            throw new NoSuchResourceException("File:" + filePath + ", Owner: " + email);
        }

        Group group = groupService.getOrCreateGroup(file);
        groupService.addUserToGroup(user, group, Permissions.valueOf(permissions));
        fileService.updateFileGroup(file, email, group);
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
            logger.debug("file {} not found for email {}", filePath, email);
            throw new NoSuchResourceException();
        }

        String headerValue = String.format("attachment; filename=\"%s\"", file.getName());

        return ResponseEntity.ok().contentLength(file.getSize()).contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(new InputStreamResource(fileService.getFile(filePath, email)));

    }

    @RequestMapping(value = "/list/{email:.+}/**", method = RequestMethod.GET)
    public Iterable<File> getFileListForGivenPath(@PathVariable String email, HttpServletRequest request) {
        String filePath = exctractPathFromRequest(request, email);
        validateFolder(email, filePath);

        return fileRepository.getListByPath(email, filePath);
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
                throw new NoSuchResourceException("Resource was not found");
            }
        }
    }
}
