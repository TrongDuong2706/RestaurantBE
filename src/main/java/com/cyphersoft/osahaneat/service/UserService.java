package com.cyphersoft.osahaneat.service;

import com.cyphersoft.osahaneat.dto.UserDTO;
import com.cyphersoft.osahaneat.entity.Restaurant;
import com.cyphersoft.osahaneat.entity.Users;
import com.cyphersoft.osahaneat.repository.UserRepository;
import com.cyphersoft.osahaneat.service.imp.FileServiceImp;
import com.cyphersoft.osahaneat.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class UserService implements UserServiceImp {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FileServiceImp fileServiceImp;
    @Override
    public List<UserDTO> getAllUser() {
        List<Users> listUser = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(Users users: listUser){
            UserDTO userDTO = new UserDTO();
            userDTO.setId(users.getId());
            userDTO.setAvatar(users.getAvatar());
            userDTO.setUserName(users.getUserName());
            userDTO.setFullname(users.getFullName());
            userDTO.setPassword(users.getPassword());
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    @Override
    public UserDTO getOneUser(int userId) {
        Users user = userRepository.findById(userId).orElse(null);
        if(user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setAvatar(user.getAvatar());
            userDTO.setUserName(user.getUserName());
            userDTO.setFullname(user.getFullName());
            userDTO.setPassword(user.getPassword());
            userDTO.setUserRole(user.getRoles().getId());
            return userDTO;
        } else {
            return null;
        }
    }


}
