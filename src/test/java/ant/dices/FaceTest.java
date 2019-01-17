package ant.dices;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class FaceTest {
    @Test
    @DisplayName("should return the symbols present on the face")
    public void get_symbols() {

        Face face = new Face(new Symbol[]{
                new Symbol("success", "failure"),
                new Symbol("failure", "success")}
        );

        Symbol[] result =   face.getSymbols();
        assertThat(result).containsExactlyInAnyOrder(new Symbol[]{
                new Symbol("failure","success"),
                new Symbol("success","failure")});
    }
}