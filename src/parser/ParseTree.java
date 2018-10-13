package parser;

import com.lisp.interpreter.Node;
import lexer.Lexer;
import helpers.ReadFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParseTree {

    private int ef = 0;
    private int t = 0;
    private String fileContent = "";
    Lexer lexer = new Lexer();
    ReadFile read = new ReadFile();
    public static List<Node> roots = new ArrayList<Node>();


    public void makeTree(Node root) throws IOException {

        fileContent = read.readInputFile();

        if(ef != 1) {
            String atom = lexer.getNextToken(fileContent);
            if(atom.equals("(")) {
                root.left = new Node("");
                makeTree(root.left);
                root.right = new Node("");
                makeTree(root.right);
            }
            else if(atom.equals(")")) {
                root.value = "NIL";
                return;
            }
            else {
                root.left = new Node(atom);
                root.right = new Node("");
                makeTree(root.right);
                return;
            }
        }
        else
            return;
    }

    public void someFunc() throws IOException {
        while(ef != 1){
            String tokens = lexer.getNextToken(fileContent);
            if(tokens.equals("(")){
                Node root = new Node("");
                roots.add(t, root);
                makeTree(roots.get(t));
                t++;
            }
            else if (tokens.equals("$")){
                break;
            }
            else{
                Node root = new Node(tokens);
                roots.add(t, root);
                t++;
            }
        }
    }

}
