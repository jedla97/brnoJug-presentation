package brno.jub.presentation.jdk23.preview.helpers;

import org.apache.commons.lang3.RandomStringUtils;

public class Guard extends Employe {
    public Guard(int age, String name) {
        super(age, name, "");
        String id = generateId();
        // check if id is not in used etc
    }

    public String generateId() {
        return RandomStringUtils.randomAlphabetic(10);
    }
}
