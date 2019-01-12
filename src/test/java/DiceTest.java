import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

class DiceTest {

    Dice dice;

    @BeforeEach
    void setUp() {
        dice = new Dice();
    }

    @AfterEach
    void tearDown() {
    }

    @Test()
    void mon_premier_test() {
        String result = dice.run(1);
        assertEquals("toto", result);
    }

    @Test
    void mon_deuxieme_test() {
        String result = dice.run(2);
        assertEquals("toto", result);
    }
}