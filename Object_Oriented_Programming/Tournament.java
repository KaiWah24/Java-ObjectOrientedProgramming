/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Tournament {
    private static int nextID = 1;
    private int id;
    private String tournamentName;
    private Date startDate;
    private Date endDate;
    private double prizePool;
    private double tournamentFee;
    private int slots;
    private int maxSlots = 10;
    private Game game;

    private List<Team> teamList;


    public Tournament(String tournamentName, Date startDate, Date endDate, double prizePool, Game game, double tournamentFee) {
        this.id = nextID++;
        this.tournamentName = tournamentName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.prizePool = prizePool;
        this.game = game;
        this.tournamentFee = tournamentFee;
        this.slots = maxSlots;
    }


    public Tournament() {
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    public static int getNextID() {
        return nextID;
    }

    public Game getGame() {
        return game;
    }

    public static void setNextID(int nextID) {
        Tournament.nextID = nextID;
    }

    public void setGame(Game game) {
        this.game = game;
    }


    public void setSlots(int slots) {

        this.slots = slots;
    }

    public int getMaxSlots() {
        return maxSlots;
    }

    public double getTournamentFee() {
        return tournamentFee;
    }

    public int getSlots() {
        return slots;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTournamentName() {

        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {

        this.tournamentName = tournamentName;
    }

    public Date getStartDate() {

        return startDate;
    }

    public void setStartDate(Date startDate) {

        this.startDate = startDate;
    }

    public Date getEndDate() {

        return endDate;
    }

    public void setEndDate(Date endDate) {

        this.endDate = endDate;
    }

    public double getPrizePool() {

        return prizePool;
    }

    public void setPrizePool(double prizePool) {

        this.prizePool = prizePool;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return String.format("Tournament ID: %-5d | Tournament Name: %-20s | Start Date: %-15s | End Date: %-15s | Prize Pool: %-20s | Register Fee: %-20s | Remaining Slots: %-15d",
                id,
                tournamentName,
                dateFormat.format(startDate),
                dateFormat.format(endDate),
                prizePool,
                tournamentFee,
                slots);
    }


    // createTournament method
    public static void createTournaments(List<Event> events, List<Tournament> tournaments) {

        Scanner input = new Scanner(System.in);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Map<String, Tournament> tournamentMap = new HashMap<>();
        boolean createAnotherTournament = true;

        while (createAnotherTournament) {


            System.out.print("Enter Tournament Name: ");
            String tournamentName = input.nextLine();

            //check if the tournament is empty
            while (tournamentName.trim().isEmpty()) {
                System.out.println("Tournament name cannot be empty. Please enter a valid name: ");
                System.out.print("Enter Tournament Name: ");
                tournamentName = input.nextLine();
            }
            // Check if tournament with same name and date already exists
            String tournamentKey = tournamentName.trim().toLowerCase().toUpperCase();

            if (tournamentMap.containsKey(tournamentKey)) {
                System.out.println("Tournament name already exist! Please try again");
                continue; // start the loop from the beginning to prompt the user enter new tournament details
            }


            Date startDate = null;
            boolean isValidStartDate = false;

            while (!isValidStartDate) {
                System.out.print("Enter Start Date (dd-MM-yyyy): ");
                String startDateInput = input.nextLine();

                try {
                    startDate = dateFormat.parse(startDateInput);
                    isValidStartDate = true;
                } catch (ParseException e) {
                    System.out.println("Incorrect date format entered! Please try again.");
                }
            }


            Date endDate = null;
            boolean isValidEndDate = false;

            while (!isValidEndDate) {
                System.out.print("Enter End Date (dd-MM-yyyy): ");
                String endDateInput = input.nextLine();

                try {
                    endDate = dateFormat.parse(endDateInput);

                    // Validate if end date is after start date
                    if (endDate.compareTo(startDate) > 0) {
                        isValidEndDate = true;
                    } else {
                        System.out.println("Tournament ending date must be after start date! Please try again.");
                    }
                } catch (ParseException e) {
                    System.out.println("Incorrect date format! Please try again.");
                }
            }

            System.out.print("Enter Prize Pool:RM ");
            double prizePool = input.nextDouble();

            // validate prize pool
            while (prizePool < 0) {
                System.out.println("Invalid Prize Pool value! Please enter a valid prize pool: ");
                System.out.print("Enter prize pool:RM ");
                prizePool = input.nextDouble();
            }


            System.out.print("Enter the Tournament Register Fee for each team:RM ");
            double tournamentFee = input.nextDouble();

            //Vallidate tournament fee
            while (tournamentFee < 0) {
                System.out.println("Invalid Tournament Fee! Please enter a valid tournament fee: ");
                System.out.println("Enter the Tournament Register Fee for each team:RM");
                tournamentFee = input.nextDouble();
            }
            System.out.println();
            System.out.println("List of Games");
            for (Game game : Game.getGameList()) {
                System.out.println(game.toString());
            }
            System.out.println();

            System.out.print("Please enter the ID of the Game you want to create this Tournament with: ");
            int chooseGame = input.nextInt();
            input.nextLine();
            Game game = new Game();
            if (chooseGame >= 1 && chooseGame <= Game.getGameList().size()) {
                //Add current create tournament to the selected game
                game = Game.getGameList().get(chooseGame - 1);
                System.out.println("You have selected " + game.getGameName() + " as your Game");
            }


            for (Event event : events) {
                if (event.getStatus() == Status.CREATED) {
                    System.out.println(event);
                }
            }

            System.out.print("Please enter the ID of the event you want to create this Tournament with: ");
            int chooseEvent = input.nextInt();
            input.nextLine(); // skip one line
            if (chooseEvent >= 1 && chooseEvent <= events.size()) {
                //Add current create tournament to the selected event
                Event selectedEvent = events.get(chooseEvent - 1);
                Tournament tournament = new Tournament(tournamentName, startDate, endDate, prizePool, game, tournamentFee);
                tournaments.add(tournament);
                tournamentMap.put(tournamentKey, tournament);

                selectedEvent.setTournamentList(tournaments);


                System.out.println();
                // Print the details of the newly created tournament
                String headerFormat = "%-15s %-20s %-15s %-15s %-15s %-20s %-20s %-20s%n";
                String rowFormat = "%-15d %-20s %-15s %-15s %-15.2f %-20.2f %-20s %-20d%n";
                System.out.printf(headerFormat, "Tournament ID:", "Tournament Name:", "Start Date:", "End Date:", "Prize Pool:", "Register Fee:", "Game:", "Remaining Slots:");
                System.out.printf(rowFormat,
                        tournament.getId(),
                        tournament.getTournamentName(),
                        dateFormat.format(tournament.getStartDate()),
                        dateFormat.format(tournament.getEndDate()),
                        tournament.getPrizePool(),
                        tournament.getTournamentFee(),
                        tournament.getGame().getGameName(),
                        tournament.getSlots());
                System.out.println();

                System.out.print("Do you want to create another tournament? (Y/N): ");
                String createAnother = input.next();
                input.nextLine(); // Consume the newline character

                // Check the first character of the user's response
                if (createAnother.charAt(0) == 'n' || createAnother.charAt(0) == 'N') {
                    createAnotherTournament = false;
                }
            }
            System.out.println();
        }

    }
    public static void editTournament(List<Event> events) {
        Scanner input = new Scanner(System.in);
        boolean continueEditing = true;

        while (continueEditing) {
            for (Event event : events) {
                if (event.getStatus() == Status.CREATED) {
                    System.out.println(event);
                }
            }

            System.out.print("Please enter the ID of the event that contains the tournament you want to edit: ");
            int chooseEvent = input.nextInt();
            input.nextLine(); // skip one line

            if (chooseEvent >= 1 && chooseEvent <= events.size()) {
                //get the selected event
                List<Tournament> tournaments = events.get(chooseEvent - 1).getTournamentList();

                if (tournaments != null && !tournaments.isEmpty()) {
                    for (int i = 0; i < tournaments.size(); i++) {
                        Tournament tournament = tournaments.get(i);
                        System.out.printf("%d - %s%n", i + 1, tournament.getTournamentName());
                    }

                    System.out.print("Please enter the ID of the tournament you want to edit: ");
                    int chooseTournament = input.nextInt();
                    input.nextLine(); // skip one line

                    if (chooseTournament >= 1 && chooseTournament <= tournaments.size()) {
                        Tournament selectedTournament = tournaments.get(chooseTournament - 1);

                        boolean continueEditingTournament = true;
                        while (continueEditingTournament) {
                            System.out.println("Which detail of tournament would you like to edit?");
                            System.out.println("1. Tournament name");
                            System.out.println("2. Start date");
                            System.out.println("3. End date");
                            System.out.println("4. Prize pool");
                            System.out.println("5. Tournament Fee");
                            System.out.println("6. Exit");

                            System.out.print("Enter the number of your choice: ");
                            int choice = input.nextInt();
                            input.nextLine(); // skip one line

                            switch (choice) {
                                case 1:
                                    System.out.println("Current Tournament Name is: " + selectedTournament.getTournamentName());
                                    String newTournamentName;
                                    boolean isNameValid = false;

                                    // when isNameValid = true // do below
                                    while (!isNameValid) {
                                        System.out.print("Enter new tournament name: ");
                                        newTournamentName = input.nextLine();

                                        // Check if the new tournament name is the same as the old one
                                        if (newTournamentName.equals(selectedTournament.getTournamentName())) {
                                            System.out.println("The new tournament name cannot be the same as the old tournament name!");
                                        } else {
                                            // Check if the new tournament name already exists in other tournaments
                                            boolean tournamentNameExists = false;
                                            String lowercaseName = newTournamentName.toLowerCase(); // Convert to lowercase
                                            String uppercaseName = newTournamentName.toUpperCase(); // Convert to uppercase
                                            for (Event event : events) {
                                                List<Tournament> tournamentList = event.getTournamentList();
                                                for (Tournament tournament : tournamentList) {
                                                    if (newTournamentName.equals(tournament.getTournamentName())) {
                                                        tournamentNameExists = true;
                                                        break; //exit the inner/inside loop when a duplicate is found
                                                    }
                                                }
                                                if (tournamentNameExists) {
                                                    break; //exit the outer loop when a duplicate is found ????
                                                }
                                            }

                                            if (tournamentNameExists) {
                                                System.out.println("The tournament name already exists! Please enter a different name.");
                                            } else {
                                                System.out.println("New tournament name: " + newTournamentName);
                                                System.out.print("Confirm the edit (Y = Yes, N = No): ");
                                                char confirmEdit = input.nextLine().trim().charAt(0);

                                                if (confirmEdit == 'Y' || confirmEdit == 'y') {
                                                    selectedTournament.setTournamentName(newTournamentName);
                                                    System.out.println("Tournament name updated successfully!");
                                                    isNameValid = true; // Set the flag to exit the loop
                                                } else if (confirmEdit == 'N' || confirmEdit == 'n') {
                                                    System.out.println("Edit canceled.");
                                                    isNameValid = true; // Set the flag to exit the loop
                                                } else {
                                                    System.out.println("Invalid input, edit canceled.");
                                                }
                                            }
                                        }
                                    }
                                    break;
                                // Handle other cases here


                                // Handle other cases here
                                case 2:
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                    System.out.println("Current Tournament Start Date is: " + dateFormat.format(selectedTournament.getStartDate()));
                                    System.out.print("Enter new tournament start date (dd-MM-yyyy): ");
                                    String newStartDateInput = input.nextLine();

                                    boolean isValidStartDate = false;
                                    Date newStartDate = null;

                                    while (!isValidStartDate) {
                                        try {
                                            newStartDate = dateFormat.parse(newStartDateInput);

                                            // Check if the new start date is different from the old start date
                                            if (newStartDate.equals(selectedTournament.getStartDate())) {
                                                System.out.println("The new start date cannot be the same as the old start date! Please enter a different date: ");
                                                newStartDateInput = input.nextLine();
                                            } else if(newStartDate.equals(selectedTournament.getStartDate())){
                                                System.out.println("The new start date cannot be before the old start date! Please enter a different date: ");
                                                newStartDateInput = input.nextLine();
                                            }else {
                                                isValidStartDate = true;
                                            }
                                        } catch (ParseException e) {
                                            System.out.println("Incorrect date format entered! Please try again.");
                                            System.out.print("Enter new tournament start date (dd-MM-yyyy): ");
                                            newStartDateInput = input.nextLine();
                                        }
                                    }

                                    System.out.println("New start date: " + dateFormat.format(newStartDate));
                                    System.out.print("Confirm the edit (Y = Yes, N = No): ");
                                    char confirmEditDate = input.next().charAt(0);

                                    if (confirmEditDate == 'Y' || confirmEditDate == 'y') {
                                        selectedTournament.setStartDate(newStartDate);
                                        System.out.println("Start date updated successfully!");
                                    } else if (confirmEditDate == 'N' || confirmEditDate == 'n') {
                                        System.out.println("Edit canceled.");
                                    } else {
                                        System.out.println("Invalid input, edit canceled.");
                                    }

                                    break;

                                case 3:
                                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
                                    System.out.println("Current Tournament End Date is: " + dateFormat2.format(selectedTournament.getEndDate()));
                                    System.out.print("Enter new tournament end date (dd-MM-yyyy): ");
                                    String newEndDateInput = input.nextLine();

                                    boolean isValidEndDate = false;
                                    Date newEndDate = null;

                                    while (!isValidEndDate) {
                                        try {
                                            newEndDate = dateFormat2.parse(newEndDateInput);

                                            // Check if the new end date is the same as or after the new end date
                                            if (newEndDate.equals(selectedTournament.getEndDate())) {
                                                System.out.println("The new end date cannot be the same as the old end date! Please enter a different date: ");
                                                newEndDateInput = input.nextLine();
                                            } else if (newEndDate.after(newEndDate)) {
                                                System.out.println("The new end date cannot be before the new start date! Please enter a different date: ");
                                                newEndDateInput = input.nextLine();
                                            }   else if (newEndDate.before(selectedTournament.getEndDate())){
                                                System.out.println("The new end date cannot be less than old end date! Please enter a diffrent data: ");
                                                newEndDateInput = input.nextLine();
                                            }
                                            else {
                                                isValidEndDate = true;
                                            }
                                        } catch (ParseException e) {
                                            System.out.println("Incorrect date format entered! Please try again.");
                                            System.out.print("Enter new tournament end date (dd-MM-yyyy): ");
                                            newEndDateInput = input.nextLine();
                                        }
                                    }

                                    System.out.println("New end date: " + dateFormat2.format(newEndDate));
                                    System.out.print("Confirm the edit (Y = Yes, N = No): ");
                                    char confirmEditDate2 = input.next().charAt(0);

                                    if (confirmEditDate2 == 'Y' || confirmEditDate2 == 'y') {
                                        selectedTournament.setStartDate(newEndDate);
                                        System.out.println("Start date updated successfully!");
                                    } else if (confirmEditDate2 == 'N' || confirmEditDate2 == 'n') {
                                        System.out.println("Edit canceled.");
                                    } else {
                                        System.out.println("Invalid input, edit canceled.");
                                    }
                                    break;
                                case 4:
                                    // Editing prize pool
                                    System.out.println("Current Prize Pool: " + selectedTournament.getPrizePool());
                                    System.out.print("Enter new Prize Pool: ");
                                    double newPrizePool = input.nextDouble();
                                    input.nextLine(); // skip one line

                                    // Validate new prize pool
                                    while (newPrizePool < 0) {
                                        System.out.println("Invalid Prize Pool value! Please enter a valid Prize Pool: ");
                                        System.out.print("Enter new Prize Pool: ");
                                        newPrizePool = input.nextDouble();
                                        input.nextLine(); // skip one line
                                    }

                                    System.out.println("New Prize Pool: " + newPrizePool);
                                    System.out.print("Confirm the edit (Y = Yes, N = No): ");
                                    char confirmEditPrizePool = input.next().charAt(0);

                                    if (confirmEditPrizePool == 'Y' || confirmEditPrizePool == 'y') {
                                        selectedTournament.setPrizePool(newPrizePool);
                                        System.out.println("Prize pool updated successfully!");
                                    } else if (confirmEditPrizePool == 'N' || confirmEditPrizePool == 'n') {
                                        System.out.println("Edit canceled.");
                                    } else {
                                        System.out.println("Invalid input, edit canceled.");
                                    }
                                    break;
                                case 5:
                                    // Editing tournament fee
                                    System.out.println("Current Tournament Fee: " + selectedTournament.getTournamentFee());
                                    System.out.print("Enter new Tournament Fee: ");
                                    double newTournamentFee = input.nextDouble();
                                    input.nextLine(); // skip one line

                                    // Validate new prize pool
                                    while (newTournamentFee < 0) {
                                        System.out.println("Invalid Tournament Fee value! Please enter a valid Tournament Fee: ");
                                        System.out.print("Enter new Tournament Fee: ");
                                        newTournamentFee = input.nextDouble();
                                        input.nextLine(); // skip one line
                                    }

                                    System.out.println("New Tournament Fee: " + newTournamentFee);
                                    System.out.print("Confirm the edit (Y = Yes, N = No): ");
                                    char confirmEditTournamentFee = input.next().charAt(0);

                                    if (confirmEditTournamentFee == 'Y' || confirmEditTournamentFee == 'y') {
                                        selectedTournament.setPrizePool(newTournamentFee);
                                        System.out.println("Tournament Fee updated successfully!");
                                    } else if (confirmEditTournamentFee == 'N' || confirmEditTournamentFee == 'n') {
                                        System.out.println("Edit canceled.");
                                    } else {
                                        System.out.println("Invalid input, edit canceled.");
                                    }
                                    break;
                                case 6:
                                    continueEditingTournament = false;
                                    break;

                                default:
                                    System.out.println("Invalid choice!");
                                    break;
                            }
                        }
                    } else {
                        System.out.println("Invalid tournament ID!");
                    }
                } else {
                    System.out.println("No tournaments found for the selected event!");
                }
            } else {
                System.out.println("Invalid event ID!");
            }

            System.out.print("Do you want to edit again? (Y/N): ");
            String editAgain = input.next();
            input.nextLine(); // Consume the newline character

            // Check the first character of the user's response
            if (editAgain.charAt(0) == 'n' || editAgain.charAt(0) == 'N') {
                continueEditing = false;
            }
        }
    }

    public static void deleteTournament(List<Event> events) {
        Scanner input = new Scanner(System.in);

        for (Event event : events) {
            if (event.getStatus() == Status.CREATED) {
                System.out.println(event);
            }
        }

        System.out.print("Please enter the ID of the event that contains the tournament you want to delete: ");
        int chooseEvent = input.nextInt();
        input.nextLine(); // skip one line

        if (chooseEvent >= 1 && chooseEvent <= events.size()) {
            List<Tournament> tournaments = events.get(chooseEvent - 1).getTournamentList();

            if (tournaments != null && !tournaments.isEmpty()) {
                for (int i = 0; i < tournaments.size(); i++) {
                    Tournament tournament = tournaments.get(i);
                    System.out.printf("%d - %s%n", i + 1, tournament.getTournamentName());
                }

                System.out.print("Please enter the ID of the tournament you want to delete: ");
                int chooseTournament = input.nextInt();
                input.nextLine(); // skip one line

                if (chooseTournament >= 1 && chooseTournament <= tournaments.size()) {
                    Tournament selectedTournament = tournaments.get(chooseTournament - 1);

                    System.out.print("Confirm delete? (yes/no): ");
                    String confirmDelete = input.nextLine();

                    if (confirmDelete.equalsIgnoreCase("yes")) {
                        tournaments.remove(selectedTournament);
                        System.out.println("Tournament successfully deleted.");
                    } else {
                        System.out.println("Tournament deletion cancelled.");
                    }

                    // Ask if the user wants to delete another tournament
                    System.out.print("Do you want to delete another tournament? (yes/no): ");
                    String deleteAgain = input.nextLine();

                    if (deleteAgain.equalsIgnoreCase("yes")) {
                        deleteTournament(events);
                    }
                } else {
                    System.out.println("Invalid tournament ID!");
                }
            } else {
                System.out.println("No tournaments found for the selected event!");
            }
        } else {
            System.out.println("Invalid event ID!");
        }
    }

    public static void printTournaments(List<Tournament> tournaments) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String headerFormat = "%-15s %-20s %-15s %-15s %-15s %-20s %-20s %-20s%n";
        String rowFormat = "%-15d %-20s %-15s %-15s %-15.2f %-20.2f %-20s %-20d%n";

        System.out.printf(headerFormat, "Tournament ID:", "Tournament Name:", "Start Date:", "End Date:", "Prize Pool:", "Register Fee:", "Game:", "Remaining Slots:");

        for (Tournament tournament : tournaments) {
            System.out.printf(rowFormat,
                    tournament.getId(),
                    tournament.getTournamentName(),
                    dateFormat.format(tournament.getStartDate()), // Format the start date
                    dateFormat.format(tournament.getEndDate()),   // Format the end date
                    tournament.getPrizePool(),
                    tournament.getTournamentFee(),
                    tournament.getGame().getGameName(),
                    tournament.getSlots());
        }
    }

    public void displayTournamentMenu(User Organizer, List<Event> events, List<Tournament> tournaments) {

        Scanner input = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nTournament Menu");
            System.out.println("1. Create Tournament");
            System.out.println("2. Edit  Tournament");
            System.out.println("3. Delete Tournament ");
            System.out.println("4. Display Tournaments");
            System.out.println("5. Quit");
            System.out.println("Enter choice:");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    Tournament.createTournaments(events, tournaments);
                    break;
                case 2:
                    Tournament.editTournament(events);
                    break;
                case 3:
                    Tournament.deleteTournament(events);
                    break;
                case 4:
                    Tournament.printTournaments(tournaments);
                    break;

            }
        } while (choice != 5);
    }
}
