package in.co.sunrays.proj4.bean;


/**
 * Course JavaBean encapsulates Course attributes
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */
public class CourseBean extends BaseBean {

	/**
	 * Name of Course
	 */
	private String Name;

	/**
	 * description of Course
	 */
	private String description;
	/**
	 * duration of Course
	 */
	private String duration;
/**
 * accessor
 */
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String descreption) {
		this.description = descreption;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getKey() {
		return id + "";
	}

	public String getValue() {
		return Name;
	}
}

