package net.omprasad.Journal.controller;

import net.omprasad.Journal.entity.JournalEntry;
import net.omprasad.Journal.entity.User;
import net.omprasad.Journal.service.JournalEntryService;
import net.omprasad.Journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/journal")
public class JournalEntryController {


    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUserName(userName);
        log.info("User found with username: {}, and id: {}", userName, user.getId());
        List<JournalEntry> allJournalEntries = user.getJournalEntries();
        if(allJournalEntries!=null && !allJournalEntries.isEmpty()) {
            return new ResponseEntity<>(allJournalEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryService.saveNewEntry(myEntry, userName);
            log.info("Post saved successfully");
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }
        catch (Exception e) {
            log.error("Exception: Can't add new post,\nrecived data: {}, \nError: ", myEntry, e);
            throw new RuntimeException("Something gone wrong, ", e);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable String myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        List<JournalEntry> entries = journalEntryService.findByUserName(userName);

        for(JournalEntry j: entries) {
            if(myId.equals(j.getId())) {
                log.info("Found journal entry: {}", j);
                return new ResponseEntity<>(j, HttpStatus.OK);
            }
        }

        log.error("Can't find any entry with reqest id: {}", myId);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> deleteEntryById(@PathVariable String myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        List<JournalEntry> entries = journalEntryService.findByUserName(userName);

        for(JournalEntry j: entries) {
            if(myId.equals(j.getId())) {
                journalEntryService.deleteById(myId, userName);
                log.info("Journal Entry deleted: {}", j);
                return new ResponseEntity<>(j, HttpStatus.OK);
            }
        }

        log.error("Journal entry not found with id: {}, of user: {}", myId, userName);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> updateEntryById(@PathVariable String myId, @RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        List<JournalEntry> entries = journalEntryService.findByUserName(userName);

        log.info("User authenticated, username: {}", userName);
        for(JournalEntry j: entries) {
            if(myId.equals(j.getId())) {
                log.info("Journal entry id matched in list: {}", j);
                JournalEntry oldEntry = journalEntryService.findById(myId).orElse(null);

                oldEntry.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
                oldEntry.setContent(newEntry.getContent()!=null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldEntry.getContent());
                journalEntryService.saveEntry(oldEntry);
                log.info("Journal entry updated and seved: {}", oldEntry);
                return new ResponseEntity<>(oldEntry, HttpStatus.OK);
            }
        }
        log.error("Journal entry not found in list");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
