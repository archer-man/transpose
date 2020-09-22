import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

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

    /*@Argument
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
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("transpose [-a num] [-t] [-r] [-o ofile] [file]");
            parser.printUsage(System.err);
            return;
        }

        Transposer transposer = new Transposer(wordSymbolLimit, cropTheWord, rightSideAlignment /*arguments*/);

        try {
            System.out.println(Transposer.transpose(inputFileName, outputFileName));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
