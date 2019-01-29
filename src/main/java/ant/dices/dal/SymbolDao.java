package ant.dices.dal;

import ant.dices.IOpposable;
import ant.dices.IOpposableEquivalence;
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

    public void read() {
    }

    public void update() {
    }

    public void delete() {
    }


}
