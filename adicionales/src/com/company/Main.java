package com.company;

import com.google.common.io.LittleEndianDataInputStream;
import com.google.common.io.LittleEndianDataOutputStream;

import java.io.*;
import java.util.*;

public class Main {

    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void main(String[] args) {
        // Creamos una lista de valores conocidos de tipos de archivo desde un archvo editable de texto
        List<String> tipos = LeeFicheroLista();
        String[] menu = {"identificator", "creaBMP", "identificaBMP"};
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
                        //identificator
                        List<String> systemFile = navegador();
                        if (systemFile.get(0)=="d"){
                            System.out.println("Directorio: " + systemFile.get(1));
                            identificator(systemFile.get(1), tipos);
                        }
                        else if (systemFile.get(0)=="f")
                        {
                            System.out.println("Fichero: " + systemFile.get(1));
                            identificator(systemFile.get(1), tipos);
                        }
                        else
                        {
                            System.out.println("No se ha seleccionado ningún archivo");
                        }
                        break;
                    case 2:
                        creaBMP();
                        break;
                    case 3:
                        //identificaBMP
                        System.out.print("Introduce nombre de fichero bmp: ");
                        a = sc.nextLine();
                        identificaBMP(a);
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

    private static void creaBMP()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca nombre de fichero: ");
        String a = sc.nextLine();
        System.out.println("Seleccione color R + G + B: ");
        int R = sc.nextInt();
        int G = sc.nextInt();
        int B = sc.nextInt();
        creaCuadradoBMP(a, R, G, B);
    }

    private static void identificaBMP(String filename)
    {
        tipoMetadatosBMP leidos = new tipoMetadatosBMP();

        try
        {
            FileInputStream fis = new FileInputStream(filename);
            DataInputStream dis = new DataInputStream(fis);

            LittleEndianDataInputStream leidos2 = new LittleEndianDataInputStream(dis);

            leidos.b = leidos2.readByte();
            leidos.m = leidos2.readByte();
            leidos.tamano = leidos2.readInt();
            leidos.reservado = leidos2.readInt();
            leidos.offset = leidos2.readInt();
            leidos.tamanoMetadatos = leidos2.readInt();
            leidos.alto = leidos2.readInt();
            leidos.ancho = leidos2.readInt();
            leidos.numeroPlanos = leidos2.readShort();
            leidos.profundidadColor = leidos2.readShort();
            leidos.tipoCompresion = leidos2.readInt();
            leidos.tamanoEstructura = leidos2.readInt();
            leidos.pxmh = leidos2.readInt();
            leidos.pxmv = leidos2.readInt();
            leidos.coloresUsados = leidos2.readInt();
            leidos.coloresImportantes = leidos2.readInt();

            System.out.println("b: " + leidos.b);
            System.out.println("m: " + leidos.m);
            System.out.println("tamano: " + leidos.tamano);
            System.out.println("reservado: " + leidos.reservado);
            System.out.println("offset: " + leidos.offset);
            System.out.println("tamanoMetadatos: " + leidos.tamanoMetadatos);
            System.out.println("alto: " + leidos.alto);
            System.out.println("ancho: " + leidos.ancho);
            System.out.println("numeroPlanos: " + leidos.numeroPlanos);
            System.out.println("profundidadColor: " + leidos.profundidadColor);
            System.out.println("tipoCompresión: " + leidos.tipoCompresion);
            System.out.println("tamanoEstructura: " + leidos.tamanoEstructura);
            System.out.println("pxmh: " + leidos.pxmh);
            System.out.println("pxmv: " + leidos.pxmv);
            System.out.println("coloresUsados: " + leidos.coloresUsados);
            System.out.println("coloresImportantes: " + leidos.coloresImportantes);

            dis.close();
            fis.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static void creaCuadradoBMP(String filename, int R, int G, int B)
    {
        tipoMetadatosBMP datos = new tipoMetadatosBMP();

        try {
            FileOutputStream fos = new FileOutputStream(filename);
            DataOutputStream dos = new DataOutputStream(fos);

            LittleEndianDataOutputStream datos2 = new LittleEndianDataOutputStream(fos);

            datos2.writeByte(66);
            datos2.writeByte(77);
            datos2.writeInt(30054);
            datos2.writeInt(0);
            datos2.writeInt(54);
            datos2.writeInt(40);
            datos2.writeInt(100);
            datos2.writeInt(100);
            datos2.writeShort(1);
            datos2.writeShort(24);
            datos2.writeInt(0);
            datos2.writeInt(30000);
            datos2.writeInt(11811);
            datos2.writeInt(11811);
            datos2.writeInt(0);
            datos2.writeInt(0);
            for (int i = 0; i < 10000; i++) {
                datos2.writeByte(B);
                datos2.writeByte(G);
                datos2.writeByte(R);
            }
            dos.close();
            fos.close();
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

    private static void identificator(String s, List<String> tipos)
    {
        File dir = new File(s);
        if(dir.isDirectory())
        {
            File[] arrayFicheros = dir.listFiles();
            for (int i = 0; i < arrayFicheros.length; i++) {
                if(arrayFicheros[i].isFile()) {
                    identifica(arrayFicheros[i].getAbsolutePath(), tipos);
                }
            }
        }
        else if (dir.isFile())
        {
            identifica(dir.getAbsolutePath(), tipos);
        }
    }

    private static void identifica(String archivo, List<String> tipos)
    {
        /*
        ".ZIP", "50 4B 03 04", ".RAR", "52 61 72 21", ".TAR", "1F 8B 08 00", ".TGZ", "1F 9D 90 70",
        ".DOC",	"D0 CF 11 E0", ".XLS", "D0 CF 11 E0", ".PDF", "25 50 44 46", ".WMV", "30 26 B2 75",
        ".FLV", "46 4C 56 01", ".BMP", "42 4D F8 A9", ".GIF", "47 49 46 38", ".ICO", "00 00 01 00",
        ".JPEG", "FF D8 FF E0", ".PNG", "89 50 4E 47", ".SFW", "43 57 53", ".MP3", "49 44 33 2E",
        ".EXE", "4D 5A 90 00", ".DLL", 4D 5A 50 00, "Linux bin", "7F 45 4C 46"
        https://enwikipediaorg/wiki/List_of_file_signatures
        */

        try
        {
            FileInputStream fis = new FileInputStream(archivo);
            DataInputStream dis = new DataInputStream(fis);

            // leemos los 4 primeros bytes
            String hex = "";
            for (int i = 0; i < 4; i++) {
                if(dis.available()>0)
                {
                    byte dato = dis.readByte();
                    int datoInt = unsignedToBytes(dato);
                    String convierteAString = Integer.toHexString(datoInt);
                    if (convierteAString.length()==1) convierteAString = "0" + convierteAString;
                    hex = hex + convierteAString;
                    hex = hex.toUpperCase();
                }
            }
            // buscamos en la lista de tipos
            int index = tipos.indexOf(hex);
            if (index != -1)
            {
                System.out.println("El archivo " + archivo + " es de tipo " + tipos.get(index - 2));
            }
            else
            {
                System.out.println("Archivo " + archivo + " no identificado " + hex);
            }

            dis.close();
            fis.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static int unsignedToBytes(byte b) {
        return b & 0xFF;
    }

    private static List<String> LeeFicheroLista()
    {
        List<String> lista = new ArrayList<>();
        String texto;
        try
        {
            FileReader fr = new FileReader("tipos.txt");
            BufferedReader br = new BufferedReader(fr);

            texto = br.readLine();
            while(texto != null)
            {
                String linea[] = texto.split(",");
                lista.add(linea[0]);    // Contiene el tipo
                lista.add(linea[1]);    // Contiene el byte de inicio
                lista.add(linea[2]);    // Contiene los bytes en hex
                texto = br.readLine();
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        return lista;
    }

    private static List<String> navegador()
    {
        // explorador rudimentario de archivos en modo texto
        // comandos: ls (List) sf (SelectFile) sd (SelectDirectory) exit (Exit)
        // comandos: od (OpenDirectory) .. (DownDirectory) lc (ListCommand)
        // salida: "f", "xxxx" (archivo) "d", "xxxx" (directorio) "0", "0" (cancel)
        File dir = new File("/");
        List<String> resultado = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println(ANSI_RED + "Explorador de Archivos v1.0 || type 'lc' for list command availables" + ANSI_RESET);
        System.out.println("Navega y selecciona un directorio o archivo para utilizar en la función");
        System.out.println("dbm 2020");
        System.out.print("> ");
        String command = sc.nextLine();
        String argumento;
        while (!command.equals("exit"))
        {
            switch (command.substring(0,2))
            {
                case "lc":
                    System.out.println(" ls \tList\n cd [d]\tChangeDirectory\n sf [f]\tSelectFile\n" +
                            " sd [d]\tSelectDirectory\n ex \tExit\n lc \tListCommand");
                    break;
                case "ls":
                    File[] arrayFicheros = dir.listFiles();
                    System.out.println(ANSI_GREEN + "> " + dir + ANSI_RESET);
                    for (int i = 0; i < arrayFicheros.length; i++) {
                        if(arrayFicheros[i].isDirectory())
                            System.out.println("   " + ANSI_BLUE + arrayFicheros[i].getName() + ANSI_RESET);
                    }
                    for (int i = 0; i < arrayFicheros.length; i++) {
                        if(arrayFicheros[i].isFile())
                            System.out.println("   " + arrayFicheros[i].getName());
                    }
                    break;
                case "cd":
                    argumento = command.substring(2).trim();
                    if(dir.getName().length()!=1) argumento = "/" + argumento;
                    dir = new File(dir + argumento);
                    System.out.println(ANSI_GREEN + "> " + dir + ANSI_RESET);
                    break;
                case "sd":
                    argumento = command.substring(2).trim();
                    if(dir.getName().length()!=1) argumento = "/" + argumento;
                    resultado.add("d");
                    resultado.add(dir + argumento);
                    return resultado;
                case "sf":
                    argumento = command.substring(2).trim();
                    if(dir.getName().length()!=1) argumento = "/" + argumento;
                    resultado.add("f");
                    resultado.add(dir + argumento);
                    return resultado;
            }
            System.out.print("> ");
            command = sc.nextLine();
        }
        //cancelado
        resultado.add("0");
        resultado.add("0");
        return resultado;
    }
}
