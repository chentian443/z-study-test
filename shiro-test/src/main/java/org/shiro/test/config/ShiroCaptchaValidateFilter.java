package org.shiro.test.config;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ShiroCaptchaValidateFilter extends AccessControlFilter{

	@Value("${cn.unicom.upf.captcha.enabled}")
	private boolean captchaEbabled;
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        if ( !captchaEbabled || !"post".equalsIgnoreCase(httpServletRequest.getMethod())) {
            return true;
        }
        HttpSession session = httpServletRequest.getSession();
        String code = (String)session.getAttribute("_valid_captcha");
        String inputCode = httpServletRequest.getParameter("captcha");
        boolean codeOk = null != code && code.equalsIgnoreCase(inputCode);
		return codeOk;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		
		request.setAttribute("_name", "校验码错误");
		request.setAttribute("message", "校验码错误");
		return true;
	}
	
}
