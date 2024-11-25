

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


public class BankTransfer extends Payment {
    private long bankAcc;
    private String bankName;
    private String bankAccPw;
    private int otpCode;

    //constructor
    public BankTransfer(int paymentID, double paymentAmount, LocalDate paymentDate, String paymentMethod, Status paymentStatus, double discountAmount, String promoCode, long bankAcc, String bankName, String bankAccPw, int otpCode) {
        super(paymentID, paymentAmount, paymentDate, paymentMethod, paymentStatus, discountAmount, promoCode);
        this.bankAcc = bankAcc;
        this.bankName = bankName;
        this.bankAccPw = bankAccPw;
        this.otpCode = otpCode;
    }

    public static int bankPayment(Payment payment, List<Payment> paymentList) {
        Scanner input = new Scanner(System.in);

        char yesno = ' ';
        String bankName = null;
        int count = 3;
        int selectedBank = 0;
        int paymentId = payment.getPaymentID();

        do {
            System.out.println("\nBank option");
            System.out.println("1.Public Bank");
            System.out.println("2.MayBank");
            System.out.print("Select your Bank:");
            String selection = input.nextLine();

            try {
                selectedBank = Integer.parseInt(selection);
                switch (selectedBank) {
                    case 1:
                        bankName = "Public Bank";
                        break;
                    case 2:
                        bankName = "MayBank";
                        break;

                    default:
                        System.out.println("\033[31m Invalid Option !!!\033[0m");
                }
            } catch (NumberFormatException e) {
                System.out.println("\033[31mInvalid input. Please enter a valid integer.\033[0m");
            }


        } while (selectedBank < 1 || selectedBank > 2);

        do {
            //bank account
            System.out.print("Enter Bank Account: ");
            long bankAcc = input.nextLong();

            //clear buffer
            input.nextLine();

            System.out.print("Enter Password: ");
            String bankAccPw = input.nextLine();

            if (validateBankAcc(selectedBank, bankAcc) && validPassword(bankAccPw)) {
                while (count >= 0) {
                    int autoCode = createOtpCode();
                    System.out.println("OTP Code: " + autoCode);
                    System.out.print("Enter OTP Code: ");
                    int otpCode = input.nextInt();
                    if (validateOtpCode(autoCode, otpCode)) {
                        System.out.println("\nPayment Successful\n");
                        BankTransfer bankPayment = new BankTransfer(paymentId, payment.getPaymentAmount(), payment.getPaymentDate(), payment.getPaymentMethod(), Status.COMPLETED, payment.getDiscountAmount(), payment.getPromoCode(), bankAcc, bankName, bankAccPw, otpCode);
                        paymentList.add(bankPayment);
                        return paymentId;
                    } else if (count == 0) {
                        yesno = 'N';
                        break;
                    } else {
                        System.out.println("\033[31mInvalid otpCode (" + count + "/3)\033[0m");
                        count--;
                        System.out.print("Enter again? (Y=Yes/N=No): ");
                        yesno = input.next().charAt(0);
                        if (Character.toUpperCase(yesno) == 'N') {
                            count = 0;
                            System.out.println("\033[31mPayment Failed\033[0m");
                        }
                    }
                }


            } else if (!validateBankAcc(selectedBank, bankAcc)) {
                System.out.println("\033[31mInvalid Bank Account\033[0m");
                do {
                    System.out.print("Enter again? (Y=Yes/N=No): ");
                    yesno = input.next().charAt(0);
                    if (Character.toUpperCase(yesno) == 'N') {
                        return 0;
                    } else if (Character.toUpperCase(yesno) == 'Y') {
                        break;
                    } else
                        System.out.println("\033[31mInvalid Option\033[0m");
                } while (Character.toUpperCase(yesno) != 'Y' && Character.toUpperCase(yesno) != 'N');
            } else if (!validPassword(bankAccPw)) {
                System.out.println("\033[31mInvalid Bank Password \033[0m");
                do {
                    System.out.print("Enter again? (Y=Yes/N=No): ");
                    yesno = input.next().charAt(0);
                    if (Character.toUpperCase(yesno) == 'N') {
                        return 0;
                    } else if (Character.toUpperCase(yesno) == 'Y') {
                        break;
                    } else
                        System.out.println("\033[31mInvalid Option\033[0m");
                } while (Character.toUpperCase(yesno) != 'Y' && Character.toUpperCase(yesno) != 'N');
            }
        } while (Character.toUpperCase(yesno) == 'Y');
        return 0;
    }


    //validateBankName
    public static boolean validateBankAcc(int selectedBank, long bankAcc) {
        Scanner input = new Scanner(System.in);
        int length = String.valueOf(bankAcc).length();
        long firstNum = Long.parseLong(Long.toString(bankAcc).substring(0, 1));
        boolean valid = false;

        switch (selectedBank) {
            case 1:
                if ((length == 10 && firstNum == 6)) {
                    return valid = true;
                }
                break;

            case 2:
                if ((length == 11 && firstNum == 1)) {
                    return valid = true;
                }
                break;
        }
        return valid;
    }

    //validate password
    public static boolean validPassword(String bankAccPw) {

        boolean valid = false;
        if (bankAccPw.length() == 6) {
            for (int i = 0; i < bankAccPw.length(); i++) {
                valid = Character.isDigit(bankAccPw.charAt(i));
            }
        }
        return valid;
    }

    //generate otpCode
    public static int createOtpCode() {
        int min = 100000; // Minimum 6-digit number
        int max = 999999; // Maximum 6-digit number
        return (int) (Math.random() * ((max - min) + 1));
    }

    //validate otpCode
    public static boolean validateOtpCode(int autoCode, int otpCode) {
        return autoCode == otpCode;
    }


    //getter
    public long getBankAcc() {
        return bankAcc;
    }

    public String getBankName() {
        return bankName;
    }

    public int getOtpCode() {
        return otpCode;
    }

    //setter
    public void setBankAcc(long bankAcc) {
        this.bankAcc = bankAcc;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setBankAccPw(String bankAccPw) {
        this.bankAccPw = bankAccPw;
    }

    public void setOtpCode(int otpCode) {
        this.otpCode = otpCode;
    }

    @Override
    public String toString() {
        String line = "[---------------------------------";
        return super.toString() +
                "| Bank Account : " + bankAcc + "       |" +
                "\n| Bank Name    : " + bankName + "      |\n" + line;

    }
}
