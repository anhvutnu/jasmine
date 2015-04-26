package group.funsoft.jasmine;

import static group.funsoft.jasmine.Functors.filter;
import static group.funsoft.jasmine.Functors.foldr;
import static group.funsoft.jasmine.Functors.groupBy;
import static group.funsoft.jasmine.Functors.map;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.junit.Test;

public class TestFunctors {
    
    @Test
    public void testFoldr() {
        Integer test = foldr(Accumulators::sumInt,
                             0,
                             4,
                             Adapters::intConsAdapter);

        assertEquals(Integer.valueOf(10), test);
    }
    
    @Test
    public void testFoldrCollection() {
        Collection<Integer> loin = Arrays.asList(1, 2, 3, 4, 5);
        Integer test = foldr(Accumulators::sumInt, 0, loin);
        assertEquals(Integer.valueOf(15), test);
    }
    
    @Test
    public void testMap() {
        Collection<Integer> loin = Arrays.asList(1, 2, 3, 4, 5);
        Collection<Integer> test = map(item -> item + 1, loin);
        assertEquals(loin.size(), test.size());
    }
    
    @Test
    public void testFilter() {
        Collection<Integer> loint = Arrays.asList(1, 2, 3, 4, 5, 6);
        Collection<Integer> test = filter(item -> item % 2 == 0, loint);
        assertEquals(3, test.size());
    }
    
    @Test
    public void testGroupBy() {
        Collection<TestGroup> groups = Arrays.asList(TestGroup.get(1L, "1"),
                                                     TestGroup.get(1L, "2"),
                                                     TestGroup.get(2L, "3"),
                                                     TestGroup.get(2L, "4"));
        
        Map<Long, Collection<TestGroup>> test = groupBy(item-> item.getGroupId(), groups);
        assertEquals(2, test.size());
        assertEquals(2, test.get(1L).size());
        assertEquals(2, test.get(2L).size());
    }
}
