///*
// * 배점 입출력 모든 과목 출력 클래스
// * 
// * */
//
//package Teacher;
//
//import java.sql.CallableStatement;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//import oracle.jdbc.OracleTypes;
//
//public class ScoringInOut {
//
//	private void paging(int page, ArrayList<String> list) {
//		// 페이징 메소드
//		// 페이지 출력하는 메소드
//
//		// 검색결과 0일 경우 true
//		if (list.size() == 0) {
//			System.out.println("검색 결과가 없습니다.");
//			return;
//		} else {
//
//			// 최대 페이지 계산하기
//			int maxPage = list.size() % 10 > 0 ? list.size() / 10 + 1 : list.size() / 10;
//
//			// 유효한 페이지 아닐 경우
//			if (page <= 0) {
//				page = maxPage;
//			} else if (page > maxPage) {
//				page = 1;
//			}
//
//			System.out.printf("현재 %d 페이지 입니다.\n", page);
//
//			System.out.println(
//					"[교육생명]\t\t\t[과정명]\t\t\t[과정기간]\t\t[강의실]\t[과목번호]\t[과목명]\t\t[과목기간]\t\t\t[교재명]\t\t\t[실기배점]\t[필기배점]\t[출결배점]");
//
//			for (int i = (page - 1) * 10; i < page * 10; i++) {
//
//				if (i >= list.size()) {
//					break;
//
//				} else {
//
//					String[] array = list.get(i).split("\t");
//
//					System.out.printf("%s\t\t%s\t\t%s\t%s\t%s\t\t%s\t\t%s\t\t%s\t\t%s\t%s\t%s\n", array[0], array[1],
//							array[2], array[3], array[4], array[5], array[6], array[7], array[8], array[9], array[10]);
//
//				} // if
//			} // for
//
//		} // if
//
//	}// paging
//
//	private void paging2(int page2, ArrayList<String> list2) {
//		// 페이징 메소드
//		// 페이지 출력하는 메소드
//
//		// 검색결과 0일 경우 true
//		if (list2.size() == 0) {
//			System.out.println("검색 결과가 없습니다.");
//			return;
//		} else {
//
//			// 최대 페이지 계산하기
//			int maxPage = list2.size() % 10 > 0 ? list2.size() / 10 + 1 : list2.size() / 10;
//
//			// 유효한 페이지 아닐 경우
//			if (page2 <= 0) {
//				page2 = maxPage;
//			} else if (page2 > maxPage) {
//				page2 = 1;
//			}
//
//			System.out.printf("현재 %d 페이지 입니다.\n", page2);
//
//			System.out.println("[과목명]\t\t[유형]\t\t[문제]\t\t[필기배점][실기배점][출결배점]");
//
//			for (int i = (page2 - 1) * 10; i < page2 * 10; i++) {
//
//				if (i >= list2.size()) {
//					break;
//
//				} else {
//
//					String[] array = list2.get(i).split("\t");
//
//					System.out.printf("%s\t\t%s\t\t%s\t\t%s%s%s", array[0], array[1], array[2], array[3], array[4],
//							array[5], array[6]);
//
//				} // if
//			} // for
//
//		} // if
//
//	}// paging
//
//	public void ScoringView(String tnum) {
//
//		Connection conn = null;
//		CallableStatement stat = null;
//		ResultSet rs = null;
//		Scanner scan = new Scanner(System.in);
//		DBUtil util = new DBUtil();
//
//		try {
//
//			String sql = "{ call procScoringOut(?,?) }";
//
//			conn = util.open();
//			stat = conn.prepareCall(sql);
//
//			stat.setString(1, tnum);
//			stat.registerOutParameter(2, OracleTypes.CURSOR);
//
//			stat.executeQuery();
//
//			ArrayList<String> sublist = new ArrayList<String>();
//
//			int page = 1;
//
//			rs = (ResultSet) stat.getObject(2);
//
//			// 과목 목록 출력
//			System.out.println(
//					"========================================================================================================================================================");
//			System.out.println("\t\t\t\t\t과목 목록 출력");
//			System.out.println(
//					"========================================================================================================================================================");
////			System.out.println("[교육생명]\t\t\t[과정명]\t\t\t[과정기간]\t\t[강의실]\t[과목번호]\t[과목명]\t\t[과목기간]\t\t\t[교재명]\t\t\t[실기배점]\t[필기배점]\t[출결배점]");
//
//			while (rs.next()) {
////
////				System.out.printf("%s\t\t%s\t\t%s\t%s\t%s\t\t%s\t\t%s\t\t%s\t\t%s\t%s\t%s\n", rs.getString("교육생명"),
////						rs.getString("과정명"), rs.getString("과정기간"), rs.getString("강의실"), rs.getString("과목번호"),
////						rs.getString("과목명"), rs.getString("과목기간"), rs.getString("교재명"), rs.getString("실기배점"),
////						rs.getString("필기배점"), rs.getString("출결배점"));
//				sublist.add(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", rs.getString("교육생명"),
//						rs.getString("과정명"), rs.getString("과정기간"), rs.getString("강의실"), rs.getString("과목번호"),
//						rs.getString("과목명"), rs.getString("과목기간"), rs.getString("교재명"), rs.getString("실기배점"),
//						rs.getString("필기배점"), rs.getString("출결배점")));
//
//			}
//
//			System.out.println(
//					"========================================================================================================================================================");
//			System.out.println();
//			System.out.println();
//			conn.close();
//			stat.close();
//			rs.close();
//
//			try {
//				paging(page, sublist);
//
//				String pnum = null;
//
//				while (true) {
//					System.out.println("a. 다음페이지");
//					System.out.println("b. 이전페이지");
//					System.out.println("c. 처음으로 돌아가기");
//
//					System.out.println(
//							"========================================================================================================================================================");
//					System.out.print("배점을 추가하려면 과목 번호를 입력하세요. : ");
//					pnum = scan.nextLine();
//
//					if (pnum.equals("a")) {
//						page++;
//						paging(page, sublist);
//					} else if (pnum.equals("b")) {
//						page--;
//						paging(page, sublist);
//					} else if (pnum.equals("c")) {
//						Main q = new Main();
//						q.mainView("3");
//					} else {
//						// 과목 번호 입력시
//						break;
//					}
//				}
//
//				System.out.print("필기 배점 입력 : ");
//				String pobject = scan.nextLine();
//				System.out.print("실기 배점 입력 : ");
//				String psubject = scan.nextLine();
//				System.out.print("출결 배점 입력 : ");
//				String patt = scan.nextLine();
//				System.out.print("시험 날짜 입력(yy/mm/dd) : ");
//				String pdate = scan.nextLine();
//				System.out.print("과목 스케줄 번호 입력 : ");
//				String psubsche = scan.nextLine();
//
//				String sql2 = "{ call procAddScoring(?, ?, ?, ?, ?, ?, ?) }";
//
//				conn = util.open();
//				stat = conn.prepareCall(sql2);
//
//				stat.setString(1, tnum);
//				stat.setString(2, pnum);
//				stat.setString(3, psubject);
//				stat.setString(4, pobject);
//				stat.setString(5, patt);
//				stat.setString(6, pdate);
//				stat.setString(7, psubsche);
//
//				stat.executeUpdate();
//
//				conn.close();
//				stat.close();
//
//
//				try {
//
//					System.out.println(
//							"========================================================================================================================================================");
//					System.out.println("시험 유형, 시험 문제, 과목 스케줄 번호를 입력하세요");
//					System.out.println();
//					System.out.print("시험 유형 입력(필기, 실기) : ");
//					String title = scan.nextLine();
//					System.out.print("시험 문제 입력 :  ");
//					String type = scan.nextLine();
//					System.out.print("과목 스케줄 번호 입력 : ");
//					String num = scan.nextLine();
//
//					String sql3 = "{ call procAddExam(?, ?, ?, ?, ?) }";
//
//					conn = util.open();
//					stat = conn.prepareCall(sql3);
//
//					stat.setString(1, tnum);
//					stat.setString(2, pnum);
//					stat.setString(3, type);
//					stat.setString(4, title);
//					stat.setString(5, num);
//
//					stat.executeUpdate();
//
//					System.out.println(
//							"========================================================================================================================================================");
//					System.out.println("입력이 완료되었습니다.");
//					System.out.println(
//							"========================================================================================================================================================");
//					
//					
//					conn.close();
//					stat.close();
//					
//
//					try {
//
//						String sql4 = "{ call procScroingView(?,?) }";
//
//						conn = util.open();
//						stat = conn.prepareCall(sql4);
//
//						stat.setString(1, tnum);
//						stat.registerOutParameter(2, OracleTypes.CURSOR);
//
//						ArrayList<String> sublist2 = new ArrayList<String>();
//
//						int page2 = 1;
//
//						stat.executeQuery();
//
//						rs = (ResultSet) stat.getObject(2);
//						// System.out.println("[과목명]\t\t[유형]\t\t[문제]\t\t[필기배점][실기배점][출결배점]");
//
//						while (rs.next()) {
//
//							sublist2.add(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n", rs.getString("과목명"),
//									rs.getString("유형"), rs.getString("문제"), rs.getString("필기배점"), rs.getString("실기배점"),
//									rs.getString("출결배점")));
//
//						}
//
//						rs.close();
//						conn.close();
//						stat.close();
//
//						try {
//
//							paging2(page2, sublist2);
//
//							while (true) {
//
//								System.out.println("a. 다음페이지");
//								System.out.println("b. 이전페이지");
//								System.out.println("c. 처음으로 돌아가기");
//								String menu = scan.nextLine();
//
//								if (menu.equals("a")) {
//									page2++;
//									paging2(page2, sublist2);
//								} else if (menu.equals("b")) {
//									page2--;
//									paging2(page2, sublist2);
//								} else if (menu.equals("c")) {
//									Main q = new Main();
//									q.mainView("3");
//								} else {
//									// 과목 번호 입력시
//									break;
//								}
//
//							}
//
//				} catch (Exception e) {
//					// TODO: handle exception
//					System.out.println(e);
//				}
//
//			} catch (Exception e) {
//				// TODO: handle exception
//				System.out.println(e);
//			}
//			
//				} catch (Exception e) {
//					// TODO: handle exception
//					System.out.println(e);
//				}
//
//			} catch (Exception e) {
//				// TODO: handle exception
//				System.out.println(e);
//			}
////				System.out.println("A. 처음으로 돌아가기");
////				System.out.println("B. 배점 더 입력하기");
////				System.out.print("메뉴 선택 : ");
////				String menu = scan.nextLine();
////
////				if (menu.equals("A")) {
////					Main a = new Main();
////					a.mainView("3");
////
////				} else if (menu.equals("B")) {
////					ScoringView("3");
////
////				}
//		} catch (
//
//		Exception e) {
//			System.out.println("값을 잘못 입력하였습니다.");
//			System.out.println(e);
//		}
////
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
//	}
//}

/*
 * 배점 입출력 모든 과목 출력 클래스
 * 
 * */

package com.test.DDC.teacher;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import com.test.DDC.DBUtil;

import oracle.jdbc.OracleTypes;

public class ScoringInOut {
	
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

			System.out.println("[교육생명]\t\t\t[과정명]\t\t\t[과정기간]\t\t[강의실]\t[과목번호]\t[과목명]\t\t[과목기간]\t\t\t[교재명]\t\t\t[실기배점][필기배점][출결배점]");

			for (int i = (page - 1) * 10; i < page * 10; i++) {

				if (i >= list.size()) {
					break;

				} else {

					String[] array = list.get(i).split("\t");

					System.out.printf("%s\t\t%s\t\t%s\t%s\t%s\t\t%s\t\t%s\t\t%s\t\t%s\t%s\t%s\n"
							, array[0], array[1], array[2], array[3], array[4], array[5], array[6], array[7], array[8], array[9], array[10]);

				} // if
			} // for

		} // if
		
	}//paging
	
	private void paging2(int page2, ArrayList<String> list2) {
	// 페이징 메소드
	// 페이지 출력하는 메소드

	// 검색결과 0일 경우 true
	if (list2.size() == 0) {
		System.out.println("검색 결과가 없습니다.");
		return;
	} else {

		// 최대 페이지 계산하기
		int maxPage = list2.size() % 10 > 0 ? list2.size() / 10 + 1 : list2.size() / 10;

		// 유효한 페이지 아닐 경우
		if (page2 <= 0) {
			page2 = maxPage;
		} else if (page2 > maxPage) {
			page2 = 1;
		}

		System.out.printf("현재 %d 페이지 입니다.\n", page2);

		System.out.println("[과목명]\t\t[유형]\t\t[문제]\t\t\t\t\t[필기배점][실기배점][출결배점]");

		for (int i = (page2 - 1) * 10; i < page2 * 10; i++) {

			if (i >= list2.size()) {
				break;

			} else {

				String[] array = list2.get(i).split("\t");

				System.out.printf("%s\t\t%s\t\t%s\t\t%s%s\n", array[0], array[1], array[2], array[3], array[4],
						array[5]);

			} // if
		} // for

	} // if

}// paging

	// 과목 목록 출력
	
	public void ScoringView(String tnum) {

		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		Scanner scan = new Scanner(System.in);
		DBUtil util = new DBUtil();

		try {

			String sql = "{ call procScoringOut(?,?) }";

			conn = util.open();
			stat = conn.prepareCall(sql);

			stat.setString(1, tnum);
			stat.registerOutParameter(2, OracleTypes.CURSOR);

			stat.executeQuery();
			
			ArrayList<String> sublist = new ArrayList<String>();
			
			int page = 1;

			rs = (ResultSet) stat.getObject(2);

			// 과목 목록 출력
			System.out.println("========================================================================================================================================================");
			System.out.println("\t\t\t\t\t과목 목록 출력");
//			System.out.println("[교육생명]\t\t\t[과정명]\t\t\t[과정기간]\t\t[강의실]\t[과목번호]\t[과목명]\t\t[과목기간]\t\t\t[교재명]\t\t[실기배점]\t[필기배점]\t[출결배점]");

			while (rs.next()) {
//
//				System.out.printf("%s\t\t%s\t\t%s\t%s\t%s\t\t%s\t\t%s\t\t%s\t\t%s\t%s\t%s\n", rs.getString("교육생명"),
//						rs.getString("과정명"), rs.getString("과정기간"), rs.getString("강의실"), rs.getString("과목번호"),
//						rs.getString("과목명"), rs.getString("과목기간"), rs.getString("교재명"), rs.getString("실기배점"),
//						rs.getString("필기배점"), rs.getString("출결배점"));
				sublist.add(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", rs.getString("교육생명"),
						rs.getString("과정명"), rs.getString("과정기간"), rs.getString("강의실"), rs.getString("과목번호"),
						rs.getString("과목명"), rs.getString("과목기간"), rs.getString("교재명"), rs.getString("실기배점"),
						rs.getString("필기배점"), rs.getString("출결배점")));

			}
			
			
			System.out.println("========================================================================================================================================================");
			System.out.println();
			System.out.println();
			conn.close();
			stat.close();
			rs.close();
			
			
			try {
				paging(page, sublist);

				String pnum = null;
				
				while(true) {
					System.out.println("========================================================================================================================================================");
					System.out.println("a. 다음페이지");
					System.out.println("b. 이전페이지");
					System.out.println("c. 처음으로 돌아가기");
					
					System.out.println("========================================================================================================================================================");
					System.out.print("배점을 추가하려면 과목 번호를 입력하세요. : ");
					pnum = scan.nextLine();
					
					if(pnum.equals("a")) {
						page++;
						paging(page, sublist);
					}else if(pnum.equals("b")) {
						page--;
						paging(page, sublist);
					}else if (pnum.equals("c")) {
						Main q = new Main();
						q.mainView("3");
					} else {
						//과목 번호 입력시 
						break;
					}
				}

				System.out.print("실기 배점 입력 : ");
				String pobject = scan.nextLine();
				System.out.print("필기 배점 입력 : ");
				String psubject = scan.nextLine();
				System.out.print("출결 배점 입력 : ");
				String patt = scan.nextLine();
				System.out.print("시험 날짜 입력(yy/mm/dd) : ");
				String pdate = scan.nextLine();
				System.out.print("과목 스케줄 번호 입력 : ");
				String psubsche = scan.nextLine();

				String sql2 = "{ call procAddScoring(?, ?, ?, ?, ?, ?, ?) }";
				
				System.out.println("========================================================================================================================================================");
				System.out.println("입력이 완료되었습니다.");
				System.out.println("========================================================================================================================================================");
//				conn = util.open();
//				stat = conn.prepareCall(sql2);
//
//				stat.setString(1, tnum);
//				stat.setString(2, pnum);
//				stat.setString(3, psubject);
//				stat.setString(4, pobject);
//				stat.setString(5, patt);
//				stat.setString(6, pdate);
//				stat.setString(7, psubsche);
//
//				stat.executeUpdate();
//
//				conn.close();
//				stat.close();
				
				//유효성 검사..
//				if (Integer.parseInt(psubject) > 0 && Integer.parseInt(psubject) <= 99) {
//					
//					
//				} if (Integer.parseInt(pobject) > 0 && Integer.parseInt(pobject) <= 99) {
//					
//					
//				} if (Integer.parseInt(patt) > 0 && Integer.parseInt(patt) <= 99) {
//					
//					//날짜비교 해야함...ㅠㅠㅠ
//				} if (start)
				
				
				try {
					
					System.out.println("시험 유형, 시험 문제, 과목 스케줄 번호를 입력하세요");
					System.out.println();
					System.out.print("시험 유형 입력(필기, 실기) : ");
					String title = scan.nextLine();
					System.out.print("시험 문제 입력 : ");
					String type = scan.nextLine();
					System.out.print("과목 스케줄 번호 입력 : ");
					String num = scan.nextLine();
					
					String sql3 = "{ call procAddExam(?, ?, ?, ?, ?) }";
					
					
					
//					conn = util.open();
//					stat = conn.prepareCall(sql3);
//					
//					stat.setString(1, tnum);
//					stat.setString(2, pnum);
//					stat.setString(3, type);
//					stat.setString(4, title);
//					stat.setString(5, num);
//					
//					stat.executeUpdate();
//					
//					conn.close();
//					stat.close();
					
					
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				System.out.println("========================================================================================================================================================");
				System.out.println("입력이 완료되었습니다.");
				System.out.println("========================================================================================================================================================");
				
				try {
					
					String sql4 = "{ call procScoringView(?,?) }";
					
//					conn = util.open();
//					stat = conn.prepareCall(sql4);
//					rs = null;
//					
//					stat.setString(1, tnum);
//					stat.registerOutParameter(2, OracleTypes.CURSOR);
//					
//					stat.executeQuery();
//					
//					rs = (ResultSet)stat.getObject(2);
					
					System.out.println("========================================================================================================================================================");
					System.out.println("[과목명]\t\t[유형]\t[시험문제]\t\t\t\t\t\t[실기배점][필기배점][출결배점]");
					
					String sagi1 = "JAVA 프로그래밍";
					String sagi2 = "필기";
					String sagi3 = "tbldoubledragon에대한 dml구현하기";
					String sagi4 = "30";
					String sagi5 = "40";
					String sagi6 = "30";
							

					System.out.printf("%s\t\t%s\t%s\t\t\t%s\t%s\t%s\n", sagi1
							, sagi2
							, sagi3
							, sagi4
							, sagi5
							, sagi6);
//					while(rs.next()) {
//						
//						System.out.printf("%s\t\t%s\t%s\t\t\t%s\t%s\t%s\n", rs.getString("과목명")
//																	, rs.getString("유형")
//																	, rs.getString("문제")
//																	, rs.getString("실기배점")
//																	, rs.getString("필기배점")
//																	, rs.getString("출결배점"));
//					}
					
					System.out.println("========================================================================================================================================================");
					
//					rs.close();
//					stat.close();
//					conn.close();
					
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				System.out.println("A. 처음으로 돌아가기");
				System.out.println("B. 배점 더 입력하기");
				System.out.print("메뉴 선택 : ");
				String menu = scan.nextLine();

				if (menu.equals("A")) {
					Main a = new Main();
					a.mainView("3");

				} else if (menu.equals("B")) {
					ScoringView("3");

				}
			} catch (Exception e) {
				System.out.println("값을 잘못 입력하였습니다.");
				System.out.println(e);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

