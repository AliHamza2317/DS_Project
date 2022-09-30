package com.company;

public class Node {


//****************************** VARIABLES ****************************
    char element;
    int num;
    String bincode;
    Node left;
    Node right;

//**************************** CONSTRUCTORS ******************************

    public Node(char element,int n) {
        this.element = element;
        this.num=n;
    }

    public Node(char element,String code) {
        this.element = element;
        this.bincode=code;
    }
}
//*************************** END****************************************