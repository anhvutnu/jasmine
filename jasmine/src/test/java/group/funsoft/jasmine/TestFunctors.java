package group.funsoft.jasmine;

import static group.funsoft.jasmine.Functors.foldr;
import static org.junit.Assert.assertEquals;

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
}
