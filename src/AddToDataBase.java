import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * add the hypernym and the hypernyms.
 */
public class AddToDataBase {
    private final String line;
    private Map<String, Map<String, Integer>> baseMap;

    /**
     * constructor.
     *
     * @param line    - line of the file.
     * @param baseMap - baseMap.
     */
    public AddToDataBase(String line, Map<String, Map<String, Integer>> baseMap) {
        this.line = line;
        this.baseMap = baseMap;
    }

    /**
     * run the regex.
     *
     * @param regexString the regex.
     */
    public void runRegex(String regexString) {
        String text = this.line;

        Pattern pattern;
        pattern = Pattern.compile(regexString.replace("NP", RegexPatterns.NP));
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String lineAfterRegex = text.substring(matcher.start(), matcher.end());
            runInsert(lineAfterRegex);
        }
    }

    /**
     * insert all hypernyms and hypos.
     *
     * @param lineAfterRegex - patterns.
     */
    public void runInsert(String lineAfterRegex) {
        HearstParser parser = new HearstParser(lineAfterRegex);
        List<String> listOfHypo = parser.getHyponyms();
        String hypernym = parser.getHypernym();
        for (String hyponym : listOfHypo) {
            insertToBase(hypernym, hyponym);
        }
    }

    /**
     * insert the hypernym and the hyponyms to the map.
     *
     * @param hypernym - the hypernym.
     * @param hyponym  - hyponyms
     */
    public void insertToBase(String hypernym, String hyponym) {
        //check if the hypernym inside the map.
        if (this.baseMap.containsKey(hypernym)) {
            //check if there is a map of this hyponym
            if (this.baseMap.get(hypernym).containsKey(hyponym)) {
                this.baseMap.get(hypernym).put(hyponym, this.baseMap.get(hypernym).get(hyponym) + 1);
            } else {
                //first time hyponym is in the existing map.
                this.baseMap.get(hypernym).put(hyponym, 1);
            }
        } else {
            TreeMap<String, Integer> newMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            newMap.put(hyponym, 1);
            this.baseMap.put(hypernym, newMap);
        }
    }
}
