// К калькулятору из предыдущего дз добавить логирование

import java.util.*;
import java.util.regex.*;
import java.util.logging.*;
import java.io.*;

public class Task4 {
    public static void main(String[] args) {
        // готовимся логгировать
        Logger logger = Logger.getLogger(Task4.class.getName());
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);

        try {
            FileHandler fh = new FileHandler("calc.log");
            logger.addHandler(fh);
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
        } catch (IOException ioex) {
            logger.log(Level.SEVERE, "Can't create log file handler", ioex);
        }

        Scanner scn = new Scanner(System.in);

        while (true) {
            System.out.print("Введите выражение (пустая строка для выхода): ");
            String expression = scn.nextLine();
            if (expression.equals("")) {
                break;
            }
            logger.log(Level.INFO, "Entered expression: " + expression);

            // все это ради того, чтобы можно было вводить выражение без пробелов :)
            // по простому можно было String[] expression = scn.nextLine().split("\\s+");
            List<String> tokens = new ArrayList<>();
            Pattern pattern = Pattern.compile("([0-9.]+)|(\\-|\\+|\\*|\\/)");
            Matcher matcher = pattern.matcher(expression);
            while (matcher.find()) {
                tokens.add(matcher.group().strip());
            }

            double result = 0.0;
            double x = Double.parseDouble(tokens.get(0));
            double y = Double.parseDouble(tokens.get(2));
            switch (tokens.get(1)){
                case "+":
                    result = x + y;
                    break;
                case "-":
                    result = x - y;
                    break;
                case "*":
                    result = x * y;
                    break;
                case "/":
                    result = x / y;
                    break;
                default:
                    result = Double.NaN;
            }

            if (Double.isNaN(result)) {
                System.out.println("Вы ввели неверное выражение");
                logger.log(Level.WARNING, "Expression was wrong!");
            } else {
                System.out.println(expression + " = " + result);
                logger.log(Level.INFO, "The result is " + result);
            }
        }

        scn.close();
        try {
            throw new Exception("You're leaving claculator! Shame on you");
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }
}
