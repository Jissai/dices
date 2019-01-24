package ant.dices;

import java.util.Objects;

public class AbstractSymbol implements IOpposable {

    private String id;
    private String opposite;

    protected AbstractSymbol(final String id, final String opposite) {
        this.id = id;
        this.opposite = opposite;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getOpposite() {
        return opposite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof AbstractSymbol)) {
            return false;
        }

        return Objects.equals(this.getId(), ((AbstractSymbol) o).getId());
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }
}
