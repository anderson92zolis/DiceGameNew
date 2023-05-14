package cat.dicegame.security.model.Service.AleatoryDiceMethod;

public class extraMethodsForRoller {

    public static int randomNumbers() {
        return (int) (Math.random() * 6) + 1;
    }

    public static String returnWinnerLost(int dice1, int dice2) {
        return ((dice1 + dice2) == 7) ? "WIN" : "LOST";
    }


    /*public static List<PlayerDto> averageSuccessRate(List<PlayerDto> playerDtoList) {

        Double resultNumberSuccess= null;
        Double resultNumberLoser= null;
        String result = null;

        if (playerDtoList.isEmpty()) {
            result = "you need play to see the averageSuccessRate";
        } else {
            for (PlayerDto playerDto : playerDtoList) {

                List<RollDto> listRollDto = playerDto.getRollsList();

                if (listRollDto.isEmpty()) {
                    result = "You  haven't played any game";
                    playerDto.setAverageSuccessRate(result);
                } else {
                    double countSucess = listRollDto.stream().filter(roll -> roll.getResult().equalsIgnoreCase("WIN")).count();
                    resultNumberSuccess = countSucess / listRollDto.size() * 100;
                    resultNumberLoser = 100 - resultNumberSuccess;
                    result = "Your  average success rate is " + resultNumberSuccess+ " % porcentage";

                    playerDto.setAverageLoserRateNumber(resultNumberLoser);
                    playerDto.setAverageSuccessRateNumber(resultNumberSuccess);

                    playerDto.setAverageSuccessRate(result);

                }
            }
        }
        return playerDtoList;
    }

     */

    /*
    THE METHODS UP IT IS WOING TO RETURN A UPDATE LIST
    public static void averageSuccessRate(List<PlayerDto> playerDtoList) {

        Double resultNumberSuccess= null;
        Double resultNumberLoser= null;
        String result = null;


        if (playerDtoList.isEmpty()) {
            result = "you need play to see the averageSuccessRate";
        } else {
            for (PlayerDto playerDto : playerDtoList) {

                List<RollDto> listRollDto = playerDto.getRollsList();

                if (listRollDto.isEmpty()) {
                    result = "You  haven't played any game";
                    playerDto.setAverageSuccessRate(result);
                } else {
                    double countSucess = listRollDto.stream().filter(roll -> roll.getResult().equalsIgnoreCase("WIN")).count();
                    resultNumberSuccess = countSucess / listRollDto.size() * 100;
                    resultNumberLoser = 100 - resultNumberSuccess;
                    result = "Your  average success rate is " + resultNumberSuccess+ " % porcentage";

                    playerDto.setAverageLoserRateNumber(resultNumberLoser);
                    playerDto.setAverageSuccessRateNumber(resultNumberSuccess);

                    playerDto.setAverageSuccessRate(result);
                }
            }
        }
    }

     */


    /*
    public static void averageSuccessRateGetONE(PlayerDto playerDto) {

        List<RollDto> rollsListDto = playerDto.getRollsList();
        Double resultNumberSuccess= null;
        String result = null;

        if (rollsListDto.isEmpty()) {
            result = "You  haven't played any game";
            playerDto.setAverageSuccessRate(result);
        } else {
            double countSucess = rollsListDto.stream().filter(roll -> roll.getResult().equalsIgnoreCase("WIN")).count();
            resultNumberSuccess = countSucess / rollsListDto.size() * 100;
            result = "Your  average success rate is " + resultNumberSuccess+ " % porcentage";
            playerDto.setAverageSuccessRate(result);
        }
    }

     */


    /*
    public static RankingDto averageSuccessRanking(List<PlayerDto> playerDtoList) {

        Double sumOfPlayerThatHavePlayedSuccessRanking = 0.0;
        Double averageSuccessRanking= 0.0;

        String result;
        RankingDto rankingDto = new RankingDto();
        
        if (playerDtoList.isEmpty()) {
            result = "you need play to see the averageSuccessRate";
        } else {
            for (PlayerDto playerDto : playerDtoList) {
                List<RollDto> listRollDto = playerDto.getRollsList();

                if (!listRollDto.isEmpty()) {

                    averageSuccessRanking += playerDto.getAverageSuccessRateNumber();
                    sumOfPlayerThatHavePlayedSuccessRanking++;
                }
                rankingDto.setOverageRankingAllPlayer(averageSuccessRanking/sumOfPlayerThatHavePlayedSuccessRanking );
            }
        }
        return rankingDto;
    }


     */


    /*
    public static RankingDto HighestLoserRateMethod(List<PlayerDto> playerDtoList) {



        PlayerDto minPlayerWithTheWorstSucessRate = null;

        String result;

        RankingDto rankingDto = new RankingDto();

        if (playerDtoList.isEmpty()) {
            result = "you need play to see the averageSuccessRate";
        } else {

            //     Powerful Comparison with Lambdas   https://www.baeldung.com/java-8-sort-lambda
            //     Finding Max/Min of a List or Collection      https://www.baeldung.com/java-collection-min-max
            // not usign but can serve https://www.baeldung.com/java-8-comparator-comparing

            minPlayerWithTheWorstSucessRate = playerDtoList
                    .stream()
                    .min(Comparator.comparing(PlayerDto::getAverageLoserRateNumber))
                    .orElseThrow(NoSuchElementException::new);

            rankingDto.setPlayerWithTheWorstLossPorcentage(minPlayerWithTheWorstSucessRate);
        }

        rankingDto.setPlayerWithTheWorstLossPorcentage(minPlayerWithTheWorstSucessRate);
        return rankingDto;
    }

     */
}


