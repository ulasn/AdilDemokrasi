package com.adildemokrasi.adil.Service;

import com.adildemokrasi.adil.Entity.Announcement;
import com.adildemokrasi.adil.Entity.NGO;
import com.adildemokrasi.adil.Entity.User;
import com.adildemokrasi.adil.Repository.AnnouncementRepository;
import com.adildemokrasi.adil.Repository.NgoRepository;
import com.adildemokrasi.adil.Repository.UserRepository;
import com.adildemokrasi.adil.RequestObjects.AnnouncementRequestDTO;
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

    @Autowired
    private NgoRepository ngoRepository;

    public Announcement saveAnnouncement(Announcement announcement){
        User creator = userService.getCurrentUser();
        announcement.setCreator(creator);
        creator.addAnnouncement(announcement);
        announcementRepository.save(announcement);
        userRepository.save(creator);
        return announcement;
    }

    public void addAnnouncement(AnnouncementRequestDTO announcementRequestDTO){
        Announcement announcement = new Announcement();
        ModelMapper modelMapper = new ModelMapper();
        try{
            modelMapper.map(announcementRequestDTO, announcement);
            saveAnnouncement(announcement);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error in saving announcement to database");
        }

    }

    public void addGroupAnnouncement(AnnouncementRequestDTO announcementRequestDTO) {
        Announcement announcement = new Announcement();
        ModelMapper modelMapper = new ModelMapper();

        Optional<NGO> ngo = ngoRepository.findNgoByName(announcementRequestDTO.getGroup());

        if(!ngo.get().getAdminList().contains(userService.getCurrentUser())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only admin can create announcement");
        }

        try{
            modelMapper.map(announcementRequestDTO, announcement);
            announcement.setNgo(ngo.get());
            ngo.get().addAnnouncement(saveAnnouncement(announcement));
            ngoRepository.save(ngo.get());
        }

        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error in saving announcement to database");
        }
    }
}
