package com.revature.project1.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class Reimbursement implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int reimbursement_id;
	private String reimbursement_type;
	private Timestamp reimbursement_time;
	private String reimbursement_description;
	private String receipt_name;
	private String receipt_path;
	private String reimbursement_status;
	private Employee requester;
	private Employee processor;
	
	public Reimbursement() {
		super();
	}

	public int getReimbursement_id() {
		return reimbursement_id;
	}

	public void setReimbursement_id(int reimbursement_id) {
		this.reimbursement_id = reimbursement_id;
	}

	public String getReimbursement_type() {
		return reimbursement_type;
	}

	public void setReimbursement_type(String reimbursement_type) {
		this.reimbursement_type = reimbursement_type;
	}

	public Timestamp getReimbursement_time() {
		return reimbursement_time;
	}

	public void setReimbursement_time(Timestamp reimbursement_time) {
		this.reimbursement_time = reimbursement_time;
	}

	public String getReimbursement_description() {
		return reimbursement_description;
	}

	public void setReimbursement_description(String reimbursement_description) {
		this.reimbursement_description = reimbursement_description;
	}

	public String getReceipt_name() {
		return receipt_name;
	}

	public void setReceipt_name(String receipt_name) {
		this.receipt_name = receipt_name;
	}

	public String getReceipt_path() {
		return receipt_path;
	}

	public void setReceipt_path(String receipt_path) {
		this.receipt_path = receipt_path;
	}

	public String getReimbursement_status() {
		return reimbursement_status;
	}

	public void setReimbursement_status(String reimbursement_status) {
		this.reimbursement_status = reimbursement_status;
	}

	public Employee getRequester() {
		return requester;
	}

	public void setRequester(Employee requester) {
		this.requester = requester;
	}

	public Employee getProcessor() {
		return processor;
	}

	public void setProcessor(Employee processor) {
		this.processor = processor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((processor == null) ? 0 : processor.hashCode());
		result = prime * result + ((receipt_name == null) ? 0 : receipt_name.hashCode());
		result = prime * result + ((receipt_path == null) ? 0 : receipt_path.hashCode());
		result = prime * result + ((reimbursement_description == null) ? 0 : reimbursement_description.hashCode());
		result = prime * result + reimbursement_id;
		result = prime * result + ((reimbursement_status == null) ? 0 : reimbursement_status.hashCode());
		result = prime * result + ((reimbursement_time == null) ? 0 : reimbursement_time.hashCode());
		result = prime * result + ((reimbursement_type == null) ? 0 : reimbursement_type.hashCode());
		result = prime * result + ((requester == null) ? 0 : requester.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		if (processor == null) {
			if (other.processor != null)
				return false;
		} else if (!processor.equals(other.processor))
			return false;
		if (receipt_name == null) {
			if (other.receipt_name != null)
				return false;
		} else if (!receipt_name.equals(other.receipt_name))
			return false;
		if (receipt_path == null) {
			if (other.receipt_path != null)
				return false;
		} else if (!receipt_path.equals(other.receipt_path))
			return false;
		if (reimbursement_description == null) {
			if (other.reimbursement_description != null)
				return false;
		} else if (!reimbursement_description.equals(other.reimbursement_description))
			return false;
		if (reimbursement_id != other.reimbursement_id)
			return false;
		if (reimbursement_status == null) {
			if (other.reimbursement_status != null)
				return false;
		} else if (!reimbursement_status.equals(other.reimbursement_status))
			return false;
		if (reimbursement_time == null) {
			if (other.reimbursement_time != null)
				return false;
		} else if (!reimbursement_time.equals(other.reimbursement_time))
			return false;
		if (reimbursement_type == null) {
			if (other.reimbursement_type != null)
				return false;
		} else if (!reimbursement_type.equals(other.reimbursement_type))
			return false;
		if (requester == null) {
			if (other.requester != null)
				return false;
		} else if (!requester.equals(other.requester))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reimbursement [reimbursement_id=" + reimbursement_id + ", reimbursement_type=" + reimbursement_type
				+ ", reimbursement_time=" + reimbursement_time + ", reimbursement_description="
				+ reimbursement_description + ", receipt_name=" + receipt_name + ", receipt_path=" + receipt_path
				+ ", reimbursement_status=" + reimbursement_status + ", requester=" + requester + ", processor="
				+ processor + "]";
	}
	
}
