package kr.org.dagather.common.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import kr.org.dagather.common.util.AuthUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(0)
@Component
public class AuthFilter extends OncePerRequestFilter {

	// @Getter
	// public static String currentMemberId;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws IOException, ServletException {

		System.out.println("request: " + request);
		String currentMemberId = request.getHeader("Authorization");
		AuthUtil.setMemberId(currentMemberId);
		System.out.println("currentMemberId: " + currentMemberId);
		System.out.println("AuthUtil MemberId: " + AuthUtil.getMemberId());
		filterChain.doFilter(request, response);
	}
}
