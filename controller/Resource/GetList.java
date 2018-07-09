package controller.Resource;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PMF;
import model.Resource;

	@SuppressWarnings("serial")
	public class GetList extends HttpServlet{
		
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(Resource.class);
			@SuppressWarnings("unchecked")
			List<Resource> resources = (List<Resource>)query.execute("select from Resource");
			
			req.setAttribute("resources", resources);
			try {
				req.getRequestDispatcher("/WEB-INF/Resource/list.jsp").forward(req, resp);
				query.closeAll();
			
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				pm.close();
			}
		}
	}

	