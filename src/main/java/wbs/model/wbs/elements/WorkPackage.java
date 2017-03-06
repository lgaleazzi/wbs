package wbs.model.wbs.elements;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("WorkPackage")
public class WorkPackage extends WBSElement {

    public WorkPackage() {}

    @Override
    public boolean acceptsChildren() {
        return false;
    }
}
