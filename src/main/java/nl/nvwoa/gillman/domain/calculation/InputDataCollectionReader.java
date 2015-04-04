package nl.nvwoa.gillman.domain.calculation;

import nl.nvwoa.gillman.model.InputData;
import nl.nvwoa.gillman.model.InputDataCollection;
import nl.nvwoa.gillman.util.Conversions;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class InputDataCollectionReader {
    public InputDataCollection readInputData(final String filename) {
        final InputDataCollection inputDataCollection = new InputDataCollection();
        try {
            FileReader reader = new FileReader(filename);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = null;
            try {
                jsonObject = (JSONObject) jsonParser.parse(reader);
                inputDataCollection.setDescription((String) jsonObject.get("description"));
            } catch (org.json.simple.parser.ParseException e) {
                // FIXME Auto-generated catch block
            }
            assert jsonObject != null;
            JSONArray inputs = (JSONArray) jsonObject.get("allInputData");
            Iterator<JSONObject> i = inputs.iterator();
            List<InputData> allRecords = new ArrayList<>();
            while (i.hasNext()) {
                JSONObject innerObj = i.next();
                InputData inputDataRecord = new InputData();
                inputDataRecord.setDescription((String) innerObj.get("description"));
                inputDataRecord.setGeographicLatitude((Double) innerObj.get("geographicLatitude"));
                inputDataRecord.setGeographicLongitude((Double) innerObj.get("geographicLongitude"));
                inputDataRecord.setGroupId("");
                inputDataRecord.setGroupMemberType((String) innerObj.get("description"));
                inputDataRecord.setId((String) innerObj.get("id"));
                inputDataRecord.setName("");
                inputDataRecord.setSimpleDateTime(Conversions.constructSimpleDateTime((JSONObject) innerObj.get("simpleDateTime")));
                allRecords.add(inputDataRecord);
            }
            inputDataCollection.setAllInputData(allRecords);
            System.out.println("Size of fullSet :" + allRecords.size());
        } catch (FileNotFoundException e) {
            // FIXME Auto-generated catch block
            System.out.println("Exception : file not found");
        } catch (IOException e) {
            System.out.println("Exception : io exception");
        }
        return inputDataCollection;
    }
}