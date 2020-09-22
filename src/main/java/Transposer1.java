/*import java.io.*;

public class Transposer1 {
    //public Transposer(){
        //private final String
        public Transposer1(String charsetInput, String charsetOutput) {
            this.charsetInput = charsetInput;
            this.charsetOutput = charsetOutput;
        }
    public int transpose(InputStream in, OutputStream out) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(in, charsetInput)) {
            try (OutputStreamWriter writer = new OutputStreamWriter(out, charsetOutput)) {
                File myFile =new File("c:\\fileWith11Line.txt");
                FileReader fileReader = new FileReader(myFile);
                LineNumberReader lineNumberReader = new LineNumberReader(fileReader);

                int lineNumber = 0;

                int sym = reader.read();
                int count = 0;
                while (sym != -1) {
                    writer.write(sym);
                    count++;
                    sym = reader.read();
                }
                return count;
            }
        }
    }

    //(wordSymbolLimit, cropTheWord, rightSideAlignment, outputFileName, outputFileName, inputFileName, arguments
    public void transpose(String inputFileName, String outputFileName) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(inputFileName)) {
            try (FileOutputStream outputStream = new FileOutputStream(outputFileName)) {
                transpose(inputStream, outputStream);
            }
        }
    }
}*/
