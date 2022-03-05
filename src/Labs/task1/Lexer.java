package Labs.task1;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    String string;
    int pos = 0;

    static LinkedList<Token> Tokens = new LinkedList<>();

    public Lexer(String string) {
        this.string = string;
        MakeTokens(string);
    }

    public boolean MakeTokens(String string) {

        TokenType tokenType = new TokenType();

        char[] charsInString = string.toCharArray();

        String word = ""; //sum of letters/numbers
        for (int i = 0; i < string.length(); i++) {
            String lookFrwd = " "; //Looking i + 1
            word += charsInString[i];

            if (i < string.length() - 1) {    //Out of bounds
                lookFrwd = word + charsInString[i + 1];
            }

            for (String key : tokenType.lexemes.keySet()) {

                for (String key1 : tokenType.lexemes_key_words.keySet()) {
                    Pattern pattern1 = Pattern.compile(tokenType.lexemes_key_words.get(key1));

                    Matcher match_word1 = pattern1.matcher(word);
                    Matcher match_OutOfBounds1 = pattern1.matcher(lookFrwd);

                    if (match_word1.matches() && !match_OutOfBounds1.matches()) {
                        Tokens.add(new Token(key1, word));
                        word = "";
                        //continue;
                    }
                }
                Pattern pattern = Pattern.compile(tokenType.lexemes.get(key));

                Matcher match_word = pattern.matcher(word);
                Matcher match_OutOfBounds = pattern.matcher(lookFrwd);

                if (match_word.matches() && !match_OutOfBounds.matches()) { //If we found needed word/numbers and i + 1 != same type
                    Tokens.add(new Token(key, word));
                    word = ""; //Here we go again
                }
            }
        }
        return true;
    }
}