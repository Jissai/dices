package ant.dices;

import java.util.Objects;

public class Symbol {
    private String id;
    private String opposite;

    public Symbol(final String id) {
        this(id, "");
    }

    public Symbol(final String id, final String opposite) {
        this.id = id;
        this.opposite = opposite;
    }

    public String getId() {
        return id;
    }

    public String getOpposite() {
        return opposite;
    }

    public boolean isOpposite(Symbol target) {
        return target.getId() == this.opposite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Symbol s = (Symbol)o;

        return Objects.equals(this.id, s.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
