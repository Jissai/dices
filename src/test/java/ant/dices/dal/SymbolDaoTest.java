package ant.dices.dal;

import static org.assertj.core.api.Assertions.*;

import ant.dices.CriticalSymbol;
import ant.dices.IOpposable;
import ant.dices.Symbol;
import java.util.Collection;
import org.junit.jupiter.api.*;

class SymbolDaoTest {

    SymbolDao dao;

    @BeforeEach
    void setUp() {
        dao = new SymbolDao(DbAccess.test());
    }

    @AfterEach
    void tearDown() {
        dao.drop();
    }

    protected void createDataSet() {
        IOpposable symbol = new Symbol("id", "opposite");
        IOpposable equivalence = new Symbol("opposite", "id");
        IOpposable critical = new CriticalSymbol("crit-id", "crit-opposite", equivalence);
        dao = new SymbolDao(DbAccess.test());
        dao.upsert(symbol);
        dao.upsert(critical);
    }

    @Nested
    @DisplayName("Upsert")
    public class Create {

        @Test
        @DisplayName("should write non-existing symbol in DB then read it without loss")
        void create_insert_Symbol_in_db() {
            IOpposable symbol = new Symbol("id", "opposite");
            dao.upsert(symbol);
            IOpposable result = dao.read("id");
            assertThat(result).isEqualToComparingFieldByField(symbol);
        }

        @Test
        @DisplayName("should overwrite existing symbol in DB then read it without loss")
        void create_double_insert_Symbol_in_db() {
            IOpposable symbol = new Symbol("id", "opposite");
            dao.upsert(symbol);
            dao.upsert(symbol);
            IOpposable result = dao.read("id");
            assertThat(result).isEqualToComparingFieldByField(symbol);
        }

        @Test
        @DisplayName("should write non-existing CriticalSymbol in DB then read it without loss")
        void create_insert_CriticalSymbol_in_db() {
            IOpposable equivalence = new Symbol("id", "opposite");
            IOpposable critical = new CriticalSymbol("crit-id", "crit-opposite", equivalence);
            dao.upsert(critical);
            IOpposable result = dao.read("crit-id");
            assertThat(result).isEqualToComparingFieldByFieldRecursively(critical);
        }
    }

    @Nested
    @DisplayName("List")
    protected class List {
        @BeforeEach
        void setUp() {
            createDataSet();
        }

        @Test
        @DisplayName("should list ")
        void list_retrieve_every_element_from_db() {
            Collection<IOpposable> results = dao.list();
            assertThat(results).hasSize(3);
        }
    }

//    @Nested
//    @DisplayName("Remove")
//    protected class Remove {
//        SymbolDao dao;
//
//        @BeforeEach
//        void setUp() {
//            createDataSet();
//        }
//
//        @Test
//        @DisplayName("should remove existing symbol in DB")
//        void remove_existing_symbol_in_db() {
//
//        }
//
//        @Test
//        @DisplayName("should remove existing CriticalSymbol in DB. Leave references")
//        void remove_existing_CriticalSymbol_in_db() {
//
//        }
//
//    }

}
