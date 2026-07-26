package net.omprasad.Journal.service;

import org.springframework.stereotype.Service;

@Service
public class SentimentAnalysisService {


    // Think of this method like ML response,
    // we will give it text, and it will return mood based on given text
    // Right now we are just return "you are happy" for every input
    public String getSentiment(String text) {
        return "You are happy";
    }
}
