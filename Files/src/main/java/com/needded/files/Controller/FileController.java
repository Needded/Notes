package com.needded.files.Controller;

import com.needded.files.Entity.File;
import com.needded.files.Service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("files/")
public class FileController {

    private static final Logger logger= LoggerFactory.getLogger(FileController.class);
    private final FileService fileService;

    public FileController (FileService fileService) {
        this.fileService=fileService;
    }

    @GetMapping("getAll")
    public ResponseEntity<List<File>> getAllFiles (@RequestHeader("Authorization") String authHeader){
        String userId = fileService.extractUserId(authHeader);

        logger.info("Getting all files...");
        logger.info("User id: {}",userId);


        List<File> files= fileService.findAllByUserId(userId);

        return ResponseEntity.ok(files);
    }

    @GetMapping("getById/{fileId}")
    public ResponseEntity <File> getNoteById (@PathVariable Long fileId, @RequestHeader("Authorization") String authHeader){
        String userId = fileService.extractUserId(authHeader);

        logger.info("Getting file...");
        logger.info("User id: {}",userId);

        File file= fileService.getFileById(fileId, userId);

        return  ResponseEntity.ok(file);
    }

    @PostMapping("save")
    public ResponseEntity<File> save(@RequestBody File file, @RequestHeader("Authorization") String authHeader){
        String userId = fileService.extractUserId(authHeader);

        logger.info("Saving file...");
        logger.info("User id: {}",userId);

        file.setUserId(userId);

        File saved;

        if (file.getId() == null) {
            saved = fileService.createFile(file);
        } else {
            saved = fileService.editFile(file.getId(), file);
        }

        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("deleteFile/{fileId}")
    public ResponseEntity<String> deleteNote(@PathVariable Long fileId, @RequestHeader("Authorization") String authHeader){
        String userId = fileService.extractUserId(authHeader);

        logger.info("Deleting file...");
        logger.info("User id: {}",userId);

        fileService.deleteFile(fileId, userId);

        return ResponseEntity.ok("File deleted.");
    }
}
