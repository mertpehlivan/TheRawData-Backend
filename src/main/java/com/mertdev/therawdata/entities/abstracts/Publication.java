package com.mertdev.therawdata.entities.abstracts;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.mertdev.therawdata.entities.concretes.PublicationAuthor;
import com.mertdev.therawdata.entities.concretes.PublicationPost;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "publication_type")
public abstract class Publication {
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    
    @Column(length = 2000)
	private String comment;
    
    @OneToOne(mappedBy = "publication")
    private PublicationPost publicationPost;
    
    @OneToMany(mappedBy = "publication")
    private List<PublicationAuthor> publicationAuthors;
    
    public abstract PublicationType getObject();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Publication other = (Publication) obj;
		return Objects.equals(comment, other.comment) && Objects.equals(id, other.id)
				&& Objects.equals(publicationAuthors, other.publicationAuthors)
				&& Objects.equals(publicationPost, other.publicationPost) && Objects.equals(title, other.title);
	}

	@Override
	public int hashCode() {
		return Objects.hash(comment, id, publicationAuthors, publicationPost, title);
	}
}
