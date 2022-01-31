package Week4Lecture;

import java.util.Scanner;

public class IceCreamTruck {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("input: ");
        String str = sc.nextLine();
        String[] strArr = str.split(" ", 3);
        Integer[] intArr = new Integer[3];
        for (int i = 0; i < 3; i++) {
            intArr[i] = Integer.parseInt(strArr[i]);
        }
        Integer dist_1;
        Integer dist_2;
        if (intArr[0] < intArr[2]) {
            dist_1 = intArr[2] - intArr[0];
        } else {
            dist_1 = intArr[0] - intArr[2];
        }
        if (intArr[1] < intArr[2]) {
            dist_2 = intArr[2] - intArr[1];
        } else {
            dist_2 = intArr[1] - intArr[2];
        }
        if (dist_1 < dist_2) {
            System.out.println("Kid 1");
        } else if (dist_1 > dist_2) {
            System.out.println("Kid 2");
        } else {
            System.out.println("Truck");
        }
    }
}
