package com.adildemokrasi.adil.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/user")
public class UserController {


    @RequestMapping(path="/demo", method = RequestMethod.GET)
    public @ResponseBody Long deneme()
    {
        return 1L;
    }
}
