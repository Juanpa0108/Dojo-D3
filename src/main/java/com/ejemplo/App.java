package com.ejemplo;

public class App {
    public static void main(String[] args) {
        String nombre = args.length > 0 ? args[0] : "mundo";
        System.out.println("Hola, " + nombre + "!");
        System.out.println("Proyecto Java basico listo.");
    }
}
