package net.omprasad.Journal.service;

import net.omprasad.Journal.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.security.cert.Extension;
import java.util.stream.Stream;

public class UserArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of(User.builder().userName("om").password("om").build()),
                Arguments.of(User.builder().userName("test").password("test").build()),
                Arguments.of(User.builder().userName("admin").password("admin").build())
        );
    }
}
