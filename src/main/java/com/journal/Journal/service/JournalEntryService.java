package com.journal.Journal.service;

import com.journal.Journal.entity.JournalEntry;
import com.journal.Journal.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findEntryById(ObjectId id) {
        return journalEntryRepository.findById(id.toHexString());
    }

    public JournalEntry updateEntryById(ObjectId id, JournalEntry newEntry) {
        JournalEntry old = journalEntryRepository.findById(id.toHexString()).orElse(null);
        if(old != null){
            old.setTitle(newEntry.getTitle().equals(old.getTitle()) ? old.getTitle() : newEntry.getTitle());
            old.setContent(newEntry.getContent().equals(old.getContent()) ? old.getContent() : newEntry.getContent());
            saveEntry(old);
            return old;
        }
        return null;
    }

    public void deleteById(ObjectId id) {
        journalEntryRepository.deleteById(id.toHexString());
    }
}
