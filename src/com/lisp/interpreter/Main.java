package com.lisp.interpreter;

import java.util.*;
import java.io.*;
import helpers.ReadFile;
import lexer.Lexer;
import parser.Eval;

public class Main {

    public static final String NIL = "NIL";

    static int ef = 0;
    static int t = 0;
    static String es = "";
    static String fileContent = "";
    public static List<Node> roots = new ArrayList<Node>();

    static Lexer lexer = new Lexer();
    static Eval ev = new Eval();

    public static void main(String [] args) throws IOException{

        ReadFile readfile = new ReadFile();
        fileContent = readfile.readInputFile();

        verify(fileContent);

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
        if(ef != 1){
            int r = roots.size();
            for(int j = 0; j < r; j++){
                //prtr(roots.get(j));
                //System.out.println();
            }
        }
        else
            System.out.println("ERROR: Invalid " + es);

        int r = roots.size();
        Node fin = null;

        for(int j = 0; j < r; j++){
            fin = roots.get(j);
            if(fin.value.equals("T")||fin.value.equals("NIL")||ev.intchk(fin)){
                printList(fin);
                System.out.println();
            }
            else if(fin.value.equals("")){
                Node aList = null;

                fin = ev.eval(aList,roots.get(j));
                if(ef != 1){
                    if(fin.value.equals("")){
                        System.out.print("(");
                        printList(fin);
                        System.out.print(")");
                    }
                    else{
                        printList(fin);
                    }
                }
                else{
                    System.out.println(es);
                    break;
                }
                System.out.println();
            }
            else{
                System.out.print("undefined-single atom cannot be any thing other than T NIL and an INT");
                break;
            }
        }
    }

    public static void makeTree(Node root){
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

    public static void prtr(Node ptr){
        if(ptr != null){
            if(ptr.right == null && ptr.left == null)
                System.out.print(ptr.value);
            else{
                System.out.print("(");
                prtr(ptr.left);
                System.out.print(" . ");
                prtr(ptr.right);
                System.out.print(")");
            }
        }
    }

    public static void verify(String token){
        int count = 0;
        int parentheses = 0;

        while(token.charAt(count) != '$'){
           if(token.charAt(count) == '(')
               parentheses++;
           else if(token.charAt(count) == ')')
               parentheses--;
           count++;
        }

        if(parentheses != 0){
            ef = 1;
            es = "does not follow grammar";
            //break;
        }
    }

    public static void printList(Node ptr){
        if(ptr != null/*&!(ptr.value.equals("nil"))*/){
            if(ptr.right == null && ptr.left == null)
                System.out.print(ptr.value);
            else{
                if(ptr.left.value.equals("")){
                    System.out.print("(");
                    printList(ptr.left);
                    System.out.print(")");
                }
                else 
                    printList(ptr.left);

                System.out.print(" ");
                
                if(ptr.right.value.equals(NIL))
                    System.out.print("");
                else if(ptr.right.value.equals(""))
                    printList(ptr.right);
                else {
                    System.out.print(" . ");
                    printList(ptr.right);
                }
            }
        }
    }
}

