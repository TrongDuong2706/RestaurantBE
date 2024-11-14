package com.cyphersoft.osahaneat.service.imp;

import com.cyphersoft.osahaneat.dto.MenuDTO;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MenuServiceImp {
    boolean createMenu( MultipartFile file, String title , boolean is_freeship, String time_ship,String description, double price, int cate_id, int status);
    List<MenuDTO> getAllFood(int page, int size, String keyword, Integer status, Integer cateId);


    MenuDTO getOneFood(int foodId);

    boolean updateMenu(int foodId, MultipartFile file, String title, boolean is_freeship, String time_ship, String description, double price, int cate_id, int status);

    boolean softDeleteMenu(int foodId); // Add this method for soft deleting



}
