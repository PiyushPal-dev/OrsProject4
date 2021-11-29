package in.co.sunrays.proj4.bean;

/**
 *College JavaBean encapsulates College attributes
 */
public class CollegeBean extends BaseBean {

	/**
	 * Name Of College 
	 */
	private String name;

	/**
	 * Address Of College
	 */
	private String address;

	/**
	 * State Of College 
	 */
	private String state;

	/**
	 *  City Of College
	 */
	private String city;

	/**
	 * PhoneNo of College
	 */
	private String phoneNo;

	/**
	 * 
	 * accessor
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getKey() {
		return id + "";
	}


	public String getValue() {
		return name;
	}

}