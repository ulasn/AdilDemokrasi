package com.adildemokrasi.adil.Service;

import com.adildemokrasi.adil.RequestObjects.AnnouncementRequestDTO;
import com.adildemokrasi.adil.Entity.Announcement;
import com.adildemokrasi.adil.Entity.User;
import com.adildemokrasi.adil.Repository.AnnouncementRepository;
import com.adildemokrasi.adil.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AnnouncementService {

    @Autowired
    private UserService userService;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private UserRepository userRepository;

    public void addAnnouncement(AnnouncementRequestDTO announcementRequestDTO){
        Announcement announcement = new Announcement();
        ModelMapper modelMapper = new ModelMapper();
        try{
            modelMapper.map(announcementRequestDTO, announcement);
            Optional<User> creator = userRepository.findByUsername(announcementRequestDTO.getUsername());
            announcement.setCreator(creator.get());
            creator.get().addAnnouncement(announcement);
            announcementRepository.save(announcement);
            userRepository.save(creator.get());
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error in saving announcement to database");
        }

    }
}
