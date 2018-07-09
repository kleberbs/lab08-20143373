package controller.Acces;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Acces;
import model.PMF;


	@SuppressWarnings("serial")
	public class GetList extends HttpServlet{
		
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(Acces.class);
			@SuppressWarnings("unchecked")
			List<Acces> acces = (List<Acces>)query.execute("select from Acces");
			
			req.setAttribute("acces", acces);
			try {
				req.getRequestDispatcher("/WEB-INF/Acces/list.jsp").forward(req, resp);
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

	