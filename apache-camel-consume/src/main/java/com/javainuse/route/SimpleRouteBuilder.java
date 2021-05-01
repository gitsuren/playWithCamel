package com.javainuse.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;

import com.javainuse.model.Employee;
import com.javainuse.processor.CreateEmployeeProcessor;
import com.javainuse.processor.MyProcessor;

public class SimpleRouteBuilder extends RouteBuilder {

	JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Employee.class);

	@Override
	public void configure() throws Exception {

		// route for REST GET Call
		from("file:C:/inboxREST?noop=true").setHeader(Exchange.HTTP_METHOD, simple("GET"))
				.to("http://localhost:8080/employee?id=5").process(new MyProcessor());

		// route for REST POST Call
		from("file:C:/inboxPOST?noop=true").process(new CreateEmployeeProcessor()).marshal(jsonDataFormat)
				.setHeader(Exchange.HTTP_METHOD, simple("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json")).to("http://localhost:8080/employee")
				.process(new MyProcessor());
	}

}