package cat.dicegame.security.model.Service.AleatoryDiceMethod;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class ExtraMethodsForRollerTest {

    @Test
    @DisplayName("Test Random Numbers")
    public void randomNumbersTest() {

        int randomNumber = ExtraMethodsForRoller.randomNumbers();
        Assertions.assertTrue(randomNumber >= 1 && randomNumber <= 6, "Random number is within the range of 1 to 6");
    }

    @Test
    @DisplayName("Test Return Winner or Lost")
    public void returnWinnerLostTest() {
        String result1 = ExtraMethodsForRoller.returnWinnerLost(3, 4);
        Assertions.assertEquals("WIN", result1, "Sum of dice is 7, should return 'WIN'");

        String result2 = ExtraMethodsForRoller.returnWinnerLost(3, 5);
        Assertions.assertEquals("LOST", result2, "Sum of dice is not 7, should return 'LOST'");
    }
}