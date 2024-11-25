
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author chink
 */
public class Booking {
    private Integer id =0;
    private Integer userId =0;
    private Status status;
    private static LocalDate bookingDate;
    private int eventNumber;
    private static double vipSeatPrice;
    private static double normalSeatPrice;
    private String seatType;
    private int vipSeatsQty;
    private int normalSeatsQty;
    private int vipSeatArr[];
    private int cancelVipSeat[];
    private int cancelNormalSeat[];
    private int normalSeatArr[];
    private static int totalBooking =0;
    private static int bookingCount =0;
    private static int cancelCount=0;



    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);

    public Booking() {
    }

    public Booking(LocalDate bookingDate,String seatType, int seatQty, int[] vipSeatArr, int[] cancelVipSeat, int[] cancelNormalSeat, int[] normalSeatArr,Integer id, Integer userId,int eventNumber, double vipSeatPirce, double normalSeatPrice, Status status) {
        this.bookingDate = bookingDate;
        this.seatType = seatType;
        this.vipSeatsQty = vipSeatsQty;
        this.normalSeatsQty = normalSeatsQty;
        this.vipSeatArr = vipSeatArr;
        this.normalSeatArr = normalSeatArr;
        this.eventNumber = eventNumber;
        this.id = id;
        this.userId = userId;
        this.vipSeatPrice = vipSeatPrice;
        this.normalSeatPrice = normalSeatPrice;
        this.cancelVipSeat = cancelVipSeat;
        this.cancelNormalSeat = cancelNormalSeat;
        this.status = status;



    }

    public static void showSeat(char choice,Event event){
        Booking booking = new Booking();
        String[] Rows = { "Row 1 ", "Row 2 ", "Row 3 ", "Row 4 ", "Row 5 ", "Row 6 ", "Row 7 ", "Row 8 ", "Row 9 ", "Row 10 "};
        int count = 0;
        if(choice == 'V'){
            booking.setSeatType("VIP");
            booking.setVipSeatPrice(event.getVipPricePerTicket());
            System.out.print("\n"+ booking.getSeatType() +" "+"Zone");
            System.out.print("\nPricing for VIP Zone is : RM"  + booking.getVipSeatPrice());
            String[] columns = { "\n\tCol 1",  "Col 2",  "Col 3",  "Col 4" ,"Col 5","Col 6","Col 7","Col 8","Col 9","Col 10","Col 11","Col 12","Col 13","Col 14","Col 15"};
            for(int i = 0; i < 10; i++){
                System.out.print("\t" + columns[i]);
            }
            System.out.println();

            for(int row = 0; row < (double)event.getVipSeats().length/10; row++){
                System.out.print("Row"+" "+(row+1));
                for(int col = 0; col < 10; col++){
                    if(event.getVipSeats().length <= count){
                        break;
                    }
                    if(event.getVipSeats()[count] == 0 ){
                        ++count;
                        System.out.print("\tSeat" + count);

                    }else{
                        ++count;
                        System.out.print("\tX");
                    }
                }
                System.out.println();
            }
        }else{
            booking.setNormalSeatPrice(event.getNormalPricePerTicket());
            booking.setSeatType("Normal");
            System.out.print("\n" + booking.getSeatType() +" "+ "Zone");
            System.out.print("\nPricing for Normal Zone is : RM"  + booking.getNormalSeatPrice());
            String[] columns = { "\n\tCol 1",  "Col 2",  "Col 3",  "Col 4" ,"Col 5","Col 6","Col 7","Col 8","Col 9","Col 10","Col 11","Col 12","Col 13","Col 14","Col 15"};
            for(int i = 0; i < 10; i++){
                System.out.print("\t" + columns[i]);
            }
            System.out.println();
            for(int row = 0; row < (double)event.getNormalSeats().length/10; row++){
                System.out.print("Row"+" "+(row+1));
                for(int col = 0; col < 10; col++){
                    if(event.getNormalSeats().length <= count){
                        break;
                    }
                    if(event.getNormalSeats()[count] == 0 ){
                        ++count;
                        System.out.print("\tSeat" + count);
                    }else{
                        ++count;
                        System.out.print("\tX");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public static void makeBooking(List<Event> events,List<Booking> bookings,int userID, List<User> userList){
        char choice2,yesNo,confirm,confirm2,yesNo2;
        LocalDate myDateObj;
        int vipCount = 0,normalCount =0,purchaseCounter =5,counter =0;
        Scanner input = new Scanner(System.in);

        outer3:{
            System.out.print("\n ____ ____ ____ ____ ____ ____ ____ ");
            System.out.print("\n||B |||o |||o |||k |||i |||n |||g ||");
            System.out.print("\n||__|||__|||__|||__|||__|||__|||__||");
            System.out.print("\n|/__\\|/__\\|/__\\|/__\\|/__\\|/__\\|/__\\|");
            System.out.print("\n\n");

            Booking booking = new Booking();

            for (Event evs : events) {
                if (evs.getStatus() == Status.CREATED) {
                    System.out.println(evs);
                    counter++;
                }
            }
            if(counter <=0){
                System.out.print("\nThere have no available event can be book now!! ");
                break outer3;
            }
            System.out.print("\nEnter the number of the event you want to book : ");
            int choice = input.nextInt();
            while(choice > events.size() || choice ==0){
                System.out.print("\nInvalid event Number Plese try again :");
                choice = input.nextInt();
            }

            booking.setVipSeatArr(new int[5]);
            booking.setNormalSeatArr(new int[5]);
            booking.setCancelVipSeat(new int[5]);
            booking.setCancelNormalSeat(new int[5]);
            do{
                if(purchaseCounter <= 0){
                    System.out.print("Each booking only can book 5 times !");
                    break;
                }
                System.out.print(events.get(choice -1));
                System.out.print("\nWhat zone u like to book (V = VIP / N = Normal) :");
                choice2 = input.next().charAt(0);
                while(choice2 !='V' && choice2 !='N'){
                    System.out.print("\nInvalid choice please try again (V / N) :");
                    choice2 = input.next().charAt(0);
                }
                switch(choice2){
                    case 'V':
                        booking.setSeatType("VIP");
                        Booking.showSeat(choice2,events.get(choice-1));
                        System.out.print("\nEnter the number of seat u want to book :");
                        int vipSeatNum = input.nextInt();
                        for(int i=0; i < events.get((choice -1)).getVipSeats().length; i++){
                            if(events.get((choice -1)).getVipSeats().length > (vipSeatNum -1)){
                                if(events.get((choice -1)).getVipSeats()[(vipSeatNum-1)] == 1){
                                    System.out.print("\nThis seat has been booked !");
                                    System.out.print("\nTry again :");
                                    vipSeatNum = input.nextInt();
                                }
                            }else{
                                System.out.print("\nInvalid Seat Number Plese try again :");
                                vipSeatNum = input.nextInt();
                            }
                        }
                        System.out.print("\nConfirm want to book? (Y/N):");
                        confirm = input.next().charAt(0);

                        while(confirm != 'Y' && confirm != 'y' && confirm != 'N' && confirm != 'n'){
                            System.out.print("\nInvalid input plese try again (Y/N):");
                            confirm = input.next().charAt(0);
                        }
                        if(Character.toUpperCase(confirm) == 'Y'){
                            ++vipCount;
                            booking.setElementVipArr((vipCount-1), vipSeatNum);
                            events.get((choice -1)).setElementVip(events.get((choice -1)),(vipSeatNum-1), 1);
                            purchaseCounter--;
                            booking.setVipSeatsQty(vipCount);
                            break;
                        }else{
                            purchaseCounter++;
                            break;
                        }
                    case 'N' :
                        booking.setSeatType("Normal");
                        Booking.showSeat(choice2,events.get(choice-1));
                        System.out.print("\nEnter the number of seat u want to book :");
                        int normalSeatNum = input.nextInt();
                        for(int i=0; i < events.get((choice -1)).getNormalSeats().length; i++){
                            if(events.get((choice -1)).getNormalSeats().length > (normalSeatNum -1)){
                                if(events.get((choice -1)).getNormalSeats()[(normalSeatNum-1)] == 1){
                                    System.out.print("\nThis seat has been booked !");
                                    System.out.print("\nTry again :");
                                    normalSeatNum = input.nextInt();
                                }
                            }else{
                                System.out.print("\nInvalid Seat Number Plese try again :");
                                normalSeatNum = input.nextInt();
                            }
                        }
                        System.out.print("\nConfirm want to book? (Y/N):");
                        confirm2 = input.next().charAt(0);
                        while(confirm2 != 'Y' && confirm2 != 'y' && confirm2 != 'N' && confirm2 != 'n'){
                            System.out.print("\nInvalid plese try again (Y/N):");
                            confirm2 = input.next().charAt(0);
                        }
                        if(Character.toUpperCase(confirm2) == 'Y'){
                            ++normalCount;
                            booking.setElementNormalArr((normalCount -1), normalSeatNum);
                            events.get((choice -1)).setElementNormal(events.get((choice -1)),(normalSeatNum-1), 1);
                            purchaseCounter--;
                            booking.setNormalSeatsQty(normalCount);
                            break;
                        }else{
                            purchaseCounter++;
                            break;
                        }
                    default:System.out.print("Invalid choice please try again!");
                        break outer3;
                }
                System.out.print("\nDo you want to purchase more or Exit ? (Y = Continue N = Exit):");
                yesNo = input.next().charAt(0);
                Character.toUpperCase(yesNo);
                while(yesNo !='N' && yesNo != 'Y' && yesNo !='n' && yesNo !='y'){
                    System.out.print("\nInvalid choice please try again :");
                    yesNo = input.next().charAt(0);
                }
            }while(Character.toUpperCase(yesNo) != 'N');
            if(normalCount > 0 || vipCount > 0){
                booking.setId(++bookingCount);
                booking.setUserId(userID);
                booking.setEventNumber(choice);
                totalBooking += normalCount + vipCount;
                myDateObj = LocalDate.now();
                booking.setBookingDate(LocalDate.now());
                booking.setStatus(Status.UNPAID);
                normalCount=0;
                vipCount=0;
                bookings.add(booking);
            }
            System.out.print("Do you want to proceed to payment ? (Y/N):");
            yesNo2 = input.next().charAt(0);
            while(yesNo2 !='N' && yesNo2 != 'Y' && yesNo2 !='n' && yesNo2 !='y'){
                System.out.print("\nInvalid choice please try again :");
            }

            if (Character.toUpperCase(yesNo2) == 'Y') {
                System.out.print("\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                System.out.printf("%-20s %-13s%-8s%-13s%-13s%-14s%-17s%-16s%-19s%-12s%-16s%-12s\n",
                        "Booking References", "Date", "Event", "VIP Seat", "Normal Seat", "VipSeats Qty", "NormalSeats Qty", "VipSeats Price","NormalSeats Price","Cancel Vip","Cancel Normal","Status");
                System.out.print("\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                System.out.println(booking.toString());

                int vipSeatQty = booking.getVipSeatsQty();
                int normalSeatQty = booking.getNormalSeatsQty();
                double vipPrice = events.get(booking.getEventNumber() - 1).getVipPricePerTicket();
                double normalPrice = events.get(booking.getEventNumber() - 1).getNormalPricePerTicket();
                int discountRate = events.get(booking.getEventNumber()-1).getDiscountRate();
                Payment.calcPayment(vipPrice, vipSeatQty, normalPrice, normalSeatQty,discountRate, events, booking, bookings,userID,userList);
            }


        }
    }

    public static void cancelBooking(List<Event> events,List<Booking> bookings,int userId){

        Scanner input = new Scanner(System.in);
        char choice;
        int eNum =0,counter=0,count=0;
        int vCount=0,nCount=0;
        cancelCount++;
        outer1:{
            System.out.print("\n ____ ____ ____ ____ ____ ____ _________ ____ ____ ____ ____ ____ ____ ____ ");
            System.out.print("\n||C |||a |||n |||c |||e |||l |||       |||B |||o |||o |||k |||i |||n |||g ||");
            System.out.print("\n||__|||__|||__|||__|||__|||__|||_______|||__|||__|||__|||__|||__|||__|||__||");
            System.out.print("\n|/__\\|/__\\|/__\\|/__\\|/__\\|/__\\|/_______\\|/__\\|/__\\|/__\\|/__\\|/__\\|/__\\|/__\\|");
            System.out.print("\n\n");
            System.out.print("\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            System.out.printf("%-20s %-13s%-8s%-13s%-13s%-14s%-17s%-16s%-19s%-12s%-16s%-12s\n",
                    "Booking References", "Date", "Event", "VIP Seat", "Normal Seat", "VipSeats Qty", "NormalSeats Qty", "VipSeats Price","NormalSeats Price","Cancel Vip","Cancel Normal","Status");
            System.out.print("\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

            for (Booking booking : bookings) {
                if(booking.getUserId().equals(userId) && booking.getStatus()== Status.UNPAID){
                    System.out.print(booking);
                    System.out.print("\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                    count++;
                }
            }
            if(count <=0){
                System.out.println("\nYou dont have any booking can be cancel !!");
                break outer1;
            }

            System.out.print("\nEnter the Booking Reference u wanna to cancel : ");
            int bookingId = input.nextInt();
            if(bookings.get((bookingId -1)).getId() == bookingId){
                System.out.print("\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                System.out.printf("%-20s %-13s%-8s%-13s%-13s%-14s%-17s%-16s%-19s%-12s%-16s%-12s\n",
                        "Booking References", "Date", "Event", "VIP Seat", "Normal Seat", "VipSeats Qty", "NormalSeats Qty", "VipSeats Price","NormalSeats Price","Cancel Vip","Cancel Normal","Status");
                System.out.print("\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                System.out.print(bookings.get(bookingId -1));
                System.out.print("\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            }else{
                System.out.print("\nInvalid Booking Reference !");
                break outer1;
            }

            System.out.print("\nDo you want to cancel whole or particular seat (A = Whole / P = particular): ");
            char option = input.next().charAt(0);
            while(option !='A' && option!='a' && option!='P'&& option!='p'){
                System.out.print("\nInvalid choice please try again (A / P) :");
                option = input.next().charAt(0);
            }
            if(Character.toUpperCase(option) == 'A'){
                bookings.get((bookingId-1)).setStatus(Status.CANCELLED);
                for(int i =0 ;i < bookings.get((bookingId-1)).getVipSeatArr().length;i++){
                    if(bookings.get((bookingId-1)).getVipSeatArr()[i] > 0){
                        int seatV = bookings.get((bookingId-1)).getVipSeatArr()[i];
                        eNum = bookings.get(bookingId-1).getEventNumber();
                        events.get((eNum-1)).setElementVip((seatV-1), 0);
                    }
                }
                for(int i =0 ;i < bookings.get((bookingId-1)).getNormalSeatArr().length;i++){
                    if(bookings.get((bookingId-1)).getNormalSeatArr()[i] > 0){
                        int seatN = bookings.get((bookingId-1)).getNormalSeatArr()[i];
                        eNum = bookings.get(bookingId-1).getEventNumber();
                        events.get((eNum-1)).setElementNormal((seatN-1), 0);
                    }
                }
                System.out.print("\nYou has successfully cancelled Booking references: "+" "+ bookingId);
            }else{
                System.out.print("\nEnter the seat type (V = VIP / N = Normal): ");
                char type = input.next().charAt(0);
                while(type !='V' && type !='N'){
                    System.out.print("\nInvalid choice please try again (V / N) :");
                    type = input.next().charAt(0);
                }
                outer2:{
                    switch(type){
                        case 'V':
                            for(int i =0 ;i < bookings.get((bookingId-1)).getVipSeatArr().length;i++){
                                if(bookings.get((bookingId-1)).getVipSeatArr()[i] > 0){
                                    System.out.print("\nNumber of Seat"+""+bookings.get((bookingId-1)).getVipSeatArr()[i]);
                                    counter++;
                                }
                                if(counter <=0){
                                    System.out.print("\nYou dont have any booking for VIP seat please try again");
                                    break outer2;
                                }
                            }
                            System.out.print("\nEnter the No you want to cancel (1 ,2 ,3): ");
                            int cancelVipSeat = input.nextInt();

                            System.out.print("\nConfirm want to cancel it? (Y/N): ");
                            char confirm = input.next().charAt(0);
                            while(confirm !='N' && confirm != 'Y' && confirm !='n' && confirm !='y'){
                                System.out.print("\nInvalid choice please try again :");
                                confirm = input.next().charAt(0);
                            }
                            if(Character.toUpperCase(confirm) =='Y'){
                                eNum = bookings.get((bookingId-1)).getEventNumber();
                                events.get((eNum-1)).setElementVip((cancelVipSeat-1), 0);
                                for(int j=0; j< bookings.get((bookingId-1)).getVipSeatArr().length;j++){
                                    if(bookings.get((bookingId-1)).getVipSeatArr()[j] == cancelVipSeat){
                                        int vipQty = bookings.get((bookingId-1)).getVipSeatsQty() -1;
                                        bookings.get((bookingId-1)).setElementCancelVipSeat(cancelCount, cancelVipSeat);
                                        bookings.get((bookingId-1)).setElementVipArr(j, 0);
                                        bookings.get((bookingId-1)).setVipSeatsQty(vipQty);
                                        System.out.print("\nYou has successfully cancel Seat :"+ "" +cancelVipSeat);

                                    }
                                }
                            }
                            for(int i =0 ;i < bookings.get((bookingId-1)).getVipSeatArr().length;i++){
                                if(bookings.get((bookingId-1)).getVipSeatArr()[i] > 0){
                                    vCount++;
                                }
                            }
                            for(int i =0 ;i < bookings.get((bookingId-1)).getNormalSeatArr().length;i++){
                                if(bookings.get((bookingId-1)).getNormalSeatArr()[i] > 0){
                                    nCount++;
                                }
                            }

                            if(vCount <=0 && nCount <=0){
                                bookings.get((bookingId-1)).setStatus(Status.CANCELLED);
                            }
                            break;
                        case 'N':
                            for(int i =0 ;i < bookings.get((bookingId-1)).getNormalSeatArr().length;i++){
                                if(bookings.get((bookingId-1)).getNormalSeatArr()[i] > 0){
                                    System.out.print("\nNumber of Seat"+""+bookings.get((bookingId-1)).getNormalSeatArr()[i]);
                                    counter++;
                                }
                                if(counter <=0){
                                    System.out.print("\nYou dont have any booking for Normal seat please try again");
                                    break outer2;
                                }
                            }
                            System.out.print("\n Enter the No you want to cancel (1 ,2 ,3): ");
                            int cancelNormalSeat = input.nextInt();
                            System.out.print("\nConfirm want to cancel it? (Y/N): ");
                            char confirm2 = input.next().charAt(0);
                            while(confirm2 !='N' && confirm2 != 'Y' && confirm2 !='n' && confirm2 !='y'){
                                System.out.print("\nInvalid choice please try again :");
                                confirm = input.next().charAt(0);
                            }
                            if(Character.toUpperCase(confirm2 ) =='Y'){
                                eNum = bookings.get(bookingId-1).getEventNumber();
                                events.get((eNum-1)).setElementVip((cancelNormalSeat-1), 0);
                                for(int j=0; j< bookings.get((bookingId-1)).getNormalSeatArr().length;j++){
                                    if(bookings.get((bookingId-1)).getNormalSeatArr()[j] == cancelNormalSeat){
                                        int normalQty = bookings.get((bookingId-1)).getNormalSeatsQty() -1;
                                        bookings.get((bookingId-1)).setElementCancelNormalSeat(cancelCount,cancelNormalSeat);
                                        bookings.get((bookingId-1)).setElementNormalArr(j, 0);
                                        bookings.get((bookingId-1)).setNormalSeatsQty(normalQty);
                                        System.out.print("\nYou has successfully cancel Seat :"+ "" +cancelNormalSeat);

                                    }
                                }
                            }
                            for(int i =0 ;i < bookings.get((bookingId-1)).getVipSeatArr().length;i++){
                                if(bookings.get((bookingId-1)).getVipSeatArr()[i] > 0){
                                    vCount++;
                                }
                            }
                            for(int i =0 ;i < bookings.get((bookingId-1)).getNormalSeatArr().length;i++){
                                if(bookings.get((bookingId-1)).getNormalSeatArr()[i] > 0){
                                    nCount++;
                                }
                            }

                            if(vCount <=0 && nCount <=0){
                                bookings.get((bookingId-1)).setStatus(Status.CANCELLED);
                            }
                            break;
                        default:
                            System.out.println("Invalid choice!");
                            break;
                    }
                }
            }
        }
    }

    public static void displayBooking(List<Booking> bookings,int userID){
        int count =0;
        System.out.print("\n ____ ____ ____ ____ ____ ____ ____ _________ ____ ____ ____ ____ ____ ____ ____ ");
        System.out.print("\n||D |||i |||s |||p |||l |||a |||y |||       |||B |||o |||o |||k |||i |||n |||g ||");
        System.out.print("\n||__|||__|||__|||__|||__|||__|||__|||_______|||__|||__|||__|||__|||__|||__|||__||");
        System.out.print("\n|/__\\|/__\\|/__\\|/__\\|/__\\|/__\\|/__\\|/_______\\|/__\\|/__\\|/__\\|/__\\|/__\\|/__\\|/__\\|");
        System.out.print("\n\n");
        System.out.print("\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        System.out.printf("%-20s %-13s%-8s%-13s%-13s%-14s%-17s%-16s%-19s%-12s%-16s%-12s\n",
                "Booking References", "Date", "Event", "VIP Seat", "Normal Seat", "VipSeats Qty", "NormalSeats Qty", "VipSeats Price","NormalSeats Price","Cancel Vip","Cancel Normal","Status");
        System.out.print("\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        for(Booking booking :bookings){
            if(booking.getUserId().equals(userID)){
                System.out.print(booking);
                System.out.print("\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                count++;
            }
        }
        if(count<=0){
            System.out.println("\nYou dont have any booking !!");
        }
    }

    @Override
    public String toString() {
        String vip = "";
        for (int index = 0; index < this.getVipSeatArr().length; index++) {
            if(this.getVipSeatArr()[index] != 0){
                vip += " " + this.getVipSeatArr()[index];}

        }
        String normal = "";
        for (int index = 0; index < this.getNormalSeatArr().length; index++) {
            if(this.getNormalSeatArr()[index] != 0){
                normal += " " +this.getNormalSeatArr()[index];}

        }
        String cancelVip = "";
        for (int index = 0; index < this.getCancelVipSeat().length; index++) {
            if(this.getCancelVipSeat()[index] != 0){
                cancelVip += " " +this.getCancelVipSeat()[index];}

        }
        String cancelNormal = "";
        for (int index = 0; index < this.getCancelNormalSeat().length; index++) {
            if(this.getCancelNormalSeat()[index] != 0){
                cancelNormal += " " +this.getCancelNormalSeat()[index];}

        }

        return String.format("%-20s %-13s%-8s%-13s%-13s%-14s%-17s%-16s%-19s%-12s%-15s%-12s\n",
                this.getId(), this.getBookingDate().format(dateFormatter), this.getEventNumber(),vip,
                normal, this.getVipSeatsQty(), this.getNormalSeatsQty(),
                this.getVipSeatPrice(),this.getNormalSeatPrice(),cancelVip,cancelNormal,this.getStatus());
    }


    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public static LocalDate getBookingDate() {
        return bookingDate;
    }
    public static void setBookingDate(LocalDate bookingDate) {
        Booking.bookingDate = bookingDate;
    }
    public static double getVipSeatPrice() {
        return vipSeatPrice;
    }

    public static void setVipSeatPrice(double vipSeatPrice) {
        Booking.vipSeatPrice = vipSeatPrice;
    }

    public static double getNormalSeatPrice() {
        return normalSeatPrice;
    }

    public static void setNormalSeatPrice(double normalSeatPrice) {
        Booking.normalSeatPrice = normalSeatPrice;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public int getVipSeatsQty() {
        return vipSeatsQty;
    }

    public void setVipSeatsQty(int vipSeatsQty) {
        this.vipSeatsQty = vipSeatsQty;
    }

    public int getNormalSeatsQty() {
        return normalSeatsQty;
    }

    public void setNormalSeatsQty(int normalSeatsQty) {
        this.normalSeatsQty = normalSeatsQty;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int[] getVipSeatArr() {
        return vipSeatArr;
    }

    public void setVipSeatArr(int[] vipSeatArr) {
        this.vipSeatArr = vipSeatArr;
    }

    public int[] getNormalSeatArr() {
        return normalSeatArr;
    }

    public void setNormalSeatArr(int[] normalSeatArr) {
        this.normalSeatArr = normalSeatArr;
    }
    public void setElementVipArr(int index, int value) {
        if (index >= 0 && index < getVipSeatArr().length) {
            getVipSeatArr()[index] = value;
        } else {
            System.out.println("Invalid index");
        }
    }
    public void setElementNormalArr(int index, int value) {
        if (index >= 0 && index < getNormalSeatArr().length) {
            getNormalSeatArr()[index] = value;
        } else {
            System.out.println("Invalid index");
        }
    }

    public int getEventNumber() {
        return eventNumber;
    }

    public void setEventNumber(int eventNumber) {
        this.eventNumber = eventNumber;
    }

    public int[] getCancelVipSeat() {
        return cancelVipSeat;
    }

    public void setCancelVipSeat(int[] cancelVipSeat) {
        this.cancelVipSeat = cancelVipSeat;
    }

    public int[] getCancelNormalSeat() {
        return cancelNormalSeat;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setCancelNormalSeat(int[] cancelNormalSeat) {
        this.cancelNormalSeat = cancelNormalSeat;
    }

    public void setElementCancelVipSeat(int index, int value) {
        if (index >= 0 && index < getCancelVipSeat().length) {
            getCancelVipSeat()[index] = value;
        } else {
            System.out.println("Invalid index");
        }
    }

    public void setElementCancelNormalSeat(int index, int value) {
        if (index >= 0 && index < getCancelNormalSeat().length) {
            getCancelNormalSeat()[index] = value;
        } else {
            System.out.println("Invalid index");
        }
    }


}