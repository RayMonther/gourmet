package com.cakegourmet.service;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

public interface IReportService {
	StringBuffer generateReport() throws NoSuchAlgorithmException, ParseException;
}
