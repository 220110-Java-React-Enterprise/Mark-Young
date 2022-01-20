import java.util.ArrayList;

public class SelectionSortClass2 {
    public static void main(String[] args) {
        Integer[] pArr = new Integer[]{3,2,1,5,4};
        // Boolean unsorted = true;
        Integer tempSmall;
        Integer tempIndex;
        Integer swaps = 0;
        Integer startPtIndex = 0;
        Integer startPtValue =

        while (swaps < pArr.length) { //(unsorted == true) {
            // unsorted = false;
            tempSmall = startPtValue = pArr[startPtIndex];
            tempIndex = startPtIndex;
            for(i = 0; i < pArr.length; i++) {
                if(pArr[i]<tempSmall) {
                    tempSmall = pArr[i];
                    tempIndex = i;
                    // unsorted = true;
                }
            }
            pArr[startPtIndex] = tempSmall;
            pArr[tempIndex] = startPtValue;
            swaps++;
            startPtIndex++;
        }
    }
}
