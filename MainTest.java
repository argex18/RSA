package RSA;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

import java.util.InputMismatchException;
import java.io.FileNotFoundException;

public class MainTest {
    public static void main(String[] args)
    {
        try
        {
            Scanner in = new Scanner(System.in);
            int mode;
            RSA rsa;

            while(true)
            {
                System.out.println("Create an RSA object (1/2):");
                System.out.println("1) Select 0 to initialize a 1024 bit key, 1 to initialize a 2048 bit key");
                System.out.println("2) Pass as argument respectively the public and the private key (1024-2048 bit)");

                mode = in.nextInt();
                if(mode != 1 && mode != 2)
                {
                    System.out.println("\nInvalidInput, please select 1 for the first option or 2 for the second one");
                    continue;
                }
                break;
            }

            System.out.println("\n\n");
            if(mode == 1)
            {
                while(true)
                {
                    System.out.println("0 - 1024 bit key");
                    System.out.println("1 - 2048 bit key");
                    System.out.println(":> ");

                    mode = in.nextInt();
                    if(mode != 0 && mode != 1)
                    {
                        System.out.println("\nInvalidInput, please select 0 for the first option or 1 for the second one");
                        continue;
                    }
                    else
                    {
                        rsa = new RSA(mode);
                        break;
                    }
                }
            }
            else
            {
                System.out.print("Copy and paste here the public key you want to use: ");
                String publick = in.next();

                System.out.print("Copy and paste here the private key you want to use: ");
                String privatek = in.next();

                rsa = new RSA(publick, privatek);
            }

            System.out.println("\n\n");
            while(true)
            {
                System.out.println("Select the mode you want to operate with (1/2): ");
                System.out.println("1) Encryption");
                System.out.println("2) Decryption");

                mode = in.nextInt();
                if(mode != 1 && mode != 2)
                {
                    System.out.println("\nInvalidInput, please select 1 for the first option or 2 for the second one");
                    continue;
                }

                System.out.println("\n\n");
                System.out.print("Copy and paste here the path of the file from which you want to read the data: ");
                String input_path = in.next().replaceAll("/", "\\");

                System.out.print("Copy and paste here the path of the file which you want to write the data in: ");
                String output_path = in.next().replaceAll("/", "\\");

                System.out.println("\n\n");
                if(mode == 1)
                    rsa.encrypt(new FileInputStream(input_path), new FileOutputStream(output_path), true);
                else
                    rsa.decrypt(new FileInputStream(input_path), new FileOutputStream(output_path), true);
                
                System.out.println("\n");
                System.out.print("Do you want to do other operations with this key pair? (y/n): ");
                
                if(in.next().equalsIgnoreCase("y"))
                    continue;
                else
                {
                    in.close();
                    break;
                }
            }
        }
        catch(InputMismatchException e)
        {
            System.err.println("FATAL ERROR: The input you passed is not valid\n");
            e.printStackTrace();
        }
        catch(FileNotFoundException e)
        {
            System.err.println("FATAL ERROR: The file shown in the next trace was not found\n");
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //Create an RSA object
    //1)Select 0 to initialize a 1024 bit key, 1 to initialize a 2048 bit key
    //2)Pass as argument respectively the public and the private key (1024-2048 bit)
    //RSA rsa = new RSA("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");
            
    //Encrypt and Decrypt methods
    //Please select destination files both for input and for output
    //Select true (default) to encrypt with public key, false to encrypt with private key
    //Select true (default) to decrypt with private key, false to decrypt with public key
    //rsa.encrypt(new FileInputStream("C:/"), new FileOutputStream("C:/"), true);
    //rsa.decrypt(new FileInputStream("C:/"), new FileOutputStream("C:/"), true);

    //Print generated keys
    //Please select destination files path both for public key output and for private key output
    //rsa.printKeys("C:/", "C:/");
}