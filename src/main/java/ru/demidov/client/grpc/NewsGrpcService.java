package ru.demidov.client.grpc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.Empty;

import io.grpc.ManagedChannelBuilder;
import ru.demidov.client.news.News;
import ru.demidov.grpc.news.NewsServiceGrpc;

public class NewsGrpcService {

	public static List<News> getNews() {
		var managedChannel = ManagedChannelBuilder.forAddress("localhost", 8084).usePlaintext().build();
		NewsServiceGrpc.NewsServiceBlockingStub stub = NewsServiceGrpc.newBlockingStub(managedChannel);
		var newsResponse = stub.get(Empty.newBuilder().build());
		managedChannel.shutdown();

		var result = new ArrayList<News>();
		var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		for (var newsGrpc : newsResponse.getItemsList()) {
			var news = new News();
			news.setBody(newsGrpc.getBody());
			news.setId(newsGrpc.getId());
			news.setCaption(newsGrpc.getCaption());
			news.setCategory(newsGrpc.getCategory());
			var publicationDayString = newsGrpc.getPublicationDate();

			news.setPublicationDate(LocalDateTime.parse(publicationDayString, formatter));
			result.add(news);
		}
		return result;
	}
}
