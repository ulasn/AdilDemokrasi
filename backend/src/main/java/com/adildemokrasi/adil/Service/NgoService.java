package com.adildemokrasi.adil.Service;

import com.adildemokrasi.adil.Entity.NGO;
import com.adildemokrasi.adil.Entity.User;
import com.adildemokrasi.adil.Repository.NgoRepository;
import com.adildemokrasi.adil.Repository.UserRepository;
import com.adildemokrasi.adil.RequestObjects.NgoRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class NgoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NgoRepository ngoRepository;

    public void addNgo(NgoRequestDto ngoRequestDto) {
        if(ngoRepository.findNgoByName(ngoRequestDto.getName()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Name already exists");
        }

        NGO ngo = new NGO();
        ngo.setName(ngoRequestDto.getName());
        ngo.setDescription(ngoRequestDto.getDescription());

        User user = userRepository.findByUsername(ngoRequestDto.getAdmin()).get();
        ngo.addAdmin(user);
        try{
            ngoRepository.save(ngo);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving to database");
        }
    }
}
