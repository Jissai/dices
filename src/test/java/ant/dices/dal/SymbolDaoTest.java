package ant.dices.dal;

import static org.assertj.core.api.Assertions.*;

import ant.dices.CriticalSymbol;
import ant.dices.IOpposable;
import ant.dices.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SymbolDaoTest {
    @Nested
    @DisplayName("Create")
    public class Create {
        SymbolDao dao;

        @BeforeEach
        void setUp() {
            dao = new SymbolDao(DbAccess.test());
            dao.drop();
        }

        @Test
        @DisplayName("should write non-existing symbol in DB then read it without loss")
        void create_insert_Symbol_in_db() {
            IOpposable symbol = new Symbol("id", "opposite");
            dao = new SymbolDao(DbAccess.test());
            dao.create(symbol);
            IOpposable result = dao.read("id");
            assertThat(result).isEqualToComparingFieldByField(symbol);
        }

        @Test
        @DisplayName("should overwrite existing symbol in DB then read it without loss")
        void create_duoble_insert_Symbol_in_db() {
            IOpposable symbol = new Symbol("id", "opposite");
            dao = new SymbolDao(DbAccess.test());
            dao.create(symbol);
            dao.create(symbol);
            IOpposable result = dao.read("id");
            assertThat(result).isEqualToComparingFieldByField(symbol);
        }

        @Test
        @DisplayName("should write non-existing CriticalSymbol in DB then read it without loss")
        void create_insert_CriticalSymbol_in_db() {
            IOpposable equivalence = new Symbol("id", "opposite");
            IOpposable Critical = new CriticalSymbol("crit-id", "crit-opposite", equivalence);
            dao = new SymbolDao(DbAccess.test());
            dao.create(Critical);
            IOpposable result = dao.read("crit-id");
            assertThat(result).isEqualToComparingFieldByFieldRecursively(Critical);
        }
    }

}
