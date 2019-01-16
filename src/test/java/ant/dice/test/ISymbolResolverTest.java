package ant.dice.test;

import ant.dice.Symbol;
import ant.dice.SymbolResolverSymbolLong;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ISymbolResolverTest {

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
            Map<String, Long> result = new SymbolResolverSymbolLong().resolve(symbols);
            assertEquals(2, result.size());
        }

        @Test
        @DisplayName("should return an empty list when given 2 opposite symbols")
        public void resolve_givenOpposite_returnsEmpty() {
            Collection<Symbol> symbols = Arrays.asList(ga, zo);
            Map<String, Long> result = new SymbolResolverSymbolLong().resolve(symbols);
            assertEquals(0, result.size());
        }

        @Test
        @DisplayName("should return the resulting element(s) when opposition occurs")
        public void resolve_givenOpposite_returnsRemain() {
            Collection<Symbol> symbols = Arrays.asList(ga, zo, meu);
            Map<String, Long> result = new SymbolResolverSymbolLong().resolve(symbols);
            assertEquals(1, result.size());
            assertEquals(Long.valueOf(1), result.get("meu"));
        }

        @Test
        @DisplayName("should delete only one opposite for an occurence")
        public void resolve_givenManyOpposite_returnsRemain() {
            Collection<Symbol> symbols = Arrays.asList(ga, zo, meu, zo);
            Map<String, Long> result = new SymbolResolverSymbolLong().resolve(symbols);
            assertEquals(2, result.size());
            assertEquals(Long.valueOf(1), result.get("meu"));
            assertEquals(Long.valueOf(1), result.get("zo"));
        }
    }


}