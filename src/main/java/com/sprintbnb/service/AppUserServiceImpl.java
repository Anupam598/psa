package com.sprintbnb.service;

import com.sprintbnb.entity.AppUser;
import com.sprintbnb.payload.AppUserDto;
import com.sprintbnb.payload.LoginDto;
import com.sprintbnb.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserServiceImpl  implements  AppUserService{

     private final  AppUserRepository appUserRepository;

     private final JWTService jwtService;

    public AppUserServiceImpl(AppUserRepository appUserRepository, JWTService jwtService) {
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
    }

    @Override
    public AppUserDto addUser(AppUserDto appUserDto) {

        AppUser aur =dtoToEntity(appUserDto);
        AppUser saved = appUserRepository.save(aur);
        AppUserDto audd =  entityToDto(saved);
        return audd;
    }

    @Override
    public String verifylogin(LoginDto loginDto) {
        Optional<AppUser> opUser = appUserRepository.findByUsername(loginDto.getUsername());
        if(opUser.isPresent()){
            AppUser appUser = opUser.get();

          if(BCrypt.checkpw(loginDto.getPassword() , appUser.getPassword())){
            return jwtService.generateToken(appUser);
          }
        }
        return null;
    }


    // Conversion of Dto to Entity

    //Conversion Dto to Entity
    AppUser dtoToEntity(AppUserDto appUserDto)
    {
        AppUser appUser = new AppUser();
        appUser.setName(appUserDto.getName());
        appUser.setEmail(appUserDto.getEmail());
        appUser.setUsername(appUserDto.getUsername());
        appUser.setPassword(appUserDto.getPassword());
        return appUser;
    }

    //Conversion Entity to Dto
    AppUserDto entityToDto(AppUser appUser)
    {
        AppUserDto appUserDto = new AppUserDto();
        appUserDto.setId(appUser.getId());
        appUserDto.setName(appUser.getName());
        appUserDto.setEmail(appUser.getEmail());
        appUserDto.setUsername(appUser.getUsername());
        appUserDto.setPassword(appUser.getPassword());
        return appUserDto;
    }




















}
