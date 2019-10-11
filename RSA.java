package RSA;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.InvalidKeyException;
import java.security.spec.X509EncodedKeySpec;
import java.security.PrivateKey;
import java.security.NoSuchAlgorithmException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

/**
 * @author Arge
 * A class that contains useful methods to encrypt or decrypt data by using RSA algorithm
 */
public class RSA {
    private PublicKey public_key;
    private PrivateKey private_key;

    /**
     * Constructor just with a single parameter
     * @param bit_mode It's a value which indicates the number of bits to use to generate RSA keys
     * 0 = 1024 bit, 1 = 2048 bit
     */
    public RSA(int bit_mode) 
    {
        KeyPairGenerator gen;
        KeyPair container;
        try 
        {
            gen = KeyPairGenerator.getInstance("RSA");
            if (bit_mode == 0) 
            {
                gen.initialize(1024);
            } 
            else if (bit_mode == 1) 
            {
                gen.initialize(2048);
            } 
            else 
            {
                throw new IllegalArgumentException();
            }
            container = gen.genKeyPair();
            public_key = container.getPublic();
            private_key = container.getPrivate();
        } 
        catch(NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        } 
        catch (IllegalArgumentException e) 
        {
            e.printStackTrace();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    /**
     * Constructor with 2 parameters
     * @param public_key The public key passed as String data type
     * @param private_key The private key passed as String data type
     */
    public RSA(String public_key, String private_key)
    {
        this.public_key = getPublicKey(public_key);
        this.private_key = getPrivateKey(private_key);
    }

    //Get Public and Private keys
    /**
     * Private method to generate the public key by starting from the string passed as argument
     * @param base64PublicKey The string that needs to be casted into a PublicKey type object
     * @return A PublicKey object representing the public key
     */
    private PublicKey getPublicKey(String base64PublicKey)
    {
        PublicKey publicKey = null;
        try
        {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
            return null;
        } 
        catch (InvalidKeySpecException e) 
        {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Private method to generate the private key by starting from the string passed as argument
     * @param base64PrivateKey The string that needs to be casted into a PrivateKey type object
     * @return A PrivateKey object representing the private key
     */
    private PrivateKey getPrivateKey(String base64PrivateKey)
    {
        PrivateKey privateKey = null;
        try 
        {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(keySpec);
            return privateKey;
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
            return null;
        }
        catch (InvalidKeySpecException e) 
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Public method to encrypt a plain text contained into a file both by using public key and private key
     * @param input_file The file where the plain text will be read
     * @param output_file The file where the encrypted text will be written
     * @param mode The mode which indicates if the user wants to use public key or private key to encrypt
     * @return A boolean value which indicates if the operation has been successful or not.
     * @throws IOException if the passed files don't exist or any problem occurs during their reading 
     */
    public boolean encrypt(FileInputStream input_file, FileOutputStream output_file, boolean mode)
    throws IOException 
    {
        byte[] bytes = new byte[64];
        int num_bytes;
        Cipher cipher; 

        try 
        {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            if (mode == true)
                cipher.init(Cipher.ENCRYPT_MODE, public_key);
            else
                cipher.init(Cipher.ENCRYPT_MODE, private_key);

            CipherOutputStream cipher_out = new CipherOutputStream(output_file, cipher);
            
            while((num_bytes = input_file.read(bytes)) != -1)
            {
                System.out.println(num_bytes);
                cipher_out.write(bytes,0,num_bytes);
                TimeUnit.SECONDS.sleep(1);
            }

            cipher_out.close();
            return true;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return false;
        }
        catch(NoSuchPaddingException e)
        {
            e.printStackTrace();
            return false;
        }
        catch(InvalidKeyException e)
        {
            e.printStackTrace();
            return false;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Public method to decrypt an encrypted text contained into a file both by using private key and public key
     * @param input_file The file where the encrypted text will be read
     * @param output_file The file where the decrypted text will be written
     * @param mode The mode which indicates if the user wants to use private key or public key to decrypt
     * @return A boolean value which indicates if the operation has been successful or not.
     * @throws IOException if the passed files don't exist or any problem occurs during their reading
     */
    public boolean decrypt(FileInputStream input_file, FileOutputStream output_file, boolean mode)
    throws IOException
    {
        byte[] bytes = new byte[64];
        int num_bytes;
        Cipher cipher;

        try
        {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            if(mode == true)
                cipher.init(Cipher.DECRYPT_MODE, private_key);
            else
                cipher.init(Cipher.DECRYPT_MODE, public_key);

            CipherOutputStream cipher_out = new CipherOutputStream(output_file, cipher);
            
            while((num_bytes = input_file.read(bytes)) != -1)
            {
                System.out.println(num_bytes);
                cipher_out.write(bytes,0,num_bytes);
                TimeUnit.SECONDS.sleep(1);
            }
            cipher_out.close();
            return true;
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return false;
        }
        catch(NoSuchPaddingException e)
        {
            e.printStackTrace();
            return false;
        }
        catch(InvalidKeyException e)
        {
            e.printStackTrace();
            return false;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Public method to print the keys into a text file
     * @param out_public The text file where the public key will be written
     * @param out_private The text file where the private key will be written
     */
    public void printKeys(String out_public, String out_private)
    {
        PublicKey pbk;
        PrivateKey prk;
        String public_key;
        String private_key;
        FileWriter write_pbk;
        FileWriter write_prk;
        BufferedWriter bpbk;
        BufferedWriter bprk;
        try
        {
            pbk = this.public_key;
            prk = this.private_key;

            public_key = Base64.getEncoder().encodeToString(pbk.getEncoded());
            private_key = Base64.getEncoder().encodeToString(prk.getEncoded());

            System.out.println("-----------BEGIN PUBLIC KEY-----------\n"+public_key+"\n------------END PUBLIC KEY------------\n");
            System.out.println("-----------BEGIN PRIVATE KEY----------\n"+private_key+"\n------------END PRIVATE KEY------------\n");
            
            write_pbk = new FileWriter(out_public);
            write_prk = new FileWriter(out_private);

            bpbk = new BufferedWriter(write_pbk);
            bprk = new BufferedWriter(write_prk);

            bpbk.write(public_key,0,public_key.length());
            bprk.write(private_key,0,private_key.length());

            bpbk.close();
            bprk.close();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
