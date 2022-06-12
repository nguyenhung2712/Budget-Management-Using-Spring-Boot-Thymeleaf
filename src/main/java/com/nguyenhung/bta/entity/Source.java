package com.nguyenhung.bta.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sources")
public class Source {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long csid;
	@NotBlank(message = "Hãy nhập tên nguồn thu nhập")
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "icon")
	private String icon;
	@OneToMany(mappedBy = "source")
	@JsonIgnoreProperties
	private Set<Income> expenses = new HashSet<>();
	public Long getCsid() {
		return csid;
	}
	public void setCsid(Long csid) {
		this.csid = csid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Income> getExpenses() {
		return expenses;
	}
	public void setExpenses(Set<Income> expenses) {
		this.expenses = expenses;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
}
