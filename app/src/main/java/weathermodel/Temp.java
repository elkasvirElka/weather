package weathermodel;

/**
 * Created by 25fli on 24.10.2018.
 */

public class Temp {

    private Integer day;
    private Integer min;
    private Integer max;
    private Integer night;
    private Integer eve;
    private Integer morn;

    public Temp(Integer day, Integer min, Integer max, Integer night, Integer eve, Integer morn) {
        this.day = day;
        this.min = min;
        this.max = max;
        this.night = night;
        this.eve = eve;
        this.morn = morn;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getNight() {
        return night;
    }

    public void setNight(Integer night) {
        this.night = night;
    }

    public Integer getEve() {
        return eve;
    }

    public void setEve(Integer eve) {
        this.eve = eve;
    }

    public Integer getMorn() {
        return morn;
    }

    public void setMorn(Integer morn) {
        this.morn = morn;
    }
}
