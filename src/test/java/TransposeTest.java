import org.junit.jupiter.api.Test;

import java.io.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;

class TransposeTest {

    private void assertFileContent(String inputFile, String expectedFile) {
        try {
            FileInputStream inp = new FileInputStream(inputFile);
            FileInputStream exp = new FileInputStream(expectedFile);
            BufferedReader actual = new BufferedReader(new InputStreamReader(inp));
            BufferedReader expected = new BufferedReader(new InputStreamReader(exp));
            String line;
            while ((line = expected.readLine()) != null) {
                assertEquals(line, actual.readLine());
            }
            actual.close();
            expected.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void main() {
        TransposeLauncher.main(("-a 5 -t -o output/1.txt input/1.txt").split(" "));
        assertFileContent("output/1.txt", "output/reference/true1.txt");
        TransposeLauncher.main(("input/2.txt -o output/2.txt").split(" "));
        assertFileContent("output/2.txt", "output/reference/true2.txt");
        TransposeLauncher.main(("-t -r input/3.txt -o output/3.txt").split(" "));
        assertFileContent("output/3.txt", "output/reference/true3.txt");
        TransposeLauncher.main(("-t -r input/4.txt -o output/4.txt").split(" "));
        assertFileContent("output/4.txt", "output/reference/true4.txt");
    }
}