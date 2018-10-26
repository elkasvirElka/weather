package weathermodel;

/**
 * @author Sagie Lev, Ohad Cohen, Shiran Shem-Tov
 *
 *         A class representing Sun properties defined according to
 *         OpenWeatherMap.org
 */
public class Sys
{
	private String sunrise;
	private String sunset;
	private String country;

	/**
	 * Sun object constructor receiving two parameters
	 * 
	 * @param sunrise
	 *            Describes the sun sunrise time
	 * @param sunset
	 *            Describes the sunset time
	 */
	public Sys(String sunrise, String sunset, String country)
	{
		super();
		setRise(sunrise);
		setSet(sunset);
		setCountry(country);
	}

	/**
	 * A get property for the sun sunrise time parameter
	 * 
	 * @return returns a string that describes the time of sun sunrise
	 */
	public String getRise()
	{
		return sunrise.replace('T', ' ');
	}

	/**
	 * A set property for the sun sunrise time parameter
	 */
	public void setRise(String sunrise)
	{
		this.sunrise = sunrise;
	}
	/**
	 * A set property for the  country
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * A get property for the sun set time parameter
	 * 
	 * @return returns a string that describes the time of sun set
	 */
	public String getSet()
	{
		return sunset.replace('T', ' ');
	}

	/**
	 * A set property for the sun set time parameter
	 */
	public void setSet(String sunset)
	{
		this.sunset = sunset;
	}

	/**
	 * A Method that returns a string that describes the Sun object
	 */
	@Override
	public String toString()
	{
		return "[Rise: " + getRise() + "] [Set: " + getSet() + "]";
	}

}