package lexer;

import com.lisp.interpreter.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * File: Lexer
 *
 * This is the class for the Lisp Lexical Analalyzer. It's job is to
 * break apart the meaningful symbols in a Lisp program
 *
 * @author
 * @since
 * @version
 * */


public class Lexer {

    public  List<Node> roots = new ArrayList<Node>();
    private int t = 0;

    private final String START_BRACKET = "(";
    private final String END_BRACKET = ")";
    private final String END_TOKEN = "$";

    private int expressionCount = -1;
    private int errorFlag = 0;
    private String errorStatement = "";
    private String token;

    public String getNextToken(String token){
        this.token = token;
        expressionCount = expressionCount + 1;
        if(isFirstParentheses())
            return START_BRACKET;
        else if(isEndParentheses())
            return END_BRACKET;
        else if(token.charAt(expressionCount) > 64 && token.charAt(expressionCount) < 91)
            return getMethodName();
        else if(token.charAt(expressionCount) > 47 && token.charAt(expressionCount) < 58)
            return getMethodArguments();
        else if(isWhiteSpace())  
            return getNextToken(token);
        else return END_TOKEN;
    }

    public boolean isFirstParentheses() {
        return token.charAt(expressionCount) == '(';
    }

    public boolean isEndParentheses() {
        return token.charAt(expressionCount) == ')';
    }

    public String getMethodName(){
        String methodName = "";
        int methodNameCount = expressionCount;
        while(isLiteralAtom(methodNameCount)){
            methodName = methodName + token.charAt(methodNameCount);
            methodNameCount++;
        }
        expressionCount = methodNameCount - 1;
        return methodName;
    }

    private boolean isLiteralAtom(int methodNameCount) {
        return token.charAt(methodNameCount) > 43 && token.charAt(methodNameCount) != '\n' && token.charAt(methodNameCount) != '\r';
    }

    public String getMethodArguments(){
        String methodArgument = "";
        int methodNameCount = expressionCount;
        while(token.charAt(methodNameCount) > 43){
            if(token.charAt(methodNameCount) > 60)
                errorFlag = 1;
            methodArgument = methodArgument + token.charAt(methodNameCount);
            methodNameCount++;
        }
        expressionCount = methodNameCount - 1;
        errorStatement = "token:" + methodArgument;
        return methodArgument;
    }

    public boolean isWhiteSpace(){
        return (token.charAt(expressionCount) == 32 || token.charAt(expressionCount) == 10 ||
                token.charAt(expressionCount) == 13 || token.charAt(expressionCount) == '\n' ||
                token.charAt(expressionCount) == '\r' || token.charAt(expressionCount) == '\t' || token.charAt(expressionCount) == ' ');
    }

    public void verifyInputString(String token){
        int expressionCount = 0;
        int parentheses = 0;

        while(token.charAt(expressionCount) != '$'){
            if(token.charAt(expressionCount) == '(')
                parentheses++;
            else if(token.charAt(expressionCount) == ')')
                parentheses--;
            expressionCount++;
        }

        if(parentheses != 0){
            errorFlag = 1;
            errorStatement = "does not follow grammar";
            //break;
        }
    }


}
