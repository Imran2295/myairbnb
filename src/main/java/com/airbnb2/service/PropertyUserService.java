package com.airbnb2.service;

import com.airbnb2.dto.LoginDto;
import com.airbnb2.dto.SignUpDto;
import com.airbnb2.entity.PropertyUserEntity;
import com.airbnb2.repository.PropertyUserRepository;
import com.airbnb2.security.JwtService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyUserService {

    private PropertyUserRepository userRepository;
    private JwtService jwtService;

    public PropertyUserService(PropertyUserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }


    public void addUser(SignUpDto dto) {

        PropertyUserEntity userEntity = new PropertyUserEntity();
        userEntity.setFirstName(dto.getFirstName());
        userEntity.setLastName(dto.getLastName());
        userEntity.setUsername(dto.getUsername());
        userEntity.setPassword(BCrypt.hashpw(dto.getPassword() , BCrypt.gensalt(10)));
        userEntity.setEmail(dto.getEmail());
        userEntity.setRole(dto.getRole());

        userRepository.save(userEntity);
    }

    public String signIn(LoginDto loginDto) {
        Optional<PropertyUserEntity> byUsername = userRepository.findByUsername(loginDto.getUsername());
        if(byUsername.isPresent()){
            PropertyUserEntity user = byUsername.get();
            if(BCrypt.checkpw(loginDto.getPassword(), user.getPassword())){
                String token = jwtService.generateJwtToken(user);
                return token;
            }
        }
        return null;
    }
}
