package convert;

public class Location {
	public String text;
	public String coordinates;
	public double lat;
	public double lng;

	public static Location[] parse(String texts, String locations) throws Exception {
		String[] a1 = texts.split(";");
		String[] a2 = locations.split(";");
		if (a1.length != a2.length)
			throw new Exception("Different lengths");

		Location[] locs = new Location[a1.length];
		for (int i = 0; i < locs.length; i++) {
			Location loc = new Location();
			locs[i] = loc;
			loc.text = a1[i];
			loc.coordinates = a2[i];

			String s = loc.coordinates.replace("°", "").trim();
			if (s.length() > 0) {
				String[] bits = s.split(" ");
				double sign = bits[0].endsWith("N") ? 1.0 : -1.0;
				loc.lat = sign * Double.parseDouble(bits[0].substring(0, bits[0].length() - 1));
				sign = bits[1].endsWith("E") ? 1.0 : -1.0;
				loc.lng = sign * Double.parseDouble(bits[1].substring(0, bits[1].length() - 1));
			}
		}

		return locs;
	}
}
