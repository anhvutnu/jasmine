package group.funsoft.jasmine;

import static group.funsoft.jasmine.Functors.filter;
import static group.funsoft.jasmine.Functors.foldr;
import static group.funsoft.jasmine.Functors.groupBy;
import static group.funsoft.jasmine.Functors.map;
import static org.junit.Assert.assertEquals;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;


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
    
    @Test
    public void euler1() {
        final Predicate<Integer> isMultipleOf3Or5 = new Predicate<Integer>() {
            @Override
            public boolean test(Integer arg0) {
                return arg0 % 3 == 0 || arg0 % 5 ==0;
            }
        };
        
        BiFunction<Integer, Integer, Integer> accumulator = new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer arg0, Integer arg1) {
                return isMultipleOf3Or5.test(arg0) ? arg0 + arg1 : arg1;
            }
        };
        
        Integer test = foldr(accumulator, 0, 999, Adapters::intConsAdapter);
        assertEquals(Integer.valueOf(233168), test);
    }
    
    @Test
    public void testCombine() {
        List<Integer> testX = new ArrayList<Integer>(2);
        testX.add(1);
        testX.add(2);
        
        List<Integer> testY = new ArrayList<Integer>(2);
        testY.add(3);
        testY.add(4);
        
        Collection<Integer> test = Functors.combine(testX, testY);
        assertEquals(4, test.size());
        
    }
    
    @Test
    public void testFlatten() {
        List<Integer> testX = new ArrayList<Integer>(2);
        testX.add(1);
        testX.add(2);
        
        List<Integer> testY = new ArrayList<Integer>(2);
        testY.add(3);
        testY.add(4);
        
        List<Integer> testZ = new ArrayList<Integer>(2);
        testZ.add(5);
        testZ.add(6);
        
        Collection<Collection<Integer>> nested = new ArrayList<Collection<Integer>>();
        nested.add(testX);
        nested.add(testY);
        nested.add(testZ);
        
        Collection<Integer> test = Functors.flatten(nested);
        
        assertEquals(6, test.size());
    }
}
