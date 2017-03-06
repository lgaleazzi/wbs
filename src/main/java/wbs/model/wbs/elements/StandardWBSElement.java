package wbs.model.wbs.elements;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by livia on 06.01.17.
 */
@Entity
@DiscriminatorValue("StandardWBSElement")
public class StandardWBSElement extends WBSElement {

    public StandardWBSElement() {}

    @Override
    public boolean acceptsChildren() {
        return true;
    }
}
