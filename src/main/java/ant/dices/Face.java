package ant.dices;

public class Face {
    private final Symbol[] symbols;

    public Face(Symbol[] symbols) {
        this.symbols = symbols;
    }

    public Symbol[] getSymbols() {
        return symbols;
    }
}
