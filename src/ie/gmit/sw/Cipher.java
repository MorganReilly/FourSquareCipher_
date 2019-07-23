package ie.gmit.sw;

import java.util.Random;

public class Cipher {
	private char charA, charB;
	private char encCharA, encCharB;
	private char decCharA, decCharB;

	private char[] alphabetKey = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	char[] key0 = { 'Z', 'G', 'P', 'T', 'F', 'O', 'I', 'H', 'M', 'U', 'W', 'D', 'R', 'C', 'N', 'Y', 'K', 'E', 'Q', 'A',
			'X', 'V', 'S', 'B', 'L' };
	char[] key1 = { 'M', 'F', 'N', 'B', 'D', 'C', 'R', 'H', 'S', 'A', 'X', 'Y', 'O', 'G', 'V', 'I', 'T', 'U', 'E', 'W',
			'L', 'Q', 'Z', 'K', 'P' };

	public Cipher() {
	}

	// Main encryption method
	public void encryption(char[] keyZero, char[] keyOne) {
		// Variables
		int aRow, aCol;
		int bRow, bCol;
		int indexForKey0, indexForKey1;

		// Find Rows
		aRow = findCharacterRow(charA);
		bRow = findCharacterRow(charB);

		// Find Cols
		aCol = findCharacterCol(charA);
		bCol = findCharacterCol(charB);

		// Finding index for key
		indexForKey0 = findNewIndex(aRow, bCol);
		indexForKey1 = findNewIndex(bRow, aCol);

		// Encryption
		encCharA = keyZero[indexForKey0];
		encCharB = keyOne[indexForKey1];
	}

	// Main decryption method
	public void decryption(char[] keyZero, char[] keyOne) {
		//
		// Variables
		int aRow, aCol;
		int bRow, bCol;
		int indexForAlphabet0, indexForAlphabet1;

		// Find Rows
		aRow = findCharacterRowDecrypt(charA, keyZero);
		bRow = findCharacterRowDecrypt(charB, keyOne);

		// Find Cols
		aCol = findCharacterColDecrypt(charA, keyZero);
		bCol = findCharacterColDecrypt(charB, keyOne);

		// Find index for key
		indexForAlphabet0 = findNewIndex(aRow, bCol);
		indexForAlphabet1 = findNewIndex(bRow, aCol);

		// Decryption
		decCharA = alphabetKey[indexForAlphabet0];
		decCharB = alphabetKey[indexForAlphabet1];
	}

	// Methods below are used for encryption
	public int findCharIndexInAlphabet(char inputCharacter) {
		int index = 0;
		int i;
		char[] alphabetKey = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
				'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

		for (i = 0; i < alphabetKey.length; i++) {
			if (alphabetKey[i] == inputCharacter) {
				index = i;
				return index;
			}
		}
		return index;
	}

	// O(n)
	public int findCharacterRow(char inputCharacter) {
		int row, indexValue;
		indexValue = findCharIndexInAlphabet(inputCharacter);
		row = indexValue / 5;
		return row;
	}

	public int findCharacterCol(char inputCharacter) {
		int col, indexValue;
		indexValue = findCharIndexInAlphabet(inputCharacter);
		col = indexValue % 5;
		return col;
	}

	public char getEncryptedValueA() {
		return encCharA;
	}

	public char getEncryptedValueB() {
		return encCharB;
	}

	// Methods below are used for decryption
	public int findCharIndexInKey(char inputCharacter, char[] key) {
		int index = 0;
		int i;

		for (i = 0; i < key.length; i++) {
			if (key[i] == inputCharacter) {
				index = i;
				return index;
			}
		}
		return index;
	}

	public int findCharacterRowDecrypt(char inputCharacter, char[] key) {
		int row, indexValue;
		indexValue = findCharIndexInKey(inputCharacter, key);
		row = indexValue / 5;
		return row;
	}

	public int findCharacterColDecrypt(char inputCharacter, char[] key) {
		int col, indexValue;
		indexValue = findCharIndexInKey(inputCharacter, key);
		col = indexValue % 5;
		return col;
	}

	public char getDecryptedValueA() {
		return decCharA;
	}

	public char getDecryptedValueB() {
		return decCharB;
	}

	// Methods used for retrieving characters from parser
	public void retrieveCharA(char plainA) {
		this.charA = plainA;
	}

	public void retrieveCharB(char plainB) {
		this.charB = plainB;
	}

	// Method used for finding new index to plug into a key
	public int findNewIndex(int row, int col) {
		int fromRow, fromCol, newIndex;

		fromRow = row * 5;
		fromCol = (5 - (5 - col));

		newIndex = fromRow + fromCol;

		return newIndex;
	}

	public void generateRandomKey(char[] array0, char[] array1) {
		char[] solutionArray = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
				'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

		shuffleArray(solutionArray);
		System.out.print("-------------------------\n");
		for (char i = 0; i < solutionArray.length; i++) {
			array0[i] = solutionArray[i];
			System.out.print(array0[i]);
		}
		System.out.print("\n");

		shuffleArray(solutionArray);

		for (char i = 0; i < solutionArray.length; i++) {
			array1[i] = solutionArray[i];
			System.out.print(array1[i]);
		}
		System.out.print("\n");
		System.out.print("-------------------------\n");
	}

	// Implementing Fisher–Yates shuffle
	static void shuffleArray(char[] ar) {
		Random rnd = new Random();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			char a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}
}
