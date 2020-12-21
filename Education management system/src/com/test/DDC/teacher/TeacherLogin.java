package com.test.DDC.teacher;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

public class TeacherLogin {

//	public static void main(String[] args) throws InterruptedException {
//		teacherPrint();
//	}

	public void teacherPrint() {
		
		Scanner scan = new Scanner(System.in);


		try {
			while(true) {
			System.out.println("===============================================================================================");
			System.out.println("\t\t\t[교사 로그인]");
			System.out.println("===============================================================================================");
			System.out.println();
			System.out.print("\t\t\t 교사ID : ");
			String id = scan.nextLine();
			System.out.print("\t\t\t 교사PW : ");
			String pw = scan.nextLine();
			System.out.println();
			System.out.println("===============================================================================================");
			
			
			if (id.equals("강재지") && pw.equals("2767456")) {
				Main m = new Main();
				m.mainView("3");
				
			} else {
				System.out.println("아이디 또는 비밀번호를 잘못 입력하였습니다.");
			}
			
		
//			return;
			}
			
			// 교사의 정보가 교사 번호가 3번
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
