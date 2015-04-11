package nl.nvwoa.gillman.domain.calculation;

import nl.nvwoa.gillman.model.CalculatedPosition;
import org.springframework.stereotype.Component;
import swisseph.DblObj;
import swisseph.SweConst;
import swisseph.SweDate;
import swisseph.SwissEph;

import java.io.File;

@SuppressWarnings("UnusedAssignment")
@Component
public class SEFrontend {
    private final String path = "se" + File.separator;
    private final SwissEph swissEph = new SwissEph(path);

    public double[] calcBodyLong(final SweDate sweDate, final int body, final double geoLon, final double geoLat) {
        double[] results = new double[6];
        swissEph.swe_set_topo(geoLon, geoLat, 0.0);
        swissEph.calc(sweDate.getJulDay(), body, createFiltersEcliptical(), results);
        return results;
    }

    public double[] calcBodyRA(final SweDate sweDate, final int body, final double geoLon, final double geoLat) {
        double[] results = new double[6];
        swissEph.swe_set_topo(geoLon, geoLat, 0.0);
        swissEph.calc(sweDate.getJulDay(), body, createFiltersEquatorial(), results);
        return results;
    }

    public double[] calcDomification(final SweDate sweDate, final double geoLat, final double geoLong) {
        double[] cuspPositions = new double[13];
        double[] ascMcPositions = new double[10];
        swissEph.swe_houses(sweDate.getJulDay(), createFiltersEcliptical(), geoLat, geoLong, 'Y', cuspPositions, ascMcPositions);
        return ascMcPositions;
    }

    public double[] calcNutationAndObliquity(final SweDate sweDate) {
        double[] values = new double[6];
        StringBuffer errors = new StringBuffer();
        swissEph.swe_calc(sweDate.getJulDay(), SweConst.SE_ECL_NUT, createFiltersEcliptical(), values, errors);
        return values;
    }

    public double calcRisingTime(final SweDate sweDate, final int body, final double geoLon, final double geoLat) {
        double arg0 = sweDate.getJulDay();
        int arg1 = body;
        StringBuffer arg2 = new StringBuffer("");
        int arg3 = SweConst.SEFLG_SWIEPH; // ephemeris flag
        int arg4 = SweConst.SE_CALC_RISE | SweConst.SE_BIT_DISC_CENTER | SweConst.SE_BIT_NO_REFRACTION;
        double[] arg5 = {geoLon, geoLat, 0.0};
        double arg6 = 0.0;
        double arg7 = 0.0;
        DblObj arg8 = new DblObj();
        StringBuffer arg9 = new StringBuffer();
        swissEph.swe_rise_trans(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
        return arg8.val;
    }

    private int createFiltersEcliptical() {
        int flags = 0;
        flags = flags | SweConst.SEFLG_SWIEPH;
        return flags;
    }

    private int createFiltersEquatorial() {
        int flags = 0;
        flags = flags | SweConst.SEFLG_SWIEPH;
        flags = flags | SweConst.SEFLG_EQUATORIAL;
        return flags;
    }
}
