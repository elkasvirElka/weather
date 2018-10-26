package weathermodel;

/**
 * @author Sagie Lev, Ohad Cohen, Shiran Shem-Tov
 *
 *         A class representing Wind properties defined according to
 *         OpenWeatherMap.org
 */
public class Wind
{
	private String speed;
	private String deg;

	/**
	 * Wind object constructor receiving two parameters
	 * 
	 * @param speed
	 *            Describes the speed of the Wind
	 * @param deg
	 *            Describes the direction of the wind
	 */
	public Wind(String speed, String deg)
	{
		super();
		setSpeed(speed);
		setDeg(deg);
	}
	/**
	 * A get property for the wind speed parameter
	 * 
	 * @return an object that describes the speed
	 */
	public String getSpeed()
	{
		return speed;
	}

	/**
	 * A set property for the wind speed parameter
	 * 
	 * @param speed
	 *            Sets the wind speed received from the restful web service XML
	 *            parsing
	 */
	public void setSpeed(String speed)
	{
		this.speed = speed;
	}

	/**
	 * A get property for the wind direction parameter
	 * 
	 * @return an object that describes the direction
	 */
	public String getDeg()
	{
		return deg;
	}

	/**
	 * A set property for the wind direction parameter
	 * 
	 * @param deg
	 *            Sets the wind direction received from the restful web service
	 *            XML parsing
	 */
	public void setDeg(String deg)
	{
		this.deg = deg;
	}

	/**
	 * A Method that returns a string that describes the wind object
	 */
	@Override
	public String toString()
	{
		return getSpeed() + ", " + getDeg();
	}
}