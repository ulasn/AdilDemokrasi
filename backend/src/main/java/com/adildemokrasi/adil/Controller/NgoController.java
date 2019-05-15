package com.adildemokrasi.adil.Controller;


import com.adildemokrasi.adil.RequestObjects.NgoRequestDto;
import com.adildemokrasi.adil.Service.NgoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/ngo")
public class NgoController {

    @Autowired
    private NgoService ngoService;

    @RequestMapping(path="/add", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody void addNewNgo(@RequestBody NgoRequestDto ngoRequestDto) throws Exception{
        ngoService.addNgo(ngoRequestDto);
    }
}
