package com.test.DDC.teacher;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import com.test.DDC.DBUtil;

import oracle.jdbc.OracleTypes;

public class Score_Output {
	
	private static Connection conn = null;
	private static Statement stat = null;
	private static ResultSet rs = null;
	CallableStatement cstmt = null;
	private static DBUtil util = new DBUtil();
	private static Scanner scan = new Scanner(System.in);
	
	ArrayList<String> subjectList = new ArrayList<String>();	//과목 ArrayList
	ArrayList<String> stuScoreList = new ArrayList<String>();	//학생 ArrayList
	ArrayList<Integer> scorePercent = new ArrayList<Integer>();	//배점 ArrayList
	
	public void printSubject(String tnum) {
		
		boolean loop = true;
		
		while(loop) {
			
			System.out.println("================================================");
			System.out.println("\t\t[성적 입출력]");
			System.out.println("================================================");
//			System.out.print("교사번호입력 : ");
//			int a=scan.nextInt();
			int q = Integer.parseInt(tnum);
			getSubList(q);
			
//			System.out.println("[과목번호]\t[과정명]\t\t\t\t\t\t[강의실]\t\t\t[과정기간]\t\t[과목명]\t\t\t[과목기간]\t\t\t[교재명]\t\t\t[성적배점(필기/실기/출결)]\t\t[성적등록여부]");
//			for(int i = 0; i<subjectList.size(); i++) {
//				String[] array = subjectList.get(i).split("\t");
//				System.out.printf("%5s\t%s\t\t%4s\t%10s ~ %s%s\t%s\t%3s ~ %s\t%s\t\t%s/%s/%s\t\t%5s\n"
//							,array[0],array[1],array[2],array[3].replace("00:00:00", ""),array[4].replace("00:00:00", ""),array[5],array[6],array[7].replace("00:00:00", ""),array[8].replace("00:00:00", "")
//							,array[9],array[10],array[11],array[12]
//							,array[13]);
			paging2(0, subjectList);
//		}//과목 출력	
			
			System.out.println();
		
			System.out.println("===============================================================================================");
			System.out.println("0을 입력하면 뒤로가기 ");
			System.out.println();
			System.out.print("-성적을 입력할 과목의 해당번호를 입력해주세요: ");
			int b = scan.nextInt();
			if(b == 0) {
				Main c = new Main();
				c.mainView("3");
			}
			getStudentScore(b);
			System.out.println("===============================================================================================");
			
			
			
//			System.out.println("[학생번호]\t[학생명]\t[전화번호]\t\t[수료여부]\t[과목명]\t\t[시험번호]\t[수강신청번호]\t[필기점수]\t[실기점수][출결점수]\t[결과]\t[시험날짜]");
//			for(int i = 0; i<stuScoreList.size(); i++) {
//				String[] array = stuScoreList.get(i).split("\t");
//				System.out.printf("%5s\t%5s\t%s\t%5s\t%s\t%5s\t%7s\t\t%5s\t%5s\t%5s\t%3s\t%s\n"
//							,array[0],array[1],array[2],array[3],array[4],array[5],array[6],array[7]
//							,array[8],array[9],array[10],array[11].replace("00:00:00", "")
//							);
//			}//학생 점수 출력
			paging(0, stuScoreList);
			
			System.out.println();
			System.out.println("===============================================================================================");
			System.out.print("성적을 입력할 학생의 번호를 입력하세요 : ");
			
			int c = scan.nextInt();
			System.out.println();
			
			
			getScorePercent(b, c);	// 점수 배점 가져오기
			
			System.out.println("===============================================================================================");
			System.out.printf("- 필기 점수를 입력해주세요. (배점 : %d)",scorePercent.get(0));
			System.out.print("	필기 점수 : ");
			int s_sub = scan.nextInt();	
			
			System.out.println();
			System.out.printf("- 실기 점수를 입력해주세요. (배점 : %d)", scorePercent.get(1));
			System.out.print("	실기 점수 : ");
			int s_obj = scan.nextInt();
			
			System.out.println();
			System.out.printf("- 출석 점수를 입력해주세요. (배점 : %d)", scorePercent.get(2));
			System.out.print("	출석 점수 : ");
			int s_att = scan.nextInt();
			System.out.println();
			System.out.println("===============================================================================================");
			
			
			if(s_sub <= scorePercent.get(0) && s_obj <= scorePercent.get(1) && s_att <= scorePercent.get(2)) {
				InsertScore(b,c,s_sub, s_obj,s_att);
				System.out.println();
			}else {
				
				System.out.println("입력한 점수가 배점보다 큽니다.");
				System.out.println();
			}
			
			
		}
		
		
	
		
		
		
		
		
	}//printSubject
	
	
	private void InsertScore(int sub_seq, int regi_seq, int s_sub, int s_obj, int s_att) {//점수 입력
		try {
			conn = util.open();
			stat = conn.createStatement();
			
			
			String sql = "";
			sql = "{call procUpdateScore(?,?,?,?,?)}";
			
			cstmt = conn.prepareCall(sql);
			
			cstmt.setInt(1, sub_seq);
			cstmt.setInt(2, regi_seq);
			cstmt.setInt(3, s_sub);
			cstmt.setInt(4, s_obj);
			cstmt.setInt(5, s_att);
			cstmt.executeUpdate();
			
			
			System.out.println("점수 입력 완료");
			conn.close();
			stat.close();
		} catch (Exception e) {
			System.out.println("오류");
		}
		
		
	}//InsertScore


	

	private void getScorePercent(int b, int c) {//점수 배점 출력
		try {
			conn = util.open();
			stat = conn.createStatement();
			
			String sql = "";
			sql = "{call procGetPercent(?,?,?)}";
			
			cstmt = conn.prepareCall(sql);
			
			
			cstmt.setInt(1, b);
			cstmt.setInt(2, c);
			cstmt.registerOutParameter(3, OracleTypes.CURSOR);
			cstmt.executeUpdate();
			
			rs = (ResultSet)cstmt.getObject(3);
			while(rs.next()) {
				scorePercent.add(rs.getInt("객관식배점"));
				scorePercent.add(rs.getInt("주관식배점"));
				scorePercent.add(rs.getInt("출결배점"));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}//getScorePercent





	private void getStudentScore(int sub_seq) {
		try {
			conn = util.open();
			stat = conn.createStatement();
			
			stuScoreList.clear();
			String sql = "";
			sql = "{call procScoreSelectSub(?,?)}";			
			cstmt = conn.prepareCall(sql);
			
			cstmt.registerOutParameter(2, OracleTypes.CURSOR);
			cstmt.setInt(1, sub_seq);
			cstmt.executeUpdate();
			
			rs = (ResultSet)cstmt.getObject(2);
			
			while(rs.next()) {
				stuScoreList.add(rs.getString("학생번호")+"\t"+rs.getString("학생명")+"\t"+rs.getString("전화번호")+"\t"+
						rs.getString("수료여부")+"\t"
						+rs.getString("과목명")+"\t"+rs.getString("시험번호")+"\t"+rs.getString("수강신청번호")+"\t"
						+rs.getString("필기점수")+"\t"+rs.getString("실기점수")+"\t"+rs.getString("출결점수")+"\t"
						+rs.getString("결과")+"\t"+rs.getString("시험날짜"));
			}
			
			conn.close();
			rs.close();
			cstmt.close();
		} catch (Exception e) {
			System.out.println("오류 발생 - select ProcsubjectScore");
			e.printStackTrace();
		}
		
	}//getScoreList

	public void getSubList(int t_seq) {//교사번호 받아서 입력
		
		try {
			conn = util.open();
			stat = conn.createStatement();
			
			subjectList.clear();
			String sql = "";
			sql = "{call procScoreSubject(?,?)}";			
			cstmt = conn.prepareCall(sql);
			
			cstmt.registerOutParameter(2, OracleTypes.CURSOR);
			cstmt.setInt(1, t_seq);
			cstmt.executeUpdate();
			
			rs = (ResultSet)cstmt.getObject(2);
			
			while(rs.next()) {
				subjectList.add(rs.getString("과목번호")+"\t"+rs.getString("과정명")+"\t"+rs.getString("강의실")+"\t"
						+rs.getString("과정기간(시작)")+"\t"+rs.getString("과정기간(끝)")+"\t"+
						"\t"+rs.getString("과목명")+"\t"+
						rs.getString("과목기간(시작)")+"\t"+rs.getString("과목기간(끝)")+"\t"+
						rs.getString("교재명")+"\t"+rs.getString("실기배점")+"\t"+
						rs.getString("필기배점")+"\t"+rs.getString("출결배점")+"\t"+
						rs.getString("성적등록여부")+"\t");		
			}
			conn.close();
			rs.close();
			cstmt.close();
			
			
		} catch (Exception e) {
			System.out.println("오류 발생 - getSubList");
			e.printStackTrace();
		}
	}//getSubList
	
	private void paging(int page, ArrayList<String> list) {
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

			System.out.println("[학생번호]\t[학생명]\t[전화번호]\t\t[수료여부]\t[과목명]\t[시험번호]\t[수강신청번호]\t[필기점수][실기점수][출결점수]\t[결과]\t[시험날짜]");

			for (int i = (page - 1) * 10; i < page * 10; i++) {

				if (i >= list.size()) {
					break;

				} else {
					
//					
//					for(int j = 0; j<stuScoreList.size(); j++) {
//						String[] array = stuScoreList.get(i).split("\t");
//						System.out.printf("%5s\t%5s\t%s\t%5s\t%s\t%5s\t%7s\t\t%5s\t%5s\t%5s\t%3s\t%s\n"
//									,array[0],array[1],array[2],array[3],array[4],array[5],array[6],array[7]
//									,array[8],array[9],array[10],array[11].replace("00:00:00", "")
//									);
					
					String[] array = stuScoreList.get(i).split("\t");

					System.out.printf("%5s\t\t%5s\t%s\t\t%5s\t\t%s\t\t%5s\t%7s\t\t%5s\t %5s\t %5s\t\t%3s\t%s\n"
							,array[0],array[1],array[2],array[3],array[4],array[5],array[6],array[7]
									,array[8],array[9],array[10],array[11].replace("00:00:00", ""));

				} // if
			} // for

		} // if
		
	}//paging
	
	
	void paging2(int page, ArrayList<String> list) {
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

			System.out.println("[과목번호]\t\t[과정명]\t\t\t\t[강의실]\t\t\t[과정기간]\t\t[과목명]\t\t\t[과목기간]\t\t\t[교재명]\t\t\t[성적배점(필기/실기/출결)]\t\t[성적등록여부]");

			for (int i = (page - 1) * 10; i < page * 10; i++) {

				if (i >= list.size()) {
					break;

				} else {
					
//					System.out.println("[과목번호]\t[과정명]\t\t\t\t\t\t[강의실]\t\t\t[과정기간]\t\t[과목명]\t\t\t[과목기간]\t\t\t[교재명]\t\t\t[성적배점(필기/실기/출결)]\t\t[성적등록여부]");
//					for(int i = 0; i<subjectList.size(); i++) {
//						String[] array = subjectList.get(i).split("\t");
//						System.out.printf("%5s\t%s\t\t%4s\t%10s ~ %s%s\t%s\t%3s ~ %s\t%s\t\t%s/%s/%s\t\t%5s\n"
//									,array[0],array[1],array[2],array[3].replace("00:00:00", ""),array[4].replace("00:00:00", ""),array[5],array[6],array[7].replace("00:00:00", ""),array[8].replace("00:00:00", "")
//									,array[9],array[10],array[11],array[12]
//									,array[13]);
						
					String[] array = subjectList.get(i).split("\t");

					System.out.printf("%5s\t\t%s\t\t%4s\t%10s ~ %s%s\t\t%s\t%3s ~ %s\t%s\t\t%s/%s/%s\t\t\t%5s\n"
							,array[0],array[1],array[2],array[3].replace("00:00:00", ""),array[4].replace("00:00:00", ""),array[5],array[6],array[7].replace("00:00:00", ""),array[8].replace("00:00:00", "")
							,array[9],array[10],array[11],array[12]
							,array[13]);

				} // if
			} // for

		} // if
		
	}//paging2
	
	
	
}//Score_Output
