package com.airbnb2.controller;

import com.airbnb2.dto.LoginDto;
import com.airbnb2.dto.SignUpDto;
import com.airbnb2.dto.TokenInJsonForm;
import com.airbnb2.entity.PropertyUserEntity;
import com.airbnb2.service.PropertyUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/users")
public class PropertyUserController {

    private PropertyUserService userService;

    public PropertyUserController(PropertyUserService userService) {
        this.userService = userService;
    }

    // http://localhost:8080/api/v2/users/sign-up
    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody SignUpDto dto){
        userService.addUser(dto);

        return new ResponseEntity<>("Registration Successful" , HttpStatus.CREATED);
    }

    // http://localhost:8080/api/v2/users/sign-in
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody LoginDto loginDto){
        String token = userService.signIn(loginDto);
        if(token != null) {
            TokenInJsonForm jsonForm = new TokenInJsonForm();
            jsonForm.setToken(token);
            return new ResponseEntity<>(jsonForm , HttpStatus.OK);
        }
        return new ResponseEntity<>("bad credential" , HttpStatus.UNAUTHORIZED);
    }

    // http://localhost:8080/api/v2/users/session
    @PostMapping("/session")
    public ResponseEntity<String> sessionCreation(){
        return new ResponseEntity<>("session Created" , HttpStatus.OK);
    }

    // http://localhost:8080/api/v2/users/profile
    @GetMapping("/profile")
    public PropertyUserEntity getProfile(@AuthenticationPrincipal PropertyUserEntity user){

        return user;
    }
}
