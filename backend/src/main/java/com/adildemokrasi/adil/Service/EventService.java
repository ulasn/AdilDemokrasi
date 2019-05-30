package com.adildemokrasi.adil.Service;

import com.adildemokrasi.adil.Dto.CommentDTO;
import com.adildemokrasi.adil.Dto.EventCommentsDTO;
import com.adildemokrasi.adil.Dto.EventUserListDTO;
import com.adildemokrasi.adil.Entity.Comment;
import com.adildemokrasi.adil.Entity.Event;
import com.adildemokrasi.adil.Entity.NGO;
import com.adildemokrasi.adil.Entity.User;
import com.adildemokrasi.adil.Repository.*;
import com.adildemokrasi.adil.RequestObjects.EventRequestDTO;
import com.adildemokrasi.adil.RequestObjects.NewEventUserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private NgoRepository ngoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentRepository commentRepository;


    public void addEventToUser(Event event, User user){
        user.addEvent(event);
        userRepository.save(user);
    }

    public Event saveEvent(Event event, EventRequestDTO eventRequestDTO){
        User creator = userService.getCurrentUser();
        event.setCreator(creator);
        event.addUser(creator);
        addressRepository.save(eventRequestDTO.getAddress());
        eventRepository.save(event);
        addEventToUser(event, creator);
        return event;
    }

    public void addEvent(EventRequestDTO eventRequestDTO) throws Exception{
        Event event = new Event();
        ModelMapper mapper = new ModelMapper();

        try{
            mapper.map(eventRequestDTO, event);
            saveEvent(event, eventRequestDTO);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error in saving event to database");
        }
    }

    public void addGroupEvent(EventRequestDTO eventRequestDTO) {
        Event event = new Event();
        ModelMapper mapper = new ModelMapper();

        Optional<NGO> ngo = ngoRepository.findNgoByName(eventRequestDTO.getGroup());
        if(!ngo.isPresent()){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error: no group found with that name.");
        }

        if(!ngo.get().getAdminList().contains(userService.getCurrentUser())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only admin can create event");
        }

        try{
            mapper.map(eventRequestDTO, event);
            event.setNgo(ngo.get());
            ngo.get().addEvent(saveEvent(event, eventRequestDTO));
            ngoRepository.save(ngo.get());
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown Error");
        }
    }

    public EventCommentsDTO getEventComments(String eventName) {
        Optional<Event> optEvent = eventRepository.findByTitle(eventName);
        Event event = optEvent.get();
        EventCommentsDTO eventCommentsDTO = new EventCommentsDTO();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(event, eventCommentsDTO);
        int length = event.getCommentList().size();
        List<Comment> commentList = event.getCommentList();
        for(int i = 0; i < length; i++){
            eventCommentsDTO.getCommentList().get(i).setUsername(event.getCommentList().get(i).getUser().getUsername());
            eventCommentsDTO.getCommentList().get(i).setEvent(eventName);
        }
        return eventCommentsDTO;
    }

    public void addComment(CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setComment(commentDTO.getComment());
        Optional<User> user = userRepository.findByUsername(commentDTO.getUsername());
        comment.setUser(user.get());
        comment.setDate(commentDTO.getDate());
        user.get().addComment(comment);
        Optional<Event> event = eventRepository.findByTitle(commentDTO.getEvent());
        event.get().addComment(comment);
        comment.setEvent(event.get());
        commentRepository.save(comment);
    }

    public EventUserListDTO getParticipants(String eventName) {
        Optional <Event> eventOptional = eventRepository.findByTitle(eventName);
        Event event = eventOptional.get();
        ModelMapper mapper = new ModelMapper();
        EventUserListDTO eventUserListDTO = new EventUserListDTO();
        mapper.map(event, eventUserListDTO);
        return eventUserListDTO;
    }

    public void addUserToEvent(NewEventUserDTO eventUserDTO) {
        Optional<Event> eventOptional = eventRepository.findByTitle(eventUserDTO.getEvent());
        Event event = eventOptional.get();
        Optional<User> userOptional = userRepository.findByUsername(eventUserDTO.getUsername());
        event.addUser(userOptional.get());
        userOptional.get().addEvent(event);
        userRepository.save(userOptional.get());
    }
}
