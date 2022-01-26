package bankingApp;

import java.util.Scanner;
import static bankingApp.ViewManager.quitWord;

public class UserHome extends View {
    Scanner sc;
    ViewManager viewManager = ViewManager.getViewManager();
    CustomArrayList<CustomArrayList<Object>> accountList = new CustomArrayList<>();
    CustomArrayList<Integer> accountIDList = new CustomArrayList<>();

    public UserHome(String name, ViewManager viewManager) {
        super(name, viewManager);
    }

    @Override
    public void renderView() {
        sc = viewManager.getScanner();
        System.out.println("******************");
        System.out.println("THE BANKING APP");
        System.out.println("HOME");
        System.out.println("Welcome home, " + UserData.getCurrentFirstName() + "!");
        System.out.println("******************");
        BankAccountData bacctd = new BankAccountData();
        BankAccountRepo repo = new BankAccountRepo();
        accountList = repo.readAccount(bacctd);
        accountIDList = new CustomArrayList<>();

        for (int i = 0; i < accountList.size(); i++) {
            accountIDList.add(accountList.get(i).get(0));
            //System.out.print(listOfAccounts.get(i) + " ");
        }
        bacctd.setMyAccountList(accountList);
        bacctd.setMyAccountIDList(accountIDList);


        System.out.println("User ID: " + UserData.displayUserID(bacctd.getUserID()) + "\n");
        showAccounts(bacctd);
        Integer timeout = 10;
        while (timeout > 0) {
            System.out.print("What would you like to do?\n" +
                    "\n" +
                    "CREATE - create a new account\n" +
                    "DEPOSIT - deposit funds into an account\n" +
                    "WITHDRAW - withdraw funds from an account\n" +
                    "TRANSFER - transfer funds between accounts\n" +
                    "CLOSE - close an account\n" +
                    "JOINT - add another user to an account, thus making it a joint account\n" +
                    "HISTORY - view transaction history of an account\n" +
                    "LOGOUT - log out of your user account\n" +
                    //"///////////////////////////////\n" +
                    "\n" +
                    "Please type one of the above words: ");
            String entry = sc.nextLine().toUpperCase();
            switch(entry) {
                case "CREATE":
                    viewManager.navigate("Create Account");
                    return;
                case "DEPOSIT":
                    viewManager.navigate("Deposit");
                    return;
                case "WITHDRAW":
                    viewManager.navigate("Withdraw");
                    return;
                case "TRANSFER":
                    viewManager.navigate("Transfer Screen");
                    return;
                case "CLOSE":
                    viewManager.navigate("Close Account");
                    return;
                case "HISTORY":
                    viewManager.navigate("View History");
                    return;
                case "JOINT":
                    viewManager.navigate("Joint Accounts");
                    return;
                case "LOGOUT":
                    System.out.println("You have logged out. Thank you for your business!");
                    viewManager.navigate("Start Menu");
                    return;
                case "QUIT":
                    timeout = 0;
                    viewManager.quit();
                    return;
                default:
                    timeout--;
                    System.out.println("not a valid choice. \n\n\n");
            }
        }
        if (timeout == 0) {
            System.out.println("sorry, timed out. You will be returned to the login screen.");
            viewManager.navigate("Login Screen");
            return;
        }
    }

    // this method displays accounts to the user
    // typically displayed on the user's home page automatically
    // takes in a BankAccountData object and prints to the console
    static void showAccounts(BankAccountData bacctd) {
        BankAccountRepo repo = new BankAccountRepo();
        CustomArrayList<CustomArrayList<Object>> myAcctList = bacctd.getMyAccountList();
        CustomArrayList<CustomArrayList<Object>> dummyList = repo.readAccount(bacctd);
        if (!dummyList.equals(new CustomArrayList<>())) {
            Integer listLen = dummyList.size();
            System.out.println("Account Number" + "      " + "Account Type" + "         " + "Balance");
            CustomArrayList<Object> temp;
            for (int i = 0; i < listLen; i++) {
                // temp = new CustomArrayList<>();
                temp = dummyList.get(i);
                System.out.println(BankAccountData.displayAccountID((Integer) temp.get(0)) + "                " +
                        temp.get(3) + "             " + BankAccountData.displayCurrency((Double) temp.get(2)));
            }
//            Integer listLen = myAcctList.size();
//            System.out.println("Account Number" + "      " + "Account Type" + "         " + "Balance");
//            CustomArrayList<Object> temp;
//            for (int i = 0; i < listLen; i++) {
//                // temp = new CustomArrayList<>();
//                temp = myAcctList.get(i);
//                System.out.println(BankAccountData.displayAccountID((Integer) temp.get(0)) + "                " +
//                        temp.get(3) + "             " + BankAccountData.displayCurrency((Double) temp.get(2)));
//            }
        } else {
            System.out.println("No accounts in file.");
        }
        System.out.println("");
    }
}
