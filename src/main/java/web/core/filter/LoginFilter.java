package web.core.filter;

import web.beans.login.UserInfoBean;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sam
 * <p>
 * Filter used for interception URL requests and redirecting to the login page
 * if needed.
 * </P>
 */
@WebFilter(urlPatterns = {"/pages/*", "/AdministrationSystem/pages/*"})
public class LoginFilter implements Filter {

    @Inject
    private UserInfoBean userInfoBean;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //No implementation needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (userInfoBean == null || userInfoBean.getLoggedInUser() == null) {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;

            String requestURI = req.getRequestURI();
            String contextPath = req.getContextPath();

            StringBuilder sb = new StringBuilder();
            sb.append(requestURI);

            Map<String, String[]> map = req.getParameterMap();
            if (!map.isEmpty()) {
                Map.Entry<String, String[]> entry = map.entrySet().iterator().next();

                sb.append("?");
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue()[0]);
            }

            res.sendRedirect(contextPath + "/login.xhtml?to=" + URLEncoder.encode(sb.toString(), "UTF-8"));
//            res.sendRedirect(contextPath + "/pages/profile/login.xhtml");
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        //No implementation needed
    }

}

