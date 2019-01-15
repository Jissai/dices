package ant.dice;

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
}
