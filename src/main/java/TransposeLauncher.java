import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

public class TransposeLauncher {

    @Option(name = "-a", metaVar = "num", usage = "Each word's symbol limitation")
    private int wordSymbolLimit;

    @Option(name = "-t", usage = "Crop the word to the desired size")
    private boolean cropTheWord = false;

    @Option(name = "-r", usage = "Crop the word from the right end (default: crop from the left end)")
    private boolean rightSideAlignment = false;

    @Option(name = "-o", metaVar = "ofile", usage = "Output file name")
    private String outputFileName;

    @Argument(metaVar = "file", usage = "Input file name")
    private String inputFileName;

    /*@Argument(metaVar = "consolenput", index = 1, usage = "Input from console")
    private List<String> arguments = new ArrayList<String>();*/

    public static void main(String[] args) throws IOException {
        new TransposeLauncher().doMain(args);
    }

    public void doMain(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
            if (wordSymbolLimit == 0 && (cropTheWord || rightSideAlignment)) {
                wordSymbolLimit = 10;
            }
           /*if (outputFileName == null){
//outputFileName = System.out;
                //BufferedReader inputFileName = new BufferedReader(new InputStreamReader((InputStream) arguments));
                //outputFileName
            }*/
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("transpose [-a num] [-t] [-r] [-o ofile] [file]");
            parser.printUsage(System.err);
            return;
        }

        Transposer transposer = new Transposer(wordSymbolLimit, cropTheWord, rightSideAlignment);

        try {
            //if (outputFileName == null) {
                /*File tempFile = File.createTempFile("consoleInput", ".tmp");
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
                Scanner scanner = new Scanner(System.in);
                ArrayList<String> tokens = new ArrayList<>();
                while (scanner.hasNextLine()) {

                    Scanner lineScanner = new Scanner(scanner.nextLine());

                    while (lineScanner.hasNext()) {
                        bw.write(lineScanner.next());
                    }

                    lineScanner.close();
                    //System.out.println(tokens);
                }
                scanner.close();
                bw.close();
                inputFileName = tempFile.getPath();*/
                /*try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
                    try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))){

                    }
                }
                while (reader.readLine() != null) {

                    Scanner lineScanner = new Scanner(scanner.nextLine());

                    while (lineScanner.hasNext()) {
                        bw.write(lineScanner.next());
                    }*/
                /*Transposer.transposeFromConsole();
            } else{
                System.out.println(Transposer.transpose(inputFileName, outputFileName));
            }*/
            // 2
            if (inputFileName == null && outputFileName == null) {
                InputStreamReader inputStream = new InputStreamReader(System.in);
                OutputStreamWriter outputStream = new OutputStreamWriter(System.out);
                System.out.println("Input file was not specified. Please, enter the matrix separating the individual elements with space(-s).");
                Transposer.transpose(inputStream, outputStream);
                System.out.println("Output result:");
            } else if (inputFileName != null && outputFileName == null) {
                InputStreamReader inputStream = new InputStreamReader(new FileInputStream(inputFileName));
                OutputStreamWriter outputStream = new OutputStreamWriter(System.out);
                System.out.println("Input matrix will be obtained from " + inputFileName);
                System.out.println("Output result:");
                Transposer.transpose(inputStream, outputStream);
            } else if (inputFileName == null) {
                InputStreamReader inputStream = new InputStreamReader(System.in);
                OutputStreamWriter outputStream = new OutputStreamWriter(new FileOutputStream(outputFileName));
                System.out.println("Input file was not specified. Please, enter the matrix separating the individual elements with space(-s).");
                Transposer.transpose(inputStream, outputStream);
                System.out.println("Output result was successfully written to " + outputFileName);
            } else {
                InputStreamReader inputStream = new InputStreamReader(new FileInputStream(inputFileName));
                OutputStreamWriter outputStream = new OutputStreamWriter(new FileOutputStream(outputFileName));
                System.out.println("Input matrix will be obtained from " + inputFileName);
                Transposer.transpose(inputStream, outputStream);
                System.out.println("Output result was successfully written to " + outputFileName);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
