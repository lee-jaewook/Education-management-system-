package com.test.DDC.teacher;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

import com.test.DDC.DBUtil;

import oracle.jdbc.OracleTypes;

public class LectureOutput {

	public void lectureView(String tnum) {

		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		Scanner scan = new Scanner(System.in);
		DBUtil util = new DBUtil();

		try {

			String sql = "{ call procIng(?,?) }";

			conn = util.open();
			stat = conn.prepareCall(sql);

			stat.setString(1, tnum);
			stat.registerOutParameter(2, OracleTypes.CURSOR);

			stat.executeQuery();

			rs = (ResultSet)stat.getObject(2);

			// 강의 상태 출력

			System.out.println("===============================================================================================");
			System.out.println("\t\t\t\t\t현재 강재지 교사님의 강의 상태입니다.");
			System.out.println("===============================================================================================");
			System.out.println("현재 강재지 교사님의 강의 상태입니다.");
			System.out.println();
			System.out.println("[교사명]\t\t\t\t[과정명]\t\t\t\t\t[과정기간]\t\t[진행상태]");

			while (rs.next()) {

//				System.out.printf("%s\t\t%s\t\t%s\t\t%s\n", rs.getString("교사명"), rs.getString("과정명"),
//						rs.getString("과정기간"), rs.getString("진행상태"));
				
				String result = String.format("%-5s\t\t%-43s\t%-15s\t%-6s", rs.getString("교사명"), rs.getString("과정명"),
						rs.getString("과정기간"), rs.getString("진행상태"));

				System.out.println(result);
			}
			
			
			rs.close();
			conn.close();
			stat.close();
			
			System.out.println("===============================================================================================");
			System.out.println("A. 계속 조회하기");
			System.out.println("B. 처음으로 돌아가기");
			System.out.print("입력 : ");
			String menu = scan.nextLine();
			
			if (menu.equals("A")) {
				
					try {

				String sql2 = "{ call proctSchedule1(?,?) }";

				conn = util.open();
				stat = conn.prepareCall(sql2);

				stat.setString(1, tnum);
				stat.registerOutParameter(2, OracleTypes.CURSOR);

				stat.executeQuery();

				rs = (ResultSet) stat.getObject(2);

				// 강의 상태 출력

				System.out.println("===============================================================================================");
				System.out.println("\t\t\t\t\t강의 스케줄 조회");
				System.out.println("===============================================================================================");
				System.out.println("[과정번호]\t\t\t[과정명]\t\t\t\t\t[과정기간]\t\t[강의실]");

				while (rs.next()) {

//					System.out.printf("%s\t\t%s\t\t%10s\t%s\n", rs.getString("과정번호"), rs.getString("과정명"),
//							rs.getString("과정기간"), rs.getString("강의실명"));
					
					String result = String.format("%-5s\t\t%-43s\t%-15s\t%-6s", rs.getString("과정번호"), rs.getString("과정명"),
							rs.getString("과정기간"), rs.getString("강의실명"));

					System.out.println(result);
					
				}
				System.out.println("===============================================================================================");

				rs.close();
				conn.close();
				stat.close();
				System.out.print("조회하실 과정 번호를 입력하세요 : ");
				String cnum = scan.nextLine();
				
				if (cnum.equals("13") || cnum.equals("3")) {
					try {
						String sql3 = "{ call proctSchedule2(?, ?, ?)}";
						conn = util.open();
						stat = conn.prepareCall(sql3);
						rs = null;
//						sql2 = "{ call proctSchedule2(?, ?, ?)}";

						stat.setString(1, tnum);
						stat.setString(2, cnum);
						stat.registerOutParameter(3, OracleTypes.CURSOR);

						stat.executeQuery();

						rs = (ResultSet) stat.getObject(3);

						System.out.println("===============================================================================================");
						System.out.println("\t\t\t\t\t강의 스케줄 조회");
						System.out.println("===============================================================================================");
						System.out.println("[과목명]\t\t\t[과목기간]\t   [교육생인원]\t\t[교재명]");
						while (rs.next()) {

//							System.out.printf("%s\t\t%20s\t\t%-30s\t%-5s\n", rs.getString("과목명"), rs.getString("과목기간"),
//									rs.getString("교재명"), rs.getString("교육생인원"));
							System.out.printf("%s\t\t%20s\t\t%-5s\t%-30s\n", rs.getString("과목명"), rs.getString("과목기간"),
									rs.getString("교육생인원"),rs.getString("교재명"));
						}
						
					
						System.out.println("===============================================================================================");
						rs.close();
						conn.close();
						stat.close();
						
						System.out.println("Z. 처음으로 돌아가기");
						System.out.println();
						System.out.println("메뉴 입력 : ");
						String menu1 = scan.nextLine();
						
						if (menu1.equals("Z")) {
							Main a = new Main();
							a.mainView(tnum);
						} 
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("과정 번호를 잘못 입력하였습니다.");
				}

			} catch (Exception e) {

				e.printStackTrace();
			
			}
			

			// 뒤로가기 만들어야함........
		} else if (menu.equals("B")){
			return;
		} else {
			System.out.println("값을 잘못 입력하였습니다.");
		}

		} catch (Exception e) {

			e.printStackTrace();
		}

//	

	}
}
