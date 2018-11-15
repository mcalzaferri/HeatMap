package mcalzaferri.project.heatmap.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.search.StatusCode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import mcalzaferri.json.JsonToCsvConverter;
import mcalzaferri.net.rest.QueryStringDecoder;
import mcalzaferri.project.heatmap.data.HeatmapDatastore;

@WebServlet(
	    name = "Api",
	    urlPatterns = {"/api/*"}
	)
@SuppressWarnings("unused")
public class ApiServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4406189919961725742L;

	private HeatmapDatastore datastore;
	
	public ApiServlet() throws IOException {
		datastore = HeatmapDatastore.getDefaultInstance("datastoreConfiguration.json");
	}
	
	  //for Preflight
	/*
	  @Override
	  protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	      setAccessControlHeaders(resp);
	  }
	  */
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder sb = new StringBuilder();
		setAccessControlHeaders(response);
		BufferedReader reader = request.getReader();
		while(reader.ready()) {
			sb.append(reader.readLine());
		}
		String requestContent = sb.toString();
		sb = new StringBuilder();
		try {
			JsonObject createdJsonObject = datastore.storeJson(request.getRequestURI(), requestContent);
			response.getWriter().write(createdJsonObject.toString());
		}catch(Exception e) {
			e.printStackTrace(response.getWriter());
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String responseString;
		setAccessControlHeaders(response);
		try {
			if(request.getRequestURI().contains(".")) {
				String requestedRes = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
				response.setHeader("Content-disposition","attachment; filename="+ requestedRes);
			}
			if(request.getRequestURI().endsWith(".csv")) {
				responseString = doGetAsCsv(request, response);
			}else {
				responseString = doGetAsJson(request, response);
			}
			response.getWriter().write(responseString);
		}catch(Exception e) {
			e.printStackTrace(response.getWriter());
		}
	}
	
	private String doGetAsJson(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType ("application/json");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(datastore.getJson(getRessourceURI(request), request.getQueryString()));
	}
	
	private String doGetAsCsv(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType ("text/csv");
		JsonToCsvConverter csvConverter = new JsonToCsvConverter();
		return csvConverter.toCsv(datastore.getJson(getRessourceURI(request), request.getQueryString()));
	}
	
	private String getRessourceURI(HttpServletRequest request) {
		if(request.getRequestURI().contains(".")) {
			return request.getRequestURI().substring(0, request.getRequestURI().lastIndexOf("."));
		}else {
			return request.getRequestURI();
		}
	}
	
	  private void setAccessControlHeaders(HttpServletResponse resp) {
	      resp.setHeader("Access-Control-Allow-Origin", "*");
	      resp.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
	      resp.setHeader("Access-Control-Allow-Credentials", "true");
	      resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
	      resp.setStatus(HttpServletResponse.SC_OK);
	  }
}
