package wbs.web.controller;

import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wbs.model.wbs.WBSNode;
import wbs.model.wbs.WBSTree;
import wbs.model.wbs.elements.StandardWBSElement;
import wbs.model.wbs.elements.WBSElement;
import wbs.model.wbs.elements.WorkPackage;
import wbs.exceptions.InvalidObjectException;
import wbs.service.wbs.WBSNodeService;
import wbs.service.wbs.WBSTreeService;
import wbs.service.wbs.elements.WBSElementService;
import wbs.web.FlashMessage;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

import static wbs.web.WBSElementConverter.convertWBSElementToStandard;
import static wbs.web.WBSElementConverter.convertWBSElementToWorkPackage;

//TODO: implement data validation for WBS elements

@Controller
@SessionAttributes({"element"})
public class WBSController {

    @Autowired
    WBSTreeService wbsTreeService;

    @Autowired
    WBSNodeService wbsNodeService;

    @Autowired
    WBSElementService wbsElementService;

    @ModelAttribute("elementTypes")
    public List<WBSElement.ElementType> populateElementTypes() {
        return Arrays.asList(WBSElement.ElementType.values());
    }


    //view WBS tree
    @RequestMapping("/wbs/{wbsId}")
    public String viewWBSTree(@PathVariable Long wbsId, Model model) {
        WBSTree wbsTree = wbsTreeService.findbyId(wbsId);
        WBSNode rootNode = wbsTree.getRoot();
        model.addAttribute("tree", wbsTree);
        model.addAttribute("root", rootNode);
        return "/wbs/tree";
    }

    //view WBSElement
    @RequestMapping("/wbs/element/{nodeId}")
    public String viewWBSElement(@PathVariable Long nodeId, Model model) {
        WBSNode node = wbsNodeService.findbyId(nodeId);
        WBSElement wbsElement = node.getElement();
        model.addAttribute("element", wbsElement);
        model.addAttribute("node", node);
        return "wbs/details";
    }

    //form for adding a WBSElement
    @RequestMapping("/wbs/add/{parentNodeId}")
    public String addStandardElementForm(@PathVariable Long parentNodeId, Model model) {
        WBSNode parentNode = wbsNodeService.findbyId(parentNodeId);
        model.addAttribute("parent", parentNode);

        //repopulate form data in case of errors, otherwise add new element
        if (model.containsAttribute("repopulateElement")) {
            model.addAttribute("element", model.asMap().get("repopulateElement"));
        } else {
            WBSElement element = new WBSElement();
            //Set element type field to "StandardWBSElement" so that the Element Type option "Standard" is selected by default
            //in the input form. The user can change the choice is necessary
            element.setElementType(WBSElement.ElementType.StandardWBSElement);
            model.addAttribute("element", element);
        }

        return "wbs/addForm";
    }

    //add a child WBSElement to a node and redirect to the tree
    @RequestMapping(value = "/wbs/add/{parentNodeId}",method = RequestMethod.POST)
    public String addChild(@ModelAttribute("element") @Valid WBSElement wbsElement, BindingResult result, RedirectAttributes redirectAttributes, @PathVariable Long parentNodeId) {
        WBSNode parentNode = wbsNodeService.findbyId(parentNodeId);

        //if errors exist, add errors to model on redirect, and add project back to repopulate form data
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.element", result);
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Please check form data", FlashMessage.Status.DANGER));
            redirectAttributes.addFlashAttribute("repopulateElement", wbsElement);
            return "redirect:/wbs/add/"+parentNodeId;
        }

        //if no errors, find out element type and create it
        //if user entered elementType = work package, convert the element to type WorkPackage and create it
        if (wbsElement.getElementType() == WBSElement.ElementType.WorkPackage) {
            WorkPackage workPackage = convertWBSElementToWorkPackage(wbsElement);
            wbsNodeService.createFromParentNode(workPackage, parentNode);
        }
        //if user entered elementType = standard, convert the element to type StandardWBSElement and create it
        else if(wbsElement.getElementType() == WBSElement.ElementType.StandardWBSElement) {
            StandardWBSElement standardWBSElement = convertWBSElementToStandard(wbsElement);
            wbsNodeService.createFromParentNode(standardWBSElement, parentNode);
        }

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Element created", FlashMessage.Status.SUCCESS));

        return "redirect:/wbs/"+parentNode.getTree().getId();
    }

    //deleting node with subtree and redirecting to the tree
    @RequestMapping(value = "wbs/element/{nodeId}/delete", method = RequestMethod.POST)
    public String deleteElement(@PathVariable Long nodeId) {
        WBSNode node = wbsNodeService.findbyId(nodeId);
        Long treeId = node.getTree().getId();
        wbsNodeService.deleteById(nodeId);
        return "redirect:/wbs/"+treeId;
    }


    //Form to edit element of a node
    @RequestMapping("/wbs/element/{nodeId}/edit")
    public String editElementForm(@PathVariable Long nodeId, Model model) {
        WBSNode node = wbsNodeService.findbyId(nodeId);
        model.addAttribute("node", node);

        if (node.getElement().getElementType() == WBSElement.ElementType.StandardWBSElement) {
            StandardWBSElement element = (StandardWBSElement) node.getElement();
            model.addAttribute("element", element);
        } else if (node.getElement().getElementType() == WBSElement.ElementType.WorkPackage) {
            WorkPackage element = (WorkPackage) node.getElement();
            model.addAttribute("element", element);
        } else {
            throw new InvalidObjectException("WBS Element object is invalid");
        }

        return "wbs/editForm";
    }

    //Edit a StandardWBSElement
    @RequestMapping(value = "wbs/element/{nodeId}", method = RequestMethod.POST)
    public String editStandardWBSElement(@ModelAttribute("element") StandardWBSElement element, @PathVariable Long nodeId) {
        wbsElementService.edit(element);
        return "redirect:/wbs/element/"+nodeId;
    }

    //Edit a WorkPackage
    @RequestMapping(value = "wbs/wp_element/{nodeId}", method = RequestMethod.POST)
    public String editWorkPackage(@ModelAttribute("element") WorkPackage workPackage, @PathVariable Long nodeId) {
        wbsElementService.edit(workPackage);
        return "redirect:/wbs/element/"+nodeId;
    }
}
