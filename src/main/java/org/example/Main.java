package org.example;

import org.example.list.CustomArrayList;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        CustomArrayList<Integer> customArrayList = new CustomArrayList<>();

        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            customArrayList.add(random.nextInt(-100, 101));
        }

        System.out.println(customArrayList);

        customArrayList.sort(Integer::compareTo);

        System.out.println(customArrayList);
    }
}