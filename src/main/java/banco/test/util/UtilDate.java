package banco.test.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class UtilDate {


	public static long getDifferencesDays(LocalDate beginDate, LocalDate endDate) {

		return ChronoUnit.DAYS.between(beginDate, endDate);
	}

	public static LocalDate getLongToLocalDate(Long lastAccess) {

		return Instant.ofEpochSecond(lastAccess)
				.atZone(ZoneId.systemDefault()).toLocalDate();

	}
}