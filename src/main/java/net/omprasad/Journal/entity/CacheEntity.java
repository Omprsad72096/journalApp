package net.omprasad.Journal.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cache_journal_app")
@Data
public class CacheEntity {

    private String key;
    private String value;
}