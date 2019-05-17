package com.adildemokrasi.adil.Service;

import com.adildemokrasi.adil.Entity.Event;
import com.adildemokrasi.adil.Entity.NGO;
import com.adildemokrasi.adil.Entity.User;
import com.adildemokrasi.adil.Repository.AddressRepository;
import com.adildemokrasi.adil.Repository.EventRepository;
import com.adildemokrasi.adil.Repository.NgoRepository;
import com.adildemokrasi.adil.Repository.UserRepository;
import com.adildemokrasi.adil.RequestObjects.EventRequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
}
