package com.example.app;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.wildfly.security.auth.server.SecurityDomain;
import org.wildfly.security.auth.server.SecurityIdentity;
import org.wildfly.security.authz.Attributes;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.Set;
/**
 * A simple secured HTTP servlet. It returns the user name and
 * attributes obtained from the logged-in user's Principal. If
 * there is no logged-in user, it returns the text
 * "NO AUTHENTICATED USER".
 */

@WebServlet("/secured")
public class SecuredServlet extends HttpServlet {

	@EJB
	private StatelessEJBLocal statelessEJB;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try (PrintWriter writer = resp.getWriter()) {

			Principal user = req.getUserPrincipal();
			SecurityIdentity identity = SecurityDomain.getCurrent().getCurrentSecurityIdentity();
			Attributes identityAttributes = identity.getAttributes();
			Set <String> keys = identityAttributes.keySet();
			String attributes = "<ul>";

			for (String attr : keys) {
				attributes += "<li> " +  attr + " : " + identityAttributes.get(attr).toString() + "</li>";
			}

			attributes+="</ul>";
			writer.println("<html>");
			writer.println("  <head><title>Secured Servlet</title></head>");
			writer.println("  <body>");
			writer.println("    <h1>Secured Servlet</h1>");
			writer.println("    <p>");
			writer.print(" Current Principal '");
			writer.print(user != null ? user.getName() : "NO AUTHENTICATED USER");
			writer.print("'");
			writer.print(user != null ? "\n" + attributes : "");
			writer.println("    </p>");
			if (statelessEJB != null) {
				writer.print("<p>statelessEJB: " + statelessEJB.getSecurityInformation() + "</p>");
			}
			writer.println("  </body>");
			writer.println("</html>");
		}
	}

}
