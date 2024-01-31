package csc435.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

//@SuppressWarnings("preview") // Suppress errors if using preview features
public class CleanDataset {

    public long dataset_size = 0;
    public double execution_time = 0.0;

    public void clean_dataset(String input_dir, String output_dir) {
        // Create a list of cleaning operations using ArrayList for compatibility
        List<Function<String, String>> cleanOps = new ArrayList<>();
        cleanOps.add(new Function<String, String>() {
            @Override
            public String apply(String s) {
                return s.toLowerCase();
            }
        });
        cleanOps.add(new Function<String, String>() {
            @Override
            public String apply(String s) {
                return s.replaceAll(" ", "");
            }
        });

        // Get the list of files in the input directory
        File[] files = new File(input_dir).listFiles();

        // Loop through each file
        for (File file : files) {
            if (file.isFile()) { // Check if it's a file, not a directory
                // Get the file name and size
                String fileName = file.getName();
                long fileSize = file.length();

                // Update the dataset size
                dataset_size += fileSize;

                // Try reading the file and handle potential exceptions
                try {
                    List<String> lines = Files.readAllLines(Paths.get(file.getPath()));

                    // Clean each line using a traditional loop
                    List<String> cleanedLines = new ArrayList<>();
                    for (String line : lines) {
                        String cleanLine = line; // Copy the string to avoid modifying the original
                        for (Function<String, String> function : cleanOps) {
                            cleanLine = function.apply(cleanLine);
                        }
                        cleanedLines.add(cleanLine);
                    }

                    // Write the cleaned lines to a new file in the output directory
                    Files.write(Paths.get(output_dir + "/" + fileName), cleanedLines);
                } catch (IOException e) {
                    System.err.println("Error reading file: " + file.getPath());
                    e.printStackTrace();
                    continue; // Skip to the next file in the loop
                }
            }
        }
    }


    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("improper number of arguments");
            System.exit(1);
        }

        CleanDataset cleanDataset = new CleanDataset();

        // Record the start time
        long startTime = System.currentTimeMillis();

        // Call the clean_dataset method
        cleanDataset.clean_dataset(args[0], args[1]);

        // Record the end time
        long endTime = System.currentTimeMillis();

        // Calculate the execution time
        cleanDataset.execution_time = endTime - startTime;

        System.out.print("Finished cleaning " + cleanDataset.dataset_size + " bytes of data"); // Adjust unit if needed
        System.out.println(" in " + cleanDataset.execution_time + " milliseconds");
    }
}
