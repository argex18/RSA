Update 11/10/2019:
WARNING: With the longest available key (2048 bit), the maximum number of convertable bytes it's 245 for the RSA algorithm. 
	 This because the RSA algorithm is always used to securely transfer a symmetric algorithm key with 
	 which then we will be able to encrypt an "endless" data storage. 
	 If you attempt to encrypt over this limit, the procedure will end unsuccessfully.
 
	- Created the executable .class file compiled by javac 13
	- Added a delay of 1 second during the encryption and decryption procedures in order to eventually avoid buffer overflows


-------------------------------------ITALIAN VERSION---------------------------------------

Un oggetto RSA può essere istanziato in due modi:

    1) Passando un parametro di tipo intero definito "bit_mode" per generare due chiavi RSA pubblica e privata
    2) Passando due chiavi RSA già create come argomenti di tipo stringa

Una volta istanziato l'oggetto RSA, sono disponibili i metodi encrypt e decrypt che consentono
rispettivamente di criptare e decriptare dati in un file di qualunque formato (txt, jpg, ecc.).

E' possibile criptare e decriptare utilizzando sia la chiave pubblica che la chiave privata tramite
un parametro di tipo boolean definito in entrambi i metodi.

E' inoltre possibile utilizzare il metodo printKeys per stampare le chiavi utilizzate in due file di testo distinti.
Assicurarsi, come negli altri casi, che i path dei file passati come stringa siano corretti.
In caso contrario verrà lanciata un'eccezione e l'esecuzione del programma s'interromperà.

Assicurarsi inoltre, se si decide di utilizzare chiavi RSA già create, che queste siano valide
ed invertibili soprattutto, altrimenti verrà lanciata un'eccezione.

In caso non si abbia già a disposizione una coppia di chiavi RSA, generarle utilizzando
il primo tipo di costruttore e poi stamparle su file di testo con il metodo printKeys. 

-------------------------------------ENGLISH VERSION---------------------------------------

A RSA object can be istantiated by 2 ways:

    1) By passing an integer type parameter defined "bit_mode" to generate 2 RSA public and private keys
    2) By passing 2 RSA keys already created as String type arguments

Once RSA object has been istantiated, the methods encrypt and decrypt are available which respectively allow
to encrypt and decrypt a plain text written in file, whatever the format is (txt, jpg, ecc.)

It's possible to encrypt and decrypt by both using the public key and the private key through
a boolean type parameter defined in either methods.

It's also possible to use the method printKeys for printing the used keys on 2 distinct text files.
Make sure, as in the other cases, the files path passed as String is corret.
Otherwise, an Exception will occur and the program excecution will stop.

Also make sure, if you decide to use already created RSA keys that they are valid and mostly reversal,
otherwise an Exception will occur.

In case you don't have an RSA key pair yet, generate them by using the first type of constructor
and then print them on text files with the method printKeys.


