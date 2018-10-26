package weathermodel;

import java.util.Date;

/**
 * @author Sagie Lev, Ohad Cohen, Shiran Shem-Tov
 *
 *         A class representing City properties defined according to
 *         OpenWeatherMap.org
 */
public class WeatherData
{
	private Date dt;
	private String dt_txt;
	private String id;
	private String name;
	//private String country;
	private Coordi coord;
	private Clouds clouds;
	private Main main;
	private Weather weather;
	private Wind wind;
	private Sys sys;

	public WeatherData() {
	}

	/**
	 * City object constructor receiving two parameters
	 * 
	 * @param ID
	 *            Describes the id of the city
	 * @param name
	 *            Describes the name of the city
	 */
/*	public WeatherData(String ID, String name, Coordi coord, Clouds clouds, Main main, Weather weather, Wind wind, Sys sys)
	{
		setID(ID);
		setName(name);
	}*/

	/**
	 * A get property for the city ID property
	 * 
	 * @return A string that describes the city id
	 */
	public String getID()
	{
		return id;
	}

	/**
	 * A set property for the city ID property
	 * 
	 * @param id
	 */
	public void setID(String id)
	{
		this.id = id;
	}

	/**
	 * A get property for the city name property
	 * 
	 * @return A string that describes the city name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * A set property for the city name property
	 * 
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}


	/**
	 * A get property for the Coordinates (Coord) property
	 *
	 * @return An object that describes the City geo location.
	 */
	public Coordi getCoordinates()
	{
		return coord;
	}

	/**
	 * A set property for the Coordinates (Coord) property
	 * 
	 * @param coordinates
	 */
	public void setCoordinates(Coordi coordinates)
	{
		this.coord = coordinates;
	}

	/**
	 * A get property for Sun property
	 * 
	 * @return An object that describes the sun object
	 */
	public Sys getSys()
	{
		return sys;
	}

	/**
	 * A set property for the sun object
	 * 
	 * @param sys
	 */
	public void setSys(Sys sys)
	{
		this.sys = sys;
	}

	/**
	 * A Method that returns a string that describes the city object
	 */
	/**
	 * A get property for Main property
	 *
	 * @return An object that describes the main object
	 */
	public Main getMain()
	{
		return main;
	}

	/**
	 * A set property for the sun object
	 *
	 * @param main
	 */
	public void setMain(Main main)
	{
		this.main = main;
	}


	//Weather
	public Weather getWeather()
	{
		return weather;
	}


	public void setWeather(Weather weather)
	{
		this.weather = weather;
	}

	public Date getDt() {
		return dt;
	}

	public void setDt(Date dt) {
		this.dt = dt;
	}

	public String getDt_txt() {
		return dt_txt;
	}

	public void setDt_txt(String dt_txt) {
		this.dt_txt = dt_txt;
	}

	public Wind getWind() {
		return wind;
	}

	public void setWind(Wind wind) {
		this.wind = wind;
	}

	/**
	 * A Method that returns a string that describes the city object
	 */

}