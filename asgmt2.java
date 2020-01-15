import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


/**
 * Description: <Some details regarding the pipeline of the assignment ..>
 * 
 * @author Amritpal Saini, 30039983
 * @author Md Mamunur Rashid (mmncit@gmail.com) from http://pages.cpsc.ucalgary.ca/~mdmamunur.rashid1/CPSC319-W19.html
 * @version 7.1.2
 * 
 */

public class asgmt2 {

    public static void main(String[] args) throws IOException {

		/*
		 * Read input, output file name from command line arguments
		 * 
		 * 
		 * Command line instruction of assignment:
		 * -----------------------------------------
		 * 
		 * java asgmt2 [input file name with valid path] [output file name]
		 * 
		 * As example:
		 * 
		 * java asgmt2 example_1--8_words.txt example_1--8_words-out.txt
		 * 
		 * Here, input file is considered to be in the same location/path as
		 * asgmt2.java. Output file will be generated in the same path too.
		 * 
		 * If you want to provide command line arguments in eclipse, check this
		 */
	
		// Check that exactly two command-line arguments are provided
		if (args.length != 2) {
		    System.err
			    .println("Insufficent or too many arguments entered. Program Terminated.");
		    System.exit(1);
		}
		// Quick check for valid input file name (Optional)
		File file = new File(args[0]); // first argument = input
		// if file does not exist or not a file
		if (!(file.exists() && file.isFile())) {
		    System.err
			    .println(args[0] + " does not exist. Program Terminated.");
		    System.exit(1);
		}
		// if everything is okay
		String inputFile = args[0];
		String outputFile = args[1];
		System.out.println("INPUT: " + inputFile + "\nOUTPUT: " + outputFile);
	
		// -------------------------------------------------------------------
	
		/*
		 * Usage of static array
		 */
	
		// create the 1-D array
		StaticArray<String> listA = new StaticArray<String>(30000);
		//read from input file and place into listA
		@SuppressWarnings("resource")
		Scanner read = new Scanner(file);
		listA.add(read.nextLine());
		while(read.hasNextLine()) {
			listA.add(read.nextLine());
		}		
		
		//check size of listA and use algorithm accordingly
		if (listA.capacity() > 100) {
			listA.add(" ");
			listA.trimToSize(); // trim extra memory
			MergeSort(listA);
		}
		else {
			listA.trimToSize(); // trim extra memory
			bubbleSortString(listA);
		}
		
		//Write to output file
		StaticArray<SinglyLinkedList<String>> listB = generateListB(listA);
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
	    writer.write(toString(listB));
	    writer.close();
	}
    /**
     * BubbleSortString
     * Sorts StaticArray
     * @param listA, StaticArray of type string that needs to be sorted
     */
    private static void bubbleSortString(StaticArray<String> listA) {
    	int n = listA.capacity() - 1;
    	int k = n;
    	boolean sorted = false;
    	while( (k > 0) && (sorted == false)) {
    		sorted = true;
    		for(int i = 0; i < k; i++) {
    			if(listA.get(i).compareTo(listA.get(i+1)) > 0) {
    				String tmp = listA.get(i);
    				listA.set(i, listA.get(i+1));
    				listA.set(i+1,tmp);
    				sorted = false;
    			} 
    		}
    		k--;
    	}
	}


    
	/**
     * Create 1-D array storing each set of anagram in a singly-linked list
     * 
     * @param listA
     *            array of sorted words
     * 
     * @return array of list of anagrams (sorted)
     */
    public static StaticArray<SinglyLinkedList<String>> generateListB(StaticArray<String> listA) {

		// initialize listB as an array of singly linked list of Strings
		StaticArray<SinglyLinkedList<String>> listB = new StaticArray<SinglyLinkedList<String>>(listA.size());
		// list Middle will keep the list of separated letters until needed
		StaticArray<StaticArray<String>> Middle = new StaticArray<StaticArray<String>>(listA.size());
		
		//go through all the words in listA and separate them into strings of letters
		for(int i = 0; i < listA.capacity(); i++) {
			String tmp = listA.get(i);
			StaticArray<String> letters = new StaticArray<String>();
			for(int j = 0; j < tmp.length(); j++) {
				letters.add(String.valueOf(tmp.charAt(j)));
			}
			letters.trimToSize();
			//sort list of letters
			bubbleSortString(letters);
			//add to middle to be used later
			Middle.add(letters);
		}
		Middle.trimToSize();
		
		//dne used to replace already used words
		StaticArray<String> dne = new StaticArray<String>();
		dne.add("abc");
		
		//check for anagrams
		for(int i = 0; i < Middle.capacity(); i++) {
			SinglyLinkedList<String> linkedList = new SinglyLinkedList<String>(); //create linkedlist
			if(Middle.get(i).get(0) != "abc") {
				linkedList.addFirst(listA.get(i));
				StaticArray<String> arr1 = Middle.get(i);
				arr1.trimToSize();
				for(int j = 0; 	j < Middle.capacity(); j++) {
					boolean ana = false;
					if( j != i && (Middle.size()> j) && (arr1.get(0) != "abc")) {
						StaticArray<String> arr2 = Middle.get(j);
						arr2.trimToSize();
						ana = false;
						if (arr2.capacity() == arr1.capacity()) {
							for(int k = 0; k < arr1.capacity(); k++) {
								if(!(arr1.get(k).equals(arr2.get(k)))) {
									ana = false;
									k = arr1.capacity();
								} else {
									ana = true;
								}
							}			
						}
						// add word j to i's link
						if(ana) {
							for(int l = 0; l < linkedList.size(); l++) {
								if((linkedList.get(l).equals(listA.get(j)))) {
									Middle.set(i, dne);
									Middle.set(j, dne);
									ana = false;
								}
							}
							if(listA.get(j).compareTo(linkedList.last()) != 0 && ana) {
								linkedList.addLast(listA.get(j));
								Middle.set(i, dne);
								Middle.set(j, dne);
								ana = false;
							}
						}
					}		
					
				}
			}
			//make sure no repeat words
			if(!linkedList.isEmpty()) {
				listB.add(linkedList);
				}
			}
		listB.trimToSize();	
		return listB;
	    }
    
    

    /**
     * Produces a string representation of the contents of the array.
     * 
     * @param arr
     *            StaticArray<SinglyLinkedList<String>>
     * @return string corresponds to the result
     */
    public static String toString(StaticArray<SinglyLinkedList<String>> arr) {
	StringBuilder sb = new StringBuilder("");
	for (int j = 0; j < arr.size(); j++) {
	    if (j > 0) // if new list is present
		sb.append("\n"); // go to new line
	    // if no value in the list then break
	    if (arr.get(j) == null)
		break;
	    else {
		// get all the element(s) in the list and append
		for (int k = 0; k < arr.get(j).size(); k++) {
		    sb.append(arr.get(j).get(k));
		    sb.append(" ");
		}
	    }
	}
	sb.append("");
	return sb.toString();
    }
    
    
    /**
     * MergeSort
     * sorts input
     * @param arr, StaticArray of String that needs to be sorted
     */
    public static void MergeSort(StaticArray<String> arr) {
    	int n = arr.size() - 1;
    	if (!(n < 2)) {
    		int mid = n/2;
    		StaticArray<String> arr1 = arr.subArray(0, mid);
    		StaticArray<String> arr2 = arr.subArray(mid,n);
    		MergeSort(arr1);
    		MergeSort(arr2);	
    		merge(arr1, arr2, arr);	
    	}
    }

    /**
     * merge
     * Merges two arrays into 1 in a sorted order.
     * @param arr1, first half of arr
     * @param arr2, second half of arr
     * @param arr, main array that needs to be ready for final
     */
	private static void merge(StaticArray<String> arr1, StaticArray<String> arr2, StaticArray<String> arr) {
		int i = 0;
		int j = 0;
		while(i+j < arr.capacity()) {
			if ((j == arr2.capacity() -1) || (i < arr1.capacity() -1) && (arr1.get(i).compareTo(arr2.get(j)) < 0)) {
				arr.set(i+j, arr1.get(i));
				i++;
			} else {
				arr.set(i+j, arr2.get(j));
				j++;
			}
		}
		
	}
    
}