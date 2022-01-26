package bankingApp;

import java.util.Scanner;
import static bankingApp.ViewManager.quitWord;

public class CreateAcctProc extends View {
    Scanner sc;
    ViewManager viewManager = ViewManager.getViewManager();
    String entry;
    Integer createAcctTries;

    public CreateAcctProc(String name, ViewManager viewManager) {
        super(name, viewManager);
    }

    @Override
    public void renderView() {
        sc = viewManager.getScanner();
        createAcctTries = 10;
        System.out.println("\n\n\n******************\n******************");
        System.out.println("CREATE AN ACCOUNT");
        System.out.println("******************\n******************");
        System.out.println("Type \"home\" to go back to your homepage.");
        while (createAcctTries > 0) {
            System.out.println("What kind of account would you like?");
            System.out.print("CHECKING - good for frequent transfers and bill pay, earning low interest rate\n" +
                    "SAVINGS - good for earning a bit more interest, but penalties for frequent transfers and withdrawals\n" +
                    "Which kind of account would you like to open? ");
            entry = sc.nextLine();
            BankAccountData bacctd = new BankAccountData();
            BankAccountRepo repo = new BankAccountRepo();
            if (entry.equalsIgnoreCase(quitWord)) {
                System.out.println("shutting down...");
                viewManager.quit();
                return;
            }
            if (entry.equalsIgnoreCase("HOME")) {
                viewManager.navigate("User Home");
                return;
            }
            if (entry.equalsIgnoreCase("checking")) {
                System.out.println("Creating checking account...");
                bacctd.setAccountType("checking");
                bacctd.setCheckingSavings("checking");
                bacctd = repo.createAccount(bacctd);
                System.out.println("Congratulations! Your checking account has been created with account ID " +
                        BankAccountData.displayAccountID(bacctd.getAccountID()) + ".");
                viewManager.navigate("User Home");
                return;
            } else if (entry.equalsIgnoreCase("savings")) {
                System.out.println("Creating savings account...");
                bacctd.setAccountType("savings");
                bacctd.setCheckingSavings("savings");
                bacctd = repo.createAccount(bacctd);
                System.out.println("Congratulations! Your savings account has been created with account ID " +
                        BankAccountData.displayAccountID(bacctd.getAccountID()) + ".");
                viewManager.navigate("User Home");
                return;
            } else {
                createAcctTries--;
                System.out.println("that is not a valid account type.");
            }
        }
        System.out.println("timeout error. Application shutting down. Bye!");
        viewManager.quit();
    }
}
