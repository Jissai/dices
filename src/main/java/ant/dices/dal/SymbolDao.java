package ant.dices.dal;

import ant.dices.CriticalSymbol;
import ant.dices.IOpposable;
import ant.dices.IOpposableEquivalence;
import ant.dices.Symbol;
import ant.dices.dal.schema.SymbolDbSchema;
import io.jsondb.InvalidJsonDbApiUsageException;
import io.jsondb.JsonDBTemplate;
import java.util.ArrayList;
import java.util.List;

public class SymbolDao {

    private final JsonDBTemplate jsonDBTemplate;

    public SymbolDao(JsonDBTemplate jsonDBTemplate) {
        this.jsonDBTemplate = jsonDBTemplate;
        try {
            this.jsonDBTemplate.createCollection(SymbolDbSchema.class);
        } catch (InvalidJsonDbApiUsageException e) {
        }
    }

    public void create(IOpposable opposable) {
        SymbolDbSchema schema = new SymbolDbSchema();
        schema.setId(opposable.getId());
        schema.setOppositeId(opposable.getOpposite());

        List<String> listIds = new ArrayList<>();
        if (opposable instanceof IOpposableEquivalence) {
            for (IOpposable equivalence : ((IOpposableEquivalence) opposable).getOpposableEquivalence()) {
                this.create(equivalence);
                listIds.add(equivalence.getId());
            }
        }

        schema.setEquivalenceIds(listIds);
        this.jsonDBTemplate.upsert(schema);

    }

    public IOpposable read(String id) {
        SymbolDbSchema result = this.jsonDBTemplate.findById(id, SymbolDbSchema.class);

        if (result.getEquivalenceIds().size() != 0) {
            List<IOpposable> listEquivalence = new ArrayList<>();
            for (String equivalenceId : result.getEquivalenceIds()) {
                listEquivalence.add(read(equivalenceId));
            }
            return new CriticalSymbol(
                result.getId(),
                result.getOppositeId(),
                listEquivalence.toArray(new IOpposable[listEquivalence.size()]));

        } else {
            return new Symbol(result.getId(), result.getOppositeId());
        }
    }

    public void update() {
    }

    public void delete() {
    }

    public void drop() {
        this.jsonDBTemplate.dropCollection(SymbolDbSchema.class);
    }
}
