import java.io.File;
import java.io.IOException;

/**
 * DiscoverHypernym.
 */
public class DiscoverHypernym {

    /**
     * @param args - first argument - file path. second argument - lemma.
     * @throws IOException exp.
     */
    public static void main(String[] args) throws IOException {
        CreateHypernymDatabase base = new CreateHypernymDatabase();
        String filePath = args[0];
        String lemma = args[1];
        File file = new File(filePath);

        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (final File fileEntry : files) {
            ReadFile reader = new ReadFile(fileEntry.getAbsolutePath(), base.getBaseMap());
            reader.readerBuffer();
        }
        base.findLemma(lemma);
    }
}
