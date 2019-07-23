package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Parser extends Cipher {
	Cipher cipher = new Cipher();

	// Variables
	String line;
	int i;
	char charA = ' ';
	char charB = ' ';
	char c;
	Boolean charStored = false;
	char preservedChar = 0;

	public Parser() {
	}

	public void parseForEncryption(String resourceIn, String resourceOut, char[] key0, char[] key1) {
		try {
			System.out.println("** Encrypting file: \n" + resourceIn + "\n");
			long startTime = System.nanoTime();

			BufferedReader br = new BufferedReader(new FileReader(resourceIn));
			BufferedWriter bw = new BufferedWriter(new FileWriter(resourceOut));

			while ((line = br.readLine()) != null) {
				// Convert to upper-case and strip out all unwanted characters
				line = line.toUpperCase().replaceAll("[^A-Z]", "").replace('j', 'i');

				// Iterating through each character
				for (int i = 0; i < line.length(); i++) {
					c = line.charAt(i);

					// If c is a space
					if (c == ' ') {
						if (charStored == true)
							preservedChar = ' ';
						else
							bw.write(" ");
					}
					// Else not a space
					else {
						if (charStored == true) {
							charB = c;

							// Send charB to cipher for encryption
							cipher.retrieveCharB(charB);
							cipher.encryption(key0, key1);

							bw.write(cipher.getEncryptedValueA());

							if (preservedChar != 0) {
								bw.write(preservedChar);
								preservedChar = 0;
							}

							bw.write(cipher.getEncryptedValueB());

							charStored = false;
						} else {
							charA = c;

							// Send charA to cipher for encryption
							cipher.retrieveCharA(charA);
							cipher.encryption(key0, key1);

							charStored = true;
						}
					}
				}

				if (charStored == true)
					preservedChar = '\n';
				else
					bw.write("\n");
			}
			br.close();
			bw.close();
			long endTime = System.nanoTime();
			long totalTime = endTime - startTime;

			System.out.print("\n** Encryption completed sucessfully!\nNavigate to below path to view output:\n\n"
					+ resourceOut + "\n");
			System.out.print("\nEncryption done in: " + totalTime + " (nano seconds)\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void parseForDecryption(String resourceIn, String resourceOut, char[] key0, char[] key1) {
		try {
			System.out.println("** Decrypting file: \n" + resourceIn + "\n");
			long startTime = System.nanoTime();
			BufferedReader br = new BufferedReader(new FileReader(resourceIn));
			BufferedWriter bw = new BufferedWriter(new FileWriter(resourceOut));

			while ((line = br.readLine()) != null) {
				for (int i = 0; i < line.length(); i++) {
					c = line.charAt(i);

					if (c == ' ') {
						if (charStored == true)
							preservedChar = ' ';
						else
							bw.write(" ");
					} else {
						if (charStored == true) {
							charB = c;

							cipher.retrieveCharB(charB);
							cipher.decryption(key0, key1);

							bw.write(cipher.getEncryptedValueA());

							if (preservedChar != 0) {
								bw.write(preservedChar);
								preservedChar = 0;
							}

							bw.write(cipher.getDecryptedValueB());

							charStored = false;
						} else {
							charA = c;

							cipher.retrieveCharA(charA);
							cipher.decryption(key0, key1);

							charStored = true;
						}
					}
				}

				if (charStored == true)
					preservedChar = '\n';
				else
					bw.write("\n");
			}
			br.close();
			bw.close();
			long endTime = System.nanoTime();
			long totalTime = endTime - startTime;
			System.out.print("\n** Decryption completed sucessfully!\nNavigate to below path to view output:\n\n"
					+ resourceOut + "\n");
			System.out.print("\nDecryption done in: " + totalTime + " (nano seconds)\n");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void parseFromURL(URL url, String resourceOut, char[] key0, char[] key1) {
		try {
			System.out.print("** Encrypting URL: \n" + url + "\n");
			long startTime = System.nanoTime();
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			BufferedWriter bw = new BufferedWriter(new FileWriter(resourceOut));

			while ((line = br.readLine()) != null) {
				// Converting to upper-case and stripping out unwanted characters
				line = line.toUpperCase().replaceAll("[^A-Z ]", "").replace('j', 'i');

				// Iterating through each character
				for (i = 0; i < line.length(); i++) {
					c = line.charAt(i);

					// Check if char is space
					if (c == ' ') {
						if (charStored == true)
							preservedChar = ' ';
						else
							bw.write(" ");
					}
					// If not a space
					else {
						if (charStored == true) {
							charB = c;

							// Send charB to cipher for encryption
							cipher.retrieveCharB(charB);
							cipher.encryption(key0, key1);

							bw.write(cipher.getEncryptedValueA());

							if (preservedChar != 0)
								preservedChar = 0;

							bw.write(cipher.getEncryptedValueB());

							charStored = false;
						} else {
							charA = c;

							// Send charA to cipher for encryption
							cipher.retrieveCharA(charA);
							cipher.encryption(key0, key1);

							charStored = true;
						}
					}
				} // for

				if (charStored == true)
					preservedChar = '\n';
				else
					bw.write("\n");

			} // while

			br.close();
			bw.close();
			long endTime = System.nanoTime();
			long totalTime = endTime - startTime;
			System.out.print("\n** Encryption completed sucessfully!\nNavigate to below path to view output:\n\n"
					+ resourceOut + "\n");
			System.out.print("\nEncryption done in: " + totalTime + " (nano seconds)\n");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
