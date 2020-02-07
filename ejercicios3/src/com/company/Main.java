package com.company;

import java.lang.annotation.Documented;
import java.nio.file.*;
import java.util.*;
import java.io.*;

public class Main {

    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void main(String[] args) {
        String[] menu = {"leeAlumnoLista", "imprimeListaAlumnos", "escribeFicheroAlumnosBinario", "leeFicheroAlumnosBinario", "escribeFicheroAlumnosTexto", "leeFicheroAlumnosTexto", "escribeFicheroAlumnosCSV", "leeFicheroAlumnosCSV"};
        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        String cualquierTecla, a, b;
        int opcion; //Guardamos la opcion del usuario
        List<FichaAlumno> listaAlumnos = new ArrayList<FichaAlumno>();
        // Comprobamos si existen datos anteriores
        String ficheroApp = "FicheroAlumnos.bin";
        if(Files.exists(Path.of(ficheroApp)))
        {
            leeFicheroAlumnosBinario(listaAlumnos, ficheroApp);
        }
        while (!salir) {// write your code here
            imprimeMenu(menu, "Menu principal", ANSI_BLUE);
            try{
                System.out.print("Eliga opción: ");
                opcion = sc.nextInt();
                sc.nextLine(); // buffer
                switch (opcion) {
                    case 1:
                        //leeAlumnoLista
                        leeAlumnoLista(listaAlumnos);
                        break;
                    case 2:
                        //imprimeListaAlumnos
                        imprimeListaAlumnos(listaAlumnos);
                        break;
                    case 3:
                        //escribeFicheroAlumnosBinario
                        escribeFicheroAlumnosBinario(listaAlumnos, ficheroApp);
                        break;
                    case 4:
                        //leeFicheroAlumnosBinario
                        System.out.println("Leyendo fichero de datos...");
                        leeFicheroAlumnosBinario(listaAlumnos, ficheroApp);
                        escribeFicheroAlumnosBinario(listaAlumnos, ficheroApp);
                        break;
                    case 5:
                        //escribeFicheroAlumnosTexto
                        System.out.print("Introduce nombre de fichero de texto: ");
                        a = sc.nextLine();
                        escribeFicheroAlumnosTexto(listaAlumnos, a);
                        break;
                    case 6:
                        //leeFicheroAlumnosTexto
                        System.out.print("Introduce nombre de fichero de texto: ");
                        a = sc.nextLine();
                        leeFicheroAlumnosTexto(listaAlumnos, a);
                        break;
                    case 7:
                        //escribeFicheroAlumnosCSV
                        System.out.print("Introduce nombre de fichero de texto: ");
                        a = sc.nextLine();
                        escribeFicheroAlumnosCSV(listaAlumnos, a);
                        break;
                    case 8:
                        //leeFicheroAlumnosCSV
                        System.out.print("Introduce nombre de fichero de texto: ");
                        a = sc.nextLine();
                        leeFicheroAlumnosCSV(listaAlumnos, a);
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

    private static void leeFicheroAlumnosCSV(List<FichaAlumno> listaAlumnos, String filename)
    {
        if(Files.exists(Path.of(filename)))
        {
            listaAlumnos.clear();
            try {
                FileReader fr = new FileReader(filename);
                BufferedReader br = new BufferedReader(fr);
                String texto = br.readLine();
                while(texto != null)
                {
                    FichaAlumno alumno = new FichaAlumno();
                    String[] array = texto.split(";");
                    alumno.nombre = array[0];
                    alumno.edad = Integer.parseInt(array[1]);
                    alumno.calificacion = Double.parseDouble(array[2]);
                    listaAlumnos.add(alumno);
                    texto = br.readLine();
                }
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
        }
        else
        {
            System.out.println("El fichero " + filename + " no existe");
        }
    }

    private static void escribeFicheroAlumnosCSV(List<FichaAlumno> listaAlumnos, String filename)
    {
        try {
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < listaAlumnos.size(); i++) {
                FichaAlumno alumno = new FichaAlumno();
                alumno = listaAlumnos.get(i);
                bw.write(alumno.nombre + ";" + alumno.edad + ";" + alumno.calificacion + System.lineSeparator());
            }
            bw.close();
            fw.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static void leeFicheroAlumnosTexto(List<FichaAlumno> listaAlumnos, String filename)
    {
        if(Files.exists(Path.of(filename)))
        {
            listaAlumnos.clear();
            try {
                FileReader fr = new FileReader(filename);
                BufferedReader br = new BufferedReader(fr);
                int n = Integer.parseInt(br.readLine());
                for (int i = 0; i < n; i++) {
                    FichaAlumno alumno = new FichaAlumno();
                    alumno.nombre = br.readLine();
                    alumno.edad = Integer.parseInt(br.readLine());
                    alumno.calificacion = Double.parseDouble(br.readLine());
                    listaAlumnos.add(alumno);
                }
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }

        }
        else
        {
            System.out.println("El fichero " + filename + " no existe");
        }
    }

    private static void escribeFicheroAlumnosTexto(List<FichaAlumno> listaAlumnos, String filename)
    {
        try {
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(listaAlumnos.size() + System.lineSeparator());
            for (int i = 0; i < listaAlumnos.size(); i++) {
                FichaAlumno alumno = new FichaAlumno();
                alumno = listaAlumnos.get(i);
                bw.write(alumno.nombre + System.lineSeparator());
                bw.write(alumno.edad + System.lineSeparator());
                bw.write(alumno.calificacion + System.lineSeparator());
            }
            bw.close();
            fw.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static void leeFicheroAlumnosBinario(List<FichaAlumno> listaAlumnos, String filename)
    {
        listaAlumnos.clear();
        List<Integer> lista = new ArrayList<>();
        try
        {
            FileInputStream fis = new FileInputStream(filename);
            DataInputStream dis = new DataInputStream(fis);
            // Numero de registros
            int n = dis.readInt();
            for (int i = 0; i < n; i++) {
                FichaAlumno alumno = new FichaAlumno();
                alumno.nombre = dis.readUTF();
                alumno.edad = dis.readInt();
                alumno.calificacion = dis.readDouble();
                listaAlumnos.add(alumno);
            }
            dis.close();
            fis.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static void escribeFicheroAlumnosBinario(List<FichaAlumno> listaAlumnos, String filename)
    {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            DataOutputStream dos = new DataOutputStream(fos);
            // Numero de registros
            int n = listaAlumnos.size();
            dos.writeInt(n);
            //
            for (int i = 0; i < listaAlumnos.size() ; i++) {
                dos.writeUTF(listaAlumnos.get(i).nombre);
                dos.writeInt(listaAlumnos.get(i).edad);
                dos.writeDouble(listaAlumnos.get(i).calificacion);
            }
            dos.close();
            fos.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static void imprimeListaAlumnos(List<FichaAlumno> listaAlumnos)
    {
        System.out.println("NOMBRE                  EDAD          CALIFICACIÓN");
        System.out.println("=====================================================");
        for (int i = 0; i < listaAlumnos.size(); i++) {
            System.out.print(padRight(listaAlumnos.get(i).nombre, 25));
            System.out.print(padRight(Integer.toString(listaAlumnos.get(i).edad), 15));
            System.out.print(padRight(Double.toString(listaAlumnos.get(i).calificacion), 15));
            System.out.println();
        }
    }

    public static String padRight(String a, int b)
    {
        int c = a.length();
        for (int i = 0; i < (b - c); i++) {
            a = a + " ";
        }
        if(a.length()>b) a = a.substring(0, b);
        return a;
    }

    private static void leeAlumnoLista(List<FichaAlumno> listaAlumnos)
    {
        String a;
        System.out.println("Registros actuales: " + listaAlumnos.size());
        System.out.println("ALTA NUEVO ALUMNO");
        Scanner sc = new Scanner(System.in);
        FichaAlumno alumno = new FichaAlumno();
        System.out.print("Introduce nombre de alumno:");
        alumno.nombre = sc.nextLine();
        System.out.print("Introduce edad de alumno:");
        alumno.edad = sc.nextInt();
        System.out.print("Introduce calificación de alumno:");
        alumno.calificacion = sc.nextDouble();
        listaAlumnos.add(alumno);
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