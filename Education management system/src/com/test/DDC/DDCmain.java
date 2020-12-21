package com.test.DDC;

import java.util.Scanner;

import com.test.DDC.admin.AdminMain;
import com.test.DDC.student.StudentMain;
import com.test.DDC.teacher.TeacherLogin;

/**
 * 메인 화면 구현 클래스
 * @author 전혜원, 윤대웅
 *
 */
public class DDCmain {
	
	public static void main(String[] args)  {
		
		printMain();	
		
	}

	/**
	 * 메인화면 출력 메소드
	 */
	public static void printMain() {
		
		Scanner scan = new Scanner(System.in);
		
		Boolean loop = true;
		
		System.out.println();
		//메인화면
		ddcMain();
		
		
		System.out.println();
		System.out.println("\t\t\t\t============================================================");
		System.out.println("\t\t\t\t\t\t[1] 관리자 모드");
		System.out.println("\t\t\t\t\t\t[2] 학생 모드");
		System.out.println("\t\t\t\t\t\t[3] 교사 모드");
		System.out.println("\t\t\t\t\t\t[0] 프로그램 종료");
		System.out.println("\t\t\t\t============================================================");
		System.out.print("\t\t\t\t\t\t선택 : ");
		String select = scan.nextLine();
		
		System.out.println();
		System.out.println();
s
		
		while(loop) {

			switch(select) {
				case "1" : // 관리자모드
					AdminMain admin = new AdminMain();
					admin.adminLogin();
					break;
					
				case "2" : // 학생 모드
					StudentMain student = new StudentMain();
					student.StudentLogin();
					break;
					
				case "3" : // 교사 모드
					TeacherLogin teacher = new TeacherLogin();
					teacher.teacherPrint();
					break;
					
				case "0" : // 프로그램 종료
					System.out.println("\t\t\t\t============================================================");
					System.out.println("\t\t\t\t\tDouble Dragon Center 시스템을 종료합니다.");
					
					System.out.print("");
					String aaaa = scan.nextLine();
					
					return;
//					loop = false;
//					break;
					
			}	
//			loop = false;
		}
	}

	private static void ddcMain() {
	
		System.out.println("\t\t\t\t                    ww                           \r\n" + 
	            "\t\t\t\t" +"                    ZW Z                          \r\n" + 
	            "\t\t\t\t" +"               ZZZEZ , Z                          \r\n" + 
	            "\t\t\t\t" +"              Z  8 ZW  Z                          \r\n" + 
	            "\t\t\t\t" +"              EZ5 WWw ZZ                BZj       \r\n" + 
	            "\t\t\t\t" +"                ZZ  BBZ ZZ5 ZZD       yZZ ZZ      \r\n" + 
	            "\t\t\t\t" +"                 9Z   Z Z8yZZ EZ     zZ   Z5      \r\n" + 
	            "\t\t\t\t" +"                  ZZZZD Wy  Zy Z     Z,  8Z       \r\n" + 
	            "\t\t\t\t" +"               BZZ             ZZZ, Z, WZjDjZj    \r\n" + 
	            "\t\t\t\t" +" ,zE         WZZ               j  ZZ    B 5 ZZ    \r\n" + 
	            "\t\t\t\t" +"    ZZw     ZZ  yZw                 Z ZZZZZZZ     \r\n" + 
	            "\t\t\t\t" +"      ZZ   ZZ     y                  Zj           \r\n" + 
	            "\t\t\t\t" +"       Z  ZZ                          Z           \r\n" + 
	            "\t\t\t\t" +"     jZ   Z   ZZZ             5Zz     8Z          \r\n" + 
	            "\t\t\t\t" +"    wZ   ZZ   ZZZ               w      Z          \r\n" + 
	            "\t\t\t\t" +"    Z5  DZ                             Z          \r\n" + 
	            "\t\t\t\t" +"    WZ EZ                  ZZZZ        Z          \r\n" + 
	            "\t\t\t\t" +"     zZZ                ,  wZZD        Z          \r\n" + 
	            "\t\t\t\t" +"       ZBD                             Z          \r\n" + 
	            "\t\t\t\t" +"       Z  DW 9Z8                       Z          \r\n" + 
	            "\t\t\t\t" +"       ZZ      E ZZ                   Z, ZZZZZZ   \r\n" + 
	            "\t\t\t\t" +"        DZy       wW                 yZ Z      ZE \r\n" + 
	            "\t\t\t\t" +"          ZZZ                5B     5Z  Z         \r\n" + 
	            "\t\t\t\t" +"            yZZZ              5Z8  ZZ DZ          \r\n" + 
	            "\t\t\t\t" +"                ZZZZy           WZZBZZ   9j       \r\n" + 
	            "\t\t\t\t" +"                Z   9ZZZZZZZZZZZW Z    ZZ ZB      \r\n" + 
	            "\t\t\t\t" +"                Z     8            ZyZZ   Z8      \r\n" + 
	            "\t\t\t\t" +"                Zy    Z   Z    8Z   ZZ    Z,      \r\n" + 
	            "\t\t\t\t" +"                 ZDZZ8 Z   Z     ZZ  D   wZ       \r\n" + 
	            "\t\t\t\t" +"                 ZD    ,Z   Zj    Z     ZZ        \r\n" + 
	            "\t\t\t\t" +"                  Z8DEZZ 8   9ZZZZ   zBZZ         \r\n" + 
	            "\t\t\t\t" +"                   ZZ    ZZBy    w Z ZZZ          \r\n" + 
	            "\t\t\t\t" +"                     ZZZ8   Zz 9z  ZZZ            \r\n" + 
	            "\t\t\t\t" +"                       WZZZZZDZZZZZ,             \r\n" +
	            "                                          ,9ZZZZZZj                         WBBBD,                   ,                                      \r\n" + 
	            "                         ZZZ ZZZ  ZZ    9ZZz     jZZZ   ZZZZZZZZZZZZZz   ZZZZZZZZZZZZ    jZZZZW DZZwZZ  ZZZZZZZZZE ZZZ                      \r\n" + 
	            "                        ZZZZZZZZZ ZZZZz jZZBwy55wWZZZ              ZZw  5ZZB      ZZZ    ZZ WZZyZZZ ZZ  ZZ5        ZZZ                      \r\n" + 
	            "                       9ZZ ZZZ ZZyZZ      jZZ,W,ZZZ                ZZ,      y9ZZ8w      ZZZ  ZZZ ZZ ZZ  ZZZ     ,  ZZZ                      \r\n" + 
	            "                       ZZ  5D   B5ZZ   ZZZZZZZZZZZZZZZ    ZZy ZZZ  ZZw ZZZZZZZZZZZZZZZ ZZZ    ZZZZZ ZZ  ZZZZZZZZZ9ZZZZ                      \r\n" + 
	            "                          ZZZZZZZZ9       WZZZZZZZD       ZZB ZZZ  ZZB     ZZ    ZZ                 D9  ZZz        ZZZ                      \r\n" + 
	            "                        ZZZ       ZZZ   ZZZ       9ZZ     ZZw ZZZ       ZZZZZZZZZZZZZ    ZZ9            ZZz        ZZZ                      \r\n" + 
	            "                        BZZZ8DjDzZZZB   ZZZZBjjWD8ZZZ  w  ZZz ZZZ    ,            5ZZ    ZZz         j  ZZZZZZZZZE ZZZ                      \r\n" + 
	            "                           8ZZZZZ8        ,EZZZZZZy    ZZZZZZZZZZZZZZZ            9ZZ    ZZZZZZZZZZZZZ             ZZZ               ");
		
	}
}


