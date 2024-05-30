/**
 * 
 */
package com.example.demo.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * 
 */
public class CreateRetroModel {

	String name;
	String summary;
	LocalDate date;
	Set<String> participants;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}
	/**
	 * @return the participants
	 */
	public Set<String> getParticipants() {
		return participants;
	}
	/**
	 * @param participants the participants to set
	 */
	public void setParticipants(Set<String> participants) {
		this.participants = participants;
	}
	@Override
	public int hashCode() {
		return Objects.hash(date, name, participants, summary);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreateRetroModel other = (CreateRetroModel) obj;
		return Objects.equals(date, other.date) && Objects.equals(name, other.name)
				&& Objects.equals(participants, other.participants) && Objects.equals(summary, other.summary);
	}
	@Override
	public String toString() {
		return "CreateRetroModel [name=" + name + ", summary=" + summary + ", date=" + date + ", participants="
				+ participants + "]";
	}
	
	
}
