/*
selection sort works by searching the array and looking for the lowest value
that we haven't sorted.

then we move that element to the start of the array, swapping places with the
element there. Here's

of the algorithm.

 */

public class SelectionSortClass {
    public static void main(String[] args) {
        ArrayList pArr = new ArrayList<Integer>{3,2,1,5,4};
        Boolean unsorted = true;
        Integer tempSmall;
        Integer tempIndex;
        Integer swaps = 0;
        Integer startPtIndex = 0;
        Integer startPtValue =

        while (swaps < pArr.length) { //(unsorted == true) {
            unsorted = false;
            tempSmall = startPtValue = pArr[startPtIndex];
            tempIndex = 0;
            for(i = 0; i < pArr.length; i++) {
                if(pArr[i]<tempSmall) {
                    tempSmall = pArr[i];
                    tempIndex = i;
                    unsorted = true;
                }
            }
            pArr[startPtIndex] = tempSmall;
            pArr[tempIndex] = startPtValue;
            swaps++;
            startPtIndex++;
        }
    }


}
