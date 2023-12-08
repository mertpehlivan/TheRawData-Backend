package com.mertdev.therawdata.entities.abstracts;

import java.util.List;
import java.util.UUID;

import com.mertdev.therawdata.entities.concretes.PublicationAuthor;
import com.mertdev.therawdata.entities.concretes.PublicationPost;
import com.mertdev.therawdata.entities.concretes.RawData;
import com.mertdev.therawdata.entities.concretes.RawDataFile;
import com.mertdev.therawdata.entities.concretes.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    
    
  
    
    @OneToOne(mappedBy = "publication")
    private PublicationPost publicationPost;
    
    @OneToMany(mappedBy = "publicationId")
	private List<PublicationAuthor> publicationAuthors;
    
    
    
}