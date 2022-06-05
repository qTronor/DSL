package Labs.task1;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    static String getCode() throws GrammarException {
        String src = "";
        try {
            File file = new File("C:/Users/Home/IdeaProjects/Labs_for_DoDSPL/res/EX.txt"); //создание объекта с файлом

            if (!file.exists()) file.createNewFile(); //создание файла, если его нет

            BufferedReader bf = new BufferedReader(new FileReader(file)); //открытие потока BufferReader, с помощью которого будет считываться файл
            String buffer = "";

            while ((buffer = bf.readLine()) != null) { //считывание одной строки с файла и занесение её в буфер
                src = src.concat(buffer); //склеивание основной строки с буфером
            }

            bf.close();
        } catch (IOException e) { //ловля исключений на открытие и закрытие потока
            System.out.println("File creating or opening error! " + e);
        }

        if (src.equals(";") || src.equals("") || src.equals(" ")) { //проверка корректности строки
            throw new GrammarException(); //создание исключения об ошибке синтаксиса
        }
        return src;
    }


    public static void main(String[] args) throws GrammarException {
        String string = getCode();
        System.out.println("\n\nИсходный код программы:");
        System.out.println(string);
        System.out.println("\nЗначения лексера:");
        Lexer lexer = new Lexer(string);
        lexer.printTokens();
        System.out.println("");

        Parser p = new Parser(lexer.getTokens(), string.length());
        try {
            p.lang();
        } catch (ParserException | IndexOutOfBoundsException ignored) {}

        if (p.correctCode) {
            Interpreter interpreter = new Interpreter(lexer.getTokens());
            System.out.println(interpreter);
        }
    }
}
class GrammarException extends Exception {
}
