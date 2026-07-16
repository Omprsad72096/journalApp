package net.omprasad.Journal.service;

import lombok.extern.slf4j.Slf4j;
import net.omprasad.Journal.entity.JournalEntry;
import net.omprasad.Journal.entity.User;
import net.omprasad.Journal.repository.JournalEntryRepository;
import net.omprasad.Journal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        return userRepository.save(user);
    }

    public User addUserWithoutEncryptingPass(User user) {
        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void deleteUser(User user) {
        for(JournalEntry j: user.getJournalEntries()) {
            journalEntryRepository.deleteById(j.getId());
        }
        userRepository.deleteById(user.getId());
    }


    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
