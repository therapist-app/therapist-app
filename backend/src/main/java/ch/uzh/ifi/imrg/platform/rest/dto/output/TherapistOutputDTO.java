package ch.uzh.ifi.imrg.platform.rest.dto.output;

public class TherapistOutputDTO {
    private String id;
    private String email;
    private String workspaceId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkspaceId() { return workspaceId; }

    public void setWorkspaceId(String workspaceId) { this.workspaceId = workspaceId; }
}