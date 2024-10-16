package brno.jug.presentation.jdk23.preview;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Path("jep455")
public class Jep455Resource {
    // http://127.0.0.1:8080/jep455/instance-of-new/10
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("instance-of-new/{number}")
    public String showInstanceOfNew(@PathParam("number") int number) {
        if (number instanceof byte b) {
            return "This is a byte with value: " + b;
        }
        return "This is not a byte";
    }

    // http://127.0.0.1:8080/jep455/instance-of-old/10
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("instance-of-old/{number}")
    public String showInstanceOfOld(@PathParam("number") int number) {
        if (number >= -128 && number <= 127) {
            byte b = (byte) number;
            return "This is a byte with value: " + b;
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
