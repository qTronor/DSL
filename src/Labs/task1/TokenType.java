package Labs.task1;

import java.util.HashMap;
import java.util.Map;

public class TokenType {

    static Map<String, String> lexemes = new HashMap<>();
    static Map<String, String> lexemes_key_words = new HashMap<>();

    public TokenType() {
        lexemes.put("VAR", "[a-z][a-z0-9]*");
        lexemes.put("STRING", "\"[0-9a-zA-Z*\\/&s ]*\"|str(\"[0-9a-zA-Z*\\/&s ]*\")");
        lexemes.put("INT", "int([1-9][0-9]*)|[1-9][0-9]*|0");
        lexemes.put("L_BRACKET", "\\(");
        lexemes.put("R_BRACKET", "\\)");
        lexemes.put("L_BRACE", "\\{");
        lexemes.put("R_BRACE", "}");
        lexemes.put("R_SQUARE_BRACKET", "\\]");
        lexemes.put("L_SQUARE_BRACKET", "\\[");
        lexemes.put("ASSIGN_OP", "=");
        lexemes.put("END_LINE", ";");
        lexemes.put("SPACE", " ");


        lexemes_key_words.put("IF_KW", "if");
        lexemes_key_words.put("FOR_KW", "for");
        lexemes_key_words.put("WHILE_KW", "while");


    }
}


