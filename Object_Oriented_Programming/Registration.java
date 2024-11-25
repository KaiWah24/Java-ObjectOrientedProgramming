import java.util.Date;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author KaiWah
 */
public class Registration {
    private int registrationID;
    private static int nextID = 1;

    private Date registrationDate;

    public int getRegistrationID() {
        return registrationID;
    }

    public static int getNextID() {
        return nextID;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationID(int registrationID) {
        this.registrationID = registrationID;
    }

    public static void setNextID(int nextID) {
        Registration.nextID = nextID;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void registerTournament(List<Team> teamList, Player player, List<Event> events, int userId, List<User> userList) {
        Scanner input = new Scanner(System.in);
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() == userId) {
                for (Event event : events) {
                    if (event.getStatus() == Status.CREATED) {
                        System.out.println(event);
                    }
                }
                System.out.println("Enter the index of the event to view Tournament: ");
                int select = input.nextInt();
                input.nextLine(); // Consume the newline character
                List<Tournament> tournaments = events.get(select - 1).getTournamentList();

                int teamSize = player.getTeam().getPlayers().size();

                System.out.println("Available Tournaments:");
                int choice = 1;
                for (Tournament t : tournaments) {
                    System.out.println(choice + ". " + t.toString());
                    choice++;
                }
                System.out.println("Please enter the index of the tournament to register:");
                int tournamentNum = input.nextInt();
                input.nextLine(); // Consume the newline character

                if (tournamentNum >= 1 && tournamentNum <= tournaments.size()) {
                    Tournament selectedTournament = tournaments.get(tournamentNum - 1);
                    if(player.getRegisteredTournament().contains(selectedTournament)){
                        System.out.println("You have already registered for this tournament.");
                    }
                    else if (selectedTournament.getSlots() != 0 && teamSize > 0) {
                        selectedTournament.setSlots(selectedTournament.getSlots() - 1);
                        System.out.printf("This is your registration Fee: RM %.2f%n", selectedTournament.getTournamentFee());
                        player.getRegisteredTournament().add(selectedTournament);
                        System.out.println("Confirm Registration? (Y/N) ");
                        char confirm = input.next().charAt(0);
                        if(Character.toUpperCase(confirm) == 'Y'){
                            double tournamentFee = selectedTournament.getTournamentFee();
                            String tournamentName = selectedTournament.getTournamentName();
                            int tournamentId = selectedTournament.getId();
                            String gameName = String.valueOf(selectedTournament.getGame());
                            int userID = player.getId();
                            String userName = player.getName();
                            String inGameName = player.getInGameName();
                            Date tournamentStartDate = selectedTournament.getStartDate();
                            Date registerDate = new Date();

                            int valid = Payment.registerDetails(userID,userName,inGameName,tournamentId,tournamentName,gameName,tournamentStartDate,registerDate,tournamentFee);
                            if(valid == 1){
                                String teamName = player.getTeam().getTeamName();
                                System.out.println(teamName);
                                // Add payment logic here

                                Team registeredTeam = player.getTeam();
                                System.out.println("Registration successful for tournament " + selectedTournament.getTournamentName());
                                selectedTournament.setTeamList(teamList);
                                System.out.println("Slots remaining: " + selectedTournament.getSlots());
                                System.out.println("Your Team: " + registeredTeam.getTeamName() + " Has Registered!");
                            }
                        }
                        else {
                            player.getRegisteredTournament().remove(selectedTournament);
                            System.out.println("Registration failed.");
                        }

                    } else {
                        System.out.println("Tournament is full or your team size is insufficient. Registration failed.");
                    }
                } else {
                    System.out.println("Invalid Tournament number. Registration failed.");
                }
            }
        }
    }


}



