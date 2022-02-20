package gr.hua.ds.freetransportation.rest_api;

import java.util.List;

public class ApplicationsResponse<T> {
    int totalPages;
    List<T> applications;

    public ApplicationsResponse() {
    }

    public ApplicationsResponse(int totalPages, List<T> applications) {
        this.totalPages = totalPages;
        this.applications = applications;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getApplications() {
        return applications;
    }

    public void setApplications(List<T> applications) {
        this.applications = applications;
    }
}
