package edu.vsu.ru.project;

import java.util.ArrayList;
import java.util.List;

public class Filter {
    public static List<Flat> FlatSearch(List<Flat> flat, int minRooms, int maxRooms, int minS, int maxS, int minKitS,
                                              int maxKitS, int minPrice, int maxPrice) {
        List<Flat> filteredList = new ArrayList<>();

        if (maxRooms == 0) {
            maxRooms = 99;
        }
        if (maxS == 0) {
            maxS = 999;
        }
        if (maxKitS == 0) {
            maxKitS = 999;
        }
        if (maxPrice == 0) {
            maxPrice = 9999999;
        }

        for (int i = 0; i < flat.size(); i++) {
            Flat curFlat = flat.get(i);
            if ((curFlat.getQuantityOfRooms() <= maxRooms) && (curFlat.getQuantityOfRooms() >= minRooms) &&
                    (curFlat.getSquareOfFlat() <= maxS) && (curFlat.getSquareOfFlat() >= minS) &&
                    (curFlat.getKitchenSquare() >= minKitS) && (curFlat.getKitchenSquare() <= maxKitS) &&
                    (curFlat.getPrice() >= minPrice) && (curFlat.getPrice() <= maxPrice)) {
                filteredList.add(curFlat);
            }
        }return filteredList;
    }
}