package in.com.rays.ORSprojectt4.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.com.rays.ORSprojectt4.utility.ServletUtility;

/**
 * @author yashita  
 *
 */
@WebFilter(filterName = "FrontCtl", urlPatterns = {"/ctl/*", "/doc/*"})
public class FrontController implements Filter{

	
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("front ctl init");
		// TODO Auto-generated method stub
		
	}

	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("front ctl dofilter");

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		HttpSession session = request.getSession();
		System.out.println("front ctl session");

		if (session.getAttribute("user") == null) {
			
			request.setAttribute("fcmessage", " Your Session has been Expired... Please Login Again");

			// Set the URI
			String str = request.getRequestURI();
			session.setAttribute("uri", str);

			ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);
		} else {
			chain.doFilter(req, resp);
		}
		
	}

	public void destroy() {
		System.out.println("front ctl destroy");
		// TODO Auto-generated method stub
		
	}

}
