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
        System.out.printf("Error in Line: %s  In Token: %d - Expected: %s but got: %s\n",
                numLine, numToken, expected, current.getType());
    }
}
