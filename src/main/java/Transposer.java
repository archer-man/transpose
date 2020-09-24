import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Transposer {

    private static int wordSymbolLimit;
    private static boolean cropTheWord;
    private static boolean rightSideAlignment;
    private static List<String> arguments;
    //String outputFileName;
    //String inputFileName;
    //List<String> arguments;

    public Transposer(int wordSymbolLimit, boolean cropTheWord, boolean rightSideAlignment/*, /*String outputFileName, String inputFileName, List arguments*/) {
        this.wordSymbolLimit = wordSymbolLimit;
        this.cropTheWord = cropTheWord;
        this.rightSideAlignment = rightSideAlignment;
        //this.outputFileName=outputFileName;
        //this.inputFileName=inputFileName;
        this.arguments = arguments;

    }

    //(wordSymbolLimit, cropTheWord, rightSideAlignment, outputFileName, inputFileName, arguments);

    //public void transpose(int wordSymbolLimit, boolean cropTheWord, boolean rightSideAlignment, String outputFileName, String inputFileName, ArrayList arguments) {
    /*private static String printMatrix(ArrayList<ArrayList<String>> matrix) throws IOException {
        matrix.forEach(row -> System.out.println(String.join(" ", row)));
    }*/
    private static ArrayList<String> alterString(ArrayList<String> list, boolean limitWasZero) throws IOException {
        //try{
        if (wordSymbolLimit != 0 && !limitWasZero) {
            for (int i = 0; i < list.size(); i++) {
                if (!rightSideAlignment) {
                    if (list.get(i).length() >= wordSymbolLimit && cropTheWord) {
                        list.set(i, list.get(i).substring(0, wordSymbolLimit));
                    } else if (list.get(i).length() <= wordSymbolLimit) {
                        list.set(i, String.format("%1$-" + wordSymbolLimit + "s", list.get(i)));
                    } else {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Encountered word " + list.get(i) + " exceeds the specified character limit (character limit: "+wordSymbolLimit+"), but the trim flag was not specified.");
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
                        System.out.println("Encountered word " + list.get(i) + " exceeds the specified character limit (character limit: "+wordSymbolLimit+"), but the trim flag was not specified.");
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
                //}
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
        /*} catch (IOException e) {
        e.printStackTrace();
    }*/
            }
        }
        return list;
    }

    public static void/*ArrayList<ArrayList<String>>*/ transpose(BufferedReader in, BufferedWriter out/*, int symbolLimit, boolean crop, boolean rightSideArrayList arguments*/) throws IOException {
        int lineNumber = 0;
        /*while (in.readLine() != null) {
            lineNumber++;
        }*/
        ArrayList<ArrayList<String>> originalMatrix = new ArrayList<>();
        ArrayList<ArrayList<String>> transposedMatrix = new ArrayList<>();
        boolean limitWasZero = false;
        if (wordSymbolLimit ==0){
            limitWasZero = true;
        }
        try {
            /*File myFile = new File(inputName);
            FileReader fileReader = new FileReader(myFile);
            LineNumberReader lineNumberReader = new LineNumberReader(fileReader);*/
            String line;
            int i = 0;
            //ArrayList<String> word = new ArrayList<>();
            while ((line = in.readLine()) != null) {
                //System.out.println(line);
                //for (int i=0; i<6;i++) {
                if (!line.trim().isEmpty()) {
                    originalMatrix.add(new ArrayList<String>());
                    ArrayList<String> word = new ArrayList<String>(Arrays.asList(line.split("\\s+")));
                    //word = line.split("\\s+").;
                    //Collections.addAll(originalMatrix.get(i), word);
                    //originalMatrix.get(i).addAll(0,word);
                    //alterString(word, limitWasZero);
                    originalMatrix.set(i, word);
                    i++;
                    //}
                } else {
                    break;
                }
            }
            for(int h = 0; h <originalMatrix.size();h++){
                    originalMatrix.set(h, alterString(originalMatrix.get(h), limitWasZero));
            }
            /*String str;
        //str = in.readLine();
            //while ((str = in.readLine()) != null) {
                for (int row = 0; row < lineNumber; row++) {
                    str = in.readLine();
                    //while ((str = in.readLine()) != null) {
                    //for (int col = 0; col < 3; col++) {
                    //while ((str = in.readLine()) != null) {
                    //String[] word = str.split(" ");
                    originalMatrix[row] = str.split(" ");

                    //}
                //}
            //}
            //return originalMatrix; 1*/
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
                        if (wordSymbolLimit!=0 && !limitWasZero) {
                            transposedMatrixRow.add(String.format("%1$-" + wordSymbolLimit + "s", ""));
                        } else if (wordSymbolLimit != 0) {
                            wordSymbolLimit = 1;
                            for (int k=0; k<oldRowElements.size(); k++) {
                                if (oldRowElements.get(k).length() > wordSymbolLimit) {
                                    wordSymbolLimit = oldRowElements.get(k).length();
                                }
                            }
                            transposedMatrixRow.add(String.format("%1$-" + wordSymbolLimit + "s", ""));
                        } /*else {
                            int maxStringLength = 1;
                            for (int b=0; b<oldRowElements.size();b++){
                                if (oldRowElements.get(b).length()>maxStringLength){
                                    maxStringLength =oldRowElements.get(b).length();
                                }
                                wordSymbolLimit = maxStringLength;
                            }
                            transposedMatrixRow.add(String.format("%1$-" + wordSymbolLimit + "s", ""));
                            //transposedMatrixRow.add("1");
                        }*/
                    }
                    try {
                        transposedMatrix.set(c, transposedMatrixRow);
                    } catch (IndexOutOfBoundsException e) {
                        transposedMatrix.add(transposedMatrixRow);
                    }
                }
            }
            //return transposedMatrix;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return originalMatrix;
        //return transposedMatrix;

        /*transposedMatrix.forEach(row -> {
            try {
                out.write(String.join(" ", row));
                if(transposedMatrix.indexOf(row) != transposedMatrix.size()){
                out.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {*/
                /*if (out != null) {
                    transposedMatrix.forEach(line -> String.join(" ", line));
                }*/
        //}
        //);
        for (int i = 0; i < transposedMatrix.size(); i++) {
            out.write(String.join(" ", transposedMatrix.get(i)));
            if (transposedMatrix.indexOf(transposedMatrix.get(i)) != transposedMatrix.size() - 1) {
                out.newLine();
            }
        }
        //return "done";
        //return printMatrix(transposedMatrix);
    }


    /*public static String/*ArrayList<ArrayList<String>>*/ /*transpose(String inputName, String outputName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputName))) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputName))) {
                return transpose(reader, writer);

            }
        } catch (FileNotFoundException e) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
            return transpose(reader, writer);
        }
        /*int b=0;*/
    //return "alalal";
    //}

    public static void/*ArrayList<ArrayList<String>>*/ transpose(InputStreamReader inputStream, OutputStreamWriter outputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(inputStream)) {
            try (BufferedWriter writer = new BufferedWriter(outputStream)) {
                transpose(reader, writer);
            }
        }
    }

    //public static String/*ArrayList<ArrayList<String>>*/ transposeFromConsole() throws IOException {
        /*try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
                return transpose(reader, writer);
            }
        }
    }*/
}