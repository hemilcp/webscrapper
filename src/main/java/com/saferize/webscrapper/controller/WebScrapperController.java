package com.saferize.webscrapper.controller;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import DAO.DBUtil;
import util.WebScrapperUtil;
import util.WikiLink;

/**
 * Root resource (exposed at "webscrapper" path)
 */
@Path("/webscrapper")
public class WebScrapperController {

	/**
	 * Method handling HTTP GET requests. The returned object will be sent
	 * to the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 * @throws IOException 
	 */

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResponse(){
		return Response.status(Status.OK).entity("HelloWorld").build();
	}


	/**
	 * Method POST handles the new wikiLink post requests. It consumes a Java Object 
	 * WikiLink and produces a Json response. 
	 * 
	 * @param uri
	 * @return
	 * @throws IOException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIt(WikiLink uri) throws IOException {
		//		System.out.println(uri.getLink());
		WebScrapperUtil util = new WebScrapperUtil();
		util.openTestSite(uri.getLink());
		List<String> path = util.getText();

		int hops = path.size();
		String json = new Gson().toJson(path);

		DBUtil dbutil = new DBUtil();
		dbutil.insertData(uri.getLink(), json);

		return Response.status(Status.OK).entity(json).build();
	}
}
