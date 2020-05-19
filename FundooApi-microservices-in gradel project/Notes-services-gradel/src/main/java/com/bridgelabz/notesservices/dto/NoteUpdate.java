package com.bridgelabz.notesservices.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NoteUpdate {

	private String title;

	private String description;

	private LocalDateTime updateDate;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

}
