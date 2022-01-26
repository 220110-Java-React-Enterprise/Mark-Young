package bankingApp;

import java.util.Scanner;
import static bankingApp.ViewManager.quitWord;

public class JointAccounts extends View {
    Scanner sc;
    ViewManager viewManager = ViewManager.getViewManager();
    String entry;
    Integer jointTries;
    Integer entryInteger;
    Integer potentialJointID;
    Integer accountListIndex;
    CustomArrayList<CustomArrayList<Object>> accountList = new CustomArrayList<>();
    CustomArrayList<Integer> accountIDList = new CustomArrayList<>();

    public JointAccounts(String name, ViewManager viewManager) {
        super(name, viewManager);
    }

    @Override
    public void renderView() {
        sc = viewManager.getScanner();
        jointTries = 10;
        System.out.println("\n\n\n******************");
        System.out.println("MAKE AN ACCOUNT JOINT");
        System.out.println("******************");
        System.out.println("Type \"home\" to go back to your homepage.");
        BankAccountRepo repo = new BankAccountRepo();
        BankAccountData bacctd = new BankAccountData();
        accountList = repo.readAccount(bacctd);
        accountIDList = new CustomArrayList<>();

        for (int i = 0; i < accountList.size(); i++) {
            accountIDList.add(accountList.get(i).get(0));
            //System.out.print(listOfAccounts.get(i) + " ");
        }
        displayAccounts(bacctd);
        //accountList = bacctd.getMyAccountList();
        //accountIDList = bacctd.getMyAccountIDList();

        while (jointTries > 0) {

            System.out.println("The joint account holder must be registered as a user with us. \n" +
                    "If that is not currently the case, please log out and register that user first.\n" +
                    "Additional note: Maximum two account holders per account\n" +
                    "You can type \"LOGOUT\" on the next line if you want to do that.");
            System.out.print("Which account would you like to join? Type the corresponding account ID: ");
            entry = sc.nextLine();
            if (entry.equalsIgnoreCase(quitWord)) {
                jointTries = 0;
                viewManager.quit();
                return;
            } else if (entry.equalsIgnoreCase("LOGOUT")) {
                viewManager.navigate("Start Menu");
                return;
            } else if (entry.equalsIgnoreCase("HOME")) {
                viewManager.navigate("User Home");
                return;
            }
            try {
                entryInteger = Integer.parseInt(entry);
            } catch (NumberFormatException e) {
                jointTries--;
                System.out.println("That is not a valid account ID.");
                continue;
            }
            if (accountIDList.contains(entryInteger) == -1) {
                jointTries--;
                System.out.println("That is not one of your account IDs.");
                continue;
            } else {
                accountListIndex = bacctd.getMyAccountIDList().contains(entryInteger);
                if (accountList.get(accountListIndex).get(4) != null) {
                    System.out.println("There are already two holders of account " + BankAccountData.displayAccountID(entryInteger) + ".");
                    System.out.println("You are not allowed to add another user to that account.");
                    viewManager.navigate("User Home");
                    return;
                }
                bacctd.setAccountID(entryInteger);
                System.out.println("Now we will gather information on the joint user.");
                System.out.println("Type \"LOGOUT\" at any time to log out.");
                while (jointTries > 0) {
                    System.out.print("Joint User - Email: ");
                    entry = sc.nextLine();
                    if (entry.equalsIgnoreCase(quitWord)) {
                        jointTries = 0;
                        viewManager.quit();
                        return;
                    } else if (entry.equalsIgnoreCase("LOGOUT")) {
                        viewManager.navigate("Start Menu");
                        return;
                    }
                    UserRepo userRepo = new UserRepo();
                    UserData userModel = new UserData();
                    userModel.setEmail(entry);
                    userModel = userRepo.readEmail(userModel);
                    if (userModel == null) {
                        jointTries--;
                        System.out.println("That email address is not in our files.");
                        continue;
                    } else {
                        potentialJointID = userModel.getUserID();
                        System.out.print("Joint User - Username: ");
                        entry = sc.nextLine().toLowerCase();
                        while (!entry.equals(userModel.getUsername()) && jointTries > 0) {
                            if (entry.equalsIgnoreCase(quitWord)) {
                                viewManager.quit();
                                return;
                            }
                            jointTries--;
                            System.out.println("Incorrect username. " + jointTries + " tries left. You can say \"" + quitWord + "\" to exit the application");
                            System.out.print("Username: ");
                            entry = sc.nextLine().toLowerCase();
                        }
                        if (jointTries == 0) {
                            System.out.println("Max login tries reached. Sorry, you will have to come back later. Shutting down.");
                            viewManager.quit();
                            return;
                        }
                        System.out.print("Joint User - Password: ");
                        entry = sc.nextLine(); //.toLowerCase();
                        while (!entry.equals(userModel.getPassword()) && jointTries > 0) {
                            if (entry.equalsIgnoreCase(quitWord)) {
                                viewManager.quit();
                                return;
                            }
                            jointTries--;
                            System.out.println("Incorrect password. " + jointTries + " tries left. You can say \"" + quitWord + "\" to exit the application");
                            System.out.println("Password: ");
                            entry = sc.nextLine(); //.toLowerCase();
                        }
                        if (jointTries == 0) {
                            System.out.println("Max login tries reached. Sorry, you will have to come back later. Shutting down.");
                            viewManager.quit();
                            return;
                        }
                        System.out.println("User verified!");
                        System.out.println("That user has been added as a joint holder of account " +
                                BankAccountData.displayAccountID(entryInteger) + ".");
                        bacctd.getMyAccountList().get(accountListIndex).set(4, potentialJointID);
                        bacctd.setUserID2(potentialJointID);
                        System.out.println("Now taking you back to the homepage...");
                        repo.updateJointUser(bacctd);
                        viewManager.navigate("User Home");
                        return;
                    }
                }
            }
        }
    }

    void displayAccounts(BankAccountData bacctd) {
        UserHome.showAccounts(bacctd);
    }
}
