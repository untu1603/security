package com.security.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

public class CookieUtil {
    public static List<Cookie> getCookie(HttpServletRequest request, String... names) {
        Cookie[] cookies = request.getCookies();
        List<Cookie> cookieList = new ArrayList<>();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                for (String name : names)
                {
                    if (cookie.getName().equals(name)) {
                        cookieList.add(cookie);
                    }
                }
            }
        }
        return cookieList;
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge, boolean httpOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(httpOnly);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void delete(HttpServletRequest request, HttpServletResponse response,String... names){
        List<Cookie> cookieList = getCookie(request, names);
        cookieList.forEach(cookie -> {
            cookie.setValue(null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        });

    }
}
