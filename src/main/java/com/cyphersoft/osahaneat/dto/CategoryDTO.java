package com.cyphersoft.osahaneat.dto;

import java.util.List;

public class CategoryDTO {
    private int id;
    private String name;
    List<MenuDTO> menus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuDTO> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuDTO> menus) {
        this.menus = menus;
    }
}
