/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.web.controller;

import com.project.config.PropertiesConfig;
import com.project.dao.network.NodeService;
import com.project.dao.user.UserService;
import com.project.exception.rest.BadRequestException;
import com.project.exception.rest.InternalServerException;
import com.project.exception.rest.UnAuthorizedException;
import com.project.model.network.EdgeListResource;
import com.project.model.network.EdgeResource;
import com.project.model.network.NodeAlertInfoResource;
import com.project.model.network.NodeAlertResource;
import com.project.model.network.NodeAndEdgeListResource;
import com.project.model.network.NodeListResource;
import com.project.model.network.NodeResource;
import com.project.model.user.UserResource;
import com.project.model.util.FileInfo;
import com.project.model.util.UploadedFileContent;
import com.project.util.ApplicationMessageEnum;
import com.project.util.Log4jUtil;
import com.project.util.Validate;
import com.project.util.enums.NODE_TYPE;
import com.project.util.searchcriteria.NodeSearchCriteria;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author SAM
 */
@Controller
@RequestMapping(value = "/nodeman")
public class NodeManagement_CLIENT_MVC_Controller extends Base_Controller {

    final int maxRecordsToFetch = 20;

    @Autowired
    private NodeService nodeService;

    @Autowired
    private UserService userService;

    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;

    @RequestMapping(value = "/node/add", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView addNodeDisplay(final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            NodeResource nodeResource = new NodeResource();
            model.addObject("nodeResource", nodeResource);
            model.setViewName("node_add");
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

    @RequestMapping(value = "/node/add/submit", method = {RequestMethod.POST})
    public ModelAndView addNodeProcess(@ModelAttribute("nodeResource") NodeResource nodeResource, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            model.setViewName("node_add");
            model.addObject("nodeResource", nodeResource);

            boolean isValidated = true;

            Validate validate = new Validate();

            if (validate.isEmptyString(nodeResource.getTitle()) || (!validate.isValidDefaultTextLength(nodeResource.getTitle()))) {
                isValidated = false;
                nodeResource.setErrTitle("Incorrect.");
            }

            if (validate.isEmptyString(nodeResource.getLabel()) || (!validate.isValidDefaultTextLength(nodeResource.getLabel()))) {
                isValidated = false;
                nodeResource.setErrLabel("Incorrect.");
            }

            if (validate.userTMP(nodeResource.getLabel())) {
                if (isNodeAlreadyExist(nodeResource.getLabel())) {
                    isValidated = false;
                    nodeResource.setErrLabel("Already exist");
                }

                if (nodeResource.getLabel().trim().startsWith("ng-")) {
                    isValidated = false;
                    nodeResource.setErrLabel("Reserved ID, Incorrect");
                }

                if (!validate.isValidDefaultTextLength(nodeResource.getLabel())) {
                    isValidated = false;
                    nodeResource.setErrLabel("Incorrect.");
                }

            } else {
                isValidated = false;
                nodeResource.setErrLabel("Incorrect. No space keyword allowed. Try '_,-' instead. ");
            }
//nodeResource.setErrRedirectingURLLink("xxxIncorrect " + validate.isValidURL(nodeResource.getRedirectingURLLink()));
            if (!validate.isValidURL(nodeResource.getRedirectingURLLink())) {
                isValidated = false;
                nodeResource.setErrRedirectingURLLink("Incorrect ");
            }

            if (isValidated) {
//                nodeResource.setEnabled(true);

                final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
                final String imagePath = baseUrl + "/resources/icons/";
                String fullImagePath = imagePath + "edit_32x32.png";

                if (nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_ROUTER.type())) {
                    fullImagePath = imagePath + "router-type.png";
                } else if (nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_SERVER.type())) {
                    fullImagePath = imagePath + "server-type.png";
                } else if (nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_NETWORKGROUP.type())) {
//                    if (nodeResource.getAlert().getType() == 1) {
                    fullImagePath = imagePath + "networkgroup-type.png";
//                    } else {
//                        fullImagePath = imagePath + "networkgroup-alert-type.png";
//                    }

                } else if (nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_DATABASE.type())) {
//                    if (nodeResource.getAlert().getType() == 1) {
                    fullImagePath = imagePath + "database-type.png";
//                    } else {
//                        fullImagePath = imagePath + "database-alert-type.png";
//                    }

                } else if (nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_HUB.type())) {
//                    if (nodeResource.getAlert().getType() == 1) {
                    fullImagePath = imagePath + "hub-type.png";
//                    } else {
//                        fullImagePath = imagePath + "hub-alert-type.png";
//                    }

                } else if (nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_PRINTER.type())) {
//                    if (nodeResource.getAlert().getType() == 1) {
                    fullImagePath = imagePath + "printer-type.png";
//                    } else {
//                        fullImagePath = imagePath + "printer-alert-type.png";
//                    }

                } else if (nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_SWITCH.type())) {
//                    if (nodeResource.getAlert().getType() == 1) {
                    fullImagePath = imagePath + "switch-type.png";
//                    } else {
//                        fullImagePath = imagePath + "switch-alert-type.png";
//                    }

                } else if (nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_COMPUTER.type())) {
                    fullImagePath = imagePath + "cmputer-type.png";

                } else {
                    fullImagePath = imagePath + "cmputer-type.png";
                }

                nodeResource.getFill().setSrc(fullImagePath);
                nodeResource.getAlert().setType(1);
                nodeResource.getAlert().setDescription("");

                double domX = (int) (Math.random() * 800);
                double domY = (int) (Math.random() * 800);

                double convasX = (int) (Math.random() * 800);
                double convasY = (int) (Math.random() * 800);

                nodeResource.setCanvasXValue(convasX);
                nodeResource.setCanvasYValue(convasY);
                nodeResource.setDomXValue(domX);
                nodeResource.setDomYValue(domY);
                nodeResource.setZoomScale(1.0D);

                NodeResource readResource = nodeService.createNode(nodeResource);
                model.addObject("nodeResource", readResource);
                model.setViewName("node_add_success");
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

    @RequestMapping(value = "/node/search", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView searchNodeDisplay(@ModelAttribute("searchCriteria") NodeSearchCriteria searchCriteria, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            searchCriteria.setLimitResult(maxRecordsToFetch);

            NodeListResource nodeListResource = nodeService.readNodesBySearchCriteria(searchCriteria);
            model.addObject("nodeListResource", nodeListResource);
            if (searchCriteria.getStatusCode() == null) {

            } else if (searchCriteria.getStatusCode() == 1) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_ADD_RECORD.getMessage());
            } else if (searchCriteria.getStatusCode() == 2) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
            } else if (searchCriteria.getStatusCode() == 3) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            }

            model.addObject("searchCriteria", searchCriteria);

            model.setViewName("node_list");
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

    @RequestMapping(value = "/node/delete/submit", method = {RequestMethod.POST})
    public ModelAndView deleteNodeProcess(@RequestParam(value = "id", required = true) Long id, final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            NodeResource readNodeResource = nodeService.deleteNodeByID(id);

            NodeSearchCriteria searchCriteria = new NodeSearchCriteria();
            searchCriteria.setId(0L);//for search all
            searchCriteria.setStatusCode(3); // 3 for deleted
            redirectAttributes.addFlashAttribute("searchCriteria", searchCriteria);
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/nodeman/node/search");

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

    @RequestMapping(value = "/node/changeenablestatus/submit", method = {RequestMethod.POST})
    public ModelAndView changeEnableStatusOfNodeProcess(@RequestParam(value = "id", required = true) Long id, final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            NodeResource readNodeResource = nodeService.updateNodeEnabledStatus(id);

            NodeSearchCriteria searchCriteria = new NodeSearchCriteria();
//            searchCriteria.setId(0L);//for search all
            System.out.println("--------------------------------------- dddd ---------- " + readNodeResource.getNodeID());
            searchCriteria.setId(readNodeResource.getNodeID());//for search all
            searchCriteria.setStatusCode(2); // 2 for Updated
            redirectAttributes.addFlashAttribute("searchCriteria", searchCriteria);
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/nodeman/node/search");

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

    @RequestMapping(value = "/node/edit", method = {RequestMethod.POST})
    public ModelAndView editNodeDisplay(@RequestParam(value = "id", required = true) Long id, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            NodeResource nodeResource = nodeService.readNodeByID(id);
            model.addObject("nodeResource", nodeResource);

            model.setViewName("node_edit");
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

    @RequestMapping(value = "/node/edit/submit", method = {RequestMethod.POST})
    public ModelAndView editNodeProcess(@RequestParam(value = "id", required = true) Long id, @ModelAttribute("nodeResource") NodeResource nodeResource, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            NodeResource readParentResource = nodeService.readNodeByID(id);
            nodeResource.setNodeID(readParentResource.getNodeID());

            boolean isValidated = true;
            Validate validate = new Validate();

            if (validate.isEmptyString(nodeResource.getTitle()) || (!validate.isValidDefaultTextLength(nodeResource.getTitle()))) {
                isValidated = false;
                nodeResource.setErrTitle("Incorrect.");
            }

            if (validate.isEmptyString(nodeResource.getLabel()) || (!validate.isValidDefaultTextLength(nodeResource.getLabel()))) {
                isValidated = false;
                nodeResource.setErrLabel("Incorrect");
            }

            if (!validate.isValidURL(nodeResource.getRedirectingURLLink())) {
                isValidated = false;
                nodeResource.setErrRedirectingURLLink("Incorrect ");
            }

            if (isValidated) {

                final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
                final String imagePath = baseUrl + "/resources/icons/";
                String fullImagePath = imagePath + "edit_32x32.png";

                if (nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_ROUTER.type())) {
//                    fullImagePath = imagePath + "router-type.png";
                    if (nodeResource.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "router-type.png";
                    } else {
                        fullImagePath = imagePath + "router-alert-type.png";
                    }
                } else if (nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_SERVER.type())) {
//                    fullImagePath = imagePath + "server-type.png";
                    if (nodeResource.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "server-type.png";
                    } else {
                        fullImagePath = imagePath + "server-alert-type.png";
                    }
                } else if (nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_COMPUTER.type())) {
//                    fullImagePath = imagePath + "server-type.png";
                    if (nodeResource.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "cmputer-type.png";
                    } else {
                        fullImagePath = imagePath + "cmputer-alert-type.png";
                    }
                } else if (nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_NETWORKGROUP.type())) {
                    if (nodeResource.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "networkgroup-type.png";
                    } else {
                        fullImagePath = imagePath + "networkgroup-alert-type.png";
                    }

                } else if (nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_DATABASE.type())) {
                    if (nodeResource.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "database-type.png";
                    } else {
                        fullImagePath = imagePath + "database-alert-type.png";
                    }

                } else if (nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_HUB.type())) {
                    if (nodeResource.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "hub-type.png";
                    } else {
                        fullImagePath = imagePath + "hub-alert-type.png";
                    }

                } else if (nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_PRINTER.type())) {
                    if (nodeResource.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "printer-type.png";
                    } else {
                        fullImagePath = imagePath + "printer-alert-type.png";
                    }

                } else if (nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_SWITCH.type())) {
                    if (nodeResource.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "switch-type.png";
                    } else {
                        fullImagePath = imagePath + "switch-alert-type.png";
                    }

                } else {
                    fullImagePath = imagePath + "cmputer-type.png";
                }

                nodeResource.getFill().setSrc(fullImagePath);

                NodeResource readUpdatedNodeResource = nodeService.updateNode(id, nodeResource);
                NodeSearchCriteria searchCriteria = new NodeSearchCriteria();
                searchCriteria.setId(readUpdatedNodeResource.getNodeID());
                searchCriteria.setStatusCode(2); // 2 for updated
                redirectAttributes.addFlashAttribute("searchCriteria", searchCriteria);
                redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
                return new ModelAndView("redirect:" + "/nodeman/node/search");

            }

            model.addObject("nodeResource", nodeResource);
            model.setViewName("node_edit");
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

    private boolean isNodeAlreadyExist(String label) throws Exception {
        NodeResource nodeResource = nodeService.readNodeByLabel(label);
        return nodeResource != null;
    }

    @RequestMapping(value = "/node/node_alert_to_reset/submit", method = {RequestMethod.POST})
    public ModelAndView changNodeAlertToReset(@RequestParam(value = "id", required = true) Long id, final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            NodeResource readNodeResource = nodeService.updateNodeAlertAsReset(id);

            NodeSearchCriteria searchCriteria = new NodeSearchCriteria();
//            searchCriteria.setId(0L);//for search all
            System.out.println("--------------------------------------- dddd ---------- " + readNodeResource.getNodeID());
            searchCriteria.setId(readNodeResource.getNodeID());//for search all
            searchCriteria.setStatusCode(2); // 2 for Updated
            redirectAttributes.addFlashAttribute("searchCriteria", searchCriteria);
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/networkman/dynamic_chart/display");

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

    @RequestMapping(value = "/node/node_alert_to_dummy_insert/submit", method = {RequestMethod.POST})
    public ModelAndView changNodeAlertToDummyInsert(@RequestParam(value = "id", required = true) Long id, final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            //                    sentNodeResource= new NodeResource();
            NodeResource nodeResource = nodeService.readNodeByID(id);
            NodeAlertResource alert = new NodeAlertResource();
            alert.setType(2 + (int) (10 * Math.random()));
            alert.setDescription("Descrption - " + Math.random());
            nodeResource.setAlert(alert);

            int rand = (int) (10 * Math.random());
            NodeAlertInfoResource nodeAlertInfoResource = new NodeAlertInfoResource();
            nodeAlertInfoResource.setDescription("Desc " + rand);
            nodeAlertInfoResource.setNodeAlertInfoID(null);
            nodeAlertInfoResource.setType(rand);

            nodeResource.getAlert().getNodeAlertInfoResources().add(nodeAlertInfoResource);

            NodeResource readNodeResource = nodeService.updateNodeAlertToAddNodeAlertInfo(id, nodeResource);

            NodeSearchCriteria searchCriteria = new NodeSearchCriteria();
//            searchCriteria.setId(0L);//for search all
            System.out.println("--------------------------------------- dddd ---------- " + readNodeResource.getNodeID());
            searchCriteria.setId(readNodeResource.getNodeID());//for search all
            searchCriteria.setStatusCode(2); // 2 for Updated
            redirectAttributes.addFlashAttribute("searchCriteria", searchCriteria);
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/networkman/dynamic_chart/display");

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

    @RequestMapping(value = "/node/add/upload_csv", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView updoadCSVToAddNodeDisplay(final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            NodeResource nodeResource = new NodeResource();
            model.addObject("nodeResource", nodeResource);
            model.setViewName("node_add_upload_csv");
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

    @RequestMapping(value = "/node/add/upload_csv/upload/submit", method = {RequestMethod.POST})
    public ModelAndView uploadUserProfileImageInUserAddProcess(@RequestParam("file") MultipartFile file, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

//            javax.swing.JOptionPane.showMessageDialog(null, "sssds");
            if (file.isEmpty()) {

                model.addObject("pageError", ApplicationMessageEnum.ERROR_EMPTY_CSV_UPLOAD.getMessage());
//                userResource.setPassword(newPassword);
//                userResource.setErrProfileImage("Please Select Valid File.");
//                model.addObject("userResource", userResource);
                model.setViewName("node_add_upload_csv");
                return model;
            } else {

                final List<String> contentTypes = Arrays.asList("application/csv", "application/vnd.ms-excel","application/octet-stream");
                String fileContentType = file.getContentType();
                
                writeLogMessage("Uploaded File Content Type : " +fileContentType);
                if (contentTypes.contains(fileContentType)) {
                    // You have the correct extension
                    // rest of your code here

                    // Get the file and save it somewhere
                    byte[] bytes = file.getBytes();

                    String extension = FilenameUtils.getExtension(file.getOriginalFilename()); // returns "txt"

                    UploadedFileContent uploadedFileContent = new UploadedFileContent();
                    uploadedFileContent.setBytes(bytes);
                    uploadedFileContent.setFileSize(file.getSize());
                    uploadedFileContent.setFilename(file.getOriginalFilename());
                    uploadedFileContent.setNewFilename("UploadedCSV" + "." + extension);
                    uploadedFileContent.setSourceId(0L);

                    FileInfo fileInfo = nodeService.uploadCSVFileToImportNodesByObject(uploadedFileContent);

                    if (fileInfo != null) {
//                        userResource.setErrProfileImage("");
                        model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_CSV_UPLOAD.getMessage());
                    } else {
//                        userResource.setErrProfileImage(ApplicationMessageEnum.ERROR_FAILED_PROFILE_PICTURE_UPLOAD.getMessage());
                        model.addObject("pageError", ApplicationMessageEnum.ERROR_FAILED_CSV_UPLOAD.getMessage());
                    }

                } else {
//                    userResource.setErrProfileImage(ApplicationMessageEnum.ERROR_INVALID_CSV_UPLOAD.getMessage());
                    model.addObject("pageError", ApplicationMessageEnum.ERROR_INVALID_CSV_UPLOAD.getMessage());
                    // Handle error of not correct extension
                }
            }
//            userResource.setPassword(newPassword);
//            model.addObject("userResource", userResource);
            model.setViewName("node_add_upload_csv");
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

    @RequestMapping(value = "/node/add/upload_csv/upload/display", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView updoadedCSVToAddNodeDisplay(final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            NodeListResource nodeListResource = nodeService.readAllCSVUploadedNodes();
//            model.addObject("nodeListResource", nodeListResource);
//
//          
//            
//            
//            
//            
//            
//            
//            
//   
//
//            NodeListResource listResource = nodeService.readAllEnabledNodes();

            model.addObject("tableData", nodeListResource);

            /// END
            model.setViewName("node_add_upload_csv_edit");
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

    //  MODIFICAITION
    @RequestMapping(value = "/node/add/upload_csv/upload/display/submit", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView updoadedCSVToAddNodeSubmit(@ModelAttribute("nodeListResource") NodeListResource nodeListResource, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            boolean isValidated = true;

            Validate validate = new Validate();

            final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            final String imagePath = baseUrl + "/resources/icons/";
            String fullImagePath = imagePath + "edit_32x32.png";

            List<String> invalidSerialNos = new ArrayList<>();

//            HashMap<Long, String> hashMap = new HashMap();
            HashMap<Long, NodeResource> hashMapNodes = new HashMap();

            if (nodeListResource.getNodeResources() != null) {
//                javax.swing.JOptionPane.showMessageDialog(null, nodeListResource.getNodeResources().size());

                for (NodeResource nodeResource : nodeListResource.getNodeResources()) {
                    if (nodeResource.isEnabled()) {
//                        hashMap.put(nodeResource.getNodeID(), nodeResource.getLabel());
                        hashMapNodes.put(nodeResource.getNodeID(), nodeResource);
                    }
                }

                for (NodeResource nodeResource : nodeListResource.getNodeResources()) {
//                    javax.swing.JOptionPane.showMessageDialog(null, "Node ID :" + nodeResource.getNodeID() + "; Label :" + nodeResource.getLabel()+ "; Title =" + nodeResource.getTitle()+ "; Skipped =" + nodeResource.isEnabled());
//                    System.out.println("x :" + nodeResource.getHeight());
                    boolean isValidatedCurrent = true;
                    if (nodeResource.isEnabled()) {

                        if (validate.isEmptyString(nodeResource.getLabel()) || (!validate.isValidDefaultTextLength(nodeResource.getLabel()))) {
                            isValidated = false;
                            isValidatedCurrent = false;
                            nodeResource.setErrLabel("Incorrect.");
                        }

                        if (validate.userTMP(nodeResource.getLabel())) {

                            if (!validate.isValidDefaultTextLength(nodeResource.getLabel())) {
                                isValidated = false;
                                isValidatedCurrent = false;
                                nodeResource.setErrLabel("Incorrect.");
                            }

                        } else {
                            isValidated = false;
                            isValidatedCurrent = false;
                            nodeResource.setErrLabel("No 'space' allowed.");
                        }

                        if (validate.isEmptyString(nodeResource.getTitle()) || (!validate.isValidDefaultTextLength(nodeResource.getTitle()))) {
                            isValidated = false;
                            isValidatedCurrent = false;
                            nodeResource.setErrTitle("Incorrect.");
                        }

                        if (validate.isEmptyString(nodeResource.getType()) || (!validate.isValidDefaultTextLength(nodeResource.getType()))) {
                            isValidated = false;
                            isValidatedCurrent = false;
                            nodeResource.setErrType("Incorrect.");
                        }

                        if (!validate.isValidURL(nodeResource.getRedirectingURLLink())) {
                            isValidatedCurrent = false;
                            isValidated = false;
                            nodeResource.setErrRedirectingURLLink("Incorrect ");
                        }

                        if (!validate.isValidEdgeInput(nodeResource.getEdgesStr())) {
                            isValidatedCurrent = false;
                            isValidated = false;
                            nodeResource.setErrEdgesStr("Incorrect ");
                        } else {
                            String edgeSnos[] = nodeResource.getEdgesStr().split(",");
                            for (String edgeSno : edgeSnos) {
                                if (!edgeSno.trim().equals("")) {

                                    long edgeN = Long.parseLong(edgeSno);
                                    if (hashMapNodes.get(edgeN) == null) {
                                        isValidatedCurrent = false;
                                        isValidated = false;
                                        nodeResource.setErrEdgesStr("Incorrect ");
                                    }
                                }
                            }
                        }

                        if (!isValidatedCurrent) {
                            invalidSerialNos.add(nodeResource.getNodeID().toString());
                        } else {
                            if (nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_ROUTER.type())) {
                                fullImagePath = imagePath + "router-type.png";
                            } else if (nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_SERVER.type())) {
                                fullImagePath = imagePath + "server-type.png";
                            } else if (nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_NETWORKGROUP.type())) {
                                fullImagePath = imagePath + "networkgroup-type.png";
                            } else if (nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_DATABASE.type()) || nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_DATABASE_2.type())) {
                                fullImagePath = imagePath + "database-type.png";
                            } else if (nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_HUB.type())) {
                                fullImagePath = imagePath + "hub-type.png";
                            } else if (nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_PRINTER.type())) {
                                fullImagePath = imagePath + "printer-type.png";
                            } else if (nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_SWITCH.type()) || nodeResource.getType().equalsIgnoreCase(NODE_TYPE.TYPE_SWITCH_2.type())) {
                                fullImagePath = imagePath + "switch-type.png";
                            } else {
                                fullImagePath = imagePath + "cmputer-type.png";
                            }

                            nodeResource.getFill().setSrc(fullImagePath);
                            nodeResource.getAlert().setType(1);
                            nodeResource.getAlert().setDescription("");

                            double domX = (int) (Math.random() * 800);
                            double domY = (int) (Math.random() * 800);

                            double convasX = (int) (Math.random() * 800);
                            double convasY = (int) (Math.random() * 800);

                            nodeResource.setCanvasXValue(convasX);
                            nodeResource.setCanvasYValue(convasY);
                            nodeResource.setDomXValue(domX);
                            nodeResource.setDomYValue(domY);
                            nodeResource.setZoomScale(1.0D);
                        }

                    }

                }

//                for (NodeResource nodeResource : nodeListResource.getNodeResources()) {
//
//                    boolean isValidatedCurrent = true;
//                    if (isValidated && nodeResource.isEnabled()) {
//                        String edgeStr = nodeResource.getEdgesStr();
//
//                        String edgeSnos[] = edgeStr.split(",");
//                        for (String edgeSno : edgeSnos) {
//                            if (!edgeSno.trim().equals("")) {
//
//                                long edgeN = Long.parseLong(edgeSno);
//                                if (hashMap.get(edgeN) == null) {
//                                    isValidated = false;
//                                    nodeResource.setErrEdgesStr("Incorrect ");
//
//                                }
//                            }
//                        }
//                    }
//
//                }
            }

            if (isValidated) {
                List<EdgeResource> edgeResources = new ArrayList<>();
                for (NodeResource nodeResource : nodeListResource.getNodeResources()) {
                    if (nodeResource.isEnabled()) {
                        String edgeSnos[] = nodeResource.getEdgesStr().split(",");
                        for (String edgeSno : edgeSnos) {
                            if (!edgeSno.trim().equals("")) {
                                long toEdgeNodeID = Long.parseLong(edgeSno);
                                EdgeResource edgeResource = new EdgeResource();
                                edgeResource.setFromNodeResource(nodeResource);
                                edgeResource.setToNodeResource(hashMapNodes.get(toEdgeNodeID));
                                edgeResources.add(edgeResource);
                            }

                        }
                    }
                }
                EdgeListResource edgeListResource = new EdgeListResource();
                edgeListResource.setEdgeResources(edgeResources);

                NodeAndEdgeListResource nodeAndEdgeListResource = new NodeAndEdgeListResource();
                nodeAndEdgeListResource.setEdgeListResource(edgeListResource);
                nodeAndEdgeListResource.setNodeListResource(nodeListResource);

                //END OF TEST
//                NodeListResource readNodeListResource = nodeService.createAndUpdateNodes(nodeListResource);
                NodeListResource readNodeListResource = nodeService.createAndUpdateNodesAndEdges(nodeAndEdgeListResource).getNodeListResource();
                model.addObject("nodeListResource", readNodeListResource);
                model.setViewName("node_add_upload_csv_success");
                return model;
            }

            model.addObject("pageError", ApplicationMessageEnum.ERROR_RECORD_PROCESS.getMessage() + ", Please Check S.Nos :- " + String.join(",", invalidSerialNos));
            model.addObject("tableData", nodeListResource);

            /// END
            model.setViewName("node_add_upload_csv_edit");
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
}
