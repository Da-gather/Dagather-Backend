package kr.org.dagather.common.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(0)
public class AuthFilter extends OncePerRequestFilter {

	@Getter
	public static String currentMemberId;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws IOException, ServletException {

		currentMemberId = request.getHeader("Authorization");
		System.out.println("currentMemberId: " + currentMemberId);
		filterChain.doFilter(request, response);
	}
}
