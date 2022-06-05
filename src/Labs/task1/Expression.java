package Labs.task1;

public class Expression {
    private final StringBuffer value = new StringBuffer("");

    public Expression(StringBuffer value) {
        this.value.append(value);
    }

    @Override
    public String toString() {return "Expression: " + value;}
}
