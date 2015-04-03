package nl.nvwoa.gillman.domain.familyselection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SelectionController {
    @Autowired
    private SelectionHandler handler;

    public void handleSelection(String fileIndicator) {
        handler.performSelection(fileIndicator);
    }
}

