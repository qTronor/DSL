package Labs.task1;

public class ParserException extends Exception{
    public Token current;
    public String expected;
    public int numLine;
    public int numToken;

    public ParserException(int numLine, int numToken, Token current, String expected) {
        this.numLine = numLine;
        this.numToken = numToken;
        this.current = current;
        this.expected = expected;
    }

    public void getInfo(int numLine, int numToken, Token current, String expected) {
        System.out.printf("Line: %s Token: %d - Expected: %s but received: %s\n",
                numLine, numToken, expected, current.getType());
    }
}
