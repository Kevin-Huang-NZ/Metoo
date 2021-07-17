package me.too.scaffold.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {

	public void setSession(HttpServletRequest request, String key, Object obj) {
		HttpSession session = request.getSession();
		session.setAttribute(key, obj);
	}

	@SuppressWarnings("unchecked")
	public <T> T getSession(HttpServletRequest request, String key, Class<T> clazz) {
		HttpSession session = request.getSession();

		return (T) session.getAttribute(key);
	}

}
