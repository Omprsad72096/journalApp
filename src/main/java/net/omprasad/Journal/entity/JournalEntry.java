package net.omprasad.Journal.entity;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journal_entries") // if you don't provide collection name, it will use class name as collection name
@Data
@NoArgsConstructor
public class JournalEntry { // @Document will tell Spring that this JournalEntry class is mapped to mongoDB collection
    // and an instance of this JournalEntry class is equal to a Document inside MongoDB->Data->Collection

    @Id
    private String id; //Mapping it as primary key (Unique for every document)

    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
}