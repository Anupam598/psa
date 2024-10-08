package com.sprintbnb.service;

import com.sprintbnb.payload.AppUserDto;
import com.sprintbnb.payload.LoginDto;

public interface AppUserService {
    AppUserDto addUser(AppUserDto appUserDto);

    String verifylogin(LoginDto loginDto);
}
