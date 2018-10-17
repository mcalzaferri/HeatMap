package mcalzaferri.heatmap.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
	    name = "Datapool",
	    urlPatterns = {"/datapool"}
	)

public class DataPoolServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4406189919961725742L;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		StringBuilder sb = new StringBuilder();
		while(reader.ready()) {
			sb.append("\r\n" +reader.readLine());
		}
		response.getWriter().write(sb.toString().toUpperCase());
	}
}
