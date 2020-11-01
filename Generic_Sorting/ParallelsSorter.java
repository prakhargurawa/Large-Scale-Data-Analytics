public class ParallelSorter {
    int[] array;

    public void sort(int[] array) throws InterruptedException { // array length must be a power of 2
        this.array = array;
        sort(0, array.length);
    }

    public int[] getArray(){
        return array;
    }

    private void sort(int low, int n) throws InterruptedException {

        if (n > 1) {
            /*******************************************
             * Implementation from https://stackoverflow.com/questions/3466242/multithreaded-merge-sort/16067335
             * Divide the given array into 2 parts which will also be of length of power 2
             * Send each part to thread which uses Sorter class in backend for processing
             * Once both the array are sorted, merge them using similiar algorithm used in merge sorting
             ******************************************/
            int[] s1 = new int[array.length/2];
            int[] s2 = new int[array.length - array.length/2];
            System.arraycopy(array, 0, s1, 0, array.length/2);
            System.arraycopy(array, array.length/2, s2, 0, array.length - array.length/2);

            ParallelSorterUtil runner1=new ParallelSorterUtil(s1);
            ParallelSorterUtil runner2=new ParallelSorterUtil(s2);
            runner1.start();
            runner2.start();
            runner1.join();
            runner2.join();
            //Merge both the sorted array in efficient way
            array=merger (runner1.getResult(), runner2.getResult());
        }
    }

    public int[] merger(int[] a, int[] array2) {
        int[] output = new int[a.length + array2.length];
        int i=0;
        int j=0;
        int r=0;
        while (i < a.length && j < array2.length) {
            if (a[i] <= array2[j]) {
                output[r]=a[i];
                i++;
                r++;
            } else {
                output[r]=array2[j];
                j++;
                r++;
            }
            if (i==a.length) {
                while (j<array2.length) {
                    output[r]=array2[j];
                    r++;
                    j++;
                }
            }
            if (j==array2.length) {
                while (i<a.length) {
                    output[r]=a[i];
                    r++;
                    i++;
                }
            }
        }
        return output;
    }
}
