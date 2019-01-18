package ant.dices;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FaceTest {
    @Test
    @DisplayName("should return the symbols present on the face")
    void get_symbols() {

        Face face = new Face(new Symbol[]{
                new Symbol("success", "failure"),
                new Symbol("failure", "success")}
        );

        Symbol[] result =   face.getSymbols();
        assertThat(result).containsExactlyInAnyOrder(new Symbol("failure"),
                new Symbol("success"));
    }

    @Test
    @DisplayName("should return empty list if no Symbol defined on the face")
    void get_retunsEmpty(){
        Symbol[] result =   Face.BLANK.getSymbols();
        assertThat(result).isEmpty();
    }
}