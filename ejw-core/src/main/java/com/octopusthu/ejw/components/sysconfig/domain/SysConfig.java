package com.octopusthu.ejw.components.sysconfig.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "SYS_CONFIG", uniqueConstraints = @UniqueConstraint(columnNames = {
		"section_name", "item_name" }))
public class SysConfig {
	@Id
	@GeneratedValue
	private Integer config_seq;

	@Column(nullable = false)
	private String section_name;

	@Column(nullable = false)
	private String item_name;

	private String item_type;

	private String default_value;

	@Column(nullable = false)
	private String item_value;

	private String item_desc;

	public Integer getConfig_seq() {
		return config_seq;
	}

	public void setConfig_seq(Integer config_seq) {
		this.config_seq = config_seq;
	}

	public String getSection_name() {
		return section_name;
	}

	public void setSection_name(String section_name) {
		this.section_name = section_name;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_type() {
		return item_type;
	}

	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}

	public String getDefault_value() {
		return default_value;
	}

	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}

	public String getItem_value() {
		return item_value;
	}

	public void setItem_value(String item_value) {
		this.item_value = item_value;
	}

	public String getItem_desc() {
		return item_desc;
	}

	public void setItem_desc(String item_desc) {
		this.item_desc = item_desc;
	}

}
