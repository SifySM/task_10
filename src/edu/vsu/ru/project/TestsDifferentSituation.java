package edu.vsu.ru.project;

import edu.vsu.ru.util.ListUtils;
import org.junit.Assert;
import org.junit.Test;
import edu.vsu.ru.util.ArrayUtils;

import java.io.FileNotFoundException;
import java.util.List;

public class TestsDifferentSituation {
    @Test
    public void roomTest() throws FileNotFoundException {
        List<String[]> list = ListUtils.readListFromFile("TestCase/roomTest.txt");
        String[][] array = Flat.listToArr(Filter.FlatSearch(Flat.listToFlat(list), 3, 99, 0, 0, 0 , 0, 0, 0));
        String strArr = ArrayUtils.toString(array, null);

        List<String[]> resultList = ListUtils.readListFromFile("TestResult/roomTestResult.txt");
        String[][] resArr = ListUtils.strListToMatrix(resultList);
        String strResArr = ArrayUtils.toString(resArr, null);
        Assert.assertEquals(strArr, strResArr);
    }

    @Test
    public void squareTest() throws FileNotFoundException {
        List<String[]> list = ListUtils.readListFromFile("TestCase/squareTest.txt");
        String[][] array = Flat.listToArr(Filter.FlatSearch(Flat.listToFlat(list), 0, 99, 90, 0, 0 , 0, 0, 0));
        String strArr = ArrayUtils.toString(array, null);

        List<String[]> resultList = ListUtils.readListFromFile("TestResult/squareTestResult.txt");
        String[][] resArr = ListUtils.strListToMatrix(resultList);
        String strResArr = ArrayUtils.toString(resArr, null);
        Assert.assertEquals(strArr, strResArr);
    }

    @Test
    public void kitchenSquareTest() throws FileNotFoundException {
        List<String[]> list = ListUtils.readListFromFile("TestCase/kitchenSquareTest.txt");
        String[][] array = Flat.listToArr(Filter.FlatSearch(Flat.listToFlat(list), 0, 99, 0, 0, 20 , 0, 0, 0));
        String strArr = ArrayUtils.toString(array, null);

        List<String[]> resultList = ListUtils.readListFromFile("TestResult/kitchenSquareTestResult.txt");
        String[][] resArr = ListUtils.strListToMatrix(resultList);
        String strResArr = ArrayUtils.toString(resArr, null);
        Assert.assertEquals(strArr, strResArr);
    }

    @Test
    public void priceTest() throws FileNotFoundException {
        List<String[]> list = ListUtils.readListFromFile("TestCase/priceTest.txt");
        String[][] array = Flat.listToArr(Filter.FlatSearch(Flat.listToFlat(list), 0, 99, 0, 0, 0 , 0, 4150000, 0));
        String strArr = ArrayUtils.toString(array, null);

        List<String[]> resultList = ListUtils.readListFromFile("TestResult/priceTestResult.txt");
        String[][] resArr = ListUtils.strListToMatrix(resultList);
        String strResArr = ArrayUtils.toString(resArr, null);
        Assert.assertEquals(strArr, strResArr);
    }
}
