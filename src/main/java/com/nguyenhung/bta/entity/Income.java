package com.nguyenhung.bta.entity;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "incomes")
public class Income {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "income_id", nullable = false)
	private Long income_id;
	@Column(name = "date", nullable = false)
	private Date date;
	@Column(name = "description", nullable = false)
	private String description;
	@Column(name = "amount", nullable = false)
	private float amount;
	@ManyToOne(cascade = {CascadeType.PERSIST})
	@JsonIgnore
	@JoinColumn(name = "user_id", insertable = true, updatable = true)
	private User user;
	@ManyToOne(cascade = {CascadeType.PERSIST})
	@JoinColumn(name = "csid", insertable = true, updatable = true)
	private Source source;
	public Long getIncome_id() {
		return income_id;
	}
	public void setIncome_id(Long income_id) {
		this.income_id = income_id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Source getSource() {
		return source;
	}
	public void setSource(Source source) {
		this.source = source;
	}
	
	
}
