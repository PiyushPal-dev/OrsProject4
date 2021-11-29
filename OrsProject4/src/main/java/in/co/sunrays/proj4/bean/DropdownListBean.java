package in.co.sunrays.proj4.bean;


/**
 *
 * DropdownList interface is implemented by Beans those are used to create drop
 * down list on HTML pages
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */

public interface DropdownListBean {

   
	/**
	 * Returns Key   of list  element
	 * @return  key
	 */
	public String getKey();

	/**
	 * Returns display text of list element 
	 * @return value
	 */
	public String getValue();

}