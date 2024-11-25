import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @author Fungpin
 */
public class Event implements Report {
    private Integer id = 0;
    private String eventName;
    private String eventDescription;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private User user;
    private Venue venue;
    private Status status;
    private LocalDateTime createdAt;
    private String promoCode;
    private int discountRate;
    private static int eventCounter = 0;
    private int[] vipSeats;
    private int[] normalSeats;
    private List<Tournament> tournamentList;
    private double vipPricePerTicket;
    private double normalPricePerTicket;

    //main
    public Event() {
        eventCounter++;
    }

    //sampler only
    public Event(Integer id, String eventName, String eventDescription, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime, User user, Venue venue, Status status, LocalDateTime createdAt, String promoCode, int[] vipSeats, int[] normalSeats, List<Tournament> tournamentList, double vipPricePerTicket, double normalPricePerTicket, int discountRate) {
        this.id = id;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.user = user;
        this.venue = venue;
        this.status = status;
        this.createdAt = createdAt;
        this.promoCode = promoCode;
        this.vipSeats = vipSeats;
        this.normalSeats = normalSeats;
        this.tournamentList = tournamentList;
        this.vipPricePerTicket = vipPricePerTicket;
        this.normalPricePerTicket = normalPricePerTicket;
        this.discountRate = discountRate;
        eventCounter++;
    }

    public int getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(int discountRate) {
        this.discountRate = discountRate;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public List<Tournament> getTournamentList() {
        return tournamentList;
    }

    public void setTournamentList(List<Tournament> tournamentList) {
        this.tournamentList = tournamentList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getVipPricePerTicket() {
        return vipPricePerTicket;
    }

    public void setVipPricePerTicket(double vipPricePerTicket) {
        this.vipPricePerTicket = vipPricePerTicket;
    }

    public double getNormalPricePerTicket() {
        return normalPricePerTicket;
    }

    public void setNormalPricePerTicket(double normalPricePerTicket) {
        this.normalPricePerTicket = normalPricePerTicket;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }


    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }
    public int[] getVipSeats() {
        return vipSeats;
    }

    public void setVipSeats(int[] vipSeats) {
        this.vipSeats = vipSeats;
    }

    public int[] getNormalSeats() {
        return normalSeats;
    }

    public void setNormalSeats(int[] normalSeats) {
        this.normalSeats = normalSeats;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    //create a list of events in main and pass a new arrayList events into the parameter
    public static boolean createEvent(User organizer, List<Event> events) {
        char yesNo = 'N';
        boolean valid = false;
        int choice;
        Event event = new Event();

        if (!(organizer instanceof Organizer)) {
            System.out.println("Unauthorized access");
            return false;
        }
        System.out.print("Please enter the event name : ");
        String eventName = Main.input.nextLine();

        event.setEventName(eventName);

        System.out.print("Please enter the event description : ");
        String eventDesc = Main.input.nextLine();
        event.setEventDescription(eventDesc);

        System.out.println("\n Venue Listing\n");
        for (Venue venue : Venue.getVenueList()) {
            System.out.println(venue.toString());
        }

        while (Character.toUpperCase(yesNo) == 'N') {

            //validation
            do {
                System.out.print("Which venue would you like to select: ");
                choice = Main.input.nextInt();

                if (choice < 1 || choice > Venue.getVenueList().size()) {
                    System.out.println("Invalid choices, please try again");
                }

            } while (choice < 1 || choice > Venue.getVenueList().size());

            System.out.println("Selected venue:");
            System.out.println(Venue.getVenueList().get(choice - 1));

            System.out.print("Would you like to select this venue ? (Y/N): ");
            yesNo = Main.input.next().charAt(0);

            if (Character.toUpperCase(yesNo) == 'Y') {
                event.setVenue(Venue.getVenueList().get(choice - 1));
            } else {
                System.out.println("Please try again");
            }
        }

        Main.input.nextLine();        //clear buffer
        do {
            try {
                System.out.print("Please provide the date when the event starts (dd-MMM-yyyy format, e.g., 29-Mar-2024):");
                String startDate = Main.input.nextLine();

                LocalDate parsedStartDate = LocalDate.parse(startDate, Main.dateFormatter);

                System.out.print("Please provide the event's starting time (HH:mm format, e.g., 18:30):");
                String startTime = Main.input.nextLine();

                LocalTime parsedStartTime = LocalTime.parse(startTime, Main.timeFormatter);


                System.out.print("Please provide the event end date (dd-MMM-yyyy format, e.g., 05-Apr-2024): ");
                String endDate = Main.input.nextLine();

                LocalDate parsedEndDate = LocalDate.parse(endDate, Main.dateFormatter);

                System.out.print("Please provide the event's ending time (HH:mm format, e.g., 18:30):");
                String endTime = Main.input.nextLine();

                LocalTime parsedEndTime = LocalTime.parse(endTime, Main.timeFormatter);

                boolean dateValidation = true;

                for (Event existingEvent : events) {
                    //condition: to allow booking for same time slot only for DIFFERENT VENUE
                    //if the venue is not clashed with the existing event then skip to next event
                    if (!existingEvent.getVenue().equals(event.getVenue())) {
                        continue;
                    }

                    if (!(parsedStartDate.isAfter(existingEvent.getEndDate()) || parsedEndDate.isBefore(existingEvent.getStartDate()))) {
                        System.out.println("Un-available event creation, event existed in between selected venue and date : " + existingEvent.startDate + " to " + existingEvent.getEndDate() + "\n");
                        dateValidation = false;
                        break;
                    }
                }

                if (parsedStartDate.equals(parsedEndDate)) {
                    System.out.println("Event duration must not be at the same day");
                    dateValidation = false;
                }

                //checking if start date input is before today date, if yes print error
                if (parsedStartDate.isBefore(LocalDate.now())) {
                    System.out.println("Past date reservation is not available");
                    dateValidation = false;
                }

                //if end Date is before start date or start date is after end date
                if (parsedEndDate.isBefore(parsedStartDate)) {
                    System.out.println("Invalid start date / end date");
                    dateValidation = false;
                }
                if (dateValidation) {
                    event.setStartDate(parsedStartDate);
                    event.setStartTime(parsedStartTime);
                    event.setEndDate(parsedEndDate);
                    event.setEndTime(parsedEndTime);
                    valid = true;
                }

            } catch (DateTimeParseException dateTimeParseException) {
                System.out.println("Error, incorrect date/time entered" + dateTimeParseException.getParsedString());
            }
        } while (!valid);

        //PROMO CODE
        System.out.print("Do you have a promo code to enter? (Y/N): ");
        yesNo = Main.input.next().charAt(0);

        if (Character.toUpperCase(yesNo) == 'Y') {
            do {
                System.out.print("Enter your promo code here: ");
                Main.input.nextLine(); // clear buffer
                String promoCode = Main.input.nextLine();

                if (!promoCode.isEmpty()) {
                    System.out.println("Promo code: " + promoCode);
                    System.out.print("Confirm entry? (Y/N): ");
                    yesNo = Main.input.next().charAt(0);

                    if (Character.toUpperCase(yesNo) == 'Y') {
                        event.setPromoCode(promoCode);

                        do {
                            System.out.print("Please enter the discount rate of this promo code " + promoCode + ": ");
                            int discountRate = Main.input.nextInt();

                            if (discountRate < 0 || discountRate >= 100) {
                                System.out.println("Invalid discount rate");
                                //to loop
                                yesNo = 'N';
                                continue;
                            }

                            System.out.println("Discount at: " + discountRate);
                            System.out.print("Confirm entry? (Y/N) : ");
                            yesNo = Main.input.next().charAt(0);

                            if (Character.toUpperCase(yesNo) == 'Y') {
                                event.setDiscountRate(discountRate);
                                System.out.println("Saved record");
                            } else {
                                System.out.println("Invalid character entered");
                            }

                        } while (Character.toUpperCase(yesNo) != 'Y');
                    } else {
                        System.out.println("Invalid character entered");
                    }


                }
            } while (Character.toUpperCase(yesNo) != 'Y');
        }

        do {
            System.out.println("The capacity of the venue selected: " + event.getVenue().getVenueCapacity());
            System.out.println("How do you want to allocate the seats? (999 for auto distribution)");
            System.out.print("VIP seats allocation: ");
            int vipSeats = Main.input.nextInt();

            if (vipSeats == 999 || vipSeats <= 0) {
                System.out.println("Automatically assigning the seats");
                vipSeats = (int) (event.getVenue().getVenueCapacity() * 0.2);
            }
            //if user input more than the capacity, will allocate 20% to vip seating or user choose to auto distribution
            else if (vipSeats > event.getVenue().getVenueCapacity()) {
                System.out.println("Seats are only " + event.getVenue().getVenueCapacity() + ".. automatically assigning the seats");
                vipSeats = (int) (event.getVenue().getVenueCapacity() * 0.2);
            }

            int normalSeats = event.getVenue().getVenueCapacity() - vipSeats;

            System.out.println("Seats distribution: ");
            System.out.println("VIP seats: " + vipSeats);
            System.out.println("Normal seats: " + normalSeats);

            System.out.print("Are you sure to allocate the seat as above? (Y/N): ");
            yesNo = Main.input.next().charAt(0);

            if (Character.toUpperCase(yesNo) == 'Y') {
                event.setVipSeats(new int[vipSeats]);
                event.setNormalSeats(new int[normalSeats]);
            } else {
                System.out.println("Please allocate the seats accordingly.");
            }
        } while (Character.toUpperCase(yesNo) == 'N');

        do {
            System.out.println("Please add seating price: ");

            if (event.getVipSeats().length > 0) {
                System.out.print("Ticket price for VIP seating (each) : RM");
                double vipTicketPrice = Main.input.nextDouble();
                event.setVipPricePerTicket(vipTicketPrice);

            }
            if (event.getNormalSeats().length > 0) {
                System.out.print("Ticket price for normal seating (each) : RM");
                double normalTicketPrice = Main.input.nextDouble();
                event.setNormalPricePerTicket(normalTicketPrice);
            }

            if (event.getVipPricePerTicket() < event.getNormalPricePerTicket()) {
                System.out.println("Reminder");
                System.out.println("Vip ticket price is cheaper than normal price");
            }

            System.out.println("VIP price per ticket: " + event.getVipPricePerTicket());
            System.out.println("Normal price per ticket: " + event.getNormalPricePerTicket());

            System.out.print("Would like like to confirm the price? (Y/N): ");
            yesNo = Main.input.next().charAt(0);

            if (Character.toUpperCase(yesNo) != 'Y') {
                System.out.println("Please enter again");
            }
        } while (Character.toUpperCase(yesNo) != 'Y');

        //Confirmation
        System.out.print("Would you like to create event? (Y/N): ");
        yesNo = Main.input.next().charAt(0);
        if (Character.toUpperCase(yesNo) == 'Y') {
            event.setId(eventCounter);
            event.setStatus(Status.CREATED);
            event.setCreatedAt(LocalDateTime.now());
            event.setUser(organizer);
            events.add(event);

            Main.input.nextLine();

            return true;
        }
        System.out.println("Event creation aborted");
        return false;
    }

    public static boolean editEvent(List<Event> events) {
        boolean valid = false;

        //if events have no content, then return
        if (events.isEmpty()) {
            System.out.println("No events found");
            return false;
        }

        displayEvent(events);

        System.out.println("Please enter the id of the desired event to change: ");
        int choice = Main.input.nextInt();

        if (choice < 1 || choice > events.size()) {
            System.out.println("Invalid id entered");
            return false;
        }

        if (events.get(choice - 1).getStatus().equals(Status.CREATED)) {
            while (!valid) {
                Event event = events.get(choice - 1);
                System.out.println("Editing event: ");
                System.out.println(event);

                System.out.println("Select item to be changed");
                System.out.println("1. Event name");
                System.out.println("2. Event description");
                System.out.println("3. Event date");
                System.out.println("4. Venue");
                System.out.println("5. Promo code");
                System.out.println("6. Exit to menu");

                choice = Main.input.nextInt();

                Main.input.nextLine();
                switch (choice) {
                    case (1) -> event.editEventName(event);
                    case (2) -> event.editEventDescription(event);
                    case (3) -> event.editEventDate(event, events);
                    case (4) -> event.editVenue(event);
                    case (5) -> event.editPromoCode(event);
                    default -> System.out.println("Returning to menu...");
                }
                valid = true;
            }
        } else {
            System.out.print("The desired event status is deleted/completed, no changes are allowed");
        }
        return valid;
    }

    public static void deleteEvent(List<Event> events) {

        displayEvent(events);
        System.out.println("Please enter id of the desired event to delete: ");
        int choice = Main.input.nextInt();

        if (choice < 1 || choice > events.size()) {
            System.out.println("Invalid id entered");
        } else {
            events.get(choice - 1).setStatus(Status.DELETED);
            System.out.println("Deleted successfully");
        }

    }

    public void editEventName(Event event) {
        Scanner input = new Scanner(System.in);
        boolean valid = false;

        do {
            System.out.print("Please enter new event name: ");
            String newEventName = input.nextLine();

            System.out.print("Please confirm the name (" + newEventName + ")" + ", (Y/N) : ");
            char yesNo = input.next().charAt(0);
            if (Character.toUpperCase(yesNo) == 'Y') {
                event.setEventName(newEventName);
                valid = true;

            } else if (Character.toUpperCase(yesNo) == 'N') {
                break;
            } else {
                System.out.println("Invalid input. Please try again.");
                input.nextLine();
            }
        } while (!valid);
    }

    public boolean editEventDescription(Event event) {
        boolean valid = false;

        System.out.print("Please enter new event description: ");
        String newEventDescription = Main.input.nextLine().trim();

        do {
            System.out.print("Please confirm the new description (" + newEventDescription + ")" + ", (Y/N) : ");
            char yesNo = Main.input.next().charAt(0);
            if (Character.toUpperCase(yesNo) == 'Y') {
                event.setEventDescription(newEventDescription);
                valid = true;
            } else if (Character.toUpperCase(yesNo) == 'N') {
                break;
            } else {
                System.out.println("Invalid input. Please try again.");
                Main.input.nextLine();
            }
        } while (!valid);
        return false;
    }

    public void editEventDate(Event event, List<Event> events) {

        LocalDate parsedStartDate = null, parsedEndDate = null;
        LocalTime parsedStartTime = null, parsedEndTime = null;
        char yesNo;


        boolean valid = false;
        System.out.println("Editing event - " + event.getEventName());
        System.out.println("Current start date: " + event.getStartDate().format(Main.dateFormatter));
        System.out.println("Current start time: " + event.getStartTime().format(Main.timeFormatter));
        System.out.println("Current end date: " + event.getEndDate().format(Main.dateFormatter));
        System.out.println("Current end time: " + event.getEndTime().format(Main.timeFormatter));

        do {
            try {
                System.out.print("Please provide the date when the event starts (dd-MMM-yyyy format, e.g., 29-Mar-2024):");
                String startDate = Main.input.nextLine();

                parsedStartDate = LocalDate.parse(startDate, Main.dateFormatter);

                System.out.print("Please provide the event's starting time (HH:mm format, e.g., 18:30):");
                String startTime = Main.input.nextLine();

                parsedStartTime = LocalTime.parse(startTime, Main.timeFormatter);


                System.out.print("Please provide the event end date (dd-MMM-yyyy format, e.g., 05-Apr-2024): ");
                String endDate = Main.input.nextLine();

                parsedEndDate = LocalDate.parse(endDate, Main.dateFormatter);

                System.out.print("Please provide the event's ending time (HH:mm format, e.g., 18:30):");
                String endTime = Main.input.nextLine();

                parsedEndTime = LocalTime.parse(endTime, Main.timeFormatter);

                boolean dateValidation = true;

                for (Event existingEvent : events) {
                    //condition: to allow booking for same time slot only for DIFFERENT VENUE
                    //if the venue is not clashed with the existing event then skip to next event
                    if (!existingEvent.getVenue().equals(event.getVenue())) {
                        continue;
                    }

                    if (!(parsedStartDate.isAfter(existingEvent.getEndDate()) || parsedEndDate.isBefore(existingEvent.getStartDate()))) {
                        System.out.println("Un-available event creation, event existed in between selected venue and date : " + existingEvent.startDate + " to " + existingEvent.getEndDate() + "\n");
                        dateValidation = false;
                        break;
                    }
                }

                //checking if start date input is before today date, if yes print error
                if (parsedStartDate.isBefore(LocalDate.now())) {
                    System.out.println("Past date reservation is not available");
                    dateValidation = false;
                }

                //if end Date is before start date or start date is after end date
                if (parsedEndDate.isBefore(parsedStartDate) || parsedStartDate.isAfter(parsedEndDate)) {
                    System.out.println("Invalid start date / end date");
                    dateValidation = false;
                }
                if (dateValidation) {
                    event.setStartDate(parsedStartDate);
                    event.setStartTime(parsedStartTime);
                    event.setEndDate(parsedEndDate);
                    event.setEndTime(parsedEndTime);
                    valid = true;
                }

            } catch (DateTimeParseException dateTimeParseException) {
                System.out.println("Error, incorrect date/time entered" + dateTimeParseException.getParsedString());
            }
        } while (!valid);

        while (true) {
            System.out.println("Confirming your changes");
            System.out.println("Amended start date: " + parsedStartDate.format(Main.dateFormatter));
            System.out.println("Amended start time: " + parsedStartTime.format(Main.timeFormatter));
            System.out.println("Amended end date: " + parsedEndDate.format(Main.dateFormatter));
            System.out.println("Amended end time: " + parsedEndTime.format(Main.timeFormatter));
            System.out.print("Confirm changes? (Y/N): ");

            yesNo = Main.input.next().charAt(0);

            if (Character.toUpperCase(yesNo) == 'Y') {
                event.setStartDate(parsedStartDate);
                event.setEndDate(parsedEndDate);
                event.setStartTime(parsedStartTime);
                event.setEndTime(parsedEndTime);
                System.out.println("Changes made successfully, returning to main menu");
                break;
            } else if (Character.toUpperCase(yesNo) == 'N') {
                System.out.println("Discarding changes");
                return;
            } else {
                System.out.println("Invalid input! Please enter Y or N.");
            }
        }
        //clear buffer
        Main.input.nextLine();
    }

    public boolean editPromoCode(Event event) {
        char yesNo;
        if (Objects.nonNull(event) && event.getPromoCode() == null || event.getPromoCode().isEmpty()) {
            System.out.println("No promo code");
            System.out.print("Would you like to add a promo code? (Y/N): ");

            if (Character.toUpperCase(Main.input.nextLine().charAt(0)) == 'Y') {
                addPromoCode(event);
            }
            return false;
        }

        System.out.println("Your current promo code: " + event.getPromoCode());
        System.out.print("Enter your new promo code here: ");
        String newPromoCode = Main.input.nextLine();

        if (!newPromoCode.isEmpty()) {
            System.out.println("Promo code: " + newPromoCode);
            System.out.print("Confirm changes? (Y/N): ");
            yesNo = Main.input.next().charAt(0);

            if (Character.toUpperCase(yesNo) == 'Y') {
                event.setPromoCode(newPromoCode);
                System.out.println("Promo code changes successfully");
                return true;
            }
        }
        System.out.println("No promo code entered");
        return false;
    }

    public void addPromoCode(Event event) {
        char yesNo = 'N';
        System.out.println("Adding promo code for event with event name : " + event.getEventName());
        do {
            System.out.print("Enter your promo code here: ");
            String promoCode = Main.input.nextLine();

            if (!promoCode.isEmpty()) {
                System.out.println("Promo code: " + promoCode);
                System.out.print("Confirm entry? (Y/N): ");
                yesNo = Main.input.next().charAt(0);

                if (Character.toUpperCase(yesNo) == 'Y') {
                    event.setPromoCode(promoCode);
                    System.out.println("Promo code added successfully");
                } else if (Character.toUpperCase(yesNo) == 'N') {
                    System.out.println("No promo code entered ");
                    break;
                } else {
                    System.out.println("Invalid input. Please try again.");
                    Main.input.nextLine();
                }
            }
        } while (Character.toUpperCase(yesNo) != 'Y');
    }

    public void editVenue(Event event) {
        char yesNo;
        boolean valid = false;
        int choice;
        System.out.println("\n Venue Listing\n");
        while (!valid) {
            try {
                do {
                    for (Venue venue : Venue.getVenueList()) {
                        System.out.println(venue.toString());
                    }
                    System.out.print("Which venue would you change to? : ");
                    choice = Main.input.nextInt();

                    if (choice < 1 || choice > Venue.getVenueList().size()) {
                        System.out.println("Please enter valid only venue id");
                    }
                } while (choice < 1 || choice > Venue.getVenueList().size());

                if (Objects.nonNull(Venue.getVenueList().get(choice - 1))) {
                    System.out.println("Selected venue:");
                    System.out.println(Venue.getVenueList().get(choice - 1));

                    System.out.print("Would you like to select this venue ? (Y/N) : ");
                    yesNo = Main.input.next().charAt(0);

                    if (Character.toUpperCase(yesNo) == 'Y') {
                        //checking if the selected venue and the event venue is the same
                        if (!event.getVenue().equals(Venue.getVenueList().get(choice - 1))) {

                            do {
                                System.out.println("The capacity of the venue selected: " + event.getVenue().getVenueCapacity());
                                System.out.println("How do you want to allocate the seats? (999 for auto distribution)");
                                System.out.print("VIP seats allocation: ");
                                int vipSeats = Main.input.nextInt();

                                if (vipSeats == 999 || vipSeats <= 0) {
                                    System.out.println("Automatically assigning the seats");
                                    vipSeats = (int) (event.getVenue().getVenueCapacity() * 0.2);
                                }
                                //if user input more than the capacity, will allocate 20% to vip seating or user choose to auto distribution
                                else if (vipSeats > event.getVenue().getVenueCapacity()) {
                                    System.out.println("Seats are only " + event.getVenue().getVenueCapacity() + ".. automatically assigning the seats");
                                    vipSeats = (int) (event.getVenue().getVenueCapacity() * 0.2);
                                }

                                int normalSeats = event.getVenue().getVenueCapacity() - vipSeats;

                                System.out.println("Seats distribution: ");
                                System.out.println("VIP seats: " + vipSeats);
                                System.out.println("Normal seats: " + normalSeats);

                                System.out.print("Are you sure to allocate the seat as above? (Y/N): ");
                                yesNo = Main.input.next().charAt(0);

                                if (Character.toUpperCase(yesNo) == 'Y') {
                                    event.setVipSeats(new int[vipSeats]);
                                    event.setNormalSeats(new int[normalSeats]);
                                } else {
                                    System.out.println("Please allocate the seats accordingly.");
                                }
                            } while (Character.toUpperCase(yesNo) == 'N');

                            event.setVenue(Venue.getVenueList().get(choice - 1));
                        } else {
                            System.out.println("Venue selected is identical.");
                        }

                        valid = true;
                    } else if (Character.toUpperCase(yesNo) == 'N') {
                        System.out.println("No changes done");
                        break;
                    } else {
                        System.out.println("Invalid character entered");
                    }
                } else {
                    System.out.println("Please enter valid only venue");
                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("Please only enter numeric valued choice");
                Main.input.nextLine();
            }
        }
    }

    public static void displayEvent(List<Event> events) {

        updateEventStatus(events);

        //sort by ongoing first
        for (Event event : events) {
            if (event.getStatus() == Status.ONGOING) {
                System.out.println(event);
            }
        }

        //then sort by created
        for (Event event : events) {
            if (event.getStatus() == Status.CREATED) {
                System.out.println(event);
            }
        }


        //then lastly only sort by created
        // Display events with other statuses
        for (Event event : events) {
            if (event.getStatus() != Status.CREATED && event.getStatus() != Status.ONGOING) {
                System.out.println(event);
            }
        }
    }

    public static void updateEventStatus(List<Event> events) {
        for (Event event : events) {
            //sort event status created show first
            if (event.getStatus() == Status.CREATED) {
                //if event date = today date, then set to ongoing
                if (event.getStartDate().equals(LocalDate.now())) {
                    event.setStatus(Status.ONGOING);
                }
            }
            //checking ongoing event
            if (event.getStatus() == Status.ONGOING) {
                //if event date is after today date, set as completed status
                if (event.getStartDate().isAfter(LocalDate.now())) {
                    event.setStatus(Status.COMPLETED);
                }
            }
        }
    }

    public static void searchByEventName(List<Event> events) {

        System.out.println("Please enter desire the event name: ");
        String searchTerm = Main.input.nextLine();

        for (Event event : events) {
            String eventName = event.getEventName().toLowerCase();
            searchTerm = searchTerm.toLowerCase();

            if (eventName.contains(searchTerm)) {
                System.out.println(event);
            }
        }
    }

    public void setElementVip(int index, int value) {
        if (index >= 0 && index < this.getVipSeats().length) {
            getVipSeats()[index] = value;
        } else {
            System.out.println("Invalid index");
        }
    }

    public void setElementNormal(int index, int value) {
        if (index >= 0 && index < getNormalSeats().length) {
            getNormalSeats()[index] = value;
        } else {
            System.out.println("Invalid index");
        }
    }

    public void setElementVip(Event event, int index, int value) {
        if (index >= 0 && index < event.getVipSeats().length) {
            event.getVipSeats()[index] = value;
        } else {
            System.out.println("Invalid index");
        }
    }

    public void setElementNormal(Event event, int index, int value) {
        if (index >= 0 && index < event.getNormalSeats().length) {
            event.getNormalSeats()[index] = value;
        } else {
            System.out.println("Invalid index");
        }
    }

    public static void getReport(List<Event> events) {
        int choice;
        displayEvent(events);
        if (events.isEmpty()) {
            System.out.println("No events found");
            return;
        }

        do {
            System.out.println("Please select event to generate report with: ");
            choice = Main.input.nextInt();
            if (choice < 1 || choice > events.size()) {
                System.out.println("Invalid id entered");
            } else {
                if (Objects.nonNull(events.get(choice - 1))) {
                    events.get(choice - 1).generateReport();
                }
            }

        } while (choice < 1 || choice > events.size());

    }


    @Override
    public void generateReport() {
        StringBuilder report = new StringBuilder();
        int totalVIPSeats = vipSeats.length;
        int totalNormalSeats = normalSeats.length;
        int numOfTournaments = tournamentList != null ? tournamentList.size() : 0;
        int occupiedVIPSeats = (int) Arrays.stream(vipSeats).filter(seat -> seat != 0).count();
        int occupiedNormalSeats = (int) Arrays.stream(normalSeats).filter(seat -> seat != 0).count();
        double vipOccupancyRate = ((double) occupiedVIPSeats / totalVIPSeats) * 100;
        double normalOccupancyRate = ((double) occupiedNormalSeats / totalNormalSeats) * 100;
        long daysUntilEvent = ChronoUnit.DAYS.between(LocalDate.now(), startDate);
        daysUntilEvent = daysUntilEvent < 0 ? 0 : daysUntilEvent;

        double potentialVIPRevenue = totalVIPSeats * vipPricePerTicket;
        double potentialNormalRevenue = totalNormalSeats * normalPricePerTicket;
        double totalPotentialRevenue = potentialVIPRevenue + potentialNormalRevenue;

        double totalEarnedRegFee = Objects.nonNull(tournamentList) ? tournamentList.stream().mapToDouble(tournament -> tournament.getTournamentFee() * (tournament.getMaxSlots() - tournament.getSlots())).sum() : 0;

        int occupiedSeats = occupiedVIPSeats + occupiedNormalSeats;
        double venueUtilization = (double) occupiedSeats / venue.getVenueCapacity() * 100;


        double achievedVIPRevenue = occupiedVIPSeats * vipPricePerTicket;
        double achievedNormalRevenue = occupiedNormalSeats * normalPricePerTicket;
        double achievedRevenueFromRegFee = Objects.nonNull(tournamentList) ? tournamentList.stream().mapToDouble(tournament -> tournament.getTournamentFee() * (tournament.getMaxSlots() - tournament.getSlots())).sum() : 0;
        //total revenue received
        double totalAchievedRevenue = achievedVIPRevenue + achievedNormalRevenue + achievedRevenueFromRegFee;

        String healthStatus = "Healthy";
        StringBuilder bar = new StringBuilder();
        if (vipOccupancyRate < 10 || normalOccupancyRate < 50 && daysUntilEvent < 7) {
            healthStatus = "Critical";
            bar.append(String.format("|%-15s%25s%18s|\n", "", " ══════════════════════╗", ""));
            bar.append(String.format("|%-15s%25s%16s|\n", "", " ║╔════════════════════╗ ╚╗", ""));
            bar.append(String.format("|%-15s%25s%13s|\n", "", " ║║\033[31m██████\033[0m░░░░░░░░░░░░░░░░░╚╗╗╗", ""));
            bar.append(String.format("|%-15s%25s%13s|\n", "", " ║║\033[31m██████\033[0m░░░░░░░░░░░░░░░░░─║║║", ""));
            bar.append(String.format("|%-15s%25s%13s|\n", "", " ║║\033[31m██████\033[0m░░░░░░░░░░░░░░░░░╔╝╔╝", ""));
            bar.append(String.format("|%-15s%25s%15s|\n", "", " ║║\033[31m██████\033[0m░░░░░░░░░░░░░░░░░╔╝", ""));
            bar.append(String.format("|%-15s%25s%17s|\n", "", " ║╚════════════════════╝╔╝", ""));
            bar.append(String.format("|%-15s%25s%18s|\n", "", " ╚══════════════════════╝", ""));

        } else if (vipOccupancyRate < 20 || normalOccupancyRate < 30 && daysUntilEvent < 14) {
            healthStatus = "At Risk";
            bar.append(String.format("|%-15s%25s%18s|\n", "", " ══════════════════════╗", ""));
            bar.append(String.format("|%-15s%25s%16s|\n", "", " ║╔════════════════════╗ ╚╗", ""));
            bar.append(String.format("|%-15s%25s%13s|\n", "", " ║║\033[32m███████████████\033░░░░░░░░╚╗╗╗", ""));
            bar.append(String.format("|%-15s%25s%13s|\n", "", " ║║\033[32m███████████████\033░░░░░░░░─║║║", ""));
            bar.append(String.format("|%-15s%25s%13s|\n", "", " ║║\033[32m███████████████\033░░░░░░░░╔╝╔╝", ""));
            bar.append(String.format("|%-15s%25s%15s|\n", "", " ║║\033[32m███████████████\033░░░░░░░░╔╝", ""));
            bar.append(String.format("|%-15s%25s%17s|\n", "", " ║╚════════════════════╝╔╝", ""));
            bar.append(String.format("|%-15s%25s%18s|\n", "", " ╚══════════════════════╝", ""));

        } else {
            bar.append(String.format("|%-15s%25s%18s|\n", "", " ══════════════════════╗", ""));
            bar.append(String.format("|%-15s%25s%16s|\n", "", " ║╔════════════════════╗ ╚╗", ""));
            bar.append(String.format("|%-15s%25s%13s|\n", "", " ║║\033[32m████████████████████████\033[0m╚╗╗", ""));
            bar.append(String.format("|%-15s%25s%12s|\n", "", " ║║\033[32m████████████████████████\033[0m─║║║", ""));
            bar.append(String.format("|%-15s%25s%12s|\n", "", " ║║\033[32m████████████████████████\033[0m╔╝╔╝", ""));
            bar.append(String.format("|%-15s%25s%14s|\n", "", " ║║\033[32m████████████████████████\033[0m╔╝", ""));
            bar.append(String.format("|%-15s%25s%17s|\n", "", " ║╚════════════════════╝╔╝", ""));
            bar.append(String.format("|%-15s%25s%18s|\n", "", " ╚══════════════════════╝", ""));
        }

        //start of report
        report.append("$$$$$$$\\                                           $$\\     \n");
        report.append("$$  __$$\\                                          $$ |    \n");
        report.append("$$ |  $$ | $$$$$$\\   $$$$$$\\   $$$$$$\\   $$$$$$\\ $$$$$$\\   \n");
        report.append("$$$$$$$  |$$  __$$\\ $$  __$$\\ $$  __$$\\ $$  __$$\\\\_$$  _|  \n");
        report.append("$$  __$$< $$$$$$$$ |$$ /  $$ |$$ /  $$ |$$ |  \\__| $$ |    \n");
        report.append("$$ |  $$ |$$   ____|$$ |  $$ |$$ |  $$ |$$ |       $$ |$$\\ \n");
        report.append("$$ |  $$ |\\$$$$$$$\\ $$$$$$$  |\\$$$$$$  |$$ |       \\$$$$  |\n");
        report.append("\\__|  \\__| \\_______|$$  ____/  \\______/ \\__|        \\____/ \n");
        report.append("                    $$ |                                   \n");
        report.append("                    $$ |                                   \n");
        report.append("                    $$ |                                   \n");
        report.append("                    \\__|                                   \n");

        //Attendance
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("|%35s%23s|\n", "Event " + eventName + "'s attendance", ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("| %-25s|%25s%6s|\n", "Quantity", "Seats", ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("| %-25s|%25d%6s|\n", "Vip Admission", occupiedVIPSeats, ""));
        report.append(String.format("| %-25s|%25d%6s|\n", "Normal Admission", occupiedNormalSeats, ""));
        report.append(String.format("| %-25s|%25d%6s|\n", "Total", occupiedSeats, ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));

        //Event Details
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("|%35s%23s|\n", "Event Details", ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("| %-25s|%28s%3s|\n", "Event name", eventName, ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("| %-25s|%25s%6s|\n", "Date & Time", startDate, ""));
        report.append(String.format("| %-25s|%25s%6s|\n", "Venue", venue.getVenueName(), ""));
        report.append(String.format("| %-25s|%25s%6s|\n", "Days Until Event", daysUntilEvent, ""));
        report.append(String.format("| %-25s|%25d%6s|\n", "Tournaments", numOfTournaments, ""));
        report.append(String.format("| %-25s|%25s%6s|\n", "Created At", createdAt.format(Main.dateTimeFormatter), ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));

        //Health Status
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("|%35s%23s|\n", "Health Status Detail", ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("| %-25s|%25s%6s|\n", "Health Status", healthStatus, ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(bar);
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));

        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("|%35s%23s|\n", "Occupancy Details", ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("| %-25s|%28s%3s|\n", "Occupancy", "Details", ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("| %-25s|%28s%3s|\n", "Promotion: ", (promoCode != null ? "Available: " + promoCode : "Not Available"), ""));
        report.append(String.format("| %-25s|%25s%6s|\n", "Venue Utilization", venueUtilization + "%", ""));
        report.append(String.format("| %-25s|%25s%6s|\n", "VIP Seat", String.format("%.2f%%", vipOccupancyRate), ""));
        report.append(String.format("| %-25s|%25s%6s|\n", "Normal Seat", String.format("%.2f%%", normalOccupancyRate), ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));

        //Potential Revenue
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("|%35s%23s|\n", "Potential Revenue", ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("| %-25s|%25s%6s|\n", "From", "Total(RM)", ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("| %-25s|%25.2f%6s|\n", "VIP seats: ", potentialVIPRevenue, ""));
        report.append(String.format("| %-25s|%25.2f%6s|\n", "Normal seats", potentialNormalRevenue, ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("| %-25s|%25.2f%6s|\n", "Total", totalPotentialRevenue, ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));

        //Actual Revenue
        //todo fix tournament calculation
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("|%35s%23s|\n", "Actual Revenue", ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("| %-25s|%25s%6s|\n", "From", "Total(RM)", ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("| %-25s|%25.2f%6s|\n", "VIP seats: ", achievedVIPRevenue, ""));
        report.append(String.format("| %-25s|%25.2f%6s|\n", "Normal seats", achievedNormalRevenue, ""));
        report.append(String.format("| %-25s|%25.2f%6s|\n", "Registration", totalEarnedRegFee, ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("| %-25s|%25.2f%6s|\n", "Total", totalAchievedRevenue, ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));


        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("|%35s%23s|\n", "Summary of event " + eventName, ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("| %-25s|%25s%6s|\n", "Title", "Total(RM)", ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("| %-25s|%25.2f%6s|\n", "Potential Revenue: ", totalPotentialRevenue, ""));
        report.append(String.format("| %-25s|%25.2f%6s|\n", "Total Revenue", totalAchievedRevenue, ""));
        report.append(String.format("| %-25s|%25s%6s|\n", "Earning", String.format("%.2f%s", (totalAchievedRevenue / totalPotentialRevenue) * 100, " %"), ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));

        System.out.print(report);

        //todo add roi reporting feature for each event
    }

    public static void generateAllEventsReport(List<Event> eventList) {
        StringBuilder report = new StringBuilder();
        long totalTicketsSold = 0;
        int totalTournaments = 0;
        double totalAchievedRevenueFromSeats = 0;
        double totalAchievedRevenueFromRegFee = 0;
        double totalInvested = 0;
        double totalPotentialRevenueFromSeats = 0;
        double totalPotentialRevenueFromRegFee = 0;

        double totalAchievedRevenue = 0;

        List<Event> completedEvent = eventList.stream().filter(event -> event.getStatus().equals(Status.COMPLETED)).toList();

        for (Event event : completedEvent) {
            long vipSeatsSold = Arrays.stream(event.getVipSeats()).filter(vipSeats -> vipSeats != 0).count();
            long normalSeatsSold = Arrays.stream(event.getNormalSeats()).filter(normalSeats -> normalSeats != 0).count();

            // 0 equals available, hence != is booked ticket (calculation for both vip and normal seats)
            totalTicketsSold += vipSeatsSold + normalSeatsSold;

            totalTournaments += event.getTournamentList() != null ? event.getTournamentList().size() : 0;

            totalAchievedRevenueFromSeats += (vipSeatsSold * event.getVipPricePerTicket()) + (normalSeatsSold + (event.getNormalPricePerTicket()));

            totalPotentialRevenueFromSeats += (event.getNormalPricePerTicket() * event.getNormalSeats().length) + (event.getVipPricePerTicket() * event.getVipSeats().length);

            totalPotentialRevenueFromRegFee += event.getTournamentList().stream().mapToDouble(tournament -> tournament.getTournamentFee() * tournament.getMaxSlots()).sum();

            totalAchievedRevenueFromRegFee += event.getTournamentList().stream().mapToDouble(tournament -> tournament.getTournamentFee() * ((tournament.getMaxSlots() - tournament.getSlots()) < 1 ? 1 : tournament.getMaxSlots() - tournament.getSlots())).sum();

            totalInvested += event.getTournamentList().stream().mapToDouble(tournament -> tournament.getPrizePool()).sum();

            totalAchievedRevenue += totalAchievedRevenueFromSeats + totalAchievedRevenueFromRegFee;
        }


        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("|%35s%23s|\n", "Summary of all completed events", ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("| %-25s|%25s%6s|\n", "Metrics", "Details", ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));
        report.append(String.format("| %-25s|%25d%6s|\n", "Total ticket sold", totalTicketsSold, ""));
        report.append(String.format("| %-25s|%25d%6s|\n", "Total tournaments", totalTournaments, ""));
        report.append(String.format("| %-25s|%25.2f%6s|\n", "Total potential revenue", totalPotentialRevenueFromSeats + totalPotentialRevenueFromRegFee, ""));
        report.append(String.format("| %-25s|%25.2f%6s|\n", "Actual revenue", totalAchievedRevenueFromSeats + totalAchievedRevenueFromRegFee, ""));
        report.append(String.format("| %-25s|%25.2f%6s|\n", "ROI (in %)", (totalAchievedRevenue / totalInvested) * 100, ""));
        report.append(String.format("%-40s\n", "------------------------------------------------------------"));

        System.out.println(report);
    }

    @Override
    public String toString() {
        StringBuilder event = new StringBuilder();
        event.append("┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐\n");
        event.append(String.format("│ %-4s │ %-40s │ %-60s │ %-10s │ %-10s │ %-16s │ %-15s │ %-60s │ %-12s │ %-15s │ %-14s │\n",
                "Id", "Name", "Description", "Start date", "End date", "Organized by", "Promo Code", "Venue name", "Status", "Vip seats", "Normal seats"));
        event.append("├────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘\n");
        event.append(String.format("│ %-4d │ %-40s │ %-60s │ %-10s │ %-10s │ %-15s │ %-15s │ %-60s │ %-12s │ %-15d │ %-13d │\n",
                this.getId(), this.getEventName(), this.getEventDescription(), this.getStartDate().format(Main.dateFormatter),
                this.getEndDate().format(Main.dateFormatter), this.getUser().getName(), this.getPromoCode(),
                this.getVenue().getVenueName(), this.status.toString(), this.getVipSeats().length, this.getNormalSeats().length));
        event.append("└────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
        return event.toString();
    }
}