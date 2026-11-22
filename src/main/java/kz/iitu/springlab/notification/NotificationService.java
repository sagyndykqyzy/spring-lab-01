
package kz.iitu.springlab.notification;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationSender sender;

    public NotificationService(NotificationSender sender) {
        this.sender = sender;
    }

    public boolean notifyReader(String email, String message,
                                boolean notificationsEnabled) {
        if (!notificationsEnabled) {
            return false;
        }

        sender.send(email, message);
        return true;
    }
}
