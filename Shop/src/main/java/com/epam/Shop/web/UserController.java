package com.epam.Shop.web;

import com.epam.Shop.entities.Cars;
import com.epam.Shop.entities.Users;
import com.epam.Shop.service.CarService;
import com.epam.Shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<Users>> getUsers(){
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/createUser")
    public ResponseEntity<Void> createUser(@RequestBody Users user){
        userService.createUser(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deleteUser{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id){
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
