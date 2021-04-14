
public class NetworkTools {

    public static double[] createRandomArray(int size, double lower_bound, double upper_bound){
        if(size < 1){
            return null;
        }
        double[] ar = new double[size];
        for(int i = 0; i < size; i++){
            ar[i] = randomValue(lower_bound,upper_bound);
        }
        return ar;
    }

    public static double[][] createRandomArray(int sizeX, int sizeY, double lower_bound, double upper_bound){
        if(sizeX < 1 || sizeY < 1){
            return null;
        }
        double[][] ar = new double[sizeX][sizeY];
        for(int i = 0; i < sizeX; i++){
            ar[i] = createRandomArray(sizeY, lower_bound, upper_bound);
        }
        return ar;
    }

    public static double randomValue(double lower_bound, double upper_bound){
        return Math.random()*(upper_bound-lower_bound) + lower_bound;
    }

    public static double[][][] copy3d(double[][][] original) {//Независимая копия трехмерного массива
        double[][][] copy = new double[original.length][][];
        for (int i = 0; i < original.length; i++) {
            if (i > 0) {
                copy[i] = copy2d(original[i]);
            }
        }
        return copy;
    }

    public static double[][] copy2d(double[][] original) {//Независимая копия двухмерного массива
        double[][] copy = new double[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = copy1d(original[i]);
        }
        return copy;
    }

    public static double[] copy1d(double[] original) {//Независимая копия одномерного массива (double)
        int length = original.length;
        double[] copy = new double[length];
        System.arraycopy(original, 0, copy, 0, length);
        return copy;
    }

    public static int[] copy1dInt(int[] original) {//Независимая копия одномерного массива (int)
        int length = original.length;
        int[] copy = new int[length];
        System.arraycopy(original, 0, copy, 0, length);
        return copy;
    }

}
