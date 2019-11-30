package com.timwi.challenges.rest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.timwi.challenges.algorithms.BackToBasics;
import com.timwi.challenges.algorithms.ReadingDigits;

@Path("api/")
public class RestController {
	
	@GET
	@Path("lookandsay/{input}")
	@Produces( MediaType.APPLICATION_JSON )
	public Response challenge1 ( @PathParam(value = "input") String input, @QueryParam(value = "times") Integer times ) {
		String result = ReadingDigits.answer( input, times );
		return Response.status(200).entity( new Object () { public String next_result = result; } ).build();
	}

	@POST
	@Path("assembly/")
	@Produces( MediaType.APPLICATION_JSON )
	public Response challenge2 ( @QueryParam( value = "wire" ) String wire ) {
		
		// Parses the file.
		List<String> circuit = new ArrayList<String>();
		
		try {
			String line = null;
			BufferedReader br = new BufferedReader( new FileReader( getClass().getClassLoader().getResource("assembly-instructions.txt").getFile() ) );
	        while ( ( line = br.readLine() ) != null ) { circuit.add( line ); }  
	        br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String, Integer> wires = BackToBasics.answer(circuit, wire);
		return Response.status(200).entity( new HashMap<> () {{ put( "wires", wires );}} ).build();
	}

}
