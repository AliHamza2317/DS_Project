package com.company;

import java.io.*;

public class Tree {

// *********************************** VARIABLES *******************************************
     Node root;
    private int front, rear;
    private Node[] QueueArr;
    private  Node[] UBinary;
    private  Node[] OBinary;
    private String Encoded_data="";

// ********************************** CONSTRUCTOR *******************************************
    public Tree() {
        root = null;
        front = 0;
        rear = -0;
    }
 // ********************************* GETTER  **************************************
    public String getEncoded_data() {
        return Encoded_data;
    }

// ********************************* ..FUNCTION TO MAKE NODES ANS ASSIGN NODES DATA TO QUEUE.. ****************************

    public void AssignNodesToQueue(char element[], int freq[]) {  // TAKING UNIQUE CHAR AND THEIR FREQUENCY AS AN INPUT PARAMETER

        QueueArr = new Node[freq.length];   // SETTING THE SIZE OF ARRAY TO SIZE OF UNIQUE CHARACTERS
        UBinary=new Node[freq.length];

        for (int i = 0; i < element.length; i++) {  // MAKING NODED UNTIL SIZE OF UNIQUE CHARS
            Node n = new Node(element[i], freq[i]);
            Enque(n);
        }
    }

// **************************************** ENQUE FUNCTION **********************************************

    private int c = 0;
    private void Enque(Node data) { // TAKING NODE(CHAR,FREQ ) AS AN INPUT PARAMETER

        if (c < QueueArr.length) {     // CONDITION WHEN ENQUE IS MADE BY ARRAY  (INSERTING AT REAR )
            QueueArr[rear] = data;
            rear++;
            c++;
        }
        else {                     // CONDITION WHEN ENQUE IS CALLED FROM HUFFMANTREE (INSERTING AT FRONT )
            rear++;
            for (int i = rear; i > 0; i--) {
                QueueArr[i] = QueueArr[i - 1];
            }
            QueueArr[front] = data;

        }

    }

    // **************************************** Dequeue FUNCTION **********************************************

    private void Dequeue() {
        if (rear < 0) {
            System.out.println("Queue is Empty");
            return;
        } else {
            for (int i = 0; i < rear - 1; i++)
                QueueArr[i] = QueueArr[i + 1];
        }
        rear--;
        return;
    }

    // ****************************************** FRONT FUNCTION **********************************************
    private Node Front()
    {
        return QueueArr[front];  // RETURN FRONT
    }

    //  ****************************************** SORT QUEUE **********************************************
    public void SortQueue() {
        for (int i = 0; i < rear - 1; i++) {      // SORTING UNTIL REAR
            for (int j = i + 1; j < rear; j++) {

                if (QueueArr[i].num > QueueArr[j].num) {   // CONSITION OF SWAPPING

                    Node temp = QueueArr[i];    // SWAP ALGO
                    QueueArr[i] = QueueArr[j];
                    QueueArr[j] = temp;
                }

            }
        }
        System.out.println(); // PRINTING FREQUENCY AND ELEMENT
        for (int i = 0; i < rear; i++) {
            System.out.print("freq[ " + QueueArr[i].element + " ] " + ":" + QueueArr[i].num + " ; ");
        }
    }
    private int LSTNODES,remove;

// ******************************************** HUFFMAN TREE MAKING FUNCTION ********************************************
    public void MakeHuffmanTree() {
        int fsize = 0;      // VARIABLE TO SET FREQUENC OF STERIC TO BE ADDED
        while (rear >= 0) {    // STOPPING CONDITION

            Node n = new Node('*', fsize);  // MAKING NEW NODE
            fsize = 0;
            root = n;   // ASSIGNING NEW NODE WHICH IS STERIC TO ROOT
            root.left = Front();   // LINKING ROOT with QUEUE FRONT
            fsize += Front().num;   // GETTING FRQ OF ENQUE FRONT ELEMENT
            Dequeue();               // THEN DEQUEue
            root.right = Front();    // REPEATING SAME WITH RIGHT
            fsize += Front().num;
            root.num = fsize;
            Dequeue();
            if (rear < 1) {    // STOPPING CONDITION
                break;
            }
            Enque(root);   // CALLING FUNCTIONS
            SortQueue();
            System.out.println();
        }
        System.out.print("                                    ________________");
        System.out.print("\n----> INORDER TRAVERSAL OF TREE :- | ");   // PRINTING INORDERS
        Inorder(root);

        System.out.print(" |\n                                    ````````````````");
        LSTNODES=calculateNode(root.left)+1;
        remove=(calculateheight(root.right,root.left.num))-1;
    }

    // ****************************************** INORDER TRAVERSAL PRINTER ***********************

    private void Inorder(Node root) {

        if (root != null) {
            Inorder(root.left);
            System.out.print(root.element);
            Inorder(root.right);
        }
    }

// *********************************** SEARCH FUNCTION **************************
    public void search() {
        this.search(this.root, "");
    }
    private int printHT=0;private int BinIndex=0;
// ********************************* SEARCH METHOD OVERRIDING *******************
    private void search(Node root, String str) {

        if(printHT==0)  // FOR ONE TIME PRINT
        {
            System.out.println("\n                  HUFFMAN TABLE \n");
            printHT++;
        }
        if(root.left==null&&root.right==null)  // STOPPING CONDITION AND GETTING CODE AND ELEMENT
        {
            System.out.println();
            System.out.print("                  |   "+root.element + " : "+str+"  |");
            UBinary[BinIndex]=new Node(root.element,str);
            BinIndex++;
        }
        else  // RECURSION
        {
            search(root.left,str+"0");
            search(root.right,str+"1");
        }

    }
   // ******************** FUNCTION FOR CALCULATING HEIGHT OF ANY NODE
    private int calculateheight(Node root, int data)
    {

        if (root == null) {
            return -1;
        }

        int leftHeight = calculateheight(root.left, data);
        int rightHeight = calculateheight(root.right, data);
        int ans = Math.max(leftHeight, rightHeight)+1;
        if (root.num == data) {
            System.out.println(root.num);
        }
        return ans;
    }

    // ******************  FUNCTION TO CALCUlTE NUMBER OF NODES OF ANY SUBTREE TREE OR RIGHT SUBTREE
    private int calculateNode(Node root) {
        int node = 1;

        if (root == null) {
            return 0;
        }
        else {
            node += calculateNode(root.left);
            node += calculateNode(root.right);
            return node;
        }

    }
// ********************************************* CONSTRUCT ENCODED **************************************
    private  int remainBits;
    public void ConstructEncoded()
    {
        String Original="";        // INITIALIZING STRINGS FOR CANCATENATION PURPOSE
        String Combine_bincode="";
        try {
            // ********************* READING ORIGINAL FILE **********************

            File original = new File("D:\\Java Practices\\DS_Project\\original.txt");  // MAKING FILE OBJECT
            BufferedReader reader = new BufferedReader(new FileReader(original));   // MAKING BUFFER READER OBJECT

            String file_data;   // VARIABLE TO FETCH DATA FROM FILE

            while ((file_data = reader.readLine()) != null) {  // WHILE CONDITION
                Original = file_data;
            }
            System.out.println("\n File Data: "+Original+"\n");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        OBinary=new Node[Original.length()];  // ASSIGING LENGTH OF ORIGINAL ARRAY TO OBINARY ARRAY WHICH ASSIGN CODE TO EVERY CHAR IN ORIGINAL ARRAY

        System.out.println(UBinary[0].element);
        // ********* ALGO OG ASSIGNING CODE AGAINST CHARACTER AND SAVING IN NODE ARRAY
        for(int i=0;i<Original.length();i++)
        {
            for(int j=0;j<UBinary.length;j++)
            {
                if(Original.charAt(i)==UBinary[j].element)  // CONDITION
                {
                    OBinary[i]=new Node(Original.charAt(i),UBinary[j].bincode);  // STORING CODE AND ELEMENT AS A NODE IN ARRAY
                    System.out.println("                             | "+OBinary[i].element+" : "+OBinary[i].bincode+" |");
                    Combine_bincode+=OBinary[i].bincode; // STRING COMBINING ALL CODES
                }
            }
        }
        System.out.println("\n                      Number OF BITS: "+Combine_bincode.length());
        System.out.println("\n                      Combine: "+Combine_bincode);

         remainBits=Combine_bincode.length()%7;  // FINDING HOW MANY MORE BITS ARE REMAINING TO COMPLETE 7 BIT
        int no_of_char=Combine_bincode.length()/7;
        if(remainBits>0)
        {
            remainBits=7-remainBits;
            no_of_char+=1;
        }
        String[] sub=new String[no_of_char];
        System.out.println("No of Char: "+no_of_char);
        System.out.println("remainbits: "+remainBits);
        for(int i=0;i<remainBits;i++)
        {
            Combine_bincode+="0";   // ASSIGING REMAIN BITS AS "0" TO STRING
        }
        System.out.println();
        int increment=0;
        // ************* ALGO OF BREAKING COMBINE STRING INTO & BITS *********
        for(int i=0;i<no_of_char;i++) {
            sub[i]=Combine_bincode.substring(increment,increment+7);  // USING SUBTREE FUNCTION TO BREAK
            System.out.println("CHARACTER "+(i+1)+" :"+sub[i]+" : "+ConvertDecimal(sub[i])+" : "+Deci_Askii(ConvertDecimal(sub[i])));
            increment+=7;
            Encoded_data+=Deci_Askii(ConvertDecimal(sub[i])); // FINAL ENCODED AFTER CONVERTING BINARY TO DECI THEN CHAR
        }
         MakeEncodedFile(Encoded_data);
        System.out.println("\n File Size in Bits: "+Encoded_data.length()*7);
    }

    // ************************** FUNCTION TO CONVERT BINARY TO DECIMAL ************************************

    private int ConvertDecimal(String binary)
    {
        int bin=Integer.parseInt(binary);
        int rem,base=1,number,deci=0;
        while ( bin > 0)
        {
            rem = bin % 10;
            deci = deci + rem * base;
            bin = bin / 10;
            base = base * 2;
        }
       return  deci;
    }

    // ********************************** FUNCTION TO CONVERT DECI TO CHAR ***************************************
    private char Deci_Askii(int num)
    {
        char askii=((char)num);
        return askii;
    }

    // ******************************* FUNCTION TO MAKE FILE AND STORING THAT ENCODED CHAR IN File *******************
    private void MakeEncodedFile(String encode)
    {

            try {

                BufferedWriter writer = new BufferedWriter(new FileWriter("encoded.txt")); // writer object creation to write in file
                writer.write(encode); // writing encoded data in file
                writer.close();
            } catch (Exception e) {
               e.printStackTrace();
            }
            System.out.println("\nData Written TO FILE Successfully");
    }

    // ************************************** FUNCTION TO CONSTRUCT RECONSTRUCED *************************************

    public void  ConstructReconstructed() {
        String Recombine = "";
        char[] char_deci = Encoded_data.toCharArray();
        int[] deci = new int[char_deci.length];
        for (int i = 0; i < char_deci.length; i++) {
            deci[i] = (Integer.valueOf(char_deci[i]));
            System.out.println(char_deci[i] + " : " + deci[i] + " : " + Deci_Binary(deci[i]));
            Recombine += Deci_Binary(deci[i]);
        }

        for (int i = 0; i < remainBits; i++) {
            StringBuilder build = new StringBuilder(Recombine);
            build.deleteCharAt(Recombine.length()-1);
            Recombine = "";
            Recombine = String.valueOf(build);
        }
        System.out.println("\nReCombine: " + Recombine);

        String recoverdata="";int j=0;
        for(int i=0;i<OBinary.length;i++)
        {
            String match="";
            for (;j<=Recombine.length();j++)
            {
                if(match.equals(OBinary[i].bincode))
                {
                    recoverdata+=OBinary[i].element;
                    break;
                }
                else
                {
                    match+=Recombine.charAt(j);
                }
            }
        }
        MakeReConstructedFile(recoverdata);
    }

    // ****************************************** DECIMAL TO BINARY CONVERSION *************************************************
    private String Deci_Binary(int num)
    {

            int get=0;String getbin="";
            while(num!=0)
            {
                get=num%2;
                num=num/2;
                getbin+=get;
            }
            while(getbin.length()!=7)
            {
                getbin+="0";
            }
        int k=getbin.length()-1;String getbin2="";
            while(k>=0) {
               getbin2+=getbin.charAt(k);
                 k--;

            }
            return getbin2;
    }

    // ********************************************* MAKING RECONSTRUCTED FILE ****************************************

    private void MakeReConstructedFile(String ReConstructedData)
    {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("reconstructed.txt"));
            writer.write(ReConstructedData);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("\nData Written TO FILE Successfully");
    }

}


