package org.luisa.miniwrangler.java.file.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

/**
 * Utility class to interact with Files.
 *
 * @author Luisa Pinto
 *
 */
public class FileUtils {

    /**
     * Return a file reader for the file with given its path
     *
     * @param filePath The target file path
     * @return a file reader
     * @throws FileNotFoundException if the file does not exist
     */
    public static Reader getBufferedReader(String filePath) throws FileNotFoundException {
        BufferedReader reader = null;
        if (filePath != null) {
            reader = new BufferedReader(new FileReader(filePath));
        }
        return reader;
    }
}
