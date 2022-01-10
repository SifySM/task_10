package edu.vsu.ru.project;

import java.util.ArrayList;
import java.util.List;

public class Flat {
    private String area;
    private int quantityOfRooms;
    private int squareOfFlat;
    private int kitchenSquare;
    private int price;

    public Flat(String area, int quantityOfRooms, int squareOfFlat, int kitchenSquare, int price) {
        this.area = area;
        this.quantityOfRooms = quantityOfRooms;
        this.squareOfFlat = squareOfFlat;
        this.kitchenSquare = kitchenSquare;
        this.price = price;
    }

    public String getArea(){
        return area;
    }

    public int getQuantityOfRooms(){
        return quantityOfRooms;
    }

    public int getSquareOfFlat(){
        return squareOfFlat;
    }

    public int getKitchenSquare() {return kitchenSquare;}

    public int getPrice(){
        return price;
    }

    public static Flat arrayToFlat(String[] array){
        Flat flat = new Flat(array[0], Integer.parseInt(array[1]), Integer.parseInt(array[2]), Integer.parseInt(array[3]), Integer.parseInt(array[4])); //вызов конструктора
        return flat;
    }

    public static List<Flat> listToFlat(List<String []> list){
        List<Flat> database = new ArrayList<>();

        for(String[] i : list){
            database.add(arrayToFlat(i));
        }
        return database;
    }

    public static String[][] listToArr(List<Flat> list){
        String [][] array = new String[list.size()][5];

        for(int i = 0; i < list.size(); i++){
            Flat flat = list.get(i);
            array[i][0] = flat.area;
            array[i][1] = String.valueOf(flat.quantityOfRooms);
            array[i][2] = String.valueOf(flat.squareOfFlat);
            array[i][3] = String.valueOf(flat.kitchenSquare);
            array[i][4] = String.valueOf(flat.price);

        }

        return array;
    }
}
