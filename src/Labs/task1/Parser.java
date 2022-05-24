package Labs.task1;

import java.util.*;

public class Parser {

    private final ArrayList<Token> tokens;
    private final int len;

    private int iterator;
    private int curLine;
    private Token curToken;

    public boolean correctCode;

    public Parser(ArrayList<Token> tokens, int len) {
        this.tokens = tokens;
        this.len = len;
        curLine = 0;
        iterator = 0;
        curToken = tokens.get(iterator);
        correctCode = true;
    }
    public void lang() throws ParserException {
        for (int i = 0; i < len; i++) {
            curLine++;
            expr();
        }
    }
    public void expr() {
        body();
        checkError("ENDL");
    }

    public void body() {
        switch (curToken.getType()) {
            case "VAR" -> {
                if (Objects.equals(tokens.get(iterator + 1).getType(), "POINT")) { // Работа со списком
                    list_op();
                } else {
                    expr_assign();  // Присваивание
                }
            }
            case "LIST" -> list_initialize(); // Снициализация списка
            case "IF" -> if_op();
            case "WHILE" -> while_op();
            case "DO" -> do_while_op();
            case "FOR" -> for_op();
            case "PRINT" -> print();
            default -> checkError("VAR");
        }
    }

    /*------------------------------------ERRORS------------------------------------*/
    public void callError(String tokenType) throws ParserException {
        if (!Objects.equals(curToken.getType(), tokenType)) {
            correctCode = false;
            throw new ParserException(curLine, iterator, curToken, tokenType);
        }
    }

    public void checkError(String tokenType) {
        try {
            callError(tokenType);
        } catch (ParserException e) {
            e.getInfo(curLine, iterator, e.current, e.expected);
            curToken = tokens.get(--iterator);
        }
        curToken = tokens.get(++iterator);
    }
    /*------------------------------------------------------------------------------*/

    public boolean smthInBody() {
        return switch (curToken.getType()) {
            case "VAR", "IF", "FOR", "WHILE", "DO", "PRINT" -> true;
            default -> false;
        };
    }

    public boolean smthInBody_DO_WHILE() {
        return switch (curToken.getType()) {
            case "VAR", "IF", "FOR", "DO", "PRINT" -> true;
            default -> false;
        };
    }

    public void expr_value() {
        switch (curToken.getType()) {
            case "VAR", "DIGIT" -> value();
            case "L_BC" -> infinityBR();
            default -> checkError("VAR");
        }
        while ("OP".equals(curToken.getType())) {
            checkError("OP");
            value();
        }
    }

    public void value() {
        switch (curToken.getType()) {
            case "DIGIT" -> checkError("DIGIT");
            case "L_BC" -> infinityBR();
            default -> checkError("VAR");
        }
    }

    public void infinityBR() { // Infinity brackets
        checkError("L_BC");
        expr_value();
        checkError("R_BC");
    }
    /*----------------------------------CONDITIONS---------------------------------*/

    public void condition() {
        checkError("VAR");
        checkError("COMPARE_OP");
        expr_value();
    }

    public void condition_in_br() {
        checkError("L_BC");
        condition();
        checkError("R_BC");
    }
    /*------------------------------------------------------------------------------*/


    /*-----------------------------OPERATIONS---------------------------------------*/
    public void if_op() {
        checkError("IF");
        condition_in_br();
        do {
            body();
        } while (smthInBody());
        if ("ELSE".equals(curToken.getType())) {
            else_op();
        }
    }

    public void else_op() {
        checkError("ELSE");
        do {
            expr();
        } while (smthInBody());
    }

    public void while_op() {
        checkError("WHILE");
        condition_in_br();
        do {
            body();
        } while (smthInBody());
    }

    public void do_while_op() {
        checkError("DO");
        do {
            body();
        } while (smthInBody_DO_WHILE());
        checkError("WHILE");
        condition_in_br();
    }

    public void for_op() {
        checkError("FOR");
        checkError("L_BC");
        assign();
        checkError("DIV");
        condition();
        checkError("DIV");
        assign();
        checkError("R_BC");
        do {
            body();
        } while (smthInBody());
    }

    public void assign() {
        checkError("VAR");
        checkError("ASSIGN_OP");
        expr_value();
    }
    public void expr_assign() {
        assign();
        while ("DIV".equals(curToken.getType())) {
            checkError("DIV");
            assign();
        }
    }

    public void print() {
        checkError("PRINT");
        if ("L_BC".equals(curToken.getType())) {
            checkError("L_BC");
            if ("DIGIT".equals(curToken.getType())) {
                checkError("DIGIT");
            } else {
                checkError("VAR");
            }
            checkError("R_BC");
        }
    }

    public void list_initialize() {
        checkError("LIST");
        checkError("VAR");
    }

    public void list_op() {
        checkError("VAR");
        checkError("POINT");
        switch (curToken.getType()) {
            case "REMOVE" -> checkError("REMOVE");
            case "CLEAR" -> checkError("CLEAR");
            case "SIZE" -> checkError("SIZE");
            case "GET" -> checkError("GET");
            case "ISEMPTY" -> checkError("ISEMPTY");
            case "CONTAINS" -> checkError("CONTAINS");
            default -> checkError("ADD");
        }
        checkError("L_BC");
        if ("DIGIT".equals(curToken.getType())) {
            checkError("DIGIT");
        }
        checkError("R_BC");
    }
    /*------------------------------------------------------------------------------*/

}