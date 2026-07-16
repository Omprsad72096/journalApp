package net.omprasad.Journal.entity;


import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
public class User {

    @Id
    private String id;

    @Indexed(unique = true) //For fast search and every username to be unique
    @NonNull //username shouldn't be null by lombok
    private String userName;

    @NonNull // password shouldn't be null, by lombok
    private String password;

    @DBRef // This list will store references of documents in journal_entries collection
    // A parent child relationship is established, and only the id's of entries are embedded
    private List<JournalEntry> journalEntries = new ArrayList<>();

    private List<String> roles;
}