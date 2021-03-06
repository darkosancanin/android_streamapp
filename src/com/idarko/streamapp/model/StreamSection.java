package com.idarko.streamapp.model;

import java.util.List;

public class StreamSection {
	private String name;
	private List<Stream> streams;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Stream> getStreams() {
		return streams;
	}

	public void setStreams(List<Stream> streams) {
		this.streams = streams;
	}
}
