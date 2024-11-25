import java.util.*;

/**
 *
 * @author KaiWah
 */
public class Team {
    //variable
    private String teamName;
    private String coachName;
    private List<Player> players;


    //getter
    public String getTeamName() {
        return teamName;
    }

    public String getCoachName() {
        return coachName;
    }

    public List<Player> getPlayers() {
        return players;
    }

    //setter
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    private static Set<String> existingTeamNames = new HashSet<>();

    private static int teamCount = 0;
    //constructor


    public Team() {
        this.players = new ArrayList<>();
    }



    public static int getTeamCount() {
        return teamCount;
    }

    //formTeam
    public void formTeam(Player newPlayer, int teamCount, int userId, List<User> userList, List<Team> teamList) {
        Scanner input = new Scanner(System.in);
        Team team = new Team();
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() == userId) { //new way to do: if i get the userID , i store into a new variable then whenever i want to use the userID to do smtg with, i use the variable to do!!!
                Player currentPlayer = (Player) userList.get(i);
                System.out.println("Welcome " + currentPlayer.getInGameName() + "!");

                //Input Team
                boolean validInputs = false;
                System.out.println("Form Team Processes..");
                while (!validInputs) {
                    System.out.print("Enter the team name: ");
                    teamName = input.nextLine();
                    if (teamName.isEmpty()) {
                        System.out.println("Invalid input. Team name cannot be empty.");
                    } else if (existingTeamNames.contains(teamName.toLowerCase())){
                        System.out.println("Team name already exists. Please try another name.");
                    }
                    else
                    {
                        validInputs = true;
                        existingTeamNames.add(teamName.toLowerCase());
                    }
                }

                validInputs = false;

                //Input Coach
                while (!validInputs) {
                    System.out.print("Enter the coach name: ");
                    coachName = input.nextLine();
                    if (coachName.isEmpty()) {
                        System.out.println("Invalid input. Coach name cannot be empty.");
                    } else {
                        validInputs = true;
                    }
                }

                team.setTeamName(teamName);
                team.setCoachName(coachName);

                teamCount++;
                this.teamCount = teamCount;

                int numPlayers;

                do {
                    System.out.print("Enter the number of players to add (2 to 6 players allowed): ");
                    numPlayers = input.nextInt();

                    if (numPlayers < 2 || numPlayers > 6) {
                        System.out.println("Invalid input. Please enter a number between 2 and 6.");
                    }
                } while (numPlayers < 2 || numPlayers > 6);

                //add leader
                team.getPlayers().add((Player) userList.get(i));
                input.nextLine();
                for (int u = 0; u < numPlayers; u++) {
                    System.out.println("Enter details for member " + (u + 1));

                    System.out.print("In-game name: ");
                    String inGameName = input.nextLine();
                    Player player = new Player(inGameName, teamCount);


                    //assign player to have team
                    player.setTeam(team);

                    //assign team player list to include player
                    team.getPlayers().add(player);

                }

                ((Player) userList.get(i)).setTeam(team);
                teamList.add(team);

                System.out.println("Team successfully formed!");
                displayTeam((Player) userList.get(i));

            }
        }
    }

    public void displayTeam(Player player) {
        System.out.println("--------------------------------------------------");
        System.out.println("                 Team Information");
        System.out.println("--------------------------------------------------");

        System.out.printf("%-15s | %-15s%n", "Team ID         ", teamCount);
        System.out.printf("%-15s | %-15s%n", "Leader name     ", player.getInGameName());
        System.out.printf("%-15s | %-15s%n", "Team Name       ", player.getTeam().getTeamName());
        System.out.printf("%-15s | %-15s%n", "Coach Name      ", player.getTeam().getCoachName());

        System.out.println("--------------------------------------------------");
        System.out.println("                 Player List");
        System.out.println("--------------------------------------------------");

        System.out.println("Player In Game Name");
        for (Player existingPlayer : player.getTeam().getPlayers()) {
            System.out.println("- " + existingPlayer.getInGameName());
        }

        System.out.println("--------------------------------------------------");
    }
}