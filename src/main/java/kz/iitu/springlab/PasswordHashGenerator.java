
package kz.iitu.springlab;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String userHash = encoder.encode("user123");
        String adminHash = encoder.encode("admin123");

        System.out.println("USER HASH: " + userHash);
        System.out.println("ADMIN HASH: " + adminHash);

        System.out.println("USER MATCH: " +
                encoder.matches("user123", userHash));

        System.out.println("ADMIN MATCH: " +
                encoder.matches("admin123", adminHash));
    }
}


