package com.idarko.streamapp.model;

import java.util.List;

public class HashTagsSection {
	private String name;
	private List<HashTagCollection> hashtagCollections;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<HashTagCollection> getHashTagCollections() {
		return hashtagCollections;
	}

	public void setHashTagCollections(List<HashTagCollection> hashTagCollections) {
		this.hashtagCollections = hashTagCollections;
	}
	
}
