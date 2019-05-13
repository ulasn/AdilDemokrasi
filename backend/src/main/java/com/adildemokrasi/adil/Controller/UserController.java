package com.adildemokrasi.adil.Controller;

import com.adildemokrasi.adil.Dto.UserProfileDTO;
import com.adildemokrasi.adil.RequestObjects.AnnouncementRequestDTO;
import com.adildemokrasi.adil.RequestObjects.EventRequestDTO;
import com.adildemokrasi.adil.Service.AnnouncementService;
import com.adildemokrasi.adil.Service.EventService;
import com.adildemokrasi.adil.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/user")
public class UserController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private AnnouncementService announcementService;

    @RequestMapping(path="/demo", method = RequestMethod.GET)
    public @ResponseBody Long deneme()
    {
        return 1L;
    }


    @RequestMapping(path="/new/event", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody void newEvent(@RequestBody EventRequestDTO eventRequestDTO) throws Exception{
            eventService.addEvent(eventRequestDTO);
    }


    @RequestMapping(path="/new/announcement", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody void newAnnouncement(@RequestBody AnnouncementRequestDTO announcementRequestDTO) throws Exception{
        announcementService.addAnnouncement(announcementRequestDTO);
    }

    @RequestMapping(path="/get/profile", method = RequestMethod.GET)
    public @ResponseBody UserProfileDTO getUserProfileData() throws Exception{
        return userService.getUserProfileData();
    }

}
