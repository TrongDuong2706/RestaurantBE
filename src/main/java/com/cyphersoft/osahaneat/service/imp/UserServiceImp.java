package com.cyphersoft.osahaneat.service.imp;

import com.cyphersoft.osahaneat.dto.UserDTO;
import com.cyphersoft.osahaneat.entity.Users;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserServiceImp {
    List<UserDTO> getAllUser();

    UserDTO getOneUser(int id);
}
