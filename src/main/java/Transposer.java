import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Transposer {

    private static int wordSymbolLimit;
    private static boolean cropTheWord;
    private static boolean rightSideAlignment;

    public Transposer(int wordSymbolLimit, boolean cropTheWord, boolean rightSideAlignment) {
        this.wordSymbolLimit = wordSymbolLimit;
        this.cropTheWord = cropTheWord;
        this.rightSideAlignment = rightSideAlignment;
    }

    private static ArrayList<String> alterString(ArrayList<String> list, boolean limitWasZero) throws IOException {
        if (wordSymbolLimit != 0 && !limitWasZero) {
            for (int i = 0; i < list.size(); i++) {
                if (!rightSideAlignment) {
                    if (list.get(i).length() >= wordSymbolLimit && cropTheWord) {
                        list.set(i, list.get(i).substring(0, wordSymbolLimit));
                    } else if (list.get(i).length() <= wordSymbolLimit) {
                        list.set(i, String.format("%1$-" + wordSymbolLimit + "s", list.get(i)));
                    } else {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Encountered word " + list.get(i) + " exceeds the specified character limit (character limit: " + wordSymbolLimit + "), but the trim flag was not specified.");
                        System.out.println("You can rerun program with \"-t\" flag, apply trimming only to this word or apply trimming for all words.");
                        System.out.println("Type \"ONCE\" to trim only this word, \"Y\" to trim all words or \"N\" to stop the program.");
                        String answer = scan.nextLine();
                        switch (answer) {
                            case "ONCE":
                            case "once":
                                list.set(i, list.get(i).substring(0, wordSymbolLimit));
                                break;
                            case "Y":
                            case "y":
                                list.set(i, list.get(i).substring(0, wordSymbolLimit));
                                cropTheWord = true;
                                break;
                            case "N":
                            case "n":
                                System.exit(0);
                            default:
                                System.out.println("Invalid argument");
                                System.exit(1);
                        }
                    }
                } else {
                    if (list.get(i).length() >= wordSymbolLimit && cropTheWord) {
                        list.set(i, list.get(i).substring(list.get(i).length() - wordSymbolLimit));
                    } else if (list.get(i).length() <= wordSymbolLimit) {
                        list.set(i, String.format("%1$" + wordSymbolLimit + "s", list.get(i)));
                    } else {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Encountered word " + list.get(i) + " exceeds the specified character limit (character limit: " + wordSymbolLimit + "), but the trim flag was not specified.");
                        System.out.println("You can rerun program with \"-t\" flag, apply trimming only to this word or apply trimming for all words.");
                        System.out.println("Type \"ONCE\" to trim only this word, \"Y\" to trim all words or \"N\" to stop the program.");
                        String answer = scan.nextLine();
                        switch (answer) {
                            case "ONCE":
                            case "once":
                                list.set(i, list.get(i).substring(list.get(i).length() - wordSymbolLimit));
                                break;
                            case "Y":
                            case "y":
                                list.set(i, list.get(i).substring(list.get(i).length() - wordSymbolLimit));
                                cropTheWord = true;
                                break;
                            case "N":
                            case "n":
                                System.exit(0);
                            default:
                                System.out.println("Invalid argument");
                                System.exit(1);
                        }
                    }
                }
            }
        } else {
            int maxStringLength = 1;
            for (int b = 0; b < list.size(); b++) {
                if (list.get(b).length() > maxStringLength) {
                    maxStringLength = list.get(b).length();
                }
                wordSymbolLimit = maxStringLength;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).length() < wordSymbolLimit) {
                        list.set(i, String.format("%1$-" + wordSymbolLimit + "s", list.get(i)));
                    }
                }
            }
        }
        return list;
    }

    public static void transpose(BufferedReader in, BufferedWriter out) throws IOException {
        ArrayList<ArrayList<String>> originalMatrix = new ArrayList<>();
        ArrayList<ArrayList<String>> transposedMatrix = new ArrayList<>();
        boolean limitWasZero = false;
        if (wordSymbolLimit == 0) {
            limitWasZero = true;
        }
        try {
            String line;
            int i = 0;
            while ((line = in.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    originalMatrix.add(new ArrayList<String>());
                    ArrayList<String> word = new ArrayList<String>(Arrays.asList(line.split("\\s+")));
                    originalMatrix.set(i, word);
                    i++;
                } else {
                    break;
                }
            }
            for (int h = 0; h < originalMatrix.size(); h++) {
                originalMatrix.set(h, alterString(originalMatrix.get(h), limitWasZero));
            }
            int rowNumber = originalMatrix.size();
            int columnNumber = 0;

            for (int m = 0; m < rowNumber; m++) {
                int rowLength = originalMatrix.get(m).size();
                if (rowLength > columnNumber) {
                    columnNumber = rowLength;
                }
            }
            for (int r = 0; r < rowNumber; r++) {
                ArrayList<String> oldRowElements = originalMatrix.get(r);

                for (int c = 0; c < columnNumber; c++) {
                    ArrayList<String> transposedMatrixRow = new ArrayList<>();
                    if (r != 0) {
                        try {
                            transposedMatrixRow = transposedMatrix.get(c);
                        } catch (IndexOutOfBoundsException e) {
                            transposedMatrixRow.add("");
                        }
                    }
                    try {
                        transposedMatrixRow.add(oldRowElements.get(c));
                    } catch (IndexOutOfBoundsException e) {
                        if (wordSymbolLimit != 0 && !limitWasZero) {
                            transposedMatrixRow.add(String.format("%1$-" + wordSymbolLimit + "s", ""));
                        } else if (wordSymbolLimit != 0) {
                            wordSymbolLimit = 1;
                            for (int k = 0; k < oldRowElements.size(); k++) {
                                if (oldRowElements.get(k).length() > wordSymbolLimit) {
                                    wordSymbolLimit = oldRowElements.get(k).length();
                                }
                            }
                            transposedMatrixRow.add(String.format("%1$-" + wordSymbolLimit + "s", ""));
                        }
                    }
                    try {
                        transposedMatrix.set(c, transposedMatrixRow);
                    } catch (IndexOutOfBoundsException e) {
                        transposedMatrix.add(transposedMatrixRow);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < transposedMatrix.size(); i++) {
            out.write(String.join(" ", transposedMatrix.get(i)));
            if (transposedMatrix.indexOf(transposedMatrix.get(i)) != transposedMatrix.size() - 1) {
                out.newLine();
            }
        }
    }

    public static void transpose(InputStreamReader inputStream, OutputStreamWriter outputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(inputStream)) {
            try (BufferedWriter writer = new BufferedWriter(outputStream)) {
                transpose(reader, writer);
            }
        }
    }
}