package weathermodel;

/**
 * 
 * @author Sagie Lev, Ohad Cohen, Shiran Shem-Tov
 *
 *         A class representing weather Pressure properties defined according to
 *         OpenWeatherMap.org
 */
public class Main
{
	private Integer temp;
	private String pressure;
	private String humidity;
	private String temp_min;
	private String temp_max;

	public Main(Integer temp, String pressure, String humidity, String temp_min, String temp_max) {
		this.temp = temp;
		this.pressure = pressure;
		this.humidity = humidity;
		this.temp_min = temp_min;
		this.temp_max = temp_max;
	}

	public Integer getTemp() {
		return temp;
	}

	public void setTemp(Integer temp) {
		this.temp = temp;
	}

	public String getPressure() {
		return pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getTemp_min() {
		return temp_min;
	}

	public void setTemp_min(String temp_min) {
		this.temp_min = temp_min;
	}

	public String getTemp_max() {
		return temp_max;
	}

	public void setTemp_max(String temp_max) {
		this.temp_max = temp_max;
	}
	/**
	 * A Method that returns a string that describes the wind object
	 */
	@Override
	public String toString()
	{

		return getTemp() + ", " + getPressure()+ ", " +
				getHumidity()+ ", " + getTemp_min()+ ", " +
				getTemp_max();
	}
}