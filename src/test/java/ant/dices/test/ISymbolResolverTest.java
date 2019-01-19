package ant.dices.test;

import static org.assertj.core.api.Assertions.*;

import ant.dices.Symbol;
import ant.dices.SymbolResolverSymbolLong;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ISymbolResolverTest {

    @Nested
    @DisplayName("resolve")
    class Resolve {

        Symbol ga = new Symbol("ga", "zo");
        Symbol bu = new Symbol("bu");
        Symbol zo = new Symbol("zo", "ga");
        Symbol meu = new Symbol("meu");

        @Test
        @DisplayName("should return unmodified list when given 2 additive symbols")
        void resolve_givenSame_returnsTwo() {
            Collection<Symbol> symbols = Arrays.asList(ga, bu);
            Map<String, Long> result = new SymbolResolverSymbolLong().resolve(symbols);
            assertThat(result).hasSize(2);
        }

        @Test
        @DisplayName("should return an empty list when given 2 opposite symbols")
        void resolve_givenOpposite_returnsEmpty() {
            Collection<Symbol> symbols = Arrays.asList(ga, zo);
            Map<String, Long> result = new SymbolResolverSymbolLong().resolve(symbols);
            assertThat(result).hasSize(0);
        }

        @Test
        @DisplayName("should return the resulting element(s) when opposition occurs")
        void resolve_givenOpposite_returnsRemain() {
            Collection<Symbol> symbols = Arrays.asList(ga, zo, meu);
            Map<String, Long> result = new SymbolResolverSymbolLong().resolve(symbols);
            assertThat(result).hasSize(1);
            assertThat(result).containsEntry("meu", Long.valueOf(1));
        }

        @Test
        @DisplayName("should delete only one opposite for an occurence")
        void resolve_givenManyOpposite_returnsRemain() {
            Collection<Symbol> symbols = Arrays.asList(ga, zo, meu, zo);
            Map<String, Long> result = new SymbolResolverSymbolLong().resolve(symbols);
            assertThat(result).hasSize(2);
            assertThat(result).containsEntry("meu", Long.valueOf(1));
            assertThat(result).containsEntry("zo", Long.valueOf(1));
        }
    }


}