package brno.jub.presentation.jdk23.preview;

import brno.jub.presentation.jdk23.preview.helpers.Guard;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("jep482")
public class Jep482Resource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("guard")
    public String sold(@PathParam("id") int id) {
        Guard guard = new Guard(39, "Jedla");
        return guard.toString();
    }
}
