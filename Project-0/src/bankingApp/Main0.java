package bankingApp;

// starts up the whole application, by getting viewManager, and associating that viewManager with all views
// then running the transfer of views, until exit called
public class Main0 {
    public static void main(String[] args) {
        ViewManager viewManager = ViewManager.getViewManager();

        viewManager.registerView(new StartMenu("Start Menu", viewManager)); // "Start Menu"
        viewManager.registerView(new Register("Register Screen", viewManager)); // "Register Screen"
        viewManager.registerView(new LoginProc("Login Screen", viewManager)); // "Login Screen"
        viewManager.registerView(new CreateAcctProc("Create Account", viewManager)); // "Create Account"
        viewManager.registerView(new Withdraw("Withdraw", viewManager)); // "Withdraw"
        viewManager.registerView(new Deposit("Deposit", viewManager)); // "Deposit"
        viewManager.registerView(new UserHome("User Home", viewManager)); // "User Home"
        viewManager.registerView(new ViewHistory("View History", viewManager)); // "View History"
        viewManager.registerView(new CloseAccount("Close Account", viewManager)); // "Close Account"
        viewManager.registerView((new TransferScreen("Transfer Screen", viewManager))); // "Transfer Screen"
        viewManager.registerView((new JointAccounts("Joint Accounts", viewManager))); // "Joint Accounts"

        viewManager.navigate("Start Menu");

        while (viewManager.isRunning()) {
            viewManager.render();
        }
        System.exit(0);
    }
}
