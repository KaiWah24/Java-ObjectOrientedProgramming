import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * @author kxiao
 */
public class Organizer extends User {

    //variable
    private int yearOfExp;
    private int ttlEventOrganized;

    //constructor
    public Organizer(String name, String email, String password, String phone, int age, String gender, String role,int yearOfExp) {
        super(name, email, password, phone, age, gender, role);
        this.yearOfExp = yearOfExp;
    }

    public Organizer() {
    }

    @Override
    public void signUp(List<User> userList) {
    }

    //getter
    public int getYearOfExp() {
        return yearOfExp;
    }

    public int getTtlEventOrganized() {
        return ttlEventOrganized;
    }

    //setter
    public void setYearOfExp(int yearOfExp) {
        this.yearOfExp = yearOfExp;
    }

    public void setTtlEventOrganized(int ttlEventOrganized) {
        this.ttlEventOrganized = ttlEventOrganized;
    }

    public void displayMenu(User user, List<Event> events, List<Tournament> tournaments) {
        Tournament tournament = new Tournament();
        Scanner input = new Scanner(System.in);
        int choice =0;
        do {
            try {
                System.out.println("\nMenu");
                System.out.println("1. Create Event");
                System.out.println("2. Edit Event");
                System.out.println("3. Delete Event");
                System.out.println("4. Display Event");
                System.out.println("5. Search Event");
                System.out.println("6. Generate report");
                System.out.println("7. Generate all event report");
                System.out.println("8. Manage Tournament");
                System.out.println("9. Log Out");
                System.out.print("\nEnter choice:");
                choice = input.nextInt();

                switch (choice) {
                    case 1:
                        Event.createEvent(user, events);
                        break;
                    case 2:
                        Event.editEvent(events);
                        break;
                    case 3:
                        Event.deleteEvent(events);
                        break;
                    case 4:
                        Event.displayEvent(events);
                        break;
                    case 5:
                        Event.searchByEventName(events);
                        break;
                    case 6:
                        Event.getReport(events);
                        break;
                    case 7:
                        Event.generateAllEventsReport(events);
                        break;
                    case 8:
                        //Check if there are no events with a status of CREATED
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

                        tournament.displayTournamentMenu(user,events,tournaments);
                        break;
                    case 9:
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid choices entered");
                }
            } catch(InputMismatchException inputMismatchException){
                System.out.println("Invalid characters entered");
                input.nextLine();
            }
        } while (choice != 9);
    }
    @Override
    public void printWelcomeMessage(){
        System.out.println("Hello " + this.getName() + "! Today is " + LocalDate.now());
    }

    @Override
    public void generateReport() {

    }
}