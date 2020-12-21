package com.test.DDC.teacher;

import java.util.Scanner;

import com.test.DDC.DDCmain;

public class Main {

	public void mainView(String tnum) {

		Scanner scan = new Scanner(System.in);
		boolean loop = true;

		while (loop) {

			System.out.println("\t\t\t강재지 선생님 안녕하세요.");
			System.out.println(
					"===============================================================================================");
			System.out.println("\t\t\t[1] 강의 스케줄 조회");
			System.out.println("\t\t\t[2] 배점 입출력");
			System.out.println("\t\t\t[3] 성적 입출력");
			System.out.println("\t\t\t[4] 출결 관리 및 출결 조회");
			System.out.println("\t\t\t[5] 로그아웃");
			System.out.println(
					"===============================================================================================");
			System.out.print("\t\t\t메뉴 선택 : ");
			String menu = scan.nextLine();

			switch (menu) {

			case "1":
				LectureOutput a = new LectureOutput();
				a.lectureView("3");
				break;
			case "2":
				ScoringInOut b = new ScoringInOut();
				b.ScoringView("3");
				break;
			case "3":
				Score_Output s = new Score_Output();
				s.printSubject("3");
				break;
			case "4":
				TeacherAttendance t = new TeacherAttendance();
				t.printAttandance("3");
				break;
			case "5":
				DDCmain main  = new DDCmain();
				main.printMain();
			}
		}
	}

}
