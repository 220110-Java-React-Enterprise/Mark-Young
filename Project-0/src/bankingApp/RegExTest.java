package bankingApp;

import java.util.regex.*;

public class RegExTest {
    public static void main(String[] args) {
        Pattern p = Pattern.compile("^[A-Za-z][A-Za-z0-9_-]");//. represents single character.

        Matcher m1 = p.matcher("hootie09");
        Matcher m2 = p.matcher("90toyota");
        Matcher m3 = p.matcher("howdy-40_");
        //Matcher m1 = p.matcher("hootie09");



        boolean b1 = m1.matches();
        boolean b2 = m2.matches();
        boolean b3 = m3.matches();

        System.out.println(b1);
        System.out.println(b2);
        System.out.println(b3);
    }
}
