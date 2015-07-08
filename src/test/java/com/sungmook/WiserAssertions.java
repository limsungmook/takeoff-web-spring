package com.sungmook;

import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import javax.mail.internet.MimeMessage;
import java.text.MessageFormat;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * http://blog.codeleak.pl/2014/09/testing-mail-code-in-spring-boot.html
 */
public class WiserAssertions {

    private final List<WiserMessage> messages;

    public static WiserAssertions assertReceivedMessage(Wiser wiser) {
        return new WiserAssertions(wiser.getMessages());
    }

    private WiserAssertions(List<WiserMessage> messages) {
        this.messages = messages;
    }

    public WiserAssertions from(final String from) {
        findFirstOrElseThrow(new Predicate<WiserMessage>() {
            @Override
            public boolean test(WiserMessage m) {
                return m.getEnvelopeSender().equals(from);
            }
        }, assertionError("No message from [{0}] found!", from));
        return this;
    }

    public WiserAssertions to(final String to) {
        findFirstOrElseThrow(new Predicate<WiserMessage>() {
                                 @Override
                                 public boolean test(WiserMessage m) {
                                     return m.getEnvelopeReceiver().equals(to);
                                 }
                             },
                assertionError("No message to [{0}] found!", to));
        return this;
    }

    public WiserAssertions withSubject(final String subject) {
        Predicate<WiserMessage> predicate = new Predicate<WiserMessage>() {
            @Override
            public boolean test(WiserMessage m) {
                final MimeMessage mimeMessage = WiserAssertions.this.getMimeMessage(m);
                return subject.equals(unchecked(new ThrowingSupplier<Object>() {
                    @Override
                    public Object get() throws Throwable {
                        return mimeMessage.getSubject();
                    }
                }));
            }
        };
        findFirstOrElseThrow(predicate,
                assertionError("No message with subject [{0}] found!", subject));
        return this;
    }

    public WiserAssertions withContent(final String content) {
        findFirstOrElseThrow(new Predicate<WiserMessage>() {
            @Override
            public boolean test(final WiserMessage m) {
                ThrowingSupplier<String> contentAsString =
                        new ThrowingSupplier<String>() {
                            @Override
                            public String get() throws Throwable {
                                return ((String) WiserAssertions.this.getMimeMessage(m).getContent()).trim();
                            }
                        };
                return content.equals(unchecked(contentAsString));
            }
        }, assertionError("No message with content [{0}] found!", content));
        return this;
    }

    private void findFirstOrElseThrow(Predicate<WiserMessage> predicate, Supplier<AssertionError> exceptionSupplier) {
        messages.stream().filter(predicate)
                .findFirst().orElseThrow(exceptionSupplier);
    }

    private MimeMessage getMimeMessage(final WiserMessage wiserMessage) {
        return unchecked((ThrowingSupplier<MimeMessage>) new ThrowingSupplier<MimeMessage>() {
            @Override
            public MimeMessage get() throws Throwable {
                return wiserMessage.getMimeMessage();
            }
        });
    }

    private static Supplier<AssertionError> assertionError(final String errorMessage, final String... args) {
        return new Supplier<AssertionError>() {
            @Override
            public AssertionError get() {
                return new AssertionError(MessageFormat.format(errorMessage, args));
            }
        };
    }

    public static <T> T unchecked(ThrowingSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    interface ThrowingSupplier<T> {
        T get() throws Throwable;
    }
}