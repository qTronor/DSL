package Labs.task1;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    static Map<String, Pattern> lexemes = new HashMap<>();
    static Map<String, Pattern> KW_lexemes = new HashMap<>();
    static ArrayList<Token> tokens = new ArrayList<>();
    static {
        /*lexemes.put("VAR", Pattern.compile("(^[a-zA-Z\\_][0-9a-zA-Z\\_]{0,31}$)"));
        //  lexemes.put("SPACE", "\\ $");
        lexemes.put("END_LINE", Pattern.compile("^;$"));
        lexemes.put("FUNC", Pattern.compile("(^[a-z\\_][0-9a-zA-Z\\_]{0,31}\\(.*\\)$)"));
        lexemes.put("INT", Pattern.compile("^0|([1-9][0-9]*)$"));
        lexemes.put("STRING", Pattern.compile("^\"[\\da-zA-Z*\\/&s ]*\"|str(\"[0-9a-zA-Z*\\/&s ]*\")$"));
        lexemes.put("OPERATOR", Pattern.compile("^([%+*\\-\\//]|(\\**)|(\\++))$"));
        lexemes.put("L_BRACKET", Pattern.compile("^\\($"));
        lexemes.put("R_BRACE", Pattern.compile("^^\\}$"));
        lexemes.put("R_BRACKET", Pattern.compile("^\\)$"));
        lexemes.put("L_BRACE", Pattern.compile("^\\{$"));
        lexemes.put("R_SQUARE_BRACKET", Pattern.compile("^\\]$"));
        lexemes.put("L_SQUARE_BRACKET",Pattern.compile("^\\[$"));
        lexemes.put("ASSIGN_OP", Pattern.compile("^\\=$"));
        lexemes.put("COMMA", Pattern.compile(",{1}$"));
        lexemes.put("R_SQUARE_BRACKET", Pattern.compile("^\\]$"));
        lexemes.put("L_SQUARE_BRACKET", Pattern.compile("^\\[$"));
        lexemes.put("ASSIGN_OP", Pattern.compile("^\\=$"));
        lexemes.put("COMPARISON", Pattern.compile("(^[<>!]|(>=)|(<=)|(==)|(\\!=))$"));
        lexemes.put("SINGLE_QUOTE", Pattern.compile("^'$"));
        lexemes.put("COMMENTS_START", Pattern.compile("^(\\/\\*)$"));
        lexemes.put("COMMENTS_END", Pattern.compile("^((\\*\\/))$"));
        //lexemes.put("OR", Pattern.compile("^\\|\\|$"));
        //lexemes.put("AND", Pattern.compile("^&&$"));

        KW_lexemes.put("WHILE_KW", Pattern.compile("^while$"));
        KW_lexemes.put("FOR_KW", Pattern.compile("^for$"));
        KW_lexemes.put("IF_KW", Pattern.compile("^if$"));
        KW_lexemes.put("ELSE_KW", Pattern.compile("^else$"));
        KW_lexemes.put("BOOL_KW", Pattern.compile("^true|false$"));
        KW_lexemes.put("NOT_KW",Pattern.compile("^\\!|not$"));
        KW_lexemes.put("DO_KW",Pattern.compile("^do$"));
        KW_lexemes.put("RETURN_KW",Pattern.compile("^return$"));
        KW_lexemes.put("PRINT_KW",Pattern.compile("^print$"));*/

        lexemes.put("VAR", Pattern.compile("(^[a-zA-Z\\_][0-9a-zA-Z\\_]{0,31}$)"));
        //  lexemes.put("SPACE", "\\ $");
        lexemes.put("ENDL", Pattern.compile("^;$"));
        lexemes.put("FUNC", Pattern.compile("(^[a-z\\_][0-9a-zA-Z\\_]{0,31}\\(.*\\)$)"));
        lexemes.put("DIGIT", Pattern.compile("^0|([1-9][0-9]*)$"));
        lexemes.put("STRING", Pattern.compile("^\"[\\da-zA-Z*\\/&s ]*\"|str(\"[0-9a-zA-Z*\\/&s ]*\")$"));
        lexemes.put("OP", Pattern.compile("^([%+*\\-\\//]|(\\**)|(\\++))$"));
        lexemes.put("L_BC", Pattern.compile("^\\($"));
        lexemes.put("R_BRACE", Pattern.compile("^^\\}$"));
        lexemes.put("R_BC", Pattern.compile("^\\)$"));
        lexemes.put("R_BRACE", Pattern.compile("^\\{$"));
        lexemes.put("R_SQUARE_BRACKET", Pattern.compile("^\\]$"));
        lexemes.put("L_SQUARE_BRACKET",Pattern.compile("^\\[$"));
        lexemes.put("ASSIGN_OP", Pattern.compile("^\\=$"));
        lexemes.put("DIV", Pattern.compile(",{1}$"));
        lexemes.put("R_SQUARE_BRACKET", Pattern.compile("^\\]$"));
        lexemes.put("L_SQUARE_BRACKET", Pattern.compile("^\\[$"));
        lexemes.put("ASSIGN_OP", Pattern.compile("^\\=$"));
        lexemes.put("COMPARE_OP", Pattern.compile("(^[<>!]|(>=)|(<=)|(==)|!=)$"));
        lexemes.put("SINGLE_QUOTE", Pattern.compile("^'$"));
        lexemes.put("COMMENTS_START", Pattern.compile("^(\\/\\*)$"));
        lexemes.put("COMMENTS_END", Pattern.compile("^((\\*\\/))$"));
        //lexemes.put("OR", Pattern.compile("^\\|\\|$"));
        //lexemes.put("AND", Pattern.compile("^&&$"));

        KW_lexemes.put("WHILE", Pattern.compile("^WHILE$"));
        KW_lexemes.put("FOR", Pattern.compile("^FOR$"));
        KW_lexemes.put("IF", Pattern.compile("^IF$"));
        KW_lexemes.put("ELSE", Pattern.compile("^ELSE$"));
        KW_lexemes.put("BOOL", Pattern.compile("^true|false$"));
        KW_lexemes.put("NOT",Pattern.compile("^\\!|not$"));
        KW_lexemes.put("DO",Pattern.compile("^DO$"));
        KW_lexemes.put("RETURN",Pattern.compile("^return$"));
        KW_lexemes.put("PRINT",Pattern.compile("^PRINT$"));
        KW_lexemes.put("LIST", Pattern.compile("^LIST$"));
        KW_lexemes.put("ADD", Pattern.compile("^ADD$"));
        KW_lexemes.put("REMOVE", Pattern.compile("^remove$"));
        KW_lexemes.put("CLEAR", Pattern.compile("^clear$"));
        KW_lexemes.put("SIZE", Pattern.compile("^size$"));
        KW_lexemes.put("GET", Pattern.compile("^get$"));
        KW_lexemes.put("ISEMPTY", Pattern.compile("^isEmpty$"));
        KW_lexemes.put("CONTAINS", Pattern.compile("^contains$"));
        KW_lexemes.put("POINT", Pattern.compile("^\\.$"));
    }
    void lexer(String src) {
        StringBuffer buffer = new StringBuffer("");
        String type = "";
        boolean itIsCommon;
        char[] chArray = src.toCharArray();
        int i = 0;
        int length = chArray.length;
        Matcher m;

        while (i < length) { //Проходимся по всем символам в строке src
            if (i == length - 1|| src.charAt(i) == '\t') break; //Выход из программы

            buffer.append(src.charAt(i));
            if (src.charAt(i) == ' '){
                buffer.reverse();
                buffer.deleteCharAt(0);
                buffer.reverse();

                i++;
                continue;
            }


            itIsCommon = false;

            for (String lexemeName : lexemes.keySet()) {
                Pattern p = lexemes.get(lexemeName);
                Pattern p_sec = lexemes.get("VAR");
                m = p_sec.matcher(buffer);

                if (m.matches()) {
                    boolean itIsKW = false;

                    for (String KW_lexeme : KW_lexemes.keySet()) {
                        Pattern p_var = KW_lexemes.get(KW_lexeme);
                        m = p_var.matcher(buffer);

                        if (m.matches()) {
                            itIsCommon = true;
                            itIsKW = true;
                            type = KW_lexeme;
                        }
                    }

                    if (!itIsKW) {
                        itIsCommon = true;
                        type = "VAR";
                    }
                    break;
                }

                m = p.matcher(buffer);

                if (m.matches()) {
                    itIsCommon = true;
                    type = lexemeName;
                }
            }

            if (!itIsCommon) {
                buffer.reverse();
                buffer.deleteCharAt(0);
                buffer.reverse();
                tokens.add(new Token(type, buffer.toString()));
                buffer.setLength(0);
                continue;
            }

            if (src.charAt(i + 1) == ';') { //условие конечной лексемы в строке
                tokens.add(new Token(type, buffer.toString()));
                buffer.setLength(0);
                i++;
                continue;
            }
            i++;
        }
    }
    void printTokens(){
        int i = 0; for (Token t : tokens) { System.out.print(t); System.out.println("Token #" + i); i++; }
    }
    ArrayList getTokens(){ return tokens; }
}
