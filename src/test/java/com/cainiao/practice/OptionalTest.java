package com.cainiao.practice;

import java.util.Optional;

class User {
    public String name;
    public int age;
}

public class OptionalTest {
    public static String getName(User user) {
        if (user == null) {
            return "unknown";
        } else {
            return user.name;
        }
    }

    public static String getNameOptional(User u) {
        return Optional.ofNullable(u).map(user -> user.name).orElse("unknown");
    }
}
