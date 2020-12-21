package com.test.DDC.admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import com.test.DDC.DBUtil;

public class AdminItem {
	
	
		
		private static Connection conn = null;
		private static Statement stat = null;
		private static ResultSet rs = null;
		private static DBUtil util = new DBUtil();
		private static Scanner scan = new Scanner(System.in);
		
		public void printItem() {
			
			boolean loop = true;
			
			
			while (loop) { // 
				System.out.println("============================================================");
				System.out.println("		[기자재 관리]");
				System.out.println("============================================================");
				System.out.println("1 : 		[과정별 기자재 관리]");
				System.out.println("2 : 		[강의실별 기자재 관리]");
				
				Connection conn = null;
				Statement stat = null;
				ResultSet rs = null;
				
				System.out.println("---------------------------------------------------------");
				System.out.print("입력 : ");
				String sel = scan.nextLine();
				
				if (sel.equals("1")) {
				try {
					conn = util.open();
					stat = conn.createStatement();
					
					String sql = "SELECT oc.openCourse_seq as 과정번호, cl.name as 과정명, oc.startDate || '~' || oc.endDate as 과정기간, t.name as 교사명, r.Name as 강의실" + 
							"    FROM tblCourselist cl" + 
							"        INNER JOIN tblOpenCourse oc" + 
							"            ON cl.courselist_seq = oc.courselist_seq" + 
							"                INNER JOIN tblRoom r" + 
							"                    ON oc.room_seq = r.room_seq" + 
							"                        INNER JOIN tblTeacherCourse tc" + 
							"                            ON tc.openCourse_seq = oc.openCourse_seq" + 
							"                                INNER JOIN tblTeacher t" + 
							"                                    ON tc.teacher_seq = t.teacher_seq" +
							"                                        order by oc.opencourse_seq";
					
					rs = stat.executeQuery(sql);
					System.out.println("================================================================================================================================");
					System.out.println("[과정번호]\t\t\t\t[과정명]\t\t\t\t[과정기간]\t\t[교사명]\t[강의실]");
					while (rs.next()) { //과정번호, 과정명, 과정기간, 교사명, 강의실
						
						String result = String.format("%-5s\t\t%-43s\t%-15s\t%-6s\t%-6s",rs.getString("과정번호"),rs.getString("과정명"),rs.getString("과정기간")
						,rs.getString("교사명"),rs.getString("강의실"));
//						String result = rs.getString("과정번호") + "\t" + rs.getString("과정명") + "\t" + rs.getString("과정기간")
//						+ "\t" + rs.getString("교사명") + "\t"+ rs.getString("강의실");
						
						System.out.println(result);
						
					}
					System.out.println("================================================================================================================================");
					
					System.out.print("기자재조회과정 : ");
					String num = scan.nextLine();
					
					System.out.println();
					System.out.println();
					conn.close();
					stat.close();
					
					
						try {
							conn = util.open();
							stat = conn.createStatement();
							rs = null;
							
							String sql2 = String.format("SELECT ti.item_seq as 기자재번호, ti.name as 기자재명, ti.itemqty as 기자재수량, cl.name as 과정명" + 
									"    FROM tblItem ti" + 
									"        INNER JOIN tblroom tr" + 
									"            ON tr.room_seq = ti.room_seq" + 
									"                INNER JOIN tblCourselist cl" + 
									"                    ON cl.Courselist_seq = ti.Courselist_seq" + 
									"                        WHERE cl.courselist_seq = %s ", num);
													
							rs = stat.executeQuery(sql2);
							System.out.println("================================================================================================================================");
							System.out.println("[기자재번호]\t[기자재명]\t[기자재수량]\t\t\t[과정명]");
							while (rs.next()) { //기자재번호, 기자재명, 기자재수량, 과정명
								
								String result = String.format("%-5s\t\t%-6s\t%-6s\t\t%-20s", rs.getString("기자재번호"),  rs.getString("기자재명"), rs.getString("기자재수량")
								,rs.getString("과정명"));
//								String result = rs.getString("기자재번호") + "\t\t" + rs.getString("기자재명") + "\t\t" + rs.getString("기자재수량")
//								+ "\t\t" + rs.getString("과정명");
								
								
						
								System.out.println(result);
//								System.out.println("---------------------------------------------------------");
									
								
							}
							System.out.println("========================================================================================================================");
							rs.close();							
							conn.close();
							stat.close();
							
							
							loop = false;
							
							System.out.println();
							System.out.print("계속하시려면 엔터를 눌러주세요");						
							String num3 = scan.nextLine();
						} catch (Exception e) {
							e.printStackTrace();
							// TODO: handle exception
						}
						
			
					
					
					conn.close();
					stat.close();
					
					loop = false;
				} catch (Exception e) {
					e.printStackTrace();
				}
				} else if (sel.equals("2")) {
					
					System.out.println("---------------------------------------------------------");
					System.out.println("1 : 		[1 강의실]");
					System.out.println("2 : 		[2 강의실]");
					System.out.println("3 : 		[3 강의실]");
					System.out.println("4 : 		[4 강의실]");
					System.out.println("5 : 		[5 강의실]");
					System.out.println("6 : 		[6 강의실]");
					System.out.println("---------------------------------------------------------");
					System.out.print("기자재조회강의실 : ");
					String num2 = scan.nextLine();
					
					try {
						conn = util.open();
						stat = conn.createStatement();
						rs = null;
						
						String sql = String.format("SELECT ti.item_seq as 기자재번호, ti.name as 기자재명, ti.itemqty as 기자재수량, ti.room_seq as 강의실" + 
								"    FROM tblItem ti" + 
								"        INNER JOIN tblroom tr" + 
								"            ON tr.room_seq = ti.room_seq" + 
								"                INNER JOIN tblCourselist cl" + 
								"                    ON cl.Courselist_seq = ti.Courselist_seq" + 
								"                        WHERE ti.room_seq = %s ", num2);
												
						rs = stat.executeQuery(sql);
						System.out.println("============================================================");
						System.out.println("[기자재번호]\t[기자재명]\t[기자재수량]\t[강의실]");
						while (rs.next()) { //기자재번호, 기자재명, 기자재수량, 강의실
							
//							String result = String.format("%5s\t\t%6s\t%6s\t\t%5s", rs.getString("기자재번호"),  rs.getString("기자재명"), rs.getString("기자재수량")
//							,rs.getString("강의실"));
							String result = String.format("%-5s\t\t%-6s\t%-6s\t\t%-5s", rs.getString("기자재번호"),  rs.getString("기자재명"), rs.getString("기자재수량")
							,rs.getString("강의실"));
					
							System.out.println(result);
								
						}
						System.out.println("============================================================");
						rs.close();							
						conn.close();
						stat.close();
						
						
						loop = false;
						
						System.out.println();
						System.out.print("계속하시려면 엔터를 눌러주세요");						
						String num3 = scan.nextLine();
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
					
					
				}
					
					
			}//while
			
			
			
		}

}

