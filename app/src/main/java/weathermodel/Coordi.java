package weathermodel;

/**
 * @author Sagie Lev, Ohad Cohen, Shiran Shem-Tov
 *
 *         A class representing Coordinates of the city properties defined
 *         according to OpenWeatherMap.org
 */
public class Coordi
{
	private String lon;
	private String lat;

	/**
	 * Coord object constructor receiving two parameters
	 * 
	 * @param lon
	 *            Describes the lon of the Coord
	 * @param lat
	 *            Describes the lat of the Coord
	 */
	public Coordi(String lon, String lat)
	{
		super();
		setlat(lat);
		setlon(lon);
	}

	/**
	 * A get property for the lon property
	 * 
	 * @return A string that describes the City geo location, lon
	 */
	public String getlon()
	{
		return lon;
	}

	/**
	 * A set property for the lon property
	 * 
	 * @param lon
	 */
	public void setlon(String lon)
	{
		this.lon = lon;
	}

	/**
	 * A get property for the lat property
	 * 
	 * @return A string that describes the City geo location, lon
	 */
	public String getlat()
	{
		return lat;
	}

	/**
	 * A set property for the lat property
	 * 
	 * @param lat
	 */
	public void setlat(String lat)
	{
		this.lat = lat;
	}

	/**
	 * A Method that returns a string that describes the Coord object
	 */
	@Override
	public String toString()
	{
		return "[" + getlon() + "," + getlat() + "]";
	}
}