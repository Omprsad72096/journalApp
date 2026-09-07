package net.omprasad.Journal.scheduler;


import lombok.extern.slf4j.Slf4j;
import net.omprasad.Journal.entity.JournalEntry;
import net.omprasad.Journal.entity.User;
import net.omprasad.Journal.repository.UserRepositoryImpl;
import net.omprasad.Journal.service.EmailService;
import net.omprasad.Journal.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserScheduler {


    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;


    @Scheduled(cron = "0 0 9 ? * SUN") // every sunday 9 am
//    @Scheduled(cron = "0 0/1 * 1/1 * ?") // every minute
    public void fetchUserAndSendEmail() {
        List<User> userForSA = userRepository.getUserForSA();

        //We will get users with SA and store their jorunal entries created in last 7 days by user,
        // whichever sentiment is most used by user, we'll return that sentiment
        for(User user: userForSA) {
            List<JournalEntry> journalEntries = user.getJournalEntries();

            String sentiment = sentimentAnalysisService.getSentiment(journalEntries);

//            emailService.sendEmail(user.getEmail(), "This is your last weeek mood", sentiment);
            log.info("Yoyo Sentiment of user: {}, is: {}", user.getUserName(), sentiment);
        }
    }


}
