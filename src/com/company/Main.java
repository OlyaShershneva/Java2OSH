package com.company;


import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter expression: ");  //24+(4-7)*9/14
        String str = scan.nextLine();

        Calculations calculator = new Calculations(str);


        if(calculator.ErrorCheck()) {
            Queue qe = new LinkedList();
            qe = calculator.ToPost();
            System.out.println("Postfix notation:");
            System.out.println(qe);
            double result = calculator.ToRes();
            System.out.println("Output: " + result);
        } else
            System.out.println("Error!");
    }
}
