package bankingApp;

import java.util.Scanner;
import static bankingApp.ViewManager.quitWord;

public class Withdraw extends View {
    Scanner sc;
    ViewManager viewManager = ViewManager.getViewManager();
    String entry;
    Integer withdrawTries;
    Integer entryInteger;
    CustomArrayList<CustomArrayList<Object>> accountList = new CustomArrayList<>();
    CustomArrayList<Integer> accountIDList = new CustomArrayList<>();

    public Withdraw(String name, ViewManager viewManager) {
        super(name, viewManager);
    }

    @Override
    public void renderView() {
        sc = viewManager.getScanner();
        withdrawTries = 10;
        System.out.println("\n\n\n******************\n******************");
        System.out.println("WITHDRAW");
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
        while (withdrawTries > 0) {
            displayAccounts(bacctd);
            //accountList = bacctd.getMyAccountList();
            //accountIDList = bacctd.getMyAccountIDList();
            System.out.print("Which account would you like to withdraw from?\n" +
                    "Please enter the corresponding account ID: ");
            entry = sc.nextLine();
            if (entry.equalsIgnoreCase(quitWord)) {
                withdrawTries = 0;
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
                withdrawTries--;
                System.out.println("That is not a valid account ID.");
                continue;
            }
            if (accountIDList.contains(entryInteger) == -1) {
                withdrawTries--;
                System.out.println("That is not one of your account IDs.");
                continue;
            } else {
                bacctd.setFromAccountID(entryInteger);
                System.out.print("How much would you like to withdraw? ");
                entry = sc.nextLine();
                if (entry.equalsIgnoreCase(quitWord)) {
                    withdrawTries = 0;
                    viewManager.quit();
                    return;
                }
                try {
                    entryNumber = Double.parseDouble(entry);
                    if (Math.floor(entryNumber*100) != entryNumber*100) {
                        withdrawTries--;
                        System.out.println("Please denote deposit amount in dollars and cents. Smaller amounts of money than one cent are not accepted.");
                        continue;
                    } else if (entryNumber > (Double) accountList.get(accountIDList.contains(bacctd.getFromAccountID())).get(2)) {
                        withdrawTries--;
                        System.out.println("You do not have enough in your account to withdraw that amount.");
                        continue;
                    } else {
                        System.out.println("withdrawing " + BankAccountData.displayCurrency(entryNumber) + " ...");
                        bacctd.setMyAccountList(accountList);
                        repo.withdraw(bacctd, entryNumber);
                        System.out.println("Your amount has been withdrawn!");
                        viewManager.navigate("User Home");
                        return;
                    }
                } catch (NumberFormatException e) {
                    withdrawTries--;
                    System.out.println("That was not a valid amount.");
                }
            }
        }
        if (withdrawTries == 0) {
            System.out.println("timeout occurred. application will shut down.");
            viewManager.quit();
            return;
        }
    }
    void displayAccounts(BankAccountData bacctd) {
        UserHome.showAccounts(bacctd);
    }
}
