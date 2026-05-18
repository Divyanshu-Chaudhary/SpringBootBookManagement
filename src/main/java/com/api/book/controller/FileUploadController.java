package com.api.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
// import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.book.helper.FileUploadHelper;


@RestController
public class FileUploadController {
    
    @Autowired
    private FileUploadHelper fileUploadHelper;
    
    @PostMapping("/upload-file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) { 
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        System.out.println(file.getContentType());

        try{
            if(file.isEmpty()){
                return ResponseEntity.badRequest().body("File is empty");
            }
            // File upload 
            boolean f = fileUploadHelper.uploadFile(file);
            if(f){
                return ResponseEntity.ok("File uploaded successfully");
                // return ResponseEntity.ok(ServletUriComponentsBuilder.fromCurrentContextPath().path("/images/").path(file.getOriginalFilename()).toUriString());
            }

        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error uploading file");
        }

        


        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong! Try again") ;
    }

}
