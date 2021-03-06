package com.airlines.flyaway.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import org.hibernate.internal.build.AllowSysOut;

import com.airlines.flyaway.constants.FlyawayConstants;
import com.airlines.flyaway.domain.City;
import com.airlines.flyaway.domain.Response;
import com.airlines.flyaway.services.CitiesService;
import com.airlines.flyaway.services.impl.CitiesServiceImpl;

public class CitiesController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8065571988232515805L;

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CitiesService citiesService = new CitiesServiceImpl();
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/cities.jsp");
		Response respo = citiesService.getCity(null);
		if (respo != null) {
			if (respo.getStatus().equals(FlyawayConstants.SUCCESS)) {
				request.setAttribute("cities", (List<City>) respo.getData());
			} else {
				request.setAttribute("errorMessage", respo.getErrorMessage());
			}
		} else {
			request.setAttribute("errorMessage"," List not found");
		}
		requestDispatcher.forward(request, response);
	}
	
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CitiesService citiesService = new CitiesServiceImpl();
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/cities.jsp");
		City city = new City();
		city.setCityName(request.getParameter("cityName"));
		Response respo = citiesService.insertCity(city);
		if (respo != null) {
			if (respo.getStatus().equals(FlyawayConstants.SUCCESS)) {
				request.setAttribute("successMessage", "Sucessfully Inserted");
			} else {
				request.setAttribute("errorMessage", respo.getErrorMessage());
			}
		} else {
			request.setAttribute("errorMessage","Unable to save city");
		}
		doGet(request, response);
		
	}

}
