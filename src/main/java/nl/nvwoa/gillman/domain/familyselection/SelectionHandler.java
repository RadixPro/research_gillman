package nl.nvwoa.gillman.domain.familyselection;


import java.util.ArrayList;
import java.util.List;

import nl.nvwoa.gillman.domain.dataconversion.InputDataCollection2JsonConverter;
import nl.nvwoa.gillman.model.InputData;
import nl.nvwoa.gillman.model.InputDataCollection;
import nl.nvwoa.gillman.util.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SelectionHandler {
    @Autowired
    private InputDataCollection2JsonConverter converter;
    @Autowired
    private SelectionDataReader dataReader;
    private int familySequenceId = 0;
    private final List<InputData> selectedInputData = new ArrayList<>();

    public void performSelection(final String fileIndicator) {
        final String unselectedFilename = constructUnselectedFilename(fileIndicator);
        final String selectedFilename = constructSelectedFilename(fileIndicator);
        final List<InputData> allInputData = dataReader.readData(unselectedFilename);
        selectFamilies(allInputData);
        writeInputData(selectedFilename);
    }

    private void addToSelectedInputData(final List<InputData> currentInputData) {
        familySequenceId++;
        for (InputData actualInputdata : currentInputData) {
            actualInputdata.setGroupId(Integer.toString(familySequenceId));
            selectedInputData.add(actualInputdata);
        }
    }

    
    private String constructSelectedFilename(final String fileIndicator) {
        return Dictionary.PATH_TO_DATA + fileIndicator + Dictionary.SELECTED_EXTENSION;
    }

    
    private String constructUnselectedFilename(final String fileIndicator) {
        return Dictionary.PATH_TO_DATA + fileIndicator + Dictionary.JSON_EXTENSION;
    }

    private boolean isValidFamily(final List<InputData> familyInputData) {
        if (familyInputData.size() < 3) { // at least father, mother and child
            return false;
        }
        if (!familyInputData.get(0).getGroupMemberType().equalsIgnoreCase("F")) { // first record should be father
            return false;
        }
        if (!familyInputData.get(1).getGroupMemberType().equalsIgnoreCase("M")) { // second record should be mother
            return false;
        }
        for (int i = 2; i < familyInputData.size(); i++) {
            if (!(familyInputData.get(i).getGroupMemberType().equalsIgnoreCase("S") || familyInputData.get(i).getGroupMemberType().equalsIgnoreCase("D"))) {
                return false; // should never happen:
            }
        }
        return true;
    }

    
    private List<InputData> selectFamilies(final List<InputData> allInputData) {
        List<InputData> currentInputData = new ArrayList<>();
        for (InputData inputData : allInputData) {
            if (inputData.getGroupMemberType().equalsIgnoreCase("F")) {
                if (isValidFamily(currentInputData)) {
                    addToSelectedInputData(currentInputData);
                }
                currentInputData = new ArrayList<>();
                currentInputData.add(inputData);
            } else if (inputData.getGroupMemberType().equalsIgnoreCase("M")) {
                if (currentInputData.size() == 1) {
                    currentInputData.add(inputData);
                } else {
                    if (isValidFamily(currentInputData)) {
                        addToSelectedInputData(currentInputData);
                    }
                    currentInputData = new ArrayList<>();
                    currentInputData.add(inputData);
                }
            } else {
                currentInputData.add(inputData);
            }
        }
        // check last possible family
        if (isValidFamily(currentInputData)) {
            addToSelectedInputData(currentInputData);
        }
        return currentInputData;
    }

    private void writeInputData(final String filename) {
        final InputDataCollection inputDataCollection = new InputDataCollection();
        inputDataCollection.setAllInputData(selectedInputData);
        inputDataCollection.setDescription("Selections defined");
        converter.convert(filename, inputDataCollection);
    }
}
