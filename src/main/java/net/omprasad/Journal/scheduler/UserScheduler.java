package net.omprasad.Journal.scheduler;


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

@Component
public class UserScheduler {


    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;


//    @Scheduled(cron = "0 0 9 ? * SUN") // every sunday 9 am
    @Scheduled(cron = "0 0/1 * 1/1 * ?") // every minute
    public void fetchUserAndSendEmail() {
        List<User> userForSA = userRepository.getUserForSA();

        //We will get users with SA and store their jorunal entries created in last 7 days by user, and get the expected mood, and send email
        for(User user: userForSA) {
            List<JournalEntry> journalEntries = user.getJournalEntries();

//            List<JournalEntry> filteredEntries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).collect(Collectors.toList());

            StringBuilder sb = new StringBuilder();
            LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
            for(JournalEntry j: journalEntries) {
                if(j.getDate().isAfter(sevenDaysAgo)) {
                    sb.append(j.getContent()).append(" ");
                }
            }
            String sentiment = sentimentAnalysisService.getSentiment(sb.toString());

//            emailService.sendEmail(user.getEmail(), "This is your last weeek mood", sentiment);
            System.out.println("java scheduler working");
        }
    }


}
