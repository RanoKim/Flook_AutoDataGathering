package automation.DTO;

public class CommentDTO {
	private String profileImageLink;
	private String name;
	private String commentDate;
	private String comment;
	
	public CommentDTO(String profileImageLink, String name, String commentDate, String comment) {
		super();
		this.profileImageLink = profileImageLink;
		this.name = name;
		this.commentDate = commentDate;
		this.comment = comment;
	}
	public String getProfileImageLink() {
		return profileImageLink;
	}
	public void setProfileImageLink(String profileImageLink) {
		this.profileImageLink = profileImageLink;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "CommentDTO [profileImageLink=" + profileImageLink + ", name=" + name + ", commentDate=" + commentDate
				+ ", comment=" + comment + "]";
	}
	
	
}
