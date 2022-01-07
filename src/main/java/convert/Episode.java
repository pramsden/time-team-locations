package convert;

import java.util.Date;

public class Episode {
	public String id;
	public String season;
	public String episode;
	public String code;
	public String title;
	public String longTitle;
	public Location[] locations;
	public String[] themes;
	public Date date;
	public String youtube;
	
	@Override
	public String toString() {
		return code + " " + title + ", " + (date==null?"": date);
	}
}
