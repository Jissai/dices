
package ant.dices;

import java.util.HashMap;

public class Dice {

    private HashMap<Integer,Face> faces;

    public Dice(HashMap<Integer,Face> faces) {
        this.faces = faces;
    }

    public HashMap<Integer, Face> getFaces() {
        return faces;
    }
}
