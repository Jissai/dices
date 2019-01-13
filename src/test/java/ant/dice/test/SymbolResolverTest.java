package ant.dice.test;

import ant.dice.Symbol;
import ant.dice.SymbolResolver;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.Collection;

public class SymbolResolverTest {

    @Nested
    @DisplayName("resolve")
    public class method_reolve {

        @BeforeEach
        public void setUp() throws Exception {
        }

        @AfterEach
        public void tearDown() throws Exception {
        }

        @Test
        @DisplayName("should return a valid instance")
        public void ctor_returnsValidInstance() {
        }

        @Test
        @DisplayName("should return a score of 2 when given 2 additive symbols")
        public void resolve_givenSame_returnsTwo() {
            Collection<Symbol> symbols = Arrays.asList(new Symbol(), new Symbol());
            int result = new SymbolResolver().resolve(symbols);
            Assertions.assertEquals(2, result);
        }

        @Test
        @DisplayName("should return a score of 0 when given 2 opposite symbols")
        public void resolve_givenOpposite_returnsEmpty() {
            Collection<Symbol> symbols = Arrays.asList(new Symbol(), new Symbol());
            int result = new SymbolResolver().resolve(symbols);
            Assertions.assertEquals(0, result);
        }
    }


}