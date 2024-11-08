package org.chiches.foodrootservir.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileUploadDTO {
    private Long id;
    private List<MultipartFile> files;

    public FileUploadDTO(Long id, List<MultipartFile> files) {
        this.id = id;
        this.files = files;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }
}
