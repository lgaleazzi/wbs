package wbs.web.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.sun.xml.internal.bind.api.impl.NameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wbs.model.wbs.WBSNode;
import wbs.model.wbs.WBSTree;
import wbs.model.wbs.elements.StandardWBSElement;
import wbs.model.wbs.elements.WBSElement;
import wbs.model.wbs.elements.WorkPackage;
import wbs.service.InvalidObjectException;
import wbs.service.ObjectNotFoundException;
import wbs.service.wbs.WBSNodeService;
import wbs.service.wbs.WBSTreeService;
import wbs.service.wbs.elements.WBSElementService;

/**
 * Created by livia on 16.01.17.
 */

@Controller
@SessionAttributes({"element"})
public class WBSController {

    @Autowired
    WBSTreeService wbsTreeService;

    @Autowired
    WBSNodeService wbsNodeService;

    @Autowired
    WBSElementService wbsElementService;

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

    //form for adding a child to a node
    @RequestMapping("/wbs/add/{parentNodeId}")
    public String addChildForm(@PathVariable Long parentNodeId, Model model) {
        WBSNode parentNode = wbsNodeService.findbyId(parentNodeId);
        model.addAttribute("parent", parentNode);
        model.addAttribute("element", new WBSElement());
        return "wbs/addForm";
    }

    //add a child to a node and redirect to the tree
    //TODO: get some of this code out of the controller?
    @RequestMapping(value = "/wbs/add/{parentNodeId}",method = RequestMethod.POST)
    public String addChild(StandardWBSElement wbsElement, @PathVariable Long parentNodeId, Model model) {
        WBSNode parentNode = wbsNodeService.findbyId(parentNodeId);
        WBSNode newNode = new WBSNode(wbsElement, parentNode);
        newNode.setTree(parentNode.getTree());
        wbsNodeService.create(newNode);
        return "redirect:/wbs/"+parentNode.getTree().getId();
    }

    //TODO: implement deleting children if node is not a leaf
    //deleting node and redirecting to the tree if the node has no children
    @RequestMapping(value = "wbs/element/{nodeId}/delete", method = RequestMethod.POST)
    public String deleteElement(@PathVariable Long nodeId) {
        WBSNode node = wbsNodeService.findbyId(nodeId);
        Long treeId = node.getTree().getId();
        if (node.isLeaf()) {
            wbsNodeService.delete(node);
            return "redirect:/wbs/"+treeId;
        }

        return "redirect:/wbs/element/"+nodeId;
    }


    //Form to edit element of a node
    @RequestMapping("/wbs/element/{nodeId}/edit")
    public String editElementForm(@PathVariable Long nodeId, Model model) {
        WBSNode node = wbsNodeService.findbyId(nodeId);
            model.addAttribute("node", node);
        if (node.getElement() instanceof StandardWBSElement) {
            StandardWBSElement element = (StandardWBSElement) node.getElement();
            model.addAttribute("element", element);
        } else if (node.getElement() instanceof WorkPackage) {
            WorkPackage element = (WorkPackage) node.getElement();
            model.addAttribute("element", element);
        } else {
            throw new InvalidObjectException("WBS Element object is invalid");
        }
        return "wbs/editForm";
    }

    //Edit an existing element
    //TODO: this doesn't work if element is a subtype of WBSElement (can I deal with that in the service layer?)

    @RequestMapping(value = "wbs/element/{nodeId}", method = RequestMethod.POST)
    public String editElement(@ModelAttribute("element") WBSElement element, @PathVariable Long nodeId) {

        wbsElementService.edit(element);

        return "redirect:/wbs/element/"+nodeId;
    }
}
