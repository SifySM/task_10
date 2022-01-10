package edu.vsu.ru.project;

import edu.vsu.ru.util.ArrayUtils;
import edu.vsu.ru.util.ListUtils;

import java.io.PrintStream;
import java.util.List;

import static java.lang.System.out;

public class ConsoleMain {

    public static CmdParams parseArgs(String[] args) {
        CmdParams params = new CmdParams();

        if (args.length > 0) {
            if (args[0].equals("--help")) {
                params.help = true;
                return params;
            }
            if (args[0].equals("--window")) {
                params.window = true;
                return params;
            }
            if (!args[0].equals("-i")) {
                params.error = true;
                params.help = true;
                return params;
            }

            if (args.length < 2) {
                params.help = true;
                params.error = true;
                return params;
            }

            params.inputFile = args[1];

            for (int i = 3; i < args.length; i += 2) {
                if (args[i - 1].equals("-o")) {
                    params.outputFile = args[i];
                    continue;
                }

                if (args[i - 1].equals("-minRooms")) {
                    params.minRooms = Integer.parseInt(args[i]);
                    continue;
                }

                if (args[i - 1].equals("-maxRooms")) {
                    params.maxRooms = Integer.parseInt(args[i]);
                    continue;
                }

                if (args[i - 1].equals("-minS")) {
                    params.minS = Integer.parseInt(args[i]);
                    continue;
                }

                if (args[i - 1].equals("-maxS")) {
                    params.maxS = Integer.parseInt(args[i]);
                    continue;
                }

                if (args[i - 1].equals("-minKitchenS")) {
                    params.minKitchenS = Integer.parseInt(args[i]);
                    continue;
                }

                if (args[i - 1].equals("-maxKitchenS")) {
                    params.maxKitchenS = Integer.parseInt(args[i]);
                    continue;
                }

                if (args[i - 1].equals("-minPrice")) {
                    params.minPrice = Integer.parseInt(args[i]);
                    continue;
                }

                if (args[i - 1].equals("-maxPrice")) {
                    params.maxPrice = Integer.parseInt(args[i]);
                    continue;
                } else {
                    params.help = true;
                    params.error = true;
                }
            }

        } else {
            params.help = true;
            params.error = true;
        }
        return params;
    }

    public static void main(String[] args) throws Exception {
        CmdParams params = parseArgs(args);

        if (params.help) {
            PrintStream out = params.error ? System.err : System.out;
            out.println("Usage:");
            out.println("  <cmd> args <input-file> (<output-file>)");
            out.println("    -r  // reverse rows");
            out.println("    -c  // reverse columns");
            out.println("  <cmd> --help");
            out.println("  <cmd> --window  // show window");
            System.exit(params.error ? 1 : 0);
        }
        if (params.window) {
            GUI_Main.winMain();
        } else {
            List<String[]> list = ListUtils.readListFromFile(params.inputFile);
            if (list == null) {
                System.err.printf("Can't read array from \"%s\"%n", params.inputFile);
                System.exit(2);
            } else {
            if (params.outputFile != null) {
                ArrayUtils.writeArrayToFile(params.outputFile, Flat.listToArr(Filter.FlatSearch(Flat.listToFlat(list),
                        params.minRooms, params.maxRooms, params.minS, params.maxS, params.minKitchenS, params.maxKitchenS,
                        params.minPrice, params.maxPrice)));
            } else {
                System.out.print(ArrayUtils.toString(Flat.listToArr(Filter.FlatSearch(Flat.listToFlat(list),
                        params.minRooms, params.maxRooms, params.minS, params.maxS, params.minKitchenS, params.maxKitchenS,
                        params.minPrice, params.maxPrice)), null));
            }
            out.close();
        }
        }
    }

    public static class CmdParams {
        public String inputFile;
        public String outputFile;
        public boolean error;
        public boolean help;
        public boolean window;
        public int minRooms;
        public int maxRooms;
        public int minS;
        public int maxS;
        public int minKitchenS;
        public int maxKitchenS;
        public int minPrice;
        public int maxPrice;
    }
}
