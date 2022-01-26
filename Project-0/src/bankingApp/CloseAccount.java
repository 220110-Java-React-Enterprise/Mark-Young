package bankingApp;

import java.util.Scanner;
import static bankingApp.ViewManager.quitWord;

public class CloseAccount extends View {
    Scanner sc;
    ViewManager viewManager = ViewManager.getViewManager();
    String entry;
    Integer closeAcctTries;
    String entry2;
    Integer entryNumber;
    CustomArrayList<Integer> listOfAccounts = new CustomArrayList<>();
    CustomArrayList<CustomArrayList<Object>> accountList = new CustomArrayList<>();

    public CloseAccount(String name, ViewManager viewManager) {
        super(name, viewManager);
    }

    @Override
    public void renderView() {
        sc = viewManager.getScanner();
        closeAcctTries = 10;
        System.out.println("\n\n\n******************\n******************");
        System.out.println("CLOSE AN ACCOUNT");
        System.out.println("******************\n******************");
        BankAccountData bacctd = new BankAccountData();
        BankAccountRepo repo = new BankAccountRepo();
        accountList = repo.readAccount(bacctd);
        listOfAccounts = new CustomArrayList<>();

        for (int i = 0; i < accountList.size(); i++) {
            listOfAccounts.add(accountList.get(i).get(0));
            //System.out.print(listOfAccounts.get(i) + " ");
        }
        listAccounts(bacctd);
        //listOfAccounts = bacctd.getMyAccountIDList();
        while (closeAcctTries > 0) {
            System.out.print("please type the number of the account you wish to close: ");
            entry = sc.nextLine();
            if (entry.equalsIgnoreCase(quitWord)) {
                viewManager.quit();
                return;
            }
            try {
                entryNumber = Integer.parseInt(entry);
            } catch (NumberFormatException e) {
                closeAcctTries--;
                System.out.println("not a number.");
                continue;
            }
            if (listOfAccounts.contains(entryNumber) != -1) {
                System.out.print("are you sure you want to delete the account with ID " + BankAccountData.displayAccountID(entryNumber) + "?\n" +
                        "You will no longer be able to use this account, and transactions will be wiped from memory.\n" +
                        "This action cannot be reversed. Continue closing? (yes/no) ");
                entry2 = sc.nextLine();
                if (entry2.equalsIgnoreCase("yes")) {
                    System.out.println("closing account...");
                    bacctd.setAccountID(entryNumber);
                    repo = new BankAccountRepo();
                    repo.deleteAccount(bacctd);
                    System.out.println("Account closed.");
                    viewManager.navigate("User Home");
                    return;
                } else {
                    System.out.println("would you like to return to the homepage? (yes/no)");
                    entry = sc.nextLine();
                    if (entry.equalsIgnoreCase("yes")) {
                        viewManager.navigate("User Home");
                        return;
                    } else {
                        closeAcctTries--;
                        continue;
                    }
                }
            } else {
                closeAcctTries--;
                System.out.println("that is not one of your account numbers.");
                continue;
            }
        }
        if (closeAcctTries == 0) {
            System.out.println("timeout error. system shutting off.");
            viewManager.quit();
            return;
        }
    }

    static void listAccounts(BankAccountData bacctd) {
        UserHome.showAccounts(bacctd);
    }
}