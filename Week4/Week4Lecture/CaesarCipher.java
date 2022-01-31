package Week4Lecture;

import java.util.Locale;

public class CaesarCipher {
    public String doCipher(String str) {
        char[] temp = str.toUpperCase().toCharArray();
        int[] intArr = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            intArr[i] = (int) temp[i] - 64;
        }
        String result = "";
        for (int i = 0; i < intArr.length; i++) {
            if (intArr[i] < 10) {
                result = result + "0" + intArr[i];
            } else {
                result = result + intArr[i];
            }
        }
        return result;
    }
}
