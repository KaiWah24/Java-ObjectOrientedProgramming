import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static final Scanner input = new Scanner(System.in);
    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
    public static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH);
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<Venue> venueList = new ArrayList<>();

        //object
        Audiences audience = new Audiences();
        Player player = new Player();
        Registration registration = new Registration();
        Team team = new Team();

        //list
        List<User> userList = new ArrayList<>();
        List<Event> eventList = new ArrayList<>();
        List<Booking> bookList = new ArrayList<>();
        List<Tournament> tournamentList = new ArrayList<>();
        List<Game> gameList = new ArrayList<>();
        List<Team> teamList = new ArrayList<>();
        List<Player> players = new ArrayList<>();

        Game.createGameList();

        Venue.createVenueList();
        //data
        User user1 = new Organizer("Mk", "1", "1", "011-12345678", 12, "Male", "Organizer", 10);
        User user2 = new Audiences("mk", "2", "2", "011-36759548", 15, "Male", "Audiences");
        userList.add(user1);
        userList.add(user2);

        int[] vip = new int[10];
        Arrays.fill(vip, 0);

        int[] normal = new int[50];
        Arrays.fill(normal, 0);

        Event event2 = new Event(
                1, // id
                "testerEvent", // eventName
                "tester2", // eventDescription
                LocalDate.of(2023, 9, 23), // startDate
                LocalTime.now(), // startTime
                LocalDate.of(2023, 9, 24), // endDate (assuming it ends the next day, adjust as needed)
                LocalTime.now(), // endTime (assuming it ends at the same time it started, adjust as needed)
                user1, // user
                Venue.getVenueList().get(0), // venue
                Status.CREATED, // status
                LocalDateTime.now(), // createdAt
                "WASD", // promoCode
                vip, // vipSeats (assuming vip is defined elsewhere)
                new int[20], // normalSeats
                tournamentList, // tournamentList (assuming it is defined elsewhere in your code)
                80000.0, // vipPricePerTicket
                50.0, 10);// normalPricePerTicket

        eventList.add(event2);

        int choice;
        do {

            System.out.println("\nLog In Menu");
            System.out.println("1. Login");
            System.out.println("2. Sign up as Audience");
            System.out.println("3. Sign up as Player");
            System.out.println("4. Quit");
            System.out.print("\nEnter choice:");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    User.login(userList, bookList, eventList, tournamentList, gameList, team, registration, player, teamList, players);
                    break;
                case 2:
                    audience.signUp(userList);
                    break;
                case 3:
                    player.signUp(userList);
                    break;
                case 4:
                    break;
                default:
                    System.out.print("Invalid choice");
            }
        } while (choice != 4);


    }
}

