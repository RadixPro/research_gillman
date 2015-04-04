package nl.nvwoa.gillman.domain.calculation;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Component;

@Component
public class CalculationController {
    @Autowired
    private CalculationHandler calculationHandler;

    public void handleCalculation(final String fileIndicator) {
        calculationHandler.performCalculation(fileIndicator);
    }
}
