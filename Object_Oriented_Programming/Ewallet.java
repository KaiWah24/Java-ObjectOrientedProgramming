

/**
 * @author zhi ming eu
 */

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kxiao
 */
public class Ewallet extends Payment {
    private String phoneNum;
    private String eWalletPw;

    //constructor
    public Ewallet(int paymentID, double paymentAmount, LocalDate paymentDate, String paymentMethod, Status paymentStatus, double discountAmount, String promoCode, String phoneNum, String eWalletPw) {
        super(paymentID, paymentAmount, paymentDate, paymentMethod, paymentStatus, discountAmount, promoCode);
        this.phoneNum = phoneNum;
        this.eWalletPw = eWalletPw;
    }

    //make payment
    public static int ePayment(Payment payment, List<Payment> paymentList) {
        Scanner input = new Scanner(System.in);
        //variable
        String phoneNum;
        String eWalletPw;
        char yesno = ' ';
        int count = 3;
        int paymentId = payment.getPaymentID();

        //phoneNum
        do {
            System.out.print("Enter phone number -->");
            phoneNum = input.nextLine();
            if (!validPhoneNumber(phoneNum)) {
                System.out.println("\033[31mInvalid Phone Number ! Please Try Again!!\033[0m");
                do {
                    System.out.print("Enter again? (Y=Yes/N=No): ");
                    yesno = input.next().charAt(0);

                    if(Character.toUpperCase(yesno) == 'N')
                        return 0;

                    else if (Character.toUpperCase(yesno) == 'Y') {
                        // clear buffer
                        input.nextLine();
                        break;
                    }
                    else
                        System.out.println("Invalid Option");
                }while(Character.toUpperCase(yesno) != 'Y' || Character.toUpperCase(yesno) != 'N');

            } else {
                //password
                while (count > -1) {
                    System.out.print("Enter password(6 digits) -->");
                    eWalletPw = input.nextLine();
                    if (validPassword(eWalletPw)) {
                        System.out.println("\nPayment Successful\n");
                        Ewallet ePayment = new Ewallet(paymentId, payment.getPaymentAmount(), payment.getPaymentDate(), payment.getPaymentMethod(), Status.COMPLETED, payment.getDiscountAmount(), payment.getPromoCode(), phoneNum, eWalletPw);
                        paymentList.add(ePayment);
                        return paymentId;
                    }  else {
                        if(count == 0){
                            return 0;
                        }
                        System.out.println("\033[31mInvalid Password ! Please Try Again!! (" + count + "/3)\033[0m");
                        do {
                            System.out.print("Enter again? (Y=Yes/N=No): ");
                            yesno = input.next().charAt(0);

                            if (Character.toUpperCase(yesno) == 'N') {
                                count = 0;
                                break;
                            } else if (Character.toUpperCase(yesno) == 'Y') {
                                count--;
                                break;
                            } else {
                                System.out.println("Invalid Option");
                            }
                        }while(Character.toUpperCase(yesno) != 'Y' || Character.toUpperCase(yesno) != 'N');
                        //clear buffer
                        input.nextLine();
                    }
                }
                yesno = 'N';
            }
        } while (Character.toUpperCase(yesno) == 'Y');
        return 0;
    }

    //validate phone number
    public static boolean validPhoneNumber(String phoneNum) {
        Pattern pattern = Pattern.compile("^(01)[2-46-9][0-9]{7}$|^(01)[1][0-9]{8}$");
        Matcher matcher = pattern.matcher(phoneNum);
        return matcher.find() && matcher.group().equals(phoneNum);
    }

    //validate password
    public static boolean validPassword(String eWalletPw) {
        boolean valid = false;
        if (eWalletPw.length() == 6) {
            for (int i = 0; i < eWalletPw.length(); i++) {
                if (Character.isDigit(eWalletPw.charAt(i))) {
                    valid = true;
                } else {
                    valid = false;
                    break;
                }
            }

        }
        return valid;
    }

    //getter
    public String getPhoneNum() {
        return phoneNum;
    }

    public String geteWalletPw() {
        return eWalletPw;
    }


    //setter
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void seteWalletPw(String eWalletPw) {
        this.eWalletPw = eWalletPw;
    }


    @Override
    public String toString() {
        String line = "[---------------------------------";
        return super.toString() + "| Phone Number : " + phoneNum + "      |\n" + line;
    }
}