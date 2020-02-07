package com.company;

import java.util.*;
import java.io.*;

public class Main {

    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void main(String[] args) {
        String[] menu = {"escribeFichero1_100", "leeFicheroInt100", "escribeFicheroIntAleatorio", "escribeFicheroIntAleatorioPro", "leeFicheroInt", "sumaFicheroInt", "leeFicheroIntLista", "escribeFicheroIntLista", "ordenaFicheroInt", "separaFicheroInt", "invierteFicheroInt"};
        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        String cualquierTecla, a, b;
        int opcion; //Guardamos la opcion del usuario
        while (!salir) {// write your code here
            imprimeMenu(menu, "Menu principal", ANSI_BLUE);
            try{
                System.out.print("Eliga opción: ");
                opcion = sc.nextInt();
                sc.nextLine();   // limpiar buffer
                switch (opcion) {
                    case 1:
                        //escribeFichero1_100
                        System.out.print("Introduce nombre de fichero binario: ");
                        a = sc.nextLine();
                        escribeFichero1_100(a);
                        break;
                    case 2:
                        //leeFicheroInt100
                        System.out.print("Introduce nombre de fichero binario: ");
                        a = sc.nextLine();
                        leeFicheroInt100(a);
                        break;
                    case 3:
                        //escribeFicheroIntAleatorio
                        System.out.print("Introduce nombre de fichero binario: ");
                        a = sc.nextLine();
                        System.out.print("¿Cuantos enteros?: ");
                        int numero = sc.nextInt();
                        escribeFicheroIntAleatorio(a, numero);
                        break;
                    case 4:
                        //escribeFicheroIntAleatorioPro
                        System.out.print("Introduce nombre de fichero binario: ");
                        a = sc.nextLine();
                        System.out.print("¿Cuantos enteros?: ");
                        numero = sc.nextInt();
                        System.out.print("¿Valor Mínimo?: ");
                        int nMin = sc.nextInt();
                        System.out.print("¿Valor Máximo?: ");
                        int nMax = sc.nextInt();
                        escribeFicheroIntAleatorioPro(a, numero, nMin, nMax);
                        break;
                    case 5:
                        //leeFicheroInt
                        System.out.print("Introduce nombre de fichero: ");
                        a = sc.nextLine();
                        leeFicheroInt(a);
                        break;
                    case 6:
                        //sumaFicheroInt
                        System.out.print("Introduce nombre de fichero: ");
                        a = sc.nextLine();
                        System.out.println("La suma es " + sumaFicheroInt(a));
                        break;
                    case 7:
                        //leeFicheroIntLista
                        System.out.print("Introduce nombre de fichero binario: ");
                        a = sc.nextLine();
                        escribeLista(LeeFicheroIntLista(a));
                        break;
                    case 8:
                        //escribeFicheroIntLista
                        System.out.print("Introduce nombre de fichero binario: ");
                        a = sc.nextLine();
                        List<Integer> li = new ArrayList<>();
                        li = new LinkedList<>(Arrays.asList(34, 85, 75, -8, 12, -45, 23, 1, 0));
                        escribeFicheroIntLista(a, li);
                        break;
                    case 9:
                        //ordenaFicheroInt
                        System.out.print("Introduce nombre de fichero binario: ");
                        a = sc.nextLine();
                        ordenaFicheroInt(a);
                        break;
                    case 10:
                        //separaFicheroInt
                        System.out.print("Introduce nombre de fichero binario: ");
                        a = sc.nextLine();
                        separaFicheroInt(a);
                        break;
                    case 11:
                        //invierteFicheroInt
                        System.out.print("Introduce nombre de fichero binario: ");
                        a = sc.nextLine();
                        invierteFicheroInt(a);
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

    private static void invierteFicheroInt(String filename)
    {
        List<Integer> lectura = LeeFicheroIntLista(filename);
        invierteList(lectura);
        escribeFicheroIntLista(filename, lectura);
    }

    public static void invierteList(List<Integer> lista)
    {
        List<Integer> auxiliar = new ArrayList<>();
        for (int i = lista.size() - 1; i >= 0; i--)
        {
            auxiliar.add(lista.get(i));
        }
        lista.clear();
        for (int i = 0; i < auxiliar.size(); i++) {
            lista.add(auxiliar.get(i));
        }
    }

    private static void separaFicheroInt(String filename)
    {
        List<Integer> positivos = new ArrayList<>();
        List<Integer> negativos = new ArrayList<>();
        List<Integer> lectura = LeeFicheroIntLista(filename);
        clasificaNumeros(lectura, positivos, negativos);
        escribeFicheroIntLista(filename +"positivos", positivos);
        escribeFicheroIntLista(filename +"negativos", negativos);
    }

    private static void clasificaNumeros(List<Integer> l1, List<Integer> l2, List<Integer> l3)
    {
        for (Integer integer : l1) {
            if (integer >= 0) {
                l2.add(integer);
            }
            else
            {
                l3.add(integer);
            }
        }
        l1.clear();
        ordenaLista(l2);
        ordenaLista(l3);
    }

    private static void ordenaFicheroInt(String filename)
    {
        List<Integer> lectura = LeeFicheroIntLista(filename);
        ordenaLista(lectura);
        escribeFicheroIntLista(filename, lectura);
    }

    private static void ordenaLista(List<Integer> li)
    {
        List<Integer> resultado;
        resultado = new LinkedList<>();
        int indice = 0;
        while(li.size()!=0) {
            int masPequenno = li.get(0);
            for (int i = 0; i < li.size(); i++) {
                if (li.get(i) <= masPequenno) {
                    masPequenno = li.get(i);
                    indice = i;
                }
            }
            li.remove(indice);
            resultado.add(masPequenno);
        }
        for (int i = 0; i < resultado.size(); i++) {
            li.add(resultado.get(i));
        }
    }

    private static void escribeFicheroIntLista(String filename, List<Integer> lista)
    {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            DataOutputStream dos = new DataOutputStream(fos);

            for (int i = 0; i < lista.size() ; i++) {
                dos.writeInt(lista.get(i));
            }
            dos.close();
            fos.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static List<Integer> LeeFicheroIntLista(String filename)
    {
        List<Integer> lista = new ArrayList<>();
        try
        {
            FileInputStream fis = new FileInputStream(filename);
            DataInputStream dis = new DataInputStream(fis);

            while(dis.available() > 0)
            {
                lista.add(dis.readInt());
            }

            dis.close();
            fis.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        return lista;
    }

    private static void escribeLista(List<Integer> li)
    {
        // Escribir todos los elementos de una lista
        int i;
        System.out.print("<");
        if(li.size()>0) {
            for (i = 0; i < li.size() - 1; i++) {
                System.out.print(li.get(i));
                System.out.print(", ");
            }
            System.out.print(li.get(i));
        }
        System.out.println(">");
    }

    public static int sumaFicheroInt(String filename)
    {
        int suma = 0;
        try
        {
            FileInputStream fis = new FileInputStream(filename);
            DataInputStream dis = new DataInputStream(fis);

            while(dis.available() > 0)
            {
                suma = suma + dis.readInt();
            }

            dis.close();
            fis.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        return suma;
    }

    private static void leeFicheroInt(String filename)
    {
        try
        {
            FileInputStream fis = new FileInputStream(filename);
            DataInputStream dis = new DataInputStream(fis);

            while(dis.available() > 0)
            {
                System.out.println(dis.readInt());
            }

            dis.close();
            fis.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static void escribeFicheroIntAleatorioPro(String filename, int cantidad, int nMin, int nMax)
    {
        Random r = new Random();
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            DataOutputStream dos = new DataOutputStream(fos);

            for (int i = 1; i <= cantidad ; i++) {
                dos.writeInt(r.nextInt(nMax - nMin) + nMin);
            }
            dos.close();
            fos.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static void escribeFicheroIntAleatorio(String filename, int cantidad)
    {
        Random r = new Random();
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            DataOutputStream dos = new DataOutputStream(fos);

            for (int i = 1; i <= cantidad ; i++) {
                dos.writeInt(r.nextInt(100) + 1);
            }
            dos.close();
            fos.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static void leeFicheroInt100(String filename)
    {
        try
        {
            FileInputStream fis = new FileInputStream(filename);
            DataInputStream dis = new DataInputStream(fis);

            while(dis.available() > 0)
            {
                System.out.println(dis.readInt());
            }

            dis.close();
            fis.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static void escribeFichero1_100(String filename)
    {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            DataOutputStream dos = new DataOutputStream(fos);

            for (int i = 1; i <= 100 ; i++) {
                dos.writeInt(i);
            }
            dos.close();
            fos.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
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