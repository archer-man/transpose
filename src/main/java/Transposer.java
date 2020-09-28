import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;

public class Transposer {

    private int wordSymbolLimit;
    private boolean cropTheWord;
    private final boolean rightSideAlignment;

    public Transposer(int wordSymbolLimit, boolean cropTheWord, boolean rightSideAlignment) {
        this.wordSymbolLimit = wordSymbolLimit;
        this.cropTheWord = cropTheWord;
        this.rightSideAlignment = rightSideAlignment;
    }

    private ArrayList<String> alterString(ArrayList<String> list, boolean limitWasZero) {
        try {
            if (wordSymbolLimit != 0 && !limitWasZero) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).length() >= wordSymbolLimit && cropTheWord) {
                        if (!rightSideAlignment) {
                            list.set(i, list.get(i).substring(0, wordSymbolLimit));
                        } else {
                            list.set(i, list.get(i).substring(list.get(i).length() - wordSymbolLimit));
                        }
                    } else if (list.get(i).length() <= wordSymbolLimit) {
                        if (!rightSideAlignment) {
                            list.set(i, String.format("%1$-" + wordSymbolLimit + "s", list.get(i)));
                        } else {
                            list.set(i, String.format("%1$" + wordSymbolLimit + "s", list.get(i)));
                        }
                    } else {
                        throw new RuntimeException(list.get(i) + " element exceeds specified symbol limit. Add -t launch argument to trim words.");
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

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public void transpose(BufferedReader in, BufferedWriter out) throws IOException {
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

    public void transpose(InputStreamReader inputStream, OutputStreamWriter outputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(inputStream)) {
            try (BufferedWriter writer = new BufferedWriter(outputStream)) {
                transpose(reader, writer);
            }
        }
    }
}