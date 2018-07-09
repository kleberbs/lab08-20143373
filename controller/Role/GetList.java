package controller.Role;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PMF;
import model.Role;


	@SuppressWarnings("serial")
	public class GetList extends HttpServlet{
		
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(Role.class);
			@SuppressWarnings("unchecked")
			List<Role> roles = (List<Role>)query.execute("select from Role");
			
			req.setAttribute("roles", roles);
			try {
				req.getRequestDispatcher("/WEB-INF/Role/list.jsp").forward(req, resp);
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

	