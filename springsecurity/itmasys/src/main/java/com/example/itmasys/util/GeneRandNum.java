package com.example.itmasys.util;

import java.util.Random;

public class GeneRandNum {
    public static Integer generating_random_numbers(){
        Random random = new Random();
        return Math.abs(random.nextInt())%100000;
    }
}
