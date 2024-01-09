package com.mertdev.therawdata.entities.concretes;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rawData")
public class RawData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String previewImageName;
	private String name;
	private String comment;
	private String rawDataName;
	private int price;
	@ManyToOne
	@JoinColumn(name = "rawdataFileId", nullable = false)
	private RawDataFile rawDataFileId;
	
	@ManyToMany(mappedBy = "rawDatas")
    private Set<Basket> carts;
	
	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;

	    result = prime * result + ((id == null) ? 0 : id.hashCode());
	    result = prime * result + ((rawDataFileId == null || rawDataFileId.getId() == null) ? 0 : rawDataFileId.getId().hashCode());
	    // Diğer alanlar için benzer şekilde devam edebilirsiniz.

	    return result;
	}
}
