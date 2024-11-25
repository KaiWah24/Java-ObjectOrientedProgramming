import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author KaiWah
 */
public class Game {
    private int gameID;
    private String gameName;
    private String gameType;
    private static List<Game> gameList = new ArrayList<>();
    private List<Tournament> tournamentList;

    public Game() {
    }

    public Game(int gameID, String gameName, String gameType) {
        this.gameID = gameID;
        this.gameName = gameName;
        this.gameType = gameType;
    }

    public static void createGameList(){
        Game game1 = new Game(1,"Valorant", "FPS");
        Game game2 = new Game(2,"Counter-Strike 2", "FPS");
        Game game3 = new Game(3,"Apex Legends", "FPS");
        Game game4 = new Game(4,"League of Legend", "MOBA");
        Game game5 = new Game(5,"Dota 2", "MOBA");




        gameList.add(game1);
        gameList.add(game2);
        gameList.add(game3);
        gameList.add(game4);
        gameList.add(game5);
    }

    public static List<Game> getGameList() {
        return gameList;
    }

    public void addTournament(Tournament tournament){
        tournamentList.add(tournament);
    }

    public String getGameName() {
        return gameName;
    }

    public String getGameType() {
        return gameType;
    }


    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }


    public static void setGameList(List<Game> gameList) {
        Game.gameList = gameList;
    }

    @Override
    public String toString() {
        return String.format("Game ID: %-5d Game Name: %-20s Game Type: %-20s", this.gameID, this.getGameName(), this.gameType);
    }

    public static void displayGames(){
        for(Game game :gameList){
            System.out.println(game.toString());
        }
    }



}