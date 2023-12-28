package com.mertdev.therawdata.entities.concretes;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.mertdev.therawdata.entities.abstracts.Publication;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity 
@Data 
@NoArgsConstructor 
@AllArgsConstructor 

public class PublicationPost {
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name="ownerId")
	private User user;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "publicationId", referencedColumnName = "id")
	private Publication publication;
	
	@OneToMany(mappedBy="publicationPostId")
    private List<RawDataFile> rawDataFile;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_time", updatable = false)
    private Date creationTime;
    
    @PrePersist
    protected void onCreate() {
        creationTime = new Date();
    }	
	
	
}
