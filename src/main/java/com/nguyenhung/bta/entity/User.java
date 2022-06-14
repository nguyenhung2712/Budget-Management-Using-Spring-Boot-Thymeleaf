package com.nguyenhung.bta.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false)
	private Long user_id;
	@NotBlank(message = "Hãy nhập tên người dùng")
	@Column(name = "name", nullable = false)
	private String name;
	@NotBlank(message = "Hãy nhập email")
	@Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
	        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "Hãy nhập đúng định dạng email")
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "image")
	private String image;
	@NotBlank(message = "Hãy nhập tên tài khoản")
	@Column(name = "username", nullable = false)
	private String username;
	@NotBlank(message = "Hãy nhập số điện thoại")
	@Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$", message = "Số điện thoại không hợp lệ")
	@Column(name = "phone")
	private String phone;
	@NotBlank(message = "Hãy nhập mật khẩu")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Mật khẩu chứa ít nhất 1 ký tự hoa, thường và số")
	@Column(name = "password", nullable = false)
	private String password;
	@OneToMany(mappedBy = "user")
	@JsonIgnoreProperties
	private Set<Expense> expenses = new HashSet<>();
	@OneToMany(mappedBy = "user")
	@JsonIgnoreProperties
	private Set<Income> incomes = new HashSet<>();
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	@JsonIgnoreProperties
	private Set<Role> roles;
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<Expense> getExpenses() {
		return expenses;
	}
	public void setExpenses(Set<Expense> expenses) {
		this.expenses = expenses;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Set<Income> getIncomes() {
		return incomes;
	}
	public void setIncomes(Set<Income> incomes) {
		this.incomes = incomes;
	}
	
}
