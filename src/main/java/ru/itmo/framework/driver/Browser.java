package ru.itmo.framework.driver;

public enum Browser {
    CHROME,
    FIREFOX;

    public static Browser from(String value) {
        if (value == null) return CHROME;

        return switch (value.toLowerCase()) {
            case "firefox" -> FIREFOX;
            default -> CHROME;
        };
    }
}