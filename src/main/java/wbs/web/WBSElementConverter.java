package wbs.web;

import wbs.model.wbs.elements.StandardWBSElement;
import wbs.model.wbs.elements.WBSElement;
import wbs.model.wbs.elements.WorkPackage;

public class WBSElementConverter {

    //converts object of parent class WBSElement to WBSStandardElement
    public static StandardWBSElement convertWBSElementToStandard(WBSElement wbsElement)
    {
        StandardWBSElement standardWBSElement = new StandardWBSElement();
        standardWBSElement.setName(wbsElement.getName());
        standardWBSElement.setDescription(wbsElement.getDescription());

        return standardWBSElement;
    }

    //converts object of parent class WBSElement to WorkPackage
    public static WorkPackage convertWBSElementToWorkPackage(WBSElement wbsElement) {
        WorkPackage workPackage = new WorkPackage();
        workPackage.setName(wbsElement.getName());
        workPackage.setDescription(wbsElement.getDescription());

        return workPackage;
    }
}
