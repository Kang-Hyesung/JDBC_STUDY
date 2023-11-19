package com.newlecture.app.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newlecture.app.entity.Notice;

public class NoticeService {
	String url = "jdbc:oracle:thin:@localhost:1521/xe";
	private String uid = "sys as sysdba";
	private String pwd = "7047";
	private String driver = "oracle.jdbc.driver.OracleDriver";
	
	// 읽기
	public List<Notice> getList(int page, String field, String query) throws SQLException, ClassNotFoundException{
		int start = 1 + (page - 1) * 10;
		int end = 10 * page;
		
		String sql = "SELECT * FROM NOTICE_VIEW WHERE "+ field+ " LIKE ? AND NUM BETWEEN ? AND ?";
				
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, uid, pwd);
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+query+"%");
		st.setInt(2, start);
		st.setInt(3, end);
		// 위에서 sql 전달하므로 밑에서는 빼야한다.
		ResultSet rs = st.executeQuery();
		
		// 반환하는 준비
		List<Notice> list = new ArrayList<Notice>(); 
		
		while(rs.next()) {
			
			
			int id = rs.getInt("id");
			String title = rs.getString("title");
			String writerid = rs.getString("writer_id");
			Date regDate = rs.getDate("regdate");
			String content = rs.getString("content");
			int hit = rs.getInt("hit");
			String files = rs.getString("FILES");
			
			Notice notice = new Notice(
							id,
							title,
							writerid,
							regDate,
							content,
							hit,
							files);
			
			list.add(notice);
		}
		rs.close();
		st.close();
		con.close();
		
		return list;
	}
	
	// 쓰기
	public int insert(Notice notice) throws ClassNotFoundException, SQLException {
		int id = notice.getId();
		String title = notice.getTitle();
		String writerid = notice.getWriterid();
		String content = notice.getContent();
		String files = notice.getFiles();
		
		String sql = "INSERT INTO notice (" + 
				"	 id,   " +
				"    title," + 
				"    writer_id," + 
				"    content," + 
				"    files" + 
				") VALUES (?, ? ,?, ?, ?)";
				
				
				
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, uid, pwd);
		
		//Statement st = con.createStatement();
		PreparedStatement st = con.prepareStatement(sql);
		// 각각의 자료형마다 세팅메소드가 다르다.
		st.setInt(1, id);
		st.setString(2, title);
		st.setString(3, writerid);
		st.setString(4, content);
		st.setString(5, files);
		
		int result = st.executeUpdate(); // 반영된 레코드의 건수 반환 이 경우에는 1 반환해야 한다.
		
		System.out.println(result);
		
		st.close();
		con.close();
		
		return result;
	}
	
	// 업데이트
	public int update(Notice notice) throws SQLException, ClassNotFoundException {
		String title = notice.getTitle();
		String content = notice.getContent();
		String files = notice.getFiles();
		int id = notice.getId();

		String sql = "UPDATE NOTICE " + // 칸 띄워주는거 중요하다
				"SET" + 
				"    TITLE = ?," + 
				"    CONTENT = ?," + 
				"    FILES = ?" + 
				"WHERE ID=?";
				
				
				
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, uid, pwd);
		
		//Statement st = con.createStatement();
		PreparedStatement st = con.prepareStatement(sql);
		// 각각의 자료형마다 세팅메소드가 다르다.
		st.setString(1, title);
		st.setString(2, content);
		st.setString(3, files);
		st.setInt(4, id);
		
		int result = st.executeUpdate(); // 반영된 레코드의 건수 반환 이 경우에는 1 반환해야 한다.
		
		System.out.println(result);
		
		st.close();
		con.close();
		
		return result;
	}
	
	// 삭제
	public int delete(int id) throws ClassNotFoundException, SQLException {
		String sql = "DELETE NOTICE WHERE ID=?";
							
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, uid, pwd);
		
		//Statement st = con.createStatement();
		PreparedStatement st = con.prepareStatement(sql);
		// 각각의 자료형마다 세팅메소드가 다르다.
		st.setInt(1, id);
		
		int result = st.executeUpdate(); // 반영된 레코드의 건수 반환 이 경우에는 1 반환해야 한다.
		
		System.out.println(result);
		
		st.close();
		con.close();
		
		return result;
	}
	
	//Scalar 값을 얻어오는 함수
	public int getCount() throws SQLException, ClassNotFoundException {
		int count = 0;
		
		String sql = "SELECT COUNT(ID) COUNT FROM NOTICE";
				
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, uid, pwd);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		if(rs.next())
			count = rs.getInt("COUNT");
			
		rs.close();
		st.close();
		con.close();
		
		return count;
	}
	
	
}
