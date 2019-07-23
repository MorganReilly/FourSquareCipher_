package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Menu extends Cipher{
	Cipher cipher = new Cipher();

	private Scanner s = new Scanner(System.in);
	private String mainMenuHeader = "\n-------------------------\nMain Menu Options\n1: Encrypt\n2: Decrypt\n3: Set Keys\n4: Display contents of file\n[-1 to Quit]\n-------------------------\n--> ";

	// TODO: Change to command line
	JFileChooser inFile = new JFileChooser();
	JFileChooser outFile = new JFileChooser();
	
	// Input / Output File message headers
	String inFileMessage = "Choose a file to read from: ";
	String outFileMessage = "Choose a file to write to: ";
	
	// Relative file directory locations
	String inputDIR = "./Input/";
	String outputDIR = "./Output/";

	// Default constructor
	public Menu() {
	}

	public void show() {
		displayKey(key0, key1);

		// Variables
		int option;

		System.out.println("\n** Four Square Cipher **\n");
		System.out.println(mainMenuHeader);

		// Read in option from command line
		option = s.nextInt();

		// Process the option based on choice
		process(option);

		// -1 to quit, else keep running
		while (option != -1) {
			System.out.println(mainMenuHeader);

			option = s.nextInt();

			process(option);
		}
	}

	public void process(int option) {
		if (option == 1)
			encryptMenu();
		else if (option == 2)
			decryptMenu();
		else if (option == 3)
			keyMenu();
		else if (option == 4)
			selectAndDisplayFile();
	}

	public void encryptMenu() {
		int choice;
		String menuHeader = "\n-------------------------\nEncryption Options\n1: Encrypt from file\n2: Encrypt from url\n[-1 to return]\n-------------------------\n--> ";
		System.out.print(menuHeader);
		choice = s.nextInt();

		while (choice != -1) {
			if (choice == 1) {
				// Encrypt from file

				inFile.setDialogTitle(inFileMessage);
				inFile.setCurrentDirectory(new File(inputDIR));
				int fileInputResult = inFile.showOpenDialog(null);

				outFile.setDialogTitle(outFileMessage);
				outFile.setCurrentDirectory(new File(outputDIR));
				int fileOutputResult = outFile.showOpenDialog(null);

				if (fileInputResult == JFileChooser.APPROVE_OPTION && fileOutputResult == JFileChooser.APPROVE_OPTION) {
					String resourceIn = inFile.getSelectedFile().getAbsolutePath();
					String resourceOut = outFile.getSelectedFile().getAbsolutePath();
					new Parser().parseForEncryption(resourceIn, resourceOut, key0, key1);
				}
			} else if (choice == 2) {
				// Encrypt from URL

				String inputURL = JOptionPane.showInputDialog("Enter URL");
				boolean validURL = false;

				outFile.setDialogTitle(outFileMessage);
				outFile.setCurrentDirectory(new File(outputDIR));
				int fileOutputResult = outFile.showOpenDialog(null);

				if (fileOutputResult == JFileChooser.APPROVE_OPTION) {
					String resourceOut = outFile.getSelectedFile().getAbsolutePath();

					while (!validURL) {
						try {
							validURL = true;
							new Parser().parseFromURL(new URL(inputURL), resourceOut, key0, key1);
						} catch (MalformedURLException e) {
							inputURL = JOptionPane.showInputDialog("Bad URL, try again?");
						}
					}
				}
			} else {
				System.out.println("\nInvalid Option!\n");
			}
			System.out.println(menuHeader);
			choice = s.nextInt();
		}
	}

	public void decryptMenu() {
		inFile.setDialogTitle(inFileMessage);
		inFile.setCurrentDirectory(new File(outputDIR));
		int fileInputResult = inFile.showOpenDialog(null);

		outFile.setDialogTitle(outFileMessage);
		outFile.setCurrentDirectory(new File(outputDIR));
		int fileOutputResult = outFile.showOpenDialog(null);

		if (fileInputResult == JFileChooser.APPROVE_OPTION && fileOutputResult == JFileChooser.APPROVE_OPTION) {
			String resourceInput = inFile.getSelectedFile().getAbsolutePath();
			String resourceOutput = outFile.getSelectedFile().getAbsolutePath();

			new Parser().parseForDecryption(resourceInput, resourceOutput, key0, key1);
		}
	}

	public void keyMenu() {
		int choice;
		String menuHeader = "\n-------------------------\nKey Menu\n1: Use defualt keys\n2: Generate random keys\n3: Display current key\n[-1 to return]\n-------------------------\n--> ";
		System.out.print(menuHeader);
		choice = s.nextInt();

		while (choice != -1) {
			if (choice == 1)
				useDefaultKey(key0, key1);
			else if (choice == 2)
				generateRandomKey(key0, key1);
			else if (choice == 3)
				displayKey(key0, key1);
			else
				System.out.println("Invalid option");

			System.out.println(menuHeader);
			choice = s.nextInt();
		}
	}

	public void useDefaultKey(char[] inKey0, char[] inKey1) {
		char[] default0 = { 'Z', 'G', 'P', 'T', 'F', 'O', 'I', 'H', 'M', 'U', 'W', 'D', 'R', 'C', 'N', 'Y', 'K', 'E',
				'Q', 'A', 'X', 'V', 'S', 'B', 'L' };
		char[] default1 = { 'M', 'F', 'N', 'B', 'D', 'C', 'R', 'H', 'S', 'A', 'X', 'Y', 'O', 'G', 'V', 'I', 'T', 'U',
				'E', 'W', 'L', 'Q', 'Z', 'K', 'P' };
		System.out.print("-------------------------\n");

		for (int i = 0; i < default0.length; i++) {
			key0[i] = default0[i];
			System.out.println(key0[i]);
		}
		System.out.println("\n");

		for (int i = 0; i < default1.length; i++) {
			key1[i] = default1[i];
			System.out.println(key1[i]);
		}
		System.out.println("\n");
		System.out.print("-------------------------\n");
	}

	public void displayKey(char[] inKey0, char[] inKey1) {
		System.out.print("-------------------------\n");

		for (int i = 0; i < inKey0.length; i++)
			System.out.print(inKey0[i]);
		System.out.println("\n");

		for (int i = 0; i < inKey1.length; i++)
			System.out.print(inKey1[i]);
		System.out.println("\n");

		System.out.print("-------------------------\n");
	}

	public void selectAndDisplayFile() {
		try {
			String line = null;
			inFile.setDialogTitle(inFileMessage);
			inFile.setCurrentDirectory(new File(inputDIR));
			int fileInputResult = inFile.showOpenDialog(null);

			if (fileInputResult == JFileChooser.APPROVE_OPTION) {
				String resourceInput = inFile.getSelectedFile().getAbsolutePath();
				BufferedReader br = new BufferedReader(new FileReader(resourceInput));

				while ((line = br.readLine()) != null) {
					System.out.println(line);
				}
				br.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
