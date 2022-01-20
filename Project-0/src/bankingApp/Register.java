package bankingApp;

import java.util.Scanner;

public class AccountUserInput {
    public static void main(String[] args) {

        String name = "";
        String username = "";
        String password = "";
        String passwordCheck = "";
        String email = "";
        String emailCheck = "";
        BankAccountData bacctd;

        Scanner sc = new Scanner(System.in);
        System.out.print("Welcome to The Banking App! \n     " +
                "Have you already registered with us? \nPlease answer \"yes\" or \"no\"");
        String registered = sc.nextLine(); // user inputs whether or not they have registered
        if (registered == "no") {
            bacctd = new BankAccountData();
            System.out.printf("Please enter the following to open a new account: \n\n" +
                    "First and Last Name, such as \"John Smith\": %s\n", name);
            System.out.printf("Username: %s\n", username);
            System.out.printf("Password: %s\n", password);
            System.out.printf("Verify password: %s\n", passwordCheck);
            System.out.printf("E-mail address: %s\n", email);
            System.out.printf("Verify e-mail address: %s\n", emailCheck);
        }
    }

    public boolean userDataValidate(String userInput, String compare, BankAccountData bacctd) {
        if (compare == "name1") {
            if (validName(userInput)) {
                bacctd.setName1(userInput);
                return true;
            } else {
                return false;
            }
        } else if (compare == "username") {

        } else if (compare == "password") {

        } else if (compare == "email") {

        }
    }

    public boolean validName(String userInput) {
        char[] chars = userInput.toCharArray();
        Integer spaceCount = 0;
        for (char c : chars) {
            if(!Character.isLetter(c)) {
                if(Character.equals(" ")) {
                    spaceCount++;
                    if(spaceCount > 1) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean validName(String userInput) {
        char[] chars = userInput.toCharArray();
        Integer spaceCount = 0;
        for (char c : chars) {
            if(!Character.isLetter(c)) {
                if(Character.equals(" ")) {
                    spaceCount++;
                    if(spaceCount > 1) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
