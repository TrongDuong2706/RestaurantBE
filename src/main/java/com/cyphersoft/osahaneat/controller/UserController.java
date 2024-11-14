package com.cyphersoft.osahaneat.controller;

import com.cyphersoft.osahaneat.dto.UserDTO;
import com.cyphersoft.osahaneat.payload.ResponseData;
import com.cyphersoft.osahaneat.service.imp.FileServiceImp;
import com.cyphersoft.osahaneat.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceImp userServiceImp;

    @Autowired
    FileServiceImp fileServiceImp;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<?> getAllUser(){

        return new ResponseEntity<>(userServiceImp.getAllUser(), HttpStatus.OK);
    }


    @PreAuthorize("permitAll()")
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable int userId){
        UserDTO userDTO = userServiceImp.getOneUser(userId);
        if(userDTO != null) {
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }


    @PreAuthorize("permitAll()")
    @GetMapping("/file/{filename:.+}")
    public ResponseEntity<?> getFileRestaurant(@PathVariable String filename) {
        Resource resource = fileServiceImp.loadFile(filename);

        if (resource != null && resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            // Xử lý khi không tìm thấy file
            return ResponseEntity.notFound().build();
        }
    }


}
