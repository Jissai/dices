package ant.dices;

import java.util.HashMap;
import java.util.Map;

public class Dice {

    private Map<Integer, Face> faces;

    public Dice(Face[] faces) {
        this.faces = new HashMap<>();
        int index = 0;
        for (Face face : faces) {
            this.faces.put(index++, face);
        }

    }

    public Map<Integer, Face> getFaces() {
        return faces;
    }
}
