package Labs.task1;

public class Token {
    String type;
    String  value;

    public Token(String type, String value){
        this.type = type;
        this.value = value;
    }

    public String getType() { return type; }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Token " + this.type + " : " + this.value + " ";
    }
}
