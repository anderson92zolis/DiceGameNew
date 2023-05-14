package cat.dicegame.security.model.Service.AleatoryDiceMethod;

public class ExtraMethodsForRoller {

    public static int randomNumbers() {
        return (int) (Math.random() * 6) + 1;
    }

    public static String returnWinnerLost(int dice1, int dice2) {
        return ((dice1 + dice2) == 7) ? "WIN" : "LOST";
    }

}


