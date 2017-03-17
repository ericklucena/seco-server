package co.codehaven.seco.servlets;

import java.io.IOException;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import co.codehaven.seco.datastore.DatastoreManhole;
import co.codehaven.seco.entities.Manhole;
import co.codehaven.seco.exceptions.ManholeAlreadyInsertedException;
import co.codehaven.seco.resources.RequestDictionary;

@SuppressWarnings("serial")
public class ManholeInsertServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		Manhole manhole = new Manhole();
		
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String json = "{}";
		
		try {
			Integer id = Integer.parseInt(req.getParameter(RequestDictionary.PARAM_ID));
			String name = req.getParameter(RequestDictionary.PARAM_NAME);
			String street = req.getParameter(RequestDictionary.PARAM_STREET);
			Double lat = Double.parseDouble(req.getParameter(RequestDictionary.PARAM_LATITUDE));
			Double lng = Double.parseDouble(req.getParameter(RequestDictionary.PARAM_LONGITUDE));
			String[] dimensions = req.getParameter(RequestDictionary.PARAM_DIMENSIONS).split(":");
			
			manhole.setDimensionX(Double.parseDouble(dimensions[0]));
			manhole.setDimensionY(Double.parseDouble(dimensions[1]));
			manhole.setDimensionZ(Double.parseDouble(dimensions[2]));

			manhole.setId(id);
			manhole.setName(name);
			manhole.setStreet(street);
			manhole.setLatitude(lat);
			manhole.setLongitude(lng);
			manhole.setLastManteinance(System.currentTimeMillis()/1000);
			manhole.setLastUpdated(System.currentTimeMillis()/1000);

			DatastoreManhole.getInstance().insert(manhole);
			
			json = gson.toJson(manhole);
			
		} catch (ManholeAlreadyInsertedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		resp.setContentType("text/plain");
		resp.getWriter().println(json);
	}
}
