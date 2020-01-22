package org.analyser.security;

import javax.servlet.http.HttpSession;

import org.analyser.entities.AppUser;

public class SessionHandler {

	public static void addUser(HttpSession session, AppUser user) {
		session.setAttribute("user", user);
	}

	public static AppUser getUser(HttpSession session) {
		AppUser user = (AppUser) session.getAttribute("user");
		return user;
	}
}
