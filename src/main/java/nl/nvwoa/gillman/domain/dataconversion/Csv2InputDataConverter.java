package nl.nvwoa.gillman.domain.dataconversion;

import nl.nvwoa.gillman.model.DataDefinition;
import nl.nvwoa.gillman.model.InputData;
import nl.nvwoa.gillman.model.SimpleDateTime;
import nl.nvwoa.gillman.model.SimpleDateTimeFactory;
import nl.nvwoa.gillman.util.Conversions;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Csv2InputDataConverter {
    private List<String> items;

    public InputData convert(final List<String> items, final DataDefinition dataDefinition) {
        this.items = items;
        InputData inputData = new InputData();
        inputData.setId(constructInputData(dataDefinition.getIndexOfId()));
        inputData.setName(constructInputData(dataDefinition.getIndexOfName()));
        inputData.setDescription(constructInputData(dataDefinition.getIndexOfCategory()));
        inputData.setGeographicLatitude(constructGeoLat(dataDefinition));
        inputData.setGeographicLongitude(constructGeoLong(dataDefinition));
        inputData.setSimpleDateTime(constructSimpleDateTime(dataDefinition));
        return inputData;
    }

    private double constructGeoLat(final DataDefinition dataDefinition) {
        String geoLatText = items.get(dataDefinition.getIndexOfGeoLatFull());
        return Conversions.convertGeoLat(geoLatText);
    }

    private double constructGeoLong(final DataDefinition dataDefinition) {
        String geoLongText = items.get(dataDefinition.getIndexOfGeoLongFull());
        return Conversions.convertGeoLong(geoLongText);
    }


    private SimpleDateTime constructSimpleDateTime(final DataDefinition dataDefinition) {
        String hour = items.get(dataDefinition.getIndexOfHour());
        String minute = items.get(dataDefinition.getIndexOfMinute());
        String second = items.get(dataDefinition.getIndexOfSecond());
        String day = items.get(dataDefinition.getIndexOfDay());
        String month = items.get(dataDefinition.getIndexOfMonth());
        String year = items.get(dataDefinition.getIndexOfYear());
        return SimpleDateTimeFactory.getSimpleDateTime(year, month, day, hour, minute, second);
    }


    private String constructInputData(final int index) {
        if (index >= 0) {
            return items.get(index);
        }
        return "";
    }
}