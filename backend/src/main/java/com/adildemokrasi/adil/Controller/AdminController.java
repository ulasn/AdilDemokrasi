package com.adildemokrasi.adil.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private")
public class AdminController {

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("/demo")
    public String demoAdmin(){
        return "This is the admin area";
    }
}
