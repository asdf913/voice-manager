import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.MutablePair;

public class Main2 {

	public static void main(final String[] args) throws IOException {
		//
		final List<String> lines = Files.readAllLines(Path.of("111.txt"), StandardCharsets.UTF_8);
		//
		Matcher matcher = null;
		//
		MutablePair<Integer, Integer> mp = null;
		//
		YearMonth ym1 = null, ym2;
		//
		for (int i = 0; lines != null && i < lines.size(); i++) {
			//
			if (mp == null) {
				mp = MutablePair.of(null, null);
			}
			//
			if ((matcher = Pattern.compile("^\"(\\d{4})/(\\d{2})").matcher(lines.get(i))) != null && matcher.matches()
					&& matcher.groupCount() > 1) {
				//
				ym1 = YearMonth.of(Integer.valueOf(matcher.group(1)), Integer.valueOf(matcher.group(2)));
				//
			} // if
				//
			if ((matcher = Pattern.compile("^(\\d{4})/(\\d{2})").matcher(lines.get(i))) != null && matcher.matches()
					&& matcher.groupCount() > 1) {
				//
				ym2 = YearMonth.of(Integer.valueOf(matcher.group(1)), Integer.valueOf(matcher.group(2)));
				//
				System.out.println(ym1 + " " + ym2 + " " + (ChronoUnit.MONTHS.between(ym1, ym2) + 1));
				//
			} // if
				//
		} // for
			//
	}

}
