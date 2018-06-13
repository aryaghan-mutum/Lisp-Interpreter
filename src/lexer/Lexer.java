package lexer;

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

    private final String START_BRACKET = "(";
    private final String END_BRACKET = ")";
    private final String END_TOKEN = "$";

    private int a = -1;
    private int ef = 0;
    private String es = "";
    private String token = "";

    public String getNextToken(String token){
        this.token = token;
        a = a + 1;
        if(isFirstParentheses())
            return START_BRACKET;
        else if(isEndParentheses())
            return END_BRACKET;
        else if(token.charAt(a) > 64 && token.charAt(a) < 91)
            return getMethodName();
        else if(token.charAt(a) > 47 && token.charAt(a) < 58)
            return getMethodArgument();
        else if(isCheck())
            return getNextToken(token);
        else return END_TOKEN;
    }

    public boolean isFirstParentheses()
    {
        return token.charAt(a) == '(';
    }

    public boolean isEndParentheses()
    {
        return token.charAt(a) == ')';
    }

    public String getMethodName(){
        int count = a;
        String method_name = "";
        while(token.charAt(count) > 43 && token.charAt(count) != '\n'&& token.charAt(count) != '\r'){
            method_name = method_name + token.charAt(count);
            count++;
        }
        a = count - 1;
        return(method_name);
    }

    public String getMethodArgument(){
        int temp = a;
        String method_argument = "";
        while(token.charAt(temp) > 43){
            if(token.charAt(temp) > 60)
                ef = 1;
            method_argument = method_argument + token.charAt(temp);
            temp++;
        }
        a = temp - 1;
        es = "token:" + method_argument;
        return(method_argument);
    }

    public boolean isCheck(){
        return (token.charAt(a) == 32 || token.charAt(a) == 10 ||
                token.charAt(a) == 13 || token.charAt(a) == '\n' ||
                token.charAt(a) == '\r' || token.charAt(a) == '\t' || token.charAt(a) == ' ');
    }


}
