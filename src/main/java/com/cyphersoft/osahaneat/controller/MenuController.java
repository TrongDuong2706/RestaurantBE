package com.cyphersoft.osahaneat.controller;

import com.cyphersoft.osahaneat.dto.MenuDTO;
import com.cyphersoft.osahaneat.dto.UserDTO;
import com.cyphersoft.osahaneat.payload.ResponseData;
import com.cyphersoft.osahaneat.service.imp.FileServiceImp;
import com.cyphersoft.osahaneat.service.imp.MenuServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    MenuServiceImp menuServiceImp;

    @Autowired
    FileServiceImp fileServiceImp;

    @PostMapping()
    public ResponseEntity<?> createMenu(
            @RequestParam MultipartFile file,
            @RequestParam String title,
            @RequestParam boolean is_freeship,
            @RequestParam String time_ship,
            @RequestParam String description,
            @RequestParam double price,
            @RequestParam int cate_id,
            @RequestParam int status
    ){
        ResponseData responseData = new ResponseData();
        boolean isSuccess = menuServiceImp.createMenu(file, title, is_freeship, time_ship, description, price, cate_id, status);
        responseData.setData(isSuccess);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllFood(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer cateId) {

        ResponseData responseData = new ResponseData();
        responseData.setData(menuServiceImp.getAllFood(page, size, keyword, status, cateId));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }


    @PostMapping("/update/{foodId}")
    public ResponseData updateFood(@PathVariable("foodId") int foodId,
                                   @RequestParam(value = "file", required = false) MultipartFile file,
                                   @RequestParam("title") String title,
                                   @RequestParam("is_freeship") boolean is_freeship,
                                   @RequestParam("time_ship") String time_ship,
                                   @RequestParam("description") String description,
                                   @RequestParam("price") double price,
                                   @RequestParam("cate_id") int cate_id,
                                   @RequestParam("status") int status) {
        ResponseData responseData = new ResponseData();
        boolean isSuccess = menuServiceImp.updateMenu(foodId, file, title, is_freeship, time_ship, description, price, cate_id, status);
        if (isSuccess) {
            responseData.setSuccess(true);
            responseData.setStatus(200);
            responseData.setData(isSuccess);
            responseData.setDesc("Food updated successfully");
        } else {
            responseData.setSuccess(false);
            responseData.setStatus(500);
            responseData.setData(isSuccess);
            responseData.setDesc("Failed to update food");
        }
        return responseData;
    }

    @GetMapping("/{foodId}")
    public ResponseEntity<?> getFoodById(@PathVariable int foodId){
        ResponseData responseData = new ResponseData();
        responseData.setData(menuServiceImp.getOneFood(foodId));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/file/{filename:.+}")
    public ResponseEntity<?> getFileRestaurant(@PathVariable String filename ){
        Resource resource = fileServiceImp.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }

    @DeleteMapping("/delete/{foodId}")
    public ResponseEntity<?> softDeleteMenu(@PathVariable int foodId) {
        boolean isDeleted = menuServiceImp.softDeleteMenu(foodId);
        if (isDeleted) {
            return ResponseEntity.ok("Menu item soft deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Menu item not found");
        }
    }
}
