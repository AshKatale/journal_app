package com.journal.Journal.controller;

import com.journal.Journal.entity.JournalEntry;
import com.journal.Journal.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    public JournalEntryService journalEntryService;
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<JournalEntry> all =  journalEntryService.getAllEntries();
        if(all!=null && !all.isEmpty())
        {
            return new ResponseEntity<>(all,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
        myEntry.setDate(LocalDateTime.now());
       journalEntryService.saveEntry(myEntry);
       return new ResponseEntity<JournalEntry>(myEntry,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> changeEntry(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        JournalEntry entry =  journalEntryService.updateEntryById(id,newEntry);
        if(entry==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(entry,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEntryById(@PathVariable ObjectId id) {
        Optional<JournalEntry> entry = journalEntryService.findEntryById(id);
        if(entry.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(entry, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId id){
        journalEntryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
