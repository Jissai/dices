package ant.dices;

public class Face {

    public static final Face BLANK = new Face(new Symbol[0]);

    private final IOpposable[] opposables;

    public Face(IOpposable... opposables) {
        this.opposables = opposables;
    }

    public IOpposable[] getOpposables() {
        return opposables;
    }
}
