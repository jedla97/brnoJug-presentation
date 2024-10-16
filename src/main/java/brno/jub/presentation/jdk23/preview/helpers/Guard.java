package brno.jub.presentation.jdk23.preview.helpers;

import org.apache.commons.lang3.RandomStringUtils;

import java.lang.reflect.MalformedParametersException;

public class Guard extends Employe {
    public Guard(int age, String name) throws MalformedParametersException {
        String id = RandomStringUtils.randomAlphabetic(10);
        if (id.contains("A") || id.contains("B") || id.contains("C") || id.contains("D") || id.contains("E")) {
            throw new MalformedParametersException("");
        }
        // check if id is not in used etc
        super(age, name, id);
    }

    public String generateId() {
        return RandomStringUtils.randomAlphabetic(10);
    }
}
