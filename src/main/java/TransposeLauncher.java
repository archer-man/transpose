import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;

public class TransposeLauncher {

    @Option(name = "-a", metaVar = "num", usage = "Each word's symbol limitation")
    private int wordSymbolLimit;

    @Option(name = "-t", usage = "Crop the word to the desired size")
    private boolean cropTheWord = false;

    @Option(name = "-r", usage = "Right-side alignment")
    private boolean rightSideAlignment = false;

    @Option(name = "-o", metaVar = "ofile", usage = "Output file name")
    private String outputFileName;

    @Argument(metaVar = "file", usage = "Input file name")
    private String inputFileName;

    public static void main(String[] args) {
        new TransposeLauncher().doMain(args);
    }

    public void doMain(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
            if (wordSymbolLimit == 0 && (cropTheWord || rightSideAlignment)) {
                wordSymbolLimit = 10;
            }
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar transpose [-a num] [-t] [-r] [-o ofile] [file]");
            parser.printUsage(System.err);
            return;
        }

        Transposer transposer = new Transposer(wordSymbolLimit, cropTheWord, rightSideAlignment);

        try {
            InputStreamReader inputStream = new InputStreamReader(System.in);
            OutputStreamWriter outputStream = new OutputStreamWriter(System.out);
            if (inputFileName != null) {
                inputStream = new InputStreamReader(new FileInputStream(inputFileName));
                System.out.println("Input matrix will be obtained from " + inputFileName);
            } else {
                System.out.println("Input file was not specified. Please, enter the matrix separating the individual elements with space(-s):");
            }
            if (outputFileName != null) {
                outputStream = new OutputStreamWriter(new FileOutputStream(outputFileName));
                System.out.println("Output result was successfully written to " + outputFileName + "\n");
            }
            transposer.transpose(inputStream, outputStream);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
