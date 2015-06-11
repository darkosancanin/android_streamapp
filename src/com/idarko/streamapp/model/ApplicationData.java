package com.idarko.streamapp.model;

import java.util.List;

public class ApplicationData {
	private Integer version;
	private List<StreamCategory> streamCategories;
	private List<HashTagsCategory> hashtagCategories;
	
	public Integer getVersion() {
		return version;
	}
	
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public List<StreamCategory> getStreamCategories() {
		return streamCategories;
	}
	
	public void setStreamCategories(List<StreamCategory> streamCategories) {
		this.streamCategories = streamCategories;
	}

	public List<HashTagsCategory> getHashtagCategories() {
		return hashtagCategories;
	}

	public void setHashtagCategories(List<HashTagsCategory> hashtagCategories) {
		this.hashtagCategories = hashtagCategories;
	}
}
