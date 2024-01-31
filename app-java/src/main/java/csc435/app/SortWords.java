package csc435.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;


public class SortWords
{
    public long num_words = 0;
    public double execution_time = 0.0;

    public void sort_words(String input_dir, String output_dir)
    {

        // Get the list of files in the input directory
        File[] files = new File(input_dir).listFiles();

        // Loop through each file
        for (File file : files) {
            // Get the file name
            String fileName = file.getName();

            // Read the file content as a list of words
            List<String> words = null;
            try {
                words = Files.readAllLines(Paths.get(file.getPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Update the number of words
            num_words += words.size();

            // Sort the words using Collections.sort()
            Collections.sort(words);

            // Write the sorted words to a new file in the output directory
            try {
                Files.write(Paths.get(output_dir + "/" + fileName), words);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        if (args.length != 2) {
            System.err.println("improper number of arguments");
            System.exit(1);
        }

        SortWords sortWords = new SortWords();

        // Record the start time
        long startTime = System.currentTimeMillis();

        // Call the sort_words method
        sortWords.sort_words(args[0], args[1]);

        // Record the end time
        long endTime = System.currentTimeMillis();

        // Calculate the execution time
        sortWords.execution_time = endTime - startTime;

        System.out.print("Finished sorting " + sortWords.num_words + " words");
        System.out.println(" in " + sortWords.execution_time + " miliseconds");
    }
}
