package ant.dices;

class Face {

    static final Face BLANK = new Face(new Symbol[0]);

    private final Symbol[] symbols;

    Face(Symbol[] symbols) {
        this.symbols = symbols;
    }

    Symbol[] getSymbols() {
        return symbols;
    }
}
