package com.test.DDC.admin;

import java.util.ArrayList;
import java.util.Scanner;

public class ManageCourseAndSub {
	static Manage m = new Manage();

	public void exe() {

		
		Scanner scan = new Scanner(System.in);
		ArrayList<String[]> row = new ArrayList<String[]>();
		boolean flag=true;
		while(flag) {
			row = m.procSearchOpencourse();
			String space = "              ";// 14칸
			String header = String.format("[%2s] [%2s] %s [%s] %s [%2s] [%2s] [%s]   [%s] [%2s]", "번호", "과정번호", space,
					"개설된 과정명", space, "강의실", "정원", "시작일", "종료일", "기간");
			System.out.println("======================================================================================================================");
			System.out.println("1. 관리자 -2. 개설 과정 관리                                     ");
			System.out.println("======================================================================================================================");
			System.out.println();
			System.out.println(header);
			int i = 0;
			while (i < row.size()) {
				if (i == 8) {
					String str = String.format("%3s %7s   %35s      %3s %6s  %3s %s %s", row.get(i)[0]// PK
							, row.get(i)[1]// 개설과정번호
							, row.get(i)[2]// 과정명
							, row.get(i)[3]// 강의실
							, row.get(i)[4]// 수강생
							, row.get(i)[5]// 시작일
							, row.get(i)[6], row.get(i)[7]);// 기간
					System.out.println(str);
					i++;
				} else {
					String str = String.format("%3s %7s   %35s \t  %3s %6s  %3s %s %s", row.get(i)[0]// PK
							, row.get(i)[1]// 개설과정번호
							, row.get(i)[2]// 과정명
							, row.get(i)[3]// 강의실
							, row.get(i)[4]// 수강생
							, row.get(i)[5]// 시작일
							, row.get(i)[6], row.get(i)[7]);// 기간
					System.out.println(str);
					i++;
				}
			}
			System.out.println("a. 개설과정의 과목정보 검색");
			System.out.println("b. 개설과정의 교육생 검색");
			System.out.println("c. 새로운 과정 만들기");
			System.out.println("d. 과정 개설하기");
			System.out.println("e. 과정 지우기");
			System.out.println("f. 개설된 과정 지우기");
			System.out.println("0. 뒤로가기");
			System.out.print("원하는 메뉴를 입력하세요 : ");
			String answer = scan.nextLine();
			if (answer.toLowerCase().equals("a")) {
				System.out.print("검색할 개설과정 번호 입력 : ");
				int opencourseNum = scan.nextInt();
				scan.nextLine();
				a(opencourseNum);
				System.out.print("이전메뉴로 : (Enter)");
				scan.nextLine();
				continue;
			} else if (answer.toLowerCase().equals("b")) {
				System.out.print("검색할 과정 번호 입력 : ");
				int opencourseNum = scan.nextInt();
				scan.nextLine();
				b(opencourseNum);
				System.out.print("이전메뉴로 : (Enter)");
				scan.nextLine();
				continue;
			} else if (answer.toLowerCase().equals("c")) {
				c();
				System.out.print("이전메뉴로 : (Enter)");
				scan.nextLine();
				continue;
			} else if (answer.toLowerCase().equals("d")) {
				d();
				System.out.print("이전메뉴로 : (Enter)");
				scan.nextLine();
				continue;
			} else if (answer.toLowerCase().equals("e")) {
				e();
				System.out.print("이전메뉴로 : (Enter)");
				scan.nextLine();
				continue;
			} else if (answer.toLowerCase().equals("f")) {
				f();System.out.print("이전메뉴로 : (Enter)");
				scan.nextLine();
				continue;
			} else if(answer.toLowerCase().equals("g")) {
				
				System.out.println("1. 개설 중 과정(폐지된 과정 제외)");
				System.out.println("2. 모든 과정");
				String input=scan.nextLine();
				if(input.equals("1")) {
					g();	
				}else if(input.equals("2")) {
					g2();
				}
				
				System.out.print("이전메뉴로 : (Enter)");
				scan.nextLine();
				continue;
			}else if (answer.equals("0")) {
				flag=false;
				break;
			}
		}
	}// main

	private void g2() {
		// TODO Auto-generated method stub
		Scanner scan=new Scanner(System.in);
		ArrayList<String[]> row=new ArrayList<String[]>();
		System.out.println("===========================================================");
		System.out.println("1. 관리자 -2. 개설 과정 관리 g.과정 조회하기");
		System.out.println("===========================================================");
		System.out.println();
		row=m.procgetcourselist2();
		int i=0;
		String header =String.format("[%s]                 [%s]                     [%s]","번호","과정명","개설여부" );
		System.out.println(header);
		while(i<row.size()) {
			
			String str= String.format("%2s\t%10s\t\t\t\t%s", row.get(i)[0],row.get(i)[1],row.get(i)[2]);
			System.out.println(str);
			i++;
		}
	}
	
	private void g() {
		// TODO Auto-generated method stub
		Scanner scan=new Scanner(System.in);
		ArrayList<String[]> row=new ArrayList<String[]>();
		System.out.println("===========================================================");
		System.out.println("1. 관리자 -2. 개설 과정 관리 g.과정 조회하기");
		System.out.println("===========================================================");
		System.out.println();
		row=m.procgetcourselist();
		int i=0;
		String header =String.format("[%s]                 [%s]","번호","과정명" );
		System.out.println(header);
		while(i<row.size()) {
			String str= String.format("%2s\t%10s", row.get(i)[0],row.get(i)[1]);
			System.out.println(str);
			i++;
		}
	}
	
	private static void f() {// f. 개설된 과정 지우기
		// TODO Auto-generated method stub
		Scanner scan=new Scanner(System.in);
		System.out.println("===========================================================");
		System.out.println("1. 관리자 -2. 개설 과정 관리 f.단일 과정 지우기");
		System.out.println("===========================================================");
		System.out.println();
		System.out.print("삭제할 과정번호 입력 : ");
		int pcourseList_seq=scan.nextInt();
		scan.nextLine();
		
		if(m.procDeleteCourseList(pcourseList_seq)) {
			System.out.printf("%d번 과정이 삭제되었습니다.\n",pcourseList_seq);
			System.out.println("-----------------------------------------------------------");

		}else {
			System.out.println("삭제에 실패했습니다.");
			System.out.println("-----------------------------------------------------------");

		}
	}

	private static void e() {// e. 과정 지우기 // 과정지우기를 두번하면 다시 살아나는 기적?, 그렇다고 유효성 검사를 하기에는 너무 귀찮다.
		// TODO Auto-generated method stub
		Scanner scan=new Scanner(System.in);
		System.out.println("===========================================================");
		System.out.println("1. 관리자 -2. 개설 과정 관리 e.개설된 과정 지우기");
		System.out.println("===========================================================");
		System.out.println();

		System.out.printf("■ 삭제할 과정 번호 입력 : ");
		int popencourse_seq=scan.nextInt();
		scan.nextLine();
		if(m.procDeleteopenCourse(popencourse_seq)) {
			System.out.println("과정이 성공적으로 삭제되었습니다.");
			System.out.println("-----------------------------------------------------------");

		}else {
			System.out.println("과정 삭제에 실패했습니다.");
			System.out.println("-----------------------------------------------------------");

		}
		
	}

	private static void d() {// d. 과정 개설하기 숫자 잘못 입력하면 오류나옴
		// TODO Auto-generated method stub
		Scanner scan=new Scanner(System.in);
		System.out.println("===========================================================");
		System.out.println("1. 관리자 -2. 개설 과정 관리 d.과정 개설하기");
		System.out.println("===========================================================");
		System.out.println();

		System.out.printf("■ 개설할 과정 번호 입력 : ");
		int courselist_seq=scan.nextInt();
		System.out.printf("■ 개설할 과정 시작일 입력 : ('yyyymmdd'형식) 주말은 입력할 수 없습니다. ");
		String open=scan.next();
		scan.skip("\r\n");
		System.out.printf("■ 강의실 배정 : ");
		int room=scan.nextInt();
		scan.nextLine();
		
		if(m.procOpenCourse(courselist_seq, open, room)) {
			String[] row=m.procSearchOpencourse(courselist_seq);
			System.out.println("-----------------------------------------------------------");
			System.out.printf("새로운 강의가 개설되었습니다.\n");
			System.out.printf("[%s]\t\t\t[%s]\t\t\t     [%s]    [%s]    [%s]   [%s]\n","과정번호","과정명","시작일","종료일","기간","강의실");
			String str="";
			str=String.format("    %s    %s    %s   %s   %s       %s",row[0],row[2],row[5],row[6],row[7],row[3] );
			System.out.println(str);
			System.out.println("-----------------------------------------------------------");
			
			
			
		}else {
			System.out.println("강의 개설에 실패했습니다.");//프로시저에 트리거 설정해야 할듯
		}

	}

	private static void c() {// c. 새로운 과정 만들기
		// TODO Auto-generated method stub
		Scanner scan=new Scanner(System.in);
		int sub1,sub2,sub3,sub4,sub5,sub6;
		ArrayList<String[]> row=new ArrayList<String[]>();
		System.out.println("===========================================================");
		System.out.println("1. 관리자 -2. 개설 과정 관리 c.새로운 과정 만들기                         ");
		System.out.println("===========================================================");
		System.out.println();

		System.out.printf("■ 추가할 과정 이름 설정 : ");
		String name= scan.nextLine();
		System.out.println();
		row=m.procSubject();
		String header =String.format("[%3s]  [%s]  [%s]", "번호", "기간","과목명");
		System.out.println(header);
		for(int i=0;i<row.size();i++) {

			String str= String.format("  %s   %s %s"
												,row.get(i)[0]
												,row.get(i)[2]
												,row.get(i)[1]);
			System.out.println(str);
			
		}
		System.out.printf("■ 과정에 추가할 과목 입력\n");
		System.out.printf("1. 첫번째 과목번호 : ");
		sub1=scan.nextInt();
		scan.nextLine();
		System.out.printf("2. 두번째 과목번호 : ");
		sub2=scan.nextInt();
		scan.nextLine();
		System.out.printf("3. 세번째 과목번호 : ");
		sub3=scan.nextInt();
		scan.nextLine();
		System.out.printf("4. 네번째 과목번호 : ");
		sub4=scan.nextInt();
		scan.nextLine();
		System.out.printf("5. 다섯째 과목번호 : ");
		sub5=scan.nextInt();
		scan.nextLine();
		System.out.printf("6. 마지막 과목번호 : ");
		sub6=scan.nextInt();
		scan.nextLine();
		m.procNewcourseListSubject(name, sub1, sub2, sub3, sub4, sub5, sub6);
		System.out.println("새로운 과정이 생성되었습니다.");
		System.out.println("-----------------------------------------------------------");
		
		
		
	}

	private static void b(int opencourseNum) {// b. 개설과정의 교육생 검색

		String cCourse=m.procSearchOpencourse(opencourseNum)[2];//과정명
		String header = String.format("[%s] [%s] [%s] [%s]   [%s] [%s]",
				"번호", "이름", "주민번호 뒷자리", "전화번호", "등록일", "수료여부");
		
		int i=0;//수강생 번호입력+반복자
		
		System.out.println("===========================================================");
		System.out.println("1. 관리자 -2. 개설 과정 관리 b.개설과정의 교육생 검색");
		System.out.println("===========================================================");
		System.out.println();
		System.out.printf("■ \"%s\" 과정의 수강생 목록\n",cCourse);
		System.out.println("-----------------------------------------------------------");
		System.out.println(header);
		int until=Integer.parseInt(m.procSearchOpencourse(opencourseNum)[4]);
		ArrayList<String[]> row=m.procgetStudentInfo(opencourseNum);		
		while(i<until) {
			
			
			String str=String.format("%3d %4s          %7s   %s %s %s",i+1,row.get(i)[0],row.get(i)[1].substring(6,13),row.get(i)[2],row.get(i)[3],row.get(i)[6] );
			System.out.println(str);
			i++;
		}
		
		System.out.println("-----------------------------------------------------------");
		
	
		//학생 번호 이름 주민번호 뒷자리 전화번호 등록일 수료여부
		
	}

	private static void a(int opencourseNum) {//a. 개설과정의 과목정보 검색
		// TODO Auto-generated method stub
		
		//[0]과목번호,[1]과정번호,[2]과목명,[3]과목시작일,[4]과목종료일,[5]과정시작일,[6]과정종료일,[7]선생
		String[] row=m.procGetOpenCourseInfo(opencourseNum).get(0).split("-");
		String cRoom=m.procSearchOpencourse(opencourseNum)[3];
		System.out.println("===========================================================");
		System.out.println("1. 관리자 -2. 개설 과정 관리 a.과목검색                         ");
		System.out.println("===========================================================");
		System.out.println();
		System.out.printf("■ 과정명 : %s\n",row[2]);
		System.out.printf("■ 교사명 : %s\n",row[7]);
		System.out.printf("■ 강의실 : %s\n\n",cRoom);
		System.out.printf("■ 과정기간 \n%s ~ %s\n\n",row[5],row[6]);
		System.out.printf("■ 과목명 및 기간\n");
		//과정명 및 기간작성
		int i=0;
		ArrayList<String> row2=m.procGetOpenCourseInfo(opencourseNum); 
		while (i < 6) {// [2]과목명
			String[] temp=row2.get(i).split("-");
			System.out.printf("%d. %s\n",i+1,temp[2]);
			i++;
		}
		System.out.println("-----------------------------------------------------------");
		
	}
}
