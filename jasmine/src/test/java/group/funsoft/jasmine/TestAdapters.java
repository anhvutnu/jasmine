package group.funsoft.jasmine;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestAdapters {
    
    @Test
    public void testCollectionConsAdapter() throws Exception {
        Collection<Integer> loint = new ArrayList<Integer>();
        loint.add(1);
        loint.add(2);
        
        ConsAdapter<Integer, Collection<Integer>> test = Adapters.collectionConsAdapter();
        
        assertEquals(Integer.valueOf(1), test.getFirst(loint));
        
        assertEquals(1, test.getRest(loint).size());
    }
}
