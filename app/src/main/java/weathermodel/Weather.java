package weathermodel;

/**
 * @author Sagie Lev, Ohad Cohen, Shiran Shem-Tov
 *
 *         A class representing Weather conditions defined according to
 *         OpenWeatherMap.org
 */
public class Weather
{
	private String main;
	private String description;
	private String icon;


	public Weather(String main, String icon, String description)
	{
		super();
		setMain(main);
		setIcon(icon);
		setDescription(description);
	}


	public void setMain(String main)
	{
		this.main = main;
	}


	public String getMain()
	{
		return main;
	}


	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}


	public String getIcon()
	{
		return icon;
	}


	public void setIcon(String icon)
	{
		this.icon = icon;
	}


}