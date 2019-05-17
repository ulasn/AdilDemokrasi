package com.adildemokrasi.adil.Controller;


import com.adildemokrasi.adil.Dto.NgoProfileDto;
import com.adildemokrasi.adil.RequestObjects.AnnouncementRequestDTO;
import com.adildemokrasi.adil.RequestObjects.EventRequestDTO;
import com.adildemokrasi.adil.RequestObjects.NgoRequestDto;
import com.adildemokrasi.adil.Service.AnnouncementService;
import com.adildemokrasi.adil.Service.EventService;
import com.adildemokrasi.adil.Service.NgoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/ngo")
public class NgoController {

    @Autowired
    private NgoService ngoService;

    @Autowired
    private EventService eventService;

    @Autowired
    private AnnouncementService announcementService;

    @RequestMapping(path="/add", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody void addNewNgo(@RequestBody NgoRequestDto ngoRequestDto) throws Exception{
        ngoService.addNgo(ngoRequestDto);
    }

    @RequestMapping(path="/settings", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody void saveSettings(@RequestBody NgoRequestDto ngoDto){
        ngoService.saveNgoSettings(ngoDto);
    }

    @RequestMapping(path="/new/event", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody void newEvent(@RequestBody EventRequestDTO eventRequestDTO) throws Exception{
        eventService.addGroupEvent(eventRequestDTO);
    }


    @RequestMapping(path="/new/announcement", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody void newAnnouncement(@RequestBody AnnouncementRequestDTO announcementRequestDTO) throws Exception{
        announcementService.addGroupAnnouncement(announcementRequestDTO);
    }


    @RequestMapping(path="/get/profile", method = RequestMethod.GET)
    public @ResponseBody NgoProfileDto getUserProfileData(@RequestParam(name = "name") String name) throws Exception{
        return ngoService.getNgoProfileData(name);
    }
}
