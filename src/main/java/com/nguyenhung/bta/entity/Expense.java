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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "expenses")
public class Expense {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "expense_id", nullable = false)
	private Long expense_id;
	@NotEmpty(message = "Hãy chọn ngày thực hiện")
	@Column(name = "date", nullable = false)
	private Date date;
	@NotBlank(message = "Hãy nhập mô tả chi tiết ")
	@Column(name = "description", nullable = false)
	private String description;
	@NotNull(message = "Hãy nhập số tiền")
	@Column(name = "amount", nullable = false)
	private float amount;
	@ManyToOne(cascade = {CascadeType.PERSIST})
	@JoinColumn(name = "user_id", insertable = true, updatable = true)
	private User user;
	@ManyToOne(cascade = {CascadeType.PERSIST})
	@JoinColumn(name = "csid", insertable = true, updatable = true)
	private Category category;
	public Long getExpense_id() {
		return expense_id;
	}
	public void setExpense_id(Long expense_id) {
		this.expense_id = expense_id;
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
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	
}