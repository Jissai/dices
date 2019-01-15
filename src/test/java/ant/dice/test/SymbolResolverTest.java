package ant.dice.test;

import ant.dice.Symbol;
import ant.dice.SymbolResolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
            Map<String, LinkedList<Symbol>> result = new SymbolResolver().resolve(symbols);
            assertEquals(2, result.size());
        }

        @Test
        @DisplayName("should return an empty list when given 2 opposite symbols")
        public void resolve_givenOpposite_returnsEmpty() {
            Collection<Symbol> symbols = Arrays.asList(ga, zo);
            Map<String, LinkedList<Symbol>> result = new SymbolResolver().resolve(symbols);
            assertEquals(0, result.size());
        }

        @Test
        @DisplayName("should return the resulting element(s) when opposition occurs")
        public void resolve_givenOpposite_returnsRemain() {
            Collection<Symbol> symbols = Arrays.asList(ga, zo, meu);
            Map<String, LinkedList<Symbol>> result = new SymbolResolver().resolve(symbols);
            assertEquals(1, result.size());
            assertEquals(1, result.get("meu").size());
            assertEquals(meu, result.get("meu").getFirst());
        }

        @Test
        @DisplayName("should delete only one opposite for an occurence")
        public void resolve_givenManyOpposite_returnsRemain() {
            Collection<Symbol> symbols = Arrays.asList(ga, zo, meu, zo);
            Map<String, LinkedList<Symbol>> result = new SymbolResolver().resolve(symbols);
            assertEquals(2, result.size());
            assertEquals(1, result.get("meu").size());
            assertEquals(meu, result.get("meu").getFirst());
            assertEquals(1, result.get("zo").size());
            assertEquals(zo, result.get("zo").getFirst());
        }
    }


}