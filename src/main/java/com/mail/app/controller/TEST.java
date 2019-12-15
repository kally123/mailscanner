package com.mail.app.controller;

import static com.mail.app.components.KeyUtils.CURRENCY_REPLACEMENT_CHARACTERS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TEST {

	public static void main(String[] args) {
		String s1 = "1,095";
		s1 = s1.replaceAll(CURRENCY_REPLACEMENT_CHARACTERS, "");
		System.out.println(s1);
		List l1 = new ArrayList();
		l1.add("K");
		l1.add("A");
		l1.add("L");
		Set s = new HashSet<>();
		s.add("bvjdvjdv");
		s.add("fbjebjk");
		List<Object> l = new ArrayList<>();
		l.add("Kally");
		l.add(1);
		l.add(1.2);
		l.add(1l);
		l.add(new String[] { "1", "2" }.toString());
		l.add(l1);
		l.add(new ArrayList());
		l.add(s);
		System.out.println(l);
	}
}