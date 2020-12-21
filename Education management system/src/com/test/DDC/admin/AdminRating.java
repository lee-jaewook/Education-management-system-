package com.test.DDC.admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.test.DDC.DBUtil;

/**
 * 과목평가 메뉴를 출력하는 클래스
 * @author 전혜원
 *
 */
public class AdminRating {

	private static Scanner scan = new Scanner(System.in);
	
	//과정 목록 담을 list
	ArrayList<String> courseList = new ArrayList<String>(20);
	
	HashMap<String, String> mapInfo = new HashMap<String, String>();
	
	/**
	 * 과목평가 메뉴 출력 메소드
	 */
	public void printRating(){
		
		while(true) {
			
			int flag = 0;
			
			System.out.println("============================================================");
			System.out.println("		[과목평가]");
			System.out.println("============================================================");
			//과정목록 불러오기+출력
			storeList();
			
			System.out.println();
			System.out.println("0. 뒤로가기");
			System.out.println();
			System.out.println("과목별 평가를 조회할 과정번호를 입력하세요.");
			System.out.println("------------------------------------------------------------");
			System.out.print("입력 : ");
			String input = scan.nextLine();
			
			//0 또는 과정목록에 있는 번호 입력되었는지 확인
			for(int i=0; i<courseList.size(); i++) {
				String[] array = courseList.get(i).split("\t");
				if(array[0].contains(input)) {
					flag = 1;
					//과정명 입력하기
					mapInfo.put("courseName", array[1]);
					break;
				}
			}
			
			if(input.equals("0")) {
				//뒤로가기
				return;
			}else if(flag == 1) {
				//제대로 된 과정 번호
				//과목별 평점 조회 하기
				subjectRating(input);
				
			}else {				
				System.out.println("------------------------------------------------------------");
				System.out.println("잘못된 과정번호입니다.");
				continue;
			}
			
			
		}//while_loop
		
	}//printRatijng()

	private void subjectRating(String input) {
		// 과정별 과목 평점 출력하기
		
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {

			conn = util.open();
			stat = conn.createStatement();

			System.out.println("============================================================");
			System.out.println("		[과정별 과목 평가]");
			System.out.println("============================================================");
			
			//선택 과정의 평균
			String sql = "select cll.name as 과정명, round(avg(sr.ratingScore),1) as 평균" + 
					"    from tblSubjectRating sr inner join tblsubjectschedule ss on sr.subjectschedule_seq = ss.subjectschedule_seq" + 
					"        inner join tblOpenCourse oc on ss.opencourse_seq = oc.opencourse_seq" + 
					"            inner join tblCourseList cll on oc.courselist_seq = cll.courselist_seq" + 
					"                group by cll.name";
			
			rs = stat.executeQuery(sql);
			rs.next();
			
			System.out.printf("과정명 : %s\n",mapInfo.get("courseName"));
			System.out.printf("과정평점 : %s\n",rs.getString("평균"));
			System.out.println();
			System.out.println("[과목명]\t\t[평점]\n");
			
			sql = "select s.name as 과목명,round(avg(sr.ratingScore),1) as 평균\n" + 
					"    from tblSubjectRating sr inner join tblsubjectschedule ss on sr.subjectschedule_seq = ss.subjectschedule_seq\n" + 
					"        inner join tblSubject s on ss.subject_seq = s.subject_seq\n" + 
					"                group by s.name";
			rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				System.out.printf("%s\t\t%s\n",rs.getString("과목명"),rs.getString("평균"));
			}
			
			System.out.println();
			System.out.println("0. 뒤로가기");
			System.out.println("------------------------------------------------------------");
			System.out.print("입력 : ");
			String num = scan.nextLine();
		
			
			rs.close();
			stat.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void storeList() {
		//과정목록 불러오기 + 출력
		
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {

			conn = util.open();
			stat = conn.createStatement();

			System.out.println("[번호]\t\t\t[과정명]\t\t\t\t\t[과정기간]\t\t[교사명]");
			
			String sql = String.format("select oc.openCourse_seq, cll.name as 과정명, oc.startDate||'~'||oc.endDate as 과정기간, t.name as 교사명" + 
					" from tblTeacher t inner join tblTeacherCourse tc on t.teacher_seq = tc.teacher_seq" + 
					" inner join tblOpenCourse oc on tc.openCourse_seq = oc.openCourse_seq" + 
					" inner join tblCourseList cll on oc.courseList_seq = cll.courseList_seq" + 
					" order by openCourse_Seq");
			
			rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				String result = rs.getString("openCourse_seq")+"\t"+rs.getString("과정명")+"\t"+rs.getString("과정기간")+"\t"+rs.getString("교사명");
				courseList.add(result);
				System.out.printf("%3s\t%30s\t\t%15s\t %3s\n"
						 , rs.getString("openCourse_seq")
						 , rs.getString("과정명")
						 , rs.getString("과정기간")
						 , rs.getString("교사명"));
			}
			
			rs.close();
			stat.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}//storeList()
	
	
	
}
