package bankingApp;

import java.util.Scanner;
import static bankingApp.ViewManager.quitWord;

// deposits money in chosen owned account
// persists through database but also stored in local BankAccountData instance
public class Deposit extends View {
    Scanner sc;
    ViewManager viewManager = ViewManager.getViewManager();
    String entry;
    Integer depositTries;
    Integer entryInteger;
    CustomArrayList<CustomArrayList<Object>> accountList = new CustomArrayList<>();
    CustomArrayList<Integer> accountIDList = new CustomArrayList<>();

    // constructor
    public Deposit(String name, ViewManager viewManager) {
        super(name, viewManager);
    }

    // workflow of depositing into an account
    // reads user's accounts from database
    // accepts and validates account ID and deposit amount from user
    // returns to user's homepage if all goes well
    @Override
    public void renderView() {
        sc = viewManager.getScanner();
        depositTries = 10;
        System.out.println("\n\n\n******************\n******************");
        System.out.println("DEPOSIT");
        System.out.println("******************\n******************");
        System.out.println("Type \"home\" to go back to your homepage.");

        Double entryNumber;
        BankAccountRepo repo = new BankAccountRepo();
        BankAccountData bacctd = new BankAccountData();
        accountList = repo.readAccount(bacctd);
        accountIDList = new CustomArrayList<>();

        for (int i = 0; i < accountList.size(); i++) {
            accountIDList.add(accountList.get(i).get(0));
            //System.out.print(listOfAccounts.get(i) + " ");
        }

        while (depositTries > 0) {
            displayAccounts(bacctd);
            //accountList = bacctd.getMyAccountList();
            //accountIDList = bacctd.getMyAccountIDList();
            System.out.print("Which account would you like to deposit into?\n" +
                    "Please enter the corresponding account ID: ");
            entry = sc.nextLine();
            if (entry.equalsIgnoreCase(quitWord)) {
                depositTries = 0;
                viewManager.quit();
                return;
            }
            if (entry.equalsIgnoreCase("HOME")) {
                viewManager.navigate("User Home");
                return;
            }
            try {
                entryInteger = Integer.parseInt(entry);
            } catch (NumberFormatException e) {
                depositTries--;
                System.out.println("That is not a valid account ID.");
                continue;
            }
            if (accountIDList.contains(entryInteger) == -1) {
                depositTries--;
                System.out.println("That is not one of your account IDs.");
                continue;
            } else {
                while (depositTries > 0) {
                    bacctd.setToAccountID(entryInteger);
                    System.out.print("How much would you like to deposit? ");
                    entry = sc.nextLine();
                    if (entry.equalsIgnoreCase(quitWord)) {
                        depositTries = 0;
                        viewManager.quit();
                        return;
                    }
                    try {
                        entryNumber = Double.parseDouble(entry);
                        if (Math.floor(entryNumber*100) != entryNumber*100) {
                            depositTries--;
                            System.out.println("Please denote deposit amount in dollars and cents. Smaller amounts of money than one cent are not accepted.");
                            continue;
                        } else {
                            System.out.println("depositing " + BankAccountData.displayCurrency(entryNumber) + " ...");
                            repo.deposit(bacctd, entryNumber);
                            System.out.println("Your amount has been deposited!");
                            viewManager.navigate("User Home");
                            return;
                        }
                    } catch (NumberFormatException e) {
                        depositTries--;
                        System.out.println("That was not a valid amount.");
                    }
                }
            }
        }
        if (depositTries == 0) {
            System.out.println("timeout occurred. application will shut down.");
            viewManager.quit();
            return;
        }
    }

    // pulling static showAccounts() method from another class
    void displayAccounts(BankAccountData bacctd) {
        UserHome.showAccounts(bacctd);
    }
}
