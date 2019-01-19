package ant.dices.test;

import static org.assertj.core.api.Assertions.*;

import ant.dices.Dice;
import ant.dices.Face;
import ant.dices.Symbol;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class DiceTest {

    Symbol ga = new Symbol("ga");
    Face faceGa = new Face(new Symbol[] {ga});

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
    @DisplayName("getNumberOfFaces")
    public class GetNumberOfFaces {
        @Test
        @DisplayName("should return 0 given a null Dice")
        void getNumberOfFaces_returns0_givenNullDice() {
            assertThat((new Dice(null)).getNumberOfFaces()).isEqualTo(0);
        }

        @Test
        @DisplayName("should return 0 given an empty Dice")
        void getNumberOfFaces_returns0_givenEmptyDice() {
            assertThat((new Dice(null)).getNumberOfFaces()).isEqualTo(0);
        }

        @Test
        @DisplayName("should return the correct numbr of faces given a Dice")
        void getNumberOfFaces_returnsNumberOfFaces_givenDice() {
            assertThat((new Dice(new Face[] {Face.BLANK, Face.BLANK})).getNumberOfFaces()).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("getSymbolsOnFace")
    public class GetSymbolsOnFace {
        Symbol failure = new Symbol("failure", "success");
        Symbol menace = new Symbol("menace", "avantage");
        Face face1 = new Face(new Symbol[] {failure});
        Face face2 = new Face(new Symbol[] {failure, menace});
        Dice dice = new Dice(new Face[] {Face.BLANK, face1, face2, Face.BLANK, face1, face2,});

        @Test
        @DisplayName("should return expected list of symbols for an existing given Face")
        void getSymbolsOfFaces_returns_symbols() {
            assertThat(dice.getSymbolsOnFace(2)).hasSize(2).containsExactlyInAnyOrder(face2.getSymbols());
            assertThat(dice.getSymbolsOnFace(3)).hasSize(0).containsExactlyInAnyOrder(Face.BLANK.getSymbols());
        }

        @Test
        @DisplayName("should return the symbols on abs(face % dice.getNumberOfFaces) when given a face beyond limits")
        void getSymbolsOfFaces_returnsSymbols_givenOutOfRange() {
            assertThat(dice.getSymbolsOnFace(7)).hasSize(1).containsExactlyInAnyOrder(face1.getSymbols());
            assertThat(dice.getSymbolsOnFace(-3)).hasSize(0).containsExactlyInAnyOrder(Face.BLANK.getSymbols());
            assertThat(dice.getSymbolsOnFace(-44)).hasSize(2).containsExactlyInAnyOrder(face2.getSymbols());
        }
    }

    @Nested
    @DisplayName("getFaces")
    public class GetFaces {
        @Test
        @DisplayName("should return a Map which reflects the order of array given in constructor ")
        void getFaces_returnsMap() {
            Dice dice = new Dice(new Face[] {
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

        @Test
        @DisplayName("should not allow modification of faces")
        void getFaces_unmodifiable() {
            Dice dice = new Dice(new Face[0]);
            Map<Integer, Face> result = dice.getFaces();
            assertThatThrownBy(() -> result.put(1, Face.BLANK)).isInstanceOf(RuntimeException.class);
        }

    }

}