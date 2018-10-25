package mcalzaferri.project.heatmap.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mcalzaferri.project.heatmap.data.TemperatureDataStore;

@WebServlet(
	    name = "Api",
	    urlPatterns = {"/api/*"}
	)

public class ApiServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4406189919961725742L;
	private TemperatureDataStore dataStore;
	
	public ApiServlet() {
		dataStore = TemperatureDataStore.getInstance();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().write(request.getRequestURI().toString());
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		response.getWriter().write("URI = " + request.getRequestURI().toString() + " Query = " + request.getQueryString());
	}
}
