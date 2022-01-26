package bankingApp;

import java.util.Scanner;
import java.util.regex.*;
import static bankingApp.ViewManager.quitWord;

// creates user account and ID in database
public class Register extends View {
    Scanner sc;
    ViewManager viewManager = ViewManager.getViewManager();
    Integer allRegisterItemsCt = 0;
    Boolean pass = false;
    UserData userData;
    UserRepo repo;

    static CustomArrayList allRegisterItems = new CustomArrayList<>(
            "first name", "last name", "username", "password", "re-enter password", "email address", "re-enter email address");

    // constructor
    public Register(String name, ViewManager viewManager) {
        super(name, viewManager);
    }

    // validates that given input is a name
    public boolean validName(String userInput) {
        if (userInput.equals("")) {
            return false;
        }
        char[] chars = userInput.toCharArray();
        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    // validates that given input is a username meeting given requirements
    public boolean validUsername(String userInput) {
        if (userInput.equals("")) {
            return false;
        }
        Pattern p = Pattern.compile("^[a-z]+[a-z0-9_-]+");//. represents single character.
        return p.matcher(userInput).matches();
    }

    // check if password is valid
    public boolean validPassword(String userInput) {
        if (userInput.equals("")) {
            return false;
        }
        char[] chars = userInput.toCharArray();
        Integer count = 0;
        Boolean hasUppercase = false;
        Boolean hasLowercase = false;
        Boolean hasNumber = false;
        for (char c : chars) {
            count++;
            int ascii = c;
            if(ascii >= 48 && ascii <= 57) {
                hasNumber = true;
            } else if (ascii >= 65 && ascii <= 90) {
                hasUppercase = true;
            } else if (ascii >= 97 && ascii <= 122) {
                hasLowercase = true;
            }
        }

        return hasNumber && hasUppercase && hasLowercase && count >= 6;
    }

    // check if email is valid
    public boolean validEmail(String userInput) {
        if (userInput.equals("")) {
            return false;
        }
        Pattern p = Pattern.compile("^[a-z]+[a-z0-9_-]+@[a-z]+[a-z0-9_-]+(\\.com|\\.org|\\.edu|\\.gov|\\.net)$");//. represents single character.
        return p.matcher(userInput).matches();
    }

    // workflow to register
    // through seven steps:
    // first name, last name, username
    // password, confirm password
    // email, confirm email
    @Override
    public void renderView() {
        sc = viewManager.getScanner();
        allRegisterItemsCt = 0;
        userData = new UserData();
        System.out.println("******************\n******************");
        System.out.println("REGISTER");
        System.out.println("******************\n******************");

        ////////////////////////
        // 1. setting first name
        ////////////////////////
        System.out.println("Remember that you can say \"" + quitWord + "\" at any time to exit the application.\n");
        String entry = "0";
        pass = false;
        do {
            System.out.print("Please enter the following to register as a new user: \n\n" +
                    allRegisterItems.get(allRegisterItemsCt) + ": ");
            entry = sc.nextLine().toLowerCase();
            if (entry.equals(quitWord)) {
                viewManager.quit();
                return;
            } else if (validName(entry)) {
                pass = true;
                userData.setFirstName(Character.toUpperCase(entry.charAt(0)) + entry.substring(1));
            } else {
                System.out.println("Invalid. Please try again. Make sure there are no spaces. \n\n\n");
            }
        } while (!pass);

        ////////////////////////
        // 2. setting last name
        ////////////////////////
        allRegisterItemsCt++;
        entry = "0";
        pass = false;
        do {
            System.out.print(
                    allRegisterItems.get(allRegisterItemsCt) + ": ");
            entry = sc.nextLine().toLowerCase();
            if (entry.equals(quitWord)) {
                viewManager.quit();
                return;
            } else if (validName(entry)) {
                userData.setLastName(Character.toUpperCase(entry.charAt(0)) + entry.substring(1));
                pass = true;
            } else {
                System.out.println("Invalid. Please try again. Make sure there are no spaces. \n\n\n");
            }
        } while (!pass);

        ////////////////////////
        // 3. setting username
        ////////////////////////
        allRegisterItemsCt++;
        entry = "0";
        pass = false;
        do {
            System.out.print("For your username, there are three requirements: \n" +
                    "1. Must start with a letter \n" +
                    "2. Must be at least two characters long \n" +
                    "3. Only letters (a-z), numbers (0-9), underscore (_), and hyphen (-) are allowed \n" +
                    "Not case sensitive \n" +
                    allRegisterItems.get(allRegisterItemsCt) + ": ");
            entry = sc.nextLine().toLowerCase();
            if (entry.equals(quitWord)) {
                viewManager.quit();
                return;
            } else if (validUsername(entry)) {
                userData.setUsername(entry);
                pass = true;
            } else {
                System.out.println("Invalid. Please try again. Make sure there are no spaces.\n\n\n\n");
            }
        } while (!pass);

        ////////////////////////
        // 4. setting password
        ////////////////////////
        allRegisterItemsCt++;
        entry = "0";
        pass = false;
        do {
            System.out.print("For your password, you must have at least: \n" +
                    "1. one (1) lowercase letter, \n" +
                    "2. one (1) uppercase letter, and \n" +
                    "3. one (1) number. \n" +
                    "4. at least six (6) characters total \n" +
                    "Please remember your password and/or keep it in a safe place! \n" +
                    allRegisterItems.get(allRegisterItemsCt) + ": ");
            entry = sc.nextLine();
            if (entry.equalsIgnoreCase(quitWord)) {
                viewManager.quit();
                return;
            } else if (validPassword(entry)) {
                userData.setPassword(entry);
                pass = true;
            } else {
                System.out.println("Invalid. Please try again. \n\n\n\n\n");
            }
        } while (!pass);

        ////////////////////////
        // 5. confirm password
        ////////////////////////
        allRegisterItemsCt++;
        String entry2 = "0";
        pass = false;
        do {
            System.out.print(
                    allRegisterItems.get(allRegisterItemsCt) + ": ");
            entry2 = sc.nextLine();
            if (entry.equals(quitWord)) {
                viewManager.quit();
                return;
            } else if (entry.equals(entry2)) {
                pass = true;
            } else {
                System.out.println("Entry does not match password. To exit the application and start over, please type \"" +
                        quitWord + "\" \n\n");
            }
        } while (!pass);

        ////////////////////////
        // 6. setting email address
        ////////////////////////
        allRegisterItemsCt++;
        entry = "0";
        pass = false;
        do {
            System.out.print("Please enter your email address below. \n" +
                    "Make sure it is valid. \n" +
                    allRegisterItems.get(allRegisterItemsCt) + ": ");
            entry = sc.nextLine().toLowerCase();
            if (entry.equals(quitWord)) {
                viewManager.quit();
                return;
            } else if (validEmail(entry)) {
                repo = new UserRepo();
                UserData userModel = new UserData();
                userModel.setEmail(entry);
                userModel = repo.readEmail(userModel);
                if(userModel == null) {
                    userData.setEmail(entry);
                    pass = true;
                } else {
                    System.out.println("unfortunately that email is already associated with a user.");
                    System.out.print("would you like to try another email address (yes/no)? ");
                    entry = sc.nextLine();
                    if (entry.equalsIgnoreCase("yes")) {
                        continue;
                    } else {
                        System.out.print("in that case, would you like to quit the program (yes/no) ? ");
                        entry = sc.nextLine();
                        if (entry.equalsIgnoreCase("yes")) {
                            viewManager.quit();
                            return;
                        } else {
                            System.out.print("in that case, would you like to restart registration (yes/no) ?");
                            entry = sc.nextLine();
                            if (entry.equalsIgnoreCase("yes")) {
                                viewManager.navigate("Register Screen");
                                break;
                            } else {
                                System.out.println("Thank you for using The Banking App. \nSystem " +
                                        "shutting down. \nYou may reboot the application if you would like.");
                                viewManager.quit();
                                return;
                            }
                        }
                    }
                }

            } else {
                System.out.println("Invalid email address. Please try again. \n\n\n\n\n");
            }
        } while (!pass);

        ////////////////////////
        // 7. confirm email address
        ////////////////////////
        allRegisterItemsCt++;
        entry2 = "0";
        pass = false;
        do {
            System.out.print(
                    allRegisterItems.get(allRegisterItemsCt) + ": ");
            entry2 = sc.nextLine().toLowerCase();
            if (entry.equals(quitWord)) {
                viewManager.quit();
                return;
            } else if (entry.equals(entry2)) {
                pass = true;
            } else {
                System.out.println("Entry does not match given email address. To exit the application " +
                        "and start over, please type \"quitWord\" \n\n");
            }
        } while (!pass);

        System.out.println("Hang on while we securely store your information...");
        repo.create(userData);
        System.out.println("Success! You have registered as a new user! " + "Your unique User ID is " +
                UserData.displayUserID(userData.getUserID()) + ". \nIt is a good idea to keep a record of it. " +
                "\n\nTaking you back to the start screen...\n\n");
        viewManager.navigate("Start Menu");
    }
}
