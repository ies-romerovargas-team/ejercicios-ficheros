package com.company;

import javafx.scene.control.ListView;

import javax.naming.PartialResultException;
import java.io.*;
import java.net.URI;
import java.nio.file.*;
import java.util.*;

public class Main {

    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void main(String[] args) {
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
                            identificator(systemFile.get(1));
                        }
                        else if (systemFile.get(0)=="f")
                        {
                            System.out.println("Fichero: " + systemFile.get(1));
                        }
                        else
                        {
                            System.out.println("No se ha seleccionado ningún archivo");
                        }
                        break;
                    case 2:
                        //creaBMP
                        System.out.print("Introduce nombre de fichero: ");
                        a = sc.nextLine();
                        break;
                    case 3:
                        //identificaBMP
                        System.out.print("Introduce nombre de fichero para hacer backup: ");
                        a = sc.nextLine();
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

    private static void identificator(String s)
    {
        File dir = new File(s);
        if(dir.isDirectory()){
            File[] arrayFicheros = dir.listFiles();
            for (int i = 0; i < arrayFicheros.length; i++) {
                if(arrayFicheros[i].isFile()) identifica(arrayFicheros[i].getAbsolutePath());
            }
        }
        else if (dir.isFile())
        {
            identifica(dir.getAbsoluteFile());
        }
    }

    private static void identifica(File archivo)
    {
        /*
        ".ZIP", "50 4B 03 04", ".RAR", "52 61 72 21", ".TAR", "1F 8B 08 00", ".TGZ", "1F 9D 90 70",
        ".DOC",	"D0 CF 11 E0", ".XLS", "D0 CF 11 E0", ".PDF", "25 50 44 46", ".WMV", "30 26 B2 75",
        ".FLV", "46 4C 56 01", ".BMP", "42 4D F8 A9", ".GIF", "47 49 46 38", ".ICO", "00 00 01 00",
        ".JPEG", "FF D8 FF E0", ".PNG", "89 50 4E 47", ".SFW", "43 57 53", ".MP3", "49 44 33 2E",
        ".EXE", "4D 5A 90 00", ".DLL", 4D 5A 50 00, "Linux bin", "7F 45 4C 46"
        https://enwikipediaorg/wiki/List_of_file_signatures
        */
        // Esta función crea una lista de valores conocidos de tipos de archivo desde un archvo editable de texto
        // Después los compara con la cabecera del archivo enviado a la función y devuelve, si lo encuentra, su tipo
        List<String> tipos = LeeFicheroLista("tipos");
        try
        {
            FileInputStream fis = new FileInputStream(archivo);
            DataInputStream dis = new DataInputStream(fis);

            while(dis.available() > 0)
            {
                System.out.println(dis.readByte());
            }

            dis.close();
            fis.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static List<tiposFichero> LeeFicheroLista(String filename)
    {
        List<tiposFichero> lista = new ArrayList<>();
        String texto;
        try
        {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            texto = br.readLine();
            while(texto != null)
            {
                tiposFichero tipo = new tiposFichero();
                tipo.extension = texto.substring(0, texto.indexOf(','));
                tipo.offset = Byte.parseByte(texto.substring(texto.indexOf(','), texto.indexOf(',',1))) ;
                int a = texto.indexOf(',',2);
                tipo.bytes[]
                lista.add()
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
        System.out.println("Explorador de Archivos v1.0 || type 'lc' for list command availables");
        System.out.println(" Navega y selecciona un directorio o archivo para utilizar en la función");
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
