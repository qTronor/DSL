package Labs.task1;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class TokenType {
    static Map<String, Pattern> lexemes = new HashMap<>();
    public TokenType(){
        lexemes.put("VAR", Pattern.compile("^[a-z_]\\w*$"));
        lexemes.put("INT", Pattern.compile("^\\d*$"));
        lexemes.put("ASSIGN_OP", Pattern.compile("^=$"));
        lexemes.put("OP", Pattern.compile("^(-|\\+|\\*|/)$"));
        lexemes.put("L_BC", Pattern.compile("^\\($"));
        lexemes.put("R_BC", Pattern.compile("^\\)$"));
        lexemes.put("ENDL", Pattern.compile("^;$"));
        lexemes.put("COMPARE_OP", Pattern.compile("^(~|<|>|!=)$"));
        lexemes.put("IF", Pattern.compile("^IF$"));
        lexemes.put("ELSE", Pattern.compile("^ELSE$"));
        lexemes.put("WHILE", Pattern.compile("^WHILE$"));
        lexemes.put("DO", Pattern.compile("^DO$"));
        lexemes.put("FOR", Pattern.compile("^FOR$"));
        lexemes.put("COMMA", Pattern.compile("^,$"));
        lexemes.put("PRINT", Pattern.compile("^print$"));
        lexemes.put("LIST", Pattern.compile("^LIST$"));
        lexemes.put("ADD", Pattern.compile("^add$"));
        lexemes.put("REMOVE", Pattern.compile("^remove$"));
        lexemes.put("CLEAR", Pattern.compile("^CLEAR$"));
        lexemes.put("GET", Pattern.compile("^GET$"));
        lexemes.put("ISEMPTY", Pattern.compile("^ISEMPTY$"));
        lexemes.put("DOT", Pattern.compile("^\\.$"));
    }
}
