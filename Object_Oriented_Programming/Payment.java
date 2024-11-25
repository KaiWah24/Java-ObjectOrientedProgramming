/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */


/**
 * @author zhi ming eu
 */

import java.time.LocalDate;
import java.util.*;

/**
 * @author kxiao
 */
public class Payment {

    //variable
    private int paymentID;
    private double paymentAmount;
    private LocalDate paymentDate;
    private String paymentMethod;
    private Status paymentStatus;
    private double discountAmount;
    private static int nextPaymentId = 1001;
    private String promoCode;

    //constructor
    /*-------------------------------------------------------------------------------------------------------------------------*/
    public Payment(int paymentID, double paymentAmount, LocalDate paymentDate, Status paymentStatus, double discountAmount, String promoCode) {
        this.paymentID = paymentID;
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
        this.discountAmount = discountAmount;
        this.promoCode = promoCode;
    }

    public Payment(int paymentID, double paymentAmount, LocalDate paymentDate, String paymentMethod, Status paymentStatus, double discountAmount, String promoCode) {
        this.paymentID = paymentID;
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.discountAmount = discountAmount;
        this.promoCode = promoCode;
        nextPaymentId++;
    }
    /*-------------------------------------------------------------------------------------------------------------------------*/


    //getter
    /*-------------------------------------------------------------------------------------------------------------------------*/
    public int getPaymentID() {
        return paymentID;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Status getPaymentStatus() {
        return paymentStatus;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public static int getNextPaymentId() {
        return nextPaymentId;
    }

    public String getPromoCode() {
        return promoCode;
    }
    /*-------------------------------------------------------------------------------------------------------------------------*/

    //setter
    /*-------------------------------------------------------------------------------------------------------------------------*/
    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaymentStatus(Status paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public static void setNextPaymentId(int nextPaymentId) {
        Payment.nextPaymentId = nextPaymentId;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }
    /*-------------------------------------------------------------------------------------------------------------------------*/

    //Audiences
    /*-------------------------------------------------------------------------------------------------------------------------*/

    //calculate audiences booking payment
    public static void calcPayment(double vipPrice, int vipSeatQty, double normalPrice, int normalSeatQty,int discountRate, List<Event> events, Booking booking, List<Booking> bookings, int userID, List<User> userList) {
        Scanner input = new Scanner(System.in);
        char yesno;
        String promoCode = " ";
        double discountGiven = 0;
        double grandTotal = 0;
        double normalTotalPrice = sumTotal(normalPrice, normalSeatQty);
        double vipTotalPrice = sumTotal(vipPrice, vipSeatQty);
        double subTotal = sumTotal(vipTotalPrice, normalTotalPrice);
        int totalSeatQty = sumTotal(vipSeatQty, normalSeatQty);
        int eventID = booking.getEventNumber() - 1;
        LocalDate bookingDate = LocalDate.now();
        int paymentID = Payment.getNextPaymentId();
        int bookingID = booking.getId();

        do {
            System.out.print("\nAny Promo Code? (Y=Yes / N=No):");
            yesno = input.next().charAt(0);

            if (Character.toUpperCase(yesno) == 'Y') {
                promoCode = checkPromoCode(events, eventID);

                if (promoCode != null) {
                    discountGiven = calcDiscountGiven(discountRate, subTotal);
                    grandTotal = subTotal - discountGiven;
                }
            } else if (Character.toUpperCase(yesno) == 'N') {
                grandTotal = subTotal;
            } else System.out.println("\033[31mInvalid Option ! Please Try Again!! \033[0m");

        } while (Character.toUpperCase(yesno) != 'N' && Character.toUpperCase(yesno) != 'Y');

        Payment newPayment = paymentDetails(paymentID, bookingDate, bookingID, events, eventID, vipPrice, vipSeatQty, vipTotalPrice, normalPrice, normalSeatQty, normalTotalPrice, totalSeatQty, subTotal, promoCode, discountGiven, grandTotal, userID, userList);
        Payment.paymentMethod(newPayment, bookings, booking);

    }

    //display audiences booking payment details
    public static Payment paymentDetails(int paymentID, LocalDate bookingDate, int bookingID, List<Event> events, int eventID, double vipPrice, int vipSeatQty, double vipTotalPrice, double normalPrice, int normalSeatQty, double normalTotalPrice, int totalSeatQty, double subTotal, String promoCode, double discountGiven, double grandTotal, int userID, List<User> userList) {
        String line = "[----------------------------------------------------------------------------------------------------------]\n";
        String headerRowFormat = "|%-40s|%3s%9S%3s|%2s%14s%2s|%3s%8s%3s|%3s%9s%3s|\n";
        String contentRowFormat = "|%-40s|%4s%-6s%-5s|%16.2f%-2s|%6s%2d%6s|%12.2f%-3s|\n";
        String userName = null;

        System.out.print("\n" + line);
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() == userID) {
                userName = userList.get(i).getName();
            }
        }
        System.out.printf("%-106s%s\n", "| User ID: " + userID, " |");
        System.out.printf("%-106s%s\n", "| User Name: " + userName, " |");
        System.out.printf("|%-106s|", " ");
        System.out.printf("\n%-80s ", "| Booking ID: " + bookingID);
        System.out.printf("%26s|\n", "Date: " + bookingDate);
        System.out.print(line);
        System.out.printf(headerRowFormat, "Event", " ", "Seat Type", " ", " ", "Unit Price(RM)", " ", " ", "Quantity", " ", " ", "Total(RM)", " ");
        System.out.print(line);

        // Print VIP Seat Details
        System.out.printf(contentRowFormat, events.get(eventID).getEventName()," ", "VIP"," ", vipPrice," "," ", vipSeatQty," ", vipTotalPrice," ");

        // Print Normal Seat Details
        System.out.printf(contentRowFormat, " "," ", "Normal"," ", normalPrice," "," ", normalSeatQty," ", normalTotalPrice," ");

        for (int i = 0; i < 5; i++) {
            System.out.printf("|%-40s|%15s|%18s|%14s|%15s|\n", " ", " ", " ", " ", " ");
        }
        System.out.print(line);

        // Calculate and Print Subtotal
        System.out.printf("|Sub Total: %64s %6s%2d%6s %12.2f%-3s|\n", " "," ", totalSeatQty," ", subTotal, " ");
        System.out.print(line);
        System.out.printf("|Promo Code    : %-20s%70s|\n", promoCode, " ");
        System.out.printf("|Discount Given: %83s%.2f%3s|\n", " ", discountGiven, " ");
        System.out.print(line);
        System.out.printf("|Grand Total   : %75s%12.2f%3s|\n", " ", grandTotal, " ");
        System.out.print(line);

        return new Payment(paymentID, grandTotal, bookingDate, Status.UNPAID, discountGiven, promoCode);
    }

    //payment method for audiences booking
    public static void paymentMethod(Payment payment, List<Booking> bookings, Booking booking) {
        Scanner input = new Scanner(System.in);
        List<Payment> paymentList = new ArrayList<>();
        int paymentId;
        int methodSelection = 0;

        do {
            System.out.println("\nPayment Method");
            System.out.println("1.Credit Card");
            System.out.println("2.E-Wallet");
            System.out.println("3.Bank Transfer");
            System.out.println("4.Cancel Payment");
            System.out.print("Enter payment method -->");
            String selection = input.nextLine();

            try{
                methodSelection = Integer.parseInt(selection);
                switch (methodSelection) {
                    case 1:
                        payment.setPaymentID(Payment.getNextPaymentId());
                        payment.setPaymentMethod("Credit Card");
                        paymentId = CreditCard.cardPayment(payment, paymentList);

                        if (paymentId != 0) {
                            for (int i = 0; i < paymentList.size(); i++) {
                                if (paymentId == paymentList.get(i).getPaymentID()) {
                                    System.out.println(paymentList);
                                    for (Booking value : bookings) {
                                        if (Objects.equals(booking.getId(), value.getId()))
                                            value.setStatus(Status.COMPLETED);
                                    }
                                }
                            }
                        } else System.out.println("\033[31mPayment Failed\033[0m");
                        break;

                    case 2:
                        payment.setPaymentID(Payment.getNextPaymentId());
                        payment.setPaymentMethod("E-Wallet");
                        paymentId = Ewallet.ePayment(payment, paymentList);
                        if (paymentId != 0) {
                            for (int i = 0; i < paymentList.size(); i++) {
                                if (paymentId == paymentList.get(i).getPaymentID()) {
                                    System.out.println(paymentList);
                                    for (Booking value : bookings) {
                                        if (Objects.equals(booking.getId(), value.getId()))
                                            value.setStatus(Status.COMPLETED);
                                    }
                                }
                            }
                        } else System.out.println("\033[31mPayment Failed !!! \033[0m");
                        break;

                    case 3:
                        payment.setPaymentID(Payment.getNextPaymentId());
                        payment.setPaymentMethod("Bank Transfer");
                        paymentId = BankTransfer.bankPayment(payment, paymentList);
                        if (paymentId != 0) {
                            for (int i = 0; i < paymentList.size(); i++) {
                                if (paymentId == paymentList.get(i).getPaymentID()) {
                                    System.out.println(paymentList);
                                    for (Booking value : bookings) {
                                        if (Objects.equals(booking.getId(), value.getId()))
                                            value.setStatus(Status.COMPLETED);
                                    }
                                }
                            }
                        } else System.out.println("\033[31mPayment Failed !!! \033[0m");
                        break;

                    case 4:
                        break;

                    default:
                        System.out.println("\033[31mInvalid Option ! Please Try Again!! \033[0m");
                }
                break;
            }catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }


        } while (methodSelection > 4 || methodSelection < 1);
    }

    //Player
    /*-------------------------------------------------------------------------------------------------------------------------*/

    //calculate and display player register tournament payment details
    public static int registerDetails(int userID, String userName, String inGameName, int tournamentId, String tournamentName, String gameName, Date tournamentStartDate, Date registerDate, double tournamentFee) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tournamentStartDate);
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date earlyBirdDate = calendar.getTime();
        int discountRate = 0;
        String promoCode = " ";
        int paymentID = Payment.getNextPaymentId();
        LocalDate paymentDate = LocalDate.now();
        String line = "[----------------------------------------------------------------------------------------------------------]\n";


        if (registerDate.before(earlyBirdDate)) {
            discountRate = 10; // Apply early bird discount
            promoCode = "EarlyBird";
        }
        double discountGiven = calcDiscountGiven(discountRate,tournamentFee);
        double totalPrice = tournamentFee - discountGiven;
        System.out.print("\n" + line);
        System.out.println(" User ID: " + userID);
        System.out.println(" User Name: " + userName);
        System.out.println(" User In Game Name: " + inGameName);
        System.out.print(line);
        System.out.println(" Tournament ID: " + tournamentId);
        System.out.println(" Tournament Name: " + tournamentName);
        System.out.println(gameName);
        System.out.println(" Promo Code: " + promoCode);
        System.out.print(line);
        System.out.println(" Discount Given: RM" + discountGiven);
        System.out.println(" Total: RM" + totalPrice);
        System.out.print(line);


        Payment registerPayment = new Payment(paymentID, totalPrice, paymentDate, Status.UNPAID, discountGiven, promoCode);
        return paymentMethod(registerPayment);
    }

    //payment method for players registration
    public static int paymentMethod(Payment registerPayment) {
        Scanner input = new Scanner(System.in);
        List<Payment> paymentList = new ArrayList<>();
        int paymentId;
        int methodSelection = 0;

        do {
            System.out.println("\nPayment Method");
            System.out.println("1.Credit Card");
            System.out.println("2.E-Wallet");
            System.out.println("3.Bank Transfer");
            System.out.println("4.Cancel Payment");
            System.out.print("Enter payment method -->");
            String selection = input.nextLine();

            try{
                methodSelection = Integer.parseInt(selection);
                switch (methodSelection) {
                    case 1:
                        registerPayment.setPaymentID(Payment.getNextPaymentId());
                        registerPayment.setPaymentMethod("Credit Card");
                        paymentId = CreditCard.cardPayment(registerPayment, paymentList);


                        if (paymentId == 0) {
                            System.out.println("Payment Failed");
                            return 0;
                        }
                        else {
                            for (int i = 0; i < paymentList.size(); i++) {
                                if (paymentId == paymentList.get(i).getPaymentID()) {
                                    System.out.println(paymentList);
                                    break;
                                }
                            }
                        }
                        break;
                    case 2:
                        registerPayment.setPaymentID(Payment.getNextPaymentId());
                        registerPayment.setPaymentMethod("E-Wallet");
                        paymentId = Ewallet.ePayment(registerPayment, paymentList);
                        if (paymentId == 0) {
                            System.out.println("\033[31mPayment Failed !!! \033[0m");
                            return 0;
                        }
                        else {
                            for (int i = 0; i < paymentList.size(); i++) {
                                if (paymentId == paymentList.get(i).getPaymentID()) {
                                    System.out.println(paymentList);
                                    break;
                                }
                            }
                        }
                        break;
                    case 3:
                        registerPayment.setPaymentID(Payment.getNextPaymentId());
                        registerPayment.setPaymentMethod("Bank Transfer");
                        paymentId = BankTransfer.bankPayment(registerPayment, paymentList);
                        if (paymentId == 0) {
                            System.out.println("\033[31mPayment Failed !!! \033[0m");
                            return 0;
                        }
                        else {
                            for (int i = 0; i < paymentList.size(); i++) {
                                if (paymentId == paymentList.get(i).getPaymentID()) {
                                    System.out.println(paymentList);
                                    break;
                                }
                            }
                        }
                        break;
                    case 4:
                        return 0;

                    default:
                        System.out.println("\033[31mInvalid Option ! Please Try Again!! \033[0m");
                }
                break;
            }catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }


        } while (methodSelection > 4 || methodSelection < 1);
        return 1;
    }

    /*------------------------------------------------------------------------------------------------------------------*/

    //checkPromoCode
    public static String checkPromoCode(List<Event> events, int eventID) {
        Scanner input = new Scanner(System.in);
        char yesno;
        String promoCode;
        do {

            System.out.print("Enter Promo Code: ");
            promoCode = input.nextLine();

            if (promoCode.equals(events.get(eventID).getPromoCode())) {
                System.out.println("Valid Promo Code");
                return promoCode;
            } else {
                    System.out.println("Invalid Promo Code");
                do {
                    System.out.print("Enter again? (Y=Yes/N=No): ");
                    yesno = input.next().charAt(0);

                    if (Character.toUpperCase(yesno) == 'N') {
                        return " ";
                    }
                    else if(Character.toUpperCase(yesno) == 'Y')
                    {
                        //clear buffer
                        input.nextLine();
                        break;
                    }
                    else
                        System.out.println("Invalid Option");

                }while(Character.toUpperCase(yesno) != 'Y' || Character.toUpperCase(yesno) != 'N');
            }

        } while (Character.toUpperCase(yesno) == 'Y');
        return null;
    }

    //calculate totalPrice
    public static double sumTotal(double Price, int SeatQty) {
        return Price * SeatQty;
    }

    public static double sumTotal(double vipTotalPrice, double normalTotalPrice) {
        return vipTotalPrice + normalTotalPrice;
    }

    public static int sumTotal(int vipSeatQty, int normalSeatQty) {
        return vipSeatQty + normalSeatQty;
    }

    //calculateDicountGiven
    public static double calcDiscountGiven(double discountRate, double totalPrice) {
        return totalPrice * (discountRate / 100);
    }

    @Override
    public String toString() {
        String header = "---------------------------------]";
        String line = "|---------------------------------|";
        return String.format("%30s\n%-13s%-7s%15s\n%-30s\n%-15s%-16s%2s\n%-15s%-16s%2s\n%-15s%-12.2f%3s\n%-15s%-15s%3s\n%-15s%-15s%3s\n", header, "| ", "Receipt", " |", line, "| PaymentID    : ", paymentID, "|", "| PaymentDate  : ", paymentDate, "|", "| PaymentAmount: RM ", paymentAmount, "|", "| PaymentStatus: ", paymentStatus, "|", "| PaymentMethod: ", paymentMethod, "|");
    }
}




















