package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Embeddable;

@Embeddable
public class FeedBack {

	@JsonIgnore
	private String feedbackKey;
	
	@JsonProperty("Name")
	private String participantName;
	
	@JsonProperty("Body")
	private String body;
	
	@JsonProperty("FeedbackType")
	private FeedBackTypeEnum feedBackType;
	
	public FeedBack() {}
	
	public FeedBack(String key, String participantName, String body, FeedBackTypeEnum feedBackType) {
		super();
		this.feedbackKey = key;
		this.participantName = participantName;
		this.body = body;
		this.feedBackType = feedBackType;
	}
	
	/**
	 * @return the feedbackKey
	 */
	public String getFeedbackKey() {
		return feedbackKey;
	}

	/**
	 * @param feedbackKey the feedbackKey to set
	 */
	public void setFeedbackKey(String feedbackKey) {
		this.feedbackKey = feedbackKey;
	}

	/**
	 * @return the participantName
	 */
	public String getParticipantName() {
		return participantName;
	}

	/**
	 * @param participantName the participantName to set
	 */
	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}

	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public FeedBackTypeEnum getFeedBackType() {
		return feedBackType;
	}
	public void setFeedBackType(FeedBackTypeEnum feedBackType) {
		this.feedBackType = feedBackType;
	}

	@Override
	public String toString() {
		return "FeedBack [feedbackKey=" + feedbackKey + ", participantName=" + participantName + ", body="
				+ body + ", feedBackType=" + feedBackType + "]";
	} 
	
	
}
