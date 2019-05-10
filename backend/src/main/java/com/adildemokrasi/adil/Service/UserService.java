package com.adildemokrasi.adil.Service;

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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


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

    public Long addUser(UserRequestDto userDTO) throws Exception{
        if(checkUserExistByUsername(userDTO.getUsername())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username Already Exists");
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
}
