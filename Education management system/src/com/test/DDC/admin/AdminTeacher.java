package com.test.DDC.admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.test.DDC.DBUtil;

/**
 * 교사 계정을 출력하고 등록, 수정, 삭제 가능한 클래스
 * @author 전혜원
 *
 */
public class AdminTeacher {
	
	private static Connection conn = null;
	private static Statement stat = null;
	private static ResultSet rs = null;
	private static DBUtil util = new DBUtil();
	private static Scanner scan = new Scanner(System.in);
	
	private static ArrayList<String> teacherList = new ArrayList<String>(10); //교사 목록 저장할 list
	
	//교사이름, 과정이름 저장할 hashMap
	private static HashMap<String,String> mapInfo = new HashMap<String, String>();
	
	private static String teacherName = new String();
	private static String course = new String();
	
	/**
	 * 교사계정관리
	 * 교사 리스트를 출력하고, 메뉴를 출력
	 */
	public void printTeacher() {
		
		boolean loop = true;
		int result = 0;

		while (loop) {
			System.out.println("===========================================================================");
			System.out.println("\t\t\t[교사 계정 관리]");
			System.out.println("===========================================================================");
			teacherAccount("00"); // 교사 리스트 저장
			System.out.println("[교사번호]\t[교사명]\t[주민번호 뒷자리]\t[전화번호]");
			for (int i = 0; i < teacherList.size(); i++) {
				String[] array = teacherList.get(i).split("\t");
				System.out.printf("%8s\t%4s\t\t%13s\t\t%13s\n",array[0],array[1],array[2],array[3]);
			}
			System.out.println("===========================================================================");
			System.out.println("- 상세보기를 원하시면 해당 교사 번호를 입력해주세요.");
			System.out.println();
			System.out.println("a. 교사 정보 등록");
			System.out.println("b. 교사 정보 수정");
			System.out.println("c. 교사 정보 삭제");
			System.out.println();
			System.out.println("0. 뒤로가기");
			System.out.println("===========================================================================");
			System.out.print("입력 : ");
			String input = scan.nextLine();

			switch (input) {

			case "1":
			case "2":
			case "3":
			case "4":
			case "5":
			case "6":
			case "7":
			case "8":
			case "9":
			case "10":
				// 상세보기 (맡은 과정, 과목, 강의 가능 과목)
				
				//교사 정보 저장
				String[] array = teacherList.get(Integer.parseInt(input)-1).split("\t");
				mapInfo.put("name", array[1]);
				mapInfo.put("teacherSeq",array[0]);
//				name = array[1]; //교사 이름 저장
				printCourse(input);
				break;
			case "a":
				// 교사 계정 등록
				teacherAccount(input);
				break;
			case "b":
				// 교사 계정 수정
				teacherModify();
				break;
			case "c":
				//교사 계정 삭제
				teacherDelete();
				break;
			case "0":
				// 뒤로가기
//				loop=false;
				return;
			}
		} // while

	}
	

	private void printCourse(String num) {
		// 맡은 과정 출력
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();
		
		//맡은 과정 목록 담을 리스트 
		ArrayList<String> courseList = new ArrayList<String>(5);
		//과정번호 담을 리스트
		ArrayList<String> seqList = new ArrayList<String>(5);
		

		try {

			conn = util.open();
			stat = conn.createStatement();

			System.out.println("========================================================================================================================");
			System.out.println("		[교사 정보 상세보기]");
			System.out.println("========================================================================================================================");
			
			System.out.printf("교사 %s 님의 정보입니다.",mapInfo.get("name"));
			System.out.println();
			
			String sql = String.format("select oc.opencourse_seq as seq, cll.name as name, oc.startDate||'~'||oc.endDate as 과정기간, oc.countStudent as 정원, oc.room_seq as 강의실," + 
					"    case when oc.endDate < sysdate then '강의종료'" + 
					"         when oc.startDate < sysdate and oc.endDate > sysdate then '강의중'" + 
					"         when oc.startDate > sysdate then '강의예정' end as 강의진행여부" + 
					"    from tblTeacher t inner join tblTeacherCourse tc on t.teacher_seq = tc.teacher_seq" + 
					"        inner join tblOpenCourse oc on tc.opencourse_seq = oc.opencourse_seq" + 
					"            inner join tblCourseList cll on oc.courselist_seq = cll.courselist_seq" + 
					"                where t.teacher_seq = '%s'", num);
			
			rs = stat.executeQuery(sql);
			
			while(rs.next()) { //쿼리 결과 저장하기
				String result = rs.getString("name") + "\t"
								+ rs.getString("과정기간") + "\t"
								+ rs.getString("정원") + "\t"
								+ rs.getString("강의실") + "\t"
								+ rs.getString("강의진행여부");
				
				courseList.add(result);
				seqList.add(rs.getString("seq"));
			}
			
			//결과 출력
			System.out.println("[번호]\t\t[과정명]\t\t\t\t\t[과정기간]\t\t[정원]\t[강의실]\t[강의진행여부]\n");
			
			for(int i=0; i<seqList.size(); i++) {
				
				String[] array = courseList.get(i).split("\t");
				
				System.out.printf("%2d\t%s\t%s\t%s\t%s\t\t%s\n",i+1,array[0],array[1],array[2],array[3],array[4]);
			}
			
			System.out.println();
			System.out.println("0. 뒤로가기");
			System.out.println();
			System.out.println("과정별 과목을 보시려면 번호를 입력하세요.");
			System.out.println("------------------------------------------------------------------------------------------------------------------------");
			System.out.print("입력 : ");
			String input = scan.nextLine();
			
			//입력 유효성 검사
			if(seqList.indexOf(input) >= 0 || input.equals("0")) {
				switch(input) {
				
				case "0":
					//뒤로가기
					return;
				case "1":
				case "2":
				case "3":
					//과정 정보 저장
					String[] array = courseList.get(Integer.parseInt(input)-1).split("\t");
					mapInfo.put("courseSeq", seqList.get(Integer.parseInt(input)-1));
					mapInfo.put("courseName", array[0]);
//					course = array[0];
					//과정별 과목보기
					System.out.println();
					printSubject(seqList.get(Integer.parseInt(input)-1)); 
					break;
				}
				
			}else {
				System.out.println("해당 번호는 없는 번호입니다.");
			}
			
			stat.close();
			conn.close();
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	private void printSubject(String courseSeq) {
		//과정별 과목 출력하기
		
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {

			conn = util.open();
			stat = conn.createStatement();
			
			System.out.println("============================================================");
			System.out.println("		[과정별 과목 보기]");
			System.out.println("============================================================");
			
			System.out.printf("교사 %s 님의 정보입니다.",mapInfo.get("name"));
			System.out.println();
			System.out.printf("과정명 : %s\n",mapInfo.get("courseName"));
			System.out.println("------------------------------------------------------------");
			
			String sql = String.format("select s.name as 과목명, s.period||'일' as 과목기간, b.name as 교재명" + 
					"    from tblTeacher t inner join tblTeacherCourse tc on t.teacher_seq = tc.teacher_seq" + 
					"        inner join tblOpenCourse oc on tc.opencourse_seq = oc.opencourse_seq" + 
					"            inner join tblCourseList cll on oc.courselist_seq = cll.courselist_seq" + 
					"                inner join tblCourseSubject cs on cll.courselist_seq = cs.courselist_seq" + 
					"                    inner join tblSubject s on cs.subject_seq = s.subject_seq" + 
					"                        inner join tblbook b on s.book_seq = b.book_seq" + 
					"                            inner join tblSubjectSchedule ss on s.subject_seq = ss.subject_seq" + 
					"                                where t.teacher_seq = %s and oc.opencourse_seq= %s" + 
					"                                     and oc.enddate>=ss.enddate and oc.startdate<=ss.startdate"
					, mapInfo.get("teacherSeq"), mapInfo.get("courseSeq"));

			System.out.println("[과목명]\t\t[과목기간]\t[교재명]\n");
			rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				System.out.printf("%10s\t\t%3s\t\t%s\n",rs.getString("과목명"),rs.getString("과목기간"),rs.getString("교재명"));
//				System.out.println(rs.getString("과목명") + "\t" + rs.getString("과목기간") + "\t" + rs.getString("교재명"));
			}
			
			System.out.println();
			System.out.println("0. 처음으로 가기");
			System.out.println("------------------------------------------------------------");
			System.out.print("입력 : ");
			String input = scan.nextLine();

			stat.close();
			conn.close();
			rs.close();
			
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}//printSubject()

	
	private void teacherDelete() {
		// 교사 정보 삭제
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {

			conn = util.open();
			stat = conn.createStatement();

			int flag = 0;
			
			System.out.println("============================================================");
			System.out.println("		[교사 정보 삭제]");
			System.out.println("============================================================");
			System.out.print("삭제할 교사 번호 : ");
			String num = scan.nextLine();
			
			for(String s:teacherList) {
				//num이 교사리스트 번호 안에 있는 번호인지 유효성 검사
				if(s.contains(num)) { 
					flag = 1;
				}
			}
			
			if (flag == 1) {// 유효번호 있을 경우

				String sql = String.format("SELECT teacher_seq as seq,name, substr(ssn,8) as ssn, tel, (SELECT  LISTAGG(s.name, ',') WITHIN GROUP (ORDER BY s.name) " + 
						"    FROM tblTeacher t" + 
						"        INNER JOIN tblAvlSubject a" + 
						"            ON t.teacher_seq = a.teacher_seq " + 
						"                    INNER JOIN tblSubject s" + 
						"                        ON a.subject_seq = s.subject_seq" + 
						"                             where t.teacher_Seq = %s) as AvlSubject" + 
						"        FROM tblTeacher" + 
						"        where teacher_seq = %s"
						, num,num);

				
				rs = stat.executeQuery(sql);
				rs.next();
				
				System.out.println("[번호]\t[교사명]\t[전화번호]\t[강의가능과목]");
				
				System.out.printf("%2s\t%3s\t\t%13s\t%s\n",rs.getString("seq"),rs.getString("name"),rs.getString("tel"),rs.getString("avlSubject"));
				
				System.out.println("------------------------------------------------------------");
				System.out.println("정말로 삭제하시겠습니까? (y/n)");
				System.out.print("입력 : ");
				String input = scan.nextLine();
				
				if(input.equals("y")) {
//					sql = String.format("update tblTeacher set teacher_seq = -teacher_seq where teacher_seq = %s", num);
					sql = String.format("update tblTeacher set del = 'y' where teacher_seq = %s", num);
					stat.executeUpdate(sql);
					System.out.println("------------------------------------------------------------");
					System.out.println("삭제가 완료되었습니다.");

					stat.close();
					conn.close();
					rs.close();
					return;

				} else {
					System.out.println("------------------------------------------------------------");
					System.out.println("삭제가 취소 되었습니다. 이전 화면으로 돌아갑니다.");

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
		
		
	}


	private void teacherModify() {
		//교사 정보 수정
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {
			conn = util.open();
			stat = conn.createStatement();

			int flag = 0;
			
			System.out.println("============================================================");
			System.out.println("		[교사 정보 수정]");
			System.out.println("============================================================");
			System.out.print("수정할 교사 번호 : ");
			String num = scan.nextLine();
			
			for(int i=0; i<teacherList.size(); i++) {
				//num이 교사리스트 번호  안에 있는 번호인지 유효성 검사

				String[] array = teacherList.get(i).split("\t");
				
				if(array[0].equals(num)) { 
					flag = 1;
					teacherName = array[1];
				}
			}
			
			if (flag == 1) { // 유효번호 있을 경우
				System.out.println("------------------------------------------------------------");
				System.out.println("수정할 필드를 선택하세요.");
				System.out.println("1. 이름");
				System.out.println("2. 주민번호");
				System.out.println("3. 전화번호");
				System.out.println("4. 강의 가능 과목");
				System.out.println("------------------------------------------------------------");
				System.out.print("입력 : ");
				String input = scan.nextLine();
				
				System.out.println("------------------------------------------------------------");
				System.out.println("수정할 값을 입력하세요.");
				
				if(input.equals("2")) {
					System.out.println("주민번호는 '-'를 붙여서 입력해주세요.");
					inputModify(input, num);
				}else if(input.equals("3")) {
					System.out.println("전화번호는 '-'를 붙여서 입력해주세요.");
					inputModify(input, num);
				}else if(input.equals("1")) {
					inputModify(input, num);
				} else { //강의 가능 과목 추가
					
					//현재 강의 가능 과목 담을 리스트
					ArrayList<String> subList = new ArrayList<String>();
					
					//현재 강의 가능 과목 출력
					System.out.println("------------------------------------------------------------");
					System.out.printf("현재 강의 가능 과목 목록입니다.\n");

					String sql = String.format(
							"SELECT  LISTAGG(s.name, ',') WITHIN GROUP (ORDER BY s.name) as sublist " + 
							"    FROM tblTeacher t" + 
							"        INNER JOIN tblAvlSubject a" + 
							"            ON t.teacher_seq = a.teacher_seq " + 
							"                    INNER JOIN tblSubject s" + 
							"                        ON a.subject_seq = s.subject_seq" + 
							"                             where t.teacher_Seq = %s and t.del = 'n'", num);
					
					rs = stat.executeQuery(sql);
					
					rs.next();
					
					if(rs.next()) {
						subList.add(rs.getString("sublist"));
						
						String[] array = subList.get(0).split(",");
						
						for(int i=0; i<array.length; i++) {
							System.out.printf("%s\n", array[i]);
						}						
					}else {
						System.out.println("강의 가능 과목이 없습니다.");
					}
					
					rs.close();
						
					System.out.println("------------------------------------------------------------");
					
					//과목 전체 출력
					System.out.println("추가할 과목을 입력하세요.");
					System.out.println("[번호]\t[과목명]\n");
					
					sql = "select subject_seq as seq, name as sname from tblSubject";
					rs = stat.executeQuery(sql);
					
					while(rs.next()) {	
						
						if(subList.size() != 0) {
							//중복 거르기_해당 교사의 강의 가능 과목이면 출력X
							if(subList.get(0).contains(rs.getString("sname"))) {
								continue;
							}else {
								System.out.printf("%s\t%s\n",rs.getString("seq"),rs.getString("sname"));
							}							
						}else {
							System.out.printf("%s\t%s\n",rs.getString("seq"),rs.getString("sname"));							
						}
					}//while
					
					System.out.println("------------------------------------------------------------");
					System.out.print("입력 : ");
					String snum = scan.nextLine();
					
					sql = String.format("insert into tblAvlSubject values (avlSubject_seq.nextVal, %s, %s)"
							,num,snum);
					stat.executeUpdate(sql);
					
					System.out.println("------------------------------------------------------------");
					System.out.println("과목이 추가 되었습니다.");
					
					
				}
				
				
			} else {
				System.out.println("------------------------------------------------------------");
				System.out.println("없는 교사 번호입니다. 이전 화면으로 돌아갑니다.");
				return;
			}	

				
			stat.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}//teacherModify()
	

	private void inputModify(String input,String num) {
		// 수정
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {

			conn = util.open();
			stat = conn.createStatement();

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
					sql = String.format("update tblTeacher set name='%s' where teacher_seq = '%s'", modify, num);
					stat.executeUpdate(sql);
					break;
				case "2":
					sql = String.format("update tblTeacher set ssn='%s' where teacher_seq = '%s'", modify, num);
					stat.executeUpdate(sql);
					break;
				case "3":
					sql = String.format("update tblTeacher set tel='%s' where teacher_seq = '%s'", modify, num);
					stat.executeUpdate(sql);
					break;
				case "4":
					sql = String.format(
							"update tblTeacher set regiDate=to_date('%s','yyyy-mm-dd') where teacher_seq = '%s'",
							modify, num);
					stat.executeUpdate(sql);
					break;
				}
			}
			
			System.out.println("------------------------------------------------------------");
			System.out.println("수정이 완료되었습니다.");
			
			stat.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	private void teacherAccount(String input) {
		//교사 계정 관리 + 등록
		
		try {

			conn = util.open();
			stat = conn.createStatement();
			
			String sql = null;
			
			teacherList.clear();
			sql = "select teacher_seq as seq, name, substr(ssn,8) as ssn, tel from tblTeacher where del = 'n' order by teacher_seq";
			rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				teacherList.add(rs.getString("seq") + "\t" + rs.getString("name") + "\t" + rs.getString("ssn") + "\t" + rs.getString("tel"));
			}

			switch(input) {

			case "a": //교사 계정 등록
				System.out.println("============================================================");
				System.out.println("\t\t[교사 정보 등록]");
				System.out.println("============================================================");
				System.out.print("교사이름 : ");
				String name = scan.nextLine();
				System.out.print("주민번호 : ");
				String ssn = scan.nextLine();
				System.out.print("전화번호 : ");
				String tel = scan.nextLine();
				System.out.println("------------------------------------------------------------");
				System.out.println("이대로 등록하시겠습니까? (y/n) : ");
				String answer = scan.nextLine();
				
				if(answer.equals("y")) {
					sql = String.format("insert into tblTeacher (teacher_seq,name,ssn,tel) values (teacher_seq.nextVal,'%s','%s','%s')", name,ssn,tel);
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
