package edu.vsu.ru.util;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ListUtils {
    public static List<Integer> readListFromJTable(JTable table) {
        StringBuilder sb = new StringBuilder();
        TableModel tableModel = table.getModel();

        int numberOfColumns = tableModel.getColumnCount();

        for (int i = 0; i < numberOfColumns; i++) {
            sb.append(tableModel.getValueAt(0, i).toString());
            sb.append(" ");
        }

        return convertToList(sb.toString());
    }

    public static List<Integer> readIntegerListFromFile(String fileName) throws FileNotFoundException {
        Scanner scn = new Scanner(new File(fileName));
        String unsortedList;
        unsortedList = scn.nextLine();

        return convertToList(unsortedList);
    }
    public static List<String> readLinesFromFile(String fileName) throws FileNotFoundException {
        List<String> lines;
        try (Scanner scanner = new Scanner(new File(fileName), "UTF-8")) {
            lines = new ArrayList<>();
            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }
        }
        return lines;
    }


    public static List<String []> splitString(List<String> list){
        List<String []> flat = new ArrayList<>();

        for(String i : list){
            String [] property = i.split(" ");
            flat.add(property);
        }
        return flat;
    }

    public static List<String[]> strMatrixToList(String[][] arr) {
        List<String[]> list = new ArrayList<>(arr.length);
        for (int i = 0; i < arr.length; i++) {
            list.add(i, arr[i]);
        }
        return list;
    }
    public static List<String []> readListFromFile(String fileName) throws FileNotFoundException {
        return splitString(readLinesFromFile(fileName));
    }

    public static String [][] strListToMatrix(List<String[]> list) {
        String[][] arr = new String[list.size()][5];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    private static List<Integer> convertToList(String unsortedList) {
        List<Integer> sortedList = new ArrayList<>();

        if (unsortedList != null) {
            Scanner scn = new Scanner(unsortedList);
            scn.useDelimiter("(\\s|,)+");

            while (scn.hasNext())
                if (!scn.hasNextInt()) {
                    return null;
                } else {
                    sortedList.add(scn.nextInt());
                }
        } else {
            return null;
        }

        return sortedList;
    }

    public static void writeListToFile(String fileName, List<Integer> list) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(fileName);

        String res = convertListToString(list);
        out.print(res);
        out.close();
    }

    public static void printListInConsole(List<Integer> list) {
        String res = convertListToString(list);
        System.out.print(res);
    }

    public static void fillArrayRandomNumbers(int[] arr) {
        Random random = new Random();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100);
        }
    }

    public static int[] convertToIntArray(List<Integer> list) {
        int[] arr = new int[list.size()];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = list.get(i);
        }

        return arr;
    }

    public static String convertListToString(List<Integer> list) {
        StringBuilder sb = new StringBuilder();

        int numOfMembers = list.size();

        for (Integer i : list) {
            numOfMembers--;
            sb.append(i);

            if (numOfMembers != 0) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }

    public static void printListToConsole(List<Integer> list) {
        printArrayInConsole(intListToArray(list));
    }

    public static void printArrayInConsole(int[] arr) {
        for (int j : arr) {
            System.out.print(j);
            System.out.print(", ");
        }
    }

    public static List<Integer> intArrayToList(int[] arr) {
        List<Integer> list = new ArrayList<>(arr.length);
        for (int i = 0; i < arr.length; i++) {
            list.add(i, arr[i]);
        }
        return list;
    }

    public static int[] intListToArray(List<Integer> list) {
        int[] arr = new int[list.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }
}
