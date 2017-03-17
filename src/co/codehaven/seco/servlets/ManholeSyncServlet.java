package co.codehaven.seco.servlets;

import java.io.IOException;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import co.codehaven.seco.datastore.DatastoreManhole;
import co.codehaven.seco.entities.Manhole;
import co.codehaven.seco.exceptions.ManholeNotFoundException;
import co.codehaven.seco.resources.RequestDictionary;

@SuppressWarnings("serial")
public class ManholeSyncServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		Manhole manhole = new Manhole();
		
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String json = "{}";

		try {

			Integer id = Integer.parseInt(req.getParameter(RequestDictionary.PARAM_ID));
			if (id > 0)
			{
				Integer gasState = Integer.parseInt(req.getParameter(RequestDictionary.PARAM_GASSTATE));
				Double currentHeight = Double.parseDouble(req.getParameter(RequestDictionary.PARAM_CURRENTHEIGHT));
				
				//manhole.setId(id);
				manhole = DatastoreManhole.getInstance().search(id);
				
				manhole.setGasState(gasState);
				manhole.setCurrentHeight(currentHeight);
				manhole.setLastUpdated(System.currentTimeMillis()/1000);
				
				DatastoreManhole.getInstance().update(manhole);
				manhole.recalculate();
				
				json = gson.toJson(manhole);
			}
			
		} catch (ManholeNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		resp.setContentType("text/plain");
		resp.getWriter().println(json);
	}
}
