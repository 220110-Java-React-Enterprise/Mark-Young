package bankingApp;

import java.util.Scanner;
import static bankingApp.ViewManager.quitWord;

// start screen where you can decide whether to register as a new user or log in
public class StartMenu extends View {
    Scanner sc;
    ViewManager viewManager = ViewManager.getViewManager();

    // constructor
    public StartMenu(String name, ViewManager viewManager) {
        super(name, viewManager);
    }

    // display start screen
    // and navigate to given screen
    @Override
    public void renderView() {
        sc = viewManager.getScanner();
        String registerLogin = "";
        Integer registerLoginTries = 5;
        System.out.println("***********************\nWelcome to The Banking App!  \n***********************\n" +
                "Enter \"" + quitWord + "\" at any time to exit the application.");
        do {
            System.out.print(
                    "What would you like to do? \n" +
                    "REGISTER - register a new user \n" +
                    "LOGIN - log in with your username and password \n" +
                    "QUIT - close the application\n" +
                    "Please type one of the above words: ");
            registerLogin = sc.nextLine();
            if (registerLogin.equalsIgnoreCase(quitWord)) {
                viewManager.quit();
                return;
            }
            if (registerLogin.equalsIgnoreCase("register")) {
                viewManager.navigate("Register Screen");
                registerLoginTries = 0;
                break;
            } else if (registerLogin.equalsIgnoreCase("login")) {
                viewManager.navigate("Login Screen");
                break;
            } else {
                registerLoginTries--;
                if (registerLoginTries == 0) {
                    System.out.println("Too many errors. System Shutting Down.");
                    viewManager.quit();
                    return;
                } else {
                    System.out.println("Sorry, that is not a valid option. Make sure you did not enter any spaces. You have " + registerLoginTries + " more tries before system closes.\n\n\n\n");
                }
            }
        } while (registerLoginTries > 0);
    }
}
