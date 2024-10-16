package brno.jub.presentation.jdk23.preview;

import brno.jub.presentation.jdk23.preview.helpers.Guard;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.lang.reflect.MalformedParametersException;

@Path("jep482")
public class Jep482Resource {

    // http://localhost:8080/jep482/guard
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("guard")
    public String createGuard() {
        try {
            Guard guard = new Guard(39, "Jedla");
            return guard.toString();
        } catch (MalformedParametersException _) {
            return "Unable to create Guard";
        }
    }
}
