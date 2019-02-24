package ant.dices.dal;

import ant.dices.CriticalSymbol;
import ant.dices.IOpposable;
import ant.dices.IOpposableEquivalence;
import ant.dices.Symbol;
import ant.dices.dal.schema.SymbolDbSchema;
import io.jsondb.InvalidJsonDbApiUsageException;
import io.jsondb.JsonDBTemplate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SymbolDao {

    private final JsonDBTemplate jsonDBTemplate;

    public SymbolDao(JsonDBTemplate jsonDBTemplate) {
        this.jsonDBTemplate = jsonDBTemplate;
        try {
            this.jsonDBTemplate.createCollection(SymbolDbSchema.class);
        } catch (InvalidJsonDbApiUsageException e) {
        }
    }

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

    public Collection<IOpposable> list(){
        List<SymbolDbSchema> results =  this.jsonDBTemplate.findAll(SymbolDbSchema.class);
        return results.stream().map(result -> this.fromSchema(result)).collect(Collectors.toList());
    }

    public void delete(String id) {
        SymbolDbSchema result = this.jsonDBTemplate.findById(id, SymbolDbSchema.class);
        this.jsonDBTemplate.remove(result, SymbolDbSchema.class);
    }

    public void drop() {
        this.jsonDBTemplate.dropCollection(SymbolDbSchema.class);
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
