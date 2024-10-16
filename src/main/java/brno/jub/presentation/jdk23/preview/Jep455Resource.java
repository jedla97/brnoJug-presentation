package brno.jub.presentation.jdk23.preview;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Path("jep455")
public class Jep455Resource {
    // http://127.0.0.1:8080/jep455/instance-of-new
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("instance-of-new")
    public String showInstanceOfNew() {
        int firstByte = 10;
        if (firstByte instanceof byte b) {
            return "First byte is " + b;
        }
        return "This is not a byte";
    }

    // http://127.0.0.1:8080/jep455/instance-of-old
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("instance-of-old")
    public String showInstanceOfOld() {
        int firstByte = 10;
        if (firstByte >= -128 && firstByte <= 127) {
            byte b = (byte) firstByte;
            return "First byte is " + b;
        }
        return "This is not a byte";
    }


    // http://127.0.0.1:8080/jep455/switch
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("switch")
    public String switchExample() {
        Random rd = new Random();
        int number = rd.nextInt(0, 4);
        switch (number) {
            case 0 -> {
                return "This is a 0";
            }
            case 1 -> {
                return "This is a 1";
            }
            case int i -> {
                return "This is a default for other numbers like: " + i;
            }
        }
    }

    // http://127.0.0.1:8080/jep455/switch-big-constant
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("switch-big-constant")
    public String switchExample2() {
        List<Long> listOfLongs = Arrays.asList(1L, 20_000_000_000L, 56L);
        Random rd = new Random();
        long number = listOfLongs.get(rd.nextInt(0, 3));
        switch (number) {
            case 1L -> {
                return "This is a 1L";
            }
            case 20_000_000_000L -> {
                return "You can check also for large constans";
            }
            case long i -> {
                return "This is a default for other numbers like: " + i;
            }
        }
    }
}
