package nl.nvwoa.gillman.domain.dataconversion;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles line with csv items.
 */
@Component
public class CsvLineHandler {
    /**
     * Splits csv line into separate items.
     *
     * @param lineIn    csvLine to handle
     * @param separator String that is used as separator. Should include quotes,if any.
     * @return separated items.
     */
    public List<String> handleLine(String lineIn, String separator) {
        List<String> results = new ArrayList<>();
        String[] lineItems = lineIn.split(separator);
        for (String item : lineItems) {
            results.add(item.trim());
        }
        return results;
    }
}
