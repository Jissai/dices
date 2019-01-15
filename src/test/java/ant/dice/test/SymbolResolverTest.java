package ant.dice.test;

import ant.dice.Symbol;
import ant.dice.SymbolResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

public class SymbolResolverTest {

    @Nested
    @DisplayName("resolve")
    public class method_reolve {

        Symbol ga = new Symbol("ga", "zo");
        Symbol bu = new Symbol("bu");
        Symbol zo = new Symbol("zo", "ga");
        Symbol meu = new Symbol("meu");

        @Test
        @DisplayName("should return unmodified list when given 2 additive symbols")
        public void resolve_givenSame_returnsTwo() {
            Collection<Symbol> symbols = Arrays.asList(ga, bu);
            Collection<Symbol> result = new SymbolResolver().resolve(symbols);
            Assertions.assertEquals(2, result.size());
        }

        @Test
        @DisplayName("should return an empty list when given 2 opposite symbols")
        public void resolve_givenOpposite_returnsEmpty() {
            Collection<Symbol> symbols = Arrays.asList(ga, zo);
            Collection<Symbol> result = new SymbolResolver().resolve(symbols);
            Assertions.assertEquals(0, result.size());
        }

        @Test
        @DisplayName("should return the resulting element(s) when opposition occurs")
        public void resolve_givenOpposite_returnsRemain() {
            Collection<Symbol> symbols = Arrays.asList(ga, zo, meu);
            Collection<Symbol> result = new SymbolResolver().resolve(symbols);
            Assertions.assertEquals(1, result.size());
            Assertions.assertEquals(meu, result.toArray()[0]);
        }

        @Test
        @DisplayName("should delete only one opposite for an occurence")
        public void resolve_givenManyOpposite_returnsRemain() {
            Collection<Symbol> symbols = Arrays.asList(ga, zo, meu, zo);
            Collection<Symbol> result = new SymbolResolver().resolve(symbols);
            Assertions.assertEquals(2, result.size());
            Assertions.assertEquals(meu, result.toArray()[0]);
            Assertions.assertEquals(zo, result.toArray()[1]);
        }
    }


}