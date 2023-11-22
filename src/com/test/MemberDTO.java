/*=======================================
    MemberDTO.java
    - 데이터 보관 및 전송 전용 클래스
======================================= */

package com.test;

public class MemberDTO
{
	private int emp_id, city_id, buseo_id, jikwi_id, basicpay, sudang;
	private String emp_name, ssn, ibsadate, tel;
	
	// emp.id 없는 생성자(insert용)
	public MemberDTO(String emp_name, String ssn, String ibsadate, int city_id, String tel, int buseo_id
			, int jikwi_id, int basicpay, int sudang)
	{
		this.city_id = city_id;
		this.buseo_id = buseo_id;
		this.jikwi_id = jikwi_id;
		this.basicpay = basicpay;
		this.sudang = sudang;
		this.emp_name = emp_name;
		this.ssn = ssn;
		this.ibsadate = ibsadate;
		this.tel = tel;
	}
	
	// emp.id 있는 생성자(그 외)
	public MemberDTO(int emp_id, String emp_name, String ssn, String ibsadate, int city_id, String tel, int buseo_id
			, int jikwi_id, int basicpay, int sudang)
	{
		this.emp_id = emp_id;
		this.city_id = city_id;
		this.buseo_id = buseo_id;
		this.jikwi_id = jikwi_id;
		this.basicpay = basicpay;
		this.sudang = sudang;
		this.emp_name = emp_name;
		this.ssn = ssn;
		this.ibsadate = ibsadate;
		this.tel = tel;
	}
	
	public int getEmp_id()
	{
		return emp_id;
	}
	public void setEmp_id(int emp_id)
	{
		this.emp_id = emp_id;
	}
	public int getCity_id()
	{
		return city_id;
	}
	public void setCity_id(int city_id)
	{
		this.city_id = city_id;
	}
	public int getBuseo_id()
	{
		return buseo_id;
	}
	public void setBuseo_id(int buseo_id)
	{
		this.buseo_id = buseo_id;
	}
	public int getJikwi_id()
	{
		return jikwi_id;
	}
	public void setJikwi_id(int jikwi_id)
	{
		this.jikwi_id = jikwi_id;
	}
	public int getBasicpay()
	{
		return basicpay;
	}
	public void setBasicpay(int basicpay)
	{
		this.basicpay = basicpay;
	}
	public int getSudang()
	{
		return sudang;
	}
	public void setSudang(int sudang)
	{
		this.sudang = sudang;
	}
	public String getEmp_name()
	{
		return emp_name;
	}
	public void setEmp_name(String emp_name)
	{
		this.emp_name = emp_name;
	}
	public String getSsn()
	{
		return ssn;
	}
	public void setSsn(String ssn)
	{
		this.ssn = ssn;
	}
	public String getIbsadate()
	{
		return ibsadate;
	}
	public void setIbsadate(String ibsadate)
	{
		this.ibsadate = ibsadate;
	}
	public String getTel()
	{
		return tel;
	}
	public void setTel(String tel)
	{
		this.tel = tel;
	}
}
