package com.adildemokrasi.adil.Controller;

import com.adildemokrasi.adil.Dto.EventRequestDTO;
import com.adildemokrasi.adil.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/user")
public class UserController {

    @Autowired
    private EventService eventService;

    @RequestMapping(path="/demo", method = RequestMethod.GET)
    public @ResponseBody Long deneme()
    {
        return 1L;
    }


    @RequestMapping(path="/newevent", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody void newEvent(@RequestBody EventRequestDTO eventRequestDTO) throws Exception{
            eventService.addEvent(eventRequestDTO);
    }

}
