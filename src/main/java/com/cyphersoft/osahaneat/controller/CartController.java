package com.cyphersoft.osahaneat.controller;
import com.cyphersoft.osahaneat.dto.CartDTO;
import com.cyphersoft.osahaneat.service.CartService;
import com.cyphersoft.osahaneat.service.imp.FileServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@CrossOrigin("*")
@RequestMapping("/cart")
@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    FileServiceImp fileServiceImp;


    @PreAuthorize("permitAll()")
    @GetMapping("/{userId}")
    public CartDTO getCart(@PathVariable int userId) {
        return cartService.getCartByUserId(userId);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/file/{filename:.+}")
    public ResponseEntity<?> getFileRestaurant(@PathVariable String filename ){
        Resource resource = fileServiceImp.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }

}
