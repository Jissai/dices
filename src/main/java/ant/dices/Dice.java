package ant.dices;

import java.util.*;
import java.util.stream.IntStream;

public class Dice {

    private Map<Integer, Face> faces = new HashMap<>();

    public Dice(final Face... faces) {
        Face[] treat = faces != null ? faces : new Face[] {};
        IntStream.range(0, treat.length).forEachOrdered(idx -> this.faces.put(idx, treat[idx]));
    }

    public int getNumberOfFaces() {
        return this.faces.size();
    }

    public Map<Integer, Face> getFaces() {
        return Collections.unmodifiableMap(this.faces);
    }

    public Collection<Symbol> getSymbolsOnFace(int face) {
        int actualFace = Math.round(Math.abs(face) % this.getNumberOfFaces());
        return Arrays.asList(this.faces.get(actualFace).getSymbols());
    }
}
