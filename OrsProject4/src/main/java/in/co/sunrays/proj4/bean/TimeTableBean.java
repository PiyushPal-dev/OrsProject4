package in.co.sunrays.proj4.bean;
import java.util.Date;

/**
 * TimeTableBean JavaBean encapsulates TimeTableBean attributes
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */
public class TimeTableBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	/**
	 * courseId of TimeTable
	 */
	private long courseId;

	/**
	 * courseName of TimeTable
	 */
	private String courseName;

	/**
	 * subjectId of TimeTable
	 */
	private long subjectId;

	/**
	 * subjectName of TimeTable
	 */
	private String subjectName;

	/**
	 * semester of TimeTable
	 */
	private String semester;

	/**
	 * examDate of TimeTable
	 */
	private Date examDate;

	/**
	 * time of TimeTable
	 */
	private String time;

	/**
     * accessor
     */
	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return id + "";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return examDate + "";
	}

}
