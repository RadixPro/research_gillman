package nl.nvwoa.gillman.domain.calculation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import nl.nvwoa.gillman.model.CalculationResultCollection;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

// TODO make a generic parent class for all json converters, and for objects that need to be written
@Component
public class CalculationResults2JsonConverter {
    // example: http://www.studytrails.com/java/json/jackson-create-json.jsp
    public void convert(final String filename, final CalculationResultCollection calculationResultCollection) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        try {
            File jsonFile = new File(filename);
            // mapper.writeValue(System.out, inputDataCollection);
            mapper.writeValue(jsonFile, calculationResultCollection);
        } catch (JsonGenerationException e) {
            // FIXME Auto-generated catch block
            System.out.println("JsonGenerationException" + e.getMessage());
        } catch (JsonMappingException e) {
            // FIXME Auto-generated catch block
            System.out.println("JsonMappingException" + e.getMessage());
        } catch (IOException e) {
            // FIXME Auto-generated catch block
            System.out.println("IOException" + e.getMessage());
        }
    }
}
