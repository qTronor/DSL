package Labs.task1;

import java.util.*;

public class Interpreter {

    private final ArrayList<Token> infixExpr;
    private final Map<String, Object> variables = new HashMap<>();

    private int iterator;
    private Token currentToken;
    private boolean transCondition;

    private int operationPriority(Token op) {
        return switch (op.getToken()) {
            case "(" -> 0;
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            default -> throw new IllegalArgumentException("Illegal value: " + op.getToken());
        };
    }

    private double execute(Token op, double first, double second) {
        return switch (op.getToken()) {
            case "+" -> first + second;
            case "-" -> first - second;
            case "*" -> first * second;
            case "/" -> first / second;
            default -> throw new IllegalArgumentException("Illegal value: " + op.getToken());
        };
    }

    private boolean compare(Token comp, double first, double second) {
        return switch (comp.getToken()) {
            case ">" -> Double.compare(first, second) == 1;
            case "~" -> Double.compare(first, second) == 0;
            case "<" -> Double.compare(first, second) == -1;
            case "!=" -> Double.compare(first, second) != 0;
            default -> throw new IllegalArgumentException("Illegal value: " + comp.getToken());
        };
    }

    public Interpreter(ArrayList<Token> infixExpr) {
        this.infixExpr = infixExpr;
        currentToken = infixExpr.get(0);
        iterator = 0;
        transCondition = false;
        run();
    }
    private void run() {
        for (; iterator < infixExpr.size(); iterator++) {
            currentToken = infixExpr.get(iterator);
            switch (currentToken.getType()) {
                case "ASSIGN_OP" -> valueTranslate("ENDL");
                case "IF" -> ifTranslate();
                case "WHILE" -> whileTranslate();
                case "DO" -> DoTranslate();
                case "FOR" -> forTranslate();
                case "PRINT" -> printTranslate();
                case "LIST" -> variables.put(infixExpr.get(iterator + 1).getToken(), new LinkedList());
                case "POINT" -> listOperationTranslate();
            }
        }
    }
    private void ifTranslate() {
        interpret_condition();
        if (!transCondition) {
            while (!"ENDL".equals(currentToken.getType())) {
                if ("ELSE".equals(currentToken.getType())) {
                    break;
                }
                iterator++;
                currentToken = infixExpr.get(iterator);
            }
        } else {
            iterator++;
            valueTranslate("ELSE");
            while (!"ENDL".equals(currentToken.getType())) {
                iterator++;
                currentToken = infixExpr.get(iterator);
            }
        }
    }

    private void whileTranslate() {
        int start_iteration = iterator;
        interpret_condition();

        while (transCondition) {
            iterator++;
            valueTranslate("ENDL");
            iterator = start_iteration;
            currentToken = infixExpr.get(iterator);
            interpret_condition();
        }

        while (!"ENDL".equals(currentToken.getType())) {
            iterator++;
            currentToken = infixExpr.get(iterator);
        }
    }

    private void DoTranslate() {
        iterator += 2;
        currentToken = infixExpr.get(iterator);
        int start_iteration = iterator;

        do {
            valueTranslate("WHILE");
            interpret_condition();
            iterator = start_iteration;
            currentToken = infixExpr.get(iterator);
        } while (transCondition);

        while (!"ENDL".equals(currentToken.getType())) {
            iterator++;
            currentToken = infixExpr.get(iterator);
        }
    }

    private void forTranslate() {
        iterator += 3;
        currentToken = infixExpr.get(iterator);
        valueTranslate("DIV");

        iterator--;
        int condition = iterator;
        interpret_condition();

        int indexAfterFor = iterator + 1;
        while (transCondition) {
            while (!"R_BC".equals(currentToken.getType())) {
                iterator++;
                currentToken = infixExpr.get(iterator);
            }
            iterator += 2;
            currentToken = infixExpr.get(iterator);
            valueTranslate("ENDL");

            iterator = indexAfterFor;
            currentToken = infixExpr.get(iterator);
            valueTranslate("R_BC");

            iterator = condition;
            interpret_condition();
        }

        while (!"ENDL".equals(currentToken.getType())) {
            iterator++;
            currentToken = infixExpr.get(iterator);
        }
    }

    private void printTranslate() {
        iterator++;
        currentToken = infixExpr.get(iterator);
        if ("L_BC".equals(currentToken.getType())) {
            Token c = infixExpr.get(iterator + 1);
            switch (c.getType()) {
                case "DIGIT" -> System.out.println(Double.parseDouble(c.getToken()));
                case "VAR" -> System.out.println(variables.get(c.getToken()));
            }
            iterator += 2;
        } else {
            System.out.println(variables);
        }
    }

    private void listOperationTranslate() {
        LinkedList a = (LinkedList) variables.get(infixExpr.get(iterator - 1).getToken());
        Token op = infixExpr.get(iterator + 1);
        switch (op.getType()) {
            case "ADD", "REMOVE", "GET", "CONTAINS" -> {
                double value = Double.parseDouble(infixExpr.get(iterator + 3).getToken());
                switch (op.getType()) {
                    case "ADD" -> a.add(value);
                    case "REMOVE" -> a.remove(value);
                    case "GET" -> a.get((int) value);
                    case "CONTAINS" -> System.out.println(a.contains(value));
                }
            }
            case "CLEAR" -> a.clear();
            case "SIZE" -> System.out.println(a.size());
            case "ISEMPTY" -> System.out.println(a.isEmpty());
        }
    }

    private void valueTranslate(String trans) {
        int indexVar = iterator - 1;
        int startExpr = iterator + 1;

        while (!trans.equals(currentToken.getType())) {
            if ("DIV".equals(currentToken.getType())) {
                double rez = calc(convertToPostfix(infixExpr, startExpr, iterator));
                variables.put(infixExpr.get(indexVar).getToken(), rez);
                indexVar = iterator + 1;
                startExpr = iterator + 3;
            }
            iterator++;
            currentToken = infixExpr.get(iterator);
        }

        double rez = calc(convertToPostfix(infixExpr, startExpr, iterator));
        variables.put(infixExpr.get(indexVar).getToken(), rez);
    }

    private void interpret_condition() {
        int first_argument_index = iterator + 2;
        int comparison_op_index = iterator + 3;
        int second_argument_index = iterator + 4;
        Token s = infixExpr.get(second_argument_index);
        double first = (double) variables.get(infixExpr.get(first_argument_index).getToken());
        double second = switch (s.getType()) {
            case "DIGIT" -> Double.parseDouble(s.getToken());
            case "VAR" -> (double) variables.get(s.getToken());
            default ->  0.0;
        };

        iterator += 6;
        currentToken = infixExpr.get(iterator);

        transCondition = compare(infixExpr.get(comparison_op_index), first, second);
    }

    private ArrayList<Token> convertToPostfix(ArrayList<Token> infixExpr, int start, int end) {
        ArrayList<Token> postfixExpr = new ArrayList<>(); // Выходная строка, содержащая постфиксную запись
        Stack<Token> stack = new Stack<>(); // Инициализация стека, содержащий операторы в виде символов
        //	Перебираем строку
        for (int i = start; i < end; i++) {
            Token c = infixExpr.get(i); // Текущий токен
            switch (c.getType()) {
                case "DIGIT", "VAR" -> postfixExpr.add(c); // Если токен - цифра
                case "L_BC" -> stack.push(c); // Если открывающаяся скобка заносим её в стек
                case "R_BC" -> { //	Если закрывающая скобка
                    //	Заносим в выходную строку из стека всё вплоть до открывающей скобки
                    while (stack.size() > 0 && !"L_BC".equals(stack.peek().getType()))
                        postfixExpr.add(stack.pop());
                    stack.pop(); //	Удаляем открывающуюся скобку из стека
                }
                case "OP" -> {  //	Проверяем, содержится ли символ в списке операторов
                    while (stack.size() > 0 && (operationPriority(stack.peek()) >= operationPriority(c))) {
                        postfixExpr.add(stack.pop());
                    } // Заносим в выходную строку все операторы из стека, имеющие более высокий приоритет
                    stack.push(c); // Заносим в стек оператор
                }
            }
        }

        while (!stack.isEmpty()) {
            postfixExpr.add(stack.pop());
        } // Заносим все оставшиеся операторы из стека в выходную строку

        return postfixExpr;
    }

    private double calc(ArrayList<Token> postfixExpr) {
        Stack<Double> locals = new Stack<>(); // Стек для хранения чисел

        for (Token c : postfixExpr) {
            switch (c.getType()) { // Если символ число
                case "DIGIT" -> locals.push(Double.parseDouble(c.getToken()));
                case "VAR" -> locals.push((double) variables.get(c.getToken()));
                case "OP" -> { // Получаем значения из стека в обратном порядке
                    double second = locals.size() > 0 ? locals.pop() : 0, // Условные оператор
                            first = locals.size() > 0 ? locals.pop() : 0;
                    locals.push(execute(c, first, second)); // Получаем результат операции и заносим в стек
                }
            }
        }

        return locals.pop();
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    @Override
    public String toString() {
        return "Res" + variables;
    }
}
