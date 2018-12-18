import java.io.File;
import java.io.IOException;
import java.util.List;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
 
public class FileManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
  public FileManagement() {
      super();
  }
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		        if(ServletFileUpload.isMultipartContent(request)){
		            try {
		                List <FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
		                for(FileItem item : multiparts){
		                    if(!item.isFormField()){
		                        String name = new File(item.getName()).getName();
                            //FIXME: change path to your path
		                        item.write( new File("path/to/save/uploaded/file" + File.separator + name));
		                    }
		                }
		               //File uploaded successfully
		               request.setAttribute("message", "Success!!!");
		            } catch (Exception ex) {
		               request.setAttribute("message", "Oops: something went wrong " + ex);
		            }         		
		        }else{
		
		            request.setAttribute("message","No File found");
 }
		        request.getRequestDispatcher("/result.jsp").forward(request, response);
		
		    }
 
 
}
