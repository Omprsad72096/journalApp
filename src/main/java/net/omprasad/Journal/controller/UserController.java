package net.omprasad.Journal.controller;

import lombok.extern.slf4j.Slf4j;
import net.omprasad.Journal.entity.User;
import net.omprasad.Journal.repository.UserRepository;
import net.omprasad.Journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PutMapping
    public ResponseEntity<User> updateUserPassword(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User userInDb = userService.findByUserName(userName);
        userInDb.setUserName(!user.getUserName().isEmpty() ? user.getUserName() : userInDb.getUserName());
        userInDb.setPassword(!user.getPassword().isEmpty() ? user.getPassword() : userInDb.getPassword());
        userService.addUser(userInDb);
        return new ResponseEntity<>(userInDb, HttpStatus.NO_CONTENT);
    }

//    @DeleteMapping
//    public ResponseEntity<User> deleteUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        userRepository.deleteUserByUserName(authentication.getName());
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    @DeleteMapping
    public ResponseEntity<User> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userInDB = userService.findByUserName(authentication.getName());
        userService.deleteUser(userInDB);
        return new ResponseEntity<>(userInDB, HttpStatus.NO_CONTENT);
    }

}
