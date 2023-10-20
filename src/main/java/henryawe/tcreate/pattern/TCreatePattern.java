package henryawe.tcreate.pattern;

@FunctionalInterface
public interface TCreatePattern<MatchType> {
    boolean matches(MatchType o);
}
