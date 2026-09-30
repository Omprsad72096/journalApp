package net.omprasad.Journal.service;


import lombok.extern.slf4j.Slf4j;
import net.omprasad.Journal.model.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SentimentConsumerService {

    @Autowired
    private EmailService emailService;


    @KafkaListener(topics = "weekly_sentiments", groupId = "weekly_sentiment_group")
    public void consume(SentimentData sentimentData) {
        sendEmail(sentimentData);
        log.info("kafka message received by consumer: {}, Sentiment for previous week {}", sentimentData.getEmail(), sentimentData.getSentiment());
    }

    private void sendEmail(SentimentData sentimentData) {
        emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
    }
}
