package com.training.core.common.po;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * UserMember entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "scp_team", catalog = "scp")
public class SCPTeam implements java.io.Serializable {

	// Fields

	private Integer id;
	private String team_no;
	private String team_title;
	private Integer max_count;

	// Constructors

	/** default constructor */
	public SCPTeam() {}

	/** full constructor */
	public SCPTeam(Integer id, String team_no, String team_title, Integer max_count) {
		this.id = id;
		this.team_no = team_no;
		this.team_title = team_title;
		this.max_count = max_count;
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

	@Column(name = "team_no", length = 10)
	public String getTeam_no() {
		return this.team_no;
	}

	public void setTeam_no(String team_no) {
		this.team_no = team_no;
	}

    @Column(name = "team_title", length = 200)
    public String getTeam_title() {
        return this.team_title;
    }

    public void setTeam_title(String team_title) {
        this.team_title = team_title;
    }

	@Column(name = "max_count", length = 11)
	public Integer getMax_count() {
		return this.max_count;
	}

	public void setMax_count(Integer max_count) {
		this.max_count = max_count;
	}

}