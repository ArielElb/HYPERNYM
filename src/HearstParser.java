import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * get a line and with different methods return the hypernym and the hypos.
 */
public class HearstParser {
    private final String line;

    /**
     * Constructor.
     *
     * @param line - line of the file.
     */
    public HearstParser(String line) {
        this.line = line;
    }

    /**
     * extract the nps from a given line.
     * @return list of nps.
     */
    public List<String> getNPS() {
        Pattern patternString = Pattern.compile(RegexPatterns.NP);
        Matcher matcher = patternString.matcher(this.line);
        List<String> nsList = new ArrayList<>();
        while (matcher.find()) {
            String np = this.line.substring(matcher.start(), matcher.end());
            np = np.replace("<np>", "");
            np = np.replace("</np>", "");
            nsList.add(np);
        }
        return nsList;
    }

    /**
     * extract the hypernym from a given line.
     * @return String - Hypernym.
     */
    public String getHypernym() {
        List<String> nPsList = getNPS();
        if (this.line.contains("which is")) {
            return nPsList.get(nPsList.size() - 1);
        }
        return nPsList.get(0);
    }

    /**
     * extract the hypos from a given line.
     * @return list of hypos.
     */
    public List<String> getHyponyms() {
        List<String> nPsList = getNPS();
        String hypernym = getHypernym();
        nPsList.remove(hypernym);
        return nPsList;
    }

}
