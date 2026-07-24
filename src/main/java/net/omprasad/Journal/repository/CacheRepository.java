package net.omprasad.Journal.repository;

import net.omprasad.Journal.entity.CacheEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CacheRepository extends MongoRepository<CacheEntity, String> {

}
