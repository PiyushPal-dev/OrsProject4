package in.co.sunrays.proj4.model;

import in.co.sunrays.proj4.bean.CourseBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DatabaseException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.util.JDBCDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
/**
 * JDBC Implementation of Course Model
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public class CourseModel {

		private static Logger log = Logger.getLogger(CourseModel.class);

		 /**
	     * Find next PK of course
	     * 
	     * @throws DatabaseException
	     */

		public Integer nextPK() throws DatabaseException {
			log.debug("Model nextPK Started");
			Connection conn = null;
			int pk = 0;
			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_COURSE");
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
		 * Add Attribute of Course
		 * @param bean
		 * @throws ApplicationException
		 * @throws DuplicateRecordException
		 */
		public long add(CourseBean bean) throws ApplicationException, DuplicateRecordException {
			log.debug("Model add Started");
			System.out.println("add started");
			Connection conn = null;
			int pk = 0;

			CourseBean duplicateCourseName = findByName(bean.getName());

			if (duplicateCourseName != null) {
				throw new DuplicateRecordException("Course Name already exists");
			}
            System.out.println("progress");
			try {
				conn = JDBCDataSource.getConnection();
				pk = nextPK();
			
				// Get auto-generated next primary key
				conn.setAutoCommit(false); // Begin transaction
				System.out.println("prepare");
				System.out.println(pk);
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_COURSE VALUES(?,?,?,?,?,?,?,?)");
				pstmt.setInt(1, pk);
				pstmt.setString(2, bean.getName());
				pstmt.setString(3, bean.getDescription());
				pstmt.setString(4, bean.getDuration());
				pstmt.setString(5, bean.getCreatedBy());
				pstmt.setString(6, bean.getModifiedBy());
				pstmt.setTimestamp(7, bean.getCreatedDatetime());
				pstmt.setTimestamp(8, bean.getModifiedDatetime());
				
				pstmt.executeUpdate();
				System.out.println("inserted");
			
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
				throw new ApplicationException("Exception : Exception in add Course");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("Model add End");
			return pk;
		}
		 
		/**
		 * Delete Attribute of Course
		 * @param bean
		 * @throws ApplicationException
		 */
		public void delete(CourseBean bean) throws ApplicationException {
			log.debug("Model delete Started");
			Connection conn = null;
			try {
				conn = JDBCDataSource.getConnection();
				conn.setAutoCommit(false); // Begin transaction
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_COURSE WHERE ID=?");
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
				throw new ApplicationException("Exception : Exception in delete Course");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("Model delete Started");
		}
		 
		/**
		 * Find Attribute of course By name
		 * @param name
		 * @throws ApplicationException
		 */
		public CourseBean findByName(String name) throws ApplicationException {
			log.debug("Model findByName Started");
			StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE NAME=?");
			CourseBean bean = null;
			Connection conn = null;
			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, name);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					bean = new CourseBean();
					bean.setId(rs.getLong(1));
					bean.setName(rs.getString(2));
					bean.setDescription(rs.getString(3));
					bean.setDuration(rs.getString(4));
					bean.setCreatedBy(rs.getString(5));
					bean.setModifiedBy(rs.getString(6));
					bean.setCreatedDatetime(rs.getTimestamp(7));
					bean.setModifiedDatetime(rs.getTimestamp(8));

				}
				rs.close();
			} catch (Exception e) {
				log.error("Database Exception..", e);
				throw new ApplicationException("Exception : Exception in getting Course by Name");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("Model findByName End");
			return bean;
		}
		 
		/**
		 * Find Attribute of course By ID
		 * @param pk
		 * @throws ApplicationException
		 */
		public CourseBean findByPK(long pk) throws ApplicationException {
			log.debug("Model findByPK Started");
			StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE ID=?");
			CourseBean bean = null;
			Connection conn = null;
			try {

				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				pstmt.setLong(1, pk);

				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {

					bean = new CourseBean();

					bean.setId(rs.getLong(1));

					bean.setName(rs.getString(2));
					bean.setDescription(rs.getString(3));
					bean.setDuration(rs.getString(4));
					bean.setCreatedBy(rs.getString(5));
					bean.setModifiedBy(rs.getString(6));
					bean.setCreatedDatetime(rs.getTimestamp(7));
					bean.setModifiedDatetime(rs.getTimestamp(8));

				}

			} catch (Exception e) {
				log.error("Database Exception..", e);
				throw new ApplicationException("Exception : Exception in getting Course by pk");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("Model findByPK End");
			return bean;
		}

		/**
		 * Update Attribute of course
		 * @param bean
		 * @throws ApplicationException
		 * @throws DuplicateRecordException
		 */
		public void update(CourseBean bean) throws ApplicationException, DuplicateRecordException {
			log.debug("Model update Started");
			Connection conn = null;

			CourseBean beanExist = findByName(bean.getName());

			// Check if updated Course already exist
			if (beanExist != null && beanExist.getId() != bean.getId()) {

				throw new DuplicateRecordException("Course is already exist");
			}

			try {

				conn = JDBCDataSource.getConnection();

				conn.setAutoCommit(false); // Begin transaction
				PreparedStatement pstmt = conn.prepareStatement(
						"UPDATE ST_COURSE SET NAME=?,DESCRIPTION=?,DURATION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
				pstmt.setString(1, bean.getName());
				pstmt.setString(2, bean.getDescription());
				pstmt.setString(3, bean.getDuration());

				pstmt.setString(4, bean.getCreatedBy());
				pstmt.setString(5, bean.getModifiedBy());
				pstmt.setTimestamp(6, bean.getCreatedDatetime());
				pstmt.setTimestamp(7, bean.getModifiedDatetime());
				pstmt.setLong(8, bean.getId());
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
				throw new ApplicationException("Exception in updating Course ");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("Model update End");
		}

		/**
		 * Search College With Pagination
		 * @param bean
		 * @param pageNo
		 * @param pageSize
		 * @throws ApplicationException
		 */
		public List search(CourseBean bean, int pageNo, int pageSize) throws ApplicationException {
			log.debug("Model search Started");
			StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE 1=1");

			if (bean != null) {
				if (bean.getId() > 0) {
					sql.append(" AND id = " + bean.getId());
				}
				if (bean.getName() != null && bean.getName().trim().length() > 0) {
					sql.append(" AND NAME like '" + bean.getName() + "%'");
				}
				if (bean.getDescription() != null && bean.getDescription().length() > 0) {
					sql.append(" AND Description like '" + bean.getDescription() +"%'");
				}
				if (bean.getDuration() != null && bean.getDuration().length() >0&&bean.getDuration() !="") {
			
					sql.append(" AND duration like '"+ bean.getDuration()+"%'" );
					
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
					bean = new CourseBean();
					bean.setId(rs.getLong(1));
					bean.setName(rs.getString(2));
					bean.setDescription(rs.getString(3));
					bean.setDuration(rs.getString(4));
					bean.setCreatedBy(rs.getString(5));
					bean.setModifiedBy(rs.getString(6));
					bean.setCreatedDatetime(rs.getTimestamp(7));
					bean.setModifiedDatetime(rs.getTimestamp(8));
					list.add(bean);
				}
				rs.close();
			} catch (Exception e) {
				log.error("Database Exception..", e);
				throw new ApplicationException("Exception : Exception in search Course");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}

			log.debug("Model search End");
			return list;
		}

		
		public List search(CourseBean bean) throws ApplicationException {
			return search(bean, 0, 0);
		}

		
		public List list() throws ApplicationException {
			return list(0, 0);
		}
		/**
		 * Get List Of Course With pagination
		 * @param pageNo
		 *                         :Current Page No.
		 * @param pageSize
		 *                         :Size of Page
		 * @return list : List Of Course
		 * @throws ApplicationException
		 */
		public List list(int pageNo, int pageSize) throws ApplicationException {
			log.debug("Model list Started");
			ArrayList list = new ArrayList();
			StringBuffer sql = new StringBuffer("select * from ST_COURSE");
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
					CourseBean bean = new CourseBean();
					bean.setId(rs.getLong(1));
					bean.setName(rs.getString(2));
					bean.setDescription(rs.getString(3));
					bean.setDuration(rs.getString(4));
					bean.setCreatedBy(rs.getString(5));
					bean.setModifiedBy(rs.getString(6));
					bean.setCreatedDatetime(rs.getTimestamp(7));
					bean.setModifiedDatetime(rs.getTimestamp(8));
					list.add(bean);
				}
				rs.close();
			} catch (Exception e) {
				log.error("Database Exception..", e);
				throw new ApplicationException("Exception : Exception in getting list of users");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}

			log.debug("Model list End");
			return list;

		}

	
}
