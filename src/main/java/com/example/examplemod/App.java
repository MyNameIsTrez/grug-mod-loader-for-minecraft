package com.example.examplemod;

public class App {
    public String getGreeting(String[] args) {
        if (args.length == 0) {
            return "Hello grug!";
        }
        return "heya";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting(args));
    }
}