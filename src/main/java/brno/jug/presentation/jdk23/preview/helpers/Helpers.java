package brno.jug.presentation.jdk23.preview.helpers;

import org.apache.commons.lang3.RandomStringUtils;

public class Helpers {
    public static String generateId() {
        return RandomStringUtils.randomAlphabetic(10).toUpperCase();
    }
}
