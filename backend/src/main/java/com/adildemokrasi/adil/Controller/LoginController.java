package com.adildemokrasi.adil.Controller;

import com.adildemokrasi.adil.RequestObjects.UserRequestDto;
import com.adildemokrasi.adil.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(path="/user", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> loginUser(@RequestBody UserRequestDto userDTO) throws Exception{
        return userService.loginUser(userDTO);
    }
}
