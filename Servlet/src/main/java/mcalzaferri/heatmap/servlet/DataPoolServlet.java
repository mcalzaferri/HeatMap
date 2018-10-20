package mcalzaferri.heatmap.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mcalzaferri.net.http.HttpMessage;
import mcalzaferri.project.heatmap.messages.DataPostMessage;
import mcalzaferri.project.heatmap.messages.DataResponseMessage;
import mcalzaferri.project.heatmap.messages.ErrorResponseMessage;
import mcalzaferri.project.heatmap.messages.IMessageHandler;
import mcalzaferri.project.heatmap.messages.IdRequestMessage;
import mcalzaferri.project.heatmap.messages.IdResponseMessage;

@WebServlet(
	    name = "Datapool",
	    urlPatterns = {"/datapool"}
	)

public class DataPoolServlet extends HttpServlet implements IMessageHandler{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4406189919961725742L;
	private HttpMessage responseMsg;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			StringBuilder sb = new StringBuilder();
			while (reader.ready()) {
				sb.append(reader.readLine());
			}
			responseMsg = new ErrorResponseMessage();
			((ErrorResponseMessage)responseMsg).setErrorDescription("Message not supported");
			IMessageHandler.handleMessage(sb.toString(), this);
		}catch(Exception e) {
			responseMsg = new ErrorResponseMessage();
			((ErrorResponseMessage)responseMsg).setErrorDescription(e.toString());
		}
		response.getWriter().write(responseMsg.encode());
	}

	@Override
	public void handleMessage(DataPostMessage msg) {
		responseMsg = new DataResponseMessage();
	}

	@Override
	public void handleMessage(DataResponseMessage msg) {
	}

	@Override
	public void handleMessage(IdRequestMessage msg) {
		responseMsg = new IdResponseMessage();
		((IdResponseMessage)responseMsg).setId("sensor123456789");
	}

	@Override
	public void handleMessage(IdResponseMessage msg) {

	}
	@Override
	public void handleMessage(ErrorResponseMessage msg) {
		
	}
}
