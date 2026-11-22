
package kz.iitu.springlab.notification;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class NotificationSpyTest {

    @MockitoBean
    private NotificationSender sender;

    @MockitoSpyBean
    private NotificationService service;

    @Test
    void spyCallsRealMethodAndVerifiesInteraction() {
        boolean result = service.notifyReader(
                "reader@example.com",
                "Your book is ready",
                true
        );

        assertThat(result).isTrue();

        verify(service).notifyReader(
                "reader@example.com",
                "Your book is ready",
                true
        );

        verify(sender).send(
                "reader@example.com",
                "Your book is ready"
        );
    }
}

