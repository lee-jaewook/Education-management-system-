package com.test.DDC.admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import com.test.DDC.DBUtil;
import com.test.DDC.DDCmain;

/**
 * 관리자 로그인 구현 클래스
 * @author 전혜원
 *
 */
public class AdminMain {
	
	private static Scanner scan = new Scanner(System.in);
	private static String menuNum = null;
	
//	public static void main(String[] args) {	
//		adminLogin();	
//	}
	
	/**
	 * 관리자 계정을 탐색해 로그인해주는 메소드.
	 */
	public static void adminLogin() {
		
		String id;
		String pw;
		DBUtil util = new DBUtil();
		
		while(true) {
			
		System.out.println("============================================================");
		System.out.println("\t\t[관리자 로그인]");
		System.out.println("============================================================");
		
		System.out.print("\t\tid : ");
		id = scan.nextLine();
		System.out.print("\t\tpw : ");
		pw = scan.nextLine();
		
		//로그인 유효성 검사
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		
		ArrayList<String> idList = new ArrayList<String>(3); //id 담을 list
		ArrayList<String> pwdList = new ArrayList<String>(3); //pwd 담을 list
		
		int idNum = 0, answer = 0; //id든 목록의 번호와 로그인 상태 반환 변수
		
		try {

			conn = util.open();
			stat = conn.createStatement();
			
			String sql = "select id,pwd from tblAdmin";
			
			rs = stat.executeQuery(sql);
			
			while (rs.next()) { // 관리자 테이블 id,pwd 가져오기
				
				String result = rs.getString("id") + "\t" + rs.getString("pwd");
				
				idList.add(rs.getString("id"));
				pwdList.add(rs.getString("pwd"));				
			}
			
			for(int i=0; i<idList.size(); i++) {
				if(idList.get(i).equals(id)){ //id 있을 경우
					idNum = i;
						
				}else if(!idList.get(i).equals(id)){
					//없는 id
					answer = 1;
				}
			}

			if(pwdList.get(idNum).equals(pw)){ //pw 있을 경우
				//로그인 성공
				System.out.println("============================================================");
				System.out.printf("관리자로 로그인 되었습니다.\n");

				menu();
				return;
				
			} else if (!pwdList.get(idNum).equals(pw)){
				//틀린 pwd
				answer = 2;
			}
			
			//오류 메세지 출력
			if(answer == 1) {
				System.out.println("============================================================");
				System.out.println("없는 id입니다.");
				//다시 로그인
				return;
			}else if(answer == 2) {
				System.out.println("============================================================");
				System.out.println("비밀번호가 옳지 않습니다.");
				//다시 로그인
				return;
			}
			
			rs.close();
			stat.close();
			conn.close();
			
			if(menuNum.equals("0")) {
				return;
			}
				


		} catch (Exception e) {
			e.printStackTrace();
		}
		
		}//while
	}

	private static void menu() {
		//메인 메뉴
		
		boolean flag = true;
		
		while(flag) {
		System.out.println("============================================================");
		System.out.println("\t\t[관리자]");
		System.out.println("============================================================");
		System.out.println("\t\t 1. 교사 계정 관리");
		System.out.println("\t\t 2. 개설 과정 및 개설 과목 관리");
		System.out.println("\t\t 3. 교육생 관리");
		System.out.println("\t\t 4. 기자재 관리");
		System.out.println("\t\t 5. 시험 관리 및 성적 조회");
		System.out.println("\t\t 6. 출결 관리 및 출결 조회");
		System.out.println("\t\t 7. 과목 평가 조회");
		System.out.println("\t\t 8. 취업자 관리 및 조회");
		System.out.println("\t\t 9. 지원 활동 관리 및 조회");
		System.out.println("\t\t10. 기초 정보 관리");
		System.out.println();
		System.out.println("\t\t0. 로그아웃");
		System.out.println("============================================================");
		
		System.out.print("\t\t입력 : ");
		String num = scan.nextLine();
		
		switch (num) {

		case "1":
			//교사계정관리
			AdminTeacher at = new AdminTeacher();
			at.printTeacher();
			break;
			
		case "2":
			//개설 과정 및 개설 과목 관리
			ManageCourseAndSub cs = new ManageCourseAndSub();
			cs.exe();
			break;
			
		case "3":
			//교육생 관리
			AdminStudent as = new AdminStudent();
			as.printMain();
			break;
			
		case "4":
			//기자재 관리
			AdminItem ai = new AdminItem();
			ai.printItem();
			break;
			
		case "5":
			//시험 관리 및 성적 조회
			AdminExamScore aes = new AdminExamScore();
			aes.printExamScore();
			break;
			
		case "6":
			//출결 관리 및 출결 조회
			AdminAttandance aad = new AdminAttandance();
			aad.printAttandance();
			break;
			
		case "7":
			//과목 평가 조회
			AdminRating ar = new AdminRating();
			ar.printRating();
			break;
			
		case "8":
			//취업자 조회 및 관리
			ManageEmp emp = new ManageEmp();
			emp.exe();
			break;
			
		case "9":
			//지원 활동 조회 및 관리
			AdminAs aas = new AdminAs();
			aas.printAs();
			break;
		
		case "10":
			//기초 정보 관리
			AdminBasic ab = new AdminBasic();
			ab.printBasic();
			break;
			
		case "0":
			//로그아웃
			DDCmain main  = new DDCmain();
			main.printMain();
			}
		}
		
	}
	
	
	
}
