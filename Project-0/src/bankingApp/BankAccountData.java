package bankingApp;

// this is a POJO storing account data
// gets user from userID stored in UserData POJO
// perhaps most important object is myAccountList, which contains all account data associated with the user
// data in myAccountList is: accountID, userID, balance, accountType, userID2 (for joint accounts)
// also contains the static methods displayCurrency() and displayAccountID()
// which are used to elevate UI presentation
public class BankAccountData {
    private Double balance;
    private Integer userID;
    private Integer accountID;
    String accountType;
    private Integer fromAccountID;
    private Integer toAccountID;
    private Integer userID2;

    private String checkingSavings = ""; // either "checking" or "savings"
    private static String userIDCount = "000001"; // six digits
    private static String accountIDCount = "000001"; // six digits
    private static int adminID = 1;
    private static String adminFirstName = "bank";
    private static String adminLastName = "administrator";
    private static String adminUsername = "admin";
    private static String adminPassword = "passWord123";

    CustomArrayList<CustomArrayList<Object>> myAccountList = new CustomArrayList<>();
    CustomArrayList<Integer> myAccountIDList = new CustomArrayList<>();

    public BankAccountData(Integer userID) {
        this.userID = userID;
    }

    public BankAccountData() {
        this.userID = UserData.getCurrentUserID();
    }

    CustomArrayList names = new CustomArrayList<String>(
            "Abby Angola", "Bob Burry", "Calvin Calloway", "Dean DeMarco");
    CustomArrayList emails = new CustomArrayList<String>("abby-angola123@gmail.com",
            "bobs_burgers@yahoo.com", "calvin.calloway888@business.net", "ddemarco@dungeons-and-dragons.gov");
    CustomArrayList balances = new CustomArrayList<Double>(
            (double) 250, (double) 300, (double) 350, (double) 400);

    public Integer getUserID() {
        return UserData.getCurrentUserID();
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public CustomArrayList<Integer> getMyAccountIDList() {
        return myAccountIDList;
    }

    public void setMyAccountIDList(CustomArrayList<Integer> myAccountIDList) {
        this.myAccountIDList = myAccountIDList;
    }

    public CustomArrayList<CustomArrayList<Object>> getMyAccountList() {
        return myAccountList;
    }

    public void setMyAccountList(CustomArrayList<CustomArrayList<Object>> myAccountList) {
        this.myAccountList = myAccountList;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setCheckingSavings(String str) {
        this.checkingSavings = str;
    }

    public String getCheckingSavings() {
        return checkingSavings;
    }

    public Integer getUserID2() {
        return userID2;
    }

    public void setUserID2(Integer userID2) {
        this.userID2 = userID2;
    }

    public Integer getFromAccountID() {
        return fromAccountID;
    }

    public void setFromAccountID(Integer fromAccountID) {
        this.fromAccountID = fromAccountID;
    }

    public Integer getToAccountID() {
        return toAccountID;
    }

    public void setToAccountID(Integer toAccountID) {
        this.toAccountID = toAccountID;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    // input: money amount
    // return: string that looks better
    public static String displayCurrency(Double d) {
        Double newD = d*10000;
        Long newD2 = Math.round(newD);
        Long rem2 = newD2 % 10000;
        String result = "";
        if (rem2 > 0) {
            Long temp = Math.round(rem2/100.0);
            result = "." + hundredToString(temp);
        } else {
            result = ".00";
        }
        newD2 = (long) Math.floor(newD2 / 10000.0);
        Long temp2;
        while (newD2 >= 1000) {
            temp2 = newD2 % 1000;
            result = "," + thousandToString(temp2) + result;
            newD2 = (long) Math.floor(newD2/1000.0);
        }
        return "$" + newD2 + result;
    }

    // helper function for displayCurrency()
    // how to ensure placement of the zeroes that might go missing when modulo-ing
    public static String thousandToString(long k) {
        // k must be less than a thousand
        if (k < 10) {
            return "00" + k;
        } else if (k < 100) {
            return "0" + k;
        } else {
            return Long.toString(k);
        }
    }

    // helper function for displayCurrency()
    // how to ensure placement of the zeroes that might go missing when modulo-ing
    public static String hundredToString(long k) {
        // k must be less than a thousand
        if (k < 10) {
            return "0" + k;
        } else {
            return Long.toString(k);
        }
    }

    // input: accountID
    // return: string that looks better
    public static String displayAccountID(Integer id) {
        if (id < 1000) {
            if (id < 100) {
                if (id < 10) {
                    return "000" + id;
                } else {
                    return "00" + id;
                }
            } else {
                return "0" + id;
            }
        }
        return Integer.toString(id);
    }
}
