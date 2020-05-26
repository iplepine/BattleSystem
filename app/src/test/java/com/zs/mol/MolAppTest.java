package com.zs.mol;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MolAppTest {
    @Test
    public void test() {
        System.out.println(solution3(new int[]{3, 4, 5, 3, 7}));
        /*System.out.println(isGoodTrees(newM int[]{3}));
        System.out.println(isGoodTrees(new int[]{3, 5, 3, 7}));*/
    }

    public int solution1(int[] A) {
        // write your code in Java SE 8
        HashMap<Integer, Integer> counts = new HashMap();

        for (int i = 0; i < A.length; ++i) {
            int value = A[i];

            if (!counts.containsKey(value)) {
                counts.put(value, 1);
            } else {
                counts.put(value, counts.get(value) + 1);
            }
        }


        int maxN = 0;

        for (Integer key : counts.keySet()) {
            int count = counts.get(key);

            if (key == count) {
                if (maxN < key) {
                    maxN = key;
                }
            }
        }

        return maxN;
    }

    public int solution2(int[] A) {
        int maxSum = -1;

        HashMap<Long, ArrayList<Integer>> map = new HashMap<>();
        for (int i = 0; i < A.length; ++i) {
            long sumDigits = sumOfDigits(A[i]);

            ArrayList<Integer> sameSumNumbers = map.get(sumDigits);
            if (sameSumNumbers == null) {
                sameSumNumbers = new ArrayList<>();
                map.put(sumDigits, sameSumNumbers);
            }
            sameSumNumbers.add(A[i]);
        }

        for (long sumDigits : map.keySet()) {
            ArrayList<Integer> sameSumNumbers = map.get(sumDigits);
            if (sameSumNumbers.size() > 1) {
                Collections.sort(sameSumNumbers);

                int sum = sameSumNumbers.get(sameSumNumbers.size() - 1) + sameSumNumbers.get(sameSumNumbers.size() - 2);
                if (maxSum < sum) {
                    maxSum = sum;
                }
            }
        }

        return maxSum;
    }

    public static long sumOfDigits(int num) {
        if (num == 0) {
            return 0;
        } else {
            return num % 10 + sumOfDigits(num / 10);
        }
    }

    public int solution3(int[] A) {
        // write your code in Java SE 8

        int count = 0;

        for (int i = 0; i < A.length; ++i) {
            if (isGoodTrees(makeCutArray(A, i))) {
                count++;
            }
        }

        return count;
    }

    int[] makeCutArray(int[] A, int index) {
        int[] ret = new int[A.length - 1];
        int retIndex = 0;

        for (int i = 0; i < A.length; ++i) {
            if (i == index) {
                continue;
            }

            ret[retIndex++] = A[i];
        }

        return ret;
    }

    boolean isGoodTrees(int trees[]) {
        if (trees.length < 2) {
            return true;
        }

        if (trees[1] == trees[0]) {
            return false;
        }

        for (int i = 2; i < trees.length; ++i) {
            if (trees[i] == trees[i - 1]) {
                return false;
            }

            if (trees[i] < trees[i - 1] && trees[i - 1] < trees[i - 2]) {
                return false;
            }

            if (trees[i] > trees[i - 1] && trees[i - 1] > trees[i - 2]) {
                return false;
            }
        }

        return true;
    }

}
