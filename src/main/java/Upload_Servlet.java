import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "upload", urlPatterns = {"/upload/*"})
@MultipartConfig

public class Upload_Servlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	int BUFFER_LENGTH = 4096;
       
    public Upload_Servlet() {
    	
        super();
    }

	private String getFileName(Part part) {

		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    String filePath = request.getRequestURI();
	    
	    File file = new File(System.getenv("OPENSHIFT_DATA_DIR") + filePath.replace("/upload/", ""));
	    InputStream input = new FileInputStream(file);
	 
	    response.setContentLength((int) file.length());
	    response.setContentType(new MimetypesFileTypeMap().getContentType(file));
	 
	    OutputStream output = response.getOutputStream();
	    byte[] bytes = new byte[BUFFER_LENGTH];
	    int read = 0;
	    while ((read = input.read(bytes, 0, BUFFER_LENGTH)) != -1) {
	        output.write(bytes, 0, read);
	        output.flush();
	    }
	 
	    input.close();
	    output.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    for (Part part : request.getParts()) {
	        InputStream is = request.getPart(part.getName()).getInputStream();
	        String fileName = getFileName(part);
	        FileOutputStream os = new FileOutputStream(System.getenv("OPENSHIFT_DATA_DIR") + fileName);
	        byte[] bytes = new byte[BUFFER_LENGTH];
	        int read = 0;
	        while ((read = is.read(bytes, 0, BUFFER_LENGTH)) != -1) {
	            os.write(bytes, 0, read);
	        }
	        os.flush();
	        is.close();
	        os.close();
	    }
	}
}
