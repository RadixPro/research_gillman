package nl.nvwoa.gillman.domain.controlgroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ControlGroupController {

    @Autowired
    private ControlGroupHandler handler;

    public void createControlGroup(String fileIndicator) {
        handler.createControlGroup(fileIndicator);
    }
}
