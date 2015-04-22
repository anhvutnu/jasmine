package group.funsoft.jasmine;

/**
 * 
 * @author Vu Chu
 *
 * @param <X> type of elements in cons
 * @param <Y> type of cons
 */
public interface ConsAdapter<X, Y> {
    
    /**
     * Get the first element of cons
     * 
     * @param cons The cons
     * @return first element in cons
     */
    public X getFirst(Y cons);
    
    /**
     * Get the rest of cons
     * @param cons The cons
     * @return the rest of cons
     */
    public Y getRest(Y cons);
    
    /**
     * Is given cons NIL?
     * @param cons cons being tested for nil
     * @return true/false
     */
    public boolean isNil(Y cons);
    
    public Y add(X value, Y cons);
    
    public Y empty();
    
    
}
