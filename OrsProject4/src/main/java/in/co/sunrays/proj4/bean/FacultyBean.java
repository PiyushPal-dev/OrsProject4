package in.co.sunrays.proj4.bean;
import java.util.Date;

/**
 * Faculty JavaBean encapsulates Faculty attributes
 * 
 * @author Strategy
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class FacultyBean extends BaseBean {

	/**
	 * collegeId of Faculty
	 */
	private long collegeId;
	/**
	 * courseId of Faculty
	 */
	private long courseId;
	/**
	 * courseName of Faculty
	 */
	private String courseName;
	/**
	 * firstName of Faculty
	 */
	private String firstName;
	/**
	 * lastName of Faculty
	 */
	private String lastName;
	/**
	 * subjectId of Faculty
	 */
	private long subjectId;
	/**
	 * subjectName of Faculty
	 */
	private String subjectName;
	/**
	 * collegeName of Faculty
	 */
	private String collegeName;
	/**
	 * qualification of Faculty
	 */
	private String qualification;
	/**
	 * emailId of Faculty
	 */
	private String emailId;
	/**
	 * dob of Faculty
	 */
	private Date dob;
	/**
	 * mobNo of Faculty
	 */
	private String mobileNo;

	/**
	 * gender of Faculty
	 */
	private String Gender;
	
	private static final long serialVersionUID = 1L;
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

	public long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}
	public String getKey() {
		return id + "";
	}

	public String getValue() {
		return firstName + " " + lastName;
	}

}
