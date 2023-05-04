import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.Scanner;


//put-adicionar // remove-remover // get-recuperar // 

public class Calculadora{

    private static void procedenciaOperadores(){
        HashMap<Character, Character> procedencias = new HashMap<Character, Character>();

        procedencias.put('+' , '1');
        procedencias.put('-' , '1');
        procedencias.put('*' , '2');
        procedencias.put('/' , '2');
        procedencias.put('^' , '3');
    }


    public static String converterInfixaParaPreFixa(String expressaoInfixa, HashMap<Character, Character> procedenciaOperadores){
        StringBuilder preFixa = new StringBuilder();
        Stack<Character> pilhaOperadores = new Stack<>();
        expressaoInfixa = inverterString(expressaoInfixa);

        for(char elemento : expressaoInfixa.toCharArray()){
            if(elemento == '('){
                preFixa.append(')');
            }else if (elemento == ')'){
                preFixa.append('(');
            }else if(Character.isLetterOrDigit(elemento)){
                preFixa.append(elemento);
            }else{
                while (!pilhaOperadores.isEmpty() && procedenciaOperadores.get(elemento) < procedenciaOperadores.get(pilhaOperadores.peek())) {
                    preFixa.append(pilhaOperadores.pop());
            }
            pilhaOperadores.push(elemento);
        }
    }
    while(!pilhaOperadores.isEmpty()){
        preFixa.append(pilhaOperadores.pop());
    }
    return inverterString(preFixa.toString());
    }

    //metodo para inverter a String 
    public static String inverterString(String string){
        return new StringBuilder(string).reverse().toString();
    }


    public static String converterInfixaParaPosFixa(String expressaoInfixa, HashMap<Character, Character> procedenciaOperadores ){
        HashMap<String, String> procedencias = new HashMap<String, String>();
        
        StringBuilder posFixa = new StringBuilder();
        Stack<Character> pilhaOperadores = new Stack<>();
        char[] elementos = expressaoInfixa.toCharArray();

        for(int i=0; i<elementos.length; i++){
            char elemento = elementos[i];
            if(Character.isLetterOrDigit(elemento)){
                posFixa.append(elemento);
            }else if(elemento == '('){
                pilhaOperadores.push(elemento);
            }else if(elemento ==')'){
                while(!pilhaOperadores.isEmpty() && pilhaOperadores.peek() != '('){
                    posFixa.append(pilhaOperadores.pop());
                }
                if(!pilhaOperadores.isEmpty() && pilhaOperadores.peek() != '('){
                    throw new IllegalArgumentException("Expressão inválida!");
                }else{
                    pilhaOperadores.pop();
                }
            }else{
                 while(!pilhaOperadores.isEmpty() && procedenciaOperadores.get(elemento) <= procedenciaOperadores.get(pilhaOperadores.peek())){
                   posFixa.append(pilhaOperadores.pop());
               }
                pilhaOperadores.push(elemento);
            }
        }
        while(!pilhaOperadores.isEmpty()){
            posFixa.append(pilhaOperadores.pop());
        }
        return posFixa.toString();
    }


    public static String converterPreFixaParaInfixa(String expressaoPreFixa, HashMap<Character, Character> procedenciaOperadores){
        Stack<String> pilhaOperadores = new Stack<>();
        expressaoPreFixa = inverterString(expressaoPreFixa);

        for(char elemento : expressaoPreFixa.toCharArray()){
            String operando1, operando2, expressao;
            if(Character.isLetterOrDigit(elemento)){
                pilhaOperadores.push(String.valueOf(elemento));
            }else{
                operando1 = pilhaOperadores.pop();
                operando2 = pilhaOperadores.pop();
                expressao = "(" + operando1 + procedenciaOperadores.get(String.valueOf(elemento)) + operando2 + ")"; 
                pilhaOperadores.push(expressao);
            }
        }
        return pilhaOperadores.pop();
    }

    public static String converterPosFixaParaPreFixa(String expressaoPosFixa, HashMap<Character, Character> procedenciaOperadores ){
        Stack<String> pilhaOperadores = new Stack<>();
        

        for(char elemento : expressaoPosFixa.toCharArray()){
            String operando1, operando2, expressao;
            if(Character.isLetterOrDigit(elemento)){
                pilhaOperadores.push(String.valueOf(elemento));
            }else{
                operando1 = pilhaOperadores.pop();
                operando2 = pilhaOperadores.pop();
                expressao = elemento + operando1 + operando2;
                int procedenciaAtual = procedenciaOperadores.get(elemento);

                while(!pilhaOperadores.isEmpty() && procedenciaOperadores.get(pilhaOperadores.peek()) >= procedenciaAtual){
                    expressao = pilhaOperadores.pop() + expressao;
                }
                pilhaOperadores.push(expressao);
            }
        }
        return pilhaOperadores.pop();
    }

    public static String converterPosFixaParaInfixa(String expressaoPosFixa, HashMap<Character, Character> procedenciaOperadores){
        Stack<String> pilhaOperadores = new Stack<>();

        for(char elemento : expressaoPosFixa.toCharArray()){
            String operando1, operando2, expressao;
            if(Character.isLetterOrDigit(elemento)){
                pilhaOperadores.push(String.valueOf(elemento));
            }else{
                operando1 = pilhaOperadores.pop();
                operando2 = pilhaOperadores.pop();
                expressao = "(" + elemento + operando1 + operando2 + ")";
                pilhaOperadores.push(expressao);
        }
    }
    return pilhaOperadores.pop();
}


    public static String converterPreFixaParaPosFixa(String expressaoPreFixa, HashMap<Character, Character> procedenciaOperadores){
        Stack<String> pilhaOperadores = new Stack<>();
        expressaoPreFixa = inverterString(expressaoPreFixa);

        for(char elemento : expressaoPreFixa.toCharArray()){
            String operando1, operando2, expressao;
            if(Character.isLetterOrDigit(elemento)){
                pilhaOperadores.push(String.valueOf(elemento));
            }else{
                operando1 = pilhaOperadores.pop();
                operando2 = pilhaOperadores.pop();
                expressao = operando1 + operando2 + elemento;

                while(!pilhaOperadores.isEmpty() && procedenciaOperadores.get(elemento) <= procedenciaOperadores.get(pilhaOperadores.peek().charAt(0))){
                    expressao = pilhaOperadores.pop() + expressao;
                }
                pilhaOperadores.push(expressao);
            }
        }
        return pilhaOperadores.pop();
    } 


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Calculos

    public static double calcularSoma(double operador1, double operador2) {
        return operador1 + operador2;
    }
    
    public static double calcularSubtracao(double operador1, double operador2) {
        return operador1 - operador2;
    }
    
    public static double calcularMultiplicacao(double operador1, double operador2) {
        return operador1 * operador2;
    }
    
    public static double calcularDivisao(double operador1, double operador2) {
        if (operador2 == 0) {
            throw new ArithmeticException("Não é possível dividir por zero!");
        }
        return operador1 / operador2;
    }

    public static double calcularExponenciacao(double operador1, double operador2){
        return Math.pow(operador1,operador2);
    }
    

    public double calcularExpressaoPosFixa(String expressaoPosFixa){
        Stack<Double> pilhaOperadores = new Stack<>();

        for(char elemento : expressaoPosFixa.toCharArray()){
            if(Character.isLetterOrDigit(elemento)){
                pilhaOperadores.push(Double.valueOf(elemento - '0'));
            } if(elemento == '+'){
                double operador2 = pilhaOperadores.pop();
                double operador1 = pilhaOperadores.pop();
                pilhaOperadores.push(calcularSoma(operador1, operador2));
            } if (elemento == '-') {
                double operador2 = pilhaOperadores.pop();
                double operador1 = pilhaOperadores.pop();
                pilhaOperadores.push(calcularSubtracao(operador1, operador2));
            } if (elemento == '*') {
                double operador2 = pilhaOperadores.pop();
                double operador1 = pilhaOperadores.pop();
                pilhaOperadores.push(calcularMultiplicacao(operador1, operador2));
            } if (elemento == '/') {
                double operador2 = pilhaOperadores.pop();
                double operador1 = pilhaOperadores.pop();
                pilhaOperadores.push(calcularDivisao(operador1, operador2));
            } else  {
                throw new IllegalArgumentException("Operador inválido.");
            } if (elemento == '^'){
                double operador2 = pilhaOperadores.pop();
                double operador1 = pilhaOperadores.pop();
                pilhaOperadores.push(calcularExponenciacao(operador1, operador2));
            }
  }
  return pilhaOperadores.pop();
}
}
//incompleto
      
    
    

  
