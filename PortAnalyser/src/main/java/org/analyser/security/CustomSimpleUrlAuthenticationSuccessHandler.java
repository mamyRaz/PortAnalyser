package org.analyser.security;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.analyser.entities.AppUser;
import org.analyser.entities.SessionInfo;
import org.analyser.services.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomSimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	@Autowired
	private IAccountService accountService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		handle(request, response, authentication);
	}

	protected void handle(HttpServletRequest request, 
		      HttpServletResponse response, Authentication authentication)
		      throws IOException {
		
		String targetUrl = determineTargetUrl(authentication);
		
		if (response.isCommitted()) {
            System.out.println(
              "Response has already been committed. Unable to redirect to "
              + targetUrl);
            return;
        }
 
        redirectStrategy.sendRedirect(request, response, targetUrl);
	}
	
	protected String determineTargetUrl(Authentication authentication) {
        boolean isUser = false;
        boolean isAdmin = false;
        
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("USER")) {
            	isUser = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ADMIN")) {
            	//System.out.println("authentication.getName() :"+ authentication.getName());
                isAdmin = true;
                break;
            }
        }
        AppUser user = accountService.findUserByUsername(authentication.getName());
        if(user == null) {
    		return "redirect:/login";
    	}
        
        String id = String.valueOf(user.getId());
        
        if (isUser) {
        	accountService.saveSessionInfo(new SessionInfo(user, new Date()));
            return "/user?id="+id;
        } else if (isAdmin) {
        	accountService.saveSessionInfo(new SessionInfo(user, new Date()));
            return "/admin?id="+id;
        } else {
            throw new IllegalStateException();
        }
    }
}
