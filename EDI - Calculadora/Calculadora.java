import java.util.*;

public class Calculadora {

    private static final Map<Character, Integer> precedenciaOperadores = new HashMap<>();

    static {
        precedenciaOperadores.put('+', 1);
        precedenciaOperadores.put('-', 1);
        precedenciaOperadores.put('*', 2);
        precedenciaOperadores.put('/', 2);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite a expressão: ");
        String expressao = scanner.nextLine();

        System.out.print("Digite a notação (infixa, pós-fixa ou pré-fixa): ");
        String notacao = scanner.nextLine();

        double resultado;

        if (notacao.equals("infixa")) {
            String posfixa = converterInfixaParaPosfixa(expressao);
            resultado = calcularPosfixa(posfixa);
            String prefixa = converterPosfixaParaPrefixa(posfixa);

            System.out.println("Expressão em notação pós-fixa: " + posfixa);
            System.out.println("Expressão em notação pré-fixa: " + prefixa);
        } else if (notacao.equals("pós-fixa")) {
            resultado = calcularPosfixa(expressao);
            String infixa = converterPosfixaParaInfixa(expressao);
            String prefixa = converterPosfixaParaPrefixa(expressao);

            System.out.println("Expressão em notação infixa: " + infixa);
            System.out.println("Expressão em notação pré-fixa: " + prefixa);
        } else if (notacao.equals("pré-fixa")) {
            resultado = calcularPrefixa(expressao);
            String infixa = converterPrefixaParaInfixa(expressao);
            String posfixa = converterPrefixaParaPosfixa(expressao);

            System.out.println("Expressão em notação infixa: " + infixa);
            System.out.println("Expressão em notação pós-fixa: " + posfixa);
        } else {
            System.out.println("Notação inválida!");
            return;
        }

        System.out.println("Resultado: " + resultado);
    }

    public static String converterInfixaParaPosfixa(String expressao) {
        StringBuilder posfixa = new StringBuilder();
        Stack<Character> pilha = new Stack<>();

        for (int i = 0; i < expressao.length(); i++) {
            char caractere = expressao.charAt(i);

            if (Character.isDigit(caractere)) {
                posfixa.append(caractere);
            } else if (caractere == '(') {
                pilha.push(caractere);
            } else if (caractere == ')') {
                while (!pilha.isEmpty() && pilha.peek() != '(') {
                    posfixa.append(pilha.pop());
                }

                if (!pilha.isEmpty() && pilha.peek() != '(') {
                    throw new IllegalArgumentException("Expressão inválida!");
                }

                pilha.pop();
            } else {
                while (!pilha.isEmpty() && precedencia(caractere) <= precedencia(pilha.peek())) {
                    if (pilha.peek() == '(') {
                        throw new IllegalArgumentException("Expressão inválida!");
                    }

                    posfixa.append(pilha.pop());
                }

                pilha.push(caractere);
            }
        }

        while (!pilha.isEmpty()) {
            if (pilha.peek() == '(') {
                throw new IllegalArgumentException("Expressão inválida!");
            }

            posfixa.append(pilha.pop());
        }

        return posfixa.toString();
    }

    public static double calcularPosfixa(String expressao) {
        Stack<Double> pilha = new Stack<>();

        for (int i = 0; i < expressao.length(); i++) {
            char caractere = expressao.charAt(i);

            if (Character.isDigit(caractere)) {
                pilha.push((double) (caractere - '0'));
            } else {
                double operando2 = pilha.pop();
                double operando1 = pilha.pop();

                switch (caractere) {
                    case '+':
                        pilha.push(operando1 + operando2);
                        break;
                    case '-':
                        pilha.push(operando1 - operando2);
                        break;
                    case '*':
                        pilha.push(operando1 * operando2);
                        break;
                    case '/':
                        pilha.push(operando1 / operando2);
                        break;
                }
            }
        }

        return pilha.pop();
    }

    public static String converterPosfixaParaInfixa(String expressao) {
        Stack<String> pilha = new Stack<>();

        for (int i = 0; i < expressao.length(); i++) {
            char caractere = expressao.charAt(i);

            if (Character.isDigit(caractere)) {
                pilha.push(Character.toString(caractere));
            } else {
                String operando2 = pilha.pop();
                String operando1 = pilha.pop();
                String resultado = "(" + operando1 + caractere + operando2 + ")";

                pilha.push(resultado);
            }
        }

        return pilha.pop();
    }

    public static String converterPosfixaParaPrefixa(String expressao) {
        Stack<String> pilha = new Stack<>();

        for (int i = 0; i < expressao.length(); i++) {
            char caractere = expressao.charAt(i);

            if (Character.isDigit(caractere)) {
                pilha.push(Character.toString(caractere));
            } else {
                String operando2 = pilha.pop();
                String operando1 = pilha.pop();
                String resultado = caractere + operando1 + operando2;

                pilha.push(resultado);
            }
        }

        return pilha.pop();
    }

    public static String converterPrefixaParaInfixa(String expressao) {
        Stack<String> pilha = new Stack<>();

        for (int i = expressao.length() - 1; i >= 0; i--) {
            char caractere = expressao.charAt(i);

            if (Character.isDigit(caractere)) {
                pilha.push(Character.toString(caractere));
            } else {
                String operando1 = pilha.pop();
                String operando2 = pilha.pop();
                String resultado = "(" + operando1 + caractere + operando2 + ")";

                pilha.push(resultado);
            }
        }

        return pilha.pop();
    }

    public static String converterPrefixaParaPosfixa(String expressao) {
        Stack<String> pilha = new Stack<>();

        for (int i = expressao.length() - 1; i >= 0; i--) {
            char caractere = expressao.charAt(i);

            if (Character.isDigit(caractere)) {
                pilha.push(Character.toString(caractere));
            } else {
                String operando1 = pilha.pop();
                String operando2 = pilha.pop();
                String resultado = operando1 + operando2 + caractere;

                pilha.push(resultado);
            }
        }

        return pilha.pop();
    }

    public static double calcularPrefixa(String expressao) {
        Stack<Double> pilha = new Stack<>();

        for (int i = expressao.length() - 1; i >= 0; i--) {
            char caractere = expressao.charAt(i);

            if (Character.isDigit(caractere)) {
                pilha.push((double) (caractere - '0'));
            } else {
                double operando1 = pilha.pop();
                double operando2 = pilha.pop();

                switch (caractere) {
                    case '+':
                        pilha.push(operando1 + operando2);
                        break;
                    case '-':
                        pilha.push(operando1 - operando2);
                        break;
                    case '*':
                        pilha.push(operando1 * operando2);
                        break;
                    case '/':
                        pilha.push(operando1 / operando2);
                        break;
                }
            }
        }

        return pilha.pop();
    }

    public static int precedencia(char operador) {
        if (operador == '+' || operador == '-')
            return 1;
        else if (operador == '*' || operador == '/')
            return 2;
        else
            return 0;
    }
}


