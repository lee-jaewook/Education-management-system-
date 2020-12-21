package com.test.DDC.admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import com.test.DDC.DBUtil;

/**
 * 기초정보(과정,과목,강의실,교재)를 관리하는 클래스
 * @author 전혜원
 *
 */
public class AdminBasic {
	
	private static Scanner scan = new Scanner(System.in);

	/**
	 * 기초정보관리 메뉴 출력 메소드
	 */
	public void printBasic() {
		
		while (true) {
			System.out.println("============================================================");
			System.out.println("\t\t[기초정보관리]");
			System.out.println("============================================================");
			System.out.println("\t\t1. 과정 관리");
			System.out.println("\t\t2. 과목 관리");
			System.out.println("\t\t3. 강의실 관리");
			System.out.println("\t\t4. 교재 관리");
			System.out.println();
			System.out.println("\t\t0. 뒤로가기");
			System.out.println("------------------------------------------------------------");
			System.out.print("\t\t입력 : ");
			String input = scan.nextLine();
			
			switch(input) {
			
			case "1":
				//과정 관리
				ManageCourse();
				break;
			case "2":
				//과목 관리
				ManageSubject();
				break;
			case "3":
				//강의실 관리
				ManageRoom();
				break;
			case "4":
				//교재 관리
				ManageBook();
				break;
				
			case "0":
				//뒤로가기
				return;
			}

		}//while
		
	}

	private void ManageBook() {
		System.out.println("============================================================");
		System.out.println("\t\t[교재 관리]");
		System.out.println("============================================================");
		System.out.println("1. 교재 조회");
		System.out.println("2. 교재 추가");
		System.out.println("3. 교재 삭제");
		System.out.println("4. 교재명 수정");
		System.out.println();
		System.out.println("0. 뒤로가기");
		System.out.println("============================================================");
		System.out.print("입력 : ");
		String input = scan.nextLine();
		
		switch(input) {
		
		case "1":
			ViewBook();
			break;
		case "2":
			addBook();
			break;
		case "3":
			deleteBook();
			break;
		case "4":
			modifyBook();
			break;

		case "0":
			break;
		
		}
		
	}

	private void modifyBook() {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {

			conn = util.open();
			stat = conn.createStatement();

			System.out.println("수정할 교재 번호를 입력하세요.");
			System.out.print("입력 : ");
			String bnum = scan.nextLine();
			System.out.println("============================================================");
			
			System.out.println("수정할 교재이름을 입력하세요.");
			System.out.print("입력 : ");
			String bname = scan.nextLine();
			
			System.out.println("수정할 출판사를 입력하세요.");
			System.out.print("입력 : ");
			String bpub = scan.nextLine();
			
			String sql = String.format(
					"update tblBook set name = '%s', publisher = '%s' where book_seq = '%s'",bname,bpub,bnum);
			
			stat.executeUpdate(sql);
			
			System.out.println("============================================================");
			System.out.println("수정이 완료되었습니다.");

			stat.close();
			conn.close();

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void deleteBook() {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {

			conn = util.open();
			stat = conn.createStatement();
			
			System.out.println("삭제할 교재번호를 입력하세요.");
			System.out.print("입력 : ");
			String input = scan.nextLine();
			

			String sql = String.format(
					"update tblBook set book_seq = -book_seq where book_seq = %s",input);

			stat.executeUpdate(sql);
			
			System.out.println("============================================================");
			System.out.println("삭제가 완료되었습니다.");
			

			stat.close();
			conn.close();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void addBook() {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {

			conn = util.open();
			stat = conn.createStatement();

			System.out.println("추가할 교재 이름을 입력하세요.");
			System.out.print("입력 : ");
			String bname = scan.nextLine();
			
			System.out.println("출판사를 입력하세요.");
			System.out.print("입력 : ");
			String pub = scan.nextLine();
			
			String sql = String.format(
					"insert into tblBook (book_seq,name,publisher) values (book_seq.nextVal, '%s','%s')", bname,pub);
			
			stat.executeUpdate(sql);
			
			System.out.println("============================================================");
			System.out.println("추가 완료 되었습니다.");
			

			stat.close();
			conn.close();

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void ViewBook() {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {

			conn = util.open();
			stat = conn.createStatement();

			String sql = 
					"select book_seq as seq, name, publisher as pub from tblBook where book_seq > 0 order by book_seq";

			rs = stat.executeQuery(sql);
			
			System.out.println("============================================================");

			System.out.println("[번호]\t\t\t[교재이름]\t\t\t[출판사]");

			while (rs.next()) {
				System.out.printf("%2s\t%30s\t\t%s\n",rs.getString("seq"),rs.getString("name"),rs.getString("pub"));
			}
			
			System.out.println("============================================================");
			System.out.println("엔터를 입력하세요.");
			scan.nextLine();
			
			
			rs.close();
			stat.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void ManageRoom() {
		System.out.println("============================================================");
		System.out.println("\t\t[강의실 관리]");
		System.out.println("============================================================");
		System.out.println("1. 강의실 조회");
		System.out.println("2. 강의실 추가");
		System.out.println("3. 강의실 삭제");
		System.out.println("4. 강의실명 수정");
		System.out.println();
		System.out.println("0. 뒤로가기");
		System.out.println("============================================================");
		System.out.print("입력 : ");
		String input = scan.nextLine();
		
		switch(input) {
		
		case "1":
			ViewRoom();
			break;
		case "2":
			addRoom();
			break;
		case "3":
			deleteRoom();
			break;
		case "4":
			modifyRoom();
			break;

		case "0":
			break;
		
		}
		
	}

	private void modifyRoom() {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {

			conn = util.open();
			stat = conn.createStatement();

			System.out.println("수정할 강의실 번호를 입력하세요.");
			System.out.print("입력 : ");
			String rnum = scan.nextLine();
			System.out.println("============================================================");
			
			System.out.println("수정할 강의실명을 입력하세요.");
			System.out.print("입력 : ");
			String rname = scan.nextLine();
			
			String sql = String.format(
					"update tblRoom set name = '%s' where room_seq = %s",rname,rnum);
			
			stat.executeUpdate(sql);
			
			System.out.println("수정이 완료되었습니다.");
			System.out.println("============================================================");

			stat.close();
			conn.close();

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deleteRoom() {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {

			conn = util.open();
			stat = conn.createStatement();
			
			System.out.println("삭제할 강의실 번호를 입력하세요.");
			System.out.print("입력 : ");
			String input = scan.nextLine();
			

			String sql = String.format(
					"update tblRoom set room_seq = -room_seq where room_seq = %s",input);

			stat.executeUpdate(sql);
			
			System.out.println("============================================================");
			System.out.println("삭제가 완료되었습니다.");
			

			stat.close();
			conn.close();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void addRoom() {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {

			conn = util.open();
			stat = conn.createStatement();

			System.out.println("추가할 강의실 이름을 입력하세요.");
			System.out.print("입력 : ");
			String rname = scan.nextLine();
			
			System.out.println("강의실의 정원을 입력하세요.");
			System.out.print("입력 : ");
			String cnt = scan.nextLine();
			
			String sql = String.format("insert into tblRoom values (room_seq.nextVal,'%s', %s)", rname,cnt);
			
			stat.executeUpdate(sql);
			
			System.out.println("============================================================");
			System.out.println("추가 완료 되었습니다.");
			

			stat.close();
			conn.close();

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void ViewRoom() {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {

			conn = util.open();
			stat = conn.createStatement();

			String sql = 
					"select room_seq as seq, name, count from tblRoom where room_seq > 0";

			rs = stat.executeQuery(sql);
			
			System.out.println("============================================================");

			System.out.println("[번호]\t[이름]\t\t[정원]");

			while (rs.next()) {
				System.out.printf("%2s\t%5s\t%s명\n",rs.getString("seq"),rs.getString("name"),rs.getString("count"));
			}
			
			System.out.println("============================================================");
			System.out.println("엔터를 입력하세요.");
			scan.nextLine();
			
			
			rs.close();
			stat.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void ManageSubject() {
		System.out.println("============================================================");
		System.out.println("\t\t[과목 관리]");
		System.out.println("============================================================");
		System.out.println("1. 과목 조회");
		System.out.println("2. 과목 추가");
		System.out.println("3. 과목 삭제");
		System.out.println();
		System.out.println("0. 뒤로가기");
		System.out.println("============================================================");
		System.out.print("입력 : ");
		String input = scan.nextLine();
		
		
		switch(input) {
		
		case "1":
			ViewSub();
			break;
		case "2":
			addSub();
			break;
		case "3":
			deleteSub();
			break;

		case "0":
			break;
		
		}
		
	}

	private void deleteSub() {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {
			conn = util.open();
			stat = conn.createStatement();
			
			System.out.println("삭제할 과목 번호를 입력하세요.");
			System.out.print("입력 : ");
			String input = scan.nextLine();

			String sql = String.format(
					"update tblSubject set subject_Seq = -subject_Seq where subject_Seq = %s",input);

			stat.executeUpdate(sql);
			
			System.out.println("============================================================");
			System.out.println("삭제가 완료되었습니다.");
			

			stat.close();
			conn.close();


		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	private void addSub() {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {

			conn = util.open();
			stat = conn.createStatement();

			System.out.println("추가할 과목명을 입력하세요.");
			System.out.print("입력 : ");
			String cname = scan.nextLine();
			
			System.out.println("과목의 기간을 입력하세요.");
			System.out.print("입력 : ");
			String cp = scan.nextLine();
			
			
			String sql = String.format("insert into tblSubject values (subject_seq.nextVal,'%s','%s',null)", cname,cp);
			
			stat.executeUpdate(sql);
			
			System.out.println("============================================================");
			System.out.println("추가 완료 되었습니다.");
			

			stat.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void ViewSub() {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {

			conn = util.open();
			stat = conn.createStatement();

			String sql = 
					"select subject_seq as seq, name, period from tblSubject where subject_seq > 0";

			rs = stat.executeQuery(sql);
			
			System.out.println("============================================================");

			System.out.println("[번호]\t[기간]\t[과목명]");

			while (rs.next()) {
				System.out.printf("%2s\t%3s일\t%s\n",rs.getString("seq"),rs.getString("period"),rs.getString("name"));
			}
			
			System.out.println("============================================================");
			System.out.println("엔터를 입력하세요.");
			scan.nextLine();
			
			rs.close();
			stat.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	private void ManageCourse() {
		//과정 관리 메뉴
		System.out.println("============================================================");
		System.out.println("\t\t[과정 관리]");
		System.out.println("============================================================");
		System.out.println("1. 과정 조회");
		System.out.println("2. 과정 추가");
		System.out.println("3. 과정 삭제");
		System.out.println("4. 과정명 수정");
		System.out.println();
		System.out.println("0. 뒤로가기");
		System.out.println("============================================================");
		System.out.print("입력 : ");
		String input = scan.nextLine();
		
		
		switch(input) {
		
		case "1":
			ViewCourse();
			break;
		case "2":
			addCourse();
			break;
		case "3":
			deleteCourse();
			break;
		case "4":
			modifyCourse();			
			break;
		case "0":
			break;
		
		}
		
	}

	private void deleteCourse() {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {

			conn = util.open();
			stat = conn.createStatement();
			
			System.out.println("삭제할 과정 번호를 입력하세요.");
			System.out.print("입력 : ");
			String input = scan.nextLine();
			

			String sql = String.format(
					"update tblCourseList set del = 'y' where courselist_seq = %s",input);

			stat.executeUpdate(sql);
			
			System.out.println("============================================================");
			System.out.println("삭제가 완료되었습니다.");
			

			stat.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void modifyCourse() {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {

			conn = util.open();
			stat = conn.createStatement();

			System.out.println("수정할 과정 번호를 입력하세요.");
			System.out.print("입력 : ");
			String cnum = scan.nextLine();
			System.out.println("============================================================");
			
			System.out.println("수정할 과정명을 입력하세요.");
			System.out.print("입력 : ");
			String cname = scan.nextLine();
			
			String sql = String.format(
					"update tblCourseList set name = '%s' where courselist_seq = %s",cname,cnum);
			
			stat.executeUpdate(sql);
			
			System.out.println("수정이 완료되었습니다.");
			System.out.println("============================================================");

			stat.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void addCourse() {
		
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {

			conn = util.open();
			stat = conn.createStatement();

			System.out.println("추가할 과정명을 입력하세요.");
			System.out.print("입력 : ");
			String cname = scan.nextLine();
			
			
			String sql = String.format("insert into tblCourseList values ( courselist_seq.nextVal, '%s','n')", cname);
			
			stat.executeUpdate(sql);
			
			System.out.println("============================================================");
			System.out.println("추가 완료 되었습니다.");
			

			stat.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void ViewCourse() {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {

			conn = util.open();
			stat = conn.createStatement();

			String sql = "select courseList_seq as seq, name from tblCourseList where del = 'n' order by courseList_seq";

			rs = stat.executeQuery(sql);
			
			System.out.println("============================================================");

			System.out.println("[번호]\t[과정명]");

			while (rs.next()) {
				System.out.printf("%2s\t%s\n",rs.getString("seq"),rs.getString("name"));
			}
			
			System.out.println("============================================================");
			System.out.println("엔터를 입력하세요.");
			scan.nextLine();
			
			
			rs.close();
			stat.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
