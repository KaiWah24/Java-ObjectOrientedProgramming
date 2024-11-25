import java.util.List;
import java.util.Scanner;


/**
 * @author chink
 */
public abstract class User implements Report {
    //variable
    private int id = 0;
    private String name;
    private String email;
    private String password;
    private String phone;
    private int age;
    private String gender;
    private String role;
    private static int accountCount = 0;

    //constructor
    public User() {
    }

    protected User(String name, String email, String password, String phone, int age, String gender, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
        this.role = role;
        this.id = ++accountCount;
    }

    protected User(String name, String email, String password, String phone, int age, String gender) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
    }

    protected User(int id, String name, String email, String password, String phone, int age, String gender, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
        this.role = role;
    }

    //getter
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getRole() {
        return role;
    }

    public static int getAccountCount() {
        return accountCount;
    }


    //setter
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static void setAccountCount(int accountCount) {
        User.accountCount = accountCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //login
    public static void login(List<User> userList, List<Booking> bookings, List<Event> events, List<Tournament> tournamentList, List<Game> gameList, Team team, Registration registration, Player player, List<Team> teamList, List<Player> players) {
        Scanner input = new Scanner(System.in);
        int loginId = 0;
        char yesNo = ' ';
        User loggedInUser = null;

        do {
            boolean validEmail = false;
            boolean validPw = false;
            System.out.println("Log In");
            System.out.print("Enter email: ");
            String email = input.nextLine();

            System.out.print("Enter Password: ");
            String password = input.nextLine();

            for (int i = 0; i < userList.size(); i++) {
                if (email.equals(userList.get(i).getEmail())) {
                    validEmail = true;
                }
                if (password.equals(userList.get(i).getPassword())) {
                    validPw = true;
                    loginId = userList.get(i).getId();
                    loggedInUser = userList.get(i);
                }

            }
            if (validEmail && validPw) {
                loggedInUser.printWelcomeMessage();
                yesNo = 'n';
                if (loggedInUser instanceof Audiences) {
                    ((Audiences) loggedInUser).accountMenu(userList, events, bookings, loginId);
                } else if (loggedInUser instanceof Organizer) {
                    ((Organizer) loggedInUser).displayMenu(loggedInUser, events, tournamentList);
                } else if (loggedInUser instanceof Player) {
                    Player.showPlayerMenu(userList, tournamentList, events, gameList, team, registration, loginId, (Player) loggedInUser, teamList, players);

                }
            }

            if (!validEmail) {
                System.out.println("Invalid Account");
                System.out.print("Enter again? (y=yes/n=no): ");
                yesNo = input.next().charAt(0);
            }

            if (validEmail && !validPw) {
                System.out.println("Invalid password");
                System.out.print("Enter again? (y=yes/n=no): ");
                yesNo = input.next().charAt(0);
            }

            input.nextLine();

        } while (Character.toUpperCase(yesNo) != 'N');
    }

    public abstract void signUp(List<User> userList);

    //run time Polymorphism aka methodOverriding
    public void printWelcomeMessage() {
        System.out.println("Welcome, " + this.getName() + "! You are logged in as a " + this.getRole() + ". Explore and interact!");
    }

    @Override
    public String toString() {
        return "Name: " + name +
                "\nEmail: " + email +
                "\nPassword: " + password +
                "\nPhone: " + phone +
                "\nAge: " + age +
                "\nGender: " + gender +
                "\nRole:" + role;

    }


}