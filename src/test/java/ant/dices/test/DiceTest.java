package ant.dices.test;

import ant.dices.Dice;
import ant.dices.Face;
import ant.dices.Symbol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DiceTest {

    Symbol ga = new Symbol("ga");
    Face faceGa = new Face(new Symbol[]{ga});
    Dice dice = new Dice(new Face[]{
            Face.BLANK,
            faceGa
    });

    @Test
    @DisplayName("should return a Map wich reflect the order of array given in constructor ")
    void getFaces_returnsMap() {
        Map<Integer, Face> result = dice.getFaces();
        assertThat(result)
                .containsEntry(0, Face.BLANK)
                .containsEntry(1, faceGa);
    }

}