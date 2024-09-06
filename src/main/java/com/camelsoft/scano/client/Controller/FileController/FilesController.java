package com.camelsoft.scano.client.Controller.FileController;


import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.models.File.File_model;
import com.camelsoft.scano.client.models.UserTools.adminsocialmedia;
import com.camelsoft.scano.client.services.Auth.UserService;
import com.camelsoft.scano.client.services.File.FileServices;
import com.camelsoft.scano.client.services.File.FilesStorageServiceImpl;
import com.camelsoft.scano.tools.util.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/files")
public class FilesController extends BaseController {


    @Autowired
    private UserService userService;

    @Autowired
    private FilesStorageServiceImpl filesStorageService;

    @Autowired
    private FileServices fileServices;

    private static final List<String> image_accepte_type = Arrays.asList("JPEG", "jpeg", "svg", "png", "SVG", "PNG", "JPG", "jpg");

    @PostMapping(value = {"/add"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<users> add (@RequestParam(required = false) String nom, @RequestParam(value = "preview") MultipartFile preview) {

        users user = userService.findByUserName(getCurrentUser().getUsername());

        File_model resource_media = null;

        String extention = preview.getContentType().substring(preview.getContentType().indexOf("/") + 1).toLowerCase(Locale.ROOT);
        if (extention.contains("+xml") && extention.contains("svg"))
            extention = "svg";

        resource_media = filesStorageService.save_file(preview, "files",nom, extention);

        user.getFiles().add(resource_media);

        users result =this.userService.UpdateUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping(value = {"/update/{file_id}"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<users> update (@PathVariable Long file_id,@RequestParam(required = false) String nom, @RequestParam(value = "preview") MultipartFile preview) {

        users user = userService.findByUserName(getCurrentUser().getUsername());

        if (!this.fileServices.existbyid(file_id))
            return new ResponseEntity("invalid id: " + file_id, HttpStatus.BAD_REQUEST);


        File_model resource_media = this.fileServices.findbyid(file_id);

        user.getFiles().remove(resource_media);

        String extention = preview.getContentType().substring(preview.getContentType().indexOf("/") + 1).toLowerCase(Locale.ROOT);
        if (extention.contains("+xml") && extention.contains("svg"))
            extention = "svg";

        this.filesStorageService.delete_file_by_path(resource_media.getUrl(), resource_media.getId());

        resource_media = filesStorageService.save_file(preview, "files",nom, extention);

        user.getFiles().add(resource_media);

        users result =this.userService.UpdateUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = {"/remove/{file_id}"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<users> remove (@PathVariable Long file_id) {

        users user = userService.findByUserName(getCurrentUser().getUsername());



        if (!this.fileServices.existbyid(file_id))
            return new ResponseEntity("invalid id: " + file_id, HttpStatus.BAD_REQUEST);


        File_model resource_media = this.fileServices.findbyid(file_id);


        user.getFiles().remove(resource_media);


        this.filesStorageService.delete_file_by_path(resource_media.getUrl(), resource_media.getId());


        users result =this.userService.UpdateUser(user);


        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = {"/get"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<File_model>> get () {

        users user = userService.findByUserName(getCurrentUser().getUsername());


        List<File_model> comments = new ArrayList<File_model>();
        comments.addAll(user.getFiles());


        return new ResponseEntity<>(comments, HttpStatus.OK);
    }






}
