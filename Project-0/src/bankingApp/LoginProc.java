package bankingApp;

import java.util.Scanner;
import static bankingApp.ViewManager.quitWord;

// logs a user into their homepage, where they can create and update accounts
public class LoginProc extends View {
    Scanner sc;
    Integer loginTries;
    String entry;
    UserRepo repo;
    Boolean wrongLogin;
    ViewManager viewManager = ViewManager.getViewManager();

    // constructor
    public LoginProc(String name, ViewManager viewManager) {
        super(name, viewManager);
    }

    // workflow for logging in
    // takes in email, which is unique, so pulls exactly zero or one rows of userData from database
    // waits for user to confirm username and password
    // takes user to their homepage if all goes well
    @Override
    public void renderView() {
        sc = viewManager.getScanner();
        loginTries = 10;
        entry = "0";
        System.out.println("\n\n");
        System.out.println("******************\n******************");
        System.out.println("LOG IN");
        System.out.println("******************\n******************");
        System.out.println("You have " + loginTries + " tries to log in.");
        System.out.print("Email: ");
        entry = sc.nextLine();
        if (entry.equalsIgnoreCase(quitWord)) {
            viewManager.quit();
            return;
        }
        repo = new UserRepo();
        UserData model1 = new UserData();
        model1.setEmail(entry.toLowerCase());
        model1 = repo.readEmail(model1);
        if (model1 == null) {
            wrongLogin = true;
            while (wrongLogin) {
                System.out.print("That email address is not in our system. \nWould you like to: \n" +
                        "REGISTER - register as a new user, or\n" +
                        "RETRY - try logging in with a different email\n" +
                        "Please enter one of the above words here: ");
                entry = sc.nextLine();
                if (entry.equalsIgnoreCase(quitWord)) {
                    viewManager.quit();
                    return;
                }
                if (entry.equalsIgnoreCase("REGISTER")) {
                    wrongLogin = false;
                    viewManager.navigate("Register Screen");
                    return;
                } else if (entry.equalsIgnoreCase("RETRY")) {
                    wrongLogin = false;
                    viewManager.navigate("Login Screen");
                    return;
                } else {
                    System.out.print("That was not one of the options. Would you like to exit the application (yes/no) ? ");
                    entry = sc.nextLine();
                    if (entry.equalsIgnoreCase("NO")) {
                        continue;
                    } else {
                        wrongLogin = false;
                        viewManager.quit();
                        return;
                    }
                }
            }

        }
        System.out.print("Username: ");
        entry = sc.nextLine();
        while (!entry.equalsIgnoreCase(model1.getUsername()) && loginTries > 0) {
            if (entry.equalsIgnoreCase(quitWord)) {
                viewManager.quit();
                return;
            }
            loginTries--;
            System.out.println("Incorrect username. " + loginTries + " tries left. You can say \"" + quitWord + "\" to exit the application");
            System.out.print("Username: ");
            entry = sc.nextLine();
        }
        if (loginTries == 0) {
            System.out.println("Max login tries reached. Sorry, you will have to come back later. Shutting down.");
            viewManager.quit();
            return;
        }
        System.out.print("Password: ");
        entry = sc.nextLine();
        while (!entry.equals(model1.getPassword()) && loginTries > 0) {
            if (entry.equalsIgnoreCase(quitWord)) {
                viewManager.quit();
                return;
            }
            loginTries--;
            System.out.println("Incorrect password. " + loginTries + " tries left. You can say \"" + quitWord + "\" to exit the application");
            System.out.print("Password: ");
            entry = sc.nextLine();
        }
        if (loginTries == 0) {
            System.out.println("Max login tries reached. Sorry, you will have to come back later. Shutting down.");
            viewManager.quit();
            return;
        }
        System.out.println("Thank you for logging in!");
        System.out.println("Now taking you to your homepage...\n");
        model1.setCurrentEmail(model1.getEmail());
        model1.setCurrentUserID(model1.getUserID());
        model1.setCurrentFirstName(model1.getFirstName());

        viewManager.navigate("User Home");
    }
}
