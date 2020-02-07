package com.company;

import java.util.*;
import java.io.*;

public class Main {

    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    private static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";

    public static void main(String[] args) {
        String[] menu = {"escribeFicheroNumRandom10", "sumaFicheroInt10", "escribeFicheroNumRandom", "sumaFicheroInt", "escribeFicheroTexto", "leeFicheroTexto", "copiaFicheroTexto", "invierteLineasFichero", "invierteFicheroTexto", "separaPalabrasFichero"};
        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        String cualquierTecla, a, b;
        int opcion; //Guardamos la opcion del usuario
        while (!salir) {// write your code here
            imprimeMenu(menu, "Menu principal", ANSI_GREEN);
            try{
                System.out.print("Eliga opción: ");
                opcion = sc.nextInt();
                sc.nextLine();   // limpiar buffer
                switch (opcion) {
                    case 1:
                        //escribeFicheroNumRandom10
                        System.out.print("Introduce nombre de fichero: ");
                        a = sc.nextLine();
                        escribeFicheroNumRandom10(a + ".txt");
                        break;
                    case 2:
                        //sumaFicheroInt10
                        System.out.print("Introduce nombre de fichero: ");
                        a = sc.nextLine();
                        sumaFicheroInt10(a + ".txt");
                    case 3:
                        //escribeFicheroNumRandom
                        System.out.print("Introduce nombre de fichero: ");
                        a = sc.nextLine();
                        System.out.print("¿Cuantos enteros?: ");
                        int numero = sc.nextInt();
                        escribeFicheroNumRandom(a + ".txt", numero);
                        break;
                    case 4:
                        //sumaFicheroInt
                        System.out.print("Introduce nombre de fichero: ");
                        a = sc.nextLine();
                        sumaFicheroInt(a + ".txt");
                        break;
                    case 5:
                        //escribeFicheroTexto
                        System.out.print("Introduce nombre de fichero: ");
                        a = sc.nextLine();
                        escribeFicheroTexto(a + ".txt");
                        break;
                    case 6:
                        //leeFicheroTexto
                        System.out.print("Introduce nombre de fichero: ");
                        a = sc.nextLine();
                        leeFicheroTexto(a + ".txt");
                        break;
                    case 7:
                        //copiaFicheroTexto
                        System.out.print("Introduce nombre de fichero para copiar: ");
                        a = sc.nextLine();
                        System.out.print("Introduce un nombre de destino: ");
                        b = sc.nextLine();
                        copiaFicheroTexto(a + ".txt", b + ".txt");
                        break;
                    case 8:
                        //invierteLineasFichero
                        System.out.print("Introduce nombre de fichero para copiar: ");
                        a = sc.nextLine();
                        System.out.print("Introduce un nombre de destino: ");
                        b = sc.nextLine();
                        invierteLineasFichero(a + ".txt", b + ".txt");
                        break;
                    case 9:
                        //invierteFicheroTexto
                        System.out.print("Introduce nombre de fichero para copiar: ");
                        a = sc.nextLine();
                        System.out.print("Introduce un nombre de destino: ");
                        b = sc.nextLine();
                        invierteFicheroTexto(a + ".txt", b + ".txt");
                        break;
                    case 10:
                        //separaPalabrasFichero
                        System.out.print("Introduce nombre de fichero con frases: ");
                        a = sc.nextLine();
                        System.out.print("Introduce un nombre de destino: ");
                        b = sc.nextLine();
                        separaPalabrasFichero(a + ".txt", b + ".txt");
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
            }catch (InputMismatchException e){
                System.out.println("ERROR: Debes insertar un número. " + e.getMessage());
                sc.next();
            }
        }
    }

    private static void separaPalabrasFichero(String origin, String target)
    {
        try
        {
            FileReader fr = new FileReader(origin);
            BufferedReader br = new BufferedReader(fr);

            FileWriter fw = new FileWriter(target);
            BufferedWriter bw = new BufferedWriter(fw);

            String texto = br.readLine();

            while (texto != null)
            {
                String[] palabras = texto.split(" ");
                for (int i = 0; i < palabras.length; i++) {
                    bw.write(palabras[i] + System.lineSeparator());
                }
                texto = br.readLine();
            }

            br.close();
            bw.close();

            fw.close();
            fr.close();

            System.out.println("Fichero creado con éxito");

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private static void invierteFicheroTexto(String origin, String target)
    {
        try
        {
            FileReader fr = new FileReader(origin);
            BufferedReader br = new BufferedReader(fr);

            List<String> listaAux = new ArrayList<>();
            int i;
            int a = 0;
            listaAux.add(br.readLine());
            while (listaAux.get(a) != null)
            {
                listaAux.add(br.readLine());
                a++;
            }

            br.close();
            fr.close();

            FileWriter fw = new FileWriter(target);
            BufferedWriter bw = new BufferedWriter(fw);

            for(i = listaAux.size() - 2; i >= 0; i--)
            {
                bw.write(listaAux.get(i) + System.lineSeparator());
            }

            bw.close();
            fw.close();

            System.out.println("Fichero copiado e invertido con éxito");

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private static void invierteLineasFichero(String origin, String target)
    {
        try
        {
            FileReader fr = new FileReader(origin);
            BufferedReader br = new BufferedReader(fr);

            FileWriter fw = new FileWriter(target);
            BufferedWriter bw = new BufferedWriter(fw);

            String texto = br.readLine();
            while (texto != null)
            {
                texto = invierteCadena(texto);
                bw.write(texto+ System.lineSeparator());
                texto = br.readLine();
            }

            br.close();
            bw.close();

            fw.close();
            fr.close();

            System.out.println("Fichero copiado e invertido con éxito");

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static String invierteCadena(String a) {
        String aux = "";
        for (int i = a.length() - 1; i >= 0; i--) {
            aux = aux + a.charAt(i);
        }
        return aux;
    }

    private static void copiaFicheroTexto(String origin, String target)
    {
        try
        {
            FileReader fr = new FileReader(origin);
            BufferedReader br = new BufferedReader(fr);

            FileWriter fw = new FileWriter(target);
            BufferedWriter bw = new BufferedWriter(fw);

            String texto = br.readLine();
            while (texto != null)
            {
                bw.write(texto+ System.lineSeparator());
                texto = br.readLine();
            }

            br.close();
            bw.close();

            fw.close();
            fr.close();

            System.out.println("Fichero copiado con éxito");

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private static void leeFicheroTexto(String filename)
    {
        String texto;
        try
        {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            texto = br.readLine();
            while(texto != null)
            {
                System.out.println(texto);
                texto = br.readLine();
            }
            System.out.println();

            br.close();
            fr.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static void escribeFicheroTexto(String filename)
    {
        Scanner sc = new Scanner(System.in);
        String a;
        try {
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);

            System.out.print("Añade linea: ");
            a = sc.nextLine();
            while(!a.equals(""))
            {
                bw.write(a + System.lineSeparator());
                System.out.print("Añade linea: ");
                a = sc.nextLine();
            }

            bw.close();
            fw.close();
        }
        catch (IOException e)
        {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static void sumaFicheroInt(String filename)
    {
        String texto;
        int suma = 0;
        try
        {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            texto = br.readLine();

            while(texto != null)
            {
                suma = suma + Integer.parseInt(texto);
                System.out.println(texto);
                texto = br.readLine();
            }
            System.out.println();
            System.out.println("La suma es: " + suma);

            br.close();
            fr.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void escribeFicheroNumRandom10(String filename)
    {
        Random r = new Random();
        int i, n;
        try {
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);
            for(i = 0; i<10; i++)
            {
                n = r.nextInt(9999);
                System.out.println("Escribiendo: " + n);
                bw.write(Integer.toString(n) + System.lineSeparator());
            }
            bw.close();
            fw.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void sumaFicheroInt10(String filename)
    {
        String texto;
        int i, suma = 0;
        try
        {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            texto = br.readLine();

            for(i = 0; i<10; i++)
            {
                suma = suma + Integer.parseInt(texto);
                System.out.println(i + ". " + texto);
                texto = br.readLine();
            }
            System.out.println();
            System.out.println("La suma es: " + suma);

            br.close();
            fr.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void escribeFicheroNumRandom(String filename, int cantidad)
    {
        Random r = new Random();
        int i, n;
        try {
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);
            for(i = 0; i < cantidad; i++)
            {
                n = r.nextInt(9999);
                System.out.println("Escribiendo: " + n);
                bw.write(Integer.toString(n) + System.lineSeparator());
            }
            bw.close();
            fw.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
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
