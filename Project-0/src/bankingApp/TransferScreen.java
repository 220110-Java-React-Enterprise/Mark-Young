package bankingApp;

import java.util.Scanner;
import static bankingApp.ViewManager.quitWord;

// transfer money between accounts
public class TransferScreen extends View {
    Scanner sc;
    ViewManager viewManager = ViewManager.getViewManager();
    String entry;
    Integer transferTries;
    Integer transferTries2;
    Integer entryInteger;
    CustomArrayList<CustomArrayList<Object>> accountList = new CustomArrayList<>();
    CustomArrayList<Integer> accountIDList = new CustomArrayList<>();
    Double entryNumber;

    // constructor
    public TransferScreen(String name, ViewManager viewManager) {
        super(name, viewManager);
    }

    // asks user for two accounts: fromAccount and toAccount
    // makes sure there is money available to pull
    // then commits transfer
    @Override
    public void renderView() {
        sc = viewManager.getScanner();
        transferTries = 10;
        transferTries2 = 10;
        System.out.println("\n\n\n******************");
        System.out.println("TRANSFER BETWEEN ACCOUNTS");
        System.out.println("******************");
        System.out.println("Type \"home\" to go back to your homepage.");
        BankAccountData bacctd = new BankAccountData();
        BankAccountRepo repo = new BankAccountRepo();
        accountList = repo.readAccount(bacctd);
        accountIDList = new CustomArrayList<>();

        for (int i = 0; i < accountList.size(); i++) {
            accountIDList.add(accountList.get(i).get(0));
            //System.out.print(listOfAccounts.get(i) + " ");
        }

        while (transferTries > 0) {
            displayAccounts(bacctd);
            //accountList = bacctd.getMyAccountList();
            //accountIDList = bacctd.getMyAccountIDList();
            System.out.print("Which account would you like to transfer money out of?\n" +
                    "Please enter the corresponding account ID: ");
            entry = sc.nextLine();
            if (entry.equalsIgnoreCase(quitWord)) {
                transferTries = 0;
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
                transferTries--;
                System.out.println("That is not a valid account ID.");
                continue;
            }
            if (accountIDList.contains(entryInteger) == -1) {
                transferTries--;
                System.out.println("That is not one of your account IDs.");
                continue;
            } else {
                bacctd.setFromAccountID(entryInteger);
                while (transferTries > 0) {
                    System.out.print("Which account would you like to transfer money into?\n" +
                            "Please enter the corresponding account ID: ");
                    entry = sc.nextLine();
                    if (entry.equalsIgnoreCase(quitWord)) {
                        transferTries = 0;
                        viewManager.quit();
                        return;
                    }
                    try {
                        entryInteger = Integer.parseInt(entry);
                    } catch (NumberFormatException e) {
                        transferTries--;
                        System.out.println("That is not a valid account ID.");
                        continue;
                    }
                    if (accountIDList.contains(entryInteger) == -1) {
                        transferTries--;
                        System.out.println("That is not one of your account IDs.");
                        continue;
                    } else if (entryInteger.equals(bacctd.getFromAccountID())) {
                        transferTries--;
                        System.out.println("there is no point in transferring money from, and instantly back into, the same account.");
                        continue;
                    } else {
                        bacctd.setToAccountID(entryInteger);
                        System.out.print("How much would you like to transfer? ");
                        entry = sc.nextLine();
                        if (entry.equalsIgnoreCase(quitWord)) {
                            transferTries = 0;
                            viewManager.quit();
                            return;
                        }
                        try {
                            entryNumber = Double.parseDouble(entry);
                            Integer tempIndex = accountIDList.contains(bacctd.getFromAccountID());
                            if (Math.floor(entryNumber*100) != entryNumber*100) {
                                transferTries--;
                                System.out.println("Please denote deposit amount in dollars and cents. Smaller amounts of money than one cent are not accepted.");
                                continue;
                            } else if (entryNumber > (Double) accountList.get(tempIndex).get(2)) {
                                transferTries--;
                                System.out.println("You do not have enough in your account to withdraw that amount.");
                                continue;
                            } else {
                                System.out.println("transferring " + BankAccountData.displayCurrency(entryNumber) + "...");
                                repo.transfer(bacctd, entryNumber);
                                System.out.println("Your amount has been transferred!");
                                viewManager.navigate("User Home");
                                return;
                            }
                        } catch (NumberFormatException e) {
                            transferTries--;
                            System.out.println("That was not a valid amount.");
                        }
                    }
                }
            }
        }
    }

    // calls method from another class
    void displayAccounts(BankAccountData bacctd) {
        UserHome.showAccounts(bacctd);
    }
}