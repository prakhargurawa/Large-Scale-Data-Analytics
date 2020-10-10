public class Util {

    static Boolean isPowerOfTwo(int num){
        //Checks a number is positive and power of 2
        return (num > 0 && ((num & (num - 1)) == 0));
    }

}
