package controller.Resource;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import model.Acces;
import model.PMF;
import model.Resource;
import model.UserX;


@SuppressWarnings("serial")
public class View extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		UserService us = UserServiceFactory.getUserService();
		User user = us.getCurrentUser();
		 if(user == null){		
			 RequestDispatcher rd=req.getRequestDispatcher("/WEB-INF/AccesDenied/accesDenied.jsp");
			 try {
				rd.forward(req, resp);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
		 else {
			 if(us.isUserAdmin() || accesResource(user.getEmail(),"/viewResource")){
				 final PersistenceManager pm = PMF.get().getPersistenceManager();
					if( req.getParameter("id")!=null ){
						String id = req.getParameter("id");
						Long idLong = Long.parseLong(id);
						
						try{
							Resource resource=pm.getObjectById(Resource.class,idLong);
							req.setAttribute("resource", resource);
							RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/Resource/view.jsp");
							rd.forward(req, resp);
						}catch(Exception e){
								System.out.println(e);
								
						}finally{
							pm.close();
						}			
					}else {		
						resp.getWriter().println("No se encontro foto.");
						resp.sendRedirect("/index.jsp");
					}		

			 }
			 else {
				 RequestDispatcher rd=req.getRequestDispatcher("/WEB-INF/AccesDenied/accesDenied.jsp");
				 try {
					rd.forward(req, resp);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			 }
		 }
		
		
	}
	@SuppressWarnings("unchecked")
	private boolean accesResource(String gmail,String url){

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(UserX.class);
		query.setFilter("email == idParam");
		query.declareParameters("String idParam");

		List<UserX> array = (List<UserX>) query.execute(gmail);

		if(array.size()> 0 ){

			UserX usuario = array.get(0);
			Query query1 = pm.newQuery(Resource.class);
			query1.setFilter("url == idParam");
			query1.declareParameters("String idParam");

			List<Resource> arrayRecurso = (List<Resource>)query1.execute(url);


			if(arrayRecurso.size() > 0 ){

				Resource recurso = arrayRecurso.get(0);

				Long roleId = usuario.getRoleId();
				Long resourceId = recurso.getId();

				Query query2 = pm.newQuery(Acces.class);
				query2.setFilter("roleId == idParam && resourceId == idParam2");
				query2.declareParameters("Long idParam , Long idParam2");				
				
				List<Acces> arrayAcceso = (List<Acces>)query2.execute(roleId,resourceId);

				if(arrayAcceso.size()>0){
					
					pm.close();
					return arrayAcceso.get(0).getStatus();
				}
			}

		}
		pm.close();
		return false;
	}

}
