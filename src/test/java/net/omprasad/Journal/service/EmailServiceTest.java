package net.omprasad.Journal.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {


    @Autowired
    private EmailService emailService;

    @Test
    void testSendEmail() {
        emailService.sendEmail(
                "omprasad72096@gmail.com",
                "Testing java mail sender with SMTP",
                "Hi bro, is it working fine?"
        );
    }
}
