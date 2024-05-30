package com.example.demo.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
@Table(name= "Retrospective")
public class Retrospective {
	
	@Id
	@JsonProperty("Name")
	@Column(name="NAME")
	private String name;
	
	@JsonProperty("Summary")
	@Column(name = "SUMMARY")
	private String summary;
	
	
	@NotNull
	@JsonProperty("Date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "DATE")
	private LocalDate date;
	
	@ElementCollection
	@JsonProperty("Participants")
	@CollectionTable(name="PARTICIPANTS", joinColumns = @JoinColumn(name="NAME"))
	@Column(name = "PARTICIPANTS")
	private Set<String> participants;
	
	@JsonIgnoreProperties("key")
	@JsonProperty("Feedback")
	@ElementCollection(fetch=FetchType.EAGER)
    @MapKeyColumn(name="feedbackKey",insertable = false,updatable = false,unique = true)
    @CollectionTable(name="FeedBackMap", joinColumns= @JoinColumn(name="NAME"))
    private Map<String, FeedBack> feedbacks ;
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Set<String> getParticipants() {
		return participants;
	}

	public void setParticipants(Set<String> participants) {
		this.participants = participants;
	}

	public Map<String, FeedBack> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(Map<String, FeedBack> feedbacks) {
		this.feedbacks = feedbacks;
	}

	@Override
	public String toString() {
		return "Retrospective [name=" + name + ", summary=" + summary + ", date=" + date + ", participants="
				+ participants + ", feedbacks=" + feedbacks + "]";
	}
	
	
}
