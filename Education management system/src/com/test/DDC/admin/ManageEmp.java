package com.test.DDC.admin;

import java.util.ArrayList;
import java.util.Scanner;

public class ManageEmp {
	static Manage m = new Manage();
	
	public void exe() {
		boolean flag=true;
		while(flag) {
			Scanner scan=new Scanner(System.in);
			System.out.println("===========================================================");
			System.out.println("1. 관리자 -8.취업자 조회 및 관리 기능 - 등록하기");
			System.out.println("===========================================================");
			System.out.println("a. 모든 취업자 검색");
			System.out.println("b. 이름으로 취업자 검색");
			System.out.println("c. 과정번호로 취업자 검색");
			System.out.println("d. 새로운 취업자 추가");
			System.out.println("e. 취업자 정보 삭제");
			System.out.println("f. 취업자 정보 수정");
			System.out.println("g. 회사 정보 검색");
			System.out.println("h. 회사 정보 신규 등록");
			System.out.println("i. 회사 정보 수정");
			System.out.println("j. 회사 정보 삭제");
			System.out.println("이전 메뉴로  (Enter)");
			System.out.println("-----------------------------------------------------------");
			System.out.print("번호 입력 : ");
			String num=scan.nextLine();
			switch (num) {
			case "a":
				getAllEmployee();// 모든 취업자 검색
				System.out.print("이전메뉴로 : (Enter)");
				scan.nextLine();
				break;
			case "b":
				getEmpByname();//이름으로 검색
				System.out.print("이전메뉴로 : (Enter)");
				scan.nextLine();
				break;
			case "c":
				getEmpByCourse();//과정번호로 검색
				System.out.print("이전메뉴로 : (Enter)");
				scan.nextLine();
				break;
			case "d":
				setEmp();//새로운 취업자 추가
				System.out.print("이전메뉴로 : (Enter)");
				scan.nextLine();
				break;
			case "e":
				setDelEmp();//취업자 정보 삭제
				System.out.print("이전메뉴로 : (Enter)");
				scan.nextLine();
				break;
			case "f":
				setUpEmp();//취업자 정보 수정
				System.out.print("이전메뉴로 : (Enter)");
				scan.nextLine();
				break;
			case "g":
				getCmpData();//회사 정보 검색
				System.out.print("이전메뉴로 : (Enter)");
				scan.nextLine();
				break;
			case "h":
				setNewcmp();//회사 정보 신규 등록
				System.out.print("이전메뉴로 : (Enter)");
				scan.nextLine();
				break;
			case "i":
				procupdatecmp();//회사 정보 수정
				System.out.print("이전메뉴로 : (Enter)");
				scan.nextLine();
				break;
			case "j":
				setDelcmp();//회사 정보 삭제
				System.out.print("이전메뉴로 : (Enter)");
				scan.nextLine();
				break;
			default :
					flag=false;
					
					
			}
		}
		//getAllEmployee();// 모든 취업자 검색
		//getEmpByname();//이름으로 검색
		//getEmpByCourse();//과정번호로 검색
		//setEmp();//새로운 취업자 추가
		//setDelEmp();//취업자 정보 삭제
		//setUpEmp();//취업자 정보 수정
		//getCmpData();//회사 정보 검색
		//setNewcmp();//회사 정보 신규 등록
		//procupdatecmp();//회사 정보 수정
		//setDelcmp();//회사 정보 삭제
		

		
	}//exe
	private void setDelcmp() {
		// TODO Auto-generated method stub
		System.out.println("===========================================================");
		System.out.println("1. 관리자 -8.취업자 조회 및 관리 기능 - j.회사 정보 삭제");
		System.out.println("===========================================================");
		m.procdelcmp();
	}
	private void procupdatecmp() {
		// TODO Auto-generated method stub
		System.out.println("===========================================================");
		System.out.println("1. 관리자 -8.취업자 조회 및 관리 기능 - i.회사 정보 수정");
		System.out.println("===========================================================");
		m.procupdatecmp();
	}
	private void setNewcmp() {
		// TODO Auto-generated method stub
		System.out.println("===========================================================");
		System.out.println("1. 관리자 -8.취업자 조회 및 관리 기능 - h.회사정보 신규 등록");
		System.out.println("===========================================================");
		m.setNewcmp();
	}
	private void getCmpData() {
		// TODO Auto-generated method stub
		System.out.println("===========================================================");
		System.out.println("1. 관리자 -8.취업자 조회 및 관리 기능 - g.연계 업체 정보");
		System.out.println("===========================================================");
		m.getCmpData();
	}
	private void setUpEmp() {//정보를 입력받아 데이터 수정
		// TODO Auto-generated method stub
		System.out.println("===========================================================");
		System.out.println("1. 관리자 -8.취업자 조회 및 관리 기능 - f.취업자 정보 수정");
		System.out.println("===========================================================");
		m.procupdateemp();
	}
	private void setDelEmp() {
		// TODO Auto-generated method stub
		System.out.println("===========================================================");
		System.out.println("1. 관리자 -8.취업자 조회 및 관리 기능 - e.취업자 정보 삭제");
		System.out.println("===========================================================");
		//
		m.procdeleteemp();
	}
	private void setEmp() {
		// TODO Auto-generated method stub
		//학생 정보와 회사 정보를 보여줘야하지 않나?
		Scanner scan=new Scanner(System.in);
		System.out.println("===========================================================");
		System.out.println("1. 관리자 -8.취업자 조회 및 관리 기능 - d.신규 취업자 정보 등록");
		System.out.println("===========================================================");
		//수강신청번호를 입력하기 전에 이름을 검색하면 동명이인 찾아주기?
		System.out.print("1. 수강신청번호를 입력하세요 : ");
		int pregicourse_seq=scan.nextInt();
		scan.nextLine();
		//회사번호 입력하기 전에 회사이름을 검색하면 분점검색해주기?
		System.out.print("2. 회사번호를 입력하세요 : ");
		int pcompany=scan.nextInt();
		scan.nextLine();
		System.out.print("3. 취업날짜를 입력하세요 : (yyyymmdd) ");
		
		String pday=scan.nextLine();

		m.procsetemp(pregicourse_seq, pcompany, pday);
	}
	
	private void getEmpByCourse() {
		// TODO Auto-generated method stub
		System.out.println("===========================================================");
		System.out.println("1. 관리자 -8.취업자 조회 및 관리 기능 c.과정번호로 정보 검색");
		System.out.println("===========================================================");
		String header=String.format("[%s] [%s] [%s] [%s] [%s]   [%s]      [%s]","번호","수강신청번호","학생이름","회사번호","회사이름","연봉","취업일");
		
		Scanner scan=new Scanner(System.in);
		ArrayList<String[]> row= new ArrayList<String[]>();
		System.out.print("검색할 과정 번호를 입력하세요 : ");
		int opencourse_seq=scan.nextInt();
		scan.nextLine();
		row=m.procempbycourse(opencourse_seq);
		int i=0;
		
		
		System.out.println(header);
		int page;
		while(i<row.size()) {
		
			String temp=String.format(" %3d      %3s       %s     %s    %6s    %,d만원      %s"
											,i+1
											,row.get(i)[0]
											,row.get(i)[2]
											,row.get(i)[3]
											,row.get(i)[4]
											,Integer.parseInt(row.get(i)[5])/10000
											,row.get(i)[6]
													);
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
	}
	private void getEmpByname() {
		// TODO Auto-generated method stub
		System.out.println("===========================================================");
		System.out.println("1. 관리자 -8.취업자 조회 및 관리 기능 b.취업자 이름으로 정보 검색");
		System.out.println("===========================================================");
		String header=String.format("[%s] [%s] [%s] [%s] [%s]   [%s]      [%s]","번호","수강신청번호","학생이름","회사번호","회사이름","연봉","취업일");
		
		Scanner scan=new Scanner(System.in);
		ArrayList<String[]> row= new ArrayList<String[]>();
		System.out.print("검색할 이름을 입력하세요 : ");
		String pname =scan.nextLine();
		row=m.procempbyname(pname);
		int i=0;
		
		
		System.out.println(header);
		while(i<row.size()) {
		
			String temp=String.format(" %3d      %3s       %s     %s    %6s    %,d만원      %s"
											,i+1
											,row.get(i)[0]
											,row.get(i)[2]
											,row.get(i)[3]
											,row.get(i)[4]
											,Integer.parseInt(row.get(i)[5])/10000
											,row.get(i)[6]
													);
			System.out.println(temp);
			if((i+1)%30==0) {
				System.out.printf("\t\t\t%d 쪽/%d쪽 \n",(i/30)+1,(row.size()+1)/30);
				System.out.println("1. 다음 페이지");
				System.out.println("2. 이전 페이지");
				System.out.println("3. 이전 메뉴로");//구현해야됨
				System.out.print("번호 입력 : ");
				int page=scan.nextInt();//1이면 다음 5개,2면 이전 5개
				scan.nextLine();
				if(page==2) {
					i=i-60;
				}
			}
			i++;
		}//while
	}
	private void getAllEmployee() {
		System.out.println("===========================================================");
		System.out.println("1. 관리자 -8.취업자 조회 및 관리 기능  a.취업자 조회");
		System.out.println("===========================================================");
		String header=String.format("[%s] [%s] [%s] [%s] [%s]   [%s]      [%s]","번호","수강신청번호","학생이름","회사번호","회사이름","연봉","취업일");
		System.out.println(header);
		ArrayList<String[]> row= new ArrayList<String[]>();
		row=m.procemployee();
		int i=0;
		Scanner scan=new Scanner(System.in);
		int page;
		while(i<row.size()) {
		
			String temp=String.format(" %3d      %3s       %s     %s    %6s    %,d만원      %s"
											,i+1
											,row.get(i)[0]
											,row.get(i)[2]
											,row.get(i)[3]
											,row.get(i)[4]
											,Integer.parseInt(row.get(i)[5])/10000
											,row.get(i)[6]
													);
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
	}
}//ManageEmp
