import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;


/**
 *
 * @author KaiWah
 */
public class Player extends User{
    private String inGameName;
    private Team team;
    private int teamCount = 0;

    private Set<Tournament> registeredTournament;



    public Player(String inGameName, int teamCount) {
        this.inGameName = inGameName;
        this.teamCount = teamCount;
    }

    public Player(String name, String email, String password, String phone, int age, String gender, String role, String inGameName) {
        super(name, email, password, phone, age, gender, role);
        this.inGameName = inGameName;
        registeredTournament = new HashSet<>();
    }
    public Player(){
    }

    //Getter
    public Team getTeam() {
        return team;
    }
    public String getInGameName() {
        return inGameName;
    }

    //Setter
    public void setTeam(Team team) {
        this.team = team;
    }

    public void setInGameName(String inGameName) {

        this.inGameName = inGameName;
    }
    public List<Team> teamList;

    //signUpAsPlayer

    public Set<Tournament> getRegisteredTournament() {
        return registeredTournament;
    }

    public void signUp(List<User> userList) {
        Scanner input = new Scanner(System.in);
        char yesNo;
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[\\a-zA-Z]{2,6}";
        Pattern pattern = Pattern.compile(regex);
        String regex2 = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
        Pattern p = Pattern.compile(regex2);
        Pattern p2 = Pattern.compile("\\d{3}-\\d{7}");

        System.out.println("Signing as Player");

        System.out.print("Enter name: ");
        String userName = input.nextLine();

        while(userName.isEmpty() == true){
            System.out.print("\nName field is empty please enter again!");
            System.out.print("\nRe-Enter name: ");
            userName = input.nextLine();
        }

        System.out.print("Enter Email (user@gmail.com):");
        String userEmail = input.nextLine();
        while(userEmail.isEmpty() == true || pattern.matcher(userEmail).matches()!= true){
            if(userEmail.isEmpty() == true){
                System.out.print("\nEmail field is empty please enter again!");
                System.out.print("\nRe-Enter Email (user@gmail.com):");
                userEmail = input.nextLine();

            }else if(pattern.matcher(userEmail).matches()!= true){
                System.out.print("\nIncorrect Email format please follow this format (user123@gamil.com)");
                System.out.print("\nRe-Enter Email (user@gmail.com):");
                userEmail = input.nextLine();
            }
        }

        System.out.print("Enter Password (User12@):");
        String userPassword = input.nextLine();
        p.matcher(userPassword).matches();
        while(userPassword.isEmpty() == true || p.matcher(userPassword).matches()!= true){
            if(userPassword.isEmpty() == true){
                System.out.print("\nPassword field is empty please enter again!");
                System.out.print("\nRe-Enter Password (User12@):");
                userPassword = input.nextLine();
            }else if(p.matcher(userPassword).matches()!= true){
                System.out.print("\nPassword must contain one digit, one lowercase alphabet, one uppercase alphabet");
                System.out.print("and one special character and at least 8 characters and at most 20 characters.");

                System.out.print("\nRe-Enter Password (User12@):");
                userPassword = input.nextLine();

            }
        }

        System.out.print("Enter Phone number (017-1234567):");
        String userPhoneNum = input.nextLine();
        while(userPhoneNum.isEmpty() == true || p2.matcher(userPhoneNum).matches()!= true){
            if(userPhoneNum.isEmpty() == true){
                System.out.print("\nPhone Number field is empty please enter again!");
                System.out.print("\nRe-Enter Phone number (017-1234567):");
                userPhoneNum = input.nextLine();

            }else if(p2.matcher(userPhoneNum).matches()!= true){
                System.out.print("\nWrong phone format please follow this format 017-1234567");
                System.out.print("\nRe-Enter Phone number (017-1234567):");
                userPhoneNum = input.nextLine();
            }
        }

        System.out.print("Enter Age :");
        int userAge = input.nextInt();
        while(userAge <= 13 || userAge >=100 ){
            System.out.print("\nAge field cannot enter less than 13 and more than 100!");
            System.out.print("\nRe-Enter Age :");
            userAge = input.nextInt();

        }
        //clear buffer
        input.nextLine();

        System.out.print("Enter Gender (M = Male / F = Female) :");
        String userGender = input.nextLine();
        while(userGender.isEmpty() == true){
            System.out.print("\nGender field is empty please enter again!");
            System.out.print("\nRe-Enter Gender (M = Male / F = Female) :");
            userGender = input.nextLine();
        }
        while(!userGender.equals("M")  && !userGender.equals("F") && !userGender.equals("m") && !userGender.equals("f")){
            System.out.print("\nInvalid input please try to enter (M / F)");
            System.out.print("\nRe-Enter Gender (M = Male / F = Female) :");
            userGender = input.nextLine();
        }
        if(userGender.equals("M") || userGender.equals("m")){
            userGender = "Male";
        }else if(userGender.equals("F") || userGender.equals("f")){
            userGender ="Female";
        }

        System.out.print("Confirm sign up?");
        yesNo = input.next().charAt(0);

        if (Character.toUpperCase(yesNo) == 'Y') {
            //check email
            for (int i = 0; i < userList.size(); i++) {
                if (userEmail.equals(userList.get(i).getEmail())) {
                    System.out.println("Account registered with email: " + userList.get(i).getEmail());
                    break;
                }
            }
        } else {
            System.out.println("Sign-up canceled.");
            return;
        }
        System.out.println("Please enter your In Game Name to finish this registration.");
        //clear buffer
        input.nextLine();
        System.out.print("Enter your in-game name: ");
        String iGn = input.nextLine();
        User player = new Player(userName, userEmail, userPassword, userPhoneNum, userAge, userGender, "Player",iGn);
        userList.add(player);
        System.out.println("Sign-up successful.");
    }



    public static void showPlayerMenu(List<User> userList, List<Tournament> tournamentList, List<Event> events, List<Game> gameList, Team team, Registration registration, int userId, Player player, List<Team> teamList, List<Player> players){
        Scanner input = new Scanner(System.in);
        int choice;

        do{
            System.out.println("\nPlayer Menu");
            System.out.println("1. Form Team");
            System.out.println("2. Register Tournaments");
            System.out.println("3. View Available Events ");
            System.out.println("4. View Available Tournaments");
            System.out.println("5. View Games ");
            System.out.println("6. View Team Information");
            System.out.println("7. Log Out");
            System.out.println("Enter choice:");
            choice =input.nextInt();

            switch (choice){
                case 1:
                    if(player.getTeam() == null) {
                        team.formTeam(player, Team.getTeamCount(), userId, userList, teamList);
                    } else{
                        System.out.println("You already have a team!");
                    }
                    break;
                case 2 :
                    boolean noCreatedEvents = true;
                    for (Event event : events) {
                        if (event.getStatus() == Status.CREATED) {
                            noCreatedEvents = false;
                            break;
                        }
                    }
                    if (noCreatedEvents){
                        System.out.println("No Event are available");
                        break;
                    }

                    //check is there any tournament
                    boolean noTournaments = true;
                    for(Event event : events){
                        if(event.getTournamentList() != null){
                            noTournaments = false;
                            break;
                        }
                    }
                    if (noTournaments){
                        System.out.println("No Tournaments are available");
                        break;
                    }

                    if(player.getTeam() != null) {
                        registration.registerTournament(teamList, player, events, userId, userList);
                    } else{
                        System.out.println("You are not in any team.");
                        System.out.println("Please form a team before registering tournament!");
                    }
                    break;
                case 3 :
                    boolean noCreatedEvents2 = true;
                    for (Event event : events) {
                        if (event.getStatus() == Status.CREATED) {
                            noCreatedEvents2 = false;
                            break;
                        }
                    }
                    if (noCreatedEvents2){
                        System.out.println("No Event are available");
                        break;
                    }
                    Event.displayEvent(events);
                    break;
                case 4:
                    //check is there any tournament
                    boolean noTournaments2 = true;
                    for(Event event : events){
                        if(event.getTournamentList() != null){
                            noTournaments2 = false;
                            break;
                        }
                    }
                    if (noTournaments2){
                        System.out.println("No Tournaments are available");
                        break;
                    }
                    Tournament.printTournaments(tournamentList);
                    break;
                case 5 :
                    Game.displayGames();
                    break;
                case 6 :
                    //Display team details
                    if(player.getTeam() !=null){
                        player.getTeam().displayTeam(player);
                    } else{
                        System.out.println("You are not in any team.");
                        System.out.println("Please form a team before viewing team information!");
                    }
                    break;
                case 7:
                    System.out.println("Logging out...");
                    break;
                default: System.out.println("Invalid choice, Please enter again");
                    break;

            }
        }while (choice!=7);
    }

    @Override
    public void generateReport() {

    }
}