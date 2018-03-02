package com.training.core.common.po;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * SCPIdea entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "scp_idea", catalog = "scp")
public class SCPIdea implements java.io.Serializable {

	// Fields

	private Integer id;
	private String idea_no;
	private String idea_title;
	private Integer max_count;

	// Constructors

	/** default constructor */
	public SCPIdea() {}

	/** full constructor */
	public SCPIdea(Integer id, String idea_no, String idea_title, Integer max_count) {
		this.id = id;
		this.idea_no = idea_no;
		this.idea_title = idea_title;
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

	@Column(name = "idea_no", length = 10)
	public String getIdea_no() {
		return this.idea_no;
	}

	public void setIdea_no(String idea_no) {
		this.idea_no = idea_no;
	}

    @Column(name = "idea_title", length = 200)
    public String getIdea_title() {
        return this.idea_title;
    }

    public void setIdea_title(String idea_title) {
        this.idea_title = idea_title;
    }

	@Column(name = "max_count", length = 11)
	public Integer getMax_count() {
		return this.max_count;
	}

	public void setMax_count(Integer max_count) {
		this.max_count = max_count;
	}

}