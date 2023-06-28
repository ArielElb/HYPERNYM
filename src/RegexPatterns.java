/**
 * regex const's class.
 */
public class RegexPatterns {
    static final String NP = "<np>([^<]+)<\\/np>";

    static final String SUCH_N_PAS = "such NP as NP( , NP)*( (, )?(and|or) NP)?";

    static final String INCLUDING = "NP (, )?including NP( (, )?NP)*( (, )?(and|or) NP)?";

    static final String ESPECIALLY = "NP (, )?especially NP( (, )?NP)*( (, )?(and|or) NP)?";

    static final String SUCHAS = "NP (, )?such as NP( (, )?NP)*( (, )?(and|or) NP)?";

    static final String WHICH = "NP (, )?which is ((an example|a kind|a class) of )?NP";



}
