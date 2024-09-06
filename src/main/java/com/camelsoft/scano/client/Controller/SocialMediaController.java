package com.camelsoft.scano.client.Controller;


import com.camelsoft.scano.client.Requests.Auth.companyrequest;
import com.camelsoft.scano.client.models.Auth.company;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.models.File.File_model;
import com.camelsoft.scano.client.models.UserTools.adminsocialmedia;
import com.camelsoft.scano.client.models.UserTools.socialmedia;
import com.camelsoft.scano.client.services.Auth.UserService;
import com.camelsoft.scano.client.services.File.FileServices;
import com.camelsoft.scano.client.services.File.FilesStorageServiceImpl;
import com.camelsoft.scano.client.services.projecttools.SocialmediaService;
import com.camelsoft.scano.client.services.projecttools.adminsocialmediaService;
import com.camelsoft.scano.tools.util.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/socialmedia")
public class SocialMediaController extends BaseController {

    @Autowired
    private SocialmediaService socialmediaService;

    @Autowired
    private adminsocialmediaService adminsocialmediaservice;

    @Autowired
    private UserService userService;

    @Autowired
    private FilesStorageServiceImpl filesStorageService;

    @Autowired
    private FileServices fileServices;

    private static final List<String> image_accepte_type = Arrays.asList("JPEG", "jpeg", "svg", "png", "SVG", "PNG", "JPG", "jpg");

    @PatchMapping(value = {"/add_update"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<users> add(@RequestParam(required = false) Boolean show,@RequestParam(required = false) String url,@RequestParam(required = false) Long id,@RequestParam(required = false) Long socialmediaadminid) {
        users user = userService.findByUserName(getCurrentUser().getUsername());

        if (!this.adminsocialmediaservice.existbyid(socialmediaadminid))
            return new ResponseEntity("wrong id" + socialmediaadminid, HttpStatus.BAD_REQUEST);

        adminsocialmedia adminsocialmedi= this.adminsocialmediaservice.findById(socialmediaadminid);
        if (id!=null){
            if (!this.socialmediaService.existbyid(id))
                return new ResponseEntity("data missing", HttpStatus.BAD_REQUEST);
            socialmedia socialmedias = this.socialmediaService.findById(id);
            if (show != null)
                socialmedias.setShow(show);

            if (url != null)
                socialmedias.setUrl(url);
            this.socialmediaService.Updatesocialmedia(socialmedias);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        socialmedia socialmedias = new socialmedia(adminsocialmedi,url);
        user.addSocialmedias(socialmedias);
        users res = this.userService.UpdateUser(user);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @PatchMapping(value = {"/admin_add_update"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<adminsocialmedia> admin_add_update (@RequestParam(required = false) Long id,@RequestParam(required = false) String nom, @RequestParam(value = "preview", required = false) MultipartFile preview) {


        File_model resource_media = null;

        if (preview != null) {
            String extention = preview.getContentType().substring(preview.getContentType().indexOf("/") + 1).toLowerCase(Locale.ROOT);
            if (extention.contains("+xml") && extention.contains("svg"))
                extention = "svg";

            if (!image_accepte_type.contains(extention))
                return new ResponseEntity("this type is not acceptable : " + extention, HttpStatus.BAD_REQUEST);

            resource_media = filesStorageService.save_file(preview, "adminsocialmedia", preview.getOriginalFilename(), extention);
        }
        adminsocialmedia socialmedias = new adminsocialmedia(nom,resource_media);

        if (id!=null){
            if (!this.adminsocialmediaservice.existbyid(id))
                return new ResponseEntity("data missing", HttpStatus.BAD_REQUEST);
             socialmedias = this.adminsocialmediaservice.findById(id);
             if (nom!=null)
             socialmedias.setName(nom);
             if (socialmedias.getImage()!=null) {
                 File_model f = socialmedias.getImage();
                 socialmedias.setImage(null);
                 socialmedias = this.adminsocialmediaservice.Updatesocialmedia(socialmedias);
                 this.filesStorageService.delete_file_by_path(f.getUrl(), f.getId());
             }
            socialmedias.setImage(resource_media);

        }

        adminsocialmedia res = this.adminsocialmediaservice.Updatesocialmedia(socialmedias);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @GetMapping(value = {"/get"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<socialmedia>> get() {
        users user = userService.findByUserName(getCurrentUser().getUsername());
        List<socialmedia> media = this.socialmediaService.findAllbyUser(user);
        return new ResponseEntity<>(media, HttpStatus.OK);
    }

    @GetMapping(value = {"/get_admin_list"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<adminsocialmedia>> get_admin_list () {
        List<adminsocialmedia> media = this.adminsocialmediaservice.findAll();
        return new ResponseEntity<>(media, HttpStatus.OK);
    }

    @DeleteMapping(value = {"/remove/{id}"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<users> remove(@PathVariable Long id) {
        users user = userService.findByUserName(getCurrentUser().getUsername());
        if (!this.socialmediaService.existbyidAnduserid(id, user))
            return new ResponseEntity("data missing", HttpStatus.BAD_REQUEST);
        user.removeSocialmedias(id);
        this.socialmediaService.deleteById(id);
        users res = this.userService.UpdateUser(user);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping(value = {"/admin_remove/{id}"})
    @PreAuthorize("hasRole('ADMIN') ")
    public ResponseEntity<String> admin_remove (@PathVariable Long id) {
        if (!this.adminsocialmediaservice.existbyid(id))
            return new ResponseEntity("data missing", HttpStatus.BAD_REQUEST);
        this.adminsocialmediaservice.deleteById(id);
        return new ResponseEntity<>("Done !", HttpStatus.OK);
    }


}


