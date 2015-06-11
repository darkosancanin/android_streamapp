package com.idarko.streamapp.model;

import java.util.List;

public class HashTagCollection {
	private String name;
	private List<String> hashtags;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getHashTags() {
		return hashtags;
	}

	public void setHashTags(List<String> hashTags) {
		this.hashtags = hashTags;
	}
	
	@Override
	public String toString(){
		return name;
	}
}
