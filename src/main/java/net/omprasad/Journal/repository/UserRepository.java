package net.omprasad.Journal.repository;

import net.omprasad.Journal.entity.JournalEntry;
import net.omprasad.Journal.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUserName(String userName);

    void deleteUserByUserName(String userName);
}
