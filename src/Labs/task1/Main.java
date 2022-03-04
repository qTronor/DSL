package Labs.task1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();

        new Lexer(string);
        for(Token t: Lexer.Tokens){
            System.out.println(t.type + " " + t.text);
        }
    }
}
