package com.newlecture.app.console;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.newlecture.app.entity.Notice;
import com.newlecture.app.service.NoticeService;

public class NoticeConsole {

	private NoticeService service;
	private int page;
	private String searchWord;
	private String searchField;
	
	public NoticeConsole() {
		service = new NoticeService();
		page = 1;
		searchWord = "TITLE";
		searchField = "";
	}
	
	public void printNoticeList() throws ClassNotFoundException, SQLException {
		List<Notice> list = service.getList(page, searchField, searchWord);
		int count = service.getCount();
		int lastPage = count/10;
		lastPage = count % 10 == 0 ? lastPage : lastPage + 1; 
		
		System.out.println("-----------------------------------------");
		System.out.printf("<공지사항> 총 %d 게시글\n", count);
		System.out.println("-----------------------------------------");
		
		for(Notice n : list) {
		//System.out.printf("12. 안녕하세요 / newlec / 2015-07-12", args)
			System.out.printf("%d. %s / %s / %s\n", n.getId(), n.getTitle()
							, n.getWriterid(), n.getRegDate());
		}
		System.out.println("-----------------------------------------");
		//System.out.printf("        1/2 pages\n", 12);
		System.out.printf("        %d/%d pages\n", page, lastPage);
		
	}

	public int inputNoticeMenu() {
		Scanner scan = new Scanner(System.in);
		
		System.out.printf("1.상세조회/ 2.이전/ 3.다음/ 4.글쓰기 / 5.검색 / 6.종료>");
		String menu_ = scan.nextLine(); // 오입력 대비
		int menu = Integer.parseInt(menu_);
		
		return menu;
	}

	public void movePrevList() {
		if(page == 1) {
			System.out.println("이전 페이지가 없습니다.");
			return;
		}
		page--;
	}

	public void moveNextList() throws ClassNotFoundException, SQLException {
		int count = service.getCount();
		int lastPage = count/10;
		lastPage = count % 10 == 0 ? lastPage : lastPage + 1; 
		
		if(page == lastPage) {
			System.out.println("다음 페이지가 없습니다.");
			return;
		}
		page++;
	}

	public void inputSearchWord() {
		Scanner scan = new Scanner(System.in);
		System.out.println("검색범주(title/content/writerID)중에 하나를 입력하세요");
		System.out.print(">");
		searchField = scan.nextLine();
		
		System.out.print("검색어 > ");
		searchWord = scan.nextLine();
		
	}

}
