package nl.nvwoa.gillman.domain.familyselection;


import nl.nvwoa.gillman.model.InputData;
import nl.nvwoa.gillman.util.Conversions;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class SelectionDataReader {

    public List<InputData> readData(final String filename) {
        final List<InputData> fullSet = new ArrayList<>();
        try {
            FileReader reader = new FileReader(filename);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = null;
            try {
                jsonObject = (JSONObject) jsonParser.parse(reader);
            } catch (org.json.simple.parser.ParseException e) {
                // FIXME Auto-generated catch block
            }
            assert jsonObject != null;
            JSONArray inputs = (JSONArray) jsonObject.get("allInputData");
            for (JSONObject innerObj : (Iterable<JSONObject>) inputs) {
                InputData inputDataRecord = new InputData();
                inputDataRecord.setDescription((String) innerObj.get("description"));
                inputDataRecord.setGeographicLatitude((Double) innerObj.get("geographicLatitude"));
                inputDataRecord.setGeographicLongitude((Double) innerObj.get("geographicLongitude"));
                inputDataRecord.setGroupId("");
                inputDataRecord.setGroupMemberType((String) innerObj.get("description"));
                inputDataRecord.setId((String) innerObj.get("id"));
                inputDataRecord.setName("");
                inputDataRecord.setSimpleDateTime(Conversions.constructSimpleDateTime((JSONObject) innerObj.get("simpleDateTime")));
                fullSet.add(inputDataRecord);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Exception : file not found");
        } catch (IOException e) {
            System.out.println("Exception : io exception");
        }
        return fullSet;
    }
}