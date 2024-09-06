package com.camelsoft.scano.client.Repository.File;


import com.camelsoft.scano.client.models.File.File_model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface FilesStorageService {

    public File_model save_file(MultipartFile file, String directory, String filename, String extention);
    public Set<File_model> save_all(List<MultipartFile> file, String directory, String name);
    public void delete_file_by_path(String filename,Long imageid);
    public void delete_all_file_by_path(Set<File_model> images);
}
