package nl.nvwoa.gillman;

import nl.nvwoa.gillman.domain.calculation.CalculationController;
import nl.nvwoa.gillman.domain.controlgroup.ControlGroupController;
import nl.nvwoa.gillman.domain.dataconversion.DataConversionController;
import nl.nvwoa.gillman.domain.familyselection.SelectionController;
import nl.nvwoa.gillman.domain.matching.MatchingController;
import nl.nvwoa.gillman.model.CalculationTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Frontend {
    @Autowired
    private DataConversionController dataConversionController;
    @Autowired
    private SelectionController selectionCOntroller;
    @Autowired
    private CalculationController calculationController;
    @Autowired
    private MatchingController matchingController;
    @Autowired
    private ControlGroupController controlGroupController;
    private String fileIndicator = "";
    private CalculationTypes calculationType;

    public void handleTask(String[] args) {
        readArguments(args);
        dataConversionController.createJsonFile(fileIndicator);
        System.out.println("Data converted to Json format");
        selectionCOntroller.handleSelection(fileIndicator);
        System.out.println("Selected valid families");
        controlGroupController.createControlGroup(fileIndicator);
        System.out.println("Created control group");
        calculationController.handleCalculation(fileIndicator, calculationType);
        System.out.println("Calculation completed");
        matchingController.performMatches(fileIndicator, calculationType);
        System.out.println("Matching completed.");
    }

    private void readArguments(String[] args) {
        if ((args.length < 2) || (args[0].length() < 1) || args[1].length() < 1) {
            System.out.println("Please restart and enter an argument for the file you want to use, and for the calculation type, e.g. 'B-2 long'.");
            System.exit(-1);
        }
        fileIndicator = args[0];
        calculationType = CalculationTypes.getCalculationTypeForAbbreviation(args[1]);
        if (calculationType ==  null) {
            throw new RuntimeException("Unknown calculation type : " + args[1]);
        }
    }
}
