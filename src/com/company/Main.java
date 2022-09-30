package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Main {

// ********************************************* MENU FUNCTION ****************************************************************
    public static void  Menu()
    {
        System.out.println("\n**********************  Menu  ********************");
        System.out.println("\nPress 1 to READ DATA FROM FILE ");
        System.out.println("Press 2 to PRINT FREQUENCY");
        System.out.println("Press 3 to BUILD HUFFMAN TREE AND PRINT IT IN INORDER FORM");
        System.out.println("Press 4 to BUILD HUFFMAN TABLE AND PRINT IT");
        System.out.println("Press 5 to CONSTRUCT 'encoded.txt' AND PRINT IT'S SIZE IN BITS");
        System.out.println("Press 6 to CONSTRUCT 'reconstructed.txt' ");
        System.out.println("Press 7 to Check Equality of Two Files: ");
        System.out.println("Press 8 to Print Complete Report");
        System.out.println("Press 0 to Exit");
        System.out.print("\nEnter Option: ");
    }

    public static void main(String[] args) throws Exception {

        System.out.println("\n**********************  HUFFMAN ALGORITHM  *************");
        System.out.println("\n**********************  DS Project  ********************");

        Scanner sc=new Scanner(System.in);    // CREATING SCANNER OBJECT FOR INPUT

//************************************* VARIABLES ********************************************************
        int opt=-1;
        int no_of_unique_char=0;
        char[] ch=null;
        char[] characters=null;
        int[] c=null;
        int[] freq=null;
        String input_data=null;
        String OriginalFile="",ReConstructedFile="";

//----------------------------------------------------------------------------------------------------------

        Tree t=new Tree();  // MAKING OBJECT OF TREE CLASS


//********************* LOOP FOR CALLING MENU FUNCTION ***************************

        while (opt!=0)
        {

                    Menu();               // CALLING MENU FUNCTION
                    opt = sc.nextInt();   // GETTING USER INPUT FOR QUESTION OPTION


//****************************  OPTION 1 ( READ DATA FROM FILE ) ***************************************
            if(opt==1)
            {
                    try {
                        File original = new File("C:\\Users\\user\\Downloads\\DS_Project\\original.txt");  // CREATING FILE OBJECT
                        BufferedReader reader = new BufferedReader(new FileReader(original));  // BUFFER READER OBJECT TO READ FILE
                        String file_data;   // DATA TYPE
                        while ((file_data = reader.readLine()) != null)    // CONDITION WHILE ALL DATA IS NOT READ FROM FILE
                        {
                          input_data=file_data;
                        }
                        // PRINTING FILE DATA CHARACTERISTICS
                        System.out.println("\nDATA READED SUCCESSFULLY !!");
                        System.out.println("\nDATA IN FILE: "+input_data);
                        System.out.println("\nSIZE OF DATA ( in BITS ): "+input_data.length()*7);

                        //**********  FINDING UNIQUE CHARACTERS ALGO **************

                        ch=input_data.toCharArray();;
                        for (int i = 0; i < input_data.length(); i++) {
                            if(ch[i]!='0')
                            {
                                no_of_unique_char++;
                            }
                            for (int j = i + 1; j < input_data.length(); j++) {
                                if (input_data.charAt(i) == input_data.charAt(j)) {
                                    if (ch[i] != '0') {
                                        no_of_unique_char*=1;
                                        ch[j] = '0';
                                    }
                                }
                            }
                        }
                        System.out.println("\nNUMBER OF UNIQUE CHARACTERS IN FILE: "+no_of_unique_char);
                        characters=new char[no_of_unique_char];

                        //********* ALGO END *****************
                    }

                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
            }
//****************************  OPTION 2 ( PRINT FREQUENCY ) ***************************************
            else if(opt==2)
            {


                        c = new int[input_data.length()];   // MAKING OBJ OF INT ARRAY

                        for (int i = 0; i < c.length; i++) {    // INITIALIZING ARRAY
                            c[i] = 1;
                        }

                        // **************** FREQUENCY FINDING ALGO *************
                        for (int j = 0, i =j+1; j < input_data.length(); i++) {

                            if(i>=input_data.length())
                            {
                                break;
                            }

                            if (input_data.charAt(j) == input_data.charAt(i) ) {
                                c[j] += 1;
                                ch[i]='0';
                            }
                            if (i == input_data.length() - 1) {
                                if (j != input_data.length() - 1) {
                                    j++;
                                    i=j;
                                }
                            }
                }
                        // **************************** ALGO END ************************

                            System.out.println();
                        freq=new int[no_of_unique_char];    // MAKING OBJECT OF INT UNTIL SIZE OF UNIQUR CHARS

                            for(int i=0,k=0;i<c.length;i++)  // LOOP PRINTING FREQUENCY AND UNIQUE CHARS
                            {
                                if(ch[i]!='0')
                                {
                                    System.out.println("FREQUENCY OF [ "+input_data.charAt(i)+" ]"+" : "+c[i]);
                                    characters[k]=input_data.charAt(i);
                                    freq[k]=c[i];
                                    k++;
                                }
                            }
            }
//****************************  OPTION 3 ( BUILD HUFFMAN TREE ) ***************************************
            else if(opt==3)
            {
                                // CALLING FUNCTIONS

                                t.AssignNodesToQueue(characters,freq);
                                t.SortQueue();
                                t.MakeHuffmanTree();

                               // ----------------
            }
//****************************  OPTION 4 ( BUILD HUFFMAN TABLE ) ***************************************
            else if(opt==4)
            {

                                t.search();


            }
//****************************  OPTION 5 ( CONSTRUCT ENCODED.TXT ) ***************************************
            else if(opt==5)
            {
                    t.ConstructEncoded();   // CALLING FUNCTION
            }
//****************************  OPTION 4 ( CONSTRUCT RECONSTRUCTED ) ***************************************
            else if(opt==6)
            {
                  t.ConstructReconstructed();  // CALLING FUNCTION
            }
//****************************  OPTION 7 ( FILE MATCHING ) ***************************************
            else if(opt==7)
            {
                    System.out.println("\n Data In Original File\n");
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader("original.txt"));  // BUFFER READER TO READ ORIGINAL FILE
                        OriginalFile= reader.readLine();   // STORING FILE DATA IN VARIABLE
                        System.out.println(OriginalFile);
                        reader.close();  // CLOSING FILE
                    } catch (Exception e) {

                    }
                System.out.println("\n Data In ReConstructed File\n");
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("reconstructed.txt"));   // BUFFER READER TO READ RECONSTRUCTED FILE
                    ReConstructedFile= reader.readLine();   // STORING FILE DATA IN VARIABLE
                    System.out.println(ReConstructedFile);
                    reader.close();            // CLOSING FILE
                } catch (Exception e) {

                }

                if(OriginalFile.equals(ReConstructedFile))  // CONSITION FOR MATCHING FILE
                {
                    System.out.println("\nBoth Files data Matched !!");
                }
                else
                {
                    System.out.println("\nFiles Data Not Matched");
                }

            }
//****************************  OPTION 8 ( PRINTING REPORT ) ***************************************
            else if(opt==8)
            {
                System.out.println("\n        REPORT !!\n");
                System.out.println("\n Original File Size in Bits: "+OriginalFile.length()*7);
                System.out.println("\n Encoded File Size in Bits: "+t.getEncoded_data().length()*7);
                System.out.println("\n Reconstructed File Size in Bits: "+ReConstructedFile.length()*7);
            }
//****************************  OPTION 0 ( PROGRAM ENDING ) ***************************************
            else if(opt==0)
            {
                System.out.println("\n**********************  PROGRAM ENDED  ********************");
            }
//****************************  INVALID OPTION ***************************************
            else
            {
                System.out.println("\n  Invalid Input !! \n");

            }
        }
    }
}
//******************************* END ***************************************************