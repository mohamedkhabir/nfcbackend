package com.camelsoft.scano.client.services.File;

import com.camelsoft.scano.client.Repository.File.File_Repository;
import com.camelsoft.scano.client.Repository.File.FilesStorageService;
import com.camelsoft.scano.client.models.File.File_model;
import com.camelsoft.scano.client.services.QRCodeGenerator;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {
    @Autowired
    private File_Repository repository;
    @Autowired
    private QRCodeGenerator qrCodeGenerator;
    private static final List<String> image_accepte_type = Arrays.asList("JPEG", "jpeg", "svg", "png", "SVG", "PNG","JPG","jpg","pdf","mp4");

    @Override
    public File_model save_file(MultipartFile file, String directory, String name, String extention) {

        try {
            Path root = Paths.get("WebContent/"+directory);
            if(!Files.exists(root))
                Files.createDirectories(root);

            String namesaved = ((name.substring(0, name.lastIndexOf("."))+(new Date()).getTime()).replaceAll("\\s+","")+"."+extention);
            Path filepath = root.resolve(namesaved);
            Resource resourcepast = new UrlResource(filepath.toUri());
            if (resourcepast.exists() || resourcepast.isReadable())
                FileSystemUtils.deleteRecursively(filepath.toFile());

            Files.copy(file.getInputStream(), root.resolve(namesaved));
            Path file_info = root.resolve(namesaved);
            Resource resource = new UrlResource(file_info.toUri());

            if (resource.exists() || resource.isReadable()) {
                File_model fileresult = new File_model(
                        name,
                        resource.getURI().getPath(),
                        file.getContentType(),
                        file.getSize()
                );

                return this.repository.save(fileresult);

            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }

    }

    public File_model saveQRCodeImageFromUrl(String url, String directory, String name, String extention)
            throws WriterException, IOException {
        try {


            BufferedImage qrCodeImage = qrCodeGenerator.generateQRCodeImage(url);

            // Saving the QR code image
            Path root = Paths.get("WebContent/" + directory);
            if (!Files.exists(root))
                Files.createDirectories(root);

            String namesaved = ((name.substring(0, name.lastIndexOf(".")) + (new Date()).getTime())
                    .replaceAll("\\s+", "") + "." + extention);
            Path filePath = root.resolve(namesaved);

            ImageIO.write(qrCodeImage, "PNG", Files.newOutputStream(filePath));

            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                File_model fileResult = new File_model(
                        name,
                        resource.getURI().getPath(),
                        "image/png", // Assuming PNG format for QR codes
                        Files.size(filePath)
                );

                return this.repository.save(fileResult);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not store the QR code. Error: " + e.getMessage());
        }
    }
    @Override
    public void delete_file_by_path(String path,Long imageid) {
        Path root_local= Paths.get(path);
        if(this.repository.existsById(imageid))
            this.repository.deleteById(imageid);
        FileSystemUtils.deleteRecursively(root_local.toFile());
    }
    @Override
    public void delete_all_file_by_path(Set<File_model> images) {
         for(int i = 0 ; i<images.size();i++){
             Path root_local= Paths.get(images.iterator().next().getUrl());
             if(this.repository.existsById(images.iterator().next().getId()))
                 this.repository.deleteById(images.iterator().next().getId());
             FileSystemUtils.deleteRecursively(root_local.toFile());
        }

    }

    @Override
    public Set<File_model> save_all(List<MultipartFile> file, String directory, String name){
         try {
             Set<File_model> images = new HashSet<>();
             for(int i = 0 ; i<file.size();i++){
                 String extention = file.get(i).getContentType().substring(file.get(i).getContentType().indexOf("/") + 1).toLowerCase(Locale.ROOT);
                 if (extention.contains("+xml") && extention.contains("svg"))
                     extention = "svg";
                 if (!image_accepte_type.contains(extention))
                     throw new RuntimeException("Could not read the file!");
                 File_model   resource_media = this.save_file(file.get(i), directory, name+i, extention);
                 resource_media.setRange(i);
                 images.add(resource_media);
             }
             return images;
         } catch (Exception e) {
             throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
         }

    }


}
