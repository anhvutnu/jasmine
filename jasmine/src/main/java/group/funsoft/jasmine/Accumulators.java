package group.funsoft.jasmine;

import java.util.ArrayList;
import java.util.Collection;

public class Accumulators {
    
    public static Integer sumInt(Integer x , Integer y) {
        return x + y;
    }
    
    public static <X> Collection<X> consCollection(X item, Collection<X> items) {
        Collection<X> r = new ArrayList<X> (items.size() + 1);
        r.add(item);
        items.stream().forEach(r::add);
        return r;
    }
}
