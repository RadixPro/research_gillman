package nl.nvwoa.gillman.domain.dataconversion;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import nl.nvwoa.gillman.model.InputDataCollection;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

// TODO make a generic parent class for all json converters, and for objects that need to be written
@Component
public class InputDataCollection2JsonConverter {
    // example: http://www.studytrails.com/java/json/jackson-create-json.jsp
    public void convert(final String filename, final InputDataCollection inputDataCollection) {

        System.out.println("Writing to " + filename);
        System.out.println("Nr of records :" +inputDataCollection.getAllInputData().size());


        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        mapper.setSerializationInclusion(Include.NON_EMPTY);
        try {
            File jsonFile = new File(filename);
            mapper.writeValue(jsonFile, inputDataCollection);
        } catch (IOException e) {
            // FIXME Auto-generated catch block
        }
    }
}
