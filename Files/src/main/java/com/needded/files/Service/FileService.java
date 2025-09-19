package com.needded.files.Service;

import com.needded.files.Entity.File;
import com.needded.files.Repository.FileRepository;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService (FileRepository filesRepository){
        this.fileRepository=filesRepository;
    }


    public List<File> findAllByUserId(@NonNull String userId) {
        try {
            return fileRepository.findAllFilesByUserId(userId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return List.of();
    }

    public File getFileById(Long fileId, @NonNull String userId) {

        try {
        return fileRepository.findFileByIdAndUserId();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public File createFile(@NonNull File file) {

        try {
            return fileRepository.save(file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public File editFile (@NonNull Long id, @NonNull File fileToUpdate){

        try {

            File file = fileRepository.findById(id)
                    .orElseThrow(FileNotFoundException::new);

            file.setFile(fileToUpdate.getFile());

            return fileRepository.save(file);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    public void deleteFile(@NonNull Long fileId, @NonNull String userId) {

        try {
            fileRepository.deleteFileByIdAndUserId(fileId, userId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public String extractUserId (@NonNull String headerToken){
        String token = headerToken.substring(7); // remove "Bearer "

        return null; //filesJWTService.extractUserId(token);
    }
}
