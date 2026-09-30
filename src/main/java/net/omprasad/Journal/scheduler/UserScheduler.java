package net.omprasad.Journal.scheduler;


import lombok.extern.slf4j.Slf4j;
import net.omprasad.Journal.entity.JournalEntry;
import net.omprasad.Journal.entity.User;
import net.omprasad.Journal.model.SentimentData;
import net.omprasad.Journal.repository.UserRepositoryImpl;
import net.omprasad.Journal.service.EmailService;
import net.omprasad.Journal.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
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

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;


    @Scheduled(cron = "0 0 9 ? * SUN") // every sunday 9 am
//    @Scheduled(cron = "0 0/1 * 1/1 * ?") // every minute
//    @Scheduled(cron = "*/5 * * * * *") // every 5 second
//    @Scheduled(fixedRate = 5000) // 5000 milliseconds = 5 seconds
    public void fetchUserAndSendEmail() {
        List<User> userForSA = userRepository.getUserForSA();

        //We will get users with SA and store their jorunal entries created in last 7 days by user,
        // whichever sentiment is most used by user, we'll return that sentiment
        for(User user: userForSA) {
            List<JournalEntry> journalEntries = user.getJournalEntries();

            String sentiment = sentimentAnalysisService.getSentiment(journalEntries);


            if(sentiment==null) continue;

//            emailService.sendEmail(user.getEmail(), "This is your last week mood", sentiment);
            SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment of last week " + sentiment).build();
            kafkaTemplate.send("weekly_sentiments", sentimentData.getEmail(), sentimentData);
        }
    }


}