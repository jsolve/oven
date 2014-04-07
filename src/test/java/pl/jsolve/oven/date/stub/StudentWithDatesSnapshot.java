package pl.jsolve.oven.date.stub;

import java.util.Calendar;
import java.util.Date;

import pl.jsolve.oven.annotationdriven.annotation.Map;
import pl.jsolve.oven.annotationdriven.annotation.MappableTo;

@MappableTo(StudentWithDates.class)
public class StudentWithDatesSnapshot {

	@Map(to = "dateAsLong")
	private Date date;

	@Map(to = "date")
	private Calendar calendar;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}
}