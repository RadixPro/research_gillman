package nl.nvwoa.gillman.util;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Scanner;

/**
 * Reader for text files.
 * Based on an example at: http://www.javapractices.com/topic/TopicAction.do?Id=42
 */
@Component
public class TextFileReader {
    private final static Charset ENCODING = StandardCharsets.UTF_8;

    /**
     * Reads lines from a text file.
     *
     * @param pathAndFilename location of file to read.
     * @return text lines as read from the file.
     */
    public List<String> readLinesFromFile(final String pathAndFilename) {
        List<String> linesFromFile = new ArrayList<>();
        Path path = Paths.get(pathAndFilename);


        try (Scanner scanner = new Scanner(path, ENCODING.name())) {
            while (scanner.hasNextLine()) {
                linesFromFile.add(scanner.nextLine());
            }
        } catch (IOException e) {
            throw new MissingResourceException("File not found: " + pathAndFilename, this.getClass().getName(), pathAndFilename);
        }
        return linesFromFile;
    }
}