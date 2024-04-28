package com.airbnb2.controller;

import com.airbnb2.entity.FavoriteProperty;
import com.airbnb2.entity.Property;
import com.airbnb2.entity.PropertyUserEntity;
import com.airbnb2.service.FavoritePropertyService;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/favorite")
public class FavoritePropertyController {

    private FavoritePropertyService favoritePropertyService;

    public FavoritePropertyController(FavoritePropertyService favoritePropertyService) {
        this.favoritePropertyService = favoritePropertyService;
    }

    // http://localhost:8080/api/v2/favorite/add-favorite/
    @PostMapping("/add-favorite/{propertyId}")
    public ResponseEntity<?> addFavorite(@AuthenticationPrincipal PropertyUserEntity user ,
        @PathVariable Long propertyId){

        FavoriteProperty favoriteProperty = favoritePropertyService.addFavoriteProperty(user, propertyId);
        if(favoriteProperty.getIsFavorite()) {
            return new ResponseEntity<>(favoriteProperty, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Already a favorite property",HttpStatus.BAD_REQUEST);
    }

    // http://localhost:8080/api/v2/favorite/remove-favorite/
    @PostMapping("/remove-favorite/{property}")
    public ResponseEntity<String> removeFavorite(@AuthenticationPrincipal PropertyUserEntity user ,
                                                 @PathVariable Property property){
        boolean status = favoritePropertyService.removeFavorite(user , property);
        if(status){
            return new ResponseEntity<>("property removed from favorite" , HttpStatus.OK);
        }
        return new ResponseEntity<>("Already a non favorite property" , HttpStatus.BAD_REQUEST);
    }

    // http://localhost:8080/api/v2/favorite/delete-favorite/1
    @PostMapping("/delete-favorite/{propertyId}")
    public ResponseEntity<String> deleteFavorite(@AuthenticationPrincipal PropertyUserEntity user ,
                                                 @PathVariable Long propertyId){
        Boolean deleted = favoritePropertyService.deleteFavorite(user, propertyId);
        if(deleted) {
            return new ResponseEntity<>("favorite is deleted", HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body("Not a Favorite Property");
    }


    // http://localhost:8080/api/v2/favorite/all-favorite
    @GetMapping("/all-favorite")
    public ResponseEntity<List<FavoriteProperty>> getAllFavoritePropertiesOfUser(@AuthenticationPrincipal PropertyUserEntity user){

        List<FavoriteProperty> allFavorite = favoritePropertyService.findAllFavorite(user);

        return new ResponseEntity<>(allFavorite , HttpStatus.OK);
    }

}
