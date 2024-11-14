package com.cyphersoft.osahaneat.service;

import com.cyphersoft.osahaneat.dto.MenuDTO;
import com.cyphersoft.osahaneat.entity.Category;
import com.cyphersoft.osahaneat.entity.Food;
import com.cyphersoft.osahaneat.repository.FoodRepository;
import com.cyphersoft.osahaneat.service.imp.FileServiceImp;
import com.cyphersoft.osahaneat.service.imp.MenuServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService implements MenuServiceImp {

    @Autowired
    FileServiceImp fileServiceImp;

    @Autowired
    FoodRepository foodRepository;
    @Override
    public boolean createMenu(MultipartFile file, String title, boolean is_freeship, String time_ship,String description, double price, int cate_id, int status) {
        boolean isInsertSucess = false;
        try{
            boolean isSaveFileSuccess = fileServiceImp.saveFile(file);
            if(isSaveFileSuccess){
                Food food = new Food();
                food.setTitle(title);
                food.setImage(file.getOriginalFilename());
                food.setTimeShip(time_ship);
                food.setPrice(price);
                food.setFreeship(is_freeship);
                food.setStatus(status);
                food.setDescription(description);
                Category category = new Category();
                category.setId(cate_id);
                food.setCategory(category);
                foodRepository.save(food);
                isInsertSucess = true;
            }
        }
        catch(Exception e){
            System.out.println("Error insert restaurant "+e.getMessage());
        }

        return isInsertSucess;
    }

    @Override
    public List<MenuDTO> getAllFood(int page, int size, String keyword, Integer status, Integer cateId) {
        List<MenuDTO> menuDTOS = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<Food> listData;
        if (keyword != null || status != null || cateId != null) {
            Specification<Food> spec = Specification.where(null);
            if (keyword != null) {
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + keyword.toLowerCase() + "%"));
            }
            if (status != null) {
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("status"), status));
            }
            if (cateId != null) {
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("category").get("id"), cateId));
            }
            listData = foodRepository.findAll(spec, pageRequest);
        } else {
            listData = foodRepository.findAll(pageRequest);
        }

        long totalElements = listData.getTotalElements();

        for (Food data : listData) {
            MenuDTO menuDTO = new MenuDTO();
            menuDTO.setId(data.getId());
            menuDTO.setImage(data.getImage());
            menuDTO.setTitle(data.getTitle());
            menuDTO.setFreeShip(data.isFreeship());
            menuDTO.setDescription(data.getDescription());
            menuDTO.setPrice(data.getPrice());
            menuDTO.setTimeShip(data.getTimeShip());
            menuDTO.setTotalItems(totalElements);
            menuDTO.setStatus(data.getStatus());
            menuDTO.setCategory(data.getCategory().getNameCate());
            menuDTOS.add(menuDTO);
        }
        return menuDTOS;
    }

    @Override
    public MenuDTO getOneFood(int foodId) {
        Food food = foodRepository.findById(foodId).orElse(null);
        if(food != null) {
            MenuDTO menuDTO = new MenuDTO();
            menuDTO.setId(food.getId());
            menuDTO.setPrice(food.getPrice());
            menuDTO.setTitle(food.getTitle());
            menuDTO.setImage(food.getImage());
            menuDTO.setCategoryId(food.getCategory().getId());
            menuDTO.setTimeShip(food.getTimeShip());
            menuDTO.setStatus(food.getStatus());
            menuDTO.setDescription(food.getDescription());
            return menuDTO;
        } else {
            return null;
        }
    }

    @Override
    public boolean updateMenu(int foodId, MultipartFile file, String title, boolean is_freeship, String time_ship, String description, double price, int cate_id, int status) {
        boolean isUpdateSuccess = false;
        try {
            Food existingFood = foodRepository.findById(foodId).orElse(null);
            if (existingFood != null) {
                if (file != null && !file.isEmpty()) {
                    boolean isSaveFileSuccess = fileServiceImp.saveFile(file);
                    if (isSaveFileSuccess) {
                        existingFood.setImage(file.getOriginalFilename());
                    } else {
                        throw new Exception("Failed to save file");
                    }
                }

                existingFood.setTitle(title);
                existingFood.setTimeShip(time_ship);
                existingFood.setPrice(price);
                existingFood.setFreeship(is_freeship);
                existingFood.setStatus(status);
                existingFood.setDescription(description);
                Category category = new Category();
                category.setId(cate_id);
                existingFood.setCategory(category);

                foodRepository.save(existingFood);
                isUpdateSuccess = true;
            } else {
                System.out.println("Food with ID " + foodId + " not found");
            }
        } catch (Exception e) {
            System.out.println("Error updating food: " + e.getMessage());
        }
        return isUpdateSuccess;
    }

    @Override
    public boolean softDeleteMenu(int foodId) {
        Food food  = foodRepository.findById(foodId).orElse(null);
        if(food!= null){
            food.setStatus(0);
            foodRepository.save(food);
            return true;
        }
        return false;
    }


}
