package net.omprasad.Journal.controller;

import net.omprasad.Journal.entity.User;
import net.omprasad.Journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> addNewUser(@RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/health-check")
    String healthCheck(){
        return "ok";
    }
}
