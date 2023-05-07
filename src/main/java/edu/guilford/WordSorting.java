package edu.guilford;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeSet;

public class WordSorting {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        Scanner fileScan = null;

        Path dataLocation = null;
        String fileName = null;
        String[][] words = null;

        try {
            System.out.println("Please enter the name of the file you would like to sort: ");
            fileName = scan.nextLine();
            dataLocation = Paths.get(WordSorting.class.getResource("/" + fileName).toURI());
            FileReader fileReader = new FileReader(dataLocation.toString());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            fileScan = new Scanner(bufferedReader);
            words = readData(fileScan);
        } catch (URISyntaxException | FileNotFoundException | NullPointerException e) {
            e.printStackTrace();
            System.out.println("File not found.");
            System.exit(1);
        }

        // Try to write the words to a new file.

        try {
            writeData(words, "output.txt");
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Prompt the user for a word to search for, and then search for it.
        // If the word is found, return it along with the number of times it appears in
        // the file. If it is not found, return a message saying so.

        System.out.println("Please enter a word to search for: ");
        String searchWord = scan.nextLine();
        int count = 0;

        for (int i = 0; i < words.length; i++) {
            if (searchWord.equals(words[i][0])) {
                count = Integer.parseInt(words[i][1]);
            }
        }

        if (count > 0) {
            System.out.println("The word '" + searchWord + "' appears " + count + " times in the file.");
        } else {
            System.out.println("The word '" + searchWord + "' does not appear in the file.");
        }

    }

    public static String[][] readData(Scanner fileScan) {

        // Using a stack in order to read the file and store it in a manner that allows
        // the system to grab
        // each element and modify it as needed. (punctuation, lowercase, numbers, etc.)

        // Simalarly, a TreeSet will allow us to sort the words in alphabetical order
        // automatically after
        // the modifications have been made to each value.

        Stack<String> stackTemp = new Stack<String>();
        TreeSet<String> organizedWords = new TreeSet<String>();

        // While loop to read the file to the stack, and modify it according to the
        // specifications.

        while (fileScan.hasNext()) {
            String temp = fileScan.next();
            // Remove all punctuation from the string
            temp = temp.replaceAll("[^a-zA-Z0-9]", "");
            // Remove all numbers from the string
            temp = temp.replaceAll("[0-9]", "");
            // Convert all letters to lowercase
            temp = temp.toLowerCase();
            // Push the modified string to the stack
            stackTemp.push(temp);
        }

        // Use the class WordCount to count the number of times each word appears in the
        // file.

        while (!stackTemp.isEmpty()) {
            String word = stackTemp.pop();
            int count = 1; // Base case as an element will always appear at least once.

            // Loop over all elements of the array and add to the count if the word appears
            // more than once.

            for (int i = 0; i < stackTemp.size(); i++) {
                if (word.equals(stackTemp.get(i))) {
                    count++;
                }
            }

            // Create a new instance of the class WordCount and add it to the TreeSet.

            WordCount wordCount = new WordCount(word, count);
            organizedWords.add(wordCount.getWord() + " " + String.valueOf(wordCount.getCount()));

            // If there are any extra copies of the word, remove them from the stack.

            while (stackTemp.contains(word)) {
                stackTemp.remove(word);
            }

        }

        // Print out the sorted words from the TreeSet.

        // for (String word : organizedWords) {
        // System.out.println(word);
        // }

        String[][] words = new String[organizedWords.size()][2];
        int i = 0;

        for (String word : organizedWords) {
            words[i][0] = word.substring(0, word.indexOf(" "));
            words[i][1] = word.substring(word.indexOf(" ") + 1);
            i++;
        }

        return words;
    }

    // Create a method to wrtie the words to a new file.

    public static void writeData(String[][] words, String location) throws URISyntaxException, IOException {
        // "throws" means "not our problem", it's the problem of whoever asked us to run
        // this method
        // Get the path of the right folder
        Path locationPath = Paths.get(WordSorting.class.getResource("/edu/guilford/").toURI());
        // Open a file in that folder
        FileWriter fileLocation = new FileWriter(locationPath.toString() + "/" + location);
        BufferedWriter bufferWrite = new BufferedWriter(fileLocation);
        // Write the data to the file
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words[0].length; j++) {
                bufferWrite.write(words[i][j] + " ");
            }
            bufferWrite.newLine();
        }
        // Always close your files when you're done so that you flush the buffer
        bufferWrite.close();
    }

}
