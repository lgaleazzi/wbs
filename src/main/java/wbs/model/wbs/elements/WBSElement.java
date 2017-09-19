package wbs.model.wbs.elements;

import javax.persistence.*;
import javax.validation.constraints.Size;

//parent class for all elements in the WBS tree

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Element_Type")
public class WBSElement {
    private static final String DEFAULT_NAME = "element";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Size(max = 60)
    private String name;

    @Column
    @Size(max = 2000)
    private String description;

    //enum listing the subclasses of WBSElement
    public enum ElementType {
        StandardWBSElement("Standard"), WorkPackage("Work package");

        private String name;

        ElementType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    //this column is managed automatically (inheritance relationship)
    @Column(name = "Element_Type", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private ElementType elementType;

    @PrePersist
    public void prePersist() {
        setNameToDefaultIfEmpty();
    }

    @PreUpdate
    public void preUpdate() {
        setNameToDefaultIfEmpty();
    }

    private void setNameToDefaultIfEmpty() {
        if (this.getName() == null || this.getName().isEmpty()) {
            this.setName(DEFAULT_NAME);
        }
    }

    public WBSElement() {}


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

    public ElementType getElementType() {
        return elementType;
    }

    public void setElementType(ElementType elementType) {
        this.elementType = elementType;
    }

    public boolean acceptsChildren() {
        return true;
    };

    @Override
    public String toString() {
        return "WBSElement{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", elementType=" + elementType +
                '}';
    }
}
