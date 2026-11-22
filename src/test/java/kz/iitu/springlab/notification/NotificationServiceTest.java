
package kz.iitu.springlab.notification;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.ArgumentCaptor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationSender sender;

    @InjectMocks
    private NotificationService service;

    @Test
    void disabledNotificationsAreNotSent() {
        boolean result = service.notifyReader(
                "reader@example.com",
                "Your book is ready",
                false
        );

        assertThat(result).isFalse();
        verify(sender, never()).send(
                "reader@example.com",
                "Your book is ready"
        );
    }


    @Test
    void enabledNotificationsAreSent() {
        boolean result = service.notifyReader(
                "reader@example.com",
                "Your book is ready",
                true
        );

        assertThat(result).isTrue();

        verify(sender).send(
                "reader@example.com",
                "Your book is ready"
        );
    }

    @Test
    void notificationContainsCorrectMessage() {
        String email = "student@example.com";
        String message = "Your reserved book is available";

        service.notifyReader(email, message, true);

        ArgumentCaptor<String> emailCaptor =
                ArgumentCaptor.forClass(String.class);

        ArgumentCaptor<String> messageCaptor =
                ArgumentCaptor.forClass(String.class);

        verify(sender).send(
                emailCaptor.capture(),
                messageCaptor.capture()
        );

        assertThat(emailCaptor.getValue()).isEqualTo(email);
        assertThat(messageCaptor.getValue()).isEqualTo(message);
    }

    @Test
    void senderFailureIsPropagated() {
        String email = "reader@example.com";
        String message = "Your book is ready";

        doThrow(new IllegalStateException("Sending failed"))
                .when(sender).send(email, message);

        assertThatThrownBy(() ->
                service.notifyReader(email, message, true)
        )
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Sending failed");

        verify(sender).send(email, message);
    }

}
