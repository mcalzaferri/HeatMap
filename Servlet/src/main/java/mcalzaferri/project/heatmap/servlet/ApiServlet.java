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
import mcalzaferri.project.heatmap.data.HeatmapDatastoreToolkit;
import mcalzaferri.project.heatmap.data.config.FieldNotFoundException;
import mcalzaferri.project.heatmap.data.config.RessourceNotFoundException;
import mcalzaferri.project.heatmap.data.config.VerificationException;

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

	private HeatmapDatastoreToolkit datastoreToolkit;
	
	public ApiServlet() throws IOException {
		datastoreToolkit = HeatmapDatastoreToolkit.getDefaultInstance("datastoreConfiguration.json");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setAccessControlHeaders(response);
		String requestContent = getRequestContent(request);
		try {
			JsonObject createdJsonObject = datastoreToolkit.storeJson(request.getRequestURI(), requestContent);
			setResponseContent(response, createdJsonObject.toString(), HttpServletResponse.SC_OK);
		}catch(RessourceNotFoundException rnfe) {
			setResponseContent(response, rnfe.getMessage(), HttpServletResponse.SC_NOT_FOUND);
		} catch (VerificationException ve) {
			setResponseContent(response, ve.getMessage(), HttpServletResponse.SC_FORBIDDEN);
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setAccessControlHeaders(response);
		try {
			String responseString = getResponseContent(request, response);
			setResponseContent(response, responseString, HttpServletResponse.SC_OK);
		}catch(FieldNotFoundException fnfe) {
			setResponseContent(response, fnfe.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
		} catch (RessourceNotFoundException rnfe) {
			setResponseContent(response, rnfe.getMessage(), HttpServletResponse.SC_NOT_FOUND);
		}
	}
	
	private String getRequestContent(HttpServletRequest request) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		while(reader.ready()) {
			sb.append(reader.readLine());
		}
		return sb.toString();
	}
	
	private String getResponseContent(HttpServletRequest request, HttpServletResponse response) throws FieldNotFoundException, RessourceNotFoundException {
		if(request.getRequestURI().contains(".")) {
			setAttachmentHeaders(request, response);
		}
		if(request.getRequestURI().endsWith(".csv")) {
			return doGetAsCsv(request, response);
		}else {
			return doGetAsJson(request, response);
		}
	}
	
	private void setResponseContent(HttpServletResponse response, String content, int status) throws IOException {
		response.setStatus(status);
		response.getWriter().write(content);
	}
	
	private void setAttachmentHeaders(HttpServletRequest request, HttpServletResponse response) {
		String requestedRes = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
		response.setHeader("Content-disposition","attachment; filename="+ requestedRes);
	}
	
	private String doGetAsJson(HttpServletRequest request, HttpServletResponse response) throws FieldNotFoundException, RessourceNotFoundException{
		response.setContentType ("application/json");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(datastoreToolkit.getJson(getRessourceURI(request), request.getQueryString()));
	}
	
	private String doGetAsCsv(HttpServletRequest request, HttpServletResponse response) throws FieldNotFoundException, RessourceNotFoundException{
		response.setContentType ("text/csv");
		JsonToCsvConverter csvConverter = new JsonToCsvConverter();
		return csvConverter.toCsv(datastoreToolkit.getJson(getRessourceURI(request), request.getQueryString()));
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
	}
}
