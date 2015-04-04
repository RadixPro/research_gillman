package nl.nvwoa.gillman.model;

import java.util.List;

public class CalculationResultCollection {
    private List<FullChart> allFullCharts;
    private String description;


    public List<FullChart> getAllFullCharts() {
        return allFullCharts;
    }

    public void setAllFullCharts(final List<FullChart> allFullCharts) {
        this.allFullCharts = allFullCharts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}