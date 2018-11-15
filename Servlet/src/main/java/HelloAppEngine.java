import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
    name = "HelloAppEngine",
    urlPatterns = {"/hello"}
)
public class HelloAppEngine extends HttpServlet {

	private static final long serialVersionUID = -326588183982128028L;

@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
	 
	  response.setContentType("text/html");
	  response.setCharacterEncoding("UTF-8");
	  
	  BufferedReader reader = new BufferedReader(new FileReader("index.html"));
	  PrintWriter out = response.getWriter();
	  char[] buf = new char[4 * 1024]; // 4Kchar buffer
      int len;
      while ((len = reader.read(buf, 0, buf.length)) != -1) {
        out.write(buf, 0, len);
      }
      out.flush();
      reader.close();
  }
}