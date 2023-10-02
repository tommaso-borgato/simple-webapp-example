package com.example.app;

import jakarta.ejb.Local;

@Local
public interface StatelessEJBLocal {
	public String getSecurityInformation();
}
