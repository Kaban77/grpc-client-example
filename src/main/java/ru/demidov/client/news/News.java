package ru.demidov.client.news;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

public class News {

	private int id;
	private String caption;
	private String body;
	private LocalDateTime publicationDate;
	private String category;

	public News() {

	}

	public News(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.caption = rs.getString("caption");
		this.body = rs.getString("body");

		var publicationDate = rs.getTimestamp("publication_date");
		if (publicationDate != null) {
			this.publicationDate = publicationDate.toLocalDateTime();
		}
		this.category = rs.getString("category");
	}

	public News(JSONObject json) {
		if(json!=null) {
			id = json.optInt("id");
			caption = json.optString("caption");
			body = json.optString("body");
			category = json.optString("category");

			if (json.optString("publicationDate", null) != null) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				publicationDate = LocalDateTime.parse(json.optString("publicationDate"), formatter);
			}
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public LocalDateTime getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(LocalDateTime publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", caption=" + caption + ", body=" + body + ", publicationDate=" + publicationDate + ", category="
				+ category + "]";
	}

	public String toJsonApi() {
		var json = new JSONObject();
		json.put("id", id);
		json.put("caption", caption);
		json.put("body", body);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		json.put("publicationDate", publicationDate.format(formatter));

		return json.toString();
	}
}
