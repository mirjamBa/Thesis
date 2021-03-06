package de.hsrm.perfunctio.core.shared.beans;

/**
 * Table column bean. Decribes the column id, column name and datatype of the
 * Column (ID of the DatatypeCodeType-Code)
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class ColumnSpec {
	private String id;
	private String text;
	private Long type;

	public ColumnSpec(String id, String text, Long type) {
		this.id = id;
		this.text = text;
		this.type = type;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the type
	 */
	public Long getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Long type) {
		this.type = type;
	}

}
