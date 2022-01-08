package convert;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseLinks {

	public String vcode;
	public String episode;

	public static void main(String[] args) {
		for (int i = 1; i < 22; i++) {
			try {
				Document doc = Jsoup.parse(new File(String.format("excel/ttc_%s.html", i)), "UTF-8");

				Elements thumbs = doc.select("a[id=video-title]");

				for (Element el : thumbs) {
					String href = el.attr("href");
					String title = el.attr("title");

					ParseLinks item = new ParseLinks();

					Pattern p = Pattern.compile("(.*\\/watch\\?v=)([^\\&]*)(\\&.*)");
					Matcher m = p.matcher(href);
					if (m.find()) {
						item.vcode = m.group(2);
					} else {
						System.err.println("No match href");
					}

					p = Pattern.compile("(.*)(S\\d+[\\s-]*E\\d+)(.*)");
					m = p.matcher(title);
					if (m.find()) {
						item.episode = m.group(2).replaceAll("[-\\s]", "");
					} else {
						item.episode = title;
					}
					System.out.println(String.format("%d\t%s\t%s", i, item.vcode, item.episode));
				}
			} catch (IOException e) {
				System.err.println(e);
			}
		}

	}
}
