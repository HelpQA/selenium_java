package com.rest;

import org.springframework.util.MultiValueMap;

public class HttpResponse {

	private MultiValueMap<String, String> headers;

	private String body;

	private int status;

	public HttpResponse() {
	}

	public HttpResponse(String body, MultiValueMap<String, String> headers, int status) {
		this.body = body;
		this.headers = headers;
		this.status = status;
	}

	public MultiValueMap<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(MultiValueMap<String, String> headers) {
		this.headers = headers;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getStatusCode() {
		return status;
	}

	public void setStatusCode(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "HttpResponse [headers=" + headers + ", body=" + body + ", status=" + status + "]";
	}

}
