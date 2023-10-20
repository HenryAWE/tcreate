package henryawe.tcreate.pattern;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

/**
 * A matcher interface.
 *
 * @author KKoishi_
 * @param <MatchType> the match type, such as BlockState
 */
public interface PatternMatcher<MatchType> {
    @NotNull
    Stream<? extends TCreatePattern<MatchType>> patterns ();

    @NotNull
    PatternMatcher<MatchType> register (@NotNull TCreatePattern<MatchType> pattern);


    default boolean matches (MatchType any) {
        return patterns().anyMatch((it) -> it.matches(any));
    }
}
