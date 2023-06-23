package ru.demidov.client.start;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.demidov.client.grpc.NewsGrpcService;

public class TestGrpcClientApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestGrpcClientApplication.class);

	public static void main(String[] args) throws IOException {
		var news = NewsGrpcService.getNews();
		if (news == null) {
			LOGGER.error("Array is empty");
		} else {
			LOGGER.info("News: " + news);
		}
	}
}
