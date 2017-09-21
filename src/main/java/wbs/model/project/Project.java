package wbs.model.project;

import wbs.model.authentication.User;
import wbs.model.wbs.WBSTree;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*
 * Each project contains one WBS tree holding the project's work breakdown structure
 * Each project belongs to a user
 */

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    @Size(min = 1, max = 60)
    private String name;

    @Column
    @Size(max = 2000)
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wbstree_id")
    private WBSTree wbsTree;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Project() {
        wbsTree = new WBSTree();
    }

    public Project(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.wbsTree = new WBSTree();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WBSTree getWbsTree() {
        return wbsTree;
    }

    public void setWbsTree(WBSTree wbsTree) {
        this.wbsTree = wbsTree;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (id != null ? !id.equals(project.id) : project.id != null) return false;
        return name != null ? name.equals(project.name) : project.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
