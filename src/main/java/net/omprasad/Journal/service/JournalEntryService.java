package net.omprasad.Journal.service;

import lombok.Setter;
import net.omprasad.Journal.entity.JournalEntry;
import net.omprasad.Journal.entity.User;
import net.omprasad.Journal.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;


    @Transactional
    public void saveNewEntry(JournalEntry journalEntry, String userName) {
        User user = userService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
        if(user.getJournalEntries()==null) {
            user.setJournalEntries(new ArrayList<>());
        }
        user.getJournalEntries().add(journalEntry);
        userService.addUserWithoutEncryptingPass(user);
        log.info("Post saved: {}", journalEntry);
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
        log.info("Post updated: {}", journalEntry);
    }

    public List<JournalEntry> getAll() {
        try {
            return journalEntryRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<JournalEntry> findById(String id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public void deleteById(String id, String userName) {
        User user = userService.findByUserName(userName);
        log.info("For deleting journal Entry, user: {} is found", userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.addUserWithoutEncryptingPass(user);
        journalEntryRepository.deleteById(id);
    }

    public List<JournalEntry> findByUserName(String userName) {
        User user = userService.findByUserName(userName);
        return user.getJournalEntries();
    }

}
