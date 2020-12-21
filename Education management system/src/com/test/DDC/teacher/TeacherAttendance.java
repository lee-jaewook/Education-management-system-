package com.test.DDC.teacher;


	import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import com.test.DDC.DBUtil;
public class TeacherAttendance {
	

		
			
			private static Connection conn = null;
			private static Statement stat = null;
			private static ResultSet rs = null;
			private static DBUtil util = new DBUtil();
			private static Scanner scan = new Scanner(System.in);
			Score_Output so = new Score_Output();
			ArrayList<String> stuAtt = new ArrayList<String>();
			ArrayList<String> stuAtt2 = new ArrayList<String>();
			
			public void printAttandance(String tnum) {
				
				boolean loop = true;
				
				
				while (loop) { // 
					System.out.println("============================================================");
					System.out.println("			[교육생 출결 관리]");
					System.out.println("============================================================");
					
					Connection conn = null;
					Statement stat = null;
					ResultSet rs = null;
					
					try {
						
//						System.out.print("교사번호입력 : ");
//						int a=scan.nextInt();
						int w = Integer.parseInt("3");
						
						so.getSubList(w);
						so.paging2(0, so.subjectList);
						
						conn = util.open();
						stat = conn.createStatement();
						
						String sql = String.format("SELECT oc.openCourse_seq as 과정번호, cl.name as 과정명, oc.startDate || '~' || oc.endDate as 과정기간, t.name as 교사명, r.Name as 강의실" + 
								"    FROM tblCourselist cl" + 
								"        INNER JOIN tblOpenCourse oc" + 
								"            ON cl.courselist_seq = oc.courselist_seq" + 
								"                INNER JOIN tblRoom r" + 
								"                    ON oc.room_seq = r.room_seq" + 
								"                        INNER JOIN tblTeacherCourse tc" + 
								"                            ON tc.openCourse_seq = oc.openCourse_seq" + 
								"                                INNER JOIN tblTeacher t" + 
								"                                    ON tc.teacher_seq = t.teacher_seq" +
								"                                       where t.teacher_seq = %s  order by oc.opencourse_seq", w);
						
						rs = stat.executeQuery(sql);
						

								
							
						
						
						System.out.println("---------------------------------------------");
						System.out.print("출석 조회 과목 : ");
						
						int num = scan.nextInt();
						scan.nextLine();
						System.out.println("---------------------------------------------");

						System.out.println();
						System.out.println();
						conn.close();
						stat.close();
						
//						to_char(ad.workon, 'MM-DD') =
							try {
								conn = util.open();
								stat = conn.createStatement();
								rs = null;
								
								String sql1 = String.format("select  rownum as 학번, s.name as 학생명, to_char(ad.workon, 'hh24:mm') as "
										+ "출근시간, to_char(ad.workoff, 'hh24:mm') as 퇴근시간, ad.state as 근태 from tblstudent s inner join tblregicourse rc"+
									    
								        
								          "  on s.student_seq = rc.student_seq" +
								           "     inner join tblopencourse oc"+
								            "        on oc.opencourse_seq = rc.opencourse_seq"+
								             "           inner join tblattendance ad"+
								              "              on ad.regicourse_seq = rc.regicourse_seq"+
							    				"				inner join tblSubjectSchedule ss"+
							      				"					on oc.opencourse_seq = ss.opencourse_seq"+
								                 "                   where ss.subject_seq = %s ", num);
														
								rs = stat.executeQuery(sql1);
								
								while (rs.next()) { //학번, 학생명, 출근시간, 퇴근시간, 근태
									
								
									String result = rs.getString("학번") + "\t" + rs.getString("학생명") + "\t" + rs.getString("출근시간")
									+ "\t" + rs.getString("퇴근시간") + "\t"+ rs.getString("근태");
									
									
									stuAtt.add(result);
									
							
//									System.out.println(result);
////									System.out.println("---------------------------------------------------------");
								}
								paging(0, stuAtt);
								
								System.out.println();
								System.out.println("---------------------------------------------");
								System.out.print("날짜로 검색하시려면 x를 누르세요 : ");
								String b = scan.nextLine();
								
								
								if(b.equals("x") ) {
									System.out.print("출석조회날짜 : ");
									String num2 =scan.nextLine();
									System.out.println("---------------------------------------------");
									searchDate(num2);
								}else {
									//메인 화면
								}
								
								
								rs.close();							
								conn.close();
								stat.close();
								
								
								loop = false;
								 
								System.out.println();
								System.out.println("---------------------------------------------");
								System.out.print("계속하시려면 엔터를 눌러주세요");						
								String num3 = scan.nextLine();
								
								//로그인 화면으로 이동
								
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
			
			
			private void searchDate(String num2) {//날짜검색
				
				
				
				try {
					conn = util.open();
					stat = conn.createStatement();
					rs = null;
					
					String sql2 = String.format("select  rownum as 학번, s.name as 학생명, to_char(ad.workon, 'hh24:mm') as "
							+ "출근시간, to_char(ad.workoff, 'hh24:mm') as 퇴근시간, ad.state as 근태 from tblstudent s inner join tblregicourse rc"+
						    
					        
					          "  on s.student_seq = rc.student_seq" +
					           "     inner join tblopencourse oc"+
					            "        on oc.opencourse_seq = rc.opencourse_seq"+
					             "           inner join tblattendance ad"+
					              "              on ad.regicourse_seq = rc.regicourse_seq"+
					                 "                   where to_char(ad.workon, 'MMDD') = '%s'", num2);
					
						rs = stat.executeQuery(sql2);
						
						while (rs.next()) { //학번, 학생명, 출근시간, 퇴근시간, 근태
						
						
						String result = rs.getString("학번") + "\t" + rs.getString("학생명") + "\t" + rs.getString("출근시간")
						+ "\t" + rs.getString("퇴근시간") + "\t"+ rs.getString("근태");
						
						
						stuAtt2.add(result);
					}
						paging(0, stuAtt2);
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}


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

					System.out.println("[목록]\t[학생명]\t[출근]\t[퇴근]\t[근태]");

					for (int i = (page - 1) * 10; i < page * 10; i++) {

						if (i >= list.size()) {
							break;

						} else {

							String[] array = list.get(i).split("\t");

							System.out.printf("%s\t%s\t%s\t%s\t%s\n"
									, array[0], array[1], array[2], array[3], array[4]);

						} // if
					} // for

				} // if
				
			}//paging

	}



