public class ParallelSorterUtil extends Thread{

    private int[] internal;
    private int[] result;

    public int[] getInternal() {
        return internal;
    }

    ParallelSorterUtil(int[] arr) {
        internal = arr;
    }

    public void run() {
        //Calling the Sorter class for sorting
        Sorter sorter=new Sorter();
        sorter.sort(internal);
        result= sorter.getArray();
    }

    public int[] getResult(){
        return result;
    }

}
