package com.lisp.interpreter;

public class Node {
    public String value = "";
    public Node right;
    public Node left;

    public Node(){
        this.right = null;
        this.left = null;
    }

    public Node(String value){
        this.value = value;
    }

    public Node getLeft(){
        return left;
    }

    public Node getRight(){
        return right;
    }
}
