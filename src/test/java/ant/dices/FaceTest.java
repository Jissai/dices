package ant.dices;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FaceTest {
    @Test
    @DisplayName("should return the symbols present on the face")
    void get_symbols() {

        Face face = new Face(
            new Symbol("success", "failure"),
            new Symbol("failure", "success")
        );

        IOpposable[] result = face.getOpposables();
        assertThat(result).containsExactlyInAnyOrder(new Symbol("failure"),
            new Symbol("success"));
    }

    @Test
    @DisplayName("should return empty list if no Symbol defined on the face")
    void get_retunsEmpty() {
        IOpposable[] result = Face.BLANK.getOpposables();
        assertThat(result).isEmpty();
    }
}
