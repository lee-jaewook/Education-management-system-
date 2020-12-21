package com.test.DDC.admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.test.DDC.DBUtil;

/**
 * 교육생 출력, 등록, 수정, 삭제 클래스
 * @author 전혜원
 *
 */
public class AdminStudent {
	
	private static Connection conn = null;
	private static Statement stat = null;
	private static ResultSet rs = null;
	private static DBUtil util = new DBUtil();
	private static Scanner scan = new Scanner(System.in);
	private static int page = 1;
	//페이징에 쓰일 헤더+포맷
	private static String mainHeader = "[번호]\t[이름]\t[주민번호 뒷자리]\t[전화번호]\t[등록일]\t[수강횟수]";
	
	private static ArrayList<String> studentList = new ArrayList<String>(505); //학생 목록 저장할 list
	private static ArrayList<String> seqList = new ArrayList<String>(505); //학생 시퀀스 저장할 list
	
	/**
	 * 학생 관리 메뉴를 출력하는 메소드
	 */
	public void printMain() {
		
		boolean loop = true;

		while (loop) {
			System.out.println("============================================================");
			System.out.println("\t\t[학생 관리]");
			System.out.println("============================================================");
			System.out.println("\t\t1. 학생 전체 보기");
			System.out.println("\t\t2. 학생 면접 관리");
			System.out.println();
			System.out.println("\t\t0. 뒤로가기");
			System.out.println("------------------------------------------------------------");
			System.out.print("\t\t입력 : ");
			String input = scan.nextLine();
			
			
			switch(input) {
			
			case "1":
				//학생 전체보기
				System.out.println("============================================================");
				System.out.println("\t\t[학생 전체 보기]");
				System.out.println("========================================================================================================================");
				studentAccount("00"); //학생 리스트 저장
				
				paging(page,studentList,mainHeader,0);
				printStudent();
				
				break;
			case "2":
				//학생 면접 관리
				studentInterview();
				continue;
				.
			case "0":
				//뒤로가기
				return;
			}

		}//while
		
	}//printMain()
	
	
	private void studentInterview() {
		// 학생 면접 관리
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		//쿼리문 결과 담을 list
		ArrayList<String> interviewList = new ArrayList<String>();
		
		try {

			conn = util.open();
			stat = conn.createStatement();


			System.out.println("============================================================");
			System.out.println("\t\t[학생 면접]");
			System.out.println("============================================================");
			
			String sql = String.format("select s.name, to_char(iv.interviewdate,'yyyy-mm-dd') as 면접일"
					+ ",iv.interviewresult,to_char(s.regidate,'yyyy-mm-dd') as 등록일,cll.name as 과정명 " + 
					"from tblStudent s inner join tblRegiCourse rc on s.student_seq = rc.student_seq" + 
					"    inner join tblInterview iv on rc.regicourse_seq = iv.regicourse_seq " + 
					"        inner join tblOpenCourse oc on rc.opencourse_seq = oc.opencourse_seq " + 
					"            inner join tblCourseList cll on oc.courselist_seq = cll.courselist_seq");
			
			rs = stat.executeQuery(sql);
			
			//학생명 면접일 면접결과 등록일 과정명 
			
			while(rs.next()) {
				interviewList.add(String.format("%s\t%s\t%s\t%s\t%s",
									rs.getString("name"), rs.getString("면접일"),
									rs.getString("interviewresult"), rs.getString("등록일"), rs.getString("과정명")));
			}
			
			rs.close();
			stat.close();
			conn.close();
			
			// 페이징
			String interviewHeader = "[학생명]\t[면접일]\t[면접결과]\t[등록일]\t[과정명]";
			
			int pagenum = 1;
			paging(pagenum, interviewList, interviewHeader, 3);
			
			while (true) {

				System.out.println("a. 다음페이지");
				System.out.println("b. 다음페이지");
				System.out.println();
				System.out.println("0. 뒤로가기");
				System.out.println("------------------------------------------------------------");
				System.out.print("입력 : ");
				String input = scan.nextLine();

				if (input.equals("0")) {
					// 뒤로 가기
					return;
				} else if (input.equals("a")) {
					// 다음페이지
					pagenum++;
					paging(pagenum, interviewList, interviewHeader, 3);

				} else if (input.equals("b")) {
					// 이전페이지
					pagenum--;
					paging(pagenum, interviewList, interviewHeader, 3);
				}

			} // while

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}//studentInterview

	private void printStudent() {
		
		boolean loop = true;
		int result = 0;
		
		while(true) {
			
			System.out.println("========================================================================================================================");
			System.out.println("- 상세보기를 원하시면 해당 학생 번호를 입력해주세요.");
			System.out.println();
			System.out.println("a. 다음페이지");
			System.out.println("b. 이전페이지");
			System.out.println("c. 교육생 등록");
			System.out.println("d. 교육생 검색");
			System.out.println("e. 교육생 정보 수정");
			System.out.println("f. 교육생 삭제");
			System.out.println();
			System.out.println("0. 뒤로가기");
			
			System.out.println("============================================================");
			System.out.print("입력 : ");
			String input = scan.nextLine();
			System.out.println("============================================================");
			
			if(input.charAt(0) >= 'a' && input.charAt(0) <= 'f') {
				
				switch (input) {

				case "a":
					// 다음 페이지
					page++;
					paging(page, studentList, mainHeader, 0);
					break;

				case "b":
					// 이전 페이지
					page--;
					paging(page, studentList, mainHeader, 0);
					break;
					
				case "c":
					// 교육생 등록
					studentAccount("a");
					return;
				case "d":
					// 교육생 검색
					studentSearch();
					return;
				case "e":
					// 교육생 수정
					studentModify();
					return;
				case "f":
					// 교육생 삭제
					studentDelete();
					return;
				case "0":
					// 뒤로가기
					loop=false;
					return;
				}
				
			} else {
				//교육생 상세보기
				printCourse(input);
				return;
				
			}//if
		}//while

	}
	
	private void studentSearch() {
		//교육생 검색 메소드
		
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();
		
		

		int flag = 0; //유효성 검사 변수
		
		try {

			conn = util.open();
			stat = conn.createStatement();
			String sql = null;

			System.out.println("============================================================");
			System.out.println("		[학생 검색]");
			System.out.println("============================================================");
			System.out.println(" - 검색할 필드를 고르세요.");
			System.out.println("1. 학생명");
			System.out.println("2. 주민번호 뒷자리");
			System.out.println("3. 전화번호");
			System.out.println("4. 등록일");
			System.out.println("5. 수료여부");
			System.out.println();
			System.out.println("0. 메뉴로 가기");
			System.out.println("------------------------------------------------------------");
			System.out.print("입력 : ");
			String input = scan.nextLine();

			if (input.equals("0")) {
				// 메뉴로 가기
				return;
			} else {
				// 필드 선택시
				System.out.println("------------------------------------------------------------");
				System.out.println(" - 검색할 단어를 입력하세요.");
				System.out.println("------------------------------------------------------------");
				System.out.print("입력 : ");
				String word = scan.nextLine();

				switch (input) { // 필드에 맞춰 검색

				case "1":
					sql = String.format(
							"SELECT student_seq as seq, name, ssn, tel, to_char(regiDate,'yyyy-mm-dd') as regiDate "
									+ "FROM tblStudent WHERE name like '%%%s%%'",
							word);
					break;
				case "2":
					sql = String.format(
							"SELECT student_seq as seq, name, ssn, tel, to_char(regiDate,'yyyy-mm-dd') as regiDate "
									+ "    FROM tblStudent WHERE ssn like '%%%s%%'",
							word);
					break;
				case "3":
					sql = String.format(
							"SELECT student_seq as seq, name, ssn, tel, to_char(regiDate,'yyyy-mm-dd') as regiDate"
									+ "    FROM tblStudent WHERE tel like '%%%s%%'",
							word);
					break;
				case "4":
					sql = String.format(
							"SELECT student_seq as seq, name, ssn, tel, to_char(regiDate,'yyyy-mm-dd') as regiDate"
									+ "    FROM tblStudent WHERE regiDate like '%%%s%%'",
							word);
					break;
				case "5":
					sql = String.format(
							"select s.student_seq as seq, s.name as name, s.ssn as ssn, s.tel as tel, to_char(s.regiDate,'yyyy-mm-dd') as regiDate, rc.finalState as state"
									+ "    from tblStudent s inner join tblRegiCourse rc on s.student_seq = rc.student_seq"
									+ "        where rc.finalstate like '%%%s%%'",
							word);
					break;

				}// switch
				

				rs = stat.executeQuery(sql);

				
				ArrayList<String> searchList = new ArrayList<String>();
				String searchHeader = null;
				
				int pagenum = 1;
				int v = 0;
				
				// 결과 값 출력하기
				if (input.equals("5")) { // 수료여부 검색은 컬럼이 더 많아 따로 출력
					System.out.println("------------------------------------------------------------------------------------------------------------------------");
					searchHeader = "[번호]\t[이름]\t[주민번호]\t[전화번호]\t[등록일]\t[수료여부]";

					while (rs.next()) {
						
						searchList.add(String.format("%s\t%s\t%s\t%s\t%s\t%s", rs.getString("seq"),
								rs.getString("name"), rs.getString("ssn"), rs.getString("tel"),
								rs.getString("regiDate"), rs.getString("state")));
					}
					v = 1;

				} else { //수료여부 검색이 아닌 다른 필드 검색시 출력
					System.out.println("------------------------------------------------------------------------------------------------------------------------");
					searchHeader = "[번호]\t[이름]\t[주민번호]\t[전화번호]\t[등록일]";

					while (rs.next()) {
						searchList.add(String.format("%s\t%s\t%s\t%s\t%s", rs.getString("seq"), rs.getString("name"),
								rs.getString("ssn"), rs.getString("tel"), rs.getString("regiDate")));
					}
					v = 2;
				}
				
				rs.close();
				stat.close();
				conn.close();
				
				paging(pagenum, searchList, searchHeader, v);
				
				while (true) {
					System.out.println("------------------------------------------------------------------------------------------------------------------------");
					System.out.println("a. 다음 페이지");
					System.out.println("b. 이전 페이지");
					System.out.println();
					System.out.println("0. 메뉴로 가기");
					System.out.println("------------------------------------------------------------------------------------------------------------------------");
					System.out.print("입력 : ");
					String pInput = scan.nextLine();

					switch (pInput) {
					case "a":
						pagenum++;
						paging(pagenum, searchList, searchHeader, v);
						break;
					case "b":
						pagenum--;
						paging(pagenum, searchList, searchHeader, v);
						break;
					case "0":
						//뒤로가기
						return;
					}

				} // while

			} // 메뉴 if

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}//studentSearch

	private void paging(int page, ArrayList<String> list, String header, int select) {
		// 페이징 메소드
		// 페이지 출력하는 메소드

		// 검색결과 0일 경우 true
		if (list.size() == 0) {
			System.out.println("검색 결과가 없습니다.");
			return;
		} else {

			// 최대 페이지 계산하기
			int maxPage = list.size() % 10 > 0 ? list.size() / 10 + 1 : list.size() / 10;

			// 유효한 페이지 아닐 경우
			if (page <= 0) {
				page = maxPage;
			} else if (page > maxPage) {
				page = 1;
			}
			
			System.out.printf("현재 %d 페이지 입니다.\n", page);
			System.out.println();
			
//			System.out.println("[번호]\t[이름]\t[주민번호 뒷자리]\t[전화번호]\t[등록일]\t\t[수강횟수]");

			System.out.println(header);
			
			for (int i = (page - 1) * 10; i < page * 10; i++) {

				if (i >= list.size()) {
					break;

				} else {

					String[] array = list.get(i).split("\t");

					switch(select) {
					
					case 0 : //학생 리스트
						System.out.printf("%3s\t%3s\t%13s\t\t%13s\t%10s\t%2s\n"
								, array[0], array[1], array[2], array[3], array[4], array[5]);
						break;
					case 1 : //수료 여부 검색 
						System.out.printf("%3s\t%4s\t%14s\t%13s\t%10s\t%2s\n"
								, array[0],array[1],array[2],array[3],array[4],array[5]);
						break;
					case 2 : //다른 필드 검색
						System.out.printf("%3s\t%4s\t%14s\t%13s\t%10s\n"
								, array[0],array[1],array[2],array[3],array[4]);
						
						break;
					case 3 : //면접
						System.out.printf("%4s\t\t%10s\t%4s\t\t%10s\t%30s\n"
								, array[0],array[1],array[2],array[3],array[4]);
						break;
					}

				} // if
			} // for
			
			System.out.println();

		} // if
		
	}//paging

	private void printCourse(String num) {
		// 학생 정보 상세 보기
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();
		
		//맡은 과정 목록 담을 리스트
		ArrayList<String> courseList = new ArrayList<String>(5);

		try {

			conn = util.open();
			stat = conn.createStatement();

			System.out.println("========================================================================================================================");
			System.out.println("\t\t[학생 정보 상세보기]");
			System.out.println("========================================================================================================================");
			
			//어떤 학생의 정보인지 출력
			String sql = String.format("select name from tblStudent where student_seq = %s",num);
			rs = stat.executeQuery(sql);
			
			if(rs.next()) {
				System.out.printf("학생 %s님의 정보입니다. \n",rs.getString("name"));
			}
			
			rs.close();
			
			System.out.println();
			
			//해당 학생의 과정 정보 불러오기
			sql = String.format("select cll.name as 과정명, to_char(oc.startdate,'yyyy-mm-dd')||'~'||to_char(oc.endDate,'yyyy-mm-dd') as 과정기간, oc.room_seq||'강의실' as 강의실명"
					+ ", rc.finalState as 수료여부, to_char(rc.finaldate,'yyyy-mm-dd') as 수료날짜" + 
					" from tblStudent s inner join tblRegiCourse rc on s.student_seq = rc.regicourse_seq" + 
					"    inner join tblOpenCourse oc on rc.openCourse_seq = oc.opencourse_seq" + 
					"        inner join tblCourseList cll on oc.courselist_seq = cll.courselist_seq" + 
					"            where s.student_seq = %s", num);
			
			rs = stat.executeQuery(sql);
			
			while(rs.next()) { //쿼리 결과 저장하기
				String result = rs.getString("과정명") + "\t"
								+ rs.getString("과정기간") + "\t"
								+ rs.getString("강의실명") + "\t"
								+ rs.getString("수료여부") + "\t"
								+ rs.getString("수료날짜");
				
				courseList.add(result);

			}
			
			//결과 출력
			for(int i=0; i<courseList.size(); i++) {
				System.out.println("[번호]\t[과정명]\t\t\t\t\t\t[과정기간]\t\t[강의실]\t[수료여부]\t[수료날짜]\n");
				
				String[] array = courseList.get(i).split("\t");
				
				System.out.printf("%2d\t%s\t%s\t%s\t\t%s\t\t%s\n",i+1,array[0],array[1],array[2],array[3],array[4]);
			}
			
			System.out.println();
			System.out.println("0. 뒤로가기");
			System.out.println("------------------------------------------------------------------------------------------------------------------------");
			System.out.print("입력 : ");
			String input = scan.nextLine();
			
			stat.close();
			conn.close();
			rs.close();

			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	private void studentDelete() {
		// 학생 정보 삭제
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {

			conn = util.open();
			stat = conn.createStatement();

			int flag = 0;
			
			System.out.println("============================================================");
			System.out.println("\t\t[학생 정보 삭제]");
			System.out.println("============================================================");
			System.out.print("삭제할 학생 번호 : ");
			String num = scan.nextLine();
			
			for(String s:studentList) {
				//num이 교사리스트 번호 안에 있는 번호인지 유효성 검사
				if(s.contains(num)) { 
					flag = 1;
				}
			}
			
			if (flag == 1) {// 유효번호 있을 경우

				String sql = String.format("SELECT student_seq as seq,name,  ssn, tel, regiDate" + 
						"                        FROM tblStudent" + 
						"                            where student_seq = %s"
						, num);

				rs = stat.executeQuery(sql);
				rs.next();
				
				System.out.println("[번호]\t[이름]\t[주민번호]\t[전화번호]\t[등록일]");
				String reslut = rs.getString("seq") + "\t" + rs.getString("name") + "\t" + rs.getString("ssn") + "\t" + rs.getString("tel") + "\t" + rs.getString("regiDate");
				System.out.println(reslut);
				System.out.println("------------------------------------------------------------------------------------------------------------------------");
				System.out.println("정말로 삭제하시겠습니까? (y/n)");
				System.out.print("입력 : ");
				String input = scan.nextLine();
				
				if(input.equals("y")) {
					sql = String.format("update tblStudent set del = 'y' where student_seq = %s", num);
					stat.executeUpdate(sql);
					System.out.println("------------------------------------------------------------");
					System.out.println("삭제가 완료되었습니다.");

					stat.close();
					conn.close();
					rs.close();
					return;

				} else {
					System.out.println("------------------------------------------------------------");
					System.out.println("삭제가취소 되었습니다. 이전 화면으로 돌아갑니다.");

					stat.close();
					conn.close();
					return;
				}

			} else {
				System.out.println("------------------------------------------------------------");
				System.out.println("없는 교사 번호입니다. 이전 화면으로 돌아갑니다.");
				return;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}//studentDelete

	private void studentModify() {
		//학생 정보 수정
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {
			conn = util.open();
			stat = conn.createStatement();

			int flag = 0;
			
			System.out.println("============================================================");
			System.out.println("\t\t[학생 정보 수정]");
			System.out.println("============================================================");
			System.out.println("수정할 학생 번호 : ");
			String num = scan.nextLine();
			
			for(String s:studentList) {
				//num이 교사리스트 번호 안에 있는 번호인지 유효성 검사
				if(s.contains(num)) { 
					flag = 1;
				}
			}
			
			if (flag == 1) { // 유효번호 있을 경우
				System.out.println("------------------------------------------------------------");
				System.out.println("수정할 필드를 선택하세요.");
				System.out.println("1. 이름");
				System.out.println("2. 주민번호");
				System.out.println("3. 전화번호");
				System.out.println("4. 등록일");
				System.out.println("5. 수료여부"); //수료 여부 + 날짜
				System.out.println("------------------------------------------------------------");
				System.out.print("입력 : ");
				String input = scan.nextLine();
				System.out.println("------------------------------------------------------------");
				System.out.print("수정할 값을 입력하세요.");
				
				if(input.equals("2")) {
					System.out.println("주민번호는 '-'를 붙여서 입력해주세요.");
				}else if(input.equals("3")) {
					System.out.println("전화번호는 '-'를 붙여서 입력해주세요.");
				}else if(input.equals("4")) {
					System.out.println("등록일은 YYYY-MM-DD 형식으로 기입해주세요.");
				}else {
					System.out.println("수료여부는 수료/탈락 두가지 형식중 하나만 입력하세요.");
					System.out.println("");
				}
				
				System.out.println();
				System.out.print("입력 : ");
				String modify = scan.nextLine();
				
				//수정할 값 유효성 검사
				if(input.equals("2") && !modify.contains("-") && modify.length() != 14) {
					System.out.println("주민번호 기입이 올바르지 않습니다.");
					return;
				}else if(input.equals("3") && !modify.contains("-")) {
					System.out.println("전화번호 기입이 올바르지 않습니다.");
					return;
				} else if (input.equals("4") && !modify.contains("-") && modify.length() != 10) {
					System.out.println("등록일 형식이 올바르지 않습니다.");
					return;
				} else {
					String sql = null;
					switch (input) {
					case "1":
						sql = String.format("update tblStudent set name='%s' where student_seq = '%s'", modify, num);
						stat.executeUpdate(sql);
						break;
					case "2":
						sql = String.format("update tblStudent set ssn='%s' where student_seq = '%s'", modify, num);
						stat.executeUpdate(sql);
						break;
					case "3":
						sql = String.format("update tblStudent set tel='%s' where student_seq = '%s'", modify, num);
						stat.executeUpdate(sql);
						break;
					case "4":
						sql = String.format(
								"update tblStudent set regiDate=to_date('%s','yyyy-mm-dd') where student_seq = '%s'",
								modify, num);
						stat.executeUpdate(sql);
						break;
					}
				}
				
				System.out.println("------------------------------------------------------------");
				System.out.println("수정이 완료되었습니다.");
				
				stat.close();
				conn.close();

			} else {
				System.out.println("------------------------------------------------------------");
				System.out.println("없는 학생 번호입니다. 이전 화면으로 돌아갑니다.");
				return;
			}	

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}//studentModify()
	
	
	private void studentAccount(String input) {
		//학생 계정 관리 + 등록
		
		try {

			conn = util.open();
			stat = conn.createStatement();
			
			String sql = null;
			
			studentList.clear();
			
			//학생이 총 몇명인지 구하기
			sql = "SELECT COUNT(*) as cntStudent FROM tblStudent where del = 'n'";
			rs = stat.executeQuery(sql);
			rs.next();
			
			int cnt = Integer.parseInt(rs.getString("cntStudent"));
			
			rs.close();
			
			//학생번호(sequence) 따로 저장하기
			sql = "select student_seq as seq from tblStudent where del = 'n' order by student_seq";
			rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				seqList.add(rs.getString("seq"));
			}
			rs.close();
			
			//학생 계정 정보 : 번호, 이름, 주민번호, 전화번호, 등록일, 수강횟수 * 학생 수
			for(String seq : seqList) {
				int i = Integer.parseInt(seq);
				
				sql = String.format("SELECT student_seq as seq,name,  substr(ssn,8) as ssn, tel, to_char(regiDate,'yyyy-mm-yy') as regiDate," + 
						"    (SELECT COUNT(*) \n" + 
						"        FROM tblStudent s\n" + 
						"            INNER JOIN tblRegiCourse rc\n" + 
						"                ON s.student_seq = rc.student_seq\n" + 
						"                    WHERE s.student_seq = %d) as cntRegi" + 
						"                        FROM tblStudent\n" + 
						"                            where student_seq = %d and del = 'n'"
						,i,i);

				rs = stat.executeQuery(sql);
				
				if (rs.next()) {
				
					studentList.add(String.format("%s\t%s\t%s\t%s\t%s\t%s",
										rs.getString("seq"),rs.getString("name"),
										rs.getString("ssn"),rs.getString("tel"),
										rs.getString("regiDate"),rs.getString("cntRegi")));	
				}
				rs.close();

			}

			switch(input) {

			case "a": //교사 계정 등록
				System.out.println("============================================================");
				System.out.println("			[학생 등록]");
				System.out.println("============================================================");
				System.out.print("학생이름 : ");
				String name = scan.nextLine();
				System.out.print("주민번호 : ");
				String ssn = scan.nextLine();
				System.out.print("전화번호 : ");
				String tel = scan.nextLine();
				System.out.println("------------------------------------------------------------");
				System.out.println("이대로 등록하시겠습니까? (y/n) : ");
				String answer = scan.nextLine();
				
				if(answer.equals("y")) {
					sql = String.format("INSERT INTO tblStudent VALUES(student_seq.nextval, '%s', '%s', '%s', sysdate)", name,ssn,tel);
					stat.executeUpdate(sql);					
				}else {
					return;
				}
				break;
			}

			stat.close();
			conn.close();
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}//teacherAccount()
	

}
