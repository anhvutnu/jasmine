package group.funsoft.jasmine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Lists of convenient adapters.
 * 
 * @author Vu Chu
 *
 */
public class Adapters {

    /**
     * An adapter for treating a integer as a cons (E.Z : 4 -> [4, 3, 2, 1, 0])
     * 
     * @return A ConsAdapter
     */
    public static ConsAdapter<Integer, Integer> intConsAdapter() {
        return new ConsAdapter<Integer, Integer>() {

            @Override
            public Integer getFirst(Integer cons) {
                return cons;
            }

            @Override
            public Integer getRest(Integer cons) {
                return cons - 1;
            }

            @Override
            public boolean isNil(Integer cons) {
                return cons == 0;
            }

            @Override
            public Integer add(Integer value, Integer cons) {
                return value + cons;
            }

            @Override
            public Integer empty() {
                return 0;
            }
        };
    }

    public static <X> ConsAdapter<X, Collection<X>> collectionConsAdapter() {

        return new ConsAdapter<X, Collection<X>>() {

            @Override
            public X getFirst(Collection<X> cons) {
                return cons.stream().findFirst().get();
            }

            @Override
            public Collection<X> getRest(Collection<X> cons) {
                Collection<X> r = new ArrayList<X>(cons.size());
                Iterator<X> it = cons.iterator();
                boolean first = true;

                while (it.hasNext()) {
                    if (!first) {
                        r.add(it.next());
                    } else {
                        it.next();
                        first = false;
                    }
                }

                return r;
            }

            @Override
            public boolean isNil(Collection<X> cons) {
                return cons.isEmpty();
            }

            @Override
            public Collection<X> add(X value, Collection<X> cons) {
                cons.add(value);
                return cons;
            }

            @Override
            public Collection<X> empty() {
                return new LinkedList<>();
            }
        };
    }
}
