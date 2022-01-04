package convert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Location {
	private final static Pattern DMS_PATTERN = Pattern.compile(
            "(-?)([0-9]{1,2})°([0-5]?[0-9])[′']([0-5]?[0-9]\\.?[0-9]*)[\"″]([NS])\\s*" +
            "(-?)([0-1]?[0-9]{1,2})°([0-5]?[0-9])[′']([0-5]?[0-9]\\.?[0-9]*)[\"″]([EW])");
	
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
			loc.text = a1[i].trim();
			loc.coordinates = a2[i].trim();

			
			double[] cc = convert(loc.coordinates);
			if(cc != null) {
			loc.lat = cc[0];
			loc.lng = cc[1];
			}
		}

		return locs;
	}

	private static double toDouble(Matcher m, int offset) {
        int sign = "".equals(m.group(1 + offset)) ? 1 : -1;
        double degrees = Double.parseDouble(m.group(2 + offset));
        double minutes = Double.parseDouble(m.group(3 + offset));
        double seconds = Double.parseDouble(m.group(4 + offset));
        int direction = "NE".contains(m.group(5 + offset)) ? 1 : -1;

        return sign * direction * (degrees + minutes / 60 + seconds / 3600);
    }

    private static double[] convert(String dms) {
    	if(dms == null || dms.trim().length() == 0)
    		return null;
    	
        Matcher m = DMS_PATTERN.matcher(dms.trim());

        if (m.matches()) {
            double latitude = toDouble(m, 0);
            double longitude = toDouble(m, 5);

            if ((Math.abs(latitude) > 90) || (Math.abs(longitude) > 180)) {
                throw new NumberFormatException("Invalid latitude or longitude");
            }

            return new double[] { latitude, longitude };
        } else {
        	String s = dms.replace("°", "").trim();
        	if (s.length() > 0) {
				String[] bits = s.split("[\\s,]");
				double sign = bits[0].endsWith("S") ? -1.0 : 1.0;
				double[] result = new double[2]; 
				result[0] = sign * Double.parseDouble(bits[0].substring(0, bits[0].length() - 1));
				sign = bits[1].endsWith("W") ? -1.0 : 1.0;
				result[1] = sign * Double.parseDouble(bits[1].substring(0, bits[1].length() - 1));
				return result;
			}
        	
            return null;
        }
    }
}
