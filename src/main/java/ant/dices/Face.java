package ant.dices;

public class Face {

    public static final Face BLANK = new Face(new Symbol[0]);

    private final Symbol[] symbols;

    public Face(Symbol[] symbols) {
        this.symbols = symbols;
    }

    Symbol[] getSymbols() {
        return symbols;
    }
}
