package bankingApp;

import java.util.Scanner;

public class BankAccount {
    public static void main(String[] args) {

        private String name1;
        private String name2;
        private Double balance;
        private String username;
        private String password;
        private String email;
        private static String userID = "0001";
        static Integer accountID = "";


        CustomArrayList names = new CustomArrayList<String>(
                "Abby Angola", "Bob Burry", "Calvin Calloway", "Dean DeMarco");
        CustomArrayList emails = new CustomArrayList<String>("abby-angola123@gmail.com",
                "bobs_burgers@yahoo.com", "calvin.calloway888@business.net", "ddemarco@dungeons-and-dragons.gov");
        CustomArrayList balances = new CustomArrayList<Double>(
                (double) 250, (double) 300, (double) 350, (double) 400);
        //CustomArrayList names = new CustomArrayList<String>();
        //CustomArrayList names = new CustomArrayList<String>();
        //CustomArrayList names = new CustomArrayList<String>();



        Scanner sc = new Scanner(System.in);
        System.out.print("Welcome to The Banking App! \n     " +
                "Have you already registered with us? Please answer \"yes\" or \"no\"");
        String registered = sc.nextLine(); // user inputs whether or not they have registered
        if (registered == "yes") {
            System.out.print("In that case, please enter the following: \n\n" +
                    "First and Last Name, such as \"John Smith\": %s", name);
        }


        System.out.println("Your name is: " + name);
        System.out.printf("Your name is: %s\n", name);

    }
}
