package nl.nvwoa.gillman.domain.matching;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import nl.nvwoa.gillman.model.InputDataCollection;
import nl.nvwoa.gillman.model.MatchData;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;


@Component
public class MatchData2JsonConverter {
    // example: http://www.studytrails.com/java/json/jackson-create-json.jsp
    public void convert(final String filename, final MatchData matchData) {
        System.out.println("Writing mapped data to " + filename);
        System.out.println("Nr of records :" + matchData.getMatchSets().size());
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        try {
            File jsonFile = new File(filename);
            mapper.writeValue(jsonFile, matchData);
        } catch (IOException e) {
            // FIXME Auto-generated catch block
        }
    }
}