package com.journal.Journal.scheduler;

import com.journal.Journal.cache.AppCache;
import com.journal.Journal.entity.JournalEntry;
import com.journal.Journal.entity.User;
import com.journal.Journal.repository.UserRespositoryImpl;
import com.journal.Journal.service.EmailService;
import com.journal.Journal.service.SentimentAnalysisService;
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
    private UserRespositoryImpl userRespository;
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;
    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 9 ? * SUN *")
    public void fetchUsersAndSendSAMail() {
        List<User> users = userRespository.getUserForSA();
        for(User user: users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String> filteredEntries = journalEntries.stream().filter(x-> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getContent()).collect(Collectors.toList());
            String entry = String.join("",filteredEntries);
            String sentiment = sentimentAnalysisService.getSentimentAnalysis(entry);
//            Disabled Due to cron
//            emailService.sendMail(user.getEmail(),"Last 7 days sentiment",sentiment);
        }
    }

    @Scheduled(cron = "0 0/10 * 1/1 * ? *")
    public void refreshCache() {
        appCache.init();
    }
}
