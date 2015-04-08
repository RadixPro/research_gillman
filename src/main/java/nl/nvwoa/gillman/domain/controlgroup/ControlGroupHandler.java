package nl.nvwoa.gillman.domain.controlgroup;

import nl.nvwoa.gillman.domain.calculation.InputDataCollectionReader;
import nl.nvwoa.gillman.domain.dataconversion.InputDataCollection2JsonConverter;
import nl.nvwoa.gillman.model.InputData;
import nl.nvwoa.gillman.model.InputDataCollection;
import nl.nvwoa.gillman.util.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ControlGroupHandler {

    @Autowired
    private InputDataCollectionReader inputReader;
    @Autowired
    private InputDataCollection2JsonConverter converter;
    private List<InputData> controlGroupInputData = new ArrayList<>();

    public void createControlGroup(String fileIndicator) {
        String selectedFilename = constructSelectedFilename(fileIndicator);
        String controlGroupFilename = constructControlGroupFilename(fileIndicator);
        final InputDataCollection inputDataCollection = inputReader.readInputData(selectedFilename);
        final List<InputData> allInputData = inputDataCollection.getAllInputData();
        System.out.println("Size of inputdata :" + allInputData.size());
        createControlGroupInputData(allInputData);
        System.out.println("Size of control group inputdata :" + controlGroupInputData.size());  // TODO 3 missing
        InputDataCollection collection = new InputDataCollection();
        collection.setDescription(fileIndicator + "-control-group");
        collection.setAllInputData(controlGroupInputData);
        converter.convert(controlGroupFilename, collection);


    }

    private void createControlGroupInputData(List<InputData> allInputData) {

        InputData father = new InputData();
        InputData mother = new InputData();
        InputData firstFather = new InputData();
        InputData firstMother = new InputData();
        List<InputData> originalChildren = new ArrayList<>();
        List<InputData> childrenToMove =new ArrayList<>();
        boolean firstFamily = false;
        boolean secondFamily = false;
        for (InputData inputData : allInputData) {
            if (inputData.getGroupMemberType().equals("F")) {
                if (firstFamily && secondFamily) {
                    addFamilyToControlGroup(father, mother, childrenToMove);
                } else {
                   firstFather = father;
                }
                childrenToMove = originalChildren;
                originalChildren = new ArrayList<>();
                father = inputData;
            } else if (inputData.getGroupMemberType().equals("M")) {
                mother = inputData;
                if (!firstFamily) {
                    firstMother = mother;
                    firstFather = father;
                    firstFamily = true;
                } else {
                    secondFamily = true;
                }
            } else {
                originalChildren.add(inputData);
            }
        }
        addFamilyToControlGroup(firstFather, firstMother, originalChildren);

    }


    private void addFamilyToControlGroup(InputData father, InputData mother,List<InputData> children) {
        controlGroupInputData.add(father);
        controlGroupInputData.add(mother);
        for (InputData child : children) {
            child.setGroupId(father.getGroupId());
            controlGroupInputData.add(child);
        }
    }

    private String constructControlGroupFilename(final String fileIndicator) {
        return Dictionary.PATH_TO_DATA + fileIndicator + Dictionary.CONTROL_GROUP_EXTENSION;
    }


    private String constructSelectedFilename(final String fileIndicator) {
        return Dictionary.PATH_TO_DATA + fileIndicator + Dictionary.SELECTED_EXTENSION;
    }


}
