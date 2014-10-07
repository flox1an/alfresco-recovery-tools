package de.fmaul.alfresco;

public class RestoreJob {

	private Long id;
	
	private String nodeRef;
	
	private String filePath;
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void setNodeRef(String nodeRef) {
		this.nodeRef = nodeRef;
	}
	
	public String getNodeRef() {
		return nodeRef;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
}
