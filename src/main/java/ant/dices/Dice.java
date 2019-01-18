package ant.dices;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Dice {

    private Map<Integer, Face> faces = new HashMap<>();

    public Dice(final Face[] faces) {
        Face[] treat = faces != null ? faces : new Face[]{};
        IntStream.range(0, treat.length).forEachOrdered(idx -> this.faces.put(idx, treat[idx]));
    }

    public Map<Integer, Face> getFaces() {
        return this.faces;
    }
}
