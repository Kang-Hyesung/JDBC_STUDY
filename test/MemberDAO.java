/*========================================
 	MemberDAO.java
 	- 데이터베이스 액션 처리 전용 클래스
 ========================================*/
package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


import com.util.DBConn;

public class MemberDAO
{
	private Connection conn;
	
	public Connection Connection()
	{
		conn = DBConn.getConnection();
		return conn;
	}
	
	// 직원 입력(테스트 완료)
	public int add(MemberDTO dto)
	{
		int result = 0;
		
		try
		{
			Statement stmt = conn.createStatement();
			
			String sql = String.format("INSERT INTO TBL_EMP (EMP_ID, EMP_NAME, SSN, IBSADATE, CITY_ID, TEL, BUSEO_ID, JIKWI_ID, BASICPAY, SUDANG) " + 
					"VALUES (EMPSEQ.NEXTVAL, '%s', '%s', '%s', %d, '%s', %d, %d, %d , %d)", 
					dto.getEmp_name(), dto.getSsn(), dto.getIbsadate(), dto.getCity_id(), dto.getTel(), dto.getBuseo_id(),  dto.getJikwi_id(), dto.getBasicpay(), dto.getSudang());
			
			result = stmt.executeUpdate(sql);
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		
		return result;
	}
	
	// 지역 가져오기
	public ArrayList<String> getCity()
	{
		ArrayList<String> cityList = new ArrayList<String>();
		try
		{
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT CITY_NAME FROM TBL_CITY";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				cityList.add(rs.getString(1));
			}
			stmt.close();
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return cityList;
	}
	
	// 부서 가져오기
	public ArrayList<String> getBuseo()
	{
		ArrayList<String> buseoList = new ArrayList<String>();
		try
		{
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT BUSEO_NAME FROM TBL_BUSEO";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				buseoList.add(rs.getString(1));
			}
			stmt.close();
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return buseoList;
	}
	
	// 직위와 기본급 가져오기
	public HashMap<String, Integer> getJikwi()
	{
		HashMap<String, Integer> jikwiList = new HashMap<String, Integer>();
		try
		{
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT JIKWI_NAME, MIN_BASICPAY FROM TBL_JIKWI";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				jikwiList.put(rs.getString(1), rs.getInt(2));
			}
			stmt.close();
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return jikwiList;
	}
	
	// 직위에 맞는 직위번호 가져오기
	public int getJikwiId(String jikwi)
	{
		int result = 0;
		try
		{
			
			Statement stmt = conn.createStatement();
			
			String sql = String.format("SELECT JIKWI_ID FROM TBL_JIKWI WHERE JIKWI_NAME = '%s'", jikwi);
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				result = rs.getInt(1);
			}
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	// 사번 정렬 조회 메소드
	public ArrayList<MemberDTO> orderById()
	{
		
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		
		try
		{
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT EMP_ID, EMP_NAME, SSN, IBSADATE, CITY_ID, NVL(TEL, '정보없음'), BUSEO_ID, JIKWI_ID, BASICPAY, SUDANG ,BASICPAY + SUDANG FROM TBL_EMP ORDER BY EMP_ID";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next())
			{
				MemberDTO dto = new MemberDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6)
								, rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10));
				result.add(dto);
			}
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	// 정보 수정 메소드
	public int modify(MemberDTO dto)
	{
		int result = 0;
		
		try {
			Statement stmt = conn.createStatement();
			
			String sql = String.format("UPDATE TBL_EMP" + 
					" SET EMP_NAME = '%s', SSN = '%s', IBSADATE = '%s', CITY_ID = %d, TEL = '%s', BUSEO_ID = %d, JIKWI_ID = %d, BASICPAY = %d" + 
					" WHERE EMP_ID = %d", dto.getEmp_name(), dto.getSsn(), dto.getIbsadate(), dto.getCity_id(), dto.getTel()
					, dto.getBuseo_id(), dto.getJikwi_id(), dto.getBasicpay(), dto.getEmp_id());
			
			result = stmt.executeUpdate(sql);
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	public int delete(int sid)
	{
		int result = 0;
		
		try {
			Statement stmt = conn.createStatement();
			String sql = String.format("DELETE FROM TBL_EMP WHERE EMP_ID = %d", sid);
			result = stmt.executeUpdate(sql);
			
			stmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}
	
	// 인원세는 메소드
	public int count()
	{
		int result = 0;
		
		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT COUNT(*) FROM TBL_EMP";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}
	
	// 검색 메소드
	public ArrayList<MemberDTO> search(String con, String val)
	{
		
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		try {
			Statement stmt = conn.createStatement();
			String sql = String.format("SELECT EMP_ID, EMP_NAME, SSN, TO_CHAR(IBSADATE, 'YYYY-MM-DD'), CITY_ID, NVL(TEL, '정보없음'), BUSEO_ID, JIKWI_ID, BASICPAY, SUDANG FROM TBL_EMP WHERE %s = '%s'", con , val);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				MemberDTO dto = new MemberDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10));
				
				result.add(dto);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}