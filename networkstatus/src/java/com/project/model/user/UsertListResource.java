package com.project.model.user;

import org.springframework.hateoas.ResourceSupport;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class UsertListResource extends ResourceSupport {

    private List<UserResource> users = new ArrayList<UserResource>();
    private Long totalPages = new Long(0);
    private Long totalDocuments = new Long(0);

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalDocuments() {
        return totalDocuments;
    }

    public void setTotalDocuments(Long totalDocuments) {
        this.totalDocuments = totalDocuments;
    }

    public List<UserResource> getUsers() {
        return users;
    }

    public void setUsers(List<UserResource> users) {
        this.users = users;
    }

}
