package ant.dices.dal;

import static org.assertj.core.api.Assertions.*;

import ant.dices.CriticalSymbol;
import ant.dices.IOpposable;
import ant.dices.Symbol;
import io.jsondb.JsonDBException;
import java.util.Collection;
import org.junit.jupiter.api.*;

class SymbolDaoTest {

    SymbolDao dao;
    IOpposable symbol = new Symbol("id", "opposite");
    IOpposable equivalence = new Symbol("opposite", "id");
    IOpposable critical = new CriticalSymbol("crit-id", "crit-opposite", equivalence);

    @BeforeEach
    void setUp() {
        dao = new SymbolDao(DbAccess.test());
    }

    @AfterEach
    void tearDown() {
        dao.dropDb();
    }

    protected void createDataSet() {
        dao = new SymbolDao(DbAccess.test());
        dao.upsert(symbol);
        dao.upsert(critical);
    }

    @Nested
    @DisplayName("Upsert")
    public class Upsert {

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
        @DisplayName("should list every Symbol in DB")
        void list_retrieve_every_element_from_db() {
            Collection<IOpposable> results = dao.list();
            assertThat(results).hasSize(3);
        }

        @Test
        @DisplayName("should return empty array if no symbol in DB ")
        void list_retrieve_empty_array_from_db() {
            dao.dropDb();
            dao.createDb();
            Collection<IOpposable> results = dao.list();
            assertThat(results).hasSize(0);
        }
    }

    @Nested
    @DisplayName("Remove")
    protected class Remove {
        @BeforeEach
        void setUp() {
            createDataSet();
        }

        @Test
        @DisplayName("should remove existing symbol in DB")
        void remove_existing_symbol_in_db() {
            dao.delete("id");
            Collection<IOpposable> results = dao.list();
            assertThat(results).hasSize(2).containsExactlyInAnyOrder(equivalence, critical);
        }

        @Test
        @DisplayName("should remove existing CriticalSymbol in DB. Leave references")
        void remove_existing_CriticalSymbol_in_db() {
            dao.delete("crit-id");
            Collection<IOpposable> results = dao.list();
            assertThat(results).hasSize(2).containsExactlyInAnyOrder(symbol, equivalence);
        }

        @Test
        @DisplayName("should throw if trying to delete referenced Symbol in DB. Leave references")
        void remove_existing_referenced_Symbol_in_db() {
            Throwable thrown = catchThrowable(() -> dao.delete("opposite"));
            assertThat(thrown)
                .isInstanceOf(JsonDBException.class)
                .hasMessageMatching("^Deletion of id.*It is still referenced by id.*$");
            Collection<IOpposable> results = dao.list();
            assertThat(results).hasSize(3).containsExactlyInAnyOrder(symbol, equivalence, critical);
        }
    }

}
