package com.adildemokrasi.adil.Service;

import com.adildemokrasi.adil.Dto.*;
import com.adildemokrasi.adil.Entity.Announcement;
import com.adildemokrasi.adil.Entity.Event;
import com.adildemokrasi.adil.Entity.NGO;
import com.adildemokrasi.adil.Entity.User;
import com.adildemokrasi.adil.Repository.NgoRepository;
import com.adildemokrasi.adil.Repository.UserRepository;
import com.adildemokrasi.adil.RequestObjects.NgoRequestDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@SuppressWarnings("Duplicates")
@Service
public class NgoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NgoRepository ngoRepository;

    @Autowired
    private UserService userService;

    public void addNgo(NgoRequestDto ngoRequestDto) {
        if(ngoRepository.findNgoByName(ngoRequestDto.getName()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Name already exists");
        }

        NGO ngo = new NGO();

        ModelMapper mapper = new ModelMapper();
        mapper.map(ngoRequestDto, ngo);

        User user = userRepository.findByUsername(ngoRequestDto.getAdmin()).get();
        ngo.addAdmin(user);

        try{
            ngoRepository.save(ngo);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving to database");
        }
    }


    public NgoProfileDto getNgoProfileData(String NgoName){
        User user = userService.getCurrentUser();
        Optional<NGO> ngo = ngoRepository.findNgoByName(NgoName);
        if(!ngo.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No NGO with that name");
        }
        NgoProfileDto ngoProfileDto = new NgoProfileDto();
        Set<User> adminList = ngo.get().getAdminList();
        ngoProfileDto.setAdmin(false);
        for(User admin : adminList){
            if(user.getUsername() == admin.getUsername()){
                ngoProfileDto.setAdmin(true);
            }
        }


        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<NGO,NgoProfileDto >() {
            @Override
            protected void configure() {
                skip(destination.getAdmin());
            }
        });
        mapper.map(ngo.get(), ngoProfileDto);


        Integer index = 0;
        List<Announcement> announcementList = ngo.get().getAnnouncements();
        for(AnnouncementDTO announcement : ngoProfileDto.getAnnouncements()){
            announcement.setUsername(announcementList.get(index).getCreator().getUsername());
            index++;
        }

        index = 0;
        List<Event> eventList = ngo.get().getEvents();
        for(EventDTO event : ngoProfileDto.getEvents()){
            event.setCreator(eventList.get(index).getCreator().getUsername());
            index++;
        }

        return ngoProfileDto;
    }


    public void saveNgoSettings(NgoRequestDto ngoRequestDto) {
        Optional <NGO> ngoOptional = ngoRepository.findNgoByName(ngoRequestDto.getName());
        User user = userService.getCurrentUser();
        if(!ngoOptional.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No NGO found with that name");
        }
        NGO ngo = ngoOptional.get();
        if(!ngo.getAdminList().contains(user)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized, only admin can save.");
        }
        ngo.setName(ngoRequestDto.getName());
        ngo.setDescription(ngoRequestDto.getDescription());
        ngo.setInstagram(ngoRequestDto.getInstagram());
        ngo.setTwitter(ngoRequestDto.getTwitter());
        ngo.setTag(ngoRequestDto.getTag());

        if(ngoRequestDto.getAdmin() != ""){
            Optional <User> newAdmin = userRepository.findByUsername(ngoRequestDto.getAdmin());
            if(newAdmin.isPresent()){
                ngo.addAdmin(newAdmin.get());
            }
        }

        ngoRepository.save(ngo);
    }

    public ModelDTO getModelData() {
        List<NGO> ngoList = ngoRepository.findAll();
        ModelDTO modelDTO = new ModelDTO();
        for(NGO group : ngoList){
            for(Event event : group.getEvents()){
                NewsItemDTO newsItemDTO = new NewsItemDTO();
                newsItemDTO.setContent(event.getContent());
                newsItemDTO.setDate(event.getDate().substring(0, 10));
                newsItemDTO.setEventLocation(event.getAddress().getRoute());
                newsItemDTO.setEventTime(event.getDate().substring(11,16));
                newsItemDTO.setTitle(event.getTitle());
                newsItemDTO.setTag(group.getTag());
                newsItemDTO.setType("event");
                newsItemDTO.setGroupName(group.getName());

                if(newsItemDTO.getTag().equals("çevre")){
                    modelDTO.addEnviromentNews(newsItemDTO);
                }
                else if(newsItemDTO.getTag().equals("eğitim")){
                    modelDTO.addEducationNews(newsItemDTO);
                }
                else if(newsItemDTO.getTag().equals("kültür")){
                    modelDTO.addCultureNews(newsItemDTO);
                }
            }

            for(Announcement announcement : group.getAnnouncements()){
                NewsItemDTO newsItemDTO = new NewsItemDTO();
                newsItemDTO.setContent(announcement.getPost());
                newsItemDTO.setDate(announcement.getCreatedAt().toString());
                newsItemDTO.setEventTime(announcement.getCreatedAt().toString());
                newsItemDTO.setTitle(announcement.getTitle());
                newsItemDTO.setTag(group.getTag());
                newsItemDTO.setType("announcement");
                newsItemDTO.setGroupName(group.getName());

                if(newsItemDTO.getTag().equals("çevre")){
                    modelDTO.addEnviromentNews(newsItemDTO);
                }
                else if(newsItemDTO.getTag().equals("eğitim")){
                    modelDTO.addEducationNews(newsItemDTO);
                }
                else if(newsItemDTO.getTag().equals("kültür")){
                    modelDTO.addCultureNews(newsItemDTO);
                }
            }
        }
        return modelDTO;
    }
}
