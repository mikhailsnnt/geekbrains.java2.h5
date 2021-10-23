package geekbrains.java2;

import java.util.Arrays;

public class Main {
    static final int size = 10000000;
    public static void main(String[] args) {
        System.out.printf("Single process  execution time: %d\n",getSingleThreadMethodExecutionTime());
        System.out.printf("Multithreading  execution time: %d\n",getMultiThreadMethodExecutionTime());

    }
    static long getSingleThreadMethodExecutionTime(){
        float[] arr = new float[size];
        Arrays.fill(arr, 1);
        long startTime = System.currentTimeMillis();
        fillWithFunction(arr);
        return System.currentTimeMillis() - startTime;
    }
    static void fillWithFunction(float [] arr){
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) ((arr[i] * Math.sin(0.2f + (float)i / 5)) * Math.cos(0.2f + (float)i/5)* Math.cos(0.4f + (float) i / 2));
        }
    }
    static void fillWithFunction(float [] arr,int offset){
        for (int i = 0; i < arr.length; i++) {
            int realI = i + offset;
            arr[i] = (float) ((arr[i] * Math.sin(0.2f + (float)realI / 5)) * Math.cos(0.2f + (float)realI/5)* Math.cos(0.4f + (float) realI/ 2));
        }
    }
    static long getMultiThreadMethodExecutionTime(){
        long startTime = System.currentTimeMillis();
        float[] arr = new float[size];
        Arrays.fill(arr, 1);
        float[] a1 = new float[size/2];
        float[] a2 = new float[size/2];
        System.arraycopy(arr,0,a1,0,size/2);
        System.arraycopy(arr,size/2, a2,0,size/2);
        Thread thread1 = new Thread(() -> fillWithFunction(a1));
        Thread thread2 = new Thread(() -> fillWithFunction(a2,size/2));
        thread1.start();
        thread2.start();
        try{
            thread1.join();
            thread2.join();
        }catch (InterruptedException exception){
            exception.printStackTrace();
        }
        System.arraycopy(a1,0,arr,0,size/2);
        System.arraycopy(a2,0,arr,size/2,size/2);
        return System.currentTimeMillis() -  startTime;

    }
}
