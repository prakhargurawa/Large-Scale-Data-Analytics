import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortingPractice {
    public static void main(String args[]) throws InterruptedException {
        /**********************************
            Question 1 Solution
            -> Approach: Sorter.java
        ***********************************/
        //Initializing an array of length of power 2 for testing sorting algorithm
        int[] array={12,34,23,45,34,67,68,24,56,11,10,9,8,7,390,456,32,31,30,566,2344,890,900,432,1000,1001,1004,987,900,900,1123,84};
        System.out.println("Question 1-------------------------------------------------------------------------------------");
        System.out.println("Length of array : "+array.length);
        if(Util.isPowerOfTwo(array.length)) {
            //The length of array was power of 2
            System.out.println("Content of array before sorting:");
            for (int i = 0; i < array.length; i++) {
                System.out.print(array[i] + " ");
            }
            System.out.println();
            //Initializing the Sorter class that contains algorithm for sorting
            Sorter sorter = new Sorter();
            sorter.sort(array);
            int[] sortedArray = sorter.getArray();
            System.out.println("Content of array after sorting:");
            for (int i = 0; i < sortedArray.length; i++) {
                System.out.print(sortedArray[i] + " ");
            }
            System.out.println();
        } else{
            //The length of array was not power of 2
            System.out.println("The sorting algorithm is valid for array of length with power of 2");
        }

        /**********************************
         Question 2 Solution
         -> Approach 1: UniversalSorter.java
         -> Approach 2: GenericSorter.java
         ***********************************/
        System.out.println("\nQuestion 2-------------------------------------------------------------------------------------");
        System.out.println("\nApproach 1\n");
        //Initializing the Universal Sorter class that contains algorithm for sorting generic
        UniversalSorter universalSorter = new UniversalSorter();
        String[] arr2={"prakhar","gurawa","abcd","abba","zasw","zzzz","koko","plpl","yuiop","yuioplk","h","kl","qaz","sdf","qazxs","dawq"};
        System.out.println("Length of array : "+arr2.length);
        if(Util.isPowerOfTwo(arr2.length)) {
            //The length of array was power of 2
            System.out.println("Content of array before sorting:");
            for (int i = 0; i < arr2.length; i++) {
                System.out.print(arr2[i] + " ");
            }
            universalSorter.sort(arr2, new SortStrings());
            System.out.println();
            String[] sortedArr2 = (String[]) universalSorter.getArray();
            System.out.println("Content of array after sorting:");
            for (int i = 0; i < sortedArr2.length; i++) {
                System.out.print(sortedArr2[i] + " ");
            }
            System.out.println();
        } else{
            //The length of array was not power of 2
            System.out.println("The sorting algorithm is valid for array of length with power of 2");
        }

        Double[] arr3={1.2,4.5,6.78,9.08,1.23,7.89,122.56,10101.67,89.90,67.89,12345.67,12.34,12.45,12.567,15.56,9000.98};
        System.out.println("Length of array : "+arr3.length);
        if(Util.isPowerOfTwo(arr3.length)) {
            System.out.println("Content of array before sorting:");
            for (int i = 0; i < arr3.length; i++) {
                System.out.print(arr3[i] + " ");
            }
            universalSorter.sort(arr3, new SortDouble());
            System.out.println();
            Double[] sortedArr3 = (Double[]) universalSorter.getArray();
            System.out.println("Content of array after sorting:");
            for (int i = 0; i < sortedArr3.length; i++) {
                System.out.print(sortedArr3[i] + " ");
            }
            System.out.println();
        } else{
            //The length of array was not power of 2
            System.out.println("The sorting algorithm is valid for array of length with power of 2");
        }

        //Second Approach
        System.out.println("\nApproach 2\n");
        GenericSorter genericSorter=new GenericSorter();
        Integer[] arr = {12,34,23,45,34,67,68,24,56,11,10,9,8,7,390,456,32,31,30,566,2344,890,900,432,1000,1001,1004,987,900,900,1123,84};
        System.out.println("Length of array : "+arr.length);
        System.out.println("Content of array before sorting:");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
        genericSorter.sort(arr);
        Integer[] sortedArr= (Integer[]) genericSorter.getArray();
        System.out.println("Content of array after sorting:");
        for (int i = 0; i < sortedArr.length; i++) {
            System.out.print(sortedArr[i] + " ");
        }
        System.out.println();

        String[] sArr={"prakhar","gurawa","abcd","abba","zasw","zzzz","koko","plpl","yuiop","yuioplk","h","kl","qaz","sdf","qazxs","dawq"};
        System.out.println("Length of array : "+sArr.length);
        System.out.println("Content of array before sorting:");
        for (int i = 0; i < sArr.length; i++) {
            System.out.print(sArr[i] + " ");
        }
        System.out.println();
        genericSorter.sort(sArr);
        String[] sortedSArr= (String []) genericSorter.getArray();
        System.out.println("Content of array after sorting:");
        for (int i = 0; i < sortedSArr.length; i++) {
            System.out.print(sortedSArr[i] + " ");
        }

        /**********************************
         Question 3 Solution
         ***********************************/
        System.out.println();
        System.out.println("\nQuestion 3-------------------------------------------------------------------------------------");
        String[] arrL={"prakhar","gurawa","abcd","abba","zasw","zzzz","koko","plpl","yuiop","yuioplk","h","kl","qaz","sdf","qazxs","dawq"};
        System.out.println("Length of array : "+arrL.length);
        if(Util.isPowerOfTwo(arrL.length)) {
            //The length of array was power of 2
            System.out.println("Content of array before sorting:");
            for (int i = 0; i < arrL.length; i++) {
                System.out.print(arrL[i] + " ");
            }
            System.out.println();
            //Providing the comparator as lambda expression
            universalSorter.sort(arrL,(Object o1,Object o2)-> {
                String c1=(String)o1;
                String c2=(String)o2;
                return c1.compareTo(c2);
            });
            String[] sortedArrL = (String[]) universalSorter.getArray();
            System.out.println("Content of array after sorting:");
            for (int i = 0; i < sortedArrL.length; i++) {
                System.out.print(sortedArrL[i] + " ");
            }
            System.out.println();
        } else{
            //The length of array was not power of 2
            System.out.println("The sorting algorithm is valid for array of length with power of 2");
        }

        Double[] arrD={1.2,4.5,6.78,9.08,1.23,7.89,122.56,10101.67,89.90,67.89,12345.67,12.34,12.45,12.567,15.56,9000.98};
        System.out.println("Length of array : "+arrD.length);
        if(Util.isPowerOfTwo(arrD.length)) {
            //The length of array was power of 2
            System.out.println("Content of array before sorting:");
            for (int i = 0; i < arrD.length; i++) {
                System.out.print(arrD[i] + " ");
            }
            //Providing the comparator as lambda expression
            universalSorter.sort(arrD, (Object o1,Object o2)-> {
                Double c1=(Double)o1;
                Double c2=(Double)o2;
                if(c1>c2){
                    return 1;
                }else{
                    return -1;
                }
            });
            System.out.println();
            Double[] sortedArrD = (Double[]) universalSorter.getArray();
            System.out.println("Content of array after sorting:");
            for (int i = 0; i < sortedArrD.length; i++) {
                System.out.print(sortedArrD[i] + " ");
            }
            System.out.println();
        } else{
            //The length of array was not power of 2
            System.out.println("The sorting algorithm is valid for array of length with power of 2");
        }



        /**********************************
         Question 4 Solution
         -> Approach : ParallelSorter.java
         ***********************************/
        System.out.println();
        System.out.println("Question 4-------------------------------------------------------------------------------------");
        int[] arrP = {12,34,23,45,34,67,68,24,56,11,10,9,8,7,390,456,32,31,30,566,2344,890,900,432,1000,1001,1004,987,900,900,1123,84};
        if(Util.isPowerOfTwo(arrP.length)) {
            //The length of array was power of 2
            System.out.println("Content of array before sorting:");
            for (int i = 0; i < arrP.length; i++) {
                System.out.print(arrP[i] + " ");
            }
            System.out.println();
            ParallelSorter parallelSorter=new ParallelSorter();
            parallelSorter.sort(arrP);
            int[] arrP2= parallelSorter.getArray();
            System.out.println("Content of array after sorting:");
            for (int i = 0; i < arrP2.length; i++) {
                System.out.print(arrP2[i] + " ");
            }
            System.out.println();
        }else{
            //The length of array was not power of 2
            System.out.println("The sorting algorithm is valid for array of length with power of 2");
        }
    }

    static class SortStrings implements java.util.Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            String c1=(String)o1;
            String c2=(String)o2;
            return c1.compareTo(c2);
        }
    }

    static class SortDouble implements java.util.Comparator{
        @Override
        public int compare(Object o1, Object o2) {
            Double c1=(Double)o1;
            Double c2=(Double)o2;
            if(c1>c2){
                return 1;
            }else{
                return -1;
            }
        }
    }
}
