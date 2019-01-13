package ant.dice.test;

import ant.dice.Dice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class DiceTest {

    Dice dice;

    @BeforeEach
    public void setUp() {
        dice = new Dice();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    @DisplayName("should return 'toto' when given 1")
    public void run_given1_returnsToto() {
        String result = dice.run(1);
        assertEquals("toto", result);
    }

    @Test
    @DisplayName("should return 'toto' when given 2")
    public void run_given2_returnsToto() {
        String result = dice.run(2);
        assertEquals("toto", result);
    }
}