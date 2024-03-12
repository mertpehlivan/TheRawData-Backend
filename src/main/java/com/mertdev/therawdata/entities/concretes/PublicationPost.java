package com.mertdev.therawdata.entities.concretes;

import java.util.Date;
import java.util.List;
import java.util.Objects;
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
	
	private String publicationType;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "publicationId", referencedColumnName = "id")
	private Publication publication;
	
	@OneToMany(mappedBy="publicationPost",cascade = CascadeType.ALL)
    private List<Share> shares;
	
	@OneToMany(mappedBy="publicationPostId")
    private List<RawDataFile> rawDataFile;
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pdf_file", referencedColumnName = "id")
    private PdfFile pdfFile;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_time", updatable = false)
    private Date creationTime;
    
    @PrePersist
    protected void onCreate() {
        creationTime = new Date();
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PublicationPost other = (PublicationPost) obj;
		return Objects.equals(creationTime, other.creationTime) && Objects.equals(id, other.id)
				&& Objects.equals(publication, other.publication) && Objects.equals(rawDataFile, other.rawDataFile)
				&& Objects.equals(user, other.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(creationTime, id, publication, rawDataFile, user);
	}

	public PublicationPost(PublicationPost exists) {
		this.user = exists.user;
		this.publication = exists.publication;
		this.rawDataFile = exists.rawDataFile;
		
	}	
	
	
}
