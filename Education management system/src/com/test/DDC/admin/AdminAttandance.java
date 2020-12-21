package com.test.DDC.admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import com.test.DDC.DBUtil;

public class AdminAttandance {
	
	
		
		private static Connection conn = null;
		private static Statement stat = null;
		private static ResultSet rs = null;
		private static DBUtil util = new DBUtil();
		private static Scanner scan = new Scanner(System.in);
		
		public void printAttandance() {
			
			boolean loop = true;
			
			
			while (loop) { // 
				System.out.println("============================================================");
				System.out.println("		[과정별 학생출석 관리]");
				System.out.println("============================================================");
				
				Connection conn = null;
				Statement stat = null;
				ResultSet rs = null;
				
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
					System.out.println("[과정번호]\t\t\t\t[과정명]\t\t\t\t  [과정기간]\t[교사명]\t[강의실]");
					while (rs.next()) { //과정번호, 과정명, 과정기간, 교사명, 강의실
						
//						String result = rs.getString("과정번호") + "\t" + rs.getString("과정명") + "\t" + rs.getString("과정기간")
//						+ "\t" + rs.getString("교사명") + "\t"+ rs.getString("강의실");
						String result = String.format("%-5s\t\t%-43s\t%-15s\t%-6s\t%-6s",rs.getString("과정번호"),rs.getString("과정명"),rs.getString("과정기간")
								,rs.getString("교사명"),rs.getString("강의실"));
						
						
						System.out.println(result);
//						System.out.println("---------------------------------------------------------");
							
						
					}
					System.out.println("================================================================================================================================");
					
					System.out.print("출석조회과정 : ");
					String num = scan.nextLine();
					System.out.print("출석조회날짜(mm-dd) : ");
					String num2 =scan.nextLine();
					System.out.println();
					System.out.println();
					conn.close();
					stat.close();
					
					
						try {
							conn = util.open();
							stat = conn.createStatement();
							rs = null;
							
							String sql2 = String.format("select  rownum as 학번, s.name as 학생명, to_char(ad.workon, 'hh24:mm') as 출근시간, to_char(ad.workoff, 'hh24:mm') as 퇴근시간, ad.state as 근태" + 
									"    from tblstudent s" + 
									"        inner join tblregicourse rc" + 
									"            on s.student_seq = rc.student_seq" + 
									"                inner join tblopencourse oc" + 
									"                    on oc.opencourse_seq = rc.opencourse_seq" + 
									"                        inner join tblattendance ad" + 
									"                            on ad.regicourse_seq = rc.regicourse_seq" + 
									"                                    where to_char(ad.workon, 'MM-DD') = '%s'", num2);
													
							rs = stat.executeQuery(sql2);
							System.out.println("============================================================");
							System.out.println("[학번]\t[학생명]\t[출근]\t[퇴근]\t[근태]");
							while (rs.next()) { //학번, 학생명, 출근시간, 퇴근시간, 근태
								
							
								String result = rs.getString("학번") + "\t" + rs.getString("학생명") + "\t\t" + rs.getString("출근시간")
								+ "\t" + rs.getString("퇴근시간") + "\t"+ rs.getString("근태");
								
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
						
			
					
					
					conn.close();
					stat.close();
					
					loop = false;
				} catch (Exception e) {
					e.printStackTrace();
				}
					
					
			}//while
			
			
			
		}

}

