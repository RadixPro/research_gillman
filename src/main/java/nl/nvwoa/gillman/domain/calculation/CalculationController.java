package nl.nvwoa.gillman.domain.calculation;

import nl.nvwoa.gillman.model.CalculationTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalculationController {
    @Autowired
    private CalculationHandler calculationHandler;

    public void handleCalculation(final String fileIndicator, final CalculationTypes calculationType) {
        calculationHandler.performCalculation(fileIndicator, calculationType);
    }
}
