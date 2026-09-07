package net.omprasad.Journal.service;

import net.omprasad.Journal.entity.JournalEntry;
import net.omprasad.Journal.enums.Sentiment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SentimentAnalysisService {


    // Return sentiment with max frequency
    public String getSentiment(List<JournalEntry> journalEntries) {
        Map<Sentiment, Integer> map = new HashMap<>();

        Pair pair = new Pair(null, 0);
        for(JournalEntry j: journalEntries) {
            Sentiment st = j.getSentiment();
            map.put(j.getSentiment(), map.getOrDefault(j.getSentiment(), 0) + 1);

            int currCnt = map.get(st);
            if(currCnt>pair.maxCnt) {
                pair.st = st;
                pair.maxCnt = currCnt;
            }
        }

        return pair.st.toString();
    }

    private class Pair {
        Sentiment st;
        int maxCnt;

        Pair(Sentiment st, int maxCnt) {
            this.st = st;
            this.maxCnt = maxCnt;
        }
    }

}
