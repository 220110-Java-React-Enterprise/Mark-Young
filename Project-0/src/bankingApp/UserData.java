package bankingApp;

// POJO for storing user data
// also contains static method for displaying user ID
public class UserData {
    private Integer userID;

    private String firstName = "";
    private String lastName = "";
    private String username = "";
    private String password = "";
    private String passwordCheck = "";
    private String email = "";
    private String emailCheck = "";

    String address1 = ""; // number and street
    String address2 = ""; // apt no. or suite
    String address3 = ""; // city
    String address4 = ""; // state
    String address5 = ""; // zip code

    private static Integer currentUserID;
    private static String currentFirstName = "";
    private static String currentEmail = "";

    public static Integer getCurrentUserID() {
        return currentUserID;
    }

    public void setCurrentUserID(Integer currentUserID) {
        this.currentUserID = currentUserID;
    }

    public static String getCurrentFirstName() {
        return currentFirstName;
    }

    public void setCurrentFirstName(String currentFirstName) {
        this.currentFirstName = currentFirstName;
    }

    public static String getCurrentEmail() {
        return currentEmail;
    }

    public void setCurrentEmail(String currentEmail) {
        this.currentEmail = currentEmail;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // displays user ID in an elevated manner
    public static String displayUserID(Integer id) {
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
