

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author chink
 */
public class Audiences extends User {
    private double loyaltyPoint;

    public Audiences() {
    }

    public Audiences(String name, String email, String password, String phone, int age, String gender, String role) {
        super(name, email, password, phone, age, gender, role);
        loyaltyPoint = 0.00;
    }


    public static void accountMenu(List<User> userList,List<Event> events,List<Booking> bookings,int userId){
        Scanner input = new Scanner(System.in);

        int choice;
        char yesNo;
        do{
            for(User user :userList){
                if(user.getId()== userId){
                    System.out.println("\nWelcome "+" "+user.getName());
                }
            }
            System.out.println("\nMenu");
            System.out.println("1. View Available Events");
            System.out.println("2. Make Booking");
            System.out.println("3. View Booking History");
            System.out.println("4. Cancel Booking");
            System.out.println("5. View Profile");
            System.out.println("6. Eidt Profile");
            System.out.println("7. Log Out");
            System.out.println("Enter choice:");
            choice =input.nextInt();

            switch(choice){
                case 1:
                    if(events.isEmpty())
                        System.out.println("There have no available event !!");
                    else {
                        for(Event event : events){
                            if(event.getStatus().equals(Status.CREATED)){
                                System.out.println(event);
                            }
                        }
                    }
                    break;
                case 2:
                    Booking.makeBooking(events, bookings,userId, userList);
                    break;
                case 3:
                    char yesNo1;
                    Booking.displayBooking(bookings, userId);
                    for (int i = 0; i < bookings.size(); i++) {
                        if (bookings.get(i).getStatus() == Status.UNPAID) {
                            System.out.println("You have booking still under Unpaid status !!");
                            System.out.println("Do u want to proceed to make Payment?? (Y/N):");
                            do {
                                 yesNo1 = input.next().charAt(0);
                                if (Character.toUpperCase(yesNo1) == 'Y') {
                                    System.out.print("Enter Booking ID:");
                                    int bookingID = input.nextInt();

                                    for (int j = 0; i < bookings.size(); j++) {
                                        if (bookings.get(j).getId().equals(bookingID)) {
                                            Booking currentBooking = bookings.get(j);
                                            System.out.print("\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                                            System.out.printf("%-20s %-13s%-8s%-13s%-13s%-14s%-17s%-16s%-19s%-12s%-16s%-12s\n",
                                                    "Booking References", "Date", "Event", "VIP Seat", "Normal Seat", "VipSeats Qty", "NormalSeats Qty", "VipSeats Price", "NormalSeats Price", "Cancel Vip", "Cancel Normal", "Status");
                                            System.out.print("\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                                            System.out.println(currentBooking.toString());

                                            int vipSeatQty = currentBooking.getVipSeatsQty();
                                            int normalSeatQty = currentBooking.getNormalSeatsQty();
                                            double vipPrice = events.get(currentBooking.getEventNumber() - 1).getVipPricePerTicket();
                                            double normalPrice = events.get(currentBooking.getEventNumber() - 1).getNormalPricePerTicket();
                                            int discountRate = events.get(currentBooking.getEventNumber() - 1).getDiscountRate();
                                            Payment.calcPayment(vipPrice, vipSeatQty, normalPrice, normalSeatQty, discountRate, events, currentBooking, bookings, userId, userList);
                                            break;
                                        } else if (j > bookings.size()) {
                                            System.out.println("Booking ID not found");
                                            break;
                                        }
                                    }
                                } else if (Character.toUpperCase(yesNo1) == 'N') {
                                    break;
                                } else {
                                    System.out.println("\033[31mInvalid Option\033[0m");
                                    System.out.print("Enter again? (Y=Yes/N=No): ");
                                }
                            } while (Character.toUpperCase(yesNo1) != 'Y' && Character.toUpperCase(yesNo1) != 'N');
                            break;
                        }
                        break;
                    }
                    break;
                case 4:
                    Booking.cancelBooking(events, bookings,userId);
                    break;
                case 5:
                    Audiences.displayProfile(userList, userId);
                    break;
                case 6:
                    Audiences.editProfile(userList, userId);
                    break;
                case 7:System.out.println("Logging out...");
                    break;
                default: System.out.println("Wrong choice! Pls enter again");
                    break;
            }
        }while(choice != 7);

    }

    @Override
    public void printWelcomeMessage() {
        System.out.println("Hello " + this.getName() + "! Feel free to explore various exciting events!");
    }


    public static void displayProfile(List<User> userList,int userId){
        System.out.print("\n ____ ____ ____ ____ ____ ____ ____ _________ ____ ____ ____ ____ ____ ____ ____ ");
        System.out.print("\n||D |||i |||s |||p |||l |||a |||y |||       |||P |||r |||o |||f |||i |||l |||e ||");
        System.out.print("\n||__|||__|||__|||__|||__|||__|||__|||_______|||__|||__|||__|||__|||__|||__|||__||");
        System.out.print("\n|/__\\|/__\\|/__\\|/__\\|/__\\|/__\\|/__\\|/_______\\|/__\\|/__\\|/__\\|/__\\|/__\\|/__\\|/__\\|");
        System.out.print("\n\n");

        for (User user : userList) {
            if (user.getId()== userId) {
                System.out.println(user);
            }
        }
    }

    //getter
    public double getLoyaltyPoint() {
        return loyaltyPoint;
    }

    //setter
    public void setLoyaltyPoint(double loyaltyPoint) {
        this.loyaltyPoint = loyaltyPoint;
    }

    public static void editProfile(List<User> userList,int userId){
        Scanner input = new Scanner(System.in);
        char confirm;
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[\\a-zA-Z]{2,6}";
        Pattern pattern = Pattern.compile(regex);
        String regex2 = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
        Pattern p = Pattern.compile(regex2);
        Pattern p2 = Pattern.compile("\\d{3}-\\d{7}");

        System.out.print("\n ____ ____ ____ ____ _________ ____ ____ ____ ____ ____ ____ ____ ");
        System.out.print("\n||E |||d |||i |||t |||       |||P |||r |||o |||f |||i |||l |||e ||");
        System.out.print("\n||__|||__|||__|||__|||_______|||__|||__|||__|||__|||__|||__|||__||");
        System.out.print("\n|/__\\|/__\\|/__\\|/__\\|/_______\\|/__\\|/__\\|/__\\|/__\\|/__\\|/__\\|/__\\|");
        System.out.print("\n\n");
        for(int i = 0;i<userList.size();i++){
            if(userList.get(i).getId() == userId){
                System.out.println("1.Your Name :"+userList.get(i).getName());
                System.out.println("2.Your Email :"+userList.get(i).getEmail());
                System.out.println("3.Your Password :"+userList.get(i).getPassword());
                System.out.println("4.Your Phone Number :"+userList.get(i).getPhone());
                System.out.println("5.Your Age :"+userList.get(i).getAge());
                System.out.println("6.Your Gender :"+userList.get(i).getGender());
                System.out.println("7.Quit ");
                System.out.print("Enter which information u wanna to modify (1,2,3):");
                int choice = input.nextInt();

                input.nextLine();

                switch(choice){
                    case 1:
                        System.out.print("Enter New Name:");
                        String modifyName = input.nextLine();
                        while(modifyName.isEmpty() == true){
                            System.out.print("\nName field is empty please enter again!");
                            System.out.print("\nRe-Enter name: ");
                            modifyName = input.nextLine();
                        }
                        System.out.print("\nConfirm want to change? (Y/N):");
                        confirm = input.next().charAt(0);
                        while(confirm !='N' && confirm != 'Y' && confirm !='n' && confirm !='y'){
                            System.out.print("\nInvalid choice please try again :");
                            confirm = input.next().charAt(0);
                        }
                        if(Character.toUpperCase(confirm) == 'Y'){
                            userList.get(i).setName(modifyName);
                            System.out.print("\nYou have successful edit the name");
                            break;
                        }else{
                            break;
                        }
                    case 2:
                        System.out.print("Enter New Email (user12@gmail.com):");
                        String modifyEmail = input.nextLine();
                        while(modifyEmail.isEmpty() == true || pattern.matcher(modifyEmail).matches()!= true){
                            if(modifyEmail.isEmpty() == true){
                                System.out.print("\nEmail field is empty please enter again!");
                                System.out.print("\nRe-Enter Email (user@gmail.com):");
                                modifyEmail = input.nextLine();
                            }else if(pattern.matcher(modifyEmail).matches()!= true){
                                System.out.print("\nIncorrect Email format please follow this format (user123@gamil.com)");
                                System.out.print("\nRe-Enter Email (user@gmail.com):");
                                modifyEmail = input.nextLine();
                            }
                        }
                        System.out.print("\nConfirm want to change? (Y/N):");
                        confirm = input.next().charAt(0);
                        if(confirm !='N' && confirm != 'Y' && confirm !='n' && confirm !='y'){
                            System.out.print("\nInvalid choice please try again :");
                            confirm = input.next().charAt(0);
                        }
                        if(Character.toUpperCase(confirm) == 'Y'){
                            userList.get(i).setEmail(modifyEmail);
                            System.out.print("\nYou have successful edit the email");
                            break;
                        }else{
                            break;
                        }
                    case 3:
                        System.out.print("Enter New Password (User12@):");
                        String modifyPassword = input.nextLine();
                        while(modifyPassword.isEmpty() == true || p.matcher(modifyPassword).matches()!= true){
                            if(modifyPassword.isEmpty() == true){
                                System.out.print("\nPassword field is empty please enter again!");
                                System.out.print("\nRe-Enter Password (User12@):");
                                modifyPassword = input.nextLine();
                            }else if(p.matcher(modifyPassword).matches()!= true){
                                System.out.print("\nPassword must contain one digit, one lowercase alphabet, one uppercase alphabet");
                                System.out.print("and one special character and at least 8 characters and at most 20 characters.");

                                System.out.print("\nRe-Enter Password (User12@):");
                                modifyPassword = input.nextLine();

                            }
                        }
                        System.out.print("\nConfirm want to change? (Y/N):");
                        confirm = input.next().charAt(0);
                        if(confirm !='N' && confirm != 'Y' && confirm !='n' && confirm !='y'){
                            System.out.print("\nInvalid choice please try again :");
                            confirm = input.next().charAt(0);
                        }
                        if(Character.toUpperCase(confirm) == 'Y'){
                            userList.get(i).setPassword(modifyPassword);
                            System.out.print("\nYou have successful edit the password");
                            break;
                        }else{
                            break;
                        }
                    case 4:
                        System.out.print("Enter New Phone Number (017-1234567):");
                        String modifyPhoneNum = input.nextLine();
                        while(modifyPhoneNum.isEmpty() == true || p2.matcher(modifyPhoneNum).matches()!= true){
                            if(modifyPhoneNum.isEmpty() == true){
                                System.out.print("\nPhone Number field is empty please enter again!");
                                System.out.print("\nRe-Enter Phone number (017-1234567):");
                                modifyPhoneNum = input.nextLine();

                            }else if(p2.matcher(modifyPhoneNum).matches()!= true){
                                System.out.print("\nWrong phone format please follow this format 017-1234567");
                                System.out.print("\nRe-Enter Phone number (017-1234567):");
                                modifyPhoneNum = input.nextLine();
                            }
                        }
                        System.out.print("\nConfirm want to change? (Y/N):");
                        confirm = input.next().charAt(0);
                        if(confirm !='N' && confirm != 'Y' && confirm !='n' && confirm !='y'){
                            System.out.print("\nInvalid choice please try again :");
                            confirm = input.next().charAt(0);
                        }
                        if(Character.toUpperCase(confirm) == 'Y'){
                            userList.get(i).setPhone(modifyPhoneNum);
                            System.out.print("\nYou have successful edit the phone number");
                            break;
                        }else{
                            break;
                        }
                    case 5:
                        System.out.print("Enter Age:");
                        int modifyAge = input.nextInt();
                        while(modifyAge <= 13 || modifyAge >=100){
                            System.out.print("\nAge field cannot enter less than 13 and more than 100!");
                            System.out.print("\nRe-Enter Age :");
                            modifyAge = input.nextInt();
                        }
                        System.out.print("\nConfirm want to change? (Y/N):");
                        confirm = input.next().charAt(0);
                        if(confirm !='N' && confirm != 'Y' && confirm !='n' && confirm !='y'){
                            System.out.print("\nInvalid choice please try again :");
                            confirm = input.next().charAt(0);
                        }
                        if(Character.toUpperCase(confirm) == 'Y'){
                            userList.get(i).setAge(modifyAge);
                            System.out.print("\nYou have successful edit the age");
                            break;
                        }else{
                            break;
                        }
                    case 6:
                        System.out.print("Enter Gender (M= Male / F= Female):");
                        String modifyGender = input.nextLine();
                        while(modifyGender.isEmpty() == true ){
                            System.out.print("\nGender field is empty please enter again!");
                            System.out.print("\nRe-Enter Gender (M = Male / F = Female) :");
                            modifyGender = input.nextLine();
                        }
                        while(!modifyGender.equals("M")  && !modifyGender.equals("F") && !modifyGender.equals("m") && !modifyGender.equals("f")){
                            System.out.print("\nInvalid input please try to enter (M / F)");
                            System.out.print("\nRe-Enter Gender (M = Male / F = Female) :");
                            modifyGender = input.nextLine();
                        }
                        if(modifyGender.equals("M") || modifyGender.equals("m")){
                            modifyGender = "Male";
                        }else if(modifyGender.equals("F") || modifyGender.equals("f")){
                            modifyGender ="Female";
                        }
                        System.out.print("\nConfirm want to change? (Y/N):");
                        confirm = input.next().charAt(0);
                        if(confirm !='N' && confirm != 'Y' && confirm !='n' && confirm !='y'){
                            System.out.print("\nInvalid choice please try again :");
                            confirm = input.next().charAt(0);
                        }
                        if(Character.toUpperCase(confirm) == 'Y'){
                            userList.get(i).setGender(modifyGender);
                            System.out.print("\nYou have successful edit the gender");
                            break;
                        }else{
                            break;
                        }
                    case 7:System.out.print("Quit ...."); break;
                    default:System.out.print("Invalide choice try again ...."); break;
                }
            }
        }
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

        System.out.println("Signing as Audience");

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
        User audiences = new Audiences(userName, userEmail, userPassword, userPhoneNum, userAge, userGender, "Audience");
        userList.add(audiences);
        System.out.println("Sign-up successful.");

    }

    @Override
    public String toString() {
        return super.toString() + "\nloyaltyPoint=" + loyaltyPoint;
    }

    @Override
    public void generateReport() {

    }
}