package controller.User;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PMF;
import model.UserX;


	@SuppressWarnings("serial")
	public class GetList extends HttpServlet{
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(UserX.class);
			
			@SuppressWarnings("unchecked")
			List<UserX> users = (List<UserX>)q.execute("select from User");
			
			req.setAttribute("users", users);
			try {
				req.getRequestDispatcher("/WEB-INF/User/list.jsp").forward(req, resp);
				q.closeAll();
			
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				pm.close();
			}
			}
		}
	