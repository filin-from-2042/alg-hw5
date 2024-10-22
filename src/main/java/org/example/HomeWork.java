package org.example;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;

public class HomeWork {

    static List<String> OPERATORS = asList("+", "-", "*", "/", "^", "sqr", "sin", "cos", "pow");

    /**
     * <h1>Задание 1.</h1>
     * Требуется реализовать метод, который по входной строке будет вычислять математические выражения.
     * <br/>
     * Операции: +, -, *, / <br/>
     * Функции: sin, cos, sqr, pow <br/>
     * Разделители аргументов в функции: , <br/>
     * Поддержка скобок () для описания аргументов и для группировки операций <br/>
     * Пробел - разделитель токенов, пример валидной строки: "1 + 2 * ( 3 - 4 )" с результатом -1.0 <br/>
     * <br/>
     * sqr(x) = x^2 <br/>
     * pow(x,y) = x^y
     */
    double calculate(String expr) {
        String rpnString = translate(expr);

        return execute(rpnString);
    }

    private static Double execute(String rpnString) {
        String[] rpn = rpnString.split(" ");
        Deque<Double> values = new LinkedList<>();
        for (String cur : rpn) {
            if (isNumber(cur)) {
                values.push(Double.parseDouble(cur));
                continue;
            }

            if (OPERATORS.contains(cur)) {
                var result = 0d;

                switch (cur) {
                    case "sqr": {
                        var opA = values.pop();
                        result = Math.sqrt(opA);
                    }
                    break;
                    case "cos": {
                        var opA = values.pop();
                        result = Math.cos(opA);
                    }
                    break;
                    case "sin": {
                        var opA = values.pop();
                        result = Math.sin(opA);
                    }
                    break;
                    case "pow": {
                        var opB = values.pop();
                        var opA = values.pop();
                        result = Math.pow(opA, opB);
                    }
                    break;
                    case "*": {
                        var opB = values.pop();
                        var opA = values.pop();
                        result = opA * opB;
                    }
                    break;
                    case "/": {
                        var opB = values.pop();
                        var opA = values.pop();
                        result = opA / opB;
                    }
                    break;
                    case "+": {
                        var opB = values.pop();
                        var opA = values.pop();
                        result = opA + opB;
                    }
                    break;
                    case "-": {
                        var opB = values.pop();
                        var opA = values.pop();
                        result = opA - opB;
                    }
                    break;
                }

                values.push(result);
            }
        }
        return values.pop();
    }

    static String translate(String inputString) {
        String[] input = inputString.split(" ");
        List<String> output = new ArrayList<>();
        Deque<String> stack = new LinkedList<>();
        for (String cur : input) {
            //Если токен — число, то добавить его в очередь вывода.
            if (isNumber(cur)) {
                output.add(cur);
            }

            //Если токен — оператор op1, то:
            if (OPERATORS.contains(cur)) {
                //Пока присутствует на вершине стека токен оператор op2, чей приоритет выше или равен приоритету op1,
                // и при равенстве приоритетов op1 является левоассоциативным:
                while (!stack.isEmpty() && !stack.peek().equals("(") && priority(stack.peek()) >= priority(cur)) {
                    //Переложить op2 из стека в выходную очередь;
                    output.add(stack.pop().toString());
                }
                //Положить op1 в стек.
                stack.push(cur);
            }
            if ((cur.equals("(") || cur.equals(")")) && stack.peek() != null
                    && (stack.peek().equals("sin") || stack.peek().equals("cos") || stack.peek().equals("sqr") || stack.peek().equals("pow"))) {
                continue;
            }
            //Если токен — открывающая скобка, то положить его в стек.
            if (cur.equals("(")) {
                stack.push(cur);
            }
            //Если токен — закрывающая скобка:
            if (cur.equals(")")) {
                //Пока токен на вершине стека не открывающая скобка
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    //Переложить оператор из стека в выходную очередь.
                    output.add(stack.pop().toString());
                }
                //Если стек закончился до того, как был встречен токен открывающая скобка, то в выражении пропущена скобка.
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Missing '(' in expression");
                }
                //Выкинуть открывающую скобку из стека, но не добавлять в очередь вывода.
                stack.pop();
            }
        }
        //Если больше не осталось токенов на входе:
        //Пока есть токены операторы в стеке:
        while (!stack.isEmpty()) {
            //Если токен оператор на вершине стека — открывающая скобка, то в выражении пропущена скобка.
            if (stack.peek().equals("(")) {
                throw new IllegalArgumentException("Missing ')' in expression");
            }
            //Переложить оператор из стека в выходную очередь.
            output.add(stack.pop().toString());
        }


        return String.join(" ", output);
    }

    private static boolean isNumber(String number) {
        try {
            Integer.valueOf(number);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static int priority(String operator) {
        switch (operator) {
            case "sqr":
            case "sin":
            case "cos":
                return 4;
            case "pow":
                return 3;
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                throw new IllegalArgumentException("Unknown operator");
        }
    }

}
