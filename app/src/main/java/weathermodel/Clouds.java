package weathermodel;

/**
 * @author Sagie Lev, Ohad Cohen, Shiran Shem-Tov
 *
 *         A class representing Clounds properties defined according to
 *         OpenWeatherMap.org
 */
public class Clouds
{
	private String all;

	/**
	 * Clouds object constructor receiving two parameters
	 * 
	 * @param all
	 *            Describes the Name of the clouds
	 */
	public Clouds(String all)
	{
		super();
		this.all = all;
	}

	/**
	 * A get property for the value of the clouds
	 * 
	 * @return a string that describes the weather's Cloudiness
	 */
	public String getValue()
	{
		return all;
	}

	/**
	 * A set property for the value of the clouds
	 * 
	 * @param value
	 */
	public void setValue(String value)
	{
		this.all = all;
	}


	/**
	 * A Method that returns a string that describes the Clouds object
	 */
	@Override
	public String toString()
	{
		return getValue();
	}
}