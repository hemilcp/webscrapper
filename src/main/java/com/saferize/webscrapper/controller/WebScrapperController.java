package com.saferize.webscrapper.controller;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
	
	
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt(WikiLink uri) throws IOException {
		
		WebScrapperUtil util = new WebScrapperUtil();
		util.openTestSite(uri.getLink());
		WikiLink wikiLink = new WikiLink();
		wikiLink.setLink(util.getText());
//		path = util.getText();
		
//		System.out.println("path response : "+path);
		
        return Response.status(Status.OK).entity(wikiLink).build();
    }
}
