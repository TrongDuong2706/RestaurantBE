package com.cyphersoft.osahaneat.controller;

import com.cyphersoft.osahaneat.dto.UserDTO;
import com.cyphersoft.osahaneat.entity.Users;
import com.cyphersoft.osahaneat.payload.LoginResponse;
import com.cyphersoft.osahaneat.payload.ResponseData;
import com.cyphersoft.osahaneat.payload.request.SignUpRequest;
import com.cyphersoft.osahaneat.repository.UserRepository;
import com.cyphersoft.osahaneat.service.LoginService;
import com.cyphersoft.osahaneat.service.TokenBlacklistService;
import com.cyphersoft.osahaneat.service.imp.LoginServiceImp;
import com.cyphersoft.osahaneat.utils.JwtUtilsHelper;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoder;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginServiceImp loginServiceImp;

    @Autowired
    JwtUtilsHelper jwtUtilsHelper;

    @Autowired
    TokenBlacklistService tokenBlacklistService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam String username, @RequestParam String password) {
        LoginResponse loginResponse = new LoginResponse();

        if (loginServiceImp.checkLogin(username, password)) {
            Users user = userRepository.findByUserName(username);
            String token = jwtUtilsHelper.generateToken(username, user.getRoles().getRoleName());
            tokenBlacklistService.removeTokenFromBlacklist(token);

            loginResponse.setDesc("Đăng nhập thành công");
            loginResponse.setUserId(user.getId());
            loginResponse.setRoleName(user.getRoles().getRoleName());
            loginResponse.setRoleId(user.getRoles().getId());
            loginResponse.setData(token);
            loginResponse.setSuccess(true); // Đặt success là true khi đăng nhập thành công
        } else {
            loginResponse.setDesc("Đăng nhập thất bại");
            loginResponse.setData("");
            loginResponse.setSuccess(false); // Đặt success là false khi đăng nhập thất bại
            loginResponse.setUserId(null); // Đặt userId là null khi đăng nhập thất bại
            loginResponse.setRoleId(null); // Đặt roleId là null khi đăng nhập thất bại
        }
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest){
        ResponseData responseData = new ResponseData();
        responseData.setDesc("Đăng ký thành công");
        responseData.setData(loginServiceImp.addUser(signUpRequest));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            tokenBlacklistService.addTokenToBlacklist(token);
        }
        ResponseData responseData = new ResponseData();
        responseData.setDesc("Đăng xuất thành công");
        responseData.setSuccess(true);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}
