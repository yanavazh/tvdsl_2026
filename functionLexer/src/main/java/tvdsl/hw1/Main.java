package tvdsl.hw1;

public class Main {
    public static void main(String[] args) {
//        if (args.length < 1) {
//            System.err.println("Не хватает аргументов");
//            System.exit(1);
//        }
//        String inputFileName = args[0];
        String inputFileName = "src/main/resources/test.txt";
        FunctionLexer functionLexer = new FunctionLexer(inputFileName);
        functionLexer.runProcess();
    }
}