package csc435.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CountWords
{
    public long dataset_size = 0;
    public double execution_time = 0.0;

    public void count_words(String input_dir, String output_dir)
    {

        // Get the list of files in the input directory
        File[] files = new File(input_dir).listFiles();

        // Loop through each file
        for (File file : files) {
            // Get the file name and size
            String fileName = file.getName();
            long fileSize = file.length();

            // Update the dataset size
            dataset_size += fileSize;

            // Read the file content as a string
            String content = null;
            try {
                content = new String(Files.readAllBytes(Paths.get(file.getPath())));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Count the words in the content using a regex
            // A word is a non-empty sequence of letters, surrounded by whitespace or punctuation
            int wordCount = content.split("\\W+").length;

            // Write the word count to a new file in the output directory
            try {
                Files.write(Paths.get(output_dir + "/" + fileName), String.valueOf(wordCount).getBytes());
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

        CountWords countWords = new CountWords();

        // Record the start time
        long startTime = System.currentTimeMillis();

        // Call the count_words method
        countWords.count_words(args[0], args[1]);

        // Record the end time
        long endTime = System.currentTimeMillis();

        // Calculate the execution time
        countWords.execution_time = endTime - startTime;

        System.out.print("Finished counting " + countWords.dataset_size + " MiB of words");
        System.out.println(" in " + countWords.execution_time + " miliseconds");
    }
}
