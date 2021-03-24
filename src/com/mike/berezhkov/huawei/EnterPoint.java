package com.mike.berezhkov.huawei;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class EnterPoint {

	public static void main(String[] args) throws FileNotFoundException {
        boolean fillingCourses=false;
        String lastCourse = "";
		HashMap<String, List<Integer>> grades = 
				new HashMap<String, List<Integer>>();
		File f = new File("target.2cp");
		Scanner reader = new Scanner(f);
		while (reader.hasNextLine()) {
			String[] data = reader.nextLine().split("[,\\s\\:;{}]");
			for(String s: data) {
				if(s.equals("Courses")) {
					fillingCourses=true;
					continue;
				}
				if(s.equals("Students")) {
					fillingCourses=false;
					continue;
				}
				if(fillingCourses && s!=null && s.length()>0) {
					grades.put(s, new LinkedList<Integer>());
					continue;
				}
				if( (!fillingCourses) && grades.containsKey(s) ) {
					lastCourse = s;
					continue;
				}
				if(s!=null && s.length()>0 && 
						grades.containsKey(lastCourse)) {
					try {
						int grade = Integer.parseInt(s);
						grades.get(lastCourse).add(grade);
					} catch (NumberFormatException e) {}
				}
			}
		}
		reader.close();
		for(String course : grades.keySet()) {
			System.out.println(course+":\t"+grades.get(course)
				.stream().collect(Collectors.averagingDouble(a->(double)a)));
		}
	}

}
