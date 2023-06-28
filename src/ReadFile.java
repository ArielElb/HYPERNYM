import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * read a file.
 */
public class ReadFile {
    private final Map<String, Map<String, Integer>> baseMap;
    private final String filePath;

    /**
     * constructor.
     * @param filePath - the absolut file path.
     * @param baseMap - the base treemap.
     */
    public ReadFile(String filePath, Map<String, Map<String, Integer>> baseMap) {
        this.filePath = filePath;
        this.baseMap = baseMap;
    }


    /**
     * read from the file line by line.
     * @throws IOException throw expedition.
     */
    public void readerBuffer() throws IOException {
        BufferedReader is = null;
        try {
            // wrapper that reads ahead
            is = new BufferedReader(
                    // wrapper that reads characters
                    new InputStreamReader(
                            new FileInputStream(this.filePath)));

            String line;
            while ((line = is.readLine()) != null) { // ’null ’->no more data in the stream
                AddToDataBase toDataBase = new AddToDataBase(line, this.baseMap);
                line = line.toLowerCase();
                String stringRegex;
                if (line.contains("including")) {
                    stringRegex = RegexPatterns.INCLUDING;
                    toDataBase.runRegex(stringRegex);
                }
                if (line.contains("especially")) {
                    stringRegex = RegexPatterns.ESPECIALLY;
                    toDataBase.runRegex(stringRegex);
                }
                if (line.contains("which")) {
                    stringRegex = RegexPatterns.WHICH;
                    toDataBase.runRegex(stringRegex);
                }
                if (line.contains("such as")) {
                    stringRegex = RegexPatterns.SUCHAS;
                    toDataBase.runRegex(stringRegex);
                }
                if (line.contains("such")) {
                    stringRegex = RegexPatterns.SUCH_N_PAS;
                    toDataBase.runRegex(stringRegex);
                }

//                toDataBase.runRegex(regex.FINAL);


            }

        } catch (IOException e) {
            System.out.println(" Something went wrong while reading !");
        } finally {
            if (is != null) { // Exception might have happened at constructor
                try {
                    is.close(); // closes FileInputStream too
                } catch (IOException e) {
                    System.out.println(" Failed closing the file !");
                }
            }
        }
    }
}
