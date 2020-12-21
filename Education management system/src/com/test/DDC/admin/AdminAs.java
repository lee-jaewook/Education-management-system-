package com.test.DDC.admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import com.test.DDC.DBUtil;

public class AdminAs {
	private static Connection conn = null;
	private static Statement stat = null;
	private static ResultSet rs = null;
	private static DBUtil util = new DBUtil();
	private static Scanner scan = new Scanner(System.in);
	
	public void printAs() {
		boolean loop = true;
		
		while (loop) { // 
			System.out.println("============================================================");
			System.out.println("		[지원활동 관리]");
			System.out.println("============================================================");
			System.out.println("1 : 		[지원활동내역 목록]");
			System.out.println("2 : 		[지원활동내역 추가]");
			System.out.println("0 : 		뒤로가기");
			Connection conn = null;
			Statement stat = null;
			ResultSet rs = null;
			System.out.print("입력 : ");
			String sel = scan.nextLine();
			if (sel.equals("1")) {
				
				try {
					conn = util.open();
					stat = conn.createStatement();
					
					String sql = "SELECT ass.as_seq as 지원번호, stu.name as 학생명, t.name as 교사명, cl.name as 과정명, stu.tel as 전화번호,  Ass.asdate as 지원날짜, Ass.asService as 지원내역" + 
							"    FROM tblAs ass" + 
							"        INNER JOIN tblRegiCourse rc" + 
							"            ON ass.regiCourse_seq = rc.regiCourse_seq" + 
							"                INNER JOIN tblOpenCourse oc" + 
							"                    ON rc.openCourse_seq = oc.openCourse_seq" + 
							"                        INNER JOIN tblCourselist cl" + 
							"                            ON oc.courselist_seq = cl.courselist_seq" + 
							"                                INNER JOIN tblTeacherCourse tc" + 
							"                                    ON oc.openCourse_seq = tc.openCourse_seq" + 
							"                                        INNER JOIN tblTeacher t" + 
							"                                            ON tc.teacher_seq = t.teacher_seq" + 
							"                                                INNER JOIN tblStudent stu" + 
							"                                                    ON rc.student_seq = stu.student_seq" + 
							"                                                        where ass.as_seq <= 10";
					
					rs = stat.executeQuery(sql);
					System.out.println("지원번호\t학생명\t\t교사명\t\t\t\t과정명\t\t\t\t\t전화번호\t\t지원날짜\t지원내역");
					while (rs.next()) { //지원번호, 학생명, 교사명, 과정명, 전화번호, 지원날짜, 지원내역
						
						String result = String.format("%-5s\t\t%-5s\t%-5s\t%-20s\t%-15s\t%-10s\t%-10s",rs.getString("지원번호"),rs.getString("학생명"),rs.getString("교사명")
						,rs.getString("과정명"),rs.getString("전화번호"),rs.getString("지원날짜"),rs.getString("지원내역"));
//						String result = rs.getString("과정번호") + "\t" + rs.getString("과정명") + "\t" + rs.getString("과정기간")
//						+ "\t" + rs.getString("교사명") + "\t"+ rs.getString("강의실");
						
						
						System.out.println(result);
						
						
						
//						System.out.println("---------------------------------------------------------");
							
						
					}
					System.out.println("---------------------------------------------------------");
					System.out.println("1.        더보기");
					System.out.println("0.        메인화면");
					System.out.print("입력 : ");
					String num2 = scan.nextLine();
					
					if (num2.equals("1")) {
						try {
							conn = util.open();
							stat = conn.createStatement();
							
							String sql2 = "SELECT ass.as_seq as 지원번호, stu.name as 학생명, t.name as 교사명, cl.name as 과정명, stu.tel as 전화번호,  Ass.asdate as 지원날짜, Ass.asService as 지원내역" + 
									"    FROM tblAs ass" + 
									"        INNER JOIN tblRegiCourse rc" + 
									"            ON ass.regiCourse_seq = rc.regiCourse_seq" + 
									"                INNER JOIN tblOpenCourse oc" + 
									"                    ON rc.openCourse_seq = oc.openCourse_seq" + 
									"                        INNER JOIN tblCourselist cl" + 
									"                            ON oc.courselist_seq = cl.courselist_seq" + 
									"                                INNER JOIN tblTeacherCourse tc" + 
									"                                    ON oc.openCourse_seq = tc.openCourse_seq" + 
									"                                        INNER JOIN tblTeacher t" + 
									"                                            ON tc.teacher_seq = t.teacher_seq" + 
									"                                                INNER JOIN tblStudent stu" + 
									"                                                    ON rc.student_seq = stu.student_seq" +
									"                                                        where ass.as_seq > 10 and ass.as_seq <= 30 ";
							
							rs = stat.executeQuery(sql2);
							System.out.println("지원번호\t학생명\t\t교사명\t\t\t\t과정명\t\t\t\t\t전화번호\t\t지원날짜\t지원내역");
							while (rs.next()) { //지원번호, 학생명, 교사명, 과정명, 전화번호, 지원날짜, 지원내역
								
								String result = String.format("%-5s\t\t%-5s\t%-5s\t%-20s\t%-15s\t%-10s\t%-10s",rs.getString("지원번호"),rs.getString("학생명"),rs.getString("교사명")
								,rs.getString("과정명"),rs.getString("전화번호"),rs.getString("지원날짜"),rs.getString("지원내역"));
//								String result = rs.getString("과정번호") + "\t" + rs.getString("과정명") + "\t" + rs.getString("과정기간")
//								+ "\t" + rs.getString("교사명") + "\t"+ rs.getString("강의실");
								
								
								System.out.println(result);
								
								
								
//								System.out.println("---------------------------------------------------------");
									
								
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
						
					} else if (num2.equals("0")) {
						loop = false;
					}
					
					conn.close();
					stat.close();
					loop = false;
					
					System.out.println("1.        더보기");
					System.out.println("0.        메인화면");
					System.out.print("입력 : ");						
					String num3 = scan.nextLine();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (sel.equals("2")) {
				try {
					conn = util.open();
					stat = conn.createStatement();
					
					System.out.print("지원날짜 : ");
					String date = scan.nextLine();
					System.out.print("지원내역 : ");
					String as = scan.nextLine();
					System.out.print("지원내용 : ");
					String ascontent = scan.nextLine();
					System.out.print("학생번호 : ");
					String studentnum = scan.nextLine();
					
					String sql3 = String.format("insert into tblAs values (as_seq.nextVal, to_date('%s','yyyy-mm-dd'), '%s', '%s', %s)", date,as,ascontent,studentnum);
					
					stat.executeUpdate(sql3);
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			} else if (sel.equals("0")) {
				loop = false;
			}
	
		}

}
}
