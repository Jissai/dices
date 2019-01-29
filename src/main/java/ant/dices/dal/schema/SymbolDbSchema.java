package ant.dices.dal.schema;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;
import java.util.List;

@Document(collection = "symbols", schemaVersion = "1.0")
public class SymbolDbSchema {

    @Id
    private String id;
    private String oppositeId;
    private List<String> equivalenceIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOppositeId() {
        return oppositeId;
    }

    public void setOppositeId(String oppositeId) {
        this.oppositeId = oppositeId;
    }

    public List<String> getEquivalenceIds() {
        return equivalenceIds;
    }

    public void setEquivalenceIds(List<String> equivalenceIds) {
        this.equivalenceIds = equivalenceIds;
    }
}
