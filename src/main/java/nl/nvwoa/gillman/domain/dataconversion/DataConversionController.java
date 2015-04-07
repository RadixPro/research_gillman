package nl.nvwoa.gillman.domain.dataconversion;

import nl.nvwoa.gillman.model.DataDefinition;
import nl.nvwoa.gillman.model.InputData;
import nl.nvwoa.gillman.model.InputDataCollection;
import nl.nvwoa.gillman.util.TextFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DataConversionController {
    private final String inputExtension = ".data";
    private final String outputExtension = ".json";
    @Autowired
    private CsvDataLineSelector csvDataLineSelector;
    @Autowired
    private CsvLineHandler csvLineHandler;
    @Autowired
    private Csv2InputDataConverter dataConverter;
    @Autowired
    private InputDataCollection inputDataCollection;
    @Autowired
    private InputDataCollection2JsonConverter jsonConverter;
    @Autowired
    private TextFileReader textFileReader;

    private static void createHeredityWithPL() {
    }

    public void createJsonFile(String fileIndicator) {
        DataDefinition dataDefinition = createDataDefinition();
        List<String> allLines = textFileReader.readLinesFromFile(createPathAndFilename(fileIndicator));
        List<String> dataLines = csvDataLineSelector.processFile(allLines, dataDefinition.getNrOfHeaderLinesToSkip());
        List<InputData> allInputData = new ArrayList<>();
        for (String dataLine : dataLines) {
            List<String> items = csvLineHandler.handleLine(dataLine, dataDefinition.getSeparator());
            allInputData.add(dataConverter.convert(items, dataDefinition));
        }
        inputDataCollection.setDescription(fileIndicator);
        inputDataCollection.setAllInputData(allInputData);
        createJson(fileIndicator, inputDataCollection);
    }

    private DataDefinition createDataDefinition() {
        DataDefinition dataDefinition = new DataDefinition();
        dataDefinition.setIndexOfId(0);
        dataDefinition.setIndexOfCategory(1);
        dataDefinition.setIndexOfDay(2);
        dataDefinition.setIndexOfMonth(3);
        dataDefinition.setIndexOfYear(4);
        dataDefinition.setIndexOfHour(5);
        dataDefinition.setIndexOfMinute(6);
        dataDefinition.setIndexOfSecond(7);
        // ignore 8, ci
        dataDefinition.setIndexOfTimeZone(9);
        dataDefinition.setIndexOfGeoLatFull(10);
        dataDefinition.setIndexOfGeoLongFull(11);
        dataDefinition.setIndexOfAdditionalLocation(12);
        dataDefinition.setGeoLatFullFormat("dddDmm");
        dataDefinition.setGeoLongFullFormat("ddDmm");
        dataDefinition.setSeparator("\t");
        dataDefinition.setNrOfHeaderLinesToSkip(2);
        return dataDefinition;
    }

    private void createJson(final String fileIndicator, final InputDataCollection inputDataCollection) {
        String jsonFilename = fileIndicator + outputExtension;
        jsonConverter.convert(jsonFilename, inputDataCollection);
    }

    private String createPathAndFilename(String fileIndicator) {
        return fileIndicator + inputExtension;
    }
}
