package bankingApp;

import java.util.Scanner;
import static bankingApp.ViewManager.quitWord;

// user can view transaction history of a chosen account
public class ViewHistory extends View {
    Scanner sc;
    ViewManager viewManager = ViewManager.getViewManager();
    CustomArrayList<Integer> listOfAccounts = new CustomArrayList<>();
    String entry;
    Integer viewHistoryTries;
    Integer entryNumber;
    CustomArrayList<CustomArrayList<Object>> accountList = new CustomArrayList<>();

    // constructor
    public ViewHistory(String name, ViewManager viewManager) {
        super(name, viewManager);
    }

    // workflow to display transactions
    // accepts and validates account ID
    // prints out transactions
    // waits for user to move back to homepage
    @Override
    public void renderView() {
        sc = viewManager.getScanner();
        viewHistoryTries = 10;
        System.out.println("******************");
        System.out.println("VIEW TRANSACTION HISTORY");
        System.out.println("******************");
        BankAccountData bacctd = new BankAccountData();
        BankAccountRepo repo = new BankAccountRepo();

        accountList = repo.readAccount(bacctd);
        listOfAccounts = new CustomArrayList<>();

        for (int i = 0; i < accountList.size(); i++) {
            listOfAccounts.add(accountList.get(i).get(0));
            //System.out.print(listOfAccounts.get(i) + " ");
        }
        //System.out.println(" ");
//        for (int i = 0; i < listOfAccounts.size(); i++) {
//            System.out.print(listOfAccounts.get(i) + " ");
//        }

        while (viewHistoryTries > 0) {
            System.out.print("please type the ID of the account whose transactions you wish to view: ");
            entry = sc.nextLine();
            if (entry.equalsIgnoreCase(quitWord)) {
                viewManager.quit();
                return;
            }
            try {
                entryNumber = Integer.parseInt(entry);
            } catch (NumberFormatException e) {
                viewHistoryTries--;
                System.out.println("not an ID number.");
                continue;
            }
            if (listOfAccounts.contains(entryNumber) != -1) {
                repo = new BankAccountRepo();
                bacctd.setAccountID(entryNumber);
                repo.viewHistory(bacctd);
                System.out.println("type anything to exit view.");
                entry = sc.nextLine();
                if (entry.equalsIgnoreCase(quitWord)) {
                    viewManager.quit();
                    return;
                } else {
                    viewManager.navigate("User Home");
                    return;
                }
            } else {
                viewHistoryTries--;
                System.out.println("that is not one of your account numbers.");
                continue;
            }
        }
        if (viewHistoryTries == 0) {
            System.out.println("timeout error. system shutting off.");
            viewManager.quit();
            return;
        }
    }
}
