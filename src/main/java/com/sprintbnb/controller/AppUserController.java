package com.sprintbnb.controller;

import com.sprintbnb.payload.AppUserDto;
import com.sprintbnb.payload.JWTTokenDto;
import com.sprintbnb.payload.LoginDto;
import com.sprintbnb.service.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class AppUserController {

    private final AppUserService appUserService;


    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<AppUserDto> addUser(
            @RequestBody AppUserDto appUserDto
    ) {
        appUserDto.setPassword(BCrypt.hashpw(appUserDto.getPassword(), BCrypt.gensalt(10)));
        AppUserDto audd = appUserService.addUser(appUserDto);
        return new ResponseEntity<>(audd, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<?> signIn(
            @RequestBody LoginDto loginDto
    ) {
        String token = appUserService.verifylogin(loginDto);
        JWTTokenDto jwtTokenDto = new JWTTokenDto();
        if (token != null) {
            jwtTokenDto.setTokenType("JWT");
            jwtTokenDto.setToken(token);
            return new ResponseEntity<>(jwtTokenDto, HttpStatus.OK);

        } else {
            return new ResponseEntity<>("Invalid", HttpStatus.UNAUTHORIZED);
        }
    }


}
