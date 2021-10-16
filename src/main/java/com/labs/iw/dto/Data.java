
package com.labs.iw.dto;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
	@JsonProperty("creationDate")
	private String creationDate;
	@JsonProperty("uuid")
	private String uuid;
	@JsonProperty("firstName")
	private String firstName;
	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("uuid")
	public String getUuid() {
		return uuid;
	}

	@JsonProperty("uuid")
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@JsonProperty("firstName")
	public String getFirstName() {
		return firstName;
	}

	@JsonProperty("firstName")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@JsonProperty("lastName")
	public String getLastName() {
		return lastName;
	}

	@JsonProperty("lastName")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

   @JsonProperty("creationDate")
   public String getCreationDate() {
       return creationDate;
   }

   @JsonProperty("creationDate")
   public void setCreationDate(String creationDate) {
       this.creationDate = creationDate;
   }

   
   @Override
   public String toString() {
   	String data = getFirstName() + " " + getLastName();
   	return data;
   }
}
