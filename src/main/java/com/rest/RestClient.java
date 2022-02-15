package com.rest;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClient {

	private static final Log LOG = LogFactory.getLog(RestClient.class);

	public HttpResponse doGet(String url, Map<String, String> reqHeaders) {
		return this.exchange(url, reqHeaders, null, HttpMethod.GET);
	}

	public HttpResponse doPost(String url, Map<String, String> reqHeaders, String requestBody) {
		return this.exchange(url, reqHeaders, requestBody, HttpMethod.POST);
	}

	public HttpResponse doPut(String url, Map<String, String> reqHeaders, String requestBody) {
		return this.exchange(url, reqHeaders, requestBody, HttpMethod.PUT);
	}

	private HttpResponse exchange(String url, Map<String, String> reqHeaders, String requestBody,
			HttpMethod httpMethod) {
		HttpResponse httpResponse = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			if (!CollectionUtils.isEmpty(reqHeaders)) {
				headers.setAll(reqHeaders);
			}
			HttpEntity<String> requestEntity = requestBody == null ? new HttpEntity<String>(headers)
					: new HttpEntity<String>(requestBody, headers);
			ResponseEntity<String> response = restTemplate.exchange(url, httpMethod, requestEntity, String.class);
			httpResponse = new HttpResponse(response.getBody(), response.getHeaders(),
					response.getStatusCode().value());
			LOG.info("Http response : " + httpResponse);
		} catch (RestClientException restClientException) {
			if (restClientException instanceof HttpClientErrorException) {
				HttpClientErrorException clientErrorException = (HttpClientErrorException) restClientException;
				httpResponse = new HttpResponse(clientErrorException.getResponseBodyAsString(),
						clientErrorException.getResponseHeaders(), clientErrorException.getStatusCode().value());
				LOG.info("Http response : " + httpResponse);
			} else {
				LOG.error("Getting RestClient Exception. Exception : ", restClientException);
				throw new RestClientException("Failed in exchange method ", restClientException);
			}
		} catch (Exception e) {
			LOG.error("Getting Exception. Exception : ", e);
			throw new RestClientException("Failed in exchange method  ", e);
		}
		return httpResponse;

	}
}
