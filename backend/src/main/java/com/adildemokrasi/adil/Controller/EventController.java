package com.adildemokrasi.adil.Controller;

import com.adildemokrasi.adil.Dto.CommentDTO;
import com.adildemokrasi.adil.Dto.EventCommentsDTO;
import com.adildemokrasi.adil.Dto.EventUserListDTO;
import com.adildemokrasi.adil.RequestObjects.NewEventUserDTO;
import com.adildemokrasi.adil.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @RequestMapping(path="/get/comments", method = RequestMethod.GET)
    public @ResponseBody
    EventCommentsDTO getEventComments(@RequestParam("eventname") String eventName){
        return eventService.getEventComments(eventName);
    }

    @RequestMapping(path = "/add/comment", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void addCommentToEvent(@RequestBody CommentDTO commentDTO){
        eventService.addComment(commentDTO);
    }

    @RequestMapping(path="/get/users", method = RequestMethod.GET)
    public @ResponseBody
    EventUserListDTO getParticipants(@RequestParam("eventname") String eventName){
        return eventService.getParticipants(eventName);
    }

    @RequestMapping(path="/add/user", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void addUserToEvent (@RequestBody NewEventUserDTO newEventUserDTO){
        eventService.addUserToEvent(newEventUserDTO);
    }
}
