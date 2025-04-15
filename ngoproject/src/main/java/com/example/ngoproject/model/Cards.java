package com.example.ngoproject.model;

import jakarta.persistence.*;

@Entity
public class Cards {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String title;
	    private String description;
	    private String imageUrl;
	    
	    public Cards(){
	    	
	    }
		public Cards(Long id, String title, String description, String imageUrl) {
			super();
			this.id = id;
			this.title = title;
			this.description = description;
			this.imageUrl = imageUrl;
		}

		public Long getId() {
			return id;
		}

		public String getTitle() {
			return title;
		}

		public String getDescription() {
			return description;
		}

		public String getImageUrl() {
			return imageUrl;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		@Override
		public String toString() {
			return "Cards [id=" + id + ", title=" + title + ", description=" + description + ", imageUrl=" + imageUrl
					+ "]";
		}
	    
	    
	}


