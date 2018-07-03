package com.deep.study.test2;

import java.util.Comparator;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class ForkJoinPoolAction {

    public static final AtomicInteger count = new AtomicInteger();

    public static void main(String[] args) throws Exception {
        /*PrintTask task = new PrintTask(1,50);
        ForkJoinPool pool = new ForkJoinPool();
        pool.submit(task);
        pool.awaitTermination(2, TimeUnit.SECONDS);
        pool.shutdown();*/
        int[] arr = new int[]{10,1,2,3,4,5,6,7,8,9};
        printArr(arr);
        sort(arr,(Integer a, Integer b) -> {
            return b - a;
        });
        printArr(arr);
        SumTask task = new SumTask(arr);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        int result = pool.invoke(task);
        System.out.println(result);
        pool.shutdown();
    }

    public static void printArr(int[] arr) {
        for(int i : arr){
            System.out.print(i + ",");
        }
        System.out.println();
    }


    public static void sort(int[] arr, Comparator<Integer> sorter){
        for(int i = 0 ; i < arr.length ; i++){
            for (int j = i; j < arr.length; j++) {
                if(sorter.compare(arr[i] , arr[j]) > 0 ){
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }

            }
        }
    }

    static class PrintTask extends RecursiveAction {
        private static final int THRESHOLD = 10;
        private int start;
        private int end;

        public PrintTask(int start,int end) {
            super();
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start < THRESHOLD) {
                for (int i = start; i <= end; i++) {
                    int c = count.addAndGet(1);
                    System.out.println(Thread.currentThread().getName() + "的i值：" + i + "; count :" + c);
                }
            } else {
                int middle = (start + end)/ 2;
                PrintTask left = new PrintTask(start,middle);
                PrintTask right = new PrintTask(middle + 1, end);
                left.fork();
                right.fork();
            }
        }
    }

    static class SumTask extends RecursiveTask<Integer> {


        private static final int THRESHOLD = 10;
        private int[] arr;
        private int start;
        private int end;

        public SumTask(int[] arr){
            this.arr = arr;
            this.start = 0;
            this.end = arr.length;
        }

        public SumTask(int[] arr,int start,int end) {
            this.arr = arr;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int size = end - start;
            if (size < THRESHOLD) {
                int sum = 0;
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
                return sum;
            } else {
                int middle = (start + end) / 2;
                SumTask task1 = new SumTask(arr,start,middle);
                SumTask task2 = new SumTask(arr,middle +1,end);
                task1.fork();
                task2.fork();
                return task1.join() + task2.join();
            }
        }
    }

}
