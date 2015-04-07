package nl.nvwoa.gillman.domain.matching;


import nl.nvwoa.gillman.model.CalculationTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MatchingController {
    @Autowired
    private MatchingHandler matchingHandler;

    public void performMatches(final String fileIndicator, final CalculationTypes calculationType) {
        matchingHandler.handleMatches(fileIndicator, calculationType);
    }
}