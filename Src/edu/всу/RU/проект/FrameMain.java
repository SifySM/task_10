package edu.vsu.ru.project;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import edu.vsu.ru.util.ArrayUtils;
import edu.vsu.ru.util.JTableUtils;
import edu.vsu.ru.util.ListUtils;
import edu.vsu.ru.util.SwingUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.NumberFormatter;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FrameMain extends JFrame {
    private JPanel panelMain;
    private JTable tableDatabase;
    private JButton buttonResetFilters;
    private JButton buttonLoadDataFromFile;
    private JButton buttonSearch;
    private JButton buttonSaveDatabaseToFile;
    private JCheckBox ZhAreaCheckBox;
    private JCheckBox SAreaCheckBox;
    private JCheckBox LAreaCheckBox;
    private JCheckBox TsAreaCheckBox;
    private JFormattedTextField formattedTextFieldMinQuanOfRooms;
    private JFormattedTextField formattedTextFieldMaxQuanOfRooms;
    private JFormattedTextField formattedTextFieldMinSOfFlat;
    private JFormattedTextField formattedTextFieldMaxSOfFlat;
    private JFormattedTextField formattedTextFieldMinKitchenS;
    private JFormattedTextField formattedTextFieldMaxKitchenS;
    private JFormattedTextField formattedTextFieldMinPrice;
    private JFormattedTextField formattedTextFieldMaxPrice;

    private final JFileChooser fileChooserOpen;
    private final JFileChooser fileChooserSave;

    private String[][] array;

    public FrameMain() {
        this.setTitle("FrameMain");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        JTableUtils.initJTableForArray(tableDatabase, 134, true, true, true, false);

        tableDatabase.setRowHeight(25);
        JTableUtils.writeArrayToJTable(tableDatabase, new String[][]{
                {"Железнодорожный", "2", "56", "15", "2400000"}
        });
        tableDatabase.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        tableDatabase.getColumnModel().getColumn(0).setMinWidth(200);
        tableDatabase.getColumnModel().getColumn(0).setMaxWidth(200);
        tableDatabase.getColumnModel().getColumn(1).setMinWidth(130);
        tableDatabase.getColumnModel().getColumn(1).setMaxWidth(130);
        tableDatabase.getColumnModel().getColumn(2).setMaxWidth(120);
        tableDatabase.getColumnModel().getColumn(2).setMaxWidth(120);
        tableDatabase.getColumnModel().getColumn(3).setMinWidth(120);
        tableDatabase.getColumnModel().getColumn(3).setMaxWidth(120);
        tableDatabase.getColumnModel().getColumn(4).setMinWidth(100);
        tableDatabase.getColumnModel().getColumn(4).setMaxWidth(100);

        tableDatabase.getColumnModel().getColumn(0).setHeaderValue("Area");
        tableDatabase.getColumnModel().getColumn(1).setHeaderValue("Quantity of Rooms");
        tableDatabase.getColumnModel().getColumn(2).setHeaderValue("Square Of Flat");
        tableDatabase.getColumnModel().getColumn(3).setHeaderValue("Square Of Kitchen");
        tableDatabase.getColumnModel().getColumn(4).setHeaderValue("Price");

        tableDatabase.getTableHeader().setFont(new Font("Calibri Light", Font.BOLD, 14));

        final TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableDatabase.getModel());
        tableDatabase.setRowSorter(sorter);

        formattedTextFieldMinQuanOfRooms.setColumns(1);
        formattedTextFieldMinQuanOfRooms.setValue(0);
        formattedTextFieldMaxQuanOfRooms.setValue(99);
        formattedTextFieldMinSOfFlat.setValue(0);
        formattedTextFieldMaxSOfFlat.setValue(999);
        formattedTextFieldMinKitchenS.setValue(0);
        formattedTextFieldMaxKitchenS.setValue(999);
        formattedTextFieldMinPrice.setValue(0);
        formattedTextFieldMaxPrice.setValue(999999999);

        NumberFormatter numberFormatter = (NumberFormatter) formattedTextFieldMaxQuanOfRooms.getFormatter();
        numberFormatter.setMaximum(7);

        fileChooserOpen = new JFileChooser();
        fileChooserSave = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File("."));
        fileChooserSave.setCurrentDirectory(new File("."));
        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen.addChoosableFileFilter(filter);
        fileChooserSave.addChoosableFileFilter(filter);

        fileChooserSave.setAcceptAllFileFilterUsed(false);
        fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserSave.setApproveButtonText("Save");

        JMenuBar menuBarMain = new JMenuBar();
        setJMenuBar(menuBarMain);

        JMenu menuLookAndFeel = new JMenu();
        menuLookAndFeel.setText("Вид");
        menuBarMain.add(menuLookAndFeel);
        SwingUtils.initLookAndFeelMenu(menuLookAndFeel);
        this.pack();

        buttonLoadDataFromFile.addActionListener(actionEvent -> {
            try {
                if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                    array = ListUtils.strListToMatrix(ListUtils.readListFromFile(fileChooserOpen.getSelectedFile().getPath()));
                    JTableUtils.writeArrayToJTable(tableDatabase, array);

                    resentFilters();

                    tableDatabase.getColumnModel().getColumn(0).setHeaderValue("Area");
                    tableDatabase.getColumnModel().getColumn(1).setHeaderValue("Quantity of Rooms");
                    tableDatabase.getColumnModel().getColumn(2).setHeaderValue("Square Of Flat");
                    tableDatabase.getColumnModel().getColumn(3).setHeaderValue("Square Of Kitchen");
                    tableDatabase.getColumnModel().getColumn(4).setHeaderValue("Price");

                }
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });
        buttonSaveDatabaseToFile.addActionListener(actionEvent -> {
            try {
                if (fileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                    String[][] matrix = JTableUtils.readStringMatrixFromJTable(tableDatabase);
                    String file = fileChooserSave.getSelectedFile().getPath();
                    if (!file.toLowerCase().endsWith(".txt")) {
                        file += ".txt";
                    }
                    ArrayUtils.writeArrayToFile(file, matrix);

                }
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });
        buttonSearch.addActionListener(actionEvent -> {
            try {
                JTableUtils.writeArrayToJTable(tableDatabase, array);
                String[][] array = JTableUtils.readStringMatrixFromJTable(tableDatabase);
                int minRooms = (int) formattedTextFieldMinQuanOfRooms.getValue();
                int maxRooms = (int) formattedTextFieldMaxQuanOfRooms.getValue();
                int minSquare = (int) formattedTextFieldMinSOfFlat.getValue();
                int maxSquare = (int) formattedTextFieldMaxSOfFlat.getValue();
                int minKitchenS = (int) formattedTextFieldMinKitchenS.getValue();
                int maxKitchenS = (int) formattedTextFieldMaxKitchenS.getValue();
                int minPrice = (int) formattedTextFieldMinPrice.getValue();
                int maxPrice = (int) formattedTextFieldMaxPrice.getValue();

                List<RowFilter<Object, Object>> AreaFilters = new ArrayList<>(4);

                if (AreaFilters.size() != 0) {
                    sorter.setRowFilter(RowFilter.orFilter(AreaFilters));
                } else {
                    sorter.setRowFilter(null);
                }

                JTableUtils.writeArrayToJTable(tableDatabase, Flat.listToArr(Filter.FlatSearch(Flat.listToFlat(ListUtils.strMatrixToList(array)),
                        minRooms, maxRooms, minSquare, maxSquare, minKitchenS, maxKitchenS, minPrice, maxPrice)));

                if (LAreaCheckBox.isSelected()) {
                    AreaFilters.add(RowFilter.regexFilter(LAreaCheckBox.getText()));
                }

                if (TsAreaCheckBox.isSelected()) {
                    AreaFilters.add(RowFilter.regexFilter(TsAreaCheckBox.getText()));
                }

                if (SAreaCheckBox.isSelected()) {
                    AreaFilters.add(RowFilter.regexFilter(SAreaCheckBox.getText()));
                }

                if (ZhAreaCheckBox.isSelected()) {
                    AreaFilters.add(RowFilter.regexFilter(ZhAreaCheckBox.getText()));
                }
                if (AreaFilters.size() != 0) {
                    sorter.setRowFilter(RowFilter.orFilter(AreaFilters));
                } else {
                    sorter.setRowFilter(null);
                }

            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });
        buttonResetFilters.addActionListener(actionEvent -> {
            try {
                resentFilters();
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });
    }

    private void resentFilters() {
        formattedTextFieldMinQuanOfRooms.setValue(1);
        formattedTextFieldMaxQuanOfRooms.setValue(99);
        formattedTextFieldMinSOfFlat.setValue(0);
        formattedTextFieldMaxSOfFlat.setValue(999);
        formattedTextFieldMinKitchenS.setValue(0);
        formattedTextFieldMaxKitchenS.setValue(999);
        formattedTextFieldMinPrice.setValue(0);
        formattedTextFieldMaxPrice.setValue(999999999);
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
