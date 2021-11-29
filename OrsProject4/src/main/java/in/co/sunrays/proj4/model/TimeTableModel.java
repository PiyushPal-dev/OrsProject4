package in.co.sunrays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.bean.CourseBean;
import in.co.sunrays.proj4.bean.SubjectBean;
import in.co.sunrays.proj4.bean.TimeTableBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DatabaseException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.exception.RecordNotFoundException;
import in.co.sunrays.proj4.util.JDBCDataSource;
/**
 * JDBC Implementation of Time Table Model
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class TimeTableModel {
	
	private static Logger log = Logger.getLogger(TimeTableModel.class);


	/**
	 * Find next PK of TimeTable
	 * 
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_TIMETABLE");
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
	 * Add a TimeTable
	 * semester validation according to course duration
	 * @param cb
	 * @param tb
	 * @throws RecordNotFoundException
	 */
	public void semChecker(CourseBean cb,TimeTableBean tb) throws RecordNotFoundException{
		String duration=cb.getDuration();
		
		if(duration.equalsIgnoreCase("3 years")&&tb.getSemester().equalsIgnoreCase("VII")||tb.getSemester().equalsIgnoreCase("VIII")
				){
			
		throw new RecordNotFoundException("This Semester is not available for this course");
			
		}if(duration.equalsIgnoreCase("2 years")&&tb.getSemester().equalsIgnoreCase("V")||tb.getSemester().equalsIgnoreCase("VI")
				||tb.getSemester().equalsIgnoreCase("VII")||tb.getSemester().equalsIgnoreCase("VIII")){
			throw new RecordNotFoundException("This Semester is not available for this course");
			
		}
	}
	
	/**
	 *  Add a TimeTable
	 * @param bean
	 * @return
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 * @throws RecordNotFoundException
	 */
	public long add(TimeTableBean bean) throws ApplicationException, DuplicateRecordException, RecordNotFoundException {

		log.debug("Model add started");
		Connection conn = null;
		
		CourseModel cModel = new CourseModel();
		CourseBean courseBean = cModel.findByPK(bean.getCourseId());
		bean.setCourseName(courseBean.getName());

		SubjectModel sModel = new SubjectModel();
		SubjectBean subjectBean = sModel.findByPK(bean.getSubjectId());
		bean.setSubjectName(subjectBean.getName());
		
		if(subjectBean!=null){
			if(subjectBean.getCourseId()!=bean.getCourseId()){
			
			throw new RecordNotFoundException("This Subject is not Available For This Course");
			}		}
		
	
		
		semChecker(courseBean, bean);

		TimeTableBean duplicatename = findTimeTableDuplicacy(bean.getCourseId(),bean.getSemester(),bean.getExamDate());
		TimeTableBean duplicatename1 = findTimeTableDuplicacy(bean.getCourseId(),bean.getSemester(),bean.getSubjectId());
		TimeTableBean duplicatename2 = findTimeTableDuplicacy(bean.getCourseId(),bean.getExamDate(),bean.getSubjectId());
	   
				int pk = 0;
				 if (duplicatename!=null ) {
						throw new DuplicateRecordException("Time Table already exist");
						}
				 
				 if(duplicatename1!=null){
					    throw new DuplicateRecordException("Time Table already exist");
					}
				 
				if (duplicatename2!=null ) {
						throw new DuplicateRecordException("Time Table already exist");
						}
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			// Get auto-generated next primary key
		
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement psmt = conn.prepareStatement("INSERT INTO ST_TIMETABLE VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			psmt.setInt(1, pk);
			psmt.setLong(2,bean.getCourseId());
			psmt.setString(3, bean.getCourseName());
			psmt.setLong(4, bean.getSubjectId());
			psmt.setString(5,bean.getSubjectName() );
			psmt.setString(6, bean.getSemester());
	        psmt.setDate(7, new java.sql.Date(bean.getExamDate().getTime()));
			psmt.setString(8, bean.getTime());
			psmt.setString(9, bean.getCreatedBy());
			psmt.setString(10, bean.getModifiedBy());
			psmt.setTimestamp(11, bean.getCreatedDatetime());
			psmt.setTimestamp(12, bean.getModifiedDatetime());

			psmt.executeUpdate();
			conn.commit();// End Transaction
			psmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	/**
	 * Delete a TimeTable
	 * 
	 * @param bean
	 * @throws ApplicationException
	 */
	public void delete(TimeTableBean bean) throws ApplicationException {
		log.debug("Model delete Started");
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement psmt = conn.prepareStatement("DELETE FROM ST_TIMETABLE WHERE ID=?");
			psmt.setLong(1, bean.getId());
			psmt.executeUpdate();
			conn.commit();
			psmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Ended");
	}

	/**
	 * Update a TimeTable
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 * @throws RecordNotFoundException 
	 * @throws @throws
	 *             DatabaseException
	 */

	public void update(TimeTableBean bean) throws ApplicationException, DuplicateRecordException, RecordNotFoundException {
		log.debug("Model update Started");
		Connection conn = null;

//check duplicacy

		TimeTableBean duplicatename = findTimeTableDuplicacy(bean.getCourseId(),bean.getSemester(),bean.getExamDate());
		TimeTableBean duplicatename1 = findTimeTableDuplicacy(bean.getCourseId(),bean.getSemester(),bean.getSubjectId());
		TimeTableBean duplicatename2 = findTimeTableDuplicacy(bean.getCourseId(),bean.getExamDate(),bean.getSubjectId());
	   
				 if (duplicatename!=null&&duplicatename.getId()!=bean.getId() ) {
						throw new DuplicateRecordException("Time Table already exist");
						}
				 
				 if(duplicatename1!=null&&duplicatename1.getId()!=bean.getId()){
					    throw new DuplicateRecordException("Time Table already exist");
					}
				 
				 if (duplicatename2!=null &&duplicatename2.getId()!=bean.getId()) {
						throw new DuplicateRecordException("Time Table already exist");
						}



		CourseModel cModel = new CourseModel();
		CourseBean courseBean = cModel.findByPK(bean.getCourseId());
		bean.setCourseName(courseBean.getName());

		SubjectModel sModel = new SubjectModel();
		SubjectBean subjectBean = sModel.findByPK(bean.getSubjectId());
		bean.setSubjectName(subjectBean.getName());
		
		if(subjectBean!=null){
			if(subjectBean.getCourseId()!=bean.getCourseId()){
			
			throw new RecordNotFoundException("This Subject is not Available For This Course");
			}		}
		semChecker(courseBean, bean);

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement psmt = conn.prepareStatement(
					"UPDATE ST_TIMETABLE SET COURSE_ID=?,COURSE_NAME=?,SUBJECT_ID=?,SUBJECT_NAME=?,SEMESTER=?,EXAM_DATE=?,EXAM_TIME=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");

			psmt.setLong(1, bean.getCourseId());
			psmt.setString(2, bean.getCourseName());
			psmt.setLong(3, bean.getSubjectId());
			psmt.setString(4, bean.getSubjectName());
			psmt.setString(5, bean.getSemester());
			psmt.setDate(6, new java.sql.Date(bean.getExamDate().getTime()));
			psmt.setString(7, bean.getTime());
			psmt.setString(8, bean.getCreatedBy());
			psmt.setString(9, bean.getModifiedBy());
			psmt.setTimestamp(10, bean.getCreatedDatetime());
			psmt.setTimestamp(11, bean.getModifiedDatetime());
			psmt.setLong(12, bean.getId());
			psmt.executeUpdate();
			conn.commit();
			psmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating TimeTable ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");

	}
    /**
	 * FindTimeTableDuplicacy
	 * @param courseId
	 * @param Semester
	 * @param examdate
	 * @return
	 * @throws ApplicationException
	 */
	public TimeTableBean findTimeTableDuplicacy(Long courseId, 
			String Semester, Date examdate) throws ApplicationException {
		log.debug("Method FindTimeTable of Model TimeTable started");
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_TIMETABLE WHERE COURSE_ID=?  AND SEMESTER=? AND EXAM_DATE = ?");
		TimeTableBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, courseId);
			pstmt.setString(2, Semester);
			pstmt.setDate(3, new java.sql.Date(examdate.getTime()));
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setSubjectId(rs.getLong(2));
				bean.setSubjectName(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));  
				bean.setTime(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException(
					"Exception : Exception in getting TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Method FindTimeTable of Model TimeTable End");
		return bean;

	}

	/**
	 * FindTimeTableDuplicacy
	 * @param courseId
	 * @param Semester
	 * @param subjectId
	 * @return
	 * @throws ApplicationException
	 */
	public TimeTableBean findTimeTableDuplicacy(Long courseId, 
			String Semester, Long subjectId) throws ApplicationException {
		log.debug("Method FindTimeTable of Model TimeTable started");
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_TIMETABLE WHERE COURSE_ID=?  AND SEMESTER=? AND SUBJECT_ID = ?");
		TimeTableBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, courseId);
			pstmt.setString(2, Semester);
			pstmt.setLong(3, subjectId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setSubjectId(rs.getLong(2));
				bean.setSubjectName(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));  
				bean.setTime(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}

			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException(
					"Exception : Exception in getting TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Method FindTimeTable of Model TimeTable End");
		return bean;

	}
	/**
	 * FindTimeTableDuplicacy
	 * @param courseId
	 * @param Semester
	 * @param subjectId
	 * @return
	 * @throws ApplicationException
	 */
	public TimeTableBean findTimeTableDuplicacy(Long courseId, 
			Date examDate, Long subjectId) throws ApplicationException {
		log.debug("Method FindTimeTable of Model TimeTable started");
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_TIMETABLE WHERE COURSE_ID=?  AND EXAM_DATE=? AND SUBJECT_ID = ?");
		TimeTableBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, courseId);
			pstmt.setDate(2, new java.sql.Date(examDate.getTime()));
			pstmt.setLong(3, subjectId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setSubjectId(rs.getLong(2));
				bean.setSubjectName(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));  
				bean.setTime(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}

			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException(
					"Exception : Exception in getting TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Method FindTimeTable of Model TimeTable End");
		return bean;

	}

	/**
	 * Find TimeTable by PK
	 * 
	 * @param pk
	 *            : get parameter
	 * @return bean
	 * @throws ApplicationException
	
	 */

	public TimeTableBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE ID=?");
		TimeTableBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseId(rs.getLong(2));
				bean.setCourseName(rs.getString(3));
				bean.setSubjectId(rs.getLong(4));
				bean.setSubjectName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));  
				bean.setTime(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}

			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting TimeTable by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");

		return bean;
	}

	/**
	 * Search TimeTable
	 * 
	 * @param bean
	 *            : Search Parameters
	 * @throws ApplicationException
	 * @throws RecordNotFoundException 
	 */

	public List search(TimeTableBean bean) throws ApplicationException, RecordNotFoundException {
		return search(bean, 0, 0);
	}

	/**
	 * Search TimeTable with pagination
	 * 
	 * @return list : List of TimeTable
	 * @param bean
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws ApplicationException
	 * @throws RecordNotFoundException 
	 */
	public List search(TimeTableBean bean, int pageNo, int PageSize) throws ApplicationException, RecordNotFoundException {
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("Select * From St_TimeTable Where 1=1");
			
			
			
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if(bean.getCourseId()>0) {
				
				sql.append(" AND COURSE_ID = "+bean.getCourseId());
			}
			if(bean.getCourseName()!=null && bean.getCourseName().length()>0)
			{
				sql.append(" AND COURSE_NAME like '" + bean.getCourseName()+"%'");
			}
			if(bean.getSubjectName()!=null && bean.getSubjectName().length()>0)
			{
				sql.append(" AND SUBJECT_NAME like '" + bean.getSubjectName()+"%'");
			}
			if(bean.getSemester()!=null && bean.getSemester().trim().length()>0)
			{
				sql.append(" AND SEMESTER like '" + bean.getSemester()+"'");
			}	
			if(bean.getExamDate()!=null) {
				
				//new java.sql.Date(bean.getExameDate().getTime())
				sql.append(" AND EXAM_DATE like '"+new SimpleDateFormat("yyyy-MM-dd").format(bean.getExamDate())+"'");
			}

			
		}
		
		// if pageSize1 is greater than zero then apply pagination
		if (PageSize > 0) {
			// calculate start record index
			pageNo = (pageNo - 1) * PageSize;
			sql.append(" limit " + pageNo + "," + PageSize);
		}
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql.toString());
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				
				bean=new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setSubjectId(rs.getLong(2));
				bean.setSubjectName(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				
				bean.setExamDate(rs.getDate(7));
				bean.setTime(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));

				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

    if(list==null || list.size()==0){
			
			throw new RecordNotFoundException("No Record Found");
		}
		log.debug("Model search End");
		return list;
	}

	/**
	 * Get List of TimeTable
	 * 
	 * @return list : List of TimeTable
	 * @throws ApplicationException
	 */

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Get List of TimeTable with pagination
	 * 
	 * @return list : List of TimeTable
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_timetable");
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
				TimeTableBean bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setSubjectId(rs.getLong(2));
				bean.setSubjectName(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));  
				bean.setTime(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	
		log.debug("Model list End");
		return list;
	}


}
