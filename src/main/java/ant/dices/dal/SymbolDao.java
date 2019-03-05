package ant.dices.dal;

import ant.dices.CriticalSymbol;
import ant.dices.IOpposable;
import ant.dices.IOpposableEquivalence;
import ant.dices.Symbol;
import ant.dices.dal.schema.SymbolDbSchema;
import io.jsondb.InvalidJsonDbApiUsageException;
import io.jsondb.JsonDBException;
import io.jsondb.JsonDBTemplate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SymbolDao {

    private final JsonDBTemplate jsonDBTemplate;

    public SymbolDao(JsonDBTemplate jsonDBTemplate) {
        this.jsonDBTemplate = jsonDBTemplate;
        this.createDb();
    }

    /**
     * Given a list of Symbols, try to find matching pairs of opposite Symbols and remove them from the final result.
     *
     * @param opposable unordered list of Symbols to resolve
     */
    public void upsert(IOpposable opposable) {

        List<String> listIds = new ArrayList<>();
        if (opposable instanceof IOpposableEquivalence) {
            for (IOpposable equivalence : ((IOpposableEquivalence) opposable).getOpposableEquivalence()) {
                this.upsert(equivalence);
                listIds.add(equivalence.getId());
            }
        }

        this.jsonDBTemplate.upsert(toSchema(opposable, listIds));

    }

    public IOpposable read(String id) {
        SymbolDbSchema result = this.jsonDBTemplate.findById(id, SymbolDbSchema.class);
        return fromSchema(result);
    }

    public Collection<IOpposable> list() {
        List<SymbolDbSchema> results = this.jsonDBTemplate.findAll(SymbolDbSchema.class);
        return results.stream().map(result -> this.fromSchema(result)).collect(Collectors.toList());
    }

    /**
     * Remove a {@link SymbolDbSchema} from the collection in DB.
     *
     * @param id the primary key of the symbol to remove.
     * @throws JsonDBException if the given id is still referenced by at least another Symbol.
     */
    public void delete(String id) throws JsonDBException {

        List<SymbolDbSchema> references = this.jsonDBTemplate.findAll(SymbolDbSchema.class).stream()
            .filter(sc -> sc.getEquivalenceIds().contains(id))
            .limit(1)
            .collect(Collectors.toList());

        if (references.isEmpty()) {
            SymbolDbSchema result = this.jsonDBTemplate.findById(id, SymbolDbSchema.class);
            this.jsonDBTemplate.remove(result, SymbolDbSchema.class);
        } else {
            throw new JsonDBException(String.format("Deletion of id: %s is forbidden. It is still referenced by id: %s", id, references.get(0).getId()));
        }
    }

    /**
     * Destroy the collection used to store {@link SymbolDbSchema}.
     */
    public void dropDb() {
        this.jsonDBTemplate.dropCollection(SymbolDbSchema.class);
    }

    /**
     * Create an empty collection to store {@link SymbolDbSchema}.
     */
    public void createDb() {
        try {
            this.jsonDBTemplate.createCollection(SymbolDbSchema.class);
        } catch (InvalidJsonDbApiUsageException e) {
            // no operation
        }
    }

    private IOpposable fromSchema(SymbolDbSchema input) {
        if (!input.getEquivalenceIds().isEmpty()) {
            List<IOpposable> listEquivalence = new ArrayList<>();
            for (String equivalenceId : input.getEquivalenceIds()) {
                listEquivalence.add(this.read(equivalenceId));
            }

            return new CriticalSymbol(
                input.getId(),
                input.getOppositeId(),
                listEquivalence.toArray(new IOpposable[listEquivalence.size()]));

        }
        return new Symbol(input.getId(), input.getOppositeId());
    }

    private SymbolDbSchema toSchema(IOpposable input, List<String> listIds) {
        SymbolDbSchema schema = new SymbolDbSchema();
        schema.setId(input.getId());
        schema.setOppositeId(input.getOpposite());
        schema.setEquivalenceIds(listIds);
        return schema;
    }
}
