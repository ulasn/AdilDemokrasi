package com.adildemokrasi.adil.Controller;

import com.adildemokrasi.adil.RequestObjects.UserRequestDto;
import com.adildemokrasi.adil.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/signup")
public class SignupController {

    @Autowired
    private UserService userService;

    @RequestMapping(path="/add", method = RequestMethod.POST)
    public @ResponseBody Long addNewUser(@RequestBody UserRequestDto userDTO){
        return userService.addUser(userDTO);
    }
}
