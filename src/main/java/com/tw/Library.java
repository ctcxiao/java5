package com.tw;

import java.util.Scanner;

public class Library {

    public void someLibraryMethod() {
        Menu menu = new Menu();
        int selection = 0;
        while (selection != 3) {
            menu.printDescription();
            selection = new Scanner(System.in).nextInt();
            menu.executeSelection(selection);
        }
    }

    public static void main(String[] args) {
        new Library().someLibraryMethod();
    }
}
