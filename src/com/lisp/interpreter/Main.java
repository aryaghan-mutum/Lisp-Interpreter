package com.lisp.interpreter;

import java.util.*;
import java.io.*;
import helpers.ReadFile;
import lexer.Lexer;
import parser.Eval;

public class Main {

    static int ef = 0;
    static int t = 0;
    static String es = "";
    static String fileContent = "";
    public static List<Node> roots = new ArrayList<Node>();

    static Lexer lexer = new Lexer();
    static Eval ev = new Eval();
    static Print print = new Print();

    public static void main(String [] args) throws IOException{

        ReadFile readfile = new ReadFile();
        fileContent = readfile.readInputFile();
        lexer.verifyInputString(fileContent);

        someFunc();
        someFunc2();
        someFunc3();
    }

    private static void someFunc() {
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

    private static void someFunc2() {
        if(ef != 1){
            int r = roots.size();
            for(int j = 0; j < r; j++){
                //print(roots.get(j));
                //System.out.println();
            }
        }
        else
            System.out.println("ERROR: Invalid " + es);
    }

    private static void someFunc3() {
        int r = roots.size();
        Node fin = null;

        for(int j = 0; j < r; j++){
            fin = roots.get(j);
            if(fin.value.equals("T")||fin.value.equals("NIL")||ev.intchk(fin)){
                print.printList(fin);
                System.out.println();
            }
            else if(fin.value.equals("")){
                Node aList = null;

                fin = ev.eval(aList,roots.get(j));
                if(ef != 1){
                    if(fin.value.equals("")){
                        System.out.print("(");
                        print.printList(fin);
                        System.out.print(")");
                    }
                    else{
                        print.printList(fin);
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
}

