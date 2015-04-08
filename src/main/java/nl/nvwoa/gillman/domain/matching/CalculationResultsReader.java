package nl.nvwoa.gillman.domain.matching;

import nl.nvwoa.gillman.model.*;
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
public class CalculationResultsReader {
    public CalculationResultCollection readCalculationResultsData(final String filename) {
        final CalculationResultCollection resultCollection = new CalculationResultCollection();
        try {
            FileReader reader = new FileReader(filename);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = null;
            try {
                jsonObject = (JSONObject) jsonParser.parse(reader);
                resultCollection.setDescription((String) jsonObject.get("description"));
            } catch (org.json.simple.parser.ParseException e) {
                // FIXME Auto-generated catch block
            }
            assert jsonObject != null;
            JSONArray inputs = (JSONArray) jsonObject.get("allFullCharts");
            Iterator<JSONObject> i = inputs.iterator();
            List<FullChart> allFullCharts = new ArrayList<>();
            while (i.hasNext()) {
                JSONObject innerObj = i.next();
                FullChart chart = new FullChart();
                String id = ((String) innerObj.get("id"));
                chart.setId(id);
                chart.setCalculatedPositions((List<CalculatedPosition>) innerObj.get("calculatedPositions"));
                String familyId = ((String) innerObj.get("familyId"));
                chart.setFamilyId(familyId);
                JSONObject planetarySequenceInnerObj = (JSONObject) innerObj.get("planetarySequence");
                JSONArray bodies = (JSONArray) planetarySequenceInnerObj.get("bodiesInSequence");
                Iterator<String> j = bodies.iterator();
                Bodies[] bodiesInSequence = new Bodies[10];
                int counter = 0;
                while (j.hasNext()) {
                    String bodyName = j.next();
                    Bodies actualBody = null;
                    for (Bodies body : Bodies.values()) {
                        if (body.name().equalsIgnoreCase(bodyName)) {
                            actualBody = body;
                        }
                    }
                    bodiesInSequence[counter++] = actualBody;
                }
                PlanetarySequence planetarySequence = new PlanetarySequence();
                planetarySequence.setId(id);
                planetarySequence.setFamilyGroupId(familyId);
                planetarySequence.setBodiesInSequence(bodiesInSequence);
                chart.setPlanetarySequence(planetarySequence);
                chart.setFamilyRole((String) innerObj.get("familyRole"));

                allFullCharts.add(chart);
            }
            resultCollection.setAllFullCharts(allFullCharts);
        } catch (FileNotFoundException e) {
            // FIXME Auto-generated catch block
            System.out.println("Exception : file not found");
        } catch (IOException e) {
            System.out.println("Exception : io exception");
        }
        return resultCollection;
    }
}
