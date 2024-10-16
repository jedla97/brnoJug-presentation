package brno.jub.presentation.jdk23;

import brno.jub.presentation.jdk23.helpers.RandomRecord;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Gatherers;

@Path("jep456")
public class Jep456Resource {

    private static int soldItemsCounter = 0;

    private final List<String> firstOrder = Arrays.asList("apple", "orange");
    private final List<String> secondOrder = Arrays.asList("banana", "apple", "milk");
    private final List<List<String>> ordersList = Arrays.asList(firstOrder, secondOrder);

    private final Object[] objectsForRecord = new Object[]{39, "This is string"};

    public Jep456Resource() {
    }

    // http://localhost:8080/jep456/exception/1
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/exception/{divisor}")
    public String exception(@PathParam("divisor") int divisor) {
        // TODO modify
        try {
            int result = 5 / divisor;
            return "5 divided by " + divisor + " is " + result;
        } catch (ArithmeticException e) {
            return e.toString();
        }
    }

    // http://localhost:8080/jep456/order/0
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/order/{id}")
    public String order(@PathParam("id") int id) {
        // TODO modify
        for (String item : ordersList.get(id)) {
            soldItemsCounter++;
        }
        return "Order placed";
    }

    // http://localhost:8080/jep456/order/sold
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/order/sold")
    public String sold(@PathParam("id") int id) {
        return "Sold items = " + soldItemsCounter;
    }

    // http://localhost:8080/jep456/type-check
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/type-check")
    public String checkTheType() {
        switch (generate()) {
            case RandomRecord(String string, int _) -> {
                return "I just want use first string which is " + string + " But second value need to be int";
            }
            case RandomRecord(int number, _) -> {
                return "I just want use number: " + number;
            }
            case RandomRecord(_, String string) -> {
                return "Just mapping for second value to be string. For example first can be Log level etc.";
            }
            default -> {
                return "This use case is not mapped yet";
            }
        }
    }


    private RandomRecord generate() {
        Random rand = new Random();
        return new RandomRecord(objectsForRecord[rand.nextInt(2)], objectsForRecord[rand.nextInt(2)]);
    }

}
