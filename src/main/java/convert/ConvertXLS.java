package convert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections4.functors.ForClosure;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertXLS {
	private static final int COL_ID = 0;
	private static final int COL_SEASON = 1;
	private static final int COL_EPISODE = 2;
	private static final int COL_CODE = 3;
	private static final int COL_TITLE = 4;
	private static final int COL_LONG_TITLE = 5;
	private static final int COL_LOCATION = 6;
	private static final int COL_COORDINATES = 7;
	private static final int COL_THEME = 8;
	private static final int COL_DATE = 9;

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		try (InputStream inp = new FileInputStream("excel/TimeTeamEpisodes.xlsx")) {
			Workbook wb = WorkbookFactory.create(inp);

			Sheet sheet = wb.getSheetAt(0);

			SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.US);

			List<Episode> list = new ArrayList<Episode>();

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);

				Episode epi = new Episode();
				list.add(epi);

				epi.id = row.getCell(COL_ID).toString();
				epi.season = row.getCell(COL_SEASON).toString();
				epi.episode = row.getCell(COL_EPISODE).toString();
				epi.code = row.getCell(COL_CODE).getStringCellValue();
				epi.title = row.getCell(COL_TITLE) == null ? "Untitled" : row.getCell(COL_TITLE).toString();
				epi.longTitle = row.getCell(COL_LONG_TITLE) == null ? null : row.getCell(COL_LONG_TITLE).toString();
				String d = row.getCell(COL_DATE) == null ? null : row.getCell(COL_DATE).toString();
				if (d != null && d.trim().length() > 0) {
					epi.date = sdf.parse(d);
				}
				epi.locations = Location.parse(row.getCell(COL_LOCATION).toString(),
						row.getCell(COL_COORDINATES).toString());
				epi.themes = row.getCell(COL_THEME) == null ? null : row.getCell(COL_THEME).toString().split(";");
				if (epi.themes != null) {
					for (int j = 0; j < epi.themes.length; j++) {
						epi.themes[j] = epi.themes[j].trim();
					}
				}

				System.out.println(epi);
			}

			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(list);
			FileWriter fw = new FileWriter("app/data.json");
			fw.write("var data = ");
			fw.write(json);
			fw.close();

		} catch (Exception e) {
			System.err.println(e);
		}

	}

}
