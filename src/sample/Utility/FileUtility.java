package sample.Utility;
/*
 * This class used for defining operations on File
 *
 * @author Sourabh Kolekar
 */

import java.io.*;
import java.util.ArrayList;

public class FileUtility {
    /**
     * Reads data from file returning the lines as a list, or null if error
     *
     * @param fileName A String which contains the name of the file to be read
     * @return ArrayList<String>  A list of Strings which contains data read from file
     */
    public static ArrayList<String> readFromFile(String fileName) throws IOException {
        ArrayList<String> data = new ArrayList<>();
        BufferedReader in = new BufferedReader(new FileReader(fileName));

        String temp = in.readLine().trim().toLowerCase();
        while (temp != null) {
            data.add(temp);
            temp = in.readLine();
        }
        in.close();
        return data;
    }

    /**
     * Write data to file
     *
     * @param fileName A String which contains the name of the file to be written
     * @param data     An array List of Strings which contains data to be written to the file
     */
    public static void writeToFile(String fileName, ArrayList<String> data) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, true))) {
            for (String s : data) {
                pw.println(s);
            }
        } catch (IOException e) {
            System.out.println("Exception while file write:" + e);
        }
    }

    /**
     * clears the content in the file
     *
     * @param fileName file path/ name to clear the content
     * @throws FileNotFoundException {@link FileNotFoundException} occurs if file is not found at the location
     */
    public static void clearTheFile(String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        pw.close();
    }

    /**
     * writes all given data to the provided file
     *
     * @param stringData path of the file to be written
     * @throws IOException {@link IOException} in case of failure with file operations
     */
    public static void writeDataToStringArray(String fileName, ArrayList<String> stringData) throws IOException {
        FileUtility.writeToFile(fileName, stringData);
    }

    /**
     * Logs the error messages received by the program
     *
     * @param errorMessage Error message sent by the program
     */
    public static void errorLogger(String fileName, String errorMessage) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, true))) {
            pw.println(errorMessage);
        } catch (IOException e) {
            System.out.println("Exception while file write:" + e);
        }
    }
}
