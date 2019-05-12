package com.adildemokrasi.adil.Service;

import com.adildemokrasi.adil.Dto.EventRequestDTO;
import com.adildemokrasi.adil.Entity.Event;
import com.adildemokrasi.adil.Entity.User;
import com.adildemokrasi.adil.Repository.AddressRepository;
import com.adildemokrasi.adil.Repository.EventRepository;
import com.adildemokrasi.adil.Repository.UserRepository;
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


    public void addUserToEvent(Event event, User user){
        user.addEvent(event);
        userRepository.save(user);
    }

    public void addEvent(EventRequestDTO eventRequestDTO) throws Exception{
        Event event = new Event();
        ModelMapper mapper = new ModelMapper();

        try{
            mapper.map(eventRequestDTO, event);
            Optional<User> creator = userRepository.findByUsername(eventRequestDTO.getUsername());
            event.setCreator(creator.get());
            event.addUser(creator.get());
            addressRepository.save(eventRequestDTO.getAddress());
            eventRepository.save(event);
            addUserToEvent(event, creator.get());
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error in saving event to database");
        }
    }
}
