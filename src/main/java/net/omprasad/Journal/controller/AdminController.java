package net.omprasad.Journal.controller;


import lombok.extern.slf4j.Slf4j;
import net.omprasad.Journal.entity.User;
import net.omprasad.Journal.repository.UserRepository;
import net.omprasad.Journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        log.error("Returning the data");
        log.info("Returning the data");
        log.debug("Returning the data");
        log.trace("Returning the data");
        log.warn("Returning the data");
        List<User> allUsers = userService.getAll();

        log.info("Got the list of users, \nUsers: {}", allUsers);

        if(allUsers!=null) {
            log.debug("Returning the data");
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }

        log.warn("No user found");

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<User> createAdmin(@RequestBody User user) {
        User userInDB = userRepository.findByUserName(user.getUserName());

        if(userInDB!=null) {
            log.info("User already exists, only updating role, user: {}", userInDB);
            User temp = userService.addAdminRoleInUser(userInDB);
            return new ResponseEntity<>(temp, HttpStatus.OK);
        }

        User temp = userService.addNewAdminUser(user);
        return new ResponseEntity<>(temp, HttpStatus.CREATED);
    }
}
