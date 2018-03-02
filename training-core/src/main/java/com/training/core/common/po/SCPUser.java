package com.training.core.common.po;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * UserMember entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "scp_user", catalog = "scp")
public class SCPUser implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String gender;
	private String nationality;
	private String entity;
	private String division;

	// Constructors

	/** default constructor */
	public SCPUser() {}

	/** full constructor */
	public SCPUser(Integer id, String name, String gender, String nationality, String entity, String division) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.nationality = nationality;
		this.entity = entity;
		this.division = division;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

    @Column(name = "gender", length = 1)
    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

	@Column(name = "nationality", length = 100)
	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Column(name = "entity", length = 100)
	public String getEntity() {
		return this.entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	@Column(name = "division", length = 100)
	public String getDivision() {
		return this.division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

}