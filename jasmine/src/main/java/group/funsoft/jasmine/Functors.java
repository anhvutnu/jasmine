package group.funsoft.jasmine;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Functors {
    
    /**
     * 
     * @param p
     * @param cons
     * @return
     */
    public static <X> Boolean all(Predicate<X> p , Collection<X> cons) {
        return all(p, cons, Adapters::collectionConsAdapter);
    }
    
    /**
     *  
     * @param p
     * @param cons
     * @param consAdpaterYSupplier
     * @return
     */
    public static <X, Y> Boolean all(Predicate<X> p , Y cons, Supplier<ConsAdapter<X, Y>> consAdpaterYSupplier) {
       return foldr((X value, Boolean bCons)-> p.test(value) && bCons,
                    true,
                    cons,
                    consAdpaterYSupplier); 
     }
     
    /**
     * 
     * @param p
     * @param cons
     * @return
     */
     public static <X> Boolean any(Predicate<X> p , Collection<X> cons) {
         return any(p, cons, Adapters::collectionConsAdapter);
     }
     
    /**
     * 
     * @param p
     * @param cons
     * @param consAdapterYSupplier
     * @return
     */
    public static <X, Y> Boolean any(Predicate<X> p, Y cons, Supplier<ConsAdapter<X, Y>> consAdapterYSupplier) {
        
       return foldr((X value, Boolean bCons) -> p.test(value) || bCons, 
                    false,
                    cons,
                    consAdapterYSupplier);
    }
    
    /**
     * 
     * @param cons
     * @return
     */
    public static <X> Collection<X> rest(Collection<X> cons) {
        return rest(cons, Adapters::collectionConsAdapter);
    }
    
    /**
     * 
     * @param cons
     * @param consAdapterYSupplier
     * @return
     */
    public static <X, Y> Y rest(Y cons, Supplier<ConsAdapter<X, Y>> consAdapterYSupplier) {
        ConsAdapter<X, Y> adapter = consAdapterYSupplier.get();
        return adapter.getRest(cons);
    }
    
    /**
     * 
     * @param cons
     * @return
     */
    public static <X> X first (Collection<X> cons) {
        return first(cons, Adapters::collectionConsAdapter);
    }
    
    /**
     * 
     * @param cons
     * @param consAdpaterYSupplier
     * @return
     */
    public static <X, Y> X first(Y cons, Supplier<ConsAdapter<X, Y>> consAdpaterYSupplier) {
        ConsAdapter<X, Y> adapter = consAdpaterYSupplier.get();
        return adapter.getFirst(cons);
    }
    
    /**
     * 
     * @param p
     * @param cons
     * @return
     */
    public static <X> Optional<X> first(Predicate<X> p, Collection<X> cons) {
        return first(p, cons, Adapters::collectionConsAdapter);
    }
    
    /**
     * 
     * @param p
     * @param cons
     * @param adapterYSupplier
     * @return
     */
     public static <X, Y> Optional<X> first(Predicate<X> p, Y cons, Supplier<ConsAdapter<X, Y>> adapterYSupplier) {
         ConsAdapter<X, Y> adapterY = adapterYSupplier.get();
         
         if (adapterY.isNil(cons)) {
             return Optional.empty();
         }
         
         X first = adapterY.getFirst(cons);
         
         if (p.test(adapterY.getFirst(cons))) {
             return Optional.of(first);
         }
         
         return first(p, adapterY.getRest(cons), adapterYSupplier);
     }
     
    /**
     * 
     * @param p
     * @param cons
     * @return
     */
    public static <X> Collection<X> filter(Predicate<X> p , Collection<X> cons) {
        return filter(p, cons, Adapters::collectionConsAdapter);
    }
    
    /**
     * 
     * @param p
     * @param cons
     * @param consAdapterYSupplier
     * @return
     */
    public static <X, Y> Y filter(Predicate<X> p, Y cons, Supplier<ConsAdapter<X, Y>> consAdapterYSupplier) {
        ConsAdapter<X, Y> adapterY = consAdapterYSupplier.get();
        if (adapterY.isNil(cons)) {
            return adapterY.empty();
        }
        
        X first = adapterY.getFirst(cons);
        
        if (p.test(first)) {
            return adapterY.add(first, filter(p, adapterY.getRest(cons), consAdapterYSupplier));
        }
        
        return filter(p, adapterY.getRest(cons), consAdapterYSupplier);
    }
    
    /**
     * 
     * @param converter
     * @param cons
     * @return
     */
    public static <X, Y> Collection<X> map(Function<Y, X> converter, Collection<Y> cons) {
        return map(converter,
                   cons, 
                   Adapters::collectionConsAdapter, 
                   Adapters::collectionConsAdapter);
    }
    
    /**
     * 
     * @param converter
     * @param cons
     * @param adapterYSupplier
     * @param adapterTSupplier
     * @return
     */
    public static <X, Y, Z, T> T map(Function<X, Z> converter, 
                                     Y cons,
                                     Supplier<ConsAdapter<X, Y>> adapterYSupplier,
                                     Supplier<ConsAdapter<Z, T>> adapterTSupplier) {
        
       ConsAdapter<Z, T> adapterT = adapterTSupplier.get();
        
       return foldr((Z value, T rCons)-> adapterT.add(value, rCons),
                    converter, 
                    adapterT.empty(),
                    cons,
                    adapterYSupplier);
    }
    
    /**
      * Foldr over a collection of objects
      * 
      * @param accumulator
      * @param base
      * @param cons
      * @return
      */
     public static <X, Y> Y foldr(Accumulator<X, Y> accumulator, Y base, Collection<X> cons) {
         return foldr(accumulator, base, cons, Adapters::collectionConsAdapter);
     }

    /**
     * 
     * @param accumulator
     * @param base
     * @param cons
     * @param adapterSupplier
     * @return
     */
    public static <X, Y, Z> Z foldr(Accumulator<X, Z> accumulator, 
                                    Z base, 
                                    Y cons, 
                                    Supplier<ConsAdapter<X, Y>> adapterSupplier) {
        
        ConsAdapter<X, Y> adapter = adapterSupplier.get();
        
        if (adapter.isNil(cons)) {
            return base;
        }
        
        return accumulator.accumulate(adapter.getFirst(cons), 
                                      foldr(accumulator, base, adapter.getRest(cons), adapterSupplier));
        
    }
    
    /**
     * This method is a basically the implementation of:
     * 
     * foldr (f.g) base NIL = base
     * foldr (f.g) base (Cons first rest) = (f.g) a (foldr (f.g) base rest) 
     * With (f.g) x = f(g(x))
     * 
     * Where f is the accumulator, g is the transformer, base is the base starting value.
     * 
     * @param accumulator The function that performs the accumulation
     * @param converter The function that performs the conversion from Z to X
     * @param base The base value
     * @param cons The cons
     * @param adapterSupplier The supplier that's responsible for supplying the adapter for cons.
     * @return accumulated value 
     */
    public static <X, Y, Z, T> X foldr(Accumulator<T, X> accumulator, 
                                    Function<Z, T> converter, 
                                    X base,
                                    Y cons,
                                    Supplier<ConsAdapter<Z, Y>> adapterSupplier) {
        
        ConsAdapter<Z, Y> adapter = adapterSupplier.get();
        
        if (adapter.isNil(cons)) {
            return base;
        }
        
       return accumulator.accumulate(converter.apply(adapter.getFirst(cons)), foldr(accumulator, converter, base, adapter.getRest(cons), adapterSupplier));
    }
}
