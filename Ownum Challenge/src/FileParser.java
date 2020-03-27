/*
Ownum coding Challenge
Reads in a file and prints out three pieces of information
    The number of words in the file
    The top ten most common words and how often they appear
    The last sentence in which the most common words appears
 */

import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

public class FileParser {

    public static void main(String[] args) {
        try {
            //Finds and scans the file
            String basePath = new File("").getAbsolutePath();
            String path = basePath + ("\\src\\passage.txt");
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            //String that will contain the entire file
            String lines = "";
            while (scanner.hasNext()) {
                lines = lines.concat((scanner.nextLine() + " "));
            }

            lines = lines.toLowerCase();
            //Array of Strings containing each sentence
            String[] sentences = lines.split("\\.");
            //Array containing each word individually - removes punctuation
            String[] words = lines.replaceAll("[^a-z\\s\\-]", "").split("\\s+");

            System.out.println("Number of Words: " + words.length);

            //Map that will link words to their counts
            SortedMap<String, Integer> wordMap = new TreeMap<>();

            for (String str : words) {
                placeInMap(str, wordMap);
            }

            //List of words in order
            ArrayList<String> wordList = new ArrayList<>(wordMap.keySet());
            //List of word counts in order
            ArrayList<Integer> countList = new ArrayList<>(wordMap.values());

            //Sort the lists
            doubleListSort(wordList, countList);

            //Take the top ten words
            topTenWords(wordList, countList);

            //Find the last sentence with the most common word.
            sentenceMatch(wordList.get(0), sentences);

            //Catches if the file is not found
        } catch (FileNotFoundException exception) {
            System.out.println("File not found");
        }
    }

    /*
    Takes a String (word) as an input
    Keeps track of how many times a word has occurred
    If word is not already in the map places word in the map as a key with a value of 1
    Otherwise increments the value associated with word in the map by one
 */
    private static void placeInMap(String word, Map<String, Integer> map) {
        if (map.containsKey(word)) {
            int count = map.get(word);
            count += 1;
            map.put(word, count);
        } else {
            map.putIfAbsent(word, 1);
        }
    }


    /*
    Sorts the wordList and countList based on the countList values
    Every move countList makes is duplicated in wordList
     */
    private static void doubleListSort(List<String> strings, List<Integer> integers) {
        for (int i = 0; i < strings.size() - 1; i++) {
            for (int j = i + 1; j < strings.size(); j++) {
                if (integers.get(i) < integers.get(j)) {
                    swap(i, j, strings, integers);
                }
            }
        }
    }

    /*
    Swaps the values at the input indices
    In both the countList and the wordList
     */
    private static void swap(int x, int y, List<String> strings, List<Integer> integers) {
        String tempString = strings.get(x);
        Integer tempInt = integers.get(x);
        integers.set(x, integers.get(y));
        integers.set(y, tempInt);
        strings.set(x, strings.get(y));
        strings.set(y, tempString);
    }

    /*
    Finds the last String in the input String array
    containing the input String
     */
    private static void sentenceMatch(String word, String[] sentences) {
        boolean isFound = false;
        int x = sentences.length - 1;
        while (!isFound) {
            if (sentences[x].contains(word)) {
                System.out.println("The last sentence containing the most common word is:");
                System.out.println(sentences[x].trim());
                isFound = true;
            } else {
                x -= 1;
            }
        }
    }

    /*
    Prints the first ten values in the two input lists
    Does not care between two words that appear
    the same number of times
     */
    private static void topTenWords(List<String> strings, List<Integer> integers){
        System.out.println("The top ten words are:");
        for (int i = 0; i < 10; i++) {
            System.out.println((i+1) + ". " + strings.get(i) + " - " + integers.get(i));
        }
    }
}
