package wbs.model.wbs.elements;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("StandardWBSElement")
public class StandardWBSElement extends WBSElement {

    public StandardWBSElement() {}

    @Override
    public boolean acceptsChildren() {
        return true;
    }
}
