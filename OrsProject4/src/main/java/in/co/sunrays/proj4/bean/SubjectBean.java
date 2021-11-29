package in.co.sunrays.proj4.bean;


/**
 * Subject JavaBean encapsulates Subject attributes
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */
public class SubjectBean extends BaseBean {
	/**
	 * Name of Subject
	 */
	private String name;
	/**
	 * description of Subject
	 */
	private String description;
	/**
	 * courseId of Subject
	 */
	private long courseId;
	/**
	 * courseName of Subject
	 */
	private String courseName;

	/**
     * accessor
     */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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

	public String getKey() {
		return id + "";

	}

	public String getValue() {
		return name + "";
	}

}
