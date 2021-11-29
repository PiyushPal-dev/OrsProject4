package in.co.sunrays.proj4.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *   Parent class of all Beans in application. It contains generic attributes.
 *   
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public abstract class BaseBean implements Serializable, DropdownListBean, Comparable<BaseBean> {

	/**
	 *  Non Business primary key
	 */
	protected long id;

	/**
	 * Contains USER ID who created this database record
	 */
	protected String createdBy;

	/**
	 * Contains USER ID who modified this database record
	 */
	protected String modifiedBy;

	/**
	 * Contains Created Timestamp of database record
	 */
	protected Timestamp createdDatetime;

	/**
	 *  protected Timestamp modifiedDatetime;
	 */
	protected Timestamp modifiedDatetime;

	 /**
     * accessor
     */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getCreatedDatetime() {
		return createdDatetime;
	}

	public void setCreatedDatetime(Timestamp createdDatetime) {
		this.createdDatetime = createdDatetime;
	}

	public Timestamp getModifiedDatetime() {
		return modifiedDatetime;
	}

	public void setModifiedDatetime(Timestamp modifiedDatetime) {
		this.modifiedDatetime = modifiedDatetime;
	}

	public int compareTo(BaseBean next) {
		return getValue().compareTo(next.getValue());
	}

/*	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}
*/
	}
