package ant.dices.dal;

import ant.dices.Symbol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SymbolDaoTest {
    @Nested
    @DisplayName("Create")
    public class Create {

        @Test
        @DisplayName("should")
        void create_insert_symbol_in_db() {
            Symbol symbol = new Symbol("id", "opposite");
            SymbolDao dao = new SymbolDao(DbAccess.test());
            dao.create(symbol);
        }
    }

}
