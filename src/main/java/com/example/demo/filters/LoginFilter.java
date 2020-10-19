package com.example.demo.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.example.demo.model.Employee;

@Component
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String context_path = ((HttpServletRequest)request).getContextPath();
		String servlet_path = ((HttpServletRequest)request).getServletPath();

		if(!servlet_path.matches("/webjars.*")){
			HttpSession session = ((HttpServletRequest)request).getSession();

			//セッションスコープに保存された従業員情報を取得
			Employee e = (Employee)session.getAttribute("login_employee");

			if(!servlet_path.equals("/login")) {
				if(e == null) {
					((HttpServletResponse)response).sendRedirect(context_path + "/login");
					return;
				}

			} else {
				if(e != null) {
					((HttpServletResponse)response).sendRedirect(context_path + "/");
					return;
				}
			}
		}

		chain.doFilter(request, response);
	}

}
