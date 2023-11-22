/*==========================================
   MemberProcess.java
   - 콘솔 기반 서브 메뉴 입출력 전용 클래스
 ==========================================*/

package com.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class MemberProcess
{

	private MemberDAO dao;

	public MemberProcess()
	{
		dao = new MemberDAO();
	}
	
	// 직원 입력 메소드
	public void insertPeople()
	{
		dao.Connection();
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("직원 정보 입력 --------------------------------------------------------------");
		
		System.out.print("이름 : ");
		String emp_name = sc.next();
		
		System.out.print("주민등록번호(yymmdd-nnnnnnn) : ");
		String ssn = sc.next();	
		
		int city_id = getCityList();
		if(city_id == 0)
			return;
		
		System.out.print("전화번호 : ");
		String tel = sc.next();
		
		int buseo_id = getBuseoList();
		if(buseo_id == 0)
			return;
	
		String jikwi = putJikwi();
		if(jikwi.equals("0"))
			return;
		
		int basicpay = checkBasicPay(jikwi);
		if(basicpay == 0)
			return;
		
		int jikwi_id = dao.getJikwiId(jikwi);
		
		System.out.print("수당 : ");
		int sudang = sc.nextInt();
		
		String todaysDate = String.valueOf(LocalDate.now());	
		
		MemberDTO dto = new MemberDTO(emp_name, ssn, todaysDate, city_id, tel, buseo_id, jikwi_id, basicpay, sudang);
		
		int result = dao.add(dto);
		if(result > 0)
			System.out.println("입력이 완료되었습니다.");
		else
			System.out.println("변경사항 없음");
	}
	
	// 정보 수정 메소드
	public void modify()
	{
		dao.Connection();
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("정보 수정");
		System.out.print("수정할 사원의 사원번호를 입력해주세요 : ");
		
		int emp_no = sc.nextInt();
		
		System.out.println("수정할 정보를 입력하세요");
		
		System.out.print("이름 : ");
		String emp_name = sc.next();
		
		System.out.print("주민등록번호(yymmdd-nnnnnnn) : ");
		String ssn = sc.next();	
		
		int city_id = getCityList();
		if(city_id == 0)
			return;
		
		System.out.print("전화번호 : ");
		String tel = sc.next();
		
		int buseo_id = getBuseoList();
		if(buseo_id == 0)
			return;
	
		String jikwi = putJikwi();
		if(jikwi.equals("0"))
			return;
		
		int basicpay = checkBasicPay(jikwi);
		if(basicpay == 0)
			return;
		
		int jikwi_id = dao.getJikwiId(jikwi);
		
		System.out.print("수당 : ");
		int sudang = sc.nextInt();
		
		String todaysDate = String.valueOf(LocalDate.now());	
		
		MemberDTO dto = new MemberDTO(emp_no, emp_name, ssn, todaysDate, city_id, tel, buseo_id, jikwi_id, basicpay, sudang);
		
		int result = dao.modify(dto);
		if(result > 0)
			System.out.println("수정이 완료되었습니다.");
		else
			System.out.println("변경사항 없음");
	}
	
	// 직원 삭제 메소드
	public void delete()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("=====직원정보삭제=====");
		System.out.println("삭제할 직원의 사원번호를 입력하세요 : ");
		int enp_no = sc.nextInt();
		
		int result = dao.delete(enp_no);
		if(result > 0)
			System.out.println("삭제가 완료되었습니다.");
		else
			System.out.println("변경사항 없음");
	}
	
	///////////////////////////////
	// 지역 목록
	public int getCityList()
	{
		dao.Connection();
		Scanner sc = new Scanner(System.in);
		// 지역 목록 가져오기
		ArrayList<String> cityList = dao.getCity();
		System.out.print("지역(");
		for (String str : cityList)
		{
			System.out.print(str+"/");
		}
		System.out.print(") : ");
		
		// cityList에 입력받은 도시가 없다면 나가게끔 처리
		
		String city = sc.next();
		if(!cityList.contains(city))
		{
			System.out.println("선택 가능한 도시가 아닙니다, 메인화면으로 돌아갑니다");
			return 0;
		}
		int city_id = cityList.indexOf(city) + 1; // 인덱스가 0부터 시작하므로 +1 해줌
		
		return city_id;
	}
	
	// 부서 체크
	public int getBuseoList()
	{
		dao.Connection();
		Scanner sc = new Scanner(System.in);
		// 부서목록 가져오기
		ArrayList<String> buseoList = dao.getBuseo();
		System.out.print("부서(");
		for (String str : buseoList)
		{
			System.out.print(str+"/");
		}
		System.out.print(") : ");
		
		String buseo = sc.next();
		
		// buseoList에 입력받은 부서가 없다면 나가게끔 처리
		if(!buseoList.contains(buseo))
		{
			System.out.println("선택 가능한 부서가 아닙니다, 메인화면으로 돌아갑니다");
			return 0;
		}
		int buseo_id = buseoList.indexOf(buseo) + 1;
		
		return buseo_id;
	}
	
	// 직위 체크
	public String putJikwi()
	{
		dao.Connection();
		Scanner sc = new Scanner(System.in);
		
		// 직위목록 가져오기
		HashMap<String, Integer> jikwiList = dao.getJikwi();
		System.out.print("직위(");
		for (String str : jikwiList.keySet())
		{
			System.out.print(str+"/");
		}
		System.out.print(") : ");
		
		String jikwi = sc.next();
		
		// jikwiListSet 에 입력받은 직위가 없다면 나가게끔 처리
		if(!jikwiList.keySet().contains(jikwi))
		{
			System.out.println("선택 가능한 직급이 아닙니다, 메인화면으로 돌아갑니다");
			return "0";
		}
		
		return jikwi;
	}
	
	// 기본급 체크
	public int checkBasicPay(String jikwi)
	{
		dao.Connection();
		Scanner sc = new Scanner(System.in);
		
		// 직위목록 가져와서 직위에 맞는 급여 가져옴
		HashMap<String, Integer> jikwiList = dao.getJikwi();
		int standard = jikwiList.get(jikwi);
		
		// 입력받은 급여가 직위의 급여와 맞지 않다면 나가게끔 처리
		System.out.printf("기본급(최소 %d이상) : ", standard);
		int basicpay = sc.nextInt();
		
		if(basicpay < standard)
		{
			System.out.println("직위별 최저 급여와 맞지 않습니다. 메인화면으로 돌아갑니다.");
			return 0;
		}
		return basicpay;
	}
	
	public void search(String con)
	{
		dao.Connection();
		Scanner sc = new Scanner(System.in);
		System.out.println("검색어를 입력하세요 : ");
		String val = sc.next();
		
		ArrayList<MemberDTO> arrayList = dao.search(con, val);
		
		for (MemberDTO dto : arrayList) {
			System.out.println(dto);
		}
		
		
	}
	

}