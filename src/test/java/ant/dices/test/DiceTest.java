package ant.dices.test;

import ant.dices.Dice;
import ant.dices.Face;
import ant.dices.Symbol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;


public class DiceTest {

    Symbol ga = new Symbol("ga");
    Face faceGa = new Face(new Symbol[]{ga});

    @Nested
    @DisplayName("constructor")
    public class TestConstructor {

        @Test
        @DisplayName("should not fail if constructor is given null")
        void ctor_givenNull() {
            assertThatCode(() -> new Dice(null)).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("should not fail if constructor is given empty arrray")
        void ctor_givenEmpty() {
            assertThatCode(() -> new Dice(new Face[0])).doesNotThrowAnyException();
        }


    }

    @Nested
    @DisplayName("getFaces")
    public class GetFaces {
        @Test
        @DisplayName("should return a Map which reflects the order of array given in constructor ")
        void getFaces_returnsMap() {
            Dice dice = new Dice(new Face[]{
                    Face.BLANK,
                    faceGa
            });
            Map<Integer, Face> result = dice.getFaces();
            assertThat(result)
                    .containsEntry(0, Face.BLANK)
                    .containsEntry(1, faceGa);
        }

        @Test
        @DisplayName("should return a Map which reflects the order of array given in constructor ")
        void getFaces_returnsEmptyMap() {
            Dice dice = new Dice(new Face[0]);
            Map<Integer, Face> result = dice.getFaces();
            assertThat(result).isEmpty();
        }

    }

}