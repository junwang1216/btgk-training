package com.training.core.common.po;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * UserMember entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "scp_user_team", catalog = "scp")
public class SCPUserTeam implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer team_id;
	private Integer user_id;
	private String user_team;

	// Constructors

	/** default constructor */
	public SCPUserTeam() {}

	/** full constructor */
	public SCPUserTeam(Integer id, Integer team_id, Integer user_id, String user_team) {
		this.id = id;
		this.team_id = team_id;
		this.user_id = user_id;
		this.user_team = user_team;
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

	@Column(name = "team_id", length = 11)
	public Integer getTeam_id() {
		return this.team_id;
	}

	public void setTeam_id(Integer team_id) {
		this.team_id = team_id;
	}

	@Column(name = "user_id", length = 11)
	public Integer getUser_id() {
		return this.user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

    @Column(name = "user_team")
    public String getUser_team() {
        return this.user_team;
    }

    public void setUser_team(String user_team) {
        this.user_team = user_team;
    }

}