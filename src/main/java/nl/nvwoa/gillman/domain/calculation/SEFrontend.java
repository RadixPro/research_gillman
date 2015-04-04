package nl.nvwoa.gillman.domain.calculation;

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
        int returnCode = swissEph.calc(sweDate.getJulDay(), body, createFiltersEcliptical(), results);
        return results;
    }

    public double[] calcBodyRA(final SweDate sweDate, final int body, final double geoLon, final double geoLat) {
        double[] results = new double[6];
        swissEph.swe_set_topo(geoLon, geoLat, 0.0);
        int returnCode = swissEph.calc(sweDate.getJulDay(), body, createFiltersEquatorial(), results);
        return results;
    }

    public double[] calcDomification(final SweDate sweDate, final double geoLat, final double geoLong) {
        double[] cuspPositions = new double[13];
        double[] asmcPositions = new double[10];
        int returnCode = swissEph.swe_houses(sweDate.getJulDay(), createFiltersEcliptical(), geoLat, geoLong, 'Y', cuspPositions, asmcPositions);
        return asmcPositions;
    }

    public double[] calcNutationAndObliquity(final SweDate sweDate) {
        double[] values = new double[6];
        StringBuffer errors = new StringBuffer();
        int returnCode = swissEph.swe_calc(sweDate.getJulDay(), SweConst.SE_ECL_NUT, createFiltersEcliptical(), values, errors);
        return values;
    }

    public double calcRisingTime(final SweDate sweDate, final int body, final double geoLon, final double geoLat) {
        double arg0 = sweDate.getJulDay();
        int arg1 = body;
        StringBuffer arg2 = new StringBuffer("");
        int arg3 = SweConst.SEFLG_SWIEPH; // ephemeris flag
        int arg4 = SweConst.SE_CALC_RISE | SweConst.SE_BIT_DISC_CENTER | SweConst.SE_BIT_NO_REFRACTION; // rsmi
        double[] arg5 = {geoLon, geoLat, 0.0};
        double arg6 = 0.0;
        double arg7 = 0.0;
        DblObj arg8 = new DblObj();
        StringBuffer arg9 = new StringBuffer();
        swissEph.swe_rise_trans(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
        return arg8.val;
        // int32 swe_rise_trans(
        // double tjd_ut, /* search after this time (UT) */
        // int32 ipl, /* planet number, if planet or moon */
        // char *starname, /* star name, if star */
        // int32 epheflag, /* ephemeris flag */
        // int32 rsmi, /* integer specifying that rise, set, orone of the two meridian transits is
        // wanted. see definition below */
        // double *geopos, /* array of three doubles containing
        // * geograph. long., lat., height of observer */
        // double atpress, /* atmospheric pressure in mbar/hPa */
        // double attemp, /* atmospheric temperature in deg. C */
        // double *tret, /* return address (double) for rise time etc. */
        // char *serr); /* return address for error message */
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
        // flags = flags | SweConst.SEFLG_TOPOCTR; // TODO check of parallax mee wordt genomen
        return flags;
    }
}
