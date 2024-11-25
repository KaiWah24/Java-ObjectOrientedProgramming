

/**
 * @author zhi ming eu
 */

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * @author kxiao
 */
public class CreditCard extends Payment {
    private long creditCardNo;
    private int expiredMonth, expiredYear;
    private int ccvCode;

    //constructor
    public CreditCard(int paymentID, double paymentAmount, LocalDate paymentDate, String paymentMethod, Status paymentStatus, double discountAmount, String promoCode, long creditCardNo, int expiredMonth, int expiredYear, int ccvCode) {
        super(paymentID, paymentAmount, paymentDate, paymentMethod, paymentStatus, discountAmount, promoCode);
        this.creditCardNo = creditCardNo;
        this.expiredMonth = expiredMonth;
        this.expiredYear = expiredYear;
        this.ccvCode = ccvCode;
    }

    public static int cardPayment(Payment payment, List<Payment> paymentList) {
        Scanner input = new Scanner(System.in);
        //variable
        long creditCardNo;
        int expiredMonth;
        int expiredYear;
        int cvvCode;
        char yesno;
        int count = 3;
        int paymentId = payment.getPaymentID();
        boolean valid = false;
        //cardNo
        do {
            System.out.print("Enter Credit Card Number:");
            creditCardNo = input.nextLong();
            if (!CreditCard.validCreditCardNo(creditCardNo)) {
                System.out.println("\033[31mInvalid Card Number !!! \033[0m");
                do {
                    System.out.print("Enter again? (Y=Yes/N=No): ");
                    yesno = input.next().charAt(0);
                    if (Character.toUpperCase(yesno) == 'N') {
                        return 0;
                    } else if (Character.toUpperCase(yesno) == 'Y') {
                        break;
                    } else
                        System.out.println("Invalid Option");
                } while (Character.toUpperCase(yesno) != 'Y' || Character.toUpperCase(yesno) != 'N');
            } else {
                valid = true;
                yesno = 'N';
            }
        } while (Character.toUpperCase(yesno) == 'Y');

        if (valid) {
            System.out.println("Valid Card");
            //expiredDate
            do {
                System.out.println("Enter expired date");
                System.out.print("   Month(1-12) --> ");
                expiredMonth = input.nextInt();
                System.out.print("   Year(YYYY) --> ");
                expiredYear = input.nextInt();
                if (!CreditCard.validDate(expiredMonth, expiredYear)) {
                    System.out.println("\033[31mInvalid Date ! Please Try Again!! \033[0m");
                    do {
                        System.out.print("Enter again? (Y=Yes/N=No): ");
                        yesno = input.next().charAt(0);
                        if (Character.toUpperCase(yesno) == 'N') {
                            return 0;
                        } else if (Character.toUpperCase(yesno) == 'Y') {
                            break;
                        } else
                            System.out.println("Invalid Option");
                    } while (Character.toUpperCase(yesno) != 'Y' || Character.toUpperCase(yesno) != 'N');
                }
                else
                    yesno = 'N';
            } while (Character.toUpperCase(yesno) == 'Y');

            while (count >= 0) {
                System.out.print("Enter cvv --> ");
                cvvCode = input.nextInt();
                if (!validCvvCode(cvvCode)) {
                    if(count == 0){
                        return 0;
                    }
                    System.out.println("\033[31mInvalid Cvv ! Please Try Again!! (" + count + "/3) \033[0m");
                    do {
                        System.out.print("Enter again? (Y=Yes/N=No): ");
                        yesno = input.next().charAt(0);
                        if (Character.toUpperCase(yesno) == 'Y') {
                            count--;
                            break;
                        }
                        else if (Character.toUpperCase(yesno) == 'N')
                            return 0;
                        else
                            System.out.println("\033[31mInvalid Option\033[0m");
                    } while (Character.toUpperCase(yesno) != 'Y' && Character.toUpperCase(yesno) != 'N');
                } else {
                    CreditCard card = new CreditCard(paymentId, payment.getPaymentAmount(), payment.getPaymentDate(), payment.getPaymentMethod(), Status.COMPLETED, payment.getDiscountAmount(), payment.getPromoCode(), creditCardNo, expiredMonth, expiredYear, cvvCode);
                    paymentList.add(card);
                    System.out.println("\nPayment Successful\n");
                    return paymentId;
                }
            }
        }
        return 0;
    }


    //validate creditCard
    public static boolean validCreditCardNo(long creditCardNo) {
        //long creditCard = this.creditCardNum;
        int length = String.valueOf(creditCardNo).length();
        long firstNum = Long.parseLong(Long.toString(creditCardNo).substring(0, 1));
        boolean valid;

        if ((length == 16 && firstNum == 4) || (length == 16 && firstNum == 5)) {
            valid = (((sumOfOddPlace(creditCardNo)) + (sumOfEvenPlace(creditCardNo))) % 10 == 0);
        } else {
            valid = false;
        }
        return valid;
    }


    // method to sum odd number
    public static int sumOfOddPlace(long creditCardNo) {
        int sumOdd = 0;
        long tempCardNum1 = creditCardNo;

        for (int i = 0; i < 16; i += 2) {
            sumOdd += tempCardNum1 % 10;
            tempCardNum1 /= 100;
        }
        return sumOdd;
    }


    // method to sum even number
    public static int sumOfEvenPlace(long d) {

        int sumEven = 0;
        long digit, tempCardNum2 = d / 10;

        for (int i = 1; i < 16; i += 2) {
            digit = (tempCardNum2 % 10) * 2;
            sumEven += digit % 10;
            digit /= 10;
            sumEven += digit % 100;
            tempCardNum2 /= 100;
        }

        return sumEven;
    }


    //validate date input
    public static boolean validDate(int expiredMonth, int expiredYear) {
        LocalDate date = LocalDate.now();
        int currentMonth = date.getMonthValue();
        int currentYear = date.getYear();
        boolean valid;

        if (expiredYear < currentYear || (expiredYear == currentYear && expiredMonth < currentMonth))
            valid = false;
        else
            valid = true;

        return valid;
    }


    //validate cvvCode
    public static boolean validCvvCode(int cvvCode) {
        boolean valid = false;
        int length = String.valueOf(cvvCode).length();


        valid = length == 3;
        return valid;
    }


    //getter
    public long getCreditCardNo() {
        return creditCardNo;
    }

    public int getExpiredMonth() {
        return expiredMonth;
    }

    public int getExpiredYear() {
        return expiredYear;
    }

    public int getCcvCode() {
        return ccvCode;
    }


    //setter
    public void setCreditCardNo(long creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    public void setExpiredMonth(int expiredMonth) {
        this.expiredMonth = expiredMonth;
    }

    public void setExpiredYear(int expiredYear) {
        this.expiredYear = expiredYear;
    }

    public void setCcvCode(int ccvCode) {
        this.ccvCode = ccvCode;
    }

    @Override
    public String toString() {
        String line = "[---------------------------------";
        return super.toString() + "| creditCardNo : " + creditCardNo + " |\n" + line;

    }


}