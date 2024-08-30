package ru.otus.kudaiberdieva.security;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.otus.kudaiberdieva.entities.User;

public class SecurityUtils {
    public static User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }
}
