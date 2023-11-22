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
			return;
		}
		int city_id = cityList.indexOf(city) + 1;
		
		System.out.print("전화번호 : ");
		String tel = sc.next();
		
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
			return;
		}
		int buseo_id = buseoList.indexOf(buseo) + 1;
		
		
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
			System.out.println("선택 가능한 부서가 아닙니다, 메인화면으로 돌아갑니다");
			return;
		}
		
		// 직위에 맞는 부서번호 가져오는 메소드 만들기
		int jikwi_id = dao.getJikwiId(jikwi);
		
		
		System.out.print("기본급(최소 1800000 이상) : ");
		int basicpay = sc.nextInt();
		
		// 입력받은 급여가 직위의 급여와 맞지 않다면 나가게끔 처리
		int standard = jikwiList.get(jikwi);
		if(basicpay < standard)
		{
			System.out.println("직위별 최저 급여와 맞지 않습니다. 메인화면으로 돌아갑니다.");
			return;
		}
		
		System.out.print("수당 : ");
		int sudang = sc.nextInt();
		
		String todaysDate = String.valueOf(LocalDate.now());	
		
		MemberDTO dto = new MemberDTO(emp_name, ssn, todaysDate, city_id, tel, buseo_id, jikwi_id, basicpay, sudang);
		
		dao.add(dto);
	}
	

}
