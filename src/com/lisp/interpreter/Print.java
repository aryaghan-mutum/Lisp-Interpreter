package com.lisp.interpreter;

public class Print {

    private final String NIL = "NIL";
    private final String START_BRACKET = "(";
    private final String END_BRACKET = ")";
    private final String DOTTED_PAIR = " . ";

    public void printList(Node node){
        if(node != null){
            if(isBothNodesNull(node))
                System.out.print(node.value);
            else{
                printLeftArgWhenNull(node);
                System.out.print(" ");
                printRightArgWhenNull(node);
            }
        }
    }

    private static boolean isBothNodesNull(Node node) {
        return node.right == null && node.left == null;
    }

    private void printLeftArgWhenNull(Node node) {
        if(node.left.value.equals("")){
            System.out.print(START_BRACKET);
            printList(node.left);
            System.out.print(END_BRACKET);
        }
        else
            printList(node.left);
    }

    private void printRightArgWhenNull(Node node) {
        if(node.right.value.equals(NIL))
            System.out.print("");
        else if(node.right.value.equals(""))
            printList(node.right);
        else {
            System.out.print(DOTTED_PAIR);
            printList(node.right);
        }
    }

    public void print(Node node){
        if(node != null){
            if(isBothNodesNull(node))
                System.out.print(node.value);
            else{
                System.out.print(START_BRACKET);
                print(node.left);
                System.out.print(DOTTED_PAIR);
                print(node.right);
                System.out.print(END_BRACKET);
            }
        }
    }
}
