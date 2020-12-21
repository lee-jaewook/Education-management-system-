package com.test.DDC.admin;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import com.test.DDC.DBUtil;

import oracle.jdbc.OracleTypes;

public class Manage {
	//manege
	/**
		 * 과정을 조회합니다.
		 * @return
		 */
		public ArrayList<String[]> procgetcourselist(){
			
			Connection conn = null;
			CallableStatement stat = null;
			ResultSet rs = null;
			DBUtil util = new DBUtil();
			ArrayList<String[]> row=new ArrayList<String[]>();
			try {
				String sql = String.format("{call procgetcourselist(?)}");

				conn = util.open();
				stat = conn.prepareCall(sql);
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.executeQuery();
				
				rs=(ResultSet)stat.getObject(1);
				
				while(rs.next()) {
					String[] temp= {rs.getString("과정번호"),rs.getString("과정명")};
					row.add(temp);
				}
				
				stat.close();
				conn.close();
				return row;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
		}//procgetcourselist
		/**
		 * 폐지된 과정을 포함해서 출력합니다
		 * @return
		 */
		public ArrayList<String[]> procgetcourselist2(){
			
			Connection conn = null;
			CallableStatement stat = null;
			ResultSet rs = null;
			DBUtil util = new DBUtil();
			ArrayList<String[]> row=new ArrayList<String[]>();
			try {
				String sql = String.format("{call procgetcourselist2(?)}");

				conn = util.open();
				stat = conn.prepareCall(sql);
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.executeQuery();
				
				rs=(ResultSet)stat.getObject(1);
				
				while(rs.next()) {
					System.out.println(rs.getString("개설여부"));
					String[] temp= {rs.getString("과정번호"),rs.getString("과정명"),rs.getString("개설여부")};
					row.add(temp);
				}
				
				stat.close();
				conn.close();
				return row;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
		}//procgetcourselist

	
	
	/**
	 * 과정 이름을 정하고 과목을 구성합니다.
	 * @param name 과정이름
	 * @param sub1 1과목
	 * @param sub2 2과목
	 * @param sub3 3과목
	 * @param sub4 4과목
	 * @param sub5 5과목
	 * @param sub6 6과목
	 */
	public boolean procNewcourseListSubject(String name,int sub1,int sub2,int sub3,int sub4,int sub5,int sub6) {
		// TODO Auto-generated method stub
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {
			String sql = String.format("{call procNewcourseListSubject(?,?,?,?,?,?,?)}");
			conn = util.open();
			stat = conn.prepareCall(sql);
			
			stat.setString(1, name);
			stat.setInt(2, sub1);
			stat.setInt(3, sub2);
			stat.setInt(4, sub3);
			stat.setInt(5, sub4);
			stat.setInt(6, sub5);
			stat.setInt(7, sub6);
			stat.executeQuery();
			System.out.println("과정에 과목설정 완료");

			stat.close();
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 새로운 과정을 개설합니다.
	 * @param courselist_seq 과정목록 번호
	 * @param open	강의개설일
	 * @param room	강의실배정
	 */
	public boolean procOpenCourse(int courselist_seq,String open,int room) {
		// TODO Auto-generated method stub
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {
			String sql = String.format("{call procOpenCourse(?,?,?)}");
			conn = util.open();
			stat = conn.prepareCall(sql);
			stat.setInt(1,courselist_seq);
			stat.setString(2, open);
			stat.setInt(3,room);
			stat.executeQuery();
			
			System.out.println("개설과정 추가 완료");

			stat.close();
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}//procOpenCourse
	
	/**
	 * 개설된 과정정보를 가져옵니다.
	 *  (0)PK
	 *	(1)개설과정번호
	 *	(2)과정명
	 *	(3)강의실
	 *	(4)수강생
	 *	(5)시작일
	 *	(6)종료일
	 *	(7)기간
	 */
	public ArrayList<String[]> procSearchOpencourse() {
		// TODO Auto-generated method stub
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();
		ArrayList<String[]> row=new ArrayList<String[]>(); 
		try {
			String sql = String.format("{call procSearchOpencourse(?)}");
			
			conn = util.open();
			stat = conn.prepareCall(sql);
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.executeQuery();
			rs=(ResultSet)stat.getObject(1);
			while(rs.next()) {
				String str=String.format("%s-%s-%s-%s-%s-%s-%s-%s"
						,rs.getString(1)//PK
						,rs.getString(2)//개설과정번호
						,rs.getString(3)//과정명
						,rs.getString(4)//강의실
						,rs.getString(5)//수강생
						,rs.getString(6).substring(0,10).replace("-", "")//시작일
						,rs.getString(7).substring(0,10).replace("-", "")//종료일
						,rs.getString(8));//기간
				String[] a=str.split("-");
				row.add(a);
				
			}
			
			stat.close();
			conn.close();
			return row;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}//procSearchOpencourse
	/**
	 * 과정번호를 입력하면 과정정보를 가져옵니다.
	 * @param 과정번호
	 * @return 과정정보
	 *  (0)PK
	 *	(1)개설과정번호
	 *	(2)과정명
	 *	(3)강의실
	 *	(4)수강생
	 *	(5)시작일
	 *	(6)종료일
	 *	(7)기간
	 */
	public String[] procSearchOpencourse(int num) {

		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();
		ArrayList<String[]> row=new ArrayList<String[]>(); 
		try {
			String sql = String.format("{call procSearchOpencourse(?)}");
			
			conn = util.open();
			stat = conn.prepareCall(sql);
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.executeQuery();
			rs=(ResultSet)stat.getObject(1);
			while(rs.next()) {
				String str=String.format("%s-%s-%s-%s-%s-%s-%s-%s"
						,rs.getString(1)//PK
						,rs.getString(2)//개설과정번호
						,rs.getString(3)//과정명
						,rs.getString(4)//강의실
						,rs.getString(5)//수강생
						,rs.getString(6).substring(0,10).replace("-", "")//시작일
						,rs.getString(7).substring(0,10).replace("-", "")//종료일
						,rs.getString(8));//기간
				String[] a=str.split("-");
				row.add(a);
				
			}
			
			rs.close();
			stat.close();
			conn.close();
			return row.get(num-1);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}//procSearchOpencourse
	/**
	 * 입력한 과정번호에 수료일을 지정합니다(주말지정불가)
	 * @param opencourseNum 개설과정번호
	 * @param finishdate	수료일입력
	 */
	public void procAssignFinaldate(int opencourseNum,String finishdate) {
		// TODO Auto-generated method stub
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();
		
		Calendar c=Calendar.getInstance();
		c.set(Integer.parseInt(finishdate.substring(0, 4)),Integer.parseInt(finishdate.substring(4, 6))-1,Integer.parseInt(finishdate.substring(6, 8)));
	
		if(c.get(Calendar.DAY_OF_WEEK)==1||c.get(Calendar.DAY_OF_WEEK)==7) {
			System.out.println("주말은 수료일로 지정할 수 없습니다.");
		}else {
		try {
			String sql = String.format("{call procAssignFinaldate(?,?)}");

			conn = util.open();
			//conn.setAutoCommit(false);
			stat = conn.prepareCall(sql);
			
			stat.setInt(1,opencourseNum);
			stat.setString(2, finishdate);
			stat.executeQuery();
			
			System.out.println("수료날짜 지정 완료");
			stat.close();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("수료날짜 지정 실패");
			e.printStackTrace();
			
		}
		}
	}//procAssignFinaldate
	/**
	 * 개설된 과정의 과목정보를 가져옵니다.
	 * @param opencourseNum 개설과정 번호입력
	 *  (0)과목번호
	 *	(1)과정번호
	 *	(2)과목명
	 *	(3)시작일
	 *	(4)종료일
	 *	(5)과정시작일
	 *	(6)과정종료일
	 *	(7)강사명
	 */
	public ArrayList<String> procGetOpenCourseInfo(int opencourseNum) {
		// TODO Auto-generated method stub
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();
		ArrayList<String> row=new ArrayList<String>();
		try {
			String sql = String.format("{call procGetOpenCourseInfo(?,?)}");

			conn = util.open();
			stat = conn.prepareCall(sql);
			stat.setInt(1, opencourseNum);
			stat.registerOutParameter(2, OracleTypes.CURSOR);
			stat.executeQuery();
			rs=(ResultSet)stat.getObject(2);
			
			while(rs.next()) {
				String str=String.format("%s-%s-%s-%s-%s-%s-%s-%s"
						,rs.getString(1)//과목번호
						,rs.getString(5)//과정번호
						,rs.getString(2)//과목명
						,rs.getString(3).substring(0, 10).replace("-", "")//시작일
						,rs.getString(4).substring(0, 10).replace("-", "")//종료일
						,rs.getString(6).substring(0, 10).replace("-", "")//과정시작일
						,rs.getString(7).substring(0, 10).replace("-", "")//과정종료일
						,rs.getString(8));//강사명
				
				row.add(str);
			}
			
			stat.close();
			conn.close();
			return row;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}//procGetOpenCourseInfo
	/**
	 * 개설된 과정에 등록한 학생정보를 가져옵니다.
	 * @param opencourseNum 개설과정번호 입력
	 *  (0)이름
	 *	(1)주민번호
	 *	(2)전화번호
	 *	(3)등록일
	 *	(4)과정번호
	 *	(5)과정명
	 *	(6)상태
	 */
	public ArrayList<String[]> procgetStudentInfo(int opencourseNum) {
		// TODO Auto-generated method stub
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();
		ArrayList<String[]> row=new ArrayList<String[]>();
		try {
			String sql="{call procgetStudentInfo(?,?)}";
			conn=util.open();
			stat=conn.prepareCall(sql);
			stat.setInt(1, opencourseNum);
			stat.registerOutParameter(2, OracleTypes.CURSOR);
			stat.executeQuery();
			
			rs=(ResultSet)stat.getObject(2);
			while(rs.next()) {
				String str=String.format("%s-%s-%s-%s-%s-%s-%s"
						,rs.getString(1)//이름
						,rs.getString(2).replace("-", "")//주민번호
						,rs.getString(3).replace("-", "")//전화번호
						,rs.getString(4).substring(0, 10).replace("-", "")//등록일
						,rs.getString(5)//개설과정번호
						,rs.getString(6)//과정명
						,rs.getString(7));//상태
				String[] temp=str.split("-");
				row.add(temp);
			}
			
			stat.close();
			conn.close();
			return row;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}//procgetStudentInfo
	
	public boolean procDeleteopenCourse(int popencourse_seq) {//개설된 과정 지우기
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {
			String sql = "{call procDeleteopenCourse(?) }";
			
			conn = util.open();
			stat = conn.prepareCall(sql);
			stat.setInt(1, popencourse_seq);

			stat.executeQuery();

			stat.close();
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}//procDeleteopenCourse
	
	public boolean procDeleteCourseList(int pcourseList_seq) {//과정 지우기
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();

		try {
			String sql = "{call procDeleteCourseList(?) }";
			conn = util.open();
			stat = conn.prepareCall(sql);
			stat.setInt(1, pcourseList_seq);
			stat.executeQuery();

			stat.close();
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}//procDeleteCourseList
	
	public ArrayList<String[]> procSubject() {
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();
		ArrayList<String[]> row=new ArrayList<String[]>();

		try {
			String sql = "{call procSubject(?) }";
			conn = util.open();
			stat = conn.prepareCall(sql);
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.executeQuery();
			rs=(ResultSet)stat.getObject(1);
			while(rs.next()) {
				String str=String.format("%s-%s-%s"
						,rs.getString("번호")
						,rs.getString("이름")
						,rs.getString("기간"));
				String[] temp= {rs.getString("번호")
						,rs.getString("이름")
						,rs.getString("기간")};
				
				
				row.add(temp);
				}
			stat.close();
			conn.close();
			return row;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}//procSubject
	/**
	 * 모든 취업자의 정보를 가져옵니다.
	 * [0]수강신청번호[1]학생번호[2]학생이름[3]회사번호[4]회사이름[5]연봉[6]취직일
	 * @return
	 */
	public ArrayList<String[]>  procemployee() {
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();
		ArrayList<String[]> row=new ArrayList<String[]>();
		try {
			String sql = "{call procemployee(?) }";
			conn = util.open();
			stat = conn.prepareCall(sql);
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.executeQuery();
			
			rs=(ResultSet)stat.getObject(1);
			
			while(rs.next()) {
				String[] temp= {rs.getString("수강신청번호"),
								rs.getString("학생번호"),
								rs.getString("학생이름"),
								rs.getString("회사번호"),
								rs.getString("회사이름"),
								rs.getString("연봉"),
								rs.getString("취직일")};
				row.add(temp);
			}

			
			
			stat.close();
			conn.close();
			return row;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}//procemployee
	/**
	 * 과정번호를 입력받아 과정별 취업자를 보여줍니다.
	 * @param opencourse_seq
	 * @return
	 * [0]수강신청번호[1]학생번호[2]학생이름[3]회사번호[4]회사이름[5]연봉[6]취직일
	 */
	public ArrayList<String[]>  procempbycourse(int opencourse_seq) {
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();
		ArrayList<String[]> row=new ArrayList<String[]>();
		try {
			String sql = "{call procempbycourse(?,?) }";
			conn = util.open();
			stat = conn.prepareCall(sql);
			stat.setInt(1, opencourse_seq);
			stat.registerOutParameter(2, OracleTypes.CURSOR);
			stat.executeQuery();
			
			rs=(ResultSet)stat.getObject(2);
			
			while(rs.next()) {
				String[] temp= {rs.getString("수강신청번호"),
								rs.getString("학생번호"),
								rs.getString("학생이름"),
								rs.getString("회사번호"),
								rs.getString("회사이름"),
								rs.getString("연봉"),
								rs.getString("취직일")};
				row.add(temp);
			}

			
			
			stat.close();
			conn.close();
			return row;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}//procempbycourse
	public ArrayList<String[]>  procempbyname(String pname) {
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();
		ArrayList<String[]> row=new ArrayList<String[]>();
		try {
			String sql = "{call procempbyname(?,?) }";
			conn = util.open();
			stat = conn.prepareCall(sql);
			stat.setString(1, pname);
			stat.registerOutParameter(2, OracleTypes.CURSOR);
			stat.executeQuery();
			
			rs=(ResultSet)stat.getObject(2);
			
			while(rs.next()) {
				String[] temp= {rs.getString("수강신청번호"),
								rs.getString("학생번호"),
								rs.getString("학생이름"),
								rs.getString("회사번호"),
								rs.getString("회사이름"),
								rs.getString("연봉"),
								rs.getString("취직일")};
				row.add(temp);
			}

			
			
			stat.close();
			conn.close();
			return row;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}//procempbyname
	
	public void procsetemp(int pregicourse_seq,int pcompany,String pday) {
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();
		Scanner scan=new Scanner(System.in);
		
		try {
			String sql = "{call procsetemp(?,?,?,?) }";
			conn = util.open();
			conn.setAutoCommit(false);
			
			stat = conn.prepareCall(sql);
			
			stat.setInt(1,pregicourse_seq);
			stat.setInt(2, pcompany);
			stat.setString(3, pday);
			stat.registerOutParameter(4, OracleTypes.CURSOR);
			stat.executeQuery();
			rs=(ResultSet)stat.getObject(4);
			
			while((rs.next())) {
				String[] temp= {
						rs.getString("학생번호"),
						rs.getString("학생이름"),
						rs.getString("회사번호"),
						rs.getString("회사이름"),
						rs.getString("연봉"),
						rs.getString("취직일")};
				String line= String.format("%s   %s   %s   %s          %s             %s",
						("학생번호"),
						("학생이름"),
						("회사번호"),
						("회사이름"),
						("연봉"),
						("취직일"));
				System.out.println(line);
				System.out.printf(" %s    %s     %s   %s     %,d만원     %s\n",temp[0],temp[1],temp[2],temp[3],Integer.parseInt(temp[4])/10000,temp[5]);
			}
			
			System.out.print("y 를 누르면 입력이 완료됩니다. (y/n) ");
			String check=scan.nextLine();
			if(check.toLowerCase().equals("y")) {
			System.out.println("입력을 완료했습니다.");
			}else {
				conn.rollback();
				System.out.println("입력을 취소했습니다.");
			}
			stat.close();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("입력을 실패했습니다.");
		}
	}//procsetemp
	/**
	 * 메소드 내에서 취업번호를 입력받아 데이터를 삭제합니다.
	 * @return
	 */
	public boolean procdeleteemp() {
		Scanner scan=new Scanner(System.in);
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();
		System.out.print("삭제할 취업번호를 입력하세요 : ");
		int pemployment_seq=scan.nextInt();
		scan.nextLine();
		try {
			String sql = String.format("{call procdeleteemp(?)}");
			
			conn = util.open();
			stat = conn.prepareCall(sql);
			stat.setInt(1,pemployment_seq);
			
			stat.executeQuery();

			stat.close();
			conn.close();
			System.out.printf("취업번호 %d의 정보가 삭제되었습니다.",pemployment_seq);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
	}//procdeleteemp
	
	/**
	 * 메소드 내에서 정보를 입력받아 데이터를 수정합니다.
	 * 입력한 정보는 수정되고 입력하지 않은 부분은 기존 값을 유지합니다.
	 * @return
	 */
	public boolean procupdateemp() {
		Scanner scan=new Scanner(System.in);
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();
		System.out.print("1.취업번호를 입력하세요 : ");
		String pemployment_seq=scan.nextLine();
		
		
		System.out.print("2.수정할 수강신청번호를 입력하세요 : ");
		String pregicourse_seq=scan.nextLine();
		
		System.out.println("3.수정할 취업일을 입력하세요 : (yyyymmdd 형식)");
		String pemploymentdate=scan.nextLine();
		
		System.out.print("4.수정할 회사번호를 입력하세요");
		String pcompany_seq=scan.nextLine();
		
		try {
			String sql = String.format("{call procupdateemp(?,?,?,?)}");

			conn = util.open();
			stat = conn.prepareCall(sql);
			stat.setInt(1, Integer.parseInt(pemployment_seq));
			stat.setInt(2, pregicourse_seq.equals("")? 0:Integer.parseInt(pregicourse_seq));
			stat.setString(3, pemploymentdate.equals("")? "default":pemploymentdate);
			stat.setInt(4, pcompany_seq.equals("")? 0:Integer.parseInt(pcompany_seq));
			stat.executeQuery();

			stat.close();
			conn.close();
			
			System.out.printf("취업번호 %d의 데이터가 수정되었습니다.",pemployment_seq);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("데이터 수정에 실패했습니다.");
			return false;
		}
	}//procupdateemp
	/**
	 * 회사의 정보를 가져옵니다.
	 */
	public void getCmpData() {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();
		Scanner scan=new Scanner(System.in);
		try {
			conn = util.open();
			stat = conn.createStatement();
			String sql = String.format("select*from tblCompany where company_seq>0");
			
			stat.executeQuery(sql);
			
			rs=stat.executeQuery(sql);
			ArrayList<String[]> row= new ArrayList<String[]>();
			while(rs.next()) {
				String[] temp=String.format("%s-%s-%s-%s-%s", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5).replace("-", "")).split("-");
				
				row.add(temp);
			}
			int i=0;
			
			String header=String.format(" %s  %s    %s   %s       %s                    %s", "번호","회사번호","회사명","연봉","번호" ,"주소");
			System.out.println(header);
			int page;
			while(i<row.size()) {
				//row[0] 회사번호 [1]사명 [2]연봉 [3]주소 [4]전화번호
				String temp=String.format("%3d  %3s  %4s  %s  %6s  %s"
												,i+1
												,row.get(i)[0]
												,row.get(i)[1]
												,Integer.parseInt(row.get(i)[2])/10000
												,row.get(i)[4]
												,row.get(i)[3]);
				System.out.println(temp);
				
				if((i+1)%30==0) {
					System.out.println("-----------------------------------------------------------");
					System.out.printf("\t\t\t%d 쪽/%d쪽 \n",(i/31)+1,((row.size()+1)/30)+1);
					System.out.println("1. 다음 페이지");
					System.out.println("2. 이전 페이지");
					System.out.println("3. 이전 메뉴로");//구현해야됨
					System.out.println("-----------------------------------------------------------");
					System.out.print("번호 입력 : ");
					page=scan.nextInt();//1이면 다음 30개,아니면 이전 30개
					scan.nextLine();
					
					if(page==2) {
						if(i<31) {
							System.out.println("이전 페이지가 없습니다.");
							System.out.println("다음 페이지를 검색합니다.");
							System.out.println("-----------------------------------------------------------");
							i++;
							continue;
							
						}else {
							i=i-60;
						}
					}
					if(page==3) {
						break;
					}
				}
				i++;
			}//while
			stat.close();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}//getCmpData
	/**
	 * 회사의 정보를 등록합니다.
	 * @return
	 */
	public boolean setNewcmp() {
		Scanner scan=new Scanner(System.in);
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();
		System.out.print("1. 회사 이름 입력 : ");
		String pname=scan.nextLine();
		System.out.print("2. 연봉을 입력: ");
		String psalary=scan.nextLine();
		System.out.print("3. 주소 입력: ");
		String paddress=scan.nextLine();
		System.out.print("4. 전화번호 입력: ");
		String ptel=scan.nextLine();
		try {
			String sql = String.format("{call procsetcmp(?,?,?,?)}");

			conn = util.open();
			
			stat = conn.prepareCall(sql);
			stat.setString(1, pname);
			stat.setString(2, psalary);
			stat.setString(3,paddress);
			stat.setString(4,ptel);
			stat.executeQuery();

			System.out.println("업체 정보가 추가되었습니다.");
			stat.close();
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}//procsetcmp
	/**
	 * 회사 정보를 수정합니다.
	 * @return
	 */
	public boolean procupdatecmp() {
		Scanner scan=new Scanner(System.in);
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();
		System.out.print("1. 회사 번호 입력: ");
		String pcompany_seq=scan.nextLine();
		System.out.print("2. 회사 이름 입력: ");
		String pname=scan.nextLine();
		System.out.print("3. 연봉 입력: ");
		String psalary=scan.nextLine();
		System.out.print("4. 주소 입력: ");
		String paddress=scan.nextLine();
		System.out.print("5. 전화번호 입력: ");
		String ptel=scan.nextLine();
		
		try {
			String sql = String.format("{call procupdatecmp(?,?,?,?,?)}");

			conn = util.open();
			stat = conn.prepareCall(sql);
			stat.setInt(1, Integer.parseInt(pcompany_seq));
			stat.setString(2, pname.equals("")? "default":pname);
			stat.setInt(3, psalary.equals("")? 0:Integer.parseInt(psalary));
			stat.setString(4, paddress.equals("")? "default":paddress);
			stat.setString(5, ptel.equals("")? "default":ptel);
			stat.executeQuery();

			stat.executeQuery();
			System.out.println("회사 정보가 수정되었습니다.");
			stat.close();
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("정보 수정에 실패했습니다.");
			return false;
		}
	}//procupdatecmp
	/**
	 * 회사 정보를 삭제합니다.
	 * @return
	 */
	public boolean procdelcmp() {
		System.out.print("삭제할 회사 번호 : ");
		Scanner scan=new Scanner(System.in);
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();
		String pcompany_seq=scan.nextLine();
		try {
			String sql = String.format("{call procdelcmp(?)}");
			
			conn = util.open();
			stat = conn.prepareCall(sql);
			stat.setInt(1, Integer.parseInt(pcompany_seq));
			
			stat.executeQuery();

			System.out.println("회사 정보가 삭제되었습니다.");
			stat.close();
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}//procdelcmp
}
