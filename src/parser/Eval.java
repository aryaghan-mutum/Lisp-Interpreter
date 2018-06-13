package parser;

import com.lisp.interpreter.Node;

public class Eval {

    public static final String PLUS = "PLUS";
    public static final String MINUS = "MINUS";
    public static final String LESS = "LESS";
    public static final String TIMES = "TIMES";
    public static final String GREATER = "GREATER";
    public static final String EQ = "EQ";
    public static final String CONS = "CONS";
    public static final String CAR = "CAR";
    public static final String CDR = "CDR";
    public static final String ATOM = "ATOM";
    public static final String INT = "INT";
    public static final String QUOTE = "QUOTE";
    public static final String NULL = "NULL";
    public static final String NIL = "NIL";
    public static final String T = "T";
    public static final String COND = "COND";
    public static final String DEFUN = "DEFUN";

    static int ef = 0;
    static String es = "";

    static String[] arr = { "T", "NIL", "CAR", "CDR", "CONS", "ATOM", "EQ", "NULL", "INT", "PLUS", "MINUS", "TIMES", "LESS", "GREATER", "COND", "QUOTE", "DEFUN" };
    static Node dlist = new Node("");

    public Node eval(Node aList, Node fullTree){
        if(intchk(fullTree))
            return fullTree;
        else if(fullTree.value.equals(NIL))
            return fullTree;
        else if(fullTree.value.equals(T))
            return fullTree;
        else if(fullTree.value.equals("")){
            if(fullTree.left.value.equals(PLUS) || fullTree.left.value.equals(MINUS) ||
                    fullTree.left.value.equals(LESS) || fullTree.left.value.equals(TIMES) ||
                    fullTree.left.value.equals(GREATER) || fullTree.left.value.equals(EQ)||
                    fullTree.left.value.equals(CONS)) {
                int len = length(fullTree);
                if(len == 3){
                    if(fullTree.left.value.equals(PLUS)){
                        Node pl = plus(aList, fullTree.right);
                        fullTree = pl;
                        return fullTree;
                    }
                    else if(fullTree.left.value.equals(MINUS)){
                        Node pl = minus(aList, fullTree.right);
                        fullTree = pl;
                        return fullTree;
                    }
                    else if(fullTree.left.value.equals(LESS)){
                        Node pl = less(aList, fullTree.right);
                        fullTree = pl;
                        return fullTree;
                    }
                    else if(fullTree.left.value.equals(GREATER)){
                        Node pl = greater(aList, fullTree.right);
                        fullTree = pl;
                        return fullTree;
                    }
                    else if(fullTree.left.value.equals(TIMES)){
                        Node pl = times(aList, fullTree.right);
                        fullTree = pl;
                        return fullTree;
                    }
                    else if(fullTree.left.value.equals(CONS)){
                        Node pl = cons(aList, fullTree.right);
                        fullTree = pl;
                        return fullTree;
                    }
                    else {
                        Node pl = eq(aList, fullTree.right);
                        fullTree = pl;
                        return fullTree;
                    }
                }
                else{
                    ef = 1;
                    es = "undefined : invalid length";
                    return fullTree;
                }
            }
            else if(fullTree.left.value.equals(CAR) || fullTree.left.value.equals(CDR) ||
                    fullTree.left.value.equals(ATOM)|| fullTree.left.value.equals(INT)||
                    fullTree.left.value.equals(QUOTE)||fullTree.left.value.equals(NULL)) {
                int lgth = length(fullTree);
                if(lgth == 2){
                    if(fullTree.left.value.equals(CAR)){
                        Node pl = car(eval(aList,fullTree.right.left));
                        fullTree = pl;
                        return fullTree;
                    }
                    else if(fullTree.left.value.equals(CDR)){
                        Node pl = cdr(eval(aList,fullTree.right.left));
                        fullTree = pl;
                        return fullTree;
                    }
                    else if(fullTree.left.value.equals(QUOTE)){
                        Node pl = quote(fullTree.right);
                        fullTree = pl;
                        return fullTree;
                    }
                    else if(fullTree.left.value.equals(INT)){
                        if(intchk(eval(aList,fullTree.right.left))){
                            Node pl = new Node("T");
                            return pl;
                        }
                        else{
                            Node pl = new Node(NIL);
                            return pl;
                        }
                    }
                    else if(fullTree.left.value.equals(ATOM)){
                        if(atom(eval(aList, fullTree.right.left))){
                            Node pl = new Node(T);
                            return pl;
                        }
                        else{
                            Node pl = new Node(NIL);
                            return pl;
                        }
                    }
                    else{
                        Node pl = Null(aList,fullTree.right);
                        fullTree = pl;
                        return fullTree;
                    }
                }
                else{
                    ef = 1;
                    es = "undefined : invalid length";
                    return fullTree;
                }
            }
            else if(fullTree.left.value.equals(COND)){
                int lngth = length(fullTree);
                if(lngth > 1){
                    Node pl = cond(aList,fullTree.right);
                    fullTree = pl;
                    return fullTree;
                }
                else{
                    ef = 1;
                    es = "undefined : invalid length";
                    return fullTree;
                }
            }
            else if(fullTree.left.value.equals(DEFUN)){
                int lngth = length(fullTree);
                if(lngth == 4){
                    Node pl = defun(fullTree.right);
                    fullTree = pl;
                    return fullTree;
                }
                else{
                    ef = 1;
                    es = "undefined : invalid length";
                    return fullTree;
                }
            }
            else if(bound(fullTree.left, dlist)){
                Node n = getval(fullTree.left, dlist);
                if(checkLength(fullTree.right, n.left)){
                    aList = addPairs(aList, fullTree.right, n.left);
                    return eval(aList, n.right);
                }
                else{
                    ef = 1;
                    es = "undefined : invalid actual param size";
                    return fullTree;
                }
            }
            else{
                ef = 1;
                es = "undefined: INVALID EXPRESSION";
                return fullTree;
            }
        }

        else if(bound(fullTree, aList))	{
            Node n = getval(fullTree, aList);
            return n;
        }
        else{
            ef = 1;
            es = "eval of literal atoms not defined";
            return fullTree;
        }
    }

    public boolean intchk(Node fullTree){
        try {
            Integer.parseInt(fullTree.value);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }

    public static int length(Node node){
        final String str = "NIL";
        if(rightmost(node).value.equals(str))
            return (node.value.equals(str)) ? 0 : (node.right.value.equals(str)) ? 1 : (1 + length(node.right));
        else
            return (-1);
    }

    public static Node rightmost(Node node){
        return (node.right != null) ? rightmost(node.right) : node;
    }

    public Node plus(Node aList, Node fullTree){
        if(intchk(eval(aList, car(fullTree)))){
            if(intchk(eval(aList, car(cdr(fullTree))))){
                int firstParam = Integer.parseInt(eval(aList, car(fullTree)).value);
                int secondParam = Integer.parseInt(eval(aList, car(cdr(fullTree))).value);
                int result = firstParam + secondParam;
                return new Node(String.valueOf(result));
            }
            else{
                ef = 1;
                es = "undefined: not an integer";
                return(fullTree);
            }
        }
        else{
            ef = 1;
            es = "undefined: not an integer";
            return(fullTree);
        }
    }

    public Node minus(Node aList, Node fullTree){
        if(intchk(eval(aList, car(fullTree)))){
            if(intchk(eval(aList, car(cdr(fullTree))))){
                int firstParam = Integer.parseInt(eval(aList, car(fullTree)).value);
                int secondParam = Integer.parseInt(eval(aList, car(cdr(fullTree))).value);
                int result = firstParam - secondParam;
                return new Node(String.valueOf(result));
            }
            else{
                ef = 1;
                es = "undefined: not an integer";
                return(fullTree);
            }
        }
        else{
            ef = 1;
            es = "undefined: not an integer";
            return(fullTree);
        }
    }

    public Node times(Node aList, Node fullTree){
        if(intchk(eval(aList, car(fullTree)))){
            if(intchk(eval(aList, car(cdr(fullTree))))){
                int firstParam = Integer.parseInt(eval(aList, car(fullTree)).value);
                int secondParam = Integer.parseInt(eval(aList, car(cdr(fullTree))).value);
                int result = firstParam * secondParam;
                return new Node(String.valueOf(result));
            }
            else{
                ef = 1;
                es = "undefined: not an integer";
                return(fullTree);
            }
        }
        else{
            ef = 1;
            es = "undefined: not an integer";
            return(fullTree);
        }
    }

    public Node less(Node aList, Node fullTree){
        if(intchk(eval(aList, car(fullTree)))){
            if(intchk(eval(aList, car(cdr(fullTree))))){
                int firstParam = Integer.parseInt(eval(aList, car(fullTree)).value);
                int secondParam = Integer.parseInt(eval(aList, car(cdr(fullTree))).value);
                return (firstParam < secondParam) ? new Node(String.valueOf(T)) : new Node(String.valueOf(NIL));
            }
            else{
                ef = 1;
                es = "undefined: not an integer";
                return(fullTree);
            }
        }
        else{
            ef = 1;
            es = "undefined: not an integer";
            return(fullTree);
        }
    }

    public Node greater(Node aList, Node fullTree){
        if(intchk(eval(aList,car(fullTree)))){
            if(intchk(eval(aList,car(cdr(fullTree))))){
                int firstParam = Integer.parseInt(eval(aList, car(fullTree)).value);
                int secondParam = Integer.parseInt(eval(aList, car(cdr(fullTree))).value);
                return (firstParam > secondParam) ? new Node(String.valueOf(T)) : new Node(String.valueOf(NIL));
            }
            else{
                ef = 1;
                es = "undefined: not an integer";
                return(fullTree);
            }
        }
        else{
            ef = 1;
            es = "undefined: not an integer";
            return(fullTree);
        }
    }

    public Node eq(Node aList, Node fullTree){
        if(atom(eval(aList, car(fullTree)))){
            if(atom(eval(aList, car(cdr(fullTree))))){
                String firstParam = (eval(aList, car(fullTree))).value;
                String secondParam = (eval(aList, car(cdr(fullTree)))).value;
                return (firstParam.equals(secondParam)) ? new Node(String.valueOf(T)) : new Node(String.valueOf(NIL));
            }
            else{
                ef = 1;
                es = "undefined: not an integer";
                return(fullTree);
            }
        }
        else{
            ef = 1;
            es = "undefined: not an integer";
            return(fullTree);
        }
    }

    public Node Null(Node aList, Node fullTree){
        return ((eval(aList, car(fullTree))).value.equals(NIL)) ? new Node(String.valueOf(T)) : new Node(String.valueOf(NIL));
    }

    public static boolean atom(Node fullTree){
        return (fullTree.left == null && fullTree.right == null) ? true : false;
    }

    public Node cons(Node aList, Node fullTree){
        Node node = new Node("");
        node.left = eval(aList, car(fullTree));
        node.right = eval(aList, car(cdr(fullTree)));
        return node;
    }

    public Node cond(Node aList, Node fullTree){
        int lt = length(fullTree);
        int len = length(fullTree.left);
        if (lt > 1){
            if(fullTree.left.value.equals("") && len == 2){
                Node tem = eval(aList, car(fullTree.left));
                return (tem.value.equals(NIL)) ? cond(aList, fullTree.right) : eval(aList, car(cdr(fullTree.left)));
            }
            else{
                ef = 1;
                es = "undefined: invalid length or expression";
                return fullTree;
            }
        }
        else{
            if(fullTree.left.value.equals("") && len == 2){
                Node tem = eval(aList,car(fullTree.left));
                if(tem.value.equals(NIL)){
                    ef = 1;
                    es = "undefined: invalid  expression";
                    return fullTree;
                }
                else
                    return eval(aList,car(cdr(fullTree.left)));
            }
            else{
                ef = 1;
                es = "undefined: invalid length or expression";
                return fullTree;
            }
        }
    }

    public static Node car(Node fullTree){
        if(!(atom(fullTree)))
            return fullTree.left;
        else{
            ef = 1;
            es = "undefined- Car not defined on singe atom";
            return fullTree;
        }
    }

    public static Node cdr(Node fullTree){
        if(!(atom(fullTree)))
            return fullTree.right;
        else{
            ef = 1;
            es = "undefined- Cdr not defined on singe atom";
            return fullTree;
        }
    }

    public static Node quote(Node fullTree){
        return fullTree.left;
    }

    public static Node defun(Node fullTree){
        Node temp = dlist;
        while (temp.right != null && temp.left != null ){
            temp = temp.right;
        }
        Node q = car(fullTree);
        if((!(checklist(car(fullTree.right)))) || checkKeywords(q) || checkRepeats(car(fullTree.right))){
            ef = 1;
            es = "function name or param list problem";
            return fullTree;
        }
        else{
            temp.value = "";
            Node t = new Node("");
            t.left = car(fullTree);
            t.right = new Node("");
            t.right.left = car(fullTree.right);
            t.right.right = car(fullTree.right.right);
            temp.left = t;
            temp.right = new Node(NIL);
            return t.left;
        }
    }

    public static boolean checklist(Node node){
        return (node.value.equals(NIL) || node.value.equals("")) ? true : false;
    }

    public static boolean checkKeywords(Node node){
        String val = node.value;
        for (int keyword = 0; keyword < 17; keyword++){
            if(val.equals(arr[keyword])){
                ef = 1;
                es = "matches keywords";
                return true;
            }
        }
        return false;
    }

    public static boolean bound(Node x, Node z){
        boolean ans = false;
        while(!(z.value.equals(NIL))){
            if(z.left.left.value.equals(x.value)){
                ans = true;
                z = z.right;
                return ans;
            }
            else
                z = z.right;
        }
        return ans;
    }

    public static boolean checkRepeats(Node fullTree) {
        Node temp = fullTree;
        while(!(temp.value.equals(NIL))){
            if(check(temp.left, temp.right) || (checklist(temp.left)))
                return true;
            else
                temp = temp.right;
        }
        return false;
    }

    public static boolean checkLength(Node a, Node b) {
        return (length(a) == length(b)) ? true : false;
    }

    public static boolean check(Node x, Node y){
        if (checkKeywords(x)) return true;
        else{
            boolean flag = false;
            while(!(y.value.equals(NIL))){
                if(y.left.value.equals(x.value)){
                    y = y.right;
                    return true;
                }
                else
                    y = y.right;
            }
            return flag;
        }
    }

    public static Node getval(Node x, Node z){
        Node ans = null;
        while(!(z.value.equals("NIL"))){
            if(z.left.left.value.equals(x.value)){
                ans = z.left.right;
                return ans;
            }
            else
                z = z.right;
        }
        return ans;
    }

    public Node addPairs(Node aList, Node ap, Node fp){
        Node al = new Node(NIL);
        Node temp = al;
        while(!(ap.value.equals(NIL))){
            temp.value = "";
            temp.left = construct(fp.left, eval(aList, ap.left));
            temp.right = new Node(NIL);
            ap = ap.right;
            fp = fp.right;
            temp = temp.right;
        }
        while(aList != null&&!(aList.value.equals(NIL))){
            temp.value = "";
            temp.left = aList.left;
            aList = aList.right;
            temp.right = new Node(NIL);
            temp = temp.right;
        }
        return al;
    }

    public static Node construct(Node a, Node b){
        Node k = new Node("");
        k.right = b;
        k.left = a;
        return k;
    }

    public static void evlist(){

    }




}
