/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.web.controller;

import com.project.config.PropertiesConfig;
import com.project.dao.network.EdgeService;
import com.project.dao.network.NodeService;
import com.project.dao.user.UserService;
import com.project.exception.rest.BadRequestException;
import com.project.exception.rest.InternalServerException;
import com.project.exception.rest.UnAuthorizedException;
import com.project.model.network.EdgeListResource;
import com.project.model.network.EdgeResource;
import com.project.model.network.NodeListResource;
import com.project.model.network.NodeResource;
import com.project.util.ApplicationMessageEnum;
import com.project.util.Log4jUtil;
import com.project.util.Validate;
import com.project.util.searchcriteria.EdgeSearchCriteria;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author SAM
 */
@Controller
@RequestMapping(value = "/edgeman")
public class EdgeManagement_CLIENT_MVC_Controller extends Base_Controller {

    final int maxRecordsToFetch = 20;

    @Autowired
    private EdgeService edgeService;

    @Autowired
    private NodeService nodeService;

    @Autowired
    private UserService userService;

    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;

    @RequestMapping(value = "/edge/add", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView addEdgeDisplay(final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            EdgeResource edgeResource = new EdgeResource();
            model.addObject("edgeResource", edgeResource);

            NodeListResource readNodeListResource = nodeService.readAllEnabledNodes();
            List<NodeResource> nodeResources = readNodeListResource.getNodeResources();
            model.addObject("nodeResources", nodeResources);

            if (nodeResources == null || nodeResources.isEmpty()) {
                redirectAttributes.addFlashAttribute("pageMessage", "Please Enter Nodes First.");
                return new ModelAndView("redirect:" + "/result");
            }

            model.setViewName("edge_add");
            return model;
        } catch (UnAuthorizedException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (InternalServerException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (BadRequestException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (Exception e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    @RequestMapping(value = "/edge/add/submit", method = {RequestMethod.POST})
    public ModelAndView addEdgeProcess(@ModelAttribute("edgeResource") EdgeResource edgeResource, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            model.setViewName("edge_add");
            model.addObject("edgeResource", edgeResource);

            NodeListResource readNodeListResource = nodeService.readAllEnabledNodes();
            List<NodeResource> nodeResources = readNodeListResource.getNodeResources();
            model.addObject("nodeResources", nodeResources);

            boolean isValidated = true;

            Validate validate = new Validate();

            if (edgeResource.getFromNodeResource().getNodeID() == 0L) {
                isValidated = false;
                edgeResource.setErrFromNode("Incorrect");
            }

            if (edgeResource.getToNodeResource().getNodeID() == 0L) {
                isValidated = false;
                edgeResource.setErrToNode("Incorrect");
            }

            if ((!validate.isValidDefaultTextLength(edgeResource.getLabel()))) {
                isValidated = false;
                edgeResource.setErrLabel("Incorrect");
            }

            if (validate.isEmptyString(edgeResource.getTitle()) || (!validate.isValidDefaultTextLength(edgeResource.getTitle()))) {
                isValidated = false;
                edgeResource.setErrTitle("Incorrect");
            }

            if (isEdgeAlreadyExist(edgeResource.getFromNodeResource().getNodeID(), edgeResource.getToNodeResource().getNodeID())) {
                isValidated = false;
                edgeResource.setErrNode("Node Link is Already exist");
            }

            if (!validate.isValidDefaultTextLength(edgeResource.getLabel())) {
                isValidated = false;
                edgeResource.setErrLabel("Incorrect");
            }

            if (isValidated) {
                edgeResource.setEnabled(true);
                EdgeResource readResource = edgeService.createEdge(edgeResource);
                model.addObject("edgeResource", readResource);
                model.setViewName("edge_add_success");
                return model;
            }

            return model;
        } catch (UnAuthorizedException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (InternalServerException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (BadRequestException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (Exception e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    @RequestMapping(value = "/edge/search", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView searchEdgeDisplay(@ModelAttribute("searchCriteria") EdgeSearchCriteria searchCriteria, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            searchCriteria.setLimitResult(maxRecordsToFetch);

            EdgeListResource edgeListResource = edgeService.readEdgesBySearchCriteria(searchCriteria);
            model.addObject("edgeListResource", edgeListResource);
            if (searchCriteria.getStatusCode() == null) {

            } else if (searchCriteria.getStatusCode() == 1) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_ADD_RECORD.getMessage());
            } else if (searchCriteria.getStatusCode() == 2) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
            } else if (searchCriteria.getStatusCode() == 3) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            }

            model.addObject("searchCriteria", searchCriteria);

            model.setViewName("edge_list");
            return model;
        } catch (UnAuthorizedException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (InternalServerException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (BadRequestException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (Exception e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    @RequestMapping(value = "/edge/delete/submit", method = {RequestMethod.POST})
    public ModelAndView deleteEdgeProcess(@RequestParam(value = "id", required = true) Long id, final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            EdgeResource readEdgeResource = edgeService.deleteEdgeByID(id);

            EdgeSearchCriteria searchCriteria = new EdgeSearchCriteria();
            searchCriteria.setId(0L);//for search all
            searchCriteria.setStatusCode(3); // 3 for deleted
            redirectAttributes.addFlashAttribute("searchCriteria", searchCriteria);
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/edgeman/edge/search");

        } catch (UnAuthorizedException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (InternalServerException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (BadRequestException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (Exception e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    @RequestMapping(value = "/edge/changeenablestatus/submit", method = {RequestMethod.POST})
    public ModelAndView changeEnableStatusOfEdgeProcess(@RequestParam(value = "id", required = true) Long id, final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            EdgeResource readEdgeResource = edgeService.updateEdgeEnabledStatus(id);

            EdgeSearchCriteria searchCriteria = new EdgeSearchCriteria();
//            searchCriteria.setId(0L);//for search all
            System.out.println("--------------------------------------- dddd ---------- " + readEdgeResource.getEdgeID());
            searchCriteria.setId(readEdgeResource.getEdgeID());//for search all
            searchCriteria.setStatusCode(2); // 2 for Updated
            redirectAttributes.addFlashAttribute("searchCriteria", searchCriteria);
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/edgeman/edge/search");

        } catch (UnAuthorizedException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (InternalServerException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (BadRequestException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (Exception e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    @RequestMapping(value = "/edge/edit", method = {RequestMethod.POST})
    public ModelAndView editEdgeDisplay(@RequestParam(value = "id", required = true) Long id, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            EdgeResource edgeResource = edgeService.readEdgeByID(id);
            model.addObject("edgeResource", edgeResource);

            NodeListResource readNodeListResource = nodeService.readAllEnabledNodes();
            List<NodeResource> nodeResources = readNodeListResource.getNodeResources();
            model.addObject("nodeResources", nodeResources);

            if (nodeResources == null || nodeResources.isEmpty()) {
                redirectAttributes.addFlashAttribute("pageMessage", "Please Enter Nodes First.");
                return new ModelAndView("redirect:" + "/result");
            }

            model.setViewName("edge_edit");
            return model;
        } catch (UnAuthorizedException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (InternalServerException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (BadRequestException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (Exception e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    @RequestMapping(value = "/edge/edit/submit", method = {RequestMethod.POST})
    public ModelAndView editEdgeProcess(@RequestParam(value = "id", required = true) Long id, @ModelAttribute("edgeResource") EdgeResource edgeResource, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            EdgeResource readParentResource = edgeService.readEdgeByID(id);
            edgeResource.setEdgeID(readParentResource.getEdgeID());

            boolean isValidated = true;
            Validate validate = new Validate();

            if ((!validate.isValidDefaultTextLength(edgeResource.getLabel()))) {
                isValidated = false;
                edgeResource.setErrLabel("Incorrect");
            }

            if (validate.isEmptyString(edgeResource.getTitle()) || (!validate.isValidDefaultTextLength(edgeResource.getTitle()))) {
                isValidated = false;
                edgeResource.setErrTitle("Incorrect");
            }

            if (isValidated) {

                EdgeResource readUpdatedEdgeResource = edgeService.updateEdge(id, edgeResource);
                EdgeSearchCriteria searchCriteria = new EdgeSearchCriteria();
                searchCriteria.setId(readUpdatedEdgeResource.getEdgeID());
                searchCriteria.setStatusCode(2); // 2 for updated
                redirectAttributes.addFlashAttribute("searchCriteria", searchCriteria);
                redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
                return new ModelAndView("redirect:" + "/edgeman/edge/search");

            }

            model.addObject("edgeResource", edgeResource);
            model.setViewName("edge_edit");
            return model;
        } catch (UnAuthorizedException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (InternalServerException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (BadRequestException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (Exception e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

//    private boolean isEdgeAlreadyExist(String edgeCode) throws Exception {
//        EdgeResource edgeResource = edgeService.readEdgeByCode(edgeCode);
//        return edgeResource != null;
//    }
    private boolean isEdgeAlreadyExist(Long node1ID, Long node2ID) throws Exception {
        EdgeResource edgeResource = edgeService.readEdgeByNodeIDs(node1ID, node2ID);
        return edgeResource != null;
    }

}
