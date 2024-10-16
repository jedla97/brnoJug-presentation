package brno.jub.presentation.jdk23.preview.own.gatherers;

import java.util.function.Function;

public class CustomGatherers {
    public static <T, P> DistinctByGatherer<T, P> distinctBy(Function<T, P> extractor) {
        return new DistinctByGatherer<>(extractor);
    }
}