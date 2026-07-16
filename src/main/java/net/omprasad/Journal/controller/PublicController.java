package net.omprasad.Journal.controller;

import net.omprasad.Journal.entity.User;
import net.omprasad.Journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User addNewUser(@RequestBody User user) {
        return userService.addUser(user);
    }
}
