package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Main {

    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void main(String[] args) {
        String[] menu = {"cuantosFicheros", "cuantosFicherosPro", "creaBackup", "renombraMasivo", "cuentaLineasFicheros", "ficheroMasGrande", "clasificaFicheros"};
        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        String cualquierTecla, a, b;
        int opcion; //Guardamos la opcion del usuario
        while (!salir) {// write your code here
            imprimeMenu(menu, "Menu principal", ANSI_BLUE);
            try {
                System.out.print("Eliga opción: ");
                opcion = sc.nextInt();
                sc.nextLine(); // buffer
                switch (opcion) {
                    case 1:
                        //cuantosFicheros
                        System.out.print("Introduce extensión de fichero: ");
                        a = sc.nextLine();
                        System.out.println("Número de ficheros encontrados: " + cuantosFicheros(a));
                        break;
                    case 2:
                        //cuantosFicherosPro
                        System.out.print("Introduce extensión de fichero: ");
                        a = sc.nextLine();
                        System.out.print("Introduce Carpeta: ");
                        b = sc.nextLine();
                        System.out.println("Número de ficheros encontrados: " + cuantosFicherosPro(a, b));
                        break;
                    case 3:
                        //creaBackup
                        System.out.print("Introduce nombre de fichero para hacer backup: ");
                        a = sc.nextLine();
                        creaBackup(a);
                        break;
                    case 4:
                        //renombraMasivo
                        System.out.print("Introduce extensión de fichero: ");
                        a = sc.nextLine();
                        System.out.print("Introduce nueva extensión: ");
                        b = sc.nextLine();
                        renombraMasivo(a, b);
                        //escribeFicheroAlumnosBinario(listaAlumnos, ficheroApp);
                        break;
                    case 5:
                        //cuentaLineasFicheros
                        System.out.print("Introduce extensión de fichero: ");
                        a = sc.nextLine();
                        System.out.println(cuentaLineasFicheros(a));
                        break;
                    case 6:
                        //ficheroMasGrande
                        System.out.println("Fichero más grande: " + ficheroMasGrande());
                        break;
                    case 7:
                        //clasificaFicheros
                        clasificaFicheros();
                        //escribeFicheroAlumnosCSV(listaAlumnos, a);
                        break;
                    case 0:
                        salir = true;
                        break;
                    default:
                        System.out.println("ERROR: Opcion no válida");
                }
                if (!salir) {
                    System.out.println("Introduzca cualquier carácter + Intro para continuar:");
                    cualquierTecla = sc.next();
                }
            } catch (InputMismatchException e) {
                System.out.println("ERROR: Debes insertar un número. " + e.getMessage());
                sc.next();
            }
        }
    }

    private static void clasificaFicheros() {
        Map<String, Integer> ficheros = new HashMap<>();
        File dir = new File("."); // "." = directorio actual
        File[] arrayFicheros = dir.listFiles();
        int i;
        for (i = 0; i < arrayFicheros.length; i++) {
            if (arrayFicheros[i].isFile()) {
                // hallamos extensión
                String ext = extension(arrayFicheros[i].getName());
                if (ficheros.containsKey(ext)) {
                    ficheros.put(ext, ficheros.get(ext) + 1);
                } else {
                    ficheros.put(ext, 1);
                }

            }
        }
        System.out.println(ficheros);
    }

    private static String extension(String name) {
        int punto = name.lastIndexOf('.');
        if (punto != -1) {
            return name.substring(punto + 1);
        } else {
            return "";
        }
    }

    private static String ficheroMasGrande() {
        String maslargo = "";
        File dir = new File("."); // "." = directorio actual
        File[] arrayFicheros = dir.listFiles();
        int i;
        long mayor = 0;

        for (i = 0; i < arrayFicheros.length; i++) {
            if (arrayFicheros[i].isFile()) {
                if (arrayFicheros[i].length() > mayor) {
                    mayor = arrayFicheros[i].length();
                    maslargo = arrayFicheros[i].getName();
                }
            }
        }
        return maslargo;
    }

    private static int cuentaLineasFicheros(String a) {
        File dir = new File(".");
        File[] arrayFicheros = dir.listFiles();
        int i, cont = 0;

        for (i = 0; i < arrayFicheros.length; i++) {
            if (arrayFicheros[i].isFile()) {
                if (arrayFicheros[i].getName().endsWith(a)) {
                    // abrir fichero texto y contar lineas
                    String texto;
                    try {
                        FileReader fr = new FileReader(arrayFicheros[i].getName());
                        BufferedReader br = new BufferedReader(fr);
                        texto = br.readLine();
                        while (texto != null) {
                            cont++;
                            texto = br.readLine();
                        }
                        System.out.println();

                        br.close();
                        fr.close();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        return cont;
    }

    private static void renombraMasivo(String a, String b) {
        File dir = new File(".");
        File[] arrayFicheros = dir.listFiles();
        int i, punto;
        String filename;
        for (i = 0; i < arrayFicheros.length; i++) {
            if (arrayFicheros[i].isFile()) {
                if (arrayFicheros[i].getName().endsWith("." + a)) {
                    try {
                        punto = arrayFicheros[i].getName().lastIndexOf('.');
                        filename = arrayFicheros[i].getName().substring(0, punto);
                        Files.move(Path.of(arrayFicheros[i].getName()), Path.of(filename + "." + b));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void creaBackup(String a) {
        try {
            if (!Files.exists(Path.of(a + ".bak"))) {
                Files.copy(Path.of(a), Path.of(a + ".bak"));
            } else {
                creaBackup(a + ".bak");
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static int cuantosFicherosPro(String a, String b) {
        File dir = new File(b);
        File[] arrayFicheros = dir.listFiles();
        int i, cont = 0;

        for (i = 0; i < arrayFicheros.length; i++) {
            if (arrayFicheros[i].isFile()) {
                if (arrayFicheros[i].getName().endsWith(a)) {
                    cont++;
                }
            }
        }
        return cont;
    }

    private static int cuantosFicheros(String a) {

        File dir = new File("."); // "." = directorio actual
        File[] arrayFicheros = dir.listFiles();
        int i, cont = 0;

        for (i = 0; i < arrayFicheros.length; i++) {
            if (arrayFicheros[i].isFile()) {
                if (arrayFicheros[i].getName().endsWith(a)) {
                    cont++;
                }
            }
        }
        return cont;
    }

    public static void imprimeMenu(String[] opciones, String titulo, String color) {
        System.out.println(color + "╔══════════════════════════════════╗");
        System.out.println("║              M E N U             ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.print(color + "║" + ANSI_RESET);
        System.out.print(ANSI_BLACK + ANSI_WHITE_BACKGROUND + titulo);
        for (int j = 0; j < 34 - titulo.length(); j++) {
            System.out.print(" ");
        }
        System.out.print(ANSI_RESET + color + "║" + ANSI_RESET);
        System.out.println();
        for (int i = 1; i <= opciones.length; i++) {
            System.out.print(color + "║" + ANSI_RESET);
            if (i < 10) System.out.print(" ");
            System.out.print(i + ". " + opciones[i - 1]);
            for (int j = 0; j < 30 - opciones[i - 1].length(); j++) {
                System.out.print(" ");
            }
            System.out.print(color + "║" + ANSI_RESET);
            System.out.println();
        }
        System.out.println(color + "╠══════════════════════════════════╣");
        //System.out.print("\u001B[101m" + "\u001B[97m");
        System.out.println("║ " + ANSI_RESET + "0. Salir                         " + color + "║");
        //System.out.println("\u001B[0m");
        System.out.println("╚══════════════════════════════════╝" + ANSI_RESET);
    }
}
