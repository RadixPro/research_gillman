package nl.nvwoa.gillman.domain.dataconversion;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Reads data from a csv file.
 */
@Component
public class CsvDataLineSelector {
    private boolean checkForNonInitialLines2Skip;
    private String firstPartOfAdditionalLinesToSkip; // TODO more strings to check, should be a list.
    private int initialLinesToSkip;

    /**
     * Selects data lines from a set of csv-lines.
     *
     * @param inputLines         The set of csv-lines to check.
     * @param initialLinesToSkip The number of top lines to skip. Use zero for no lines to skip.
     * @return selected data lines.
     */
    public List<String> processFile(List<String> inputLines, int initialLinesToSkip) {
        this.initialLinesToSkip = initialLinesToSkip;
        checkForNonInitialLines2Skip = false;
        return checkLines(inputLines);
    }

    /**
     * Selects data lines from a set of csv-lines.
     *
     * @param inputLines                       The set of csv-lines to check.
     * @param initialLinesToSkip               The number of top lines to skip. Use zero for no lines to skip.
     * @param firstPartOfAdditionalLinesToSkip Start of strings to ignore
     * @return
     */
    public List<String> processFile(List<String> inputLines, int initialLinesToSkip, String firstPartOfAdditionalLinesToSkip) {
        this.initialLinesToSkip = initialLinesToSkip;
        this.firstPartOfAdditionalLinesToSkip = firstPartOfAdditionalLinesToSkip;
        checkForNonInitialLines2Skip = true;
        return checkLines(inputLines);
    }

    private List<String> checkLines(List<String> inputLines) {
        List<String> allDataLines = new ArrayList<>();
        int counter = 0;
        for (String inputLine : inputLines) {
            if ((++counter > initialLinesToSkip) && !(checkForNonInitialLines2Skip && inputLine.startsWith(firstPartOfAdditionalLinesToSkip))) {
                allDataLines.add(inputLine);
            }
        }
        return allDataLines;
    }
}
