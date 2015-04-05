package nl.nvwoa.gillman.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InputDataCollection {
    private List<InputData> allInputData;
    private String description;

    public List<InputData> getAllInputData() {
        return allInputData;
    }

    public void setAllInputData(final List<InputData> allInputData) {
        this.allInputData = allInputData;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
