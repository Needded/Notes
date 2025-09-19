package com.needded.files.Repository;

import com.needded.files.Entity.File;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository <File,Long> {

    List<File> findAllFilesByUserId(String userId);

    File findFileByIdAndUserId();

    void deleteFileByIdAndUserId(Long fileId, @NonNull String userId);
}
