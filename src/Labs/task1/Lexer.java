package Labs.task1;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

    private final String codeFile;
    private final ArrayList<Token> tokens = new ArrayList<>();

    TokenType tokenType = new TokenType();

    public Lexer(String codeFile) {
        this.codeFile = codeFile;
        run();
    }

    private void run() {
        String tokenStart = "";
        for (int i = 0; i < codeFile.length(); i++) {

            if (codeFile.toCharArray()[i] == ' ') {
                continue;
            }

            tokenStart += codeFile.toCharArray()[i];
            String tokenEnd = " ";

            if (i < codeFile.length() - 1) {
                tokenEnd = tokenStart + codeFile.toCharArray()[i + 1];
            }

            for (String key: tokenType.lexemes.keySet()) {
                Pattern p = tokenType.lexemes.get(key);
                Matcher m_1 = p.matcher(tokenStart);
                Matcher m_2 = p.matcher(tokenEnd);

                if (m_1.find() && !m_2.find()) {
                    tokens.add(new Token(key, tokenStart));
                    tokenStart = "";
                    break;
                }
            }
        }
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }
    void printTokens(){
        int i = 0; for (Token t : tokens) { System.out.print(t); System.out.println("Token #" + i); i++; }
    }
    @Override
    public String toString() {
        return "Lexer" + tokens;
    }
}
