package co.codehaven.seco.servlets;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import co.codehaven.seco.datastore.DatastoreManhole;
import co.codehaven.seco.entities.Manhole;
import co.codehaven.seco.exceptions.ManholeNotFoundException;
import co.codehaven.seco.resources.RequestDictionary;

@SuppressWarnings("serial")
public class ManholeGetServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		Manhole manhole = new Manhole();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String json = "{}";
		String idString = req.getParameter(RequestDictionary.PARAM_ID);

		try {
			List<Manhole> manholes;

			if (idString != null) {
				Integer id = Integer.parseInt(idString);

				manhole = DatastoreManhole.getInstance().search(id);
				manhole.recalculate();
				manholes = new LinkedList<Manhole>();
				manholes.add(manhole);
			} else {
				manholes = DatastoreManhole.getInstance().getAll();
			}

			json = gson.toJson(manholes);

		} catch (ManholeNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		resp.setContentType("text/plain");
		resp.getWriter().println(json);
	}
}
