package brno.jub.presentation.jdk23.preview;

import brno.jub.presentation.jdk23.preview.own.gatherers.CustomGatherers;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

@Path("jep473")
public class Jep473Resource {
    private static final List<Integer> windowsListNumbers = Arrays.asList(3, 2, 1, 4, 7, 6, 5);
    private static final int GROUP_SIZE = 3;

    // http://127.0.0.1:8080/jep473/windows-without
    @GET
    @Path("windows-without")
    public ArrayList<ArrayList<Integer>> splitToGroup() {

        return windowsListNumbers.stream()
                .collect(Collector.of(() -> new ArrayList<>(),
                        (groups, number) -> {
                            if(groups.isEmpty() || groups.getLast().size() == GROUP_SIZE) {
                                var currentGroup = new ArrayList<Integer>();
                                currentGroup.add(number);
                                groups.addLast(currentGroup);
                            } else {
                                groups.getLast().add(number);
                            }
                        }, (a, b) -> {
                            return null;
                        }));
    }

    // http://127.0.0.1:8080/jep473/windows
    @GET
    @Path("windows")
    public List<List<Integer>> splitToGroupGatherers() {

        return windowsListNumbers.stream()
                .gather(Gatherers.windowSliding(GROUP_SIZE))
                .collect(Collectors.toList());
    }

    // http://127.0.0.1:8080/jep473/fold
    @GET
    @Path("fold")
    public String fold() {
        // similar to reduce
        return Stream.of("Brno","JUG","how","are","you?")
                .gather(Gatherers.fold(() -> "", (str, element) -> str.isEmpty() ? element : str + " " + element))
                .findFirst()
                .orElseThrow();
        /**
        return Stream.of("Brno","JUG","how","are","you?")
                .gather(Gatherers.scan(() -> "", (str, element) -> str.isEmpty() ? element : str + " " + element))
                .collect(Collectors.toList()).toString();
         */
    }

    // http://127.0.0.1:8080/jep473/map
    @GET
    @Path("map")
    public List<Integer> reduce() {
        // TODO show map
        return Stream.of(1, 2, 3, 4, 5)
                .gather(Gatherers.mapConcurrent(4 , x -> x * 2))
                .collect(Collectors.toList());

    }

    // http://127.0.0.1:8080/jep473/own-distinct
    @GET
    @Path("own-distinct")
    public List<String> distinct() {
        return Stream.of("Brno","JUG","how","how","are","you?","you?").limit(4)
                .gather(CustomGatherers.distinctBy(item -> item))
                .collect(Collectors.toList());

    }
}
