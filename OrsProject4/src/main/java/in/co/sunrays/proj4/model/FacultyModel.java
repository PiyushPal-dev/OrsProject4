package in.co.sunrays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.bean.CollegeBean;
import in.co.sunrays.proj4.bean.CourseBean;
import in.co.sunrays.proj4.bean.FacultyBean;
import in.co.sunrays.proj4.bean.SubjectBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DatabaseException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.util.JDBCDataSource;

/**
 * JDBC implementation of faculty model
 * 
 * @author Strategy
 * @version 1.0
 * @Copyright (c) SunilOS
 *
 */

public class FacultyModel {
	private static Logger log = Logger.getLogger(FacultyModel.class);

	/**
	 * Find next PK of Faculty
	 * 
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM st_faculty");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk + 1;
	}


	/**
	 * Add a Faculty
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public long add(FacultyBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;
		CollegeModel collegeModel = new CollegeModel();	
		CollegeBean collegeBean = collegeModel.findByPK(bean.getCollegeId());
		bean.setCollegeName(collegeBean.getName());
		
		CourseModel courseModel = new CourseModel();	
		CourseBean courseBean = courseModel.findByPK(bean.getCourseId());
		bean.setCourseName(courseBean.getName());
		
		SubjectModel subjectModel = new SubjectModel();
		SubjectBean subjectBean = subjectModel.findByPK(bean.getSubjectId());
		bean.setSubjectName(subjectBean.getName());
		
		FacultyBean beanExist = findByEmail(bean.getEmailId());
		if (beanExist != null) { 
			  throw new DuplicateRecordException("Email already exists"); 
			  }
		 

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			// Get auto-generated next primary key
			//System.out.println(pk + " in ModelJDBC");
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO st_faculty VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getFirstName());
			pstmt.setString(3, bean.getLastName());
			pstmt.setString(4, bean.getGender());
			pstmt.setDate(5, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(6, bean.getQualification());
			pstmt.setString(7, bean.getEmailId());
			pstmt.setString(8, bean.getMobileNo());
			pstmt.setLong(9, bean.getCollegeId());
			pstmt.setString(10, bean.getCollegeName());
			pstmt.setLong(11, bean.getCourseId());
			pstmt.setString(12, bean.getCourseName());
			pstmt.setLong(13, bean.getSubjectId());
			pstmt.setString(14, bean.getSubjectName());
			pstmt.setString(15, bean.getCreatedBy());
			pstmt.setString(16, bean.getModifiedBy());
			pstmt.setTimestamp(17, bean.getCreatedDatetime());
			pstmt.setTimestamp(18, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in add Faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	
	/**
	 * Delete a Faculty
	 * @param bean
	 * @throws ApplicationException
	 */
	public void delete(FacultyBean bean) throws ApplicationException {
		log.debug("Model delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_FACULTY WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Started");
	}
	/**
	 * Find Faculty by Email
	 * @param emailId
	 * @throws ApplicationException
	 */
	public FacultyBean findByEmail(String emailId) throws ApplicationException {
		log.debug("Model findByEmail Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE EMAIL_ID=?");
		FacultyBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, emailId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setDob(rs.getDate(5));
				bean.setQualification(rs.getString(6));
				bean.setEmailId(rs.getString(7));
				bean.setMobileNo(rs.getString(8));
				bean.setCollegeId(rs.getLong(9));
				bean.setCollegeName(rs.getString(10));
				bean.setCourseId(rs.getLong(11));
				bean.setCourseName(rs.getString(12));
				bean.setSubjectId(rs.getLong(13));
				bean.setSubjectName(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));


			}
			rs.close();
		} catch (Exception e) {
			System.out.println("==catch findbyEmail==");
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting Faculty by Email");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByEmail End");
		return bean;
	}
	
	/**
	 * Find Faculty by pk
	 * @param pk :get parameter
	 * @return bean
	 * @throws ApplicationException
	 */
	public FacultyBean findByPK(long pk) throws ApplicationException {
		System.out.println("FIND started");
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE ID=?");
		FacultyBean bean = null;
		Connection conn = null;
		
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			bean = new FacultyBean();
			while (rs.next()) {
				
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setDob(rs.getDate(5));
				bean.setQualification(rs.getString(6));
				bean.setEmailId(rs.getString(7));
				bean.setMobileNo(rs.getString(8));
				bean.setCollegeId(rs.getLong(9));
				bean.setCollegeName(rs.getString(10));
				bean.setCourseId(rs.getLong(11));
				bean.setCourseName(rs.getString(12));
				bean.setSubjectId(rs.getLong(13));
				bean.setSubjectName(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting Faculty by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		System.out.println("find ended");
		return bean;
	}

	/**
	 * Update a Faculty
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public void update(FacultyBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;
		CollegeModel collegeModel = new CollegeModel();	
		CollegeBean collegeBean = collegeModel.findByPK(bean.getCollegeId());
		bean.setCollegeName(collegeBean.getName());
		
	   CourseModel courseModel = new CourseModel();	
		CourseBean courseBean = courseModel.findByPK(bean.getCourseId());
		bean.setCourseName(courseBean.getName());
		
		SubjectModel subjectModel = new SubjectModel();
		SubjectBean subjectBean = subjectModel.findByPK(bean.getSubjectId());
		bean.setSubjectName(subjectBean.getName());

		FacultyBean beanExist = findByEmail(bean.getEmailId());
		// Check if updated EmailId already exist
		if (beanExist != null && !(beanExist.getId() == bean.getId())) {
			throw new DuplicateRecordException("EmailId is already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("UPDATE ST_FACULTY SET COLLEGE_ID=?, COLLEGE_NAME=?, COURSE_ID=?, COURSE_NAME=?, SUBJECT_ID=?, SUBJECT_NAME=?, FIRST_NAME=?, LAST_NAME=?, QUALIFICATION=?, EMAIL_ID=?, DOB=?, MOBILE_NO=?, CREATED_BY=?, MODIFIED_BY=?, CREATED_DATETIME=?, MODIFIED_DATETIME=? WHERE ID=?");

			pstmt.setLong(1, bean.getCollegeId());
			pstmt.setString(2, bean.getCollegeName());
			pstmt.setLong(3, bean.getCourseId());
			pstmt.setString(4, bean.getCourseName());
			pstmt.setLong(5, bean.getSubjectId());
			pstmt.setString(6, bean.getSubjectName());
			
			
			pstmt.setString(7, bean.getFirstName());
			pstmt.setString(8, bean.getLastName());
			pstmt.setString(9, bean.getQualification());
			
			
			
			pstmt.setString(10, bean.getEmailId());
			pstmt.setDate(11, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(12, bean.getMobileNo());
			pstmt.setString(13, bean.getCreatedBy());
			pstmt.setString(14, bean.getModifiedBy());
			pstmt.setTimestamp(15, bean.getCreatedDatetime());
			pstmt.setTimestamp(16, bean.getModifiedDatetime());
			pstmt.setLong(17, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Faculty ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	/**
	 * Search Faculty
	 * 
	 * @param bean
	 *            : Search Parameters
	 * @throws ApplicationException
	 */

	public List search(FacultyBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * Search Faculty with pagination
	 * 
	 * @return list : List of Faculties
	 * @param bean
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * 
	 * @throws ApplicationException
	 */

	public List search(FacultyBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().trim().length() > 0) {
				sql.append(" AND FIRST_NAME like '" + bean.getFirstName() + "%'");
			}
			if (bean.getLastName() != null && bean.getLastName().trim().length() > 0) {
				sql.append(" AND LAST_NAME like '" + bean.getLastName() + "%'");
			}
			if (bean.getEmailId() != null && bean.getEmailId().length() > 0) {
				sql.append(" AND EMAIL like '" + bean.getEmailId() + "%'");
			}

			if (bean.getDob() != null && bean.getDob().getDate() > 0) {
				sql.append(" AND DOB = " + bean.getDob());
			}
			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" AND MOBILE_NO = " + bean.getMobileNo());
			}
			if (bean.getCollegeId() > 0) {
				sql.append(" AND COLLEGE_ID = " + bean.getCollegeId());
			}
			if (bean.getCourseId() > 0) {
				sql.append(" AND COURSE_ID = " + bean.getCourseId());
			}
			if (bean.getSubjectId() > 0) {
				sql.append(" AND SUBJECT_ID = " + bean.getSubjectId());
			}

		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setDob(rs.getDate(5));
				bean.setQualification(rs.getString(6));
				bean.setEmailId(rs.getString(7));
				bean.setMobileNo(rs.getString(8));
				bean.setCollegeId(rs.getLong(9));
				bean.setCollegeName(rs.getString(10));
				bean.setCourseId(rs.getLong(11));
				bean.setCourseName(rs.getString(12));
				bean.setSubjectId(rs.getLong(13));
				bean.setSubjectName(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search Faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
		return list;
	}

	/**
	 * Get List of Faculty
	 * 
	 * @return list : List of Faculty
	 * @throws ApplicationException
	 */

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Get List of Faculty with pagination
	 * 
	 * @return list : List of Faculty
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws ApplicationException
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_FACULTY");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				FacultyBean bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setDob(rs.getDate(5));
				bean.setQualification(rs.getString(6));
				bean.setEmailId(rs.getString(7));
				bean.setMobileNo(rs.getString(8));
				bean.setCollegeId(rs.getLong(9));
				bean.setCollegeName(rs.getString(10));
				bean.setCourseId(rs.getLong(11));
				bean.setCourseName(rs.getString(12));
				bean.setSubjectId(rs.getLong(13));
				bean.setSubjectName(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of Faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;

	}
}

