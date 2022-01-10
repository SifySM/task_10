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


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelMain = new JPanel();
        panelMain.setLayout(new GridLayoutManager(9, 2, new Insets(0, 0, 0, 0), -1, -1));
        final JScrollPane scrollPane1 = new JScrollPane();
        panelMain.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(800, 200), new Dimension(800, 200), new Dimension(800, -1), 0, false));
        scrollPane1.setBorder(BorderFactory.createTitledBorder(null, "Database", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, this.$$$getFont$$$("Copperplate Gothic Light", -1, 18, scrollPane1.getFont()), null));
        tableDatabase = new JTable();
        tableDatabase.setAlignmentX(0.8f);
        tableDatabase.setAlignmentY(0.8f);
        tableDatabase.setAutoCreateRowSorter(false);
        tableDatabase.setAutoResizeMode(0);
        tableDatabase.setEnabled(true);
        tableDatabase.setFillsViewportHeight(false);
        Font tableDatabaseFont = this.$$$getFont$$$(null, -1, 14, tableDatabase.getFont());
        if (tableDatabaseFont != null) tableDatabase.setFont(tableDatabaseFont);
        tableDatabase.setInheritsPopupMenu(false);
        tableDatabase.setPreferredScrollableViewportSize(new Dimension(800, 400));
        tableDatabase.setShowHorizontalLines(true);
        tableDatabase.setShowVerticalLines(true);
        tableDatabase.setUpdateSelectionOnSort(false);
        scrollPane1.setViewportView(tableDatabase);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(403, 24), null, 0, false));
        buttonLoadDataFromFile = new JButton();
        buttonLoadDataFromFile.setText("Load data from file");
        panel1.add(buttonLoadDataFromFile, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        buttonSearch = new JButton();
        buttonSearch.setText("Filter");
        panel1.add(buttonSearch, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel2, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 5, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel3, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(null, "Square of Flat", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, null));
        final Spacer spacer2 = new Spacer();
        panel3.add(spacer2, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel3.add(spacer3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        formattedTextFieldMinSOfFlat = new JFormattedTextField();
        Font formattedTextFieldMinSOfFlatFont = this.$$$getFont$$$("Consolas", Font.ITALIC, -1, formattedTextFieldMinSOfFlat.getFont());
        if (formattedTextFieldMinSOfFlatFont != null)
            formattedTextFieldMinSOfFlat.setFont(formattedTextFieldMinSOfFlatFont);
        panel3.add(formattedTextFieldMinSOfFlat, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        formattedTextFieldMaxSOfFlat = new JFormattedTextField();
        Font formattedTextFieldMaxSOfFlatFont = this.$$$getFont$$$("Consolas", Font.ITALIC, -1, formattedTextFieldMaxSOfFlat.getFont());
        if (formattedTextFieldMaxSOfFlatFont != null)
            formattedTextFieldMaxSOfFlat.setFont(formattedTextFieldMaxSOfFlatFont);
        panel3.add(formattedTextFieldMaxSOfFlat, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, Font.ITALIC, 12, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("from");
        panel3.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(30, -1), null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$(null, Font.ITALIC, 12, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("to");
        panel3.add(label2, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(15, -1), null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel4, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel4.setBorder(BorderFactory.createTitledBorder(null, "Kitchen Square", TitledBorder.CENTER, TitledBorder.TOP, null, null));
        final Spacer spacer4 = new Spacer();
        panel4.add(spacer4, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        formattedTextFieldMinKitchenS = new JFormattedTextField();
        Font formattedTextFieldMinKitchenSFont = this.$$$getFont$$$("Consolas", Font.ITALIC, -1, formattedTextFieldMinKitchenS.getFont());
        if (formattedTextFieldMinKitchenSFont != null)
            formattedTextFieldMinKitchenS.setFont(formattedTextFieldMinKitchenSFont);
        formattedTextFieldMinKitchenS.setText("");
        panel4.add(formattedTextFieldMinKitchenS, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        formattedTextFieldMaxKitchenS = new JFormattedTextField();
        Font formattedTextFieldMaxKitchenSFont = this.$$$getFont$$$("Consolas", Font.ITALIC, -1, formattedTextFieldMaxKitchenS.getFont());
        if (formattedTextFieldMaxKitchenSFont != null)
            formattedTextFieldMaxKitchenS.setFont(formattedTextFieldMaxKitchenSFont);
        panel4.add(formattedTextFieldMaxKitchenS, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$(null, Font.ITALIC, 12, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("from");
        panel4.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(30, -1), null, null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$(null, Font.ITALIC, 12, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setText("to");
        panel4.add(label4, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(15, -1), null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel5, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel5.setBorder(BorderFactory.createTitledBorder(null, "PRICE", TitledBorder.CENTER, TitledBorder.TOP, null, null));
        final Spacer spacer5 = new Spacer();
        panel5.add(spacer5, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        formattedTextFieldMinPrice = new JFormattedTextField();
        Font formattedTextFieldMinPriceFont = this.$$$getFont$$$("Consolas", Font.ITALIC, -1, formattedTextFieldMinPrice.getFont());
        if (formattedTextFieldMinPriceFont != null) formattedTextFieldMinPrice.setFont(formattedTextFieldMinPriceFont);
        panel5.add(formattedTextFieldMinPrice, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        formattedTextFieldMaxPrice = new JFormattedTextField();
        Font formattedTextFieldMaxPriceFont = this.$$$getFont$$$("Consolas", Font.ITALIC, -1, formattedTextFieldMaxPrice.getFont());
        if (formattedTextFieldMaxPriceFont != null) formattedTextFieldMaxPrice.setFont(formattedTextFieldMaxPriceFont);
        formattedTextFieldMaxPrice.setText("");
        panel5.add(formattedTextFieldMaxPrice, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$(null, Font.ITALIC, 12, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setText("from");
        panel5.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(30, -1), null, null, 0, false));
        final JLabel label6 = new JLabel();
        Font label6Font = this.$$$getFont$$$(null, Font.ITALIC, 12, label6.getFont());
        if (label6Font != null) label6.setFont(label6Font);
        label6.setHorizontalAlignment(0);
        label6.setHorizontalTextPosition(2);
        label6.setText("to");
        panel5.add(label6, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(15, -1), null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        panelMain.add(spacer6, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel6.setAutoscrolls(false);
        panel6.setDoubleBuffered(false);
        panel6.setEnabled(false);
        panel6.setFocusCycleRoot(false);
        panel6.setFocusTraversalPolicyProvider(false);
        panel6.setFocusable(true);
        panelMain.add(panel6, new GridConstraints(0, 1, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(-1, 200), new Dimension(-1, 200), new Dimension(-1, 200), 0, false));
        panel6.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Filters", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, this.$$$getFont$$$("Copperplate Gothic Light", -1, 18, panel6.getFont()), new Color(-16777216)));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), 0, 0, true, true));
        panel7.setAlignmentX(1.0f);
        panel7.setAlignmentY(1.0f);
        panel7.setAutoscrolls(false);
        panel7.setOpaque(true);
        panel7.setRequestFocusEnabled(true);
        panel7.setVerifyInputWhenFocusTarget(true);
        panel7.setVisible(true);
        panel6.add(panel7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 400), null, 0, true));
        panel7.setBorder(BorderFactory.createTitledBorder(null, "Area", TitledBorder.CENTER, TitledBorder.TOP, null, null));
        ZhAreaCheckBox = new JCheckBox();
        ZhAreaCheckBox.setText("Железнодорожный");
        panel7.add(ZhAreaCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SAreaCheckBox = new JCheckBox();
        SAreaCheckBox.setText("Советский");
        panel7.add(SAreaCheckBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        LAreaCheckBox = new JCheckBox();
        LAreaCheckBox.setText("Левобережный");
        panel7.add(LAreaCheckBox, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        TsAreaCheckBox = new JCheckBox();
        TsAreaCheckBox.setText("Центральный");
        panel7.add(TsAreaCheckBox, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        panelMain.add(spacer7, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        buttonResetFilters = new JButton();
        buttonResetFilters.setText("Reset Filters");
        panelMain.add(buttonResetFilters, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel8, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonSaveDatabaseToFile = new JButton();
        buttonSaveDatabaseToFile.setText("Save data base to file");
        panel8.add(buttonSaveDatabaseToFile, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        panel8.add(spacer8, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        panel8.add(spacer9, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(2, 5, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel9, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel9.setBorder(BorderFactory.createTitledBorder(null, "Quantity of Rooms", TitledBorder.CENTER, TitledBorder.TOP, null, null));
        final Spacer spacer10 = new Spacer();
        panel9.add(spacer10, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer11 = new Spacer();
        panel9.add(spacer11, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        formattedTextFieldMinQuanOfRooms = new JFormattedTextField();
        Font formattedTextFieldMinQuanOfRoomsFont = this.$$$getFont$$$("Consolas", Font.ITALIC, -1, formattedTextFieldMinQuanOfRooms.getFont());
        if (formattedTextFieldMinQuanOfRoomsFont != null)
            formattedTextFieldMinQuanOfRooms.setFont(formattedTextFieldMinQuanOfRoomsFont);
        formattedTextFieldMinQuanOfRooms.setText("");
        panel9.add(formattedTextFieldMinQuanOfRooms, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        formattedTextFieldMaxQuanOfRooms = new JFormattedTextField();
        Font formattedTextFieldMaxQuanOfRoomsFont = this.$$$getFont$$$("Consolas", Font.ITALIC, -1, formattedTextFieldMaxQuanOfRooms.getFont());
        if (formattedTextFieldMaxQuanOfRoomsFont != null)
            formattedTextFieldMaxQuanOfRooms.setFont(formattedTextFieldMaxQuanOfRoomsFont);
        panel9.add(formattedTextFieldMaxQuanOfRooms, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label7 = new JLabel();
        Font label7Font = this.$$$getFont$$$(null, Font.ITALIC, 12, label7.getFont());
        if (label7Font != null) label7.setFont(label7Font);
        label7.setHorizontalTextPosition(2);
        label7.setText("from");
        panel9.add(label7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(30, -1), null, null, 0, false));
        final JLabel label8 = new JLabel();
        Font label8Font = this.$$$getFont$$$(null, Font.ITALIC, 12, label8.getFont());
        if (label8Font != null) label8.setFont(label8Font);
        label8.setText("to");
        panel9.add(label8, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(15, -1), null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }

    /**
     * @noinspection ALL
     */

}
