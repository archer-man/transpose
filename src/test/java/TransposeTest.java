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
    void main() throws FileNotFoundException {
        TransposeLauncher.main(("-a 5 -t -o output/1.txt input/1.txt").split(" "));
        assertFileContent("output/1.txt", "output/reference/true1.txt");
        TransposeLauncher.main(("input/2.txt -o output/2.txt").split(" "));
        assertFileContent("output/2.txt", "output/reference/true2.txt");
        TransposeLauncher.main(("-t -r input/3.txt -o output/3.txt").split(" "));
        assertFileContent("output/3.txt", "output/reference/true3.txt");
        TransposeLauncher.main(("-t -r input/4.txt -o output/4.txt").split(" "));
        assertFileContent("output/4.txt", "output/reference/true4.txt");
        TransposeLauncher.main(("input/5.txt -o output/5.txt").split(" "));
        assertFileContent("output/5.txt", "output/reference/true5.txt");
        TransposeLauncher.main(("-r input/6.txt -o output/6.txt").split(" "));
        assertFileContent("output/6.txt", "output/reference/true6.txt");
        TransposeLauncher.main(("input/7.txt -o output/7.txt").split(" "));
        assertFileContent("output/7.txt", "output/reference/true7.txt");
        TransposeLauncher.main(("-t -r -a 2 input/8.txt -o output/8.txt").split(" "));
        assertFileContent("output/8.txt", "output/reference/true8.txt");
        for (int i = 1; i < 9; i++) {
            new PrintWriter("output/" + i + ".txt").close();
        }
    }
}