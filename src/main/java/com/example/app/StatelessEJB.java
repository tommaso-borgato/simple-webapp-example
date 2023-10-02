package com.example.app;

import jakarta.annotation.Resource;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;

@Stateless
@DeclareRoles({"user"})
public class StatelessEJB implements StatelessEJBLocal {

	@Resource
	private SessionContext context;

	@RolesAllowed("user")
	public String getSecurityInformation() {
		StringBuilder sb = new StringBuilder("[");
		if (context != null && context.getCallerPrincipal() != null) {
			sb.append("Principal=[").append(context.getCallerPrincipal().getName()).append("]");
		} else if (context != null && context.getCallerPrincipal() == null) {
			sb.append("Principal=[context OK but getCallerPrincipal == null]");
		} else {
			sb.append("Principal=[context null]");
		}
		return sb.toString();
	}
}
