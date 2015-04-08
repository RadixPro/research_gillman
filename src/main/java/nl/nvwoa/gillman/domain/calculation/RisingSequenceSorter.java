package nl.nvwoa.gillman.domain.calculation;

import nl.nvwoa.gillman.model.SortablePosition;
import nl.nvwoa.gillman.util.RangeUtil;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class RisingSequenceSorter {

    public List<SortablePosition> sortRisingSequence(final List<SortablePosition> positionsIn, final double referencePoint) {
        List<SortablePosition> positionsOut = positionsIn;
        for (SortablePosition position : positionsOut) {
            position.setRelativePosition(calculateRelativePosition(position, referencePoint));
        }
        Collections.sort(positionsOut, new Comparator<SortablePosition>() {
            @Override
            public int compare(final SortablePosition o1, final SortablePosition o2) {
                return Double.compare(o1.getRisingTime(), o2.getRisingTime());
            }
        });
        return positionsOut;
    }

    private double calculateRelativePosition(final SortablePosition sortablePosition, final double referencePoint) {
        double diff = sortablePosition.getPosition() - referencePoint;
        return RangeUtil.limitValueToRange(diff, 0.0, 360.0);
    }
}
