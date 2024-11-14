package com.cyphersoft.osahaneat.service.imp;

import com.cyphersoft.osahaneat.dto.CategoryDTO;
import com.cyphersoft.osahaneat.entity.Category;

import java.util.List;

public interface CategoryServiceImp {

    List<CategoryDTO> getCategoryHomePage();
    List<CategoryDTO> getAllCategory();
}
