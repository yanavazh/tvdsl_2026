package tvdsl.hw1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FunctionLexer {
    private final String inputFile;
    private Reader reader;
    private final Map<Integer, Map<Character, Integer>> transitions;

    private final int q0 = 0;
    private final int q1 = 1;
    private final int q2 = 2;
    private final int q3 = 3;
    private final int q4 = 4;
    private final int q5 = 5;
    private final int q6 = 6;
    private final int q7 = 7;
    private final int FINAL = 8;
    //private final int TRAP = 9;

    public FunctionLexer(String inputFileName) {
        this.inputFile = inputFileName;
        transitions = new HashMap<>();
        transitions.put(q0, Map.of('f', q1));
        transitions.put(q1, Map.of('u', q2));
        transitions.put(q2, Map.of('n', q3));
        transitions.put(q3, Map.of('c', q4));
        transitions.put(q4, Map.of('t', q5));
        transitions.put(q5, Map.of('i', q6));
        transitions.put(q6, Map.of('o', q7));
        transitions.put(q7, Map.of('n', FINAL));
        transitions.put(FINAL, Map.of());
        //transitions.put(TRAP, Map.of());
    }

    public void runProcess() {
        try {
            reader = new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.US_ASCII);
            boolean result = isFunctionToken();
            if (result) {
                System.out.println("> Текст соответствует токену FUNCTION");
            }
            else {
                System.out.println("> Текст не соответствует токену FUNCTION");
            }
        }
        catch (IOException e) {
            System.err.println("Не удалось открыть файл для чтения:" + e.getLocalizedMessage());
        }
    }

    private boolean isFunctionToken() throws IOException {
        int currentState = q0;
        int symbol;
        while ((symbol = reader.read()) != -1) {
            char s = (char) symbol;
            Map<Character, Integer> stateTransitions = transitions.get(currentState);

            if (stateTransitions == null || !stateTransitions.containsKey(s)) {
                //currentState = TRAP;
                break;
            }

            currentState = stateTransitions.get(s);

            if (currentState == FINAL) {
                int nextSymbol = reader.read();
                if (nextSymbol == -1) {
                    return true;
                }
                char nextChar = (char) nextSymbol;
                if (Character.isWhitespace(nextChar)) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        return false;
    }
}
