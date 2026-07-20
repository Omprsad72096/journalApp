package net.omprasad.Journal.service;

import net.omprasad.Journal.entity.User;
import net.omprasad.Journal.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.*;

@Disabled
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @ParameterizedTest
    @ValueSource(strings =  {"om","test","admin"})
    public void testFindByUserName(String userName) {
        assertNotNull("msg", userRepository.findByUserName(userName));
    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveUser(User user) {
        assertTrue("msg", userService.addUser(user));
    }


    @Disabled
    @Test
    public void testIfJournalEntryListEmpty() {
        User user = userRepository.findByUserName("admin");
        assertTrue("msg",!user.getUserName().isEmpty());
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
        "'msg', 4, 2, 2",
        "'msg', 4, 2, 1",
        "'msg', 5, 2, 3",
    })
    public void testAddition(String msg, int expected, int a, int b) {
        assertEquals(msg, expected, a+b);
    }
}
