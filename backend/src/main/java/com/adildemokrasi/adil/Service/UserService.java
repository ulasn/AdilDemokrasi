package com.adildemokrasi.adil.Service;

import com.adildemokrasi.adil.Dto.*;
import com.adildemokrasi.adil.Entity.Announcement;
import com.adildemokrasi.adil.Entity.Event;
import com.adildemokrasi.adil.Entity.Role;
import com.adildemokrasi.adil.Entity.User;
import com.adildemokrasi.adil.Enum.Status;
import com.adildemokrasi.adil.Repository.UserRepository;
import com.adildemokrasi.adil.RequestObjects.UserRequestDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserDetailsService myUserDetailsService;

    @Autowired
    private TokenEndpoint tokenEndpoint;


    public Long save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return 1L;
    }

    public boolean checkUserExistByUsername(String username){
        Optional <User> user = userRepository.findByUsername(username);
        return user.isPresent();
    }

    public boolean checkEmailExist(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            return true;
        }
        else{
            return false;
        }

    }

    public Long addUser(UserRequestDto userDTO) throws Exception{
        if(checkUserExistByUsername(userDTO.getUsername())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username Already Exists");
        }
        if(checkEmailExist(userDTO.getEmail())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email Already Exists");
        }
        else {
            User user = new User();
            ModelMapper mapper = new ModelMapper();
            mapper.map(userDTO, user);
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            Set<Role> roleList = new HashSet<>();
            roleList.add(roleService.getRole("USER"));
            user.setRoles(roleList);
            user.setActive(true);
            try{
                userRepository.save(user);
                return Status.SUCCESS.getStatus();
            }
            catch (Exception e){
                return Status.ERROR.getStatus();
            }
        }
    }


    public boolean userPasswordCheck(String password, String inputPassword){
        return passwordEncoder.matches(password, inputPassword);
    }


    public ResponseEntity<?> loginUser(UserRequestDto userDTO)
            throws BadCredentialsException, HttpRequestMethodNotSupportedException {
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(userDTO.getUsername());

        if (!userPasswordCheck(userDTO.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username/password");
        }

        String clientId = "public-client";
        String clientSecret = passwordEncoder.encode("secret");

        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("client_id", clientId);
        parameters.put("client_secret", clientSecret);
        parameters.put("grant_type", "password");
        parameters.put("username", userDTO.getUsername());
        parameters.put("password", userDTO.getPassword());

        org.springframework.security.core.userdetails.User clientDetails =
                new org.springframework.security.core.userdetails.User(clientId, clientSecret, true, true, true, true, userDetails.getAuthorities());


        UsernamePasswordAuthenticationToken principal= new UsernamePasswordAuthenticationToken
                (clientDetails, clientSecret, userDetails.getAuthorities());

        try {
            ResponseEntity<OAuth2AccessToken> accessToken = tokenEndpoint.postAccessToken(principal, parameters);
            return accessToken;

        } catch (HttpRequestMethodNotSupportedException exception) {
            throw exception;
        }

    }

    public User getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userRepository.findByUsername(username).get();
    }

    public UserProfileDTO fillUserProfileDTO(User user){
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        ModelMapper mapper = new ModelMapper();
        mapper.map(user, userProfileDTO);

        Integer index = 0;
        List<Announcement> announcementList = user.getAnnouncements();
        for(AnnouncementDTO announcement : userProfileDTO.getAnnouncements()){
            announcement.setUsername(announcementList.get(index).getCreator().getUsername());
            index++;
        }

        index = 0;
        List<Event> eventList = user.getEvents();
        for(EventDTO event : userProfileDTO.getEvents()){
            event.setCreator(eventList.get(index).getCreator().getUsername());
            index++;
        }

        if(user.getEvents().size() == 0){
            userProfileDTO.setEventCount(0);
        }
        else{
            userProfileDTO.setEventCount(user.getEvents().size());
        }

        if(user.getAnnouncements().size() == 0){
            userProfileDTO.setAnnouncementCount(0);
        }
        else{
            userProfileDTO.setAnnouncementCount(user.getAnnouncements().size());
        }

        if(user.getComments().size() == 0){
            userProfileDTO.setCommentCount(0);
        }
        else{
            userProfileDTO.setCommentCount(user.getComments().size());
        }
        return userProfileDTO;
    }

    public UserProfileDTO getUserProfileData(String username) throws Exception{
        User user;
        if(username == ""){
            user = getCurrentUser();
        }
        else{
            try{
                user = userRepository.findByUsername(username).get();
            }
            catch(Exception e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No User Exists with that username");
            }

        }

        UserProfileDTO userProfileDTO = fillUserProfileDTO(user);
        return userProfileDTO;
    }

    public SearchResultDTO getSearchResults(String searchQuery){
        List<User> users = userRepository.findByNameIgnoreCaseStartingWithOrSurnameIgnoreCaseStartingWithOrUsernameIgnoreCaseStartingWith(searchQuery,searchQuery,searchQuery);
        int index = 0;
        SearchResultDTO searchResultDTO = new SearchResultDTO();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(User user : users){
            UserDTO userDTO = new UserDTO();
            userDTO.setName(user.getName());
            userDTO.setSurname(user.getSurname());
            userDTO.setUsername(user.getUsername());
            userDTOList.add(userDTO);
            index++;
            if(index == 5)
                break;
        }
        searchResultDTO.setFilteredUserList(userDTOList);
        return searchResultDTO;
    }

    public void saveUserSettings(UserProfileDTO userProfileDTO) {
        User user = getCurrentUser();
        user.setName(userProfileDTO.getName());
        user.setSurname(userProfileDTO.getSurname());
        user.setAboutMe(userProfileDTO.getAboutMe());
        user.setTwitter(userProfileDTO.getTwitter());
        user.setInstagram(userProfileDTO.getInstagram());
        user.setJob(userProfileDTO.getJob());
        userRepository.save(user);
    }
}
