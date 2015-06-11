package com.idarko.streamapp.model;

import java.util.List;

public class StreamCategory {
	private String name;
	private List<StreamSection> streamSections;
	private Boolean isSectionsSortedAlphabetically;
	
	public Boolean getIsSectionsSortedAlphabetically() {
		return isSectionsSortedAlphabetically;
	}
	
	public void setIsSectionsSortedAlphabetically(
			Boolean isSectionsSortedAlphabetically) {
		this.isSectionsSortedAlphabetically = isSectionsSortedAlphabetically;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public List<StreamSection> getStreamSections() {
		return streamSections;
	}

	public void setStreamSections(List<StreamSection> streamSections) {
		this.streamSections = streamSections;
	}
}
