package org.shiro.test.config;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.stereotype.Component;

@Component
public class ShiroSessionFilter extends AccessControlFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = super.getSubject(request, response);
		if (!subject.isAuthenticated()) {
			// 没有登录
			
			return false;
		}

		return true;
	}
}
