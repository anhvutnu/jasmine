package group.funsoft.jasmine;

/**
 * 
 * @author Vu Chu
 *
 * @param <X>
 */
@FunctionalInterface
public interface Accumulator<X, Y> {
    
    /**
     * Add/combine new value to what has been accumulated so far.
     * 
     * @param value New value to be combined with what has been accumulated so far.
     * @param accumulatedUpToNow What has been accumulated so far.
     * @return new value for what has been accumulated so far.
     */
    public Y accumulate(X value, Y accumulatedUpToNow);
}
