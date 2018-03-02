package com.training.core.common.po;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * UserMember entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "scp_user_idea", catalog = "scp")
public class SCPUserIdea implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer idea_id;
	private Integer user_id;
	private String user_idea;

	// Constructors

	/** default constructor */
	public SCPUserIdea() {}

	/** full constructor */
	public SCPUserIdea(Integer id, Integer idea_id, Integer user_id, String user_idea) {
		this.id = id;
		this.idea_id = idea_id;
		this.user_id = user_id;
		this.user_idea = user_idea;
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

	@Column(name = "idea_id", length = 11)
	public Integer getIdea_id() {
		return this.idea_id;
	}

	public void setIdea_id(Integer idea_id) {
		this.idea_id = idea_id;
	}

	@Column(name = "user_id", length = 11)
	public Integer getUser_id() {
		return this.user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

    @Column(name = "user_idea")
    public String getUser_idea() {
        return this.user_idea;
    }

    public void setUser_idea(String user_idea) {
        this.user_idea = user_idea;
    }

}