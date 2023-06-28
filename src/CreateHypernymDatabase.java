import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Comparator;

/**
 * Create the database.
 */
public class CreateHypernymDatabase {
    private Map<String, Map<String, Integer>> baseMap;

    /**
     * Create the base.
     */
    public CreateHypernymDatabase() {
        this.baseMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    /**
     * main .
     * @param args - path and lemma.
     * @throws IOException exp.
     */
    public static void main(String[] args) throws IOException {
        CreateHypernymDatabase database = new CreateHypernymDatabase();
        String filePath = args[0];
        String writePath = args[1];
        File file = new File(filePath);

        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (final File fileEntry : files) {
            ReadFile reader = new ReadFile(fileEntry.getAbsolutePath(), database.baseMap);
            reader.readerBuffer();
        }
        database.writeToFile(writePath);
    }

    /**
     * toString.
     * @param hypernym - convert hypernym.
     * @return list of hypernyms.
     */
    public String toStringHypo(String hypernym) {

        List<String> listOfHypo = new ArrayList<>();
        for (Map.Entry<String, Integer> hypo : hypoSortedByVal(this.baseMap.get(hypernym))) {
            listOfHypo.add(hypo.getKey() + " (" + hypo.getValue().toString() + ")");
        }
        return String.join(", ", listOfHypo);
    }

    /**
     * Anonymous class to sort the big map using comparator.
     *
     * @param map - map
     * @param <K> - key of the map
     * @param <V> - val of the key.
     * @return return set.
     */
    static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> hypoSortedByVal(Map<K, V> map) {

        SortedSet<Map.Entry<K, V>> sortedEntry = new TreeSet<Map.Entry<K, V>>(
                new Comparator<Map.Entry<K, V>>() {
                    @Override
                    public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                        int val = o2.getValue().compareTo(o1.getValue());
                        if (val != 0) {
                            return val;
                        }
                        return 1;
                    }
                }
        );
        sortedEntry.addAll(map.entrySet());
        return sortedEntry;
    }

    private void writeToFile(String writePath) throws IOException {
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(writePath + "/" + "Ariel" + ".txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (String hyper : this.baseMap.keySet()) {
            if (this.baseMap.get(hyper).size() < 3) {
                continue;
            }
            String str = (hyper) + ": " + toStringHypo(hyper);
            assert file != null;
            file.write(str.getBytes());
            file.write(10);
        }
        try {
            file.close();
        } catch (IOException e) {
            System.out.println();
        }


    }

    /**
     * find the lemma.
     * @param lemma .
     */
    public void findLemma(String lemma) {

        Map<String, Integer> mapOfLemma = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        //check if lemma appears in the big map
        for (String hyper : this.baseMap.keySet()) {
            for (String insideKey : this.baseMap.get(hyper).keySet()) {
                if (this.baseMap.get(hyper).containsKey(lemma)) {
                    int lemmaCounter = this.baseMap.get(hyper).get(lemma);
                    mapOfLemma.put(hyper, lemmaCounter);
                }
            }
        }
        // 2.1 Descending-order sorting using Map.Entry.comparingByValue()
        mapOfLemma.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.println(
                        entry.getKey() + ": " + "(" + entry.getValue() + ")"));
    }

    /**
     * getter.
     * @return this base map.
     */
    public Map<String, Map<String, Integer>> getBaseMap() {
        return baseMap;
    }
}
