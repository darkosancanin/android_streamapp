package com.idarko.streamapp.model;

import java.util.List;

public class HashTagsCategory {
	private String name;
	private Boolean isSectionsSortedAlphabetically;
	private List<HashTagsSection> hashtagSections;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean getIsSectionsSortedAlphabetically() {
		return isSectionsSortedAlphabetically;
	}
	
	public void setIsSectionsSortedAlphabetically(
			Boolean isSectionsSortedAlphabetically) {
		this.isSectionsSortedAlphabetically = isSectionsSortedAlphabetically;
	}

	public List<HashTagsSection> getHashTagSections() {
		return hashtagSections;
	}

	public void setHashTagSections(List<HashTagsSection> hashTagSections) {
		this.hashtagSections = hashTagSections;
	}
}
