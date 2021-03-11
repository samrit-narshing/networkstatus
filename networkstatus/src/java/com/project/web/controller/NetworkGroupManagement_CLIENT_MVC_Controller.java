/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.web.controller;

import com.project.config.PropertiesConfig;
import com.project.dao.network.EdgeService;
import com.project.dao.network.NetworkGroupService;
import com.project.dao.network.NodeService;
import com.project.dao.user.DepartmentUserService;
import com.project.dao.user.UserService;
import com.project.exception.rest.BadRequestException;
import com.project.exception.rest.InternalServerException;
import com.project.exception.rest.ResourceInUsedException;
import com.project.exception.rest.UnAuthorizedException;
import com.project.model.network.EdgeInNetworkGroupListResource;
import com.project.model.network.EdgeInNetworkGroupResource;
import com.project.model.network.NetworkGroupListResource;
import com.project.model.network.NetworkGroupResource;
import com.project.model.network.NodeAndEdgeListInNetworkGroupResource;
import com.project.model.network.NodeAndEdgeListResource;
import com.project.model.network.NodeInNetworkGroupListResource;
import com.project.model.network.NodeInNetworkGroupResource;
import com.project.model.network.NodeListResource;
import com.project.model.network.NodeResource;
import com.project.model.network.RoleInNetworkGroupResource;
import com.project.model.user.RoleResource;
import com.project.model.user.RoletListResource;
import com.project.model.user.UserResource;
import com.project.util.ApplicationMessageEnum;
import com.project.util.DateConverter;
import com.project.util.Log4jUtil;
import com.project.util.Validate;
import com.project.util.searchcriteria.NetworkGroupSearchCriteria;
import com.project.util.searchcriteria.NetworkGroupUpdateCriteria;
import com.project.util.searchcriteria.NodeSearchCriteria;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
@RequestMapping(value = "/networkgroupman")
public class NetworkGroupManagement_CLIENT_MVC_Controller extends Base_Controller {

    final int maxRecordsToFetch = 20;

    @Autowired
    private NodeService nodeService;

    @Autowired
    private NetworkGroupService networkgroupService;

    @Autowired
    private EdgeService edgeService;

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentUserService departmentUserService;

    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;

    private String pageLink = "";

    @RequestMapping(value = "/networkgroup/add", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView addNodeDisplay(final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            Locale locale = new Locale("en", "US");
            NetworkGroupResource networkgroupResource = new NetworkGroupResource();
            model.addObject("networkgroupResource", networkgroupResource);
            model.setViewName("networkgroup_add");
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

    @RequestMapping(value = "/networkgroup/add/submit", method = {RequestMethod.POST})
    public ModelAndView addNodeProcess(@ModelAttribute("networkgroupResource") NetworkGroupResource networkGroupResource, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            model.addObject("networkGroupResource", networkGroupResource);
            model.setViewName("networkgroup_add");
            boolean isValidated = true;

            Validate validate = new Validate();

            if (validate.isEmptyString(networkGroupResource.getName()) || (!validate.isValidDefaultTextLength(networkGroupResource.getName()))) {
                isValidated = false;
                networkGroupResource.setErrName("Incorrect");
            }
//            else {
//                if (isNetworkgroupAlreadyExist(networkGroupResource.getName())) {
//                    isValidated = false;
//                    networkGroupResource.setErrName("Already exist");
//                }
//            }

            if (validate.isEmptyString(networkGroupResource.getDescription()) || (!validate.isValidDefaultTextLength(networkGroupResource.getDescription()))) {
                isValidated = false;
                networkGroupResource.setErrDescription("Incorrect");
            }

            if (isValidated) {
                networkGroupResource.setEnabled(false);
                NetworkGroupResource readCreatedResource = networkgroupService.createNetworkGroup(networkGroupResource);
                model.addObject("networkGroupResource", readCreatedResource);
                model.setViewName("networkgroup_add_success");
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

    @RequestMapping(value = "/networkgroup/search", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView listNetworkgroupInfo(@ModelAttribute("searchCriteria") NetworkGroupSearchCriteria searchCriteria, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            Locale locale = new Locale("en", "US");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
            long timeInMillis = Calendar.getInstance().getTimeInMillis();
            String initialDeviceDate = simpleDateFormat.format(Long.valueOf(timeInMillis));
            searchCriteria.setDateFrom(initialDeviceDate);
            searchCriteria.setDateTo(initialDeviceDate);

            model.addObject("searchCriteria", searchCriteria);
            model.setViewName("networkgroup_list");
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
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }

    }

    @RequestMapping(value = "/networkgroup/search/submit", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView listNetworkgroupInfoProcess(@ModelAttribute("searchCriteria") NetworkGroupSearchCriteria searchCriteria, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            searchCriteria.setLimitResult(maxRecordsToFetch);

            NetworkGroupListResource networkGroupListResource = networkgroupService.readNetworkGroupResourceBySearchCriteria(searchCriteria);

            model.addObject("totalPages", networkGroupListResource.getTotalPages());
            model.addObject("totalDocuments", networkGroupListResource.getTotalDocuments());
            pageLink = "eventsearch";
            model.addObject("pagelink", pageLink);
            model.addObject("networkGroupListResource", networkGroupListResource);
            model.addObject("searchCriteria", searchCriteria);
            model.setViewName("networkgroup_list");
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
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    @RequestMapping(value = "/networkgroup/delete/submit", method = {RequestMethod.POST})
    public ModelAndView deleteNetworkgroupProcess(@RequestParam(value = "id", required = true) Long id, final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            NetworkGroupResource readNodeResource = networkgroupService.deleteNetworkGroupByID(id);

//            NodeSearchCriteria searchCriteria = new NodeSearchCriteria();
//            searchCriteria.setId(0L);//for search all
//            searchCriteria.setStatusCode(3); // 3 for deleted
//            redirectAttributes.addFlashAttribute("searchCriteria", searchCriteria);
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/networkgroupman/networkgroup/search");

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
        } catch (ResourceInUsedException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            String message = ApplicationMessageEnum.ERROR_RESOUCES_IN_USE_FOR_DELETE.getMessage()
                    + "<br/>"
                    + "<br/>"
                    + "<br/>"
                    + "Cannot delete this record . This record may be already used in other records."
                    + "<br/>"
                    + "<br/>";

//            redirectAttributes.addFlashAttribute("pageError", message);
//            return new ModelAndView("redirect:" + "/result");
            redirectAttributes.addFlashAttribute("pageMessage", message);
            return new ModelAndView("redirect:" + "/networkgroupman/networkgroup/search");

        } catch (Exception e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    @RequestMapping(value = "/networkgroup/edit", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView editNetworkgroupDisplay(@RequestParam(value = "id", required = false) Long id, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            NetworkGroupResource readResource = networkgroupService.readNetworkGroupByID(id);

            //refilling default values
            RoletListResource roletListResource = departmentUserService.readRoles();
            List<RoleResource> roleResource = roletListResource.getRoles();
            for (RoleResource role : roleResource) {
                for (RoleInNetworkGroupResource userRole : readResource.getRoleInNetworkGroupResources()) {
                    if (role.getRoleID().equals(userRole.getRoleResource().getRoleID())) {
                        role.setSelected(true);
                        break;
                    }
                }
            }
            model.addObject("roleResource", roleResource);

            model.addObject("networkGroupResource", readResource);

            model.setViewName("networkgroup_edit");
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

    @RequestMapping(value = "/networkgroup/edit/submit", method = {RequestMethod.POST})
    public ModelAndView editNetworkgroupProcess(@RequestParam(value = "id", required = true) Long id, @ModelAttribute("networkgroupResource") NetworkGroupResource networkgroupResource, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            NetworkGroupResource readNetworkgroupResource = networkgroupService.readNetworkGroupByID(id);
            networkgroupResource.setNetworkGroupID(readNetworkgroupResource.getNetworkGroupID());
            networkgroupResource.setEntryDate(readNetworkgroupResource.getEntryDate());
            boolean isValidated = true;
            Validate validate = new Validate();

            if (validate.isEmptyString(networkgroupResource.getName()) || (!validate.isValidDefaultTextLength(networkgroupResource.getName()))) {
                isValidated = false;
                networkgroupResource.setErrName("Incorrect");
            }

            if (validate.isEmptyString(networkgroupResource.getDescription()) || (!validate.isValidDefaultTextLength(networkgroupResource.getDescription()))) {
                isValidated = false;
                networkgroupResource.setErrDescription("Incorrect");
            }

            if (networkgroupResource.getSelectedRoles() == null || networkgroupResource.getSelectedRoles().length == 0) {
                networkgroupResource.setSelectedRoles(new String[0]);
                isValidated = false;
                networkgroupResource.setErrRole("Role is not selected");

            }

            if (isValidated) {

                List<RoleInNetworkGroupResource> roleInNetworkGroupResources = new ArrayList<RoleInNetworkGroupResource>();
                for (String r : networkgroupResource.getSelectedRoles()) {
                    RoleInNetworkGroupResource inNetworkGroupResource = new RoleInNetworkGroupResource();
                    inNetworkGroupResource.setRoleInNetworkGroupID(null);
                    inNetworkGroupResource.setRoleResource(userService.readRolesByID(Long.parseLong(r)));
                    roleInNetworkGroupResources.add(inNetworkGroupResource);
//                    javax.swing.JOptionPane.showMessageDialog(null, 1);

                }
//javax.swing.JOptionPane.showMessageDialog(null, roleInNetworkGroupResources.size());
                networkgroupResource.setRoleInNetworkGroupListResources(roleInNetworkGroupResources);

                NetworkGroupResource updatedNetworkgroupResource = networkgroupService.updateNetworkGroup(id, networkgroupResource);
                model.addObject("networkGroupResource", updatedNetworkgroupResource);
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
            } else {
                model.addObject("networkGroupResource", networkgroupResource);
            }

            RoletListResource roletListResource = departmentUserService.readRoles();
            List<RoleResource> roleResource = roletListResource.getRoles();
            for (RoleResource role : roleResource) {
                for (String selectedRole : networkgroupResource.getSelectedRoles()) {
                    if (Long.valueOf(selectedRole).equals(role.getRoleID())) {
                        role.setSelected(true);
                        break;
                    }
                }
            }
            model.addObject("roleResource", roleResource);

            model.setViewName("networkgroup_edit");
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

    @RequestMapping(value = "/networkgroup/node/search", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView searchNodeForNetworkgroupDisplay(@RequestParam(value = "id", required = false) Long id, @RequestParam(value = "code", required = false) String code, @ModelAttribute("searchCriteria") NodeSearchCriteria searchCriteria, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            searchCriteria.setLimitResult(maxRecordsToFetch);
//            javax.swing.JOptionPane.showMessageDialog(null, id + " -- " + code);
//            searchCriteria.setNetworkGroupCode(updateCriteria.getNetworkGroupCode());
//            searchCriteria.setNetworkGroupID(updateCriteria.getNetworkGroupID());
//            NodeListResource nodeListResource = nodeService.readAllEnabledNodes();
            NodeListResource nodeListResource = nodeService.readAllEnabledNodesExculdingNodesInNetworkGroup(id);

            model.addObject("nodeListResource", nodeListResource);

            if (searchCriteria.getStatusCode() == null) {

            } else if (searchCriteria.getStatusCode() == 1) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_ADD_RECORD.getMessage());
            } else if (searchCriteria.getStatusCode() == 2) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
            } else if (searchCriteria.getStatusCode() == 3) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            }

            model.addObject("networkGroupID", id);
            model.addObject("networkGroupCode", code);
            model.addObject("searchCriteria", searchCriteria);

            model.setViewName("networkgroup_node_list");
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
    @RequestMapping(value = "/networkgroup/node/add/submit", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView addNodeInNetworkGroup(@RequestParam(value = "id", required = false) Long id, @RequestParam(value = "code", required = false) String code, @ModelAttribute("nodeListResource") NodeListResource nodeListResource, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            boolean isValidated = true;

            Validate validate = new Validate();
            HashMap<Long, NodeResource> hashMapNodes = new HashMap();
            List<String> invalidSerialNos = new ArrayList<>();
            NodeInNetworkGroupListResource nodeInNetworkGroupListResource = new NodeInNetworkGroupListResource();

//            HashMap<Long, NodeResource> hashMapNodes = new HashMap();
            if (nodeListResource.getNodeResources() != null) {

                for (NodeResource nodeResource : nodeListResource.getNodeResources()) {
                    if (nodeResource.isSelected()) {
//                        hashMap.put(nodeResource.getNodeID(), nodeResource.getLabel());
                        hashMapNodes.put(nodeResource.getNodeID(), nodeResource);
                    }
                }

//                javax.swing.JOptionPane.showMessageDialog(null, nodeListResource.getNodeResources().size());
                for (NodeResource nodeResource : nodeListResource.getNodeResources()) {

                    boolean isValidatedCurrent = true;

                    if (nodeResource.isSelected()) {

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
                            NodeInNetworkGroupResource nodeInNetworkGroupResource = new NodeInNetworkGroupResource();
                            nodeInNetworkGroupResource.setNodeResource(nodeResource);

                            double domX = (int) (Math.random() * 800);
                            double domY = (int) (Math.random() * 800);

                            double convasX = (int) (Math.random() * 800);
                            double convasY = (int) (Math.random() * 800);

                            nodeInNetworkGroupResource.setCanvasXValue(convasX);
                            nodeInNetworkGroupResource.setCanvasYValue(convasY);
                            nodeInNetworkGroupResource.setDomXValue(domX);
                            nodeInNetworkGroupResource.setDomYValue(domY);
                            nodeInNetworkGroupResource.setZoomScale(1.0D);
                            nodeInNetworkGroupResource.setSelected(true);
                            nodeInNetworkGroupResource.setEnabled(true);

//                        hashMapNodes.put(nodeResource.getNodeID(), nodeResource);
                            nodeInNetworkGroupListResource.getNodeInNetworkGroupResources().add(nodeInNetworkGroupResource);
                        }
                    }
                }

            }

//            javax.swing.JOptionPane.showMessageDialog(null, nodeInNetworkGroupListResource.getNodeInNetworkGroupResources().size());
            if (isValidated) {
//                List<EdgeResource> edgeResources = new ArrayList<>();
//                for (NodeResource nodeResource : nodeListResource.getNodeResources()) {
//                    if (nodeResource.isEnabled()) {
//                        String edgeSnos[] = nodeResource.getEdgesStr().split(",");
//                        for (String edgeSno : edgeSnos) {
//                            if (!edgeSno.trim().equals("")) {
//                                long toEdgeNodeID = Long.parseLong(edgeSno);
//                                EdgeResource edgeResource = new EdgeResource();
//                                edgeResource.setFromNodeResource(nodeResource);
//                                edgeResource.setToNodeResource(hashMapNodes.get(toEdgeNodeID));
//                                edgeResources.add(edgeResource);
//                            }
//
//                        }
//                    }
//                }
//                EdgeInNetworkGroupListResource edgeInNetworkGroupListResource = new EdgeInNetworkGroupListResource();
////                edgeInNetworkGroupListResource.setEdgeResources(edgeResources);
//
//                NodeAndEdgeListInNetworkGroupResource nodeAndEdgeListInNetworkGroupResource = new NodeAndEdgeListInNetworkGroupResource();
//                nodeAndEdgeListInNetworkGroupResource.setEdgeInNetworkGroupListResource(edgeInNetworkGroupListResource);
//                nodeAndEdgeListInNetworkGroupResource.setNodeInNetworkGroupListResource(nodeInNetworkGroupListResource);
//
//                //END OF TEST

                NodeAndEdgeListInNetworkGroupResource nodeAndEdgeListInNetworkGroupResource = new NodeAndEdgeListInNetworkGroupResource();
//                nodeAndEdgeListInNetworkGroupResource.setEdgeListResource(edgeListResource);
                nodeAndEdgeListInNetworkGroupResource.setNodeInNetworkGroupListResource(nodeInNetworkGroupListResource);

//                NetworkGroupResource networkGroupResource = networkgroupService.addAndUpdateNodes(id, nodeInNetworkGroupListResource);
                NetworkGroupResource networkGroupResource = networkgroupService.addAndUpdateNodesAndEdges(id, nodeAndEdgeListInNetworkGroupResource);

//                NetworkgroupResource readCreatedResource = networkgroupService.updateNetworkgroupForAddNodes(updateCriteria.getNetworkgroupID(), nodeInNetworkgroupResource);
                redirectAttributes.addAttribute("id", id);
                redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
                return new ModelAndView("redirect:" + "/networkgroupman/networkgroup/edit");

            }

            model.addObject("pageError", ApplicationMessageEnum.ERROR_RECORD_PROCESS.getMessage() + ", Please Check S.Nos :- " + String.join(",", invalidSerialNos));
            model.addObject("networkGroupID", id);
            model.addObject("networkGroupCode", code);
            model.setViewName("networkgroup_node_list");

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

    @RequestMapping(value = "/networkgroup/node/delete/submit", method = {RequestMethod.POST})
    public ModelAndView deleteNodeProcess(@RequestParam(value = "networkGroupId", required = true) Long networkGroupId, @RequestParam(value = "nodeInNetworkGroupId", required = true) Long nodeInNetworkGroupId, final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            NodeInNetworkGroupResource nodeInNetworkGroupResource = new NodeInNetworkGroupResource();
            nodeInNetworkGroupResource.setNodeInNetworkGroupID(nodeInNetworkGroupId);

            NodeInNetworkGroupListResource nodeInNetworkGroupListResource = new NodeInNetworkGroupListResource();
            nodeInNetworkGroupListResource.getNodeInNetworkGroupResources().add(nodeInNetworkGroupResource);

            NetworkGroupResource readCreatedResource = networkgroupService.deleteNodesInNetworkGroup(networkGroupId, nodeInNetworkGroupListResource);

            redirectAttributes.addAttribute("id", networkGroupId);
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/networkgroupman/networkgroup/edit");

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

    @RequestMapping(value = "/networkgroup/edge/add", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView addEdgeInNetworkGroup(@RequestParam(value = "networkGroupID", required = false) Long networkGroupID, @RequestParam(value = "networkGroupCode", required = false) String networkGroupCode, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            NetworkGroupResource networkGroupResource = networkgroupService.readNetworkGroupByID(networkGroupID);
            model.addObject("networkGroupResource", networkGroupResource);

            EdgeInNetworkGroupResource edgeInNetworkGroupResource = new EdgeInNetworkGroupResource();
            model.addObject("edgeInNetworkGroupResource", edgeInNetworkGroupResource);
            model.addObject("networkGroupID", networkGroupID);
            model.addObject("networkGroupCode", networkGroupCode);
            model.setViewName("networkgroup_edge_add");
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

    @RequestMapping(value = "/networkgroup/edge/add/submit", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView addEdgeInNetworkGroupProcess(@RequestParam(value = "networkGroupID", required = false) Long networkGroupID, @RequestParam(value = "networkGroupCode", required = false) String networkGroupCode, @ModelAttribute("edgeInNetworkGroupResource") EdgeInNetworkGroupResource edgeInNetworkGroupResource, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            model.setViewName("networkgroup_edge_add");
            model.addObject("edgeInNetworkGroupResource", edgeInNetworkGroupResource);

            NodeListResource readNodeListResource = nodeService.readAllEnabledNodes();
            List<NodeResource> nodeResources = readNodeListResource.getNodeResources();
            model.addObject("nodeResources", nodeResources);

            NetworkGroupResource networkGroupResource = networkgroupService.readNetworkGroupByID(networkGroupID);
            model.addObject("networkGroupResource", networkGroupResource);
            model.addObject("networkGroupID", networkGroupID);
            model.addObject("networkGroupCode", networkGroupCode);

            model.addObject("edgeInNetworkGroupResource", edgeInNetworkGroupResource);

            boolean isValidated = true;

            Validate validate = new Validate();

            if (edgeInNetworkGroupResource.getFromNodeInNetworkGroupResource().getNodeInNetworkGroupID() == 0L) {
                isValidated = false;
                edgeInNetworkGroupResource.setErrFromNode("Incorrect");
            }

            if (edgeInNetworkGroupResource.getToNodeInNetworkGroupResource().getNodeInNetworkGroupID() == 0L) {
                isValidated = false;
                edgeInNetworkGroupResource.setErrToNode("Incorrect");
            }

            if (isEdgeInNetworkGroupAlreadyExist(networkGroupID, edgeInNetworkGroupResource.getFromNodeInNetworkGroupResource().getNodeInNetworkGroupID(), edgeInNetworkGroupResource.getToNodeInNetworkGroupResource().getNodeInNetworkGroupID())) {
                isValidated = false;
                edgeInNetworkGroupResource.setErrNode("Node Link is Already exist");
            }
//              isValidated = false;
//                edgeInNetworkGroupResource.setErrNode("Node Link is Already exist");
            if (isValidated) {

//                javax.swing.JOptionPane.showMessageDialog(null, edgeInNetworkGroupResource.getFromNodeInNetworkGroupResource().getNodeInNetworkGroupID() +" " + edgeInNetworkGroupResource.getToNodeInNetworkGroupResource().getNodeInNetworkGroupID());
//                javax.swing.JOptionPane.showMessageDialog(null, edgeInNetworkGroupResource.getArrows() +" " + edgeInNetworkGroupResource.isDashes()+" "+ edgeInNetworkGroupResource.isSelected());
                edgeInNetworkGroupResource.setEnabled(true);
//                EdgeInNetworkGroupResource readResource = edgeService.createEdge(edgeInNetworkGroupResource);
//                model.addObject("edgeResource", readResource);

                EdgeInNetworkGroupListResource edgeInNetworkGroupListResource = new EdgeInNetworkGroupListResource();
                edgeInNetworkGroupListResource.getEdgeInNetworkGroupResources().add(edgeInNetworkGroupResource);
                NetworkGroupResource readCreatedResource = networkgroupService.addAndUpdateEdges(networkGroupID, edgeInNetworkGroupListResource);
                redirectAttributes.addAttribute("id", networkGroupID);
                redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
                return new ModelAndView("redirect:" + "/networkgroupman/networkgroup/edit");

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

    @RequestMapping(value = "/networkgroup/edge/delete/submit", method = {RequestMethod.POST})
    public ModelAndView deleteEdgeProcess(@RequestParam(value = "networkGroupId", required = true) Long networkGroupId, @RequestParam(value = "edgeInNetworkGroupId", required = true) Long edgeInNetworkGroupId, final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            EdgeInNetworkGroupResource edgeInNetworkGroupResource = new EdgeInNetworkGroupResource();
            edgeInNetworkGroupResource.setEdgeInNetworkGroupID(edgeInNetworkGroupId);

            EdgeInNetworkGroupListResource nodeInNetworkGroupListResource = new EdgeInNetworkGroupListResource();
            nodeInNetworkGroupListResource.getEdgeInNetworkGroupResources().add(edgeInNetworkGroupResource);

            NetworkGroupResource readCreatedResource = networkgroupService.deleteEdgesInNetworkGroup(networkGroupId, nodeInNetworkGroupListResource);

            redirectAttributes.addAttribute("id", networkGroupId);
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/networkgroupman/networkgroup/edit");

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

    @RequestMapping(value = "/networkgroup/edge/edit", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView editEdgeInNetworkGroupUpdate(@RequestParam(value = "networkGroupId", required = false) Long networkGroupId, @RequestParam(value = "networkGroupCode", required = false) String networkGroupCode, @RequestParam(value = "edgeInNetworkGroupId", required = false) Long edgeInNetworkGroupId, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            NetworkGroupResource networkGroupResource = networkgroupService.readNetworkGroupByID(networkGroupId);
            model.addObject("networkGroupResource", networkGroupResource);

            EdgeInNetworkGroupResource edgeInNetworkGroupResource = networkgroupService.readEdgeInNetworkGroupResourceByID(edgeInNetworkGroupId);
            model.addObject("edgeInNetworkGroupResource", edgeInNetworkGroupResource);
            model.addObject("networkGroupId", networkGroupId);
            model.addObject("networkGroupCode", networkGroupCode);
            model.setViewName("networkgroup_edge_edit");
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

    @RequestMapping(value = "/networkgroup/edge/edit/submit", method = {RequestMethod.POST})
    public ModelAndView editEdgeInNetworkGroupProcess(@RequestParam(value = "networkGroupId", required = false) Long networkGroupId, @RequestParam(value = "networkGroupCode", required = false) String networkGroupCode, @RequestParam(value = "edgeInNetworkGroupId", required = false) Long edgeInNetworkGroupId, @ModelAttribute("edgeInNetworkGroupResource") EdgeInNetworkGroupResource edgeInNetworkGroupResource, final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            model.setViewName("networkgroup_edge_edit");

            NetworkGroupResource networkGroupResource = networkgroupService.readNetworkGroupByID(networkGroupId);
            model.addObject("networkGroupResource", networkGroupResource);
            model.addObject("networkGroupId", networkGroupId);
            model.addObject("networkGroupCode", networkGroupCode);

            EdgeInNetworkGroupResource readEdgeInNetworkGroupResource = networkgroupService.readEdgeInNetworkGroupResourceByID(edgeInNetworkGroupId);
            edgeInNetworkGroupResource.setFromNodeInNetworkGroupResource(readEdgeInNetworkGroupResource.getFromNodeInNetworkGroupResource());
            edgeInNetworkGroupResource.setToNodeInNetworkGroupResource(readEdgeInNetworkGroupResource.getToNodeInNetworkGroupResource());
            edgeInNetworkGroupResource.setEdgeInNetworkGroupID(edgeInNetworkGroupId);
            model.addObject("edgeInNetworkGroupResource", edgeInNetworkGroupResource);

            model.addObject("networkGroupCode", networkGroupCode);
            model.setViewName("networkgroup_edge_edit");

            boolean isValidated = true;

            Validate validate = new Validate();

//            if (isEdgeAlreadyExist(edgeInNetworkGroupResource.getFromNodeInNetworkGroupResource().getNodeInNetworkGroupID(), edgeInNetworkGroupResource.getToNodeInNetworkGroupResource().getNodeInNetworkGroupID())) {
//                isValidated = false;
//                edgeInNetworkGroupResource.setErrNode("Node Link is Already exist");
//            }
            if (isValidated) {

                edgeInNetworkGroupResource.setEnabled(true);
                edgeInNetworkGroupResource.setEdgeInNetworkGroupID(edgeInNetworkGroupId);
                EdgeInNetworkGroupListResource edgeInNetworkGroupListResource = new EdgeInNetworkGroupListResource();
                edgeInNetworkGroupListResource.getEdgeInNetworkGroupResources().add(edgeInNetworkGroupResource);
                NetworkGroupResource readCreatedResource = networkgroupService.updateEdgesInNetworkGroup(networkGroupId, edgeInNetworkGroupListResource);
                redirectAttributes.addAttribute("id", networkGroupId);
                redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
                return new ModelAndView("redirect:" + "/networkgroupman/networkgroup/edit");

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

    private boolean isEdgeInNetworkGroupAlreadyExist(Long networkGroupID, Long nodeInNetworkGroupId1, Long nodeInNetworkGroupId2) throws Exception {
//        javax.swing.JOptionPane.showMessageDialog(null, networkGroupID + "   ---     " + nodeID1 + "---- " + nodeID2);

        NetworkGroupResource userResource = networkgroupService.readNetworkGroupIfNodesAreAlreadyExists(networkGroupID, nodeInNetworkGroupId1, nodeInNetworkGroupId2);
        return userResource != null;
    }

    @RequestMapping(value = "/networkgroup/edge/clear_and_import_from_root/submit", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView updateNetworkGroupToAddEdgeInNetworkGroupsByFetchingFromRootNodeRelationShip(@RequestParam(value = "networkGroupID", required = false) Long networkGroupID, @RequestParam(value = "networkGroupCode", required = false) String networkGroupCode, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            NetworkGroupResource networkGroupResource = networkgroupService.clearAndUpdateNetworkGroupForAddEdges_NEW(networkGroupID);
            redirectAttributes.addAttribute("id", networkGroupID);
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/networkgroupman/networkgroup/edit");
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

    //    private boolean isNetworkgroupAlreadyExist(String code) throws Exception {
//        NetworkgroupResource userResource = networkgroupService.readNetworkgroupResourceByCode(code);
//        return userResource != null;
//    }
//
//    @RequestMapping(value = "/networkgroup/view", method = {RequestMethod.POST, RequestMethod.GET})
//    public ModelAndView viewNetworkgroupDisplay(@RequestParam(value = "id", required = false) Long id, final RedirectAttributes redirectAttributes) {
//        try {
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//
//            NetworkgroupResource readCreatedResource = networkgroupService.readNetworkgroupByID(id);
//
//            readCreatedResource.setDateExpiration(DateConverter.getConvertedUnixDateToStringFormat1(readCreatedResource.getExpiryDate()));
//
//            model.addObject("networkgroupResource", readCreatedResource);
//
//            model.setViewName("networkgroup_view");
//            return model;
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//    }
//    @RequestMapping(value = "/networkgroup/node/add", method = {RequestMethod.POST})
//    public ModelAndView selectNodeForNetworkgroupDisplay(@RequestParam(value = "id", required = true) Long id, @ModelAttribute("updateCriteria") NetworkgroupUpdateCriteria updateCriteria, final RedirectAttributes redirectAttributes) {
//        try {
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//
//            NodeResource nodeResource = nodeService.readNodeByID(id);
//            model.addObject("nodeResource", nodeResource);
//            model.addObject("updateCriteria", updateCriteria);
//            //refilling default values
//            RoletListResource roletListResource = nodeService.readRoles();
//            List<RoleResource> roleResource = roletListResource.getRoles();
//            for (RoleResource role : roleResource) {
//                for (RoleResource userRole : nodeResource.getRoles()) {
//                    if (role.getRoleID().equals(userRole.getRoleID())) {
//                        role.setSelected(true);
//                        break;
//                    }
//                }
//            }
//            model.addObject("roleResource", roleResource);
//
//            if (nodeResource.getAccountExpiration().equalsIgnoreCase("Never Expires")) {
//                Locale locale = new Locale("en", "US");
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
//                long timeInMillis = Calendar.getInstance().getTimeInMillis();
//                String initialDeviceDate = simpleDateFormat.format(timeInMillis);
//                nodeResource.setAccountExpiration(initialDeviceDate);
//                nodeResource.setNeverExpire(true);
//            }
//
//            model.setViewName("networkgroup_node_add");
//            return model;
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//    }
//
//    @RequestMapping(value = "/networkgroup/node/add/submit", method = {RequestMethod.POST})
//    public ModelAndView addNodeProcess(@ModelAttribute("updateCriteria") NetworkgroupUpdateCriteria updateCriteria, @ModelAttribute("nodeInNetworkgroupResource") NodeInNetworkgroupResource nodeInNetworkgroupResource, final RedirectAttributes redirectAttributes) {
//        try {
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//
//            boolean isValidated = true;
//            Validate validate = new Validate();
//
//            if (validate.isEmptyString(nodeInNetworkgroupResource.getDescription()) || (!validate.isValidDefaultTextLength(nodeInNetworkgroupResource.getDescription()))) {
//                isValidated = false;
//                nodeInNetworkgroupResource.setErrDescription("Incorrect");
//            }
//
//            if (isValidated) {
//                NetworkgroupResource readCreatedResource = networkgroupService.updateNetworkgroupForAddNodes(updateCriteria.getNetworkgroupID(), nodeInNetworkgroupResource);
//
////                readCreatedResource.setDateExpiration(DateConverter.getConvertedUnixDateToStringFormat1(readCreatedResource.getExpiryDate()));
////                model.addObject("networkgroupResource", readCreatedResource);
////                model.setViewName("networkgroup_edit");
////                return model;
////                //
//                redirectAttributes.addAttribute("id", updateCriteria.getNetworkgroupID());
//                redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
//                return new ModelAndView("redirect:" + "/networkgroupman/networkgroup/edit");
//            }
//
//            NodeResource nodeResource = nodeService.readNodeByID(nodeInNetworkgroupResource.getNodeResource().getUserID());
//
//            //refilling default values
//            RoletListResource roletListResource = nodeService.readRoles();
//            List<RoleResource> roleResource = roletListResource.getRoles();
//            for (RoleResource role : roleResource) {
//                for (RoleResource userRole : nodeResource.getRoles()) {
//                    if (role.getRoleID().equals(userRole.getRoleID())) {
//                        role.setSelected(true);
//                        break;
//                    }
//                }
//            }
//            model.addObject("roleResource", roleResource);
//
//            if (nodeResource.getAccountExpiration().equalsIgnoreCase("Never Expires")) {
//                Locale locale = new Locale("en", "US");
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
//                long timeInMillis = Calendar.getInstance().getTimeInMillis();
//                String initialDeviceDate = simpleDateFormat.format(timeInMillis);
//                nodeResource.setAccountExpiration(initialDeviceDate);
//                nodeResource.setNeverExpire(true);
//            }
//
//            model.addObject("nodeResource", nodeResource);
//            model.addObject("updateCriteria", updateCriteria);
//            model.addObject("nodeInNetworkgroupResource", nodeInNetworkgroupResource);
//            model.setViewName("networkgroup_node_add");
//            return model;
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//    }
//
//
//    @RequestMapping(value = "/networkgroup/node/edit", method = {RequestMethod.POST})
//    public ModelAndView editNodeInNetworkgroupProcess(@RequestParam(value = "id", required = true) Long id, @ModelAttribute("updateCriteria") NetworkgroupUpdateCriteria updateCriteria, final RedirectAttributes redirectAttributes) {
//        try {
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//
//            NodeInNetworkgroupResource nodeInNetworkgroupResource = new NodeInNetworkgroupResource();
//            NetworkgroupResource readCreatedResource = networkgroupService.readNetworkgroupByID(updateCriteria.getNetworkgroupID());
//
//            for (NodeInNetworkgroupResource localNetworkgroupResource : readCreatedResource.getNodeInNetworkgroupResources()) {
//
//                if (Objects.equals(localNetworkgroupResource.getNodeInNetworkgroupID(), id)) {
//                    nodeInNetworkgroupResource = localNetworkgroupResource;
//                }
//            }
//
//            NodeResource nodeResource = nodeInNetworkgroupResource.getNodeResource();
//
//            //refilling default values
//            RoletListResource roletListResource = nodeService.readRoles();
//            List<RoleResource> roleResource = roletListResource.getRoles();
//            for (RoleResource role : roleResource) {
//                for (RoleResource userRole : nodeResource.getRoles()) {
//                    if (role.getRoleID().equals(userRole.getRoleID())) {
//                        role.setSelected(true);
//                        break;
//                    }
//                }
//            }
//            model.addObject("roleResource", roleResource);
//
//            if (nodeResource.getAccountExpiration().equalsIgnoreCase("Never Expires")) {
//                Locale locale = new Locale("en", "US");
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
//                long timeInMillis = Calendar.getInstance().getTimeInMillis();
//                String initialDeviceDate = simpleDateFormat.format(timeInMillis);
//                nodeResource.setAccountExpiration(initialDeviceDate);
//                nodeResource.setNeverExpire(true);
//            }
//            model.addObject("nodeResource", nodeResource);
//            model.addObject("updateCriteria", updateCriteria);
//            model.addObject("nodeInNetworkgroupResource", nodeInNetworkgroupResource);
//            model.setViewName("networkgroup_node_edit");
//            return model;
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//    }
//
//    @RequestMapping(value = "/networkgroup/node/edit/submit", method = {RequestMethod.POST})
//    public ModelAndView editNodeProcess(@ModelAttribute("updateCriteria") NetworkgroupUpdateCriteria updateCriteria, @ModelAttribute("nodeInNetworkgroupResource") NodeInNetworkgroupResource nodeInNetworkgroupResource, final RedirectAttributes redirectAttributes) {
//        try {
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//
//            boolean isValidated = true;
//            Validate validate = new Validate();
//
//            if (validate.isEmptyString(nodeInNetworkgroupResource.getDescription()) || (!validate.isValidDefaultTextLength(nodeInNetworkgroupResource.getDescription()))) {
//                isValidated = false;
//                nodeInNetworkgroupResource.setErrDescription("Incorrect");
//            }
//
//            if (isValidated) {
//                NetworkgroupResource readCreatedResource = networkgroupService.updateNetworkgroupForEditNodes(updateCriteria.getNetworkgroupID(), nodeInNetworkgroupResource);
//
////                readCreatedResource.setDateExpiration(DateConverter.getConvertedUnixDateToStringFormat1(readCreatedResource.getExpiryDate()));
////                model.addObject("networkgroupResource", readCreatedResource);
////                model.setViewName("networkgroup_edit");
////                return model;
////                //
//                redirectAttributes.addAttribute("id", updateCriteria.getNetworkgroupID());
//                redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
//                return new ModelAndView("redirect:" + "/networkgroupman/networkgroup/edit");
//            }
//
//            NodeResource nodeResource = nodeService.readNodeByID(nodeInNetworkgroupResource.getNodeResource().getUserID());
//
//            //refilling default values
//            RoletListResource roletListResource = nodeService.readRoles();
//            List<RoleResource> roleResource = roletListResource.getRoles();
//            for (RoleResource role : roleResource) {
//                for (RoleResource userRole : nodeResource.getRoles()) {
//                    if (role.getRoleID().equals(userRole.getRoleID())) {
//                        role.setSelected(true);
//                        break;
//                    }
//                }
//            }
//            model.addObject("roleResource", roleResource);
//
//            if (nodeResource.getAccountExpiration().equalsIgnoreCase("Never Expires")) {
//                Locale locale = new Locale("en", "US");
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
//                long timeInMillis = Calendar.getInstance().getTimeInMillis();
//                String initialDeviceDate = simpleDateFormat.format(timeInMillis);
//                nodeResource.setAccountExpiration(initialDeviceDate);
//                nodeResource.setNeverExpire(true);
//            }
//
//            model.addObject("nodeResource", nodeResource);
//            model.addObject("updateCriteria", updateCriteria);
//            model.addObject("nodeInNetworkgroupResource", nodeInNetworkgroupResource);
//            model.setViewName("networkgroup_node_edit");
//            return model;
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//    }
//
//    @RequestMapping(value = "/networkgroup/geolocation/search", method = {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView searchGeoLocationForNetworkgroupDisplay(@ModelAttribute("updateCriteria") NetworkgroupUpdateCriteria updateCriteria, @ModelAttribute("searchCriteria") GeoLocationSearchCriteria searchCriteria, final RedirectAttributes redirectAttributes) {
//        try {
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//            searchCriteria.setType("STATIC");
//            searchCriteria.setLimitResult(maxRecordsToFetch);
//
//            GeoLocationListResource geoLocationListResource = geoLocationService.readGeoLocationBySearchCriteria(searchCriteria);
//            if (searchCriteria.getStatusCode() == null) {
//
//            } else if (searchCriteria.getStatusCode() == 1) {
//                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_ADD_RECORD.getMessage());
//            } else if (searchCriteria.getStatusCode() == 2) {
//                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
//            } else if (searchCriteria.getStatusCode() == 4) {
//                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_SYNC_RECORD.getMessage());
//            } else if (searchCriteria.getStatusCode() == 5) {
//                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_SEARCH_RECORD.getMessage());
//            }
//            model.addObject("geoLocationListResource", geoLocationListResource);
//
//            model.addObject("updateCriteria", updateCriteria);
//            model.addObject("searchCriteria", searchCriteria);
//
//            model.setViewName("networkgroup_geolocation_list");
//            return model;
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//    }
//
//    @RequestMapping(value = "/networkgroup/geolocation/add", method = {RequestMethod.POST})
//    public ModelAndView selectGeoLocationForNetworkgroupDisplay(@RequestParam(value = "id", required = true) Long id, @ModelAttribute("updateCriteria") NetworkgroupUpdateCriteria updateCriteria, @ModelAttribute("geoLocationInNetworkgroupResource") GeoLocationInNetworkgroupResource geoLocationInNetworkgroupResource, final RedirectAttributes redirectAttributes) {
//        try {
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//
//            System.out.println("---" + id);
//
//            GeoLocationResource responseGeoLocationResource = geoLocationService.readGeoLocationID(id);
//
//            responseGeoLocationResource.setLatitudeStr(responseGeoLocationResource.getLatitude().toString());
//            responseGeoLocationResource.setLongitudeStr(responseGeoLocationResource.getLongitude().toString());
//
//            Locale locale = new Locale("en", "US");
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
//            long timeInMillis = Calendar.getInstance().getTimeInMillis();
//            String initialDeviceDate = simpleDateFormat.format(Long.valueOf(timeInMillis));
//            geoLocationInNetworkgroupResource.setJourneyDateStr(initialDeviceDate);
//            geoLocationInNetworkgroupResource.setJourneyHour(23);
//            geoLocationInNetworkgroupResource.setJourneyMinute(59);
//
//            model.addObject("geoLocationInNetworkgroupResource", geoLocationInNetworkgroupResource);
//            model.addObject("geoLocationResource", responseGeoLocationResource);
//            model.setViewName("networkgroup_geolocation_add");
//
//            return model;
//
////            model.setViewName("networkgroup_node_add");
////            return model;
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//    }
//
//    @RequestMapping(value = "/networkgroup/geolocation/add/submit", method = {RequestMethod.POST})
//    public ModelAndView addGeoLocationForNetworkgroupProcess(@ModelAttribute("updateCriteria") NetworkgroupUpdateCriteria updateCriteria, @ModelAttribute("geoLocationInNetworkgroupResource") GeoLocationInNetworkgroupResource geoLocationInNetworkgroupResource, final RedirectAttributes redirectAttributes) {
//        try {
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//
//            boolean isValidated = true;
//            Validate validate = new Validate();
//
//            if (validate.isEmptyString(geoLocationInNetworkgroupResource.getDescription()) || (!validate.isValidDefaultTextLength(geoLocationInNetworkgroupResource.getDescription()))) {
//                isValidated = false;
//                geoLocationInNetworkgroupResource.setErrDescription("Incorrect");
//            }
//
//            if (isValidated) {
//                NetworkgroupResource readCreatedResource = networkgroupService.updateNetworkgroupForAddGeoLocation(updateCriteria.getNetworkgroupID(), geoLocationInNetworkgroupResource);
//
//                redirectAttributes.addAttribute("id", updateCriteria.getNetworkgroupID());
//                redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
//                return new ModelAndView("redirect:" + "/networkgroupman/networkgroup/edit");
//            }
//
//            GeoLocationResource responseGeoLocationResource = geoLocationService.readGeoLocationID(geoLocationInNetworkgroupResource.getGeoLocationResource().getTableId());
//
//            responseGeoLocationResource.setLatitudeStr(responseGeoLocationResource.getLatitude().toString());
//            responseGeoLocationResource.setLongitudeStr(responseGeoLocationResource.getLongitude().toString());
//
//            model.addObject("geoLocationInNetworkgroupResource", geoLocationInNetworkgroupResource);
//            model.addObject("geoLocationResource", responseGeoLocationResource);
//            model.addObject("updateCriteria", updateCriteria);
//            model.setViewName("networkgroup_geolocation_add");
//
//            return model;
//
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//    }
//
//
//
//    @RequestMapping(value = "/networkgroup/edge/search", method = {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView searchEdgeForNetworkgroupDisplay(@ModelAttribute("updateCriteria") NetworkgroupUpdateCriteria updateCriteria, @ModelAttribute("searchCriteria") EdgeSearchCriteria searchCriteria, final RedirectAttributes redirectAttributes) {
//        try {
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//
//            searchCriteria.setLimitResult(maxRecordsToFetch);
//
//            searchCriteria.setNetworkgroupCode(updateCriteria.getNetworkgroupCode());
//            searchCriteria.setNetworkgroupID(updateCriteria.getNetworkgroupID());
//            EdgeListResource edgeListResource = edgeService.readEdgesForNetworkgroupBySearchCriteria(searchCriteria);
//            model.addObject("edgeListResource", edgeListResource);
//
//            if (searchCriteria.getStatusCode() == null) {
//
//            } else if (searchCriteria.getStatusCode() == 1) {
//                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_ADD_RECORD.getMessage());
//            } else if (searchCriteria.getStatusCode() == 2) {
//                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
//            } else if (searchCriteria.getStatusCode() == 3) {
//                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
//            }
//
//            model.addObject("updateCriteria", updateCriteria);
//            model.addObject("searchCriteria", searchCriteria);
//
//            model.setViewName("networkgroup_edge_list");
//            return model;
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//    }
//
//    @RequestMapping(value = "/networkgroup/edge/add", method = {RequestMethod.POST})
//    public ModelAndView selectEdgeForNetworkgroupDisplay(@RequestParam(value = "id", required = true) Long id, @ModelAttribute("updateCriteria") NetworkgroupUpdateCriteria updateCriteria, final RedirectAttributes redirectAttributes) {
//        try {
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//
//            EdgeResource edgeResource = edgeService.readEdgeByID(id);
//            model.addObject("edgeResource", edgeResource);
//            model.addObject("updateCriteria", updateCriteria);
//            //refilling default values
//            RoletListResource roletListResource = nodeService.readRoles();
//            List<RoleResource> roleResource = roletListResource.getRoles();
//            for (RoleResource role : roleResource) {
//                for (RoleResource userRole : edgeResource.getRoles()) {
//                    if (role.getRoleID().equals(userRole.getRoleID())) {
//                        role.setSelected(true);
//                        break;
//                    }
//                }
//            }
//            model.addObject("roleResource", roleResource);
//
//            if (edgeResource.getAccountExpiration().equalsIgnoreCase("Never Expires")) {
//                Locale locale = new Locale("en", "US");
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
//                long timeInMillis = Calendar.getInstance().getTimeInMillis();
//                String initialDeviceDate = simpleDateFormat.format(timeInMillis);
//                edgeResource.setAccountExpiration(initialDeviceDate);
//                edgeResource.setNeverExpire(true);
//            }
//
//            model.setViewName("networkgroup_edge_add");
//            return model;
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//    }
//
//    @RequestMapping(value = "/networkgroup/edge/add/submit", method = {RequestMethod.POST})
//    public ModelAndView addNodeProcess(@ModelAttribute("updateCriteria") NetworkgroupUpdateCriteria updateCriteria, @ModelAttribute("edgeInNetworkgroupResource") EdgeInNetworkgroupResource edgeInNetworkgroupResource, final RedirectAttributes redirectAttributes) {
//        try {
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//
//            boolean isValidated = true;
//            Validate validate = new Validate();
//
//            if (validate.isEmptyString(edgeInNetworkgroupResource.getDescription()) || (!validate.isValidDefaultTextLength(edgeInNetworkgroupResource.getDescription()))) {
//                isValidated = false;
//                edgeInNetworkgroupResource.setErrDescription("Incorrect");
//            }
//
//            if (isValidated) {
//                NetworkgroupResource readCreatedResource = networkgroupService.updateNetworkgroupForAddEdge(updateCriteria.getNetworkgroupID(), edgeInNetworkgroupResource);
//
////                readCreatedResource.setDateExpiration(DateConverter.getConvertedUnixDateToStringFormat1(readCreatedResource.getExpiryDate()));
////                model.addObject("networkgroupResource", readCreatedResource);
////                model.setViewName("networkgroup_edit");
////                return model;
////                //
//                redirectAttributes.addAttribute("id", updateCriteria.getNetworkgroupID());
//                redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
//                return new ModelAndView("redirect:" + "/networkgroupman/networkgroup/edit");
//            }
//
//            EdgeResource edgeResource = edgeService.readEdgeByID(edgeInNetworkgroupResource.getEdgeResource().getUserID());
//
//            //refilling default values
//            RoletListResource roletListResource = nodeService.readRoles();
//            List<RoleResource> roleResource = roletListResource.getRoles();
//            for (RoleResource role : roleResource) {
//                for (RoleResource userRole : edgeResource.getRoles()) {
//                    if (role.getRoleID().equals(userRole.getRoleID())) {
//                        role.setSelected(true);
//                        break;
//                    }
//                }
//            }
//            model.addObject("roleResource", roleResource);
//
//            if (edgeResource.getAccountExpiration().equalsIgnoreCase("Never Expires")) {
//                Locale locale = new Locale("en", "US");
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
//                long timeInMillis = Calendar.getInstance().getTimeInMillis();
//                String initialDeviceDate = simpleDateFormat.format(timeInMillis);
//                edgeResource.setAccountExpiration(initialDeviceDate);
//                edgeResource.setNeverExpire(true);
//            }
//
//            model.addObject("edgeResource", edgeResource);
//            model.addObject("updateCriteria", updateCriteria);
//            model.addObject("edgeInNetworkgroupResource", edgeInNetworkgroupResource);
//            model.setViewName("networkgroup_edge_add");
//            return model;
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//    }
//
//    @RequestMapping(value = "/networkgroup/edge/delete/submit", method = {RequestMethod.POST})
//    public ModelAndView deleteEdgeProcess(@RequestParam(value = "id", required = true) Long id, @ModelAttribute("updateCriteria") NetworkgroupUpdateCriteria updateCriteria, final RedirectAttributes redirectAttributes) {
//        try {
//
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
////            NodeResource readNodeResource = nodeService.deleteNodeByID(id);
//            NetworkgroupResource readCreatedResource = networkgroupService.deleteEdgeInNetworkgroupByID(updateCriteria.getNetworkgroupID(), id);
//            redirectAttributes.addAttribute("id", updateCriteria.getNetworkgroupID());
//            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
//            return new ModelAndView("redirect:" + "/networkgroupman/networkgroup/edit");
//
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//    }
//
//    @RequestMapping(value = "/networkgroup/edge/edit", method = {RequestMethod.POST})
//    public ModelAndView editEdgeInNetworkgroupProcess(@RequestParam(value = "id", required = true) Long id, @ModelAttribute("updateCriteria") NetworkgroupUpdateCriteria updateCriteria, final RedirectAttributes redirectAttributes) {
//        try {
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//
//            EdgeInNetworkgroupResource edgeInNetworkgroupResource = new EdgeInNetworkgroupResource();
//            NetworkgroupResource readCreatedResource = networkgroupService.readNetworkgroupByID(updateCriteria.getNetworkgroupID());
//
//            for (EdgeInNetworkgroupResource localNetworkgroupResource : readCreatedResource.getEdgeInNetworkgroupResources()) {
//
//                if (Objects.equals(localNetworkgroupResource.getEdgeInNetworkgroupID(), id)) {
//                    edgeInNetworkgroupResource = localNetworkgroupResource;
//                }
//            }
//
//            EdgeResource edgeResource = edgeInNetworkgroupResource.getEdgeResource();
//
//            //refilling default values
//            RoletListResource roletListResource = nodeService.readRoles();
//            List<RoleResource> roleResource = roletListResource.getRoles();
//            for (RoleResource role : roleResource) {
//                for (RoleResource userRole : edgeResource.getRoles()) {
//                    if (role.getRoleID().equals(userRole.getRoleID())) {
//                        role.setSelected(true);
//                        break;
//                    }
//                }
//            }
//            model.addObject("roleResource", roleResource);
//
//            if (edgeResource.getAccountExpiration().equalsIgnoreCase("Never Expires")) {
//                Locale locale = new Locale("en", "US");
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
//                long timeInMillis = Calendar.getInstance().getTimeInMillis();
//                String initialDeviceDate = simpleDateFormat.format(timeInMillis);
//                edgeResource.setAccountExpiration(initialDeviceDate);
//                edgeResource.setNeverExpire(true);
//            }
//            model.addObject("edgeResource", edgeResource);
//            model.addObject("updateCriteria", updateCriteria);
//            model.addObject("edgeInNetworkgroupResource", edgeInNetworkgroupResource);
//            model.setViewName("networkgroup_edge_edit");
//            return model;
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//    }
//
//    @RequestMapping(value = "/networkgroup/edge/edit/submit", method = {RequestMethod.POST})
//    public ModelAndView editEdgeProcess(@ModelAttribute("updateCriteria") NetworkgroupUpdateCriteria updateCriteria, @ModelAttribute("edgeInNetworkgroupResource") EdgeInNetworkgroupResource edgeInNetworkgroupResource, final RedirectAttributes redirectAttributes) {
//        try {
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//
//            boolean isValidated = true;
//            Validate validate = new Validate();
//
//            if (validate.isEmptyString(edgeInNetworkgroupResource.getDescription()) || (!validate.isValidDefaultTextLength(edgeInNetworkgroupResource.getDescription()))) {
//                isValidated = false;
//                edgeInNetworkgroupResource.setErrDescription("Incorrect");
//            }
//
//            if (isValidated) {
//                NetworkgroupResource readCreatedResource = networkgroupService.updateNetworkgroupForEditEdge(updateCriteria.getNetworkgroupID(), edgeInNetworkgroupResource);
//
////                readCreatedResource.setDateExpiration(DateConverter.getConvertedUnixDateToStringFormat1(readCreatedResource.getExpiryDate()));
////                model.addObject("networkgroupResource", readCreatedResource);
////                model.setViewName("networkgroup_edit");
////                return model;
////                //
//                redirectAttributes.addAttribute("id", updateCriteria.getNetworkgroupID());
//                redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
//                return new ModelAndView("redirect:" + "/networkgroupman/networkgroup/edit");
//            }
//
//            NodeResource nodeResource = nodeService.readNodeByID(edgeInNetworkgroupResource.getEdgeResource().getUserID());
//
//            //refilling default values
//            RoletListResource roletListResource = nodeService.readRoles();
//            List<RoleResource> roleResource = roletListResource.getRoles();
//            for (RoleResource role : roleResource) {
//                for (RoleResource userRole : nodeResource.getRoles()) {
//                    if (role.getRoleID().equals(userRole.getRoleID())) {
//                        role.setSelected(true);
//                        break;
//                    }
//                }
//            }
//            model.addObject("roleResource", roleResource);
//
//            if (nodeResource.getAccountExpiration().equalsIgnoreCase("Never Expires")) {
//                Locale locale = new Locale("en", "US");
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
//                long timeInMillis = Calendar.getInstance().getTimeInMillis();
//                String initialDeviceDate = simpleDateFormat.format(timeInMillis);
//                nodeResource.setAccountExpiration(initialDeviceDate);
//                nodeResource.setNeverExpire(true);
//            }
//
//            model.addObject("nodeResource", nodeResource);
//            model.addObject("updateCriteria", updateCriteria);
//            model.addObject("edgeInNetworkgroupResource", edgeInNetworkgroupResource);
//            model.setViewName("networkgroup_edge_edit");
//            return model;
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//    }
//
//    @RequestMapping(value = "/networkgroup/nodeandedge/search", method = {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView listNetworkgroupInfoForTravlerAndEdge(@ModelAttribute("searchCriteria") NetworkgroupSearchCriteria searchCriteria, final RedirectAttributes redirectAttributes) {
//        try {
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//            Locale locale = new Locale("en", "US");
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
//            long timeInMillis = Calendar.getInstance().getTimeInMillis();
//            String initialDeviceDate = simpleDateFormat.format(Long.valueOf(timeInMillis));
//            searchCriteria.setDateFrom(initialDeviceDate);
//            searchCriteria.setDateTo(initialDeviceDate);
//
//            model.addObject("searchCriteria", searchCriteria);
//            model.setViewName("networkgroup_fornodeandedge_list");
//
//            NetworkgroupListResource networkgroupAllListResource = networkgroupService.readNetworkgroupResourceForUser();
//            model.addObject("networkgroupAllListResource", networkgroupAllListResource.getNetworkgroupResources());
//
//            return model;
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//
//    }
//
//    @RequestMapping(value = "/networkgroup/nodeandedge/search/submit", method = {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView listNetworkgroupInfoForTravlerAndEdgeProcess(@ModelAttribute("searchCriteria") NetworkgroupSearchCriteria searchCriteria, final RedirectAttributes redirectAttributes) {
//        try {
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//
//            searchCriteria.setLimitResult(maxRecordsToFetch);
//
//            NetworkgroupListResource networkgroupListResource = networkgroupService.readNetworkgroupResourceBySearchCriteria(searchCriteria);
//
//            model.addObject("totalPages", networkgroupListResource.getTotalPages());
//            model.addObject("totalDocuments", networkgroupListResource.getTotalDocuments());
//            pageLink = "eventsearch";
//            model.addObject("pagelink", pageLink);
//            model.addObject("networkgroupListResource", networkgroupListResource);
//            model.addObject("searchCriteria", searchCriteria);
//            NetworkgroupListResource networkgroupAllListResource = networkgroupService.readNetworkgroupResourceForUser();
//            model.addObject("networkgroupAllListResource", networkgroupAllListResource.getNetworkgroupResources());
//            model.setViewName("networkgroup_fornodeandedge_list");
//            return model;
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//    }
//
//    @RequestMapping(value = "/networkgroup/nodeandedge/view", method = {RequestMethod.POST, RequestMethod.GET})
//    public ModelAndView viewNetworkgroupForNodeAndEdgeDisplay(@RequestParam(value = "id", required = false) Long id, final RedirectAttributes redirectAttributes) {
//        try {
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//
//            NetworkgroupResource readCreatedResource = networkgroupService.readNetworkgroupByID(id);
//
//            readCreatedResource.setDateExpiration(DateConverter.getConvertedUnixDateToStringFormat1(readCreatedResource.getExpiryDate()));
//
//            model.addObject("networkgroupResource", readCreatedResource);
//
//            model.setViewName("networkgroup_fornodeandedge_view");
//            return model;
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//    }
//
//    @RequestMapping(value = "/networkgroup/node/nodeandedge/search", method = {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView listNodeFromNetworkgroupInfoForTravlerAndEdge(@ModelAttribute("searchCriteria") NodeInNetworkgroupSearchCriteria searchCriteria, final RedirectAttributes redirectAttributes) {
//        try {
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//            Locale locale = new Locale("en", "US");
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
//            long timeInMillis = Calendar.getInstance().getTimeInMillis();
//            String initialDeviceDate = simpleDateFormat.format(Long.valueOf(timeInMillis));
//            searchCriteria.setDateFrom(initialDeviceDate);
//            searchCriteria.setDateTo(initialDeviceDate);
//
//            model.addObject("searchCriteria", searchCriteria);
//            model.setViewName("node_fornodeandedge_list");
//
//            NetworkgroupListResource networkgroupAllListResource = networkgroupService.readNetworkgroupResourceForUser();
//            model.addObject("networkgroupAllListResource", networkgroupAllListResource.getNetworkgroupResources());
//
//            return model;
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//
//    }
//
//    @RequestMapping(value = "/networkgroup/node/nodeandedge/search/submit", method = {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView listNodeFromNetworkgroupInfoForTravlerAndEdgeProcess(@ModelAttribute("searchCriteria") NodeInNetworkgroupSearchCriteria searchCriteria, final RedirectAttributes redirectAttributes) {
//        try {
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//
//            searchCriteria.setLimitResult(maxRecordsToFetch);
//
//            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            NodeInNetworkgroupListResource nodeInNetworkgroupListResource = null;
//            if (principal instanceof UserDetails) {
//                UserDetails details = (UserDetails) principal;
//                UserResource loggedUser = userService.readUserByUsername(details.getUsername());
//                if (loggedUser.getUserType().equalsIgnoreCase("TRAVEL_GUIDE")) {
//                    nodeInNetworkgroupListResource = networkgroupService.readNodeInNetworkgroupResourceForEdgeBySearchCriteria(searchCriteria);
//                } else {
//                    nodeInNetworkgroupListResource = networkgroupService.readNodeInNetworkgroupResourceForNodeBySearchCriteria(searchCriteria);
//                }
//            } else {
//                return null;
//            }
//
//            model.addObject("totalPages", nodeInNetworkgroupListResource.getTotalPages());
//            model.addObject("totalDocuments", nodeInNetworkgroupListResource.getTotalDocuments());
//            pageLink = "eventsearch";
//            model.addObject("pagelink", pageLink);
//            model.addObject("nodeInNetworkgroupListResource", nodeInNetworkgroupListResource);
//            model.addObject("searchCriteria", searchCriteria);
//            NetworkgroupListResource networkgroupAllListResource = networkgroupService.readNetworkgroupResourceForUser();
//            model.addObject("networkgroupAllListResource", networkgroupAllListResource.getNetworkgroupResources());
//            model.setViewName("node_fornodeandedge_list");
//            return model;
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//    }
//
//    @RequestMapping(value = "/networkgroup/node/nodeandedge/view", method = {RequestMethod.POST})
//    public ModelAndView editNodeDisplay(@RequestParam(value = "id", required = true) Long id, final RedirectAttributes redirectAttributes) {
//        try {
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//
//            NodeResource nodeResource = nodeService.readNodeByID(id);
//            model.addObject("nodeResource", nodeResource);
//
//            if (nodeResource.getAccountExpiration().equalsIgnoreCase("Never Expires")) {
//                Locale locale = new Locale("en", "US");
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
//                long timeInMillis = Calendar.getInstance().getTimeInMillis();
//                String initialDeviceDate = simpleDateFormat.format(timeInMillis);
//                nodeResource.setAccountExpiration(initialDeviceDate);
//                nodeResource.setNeverExpire(true);
//            }
////node_fornodeandedge_list
//            model.setViewName("node_fornodeandedge_view");
//            return model;
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//    }
//
//    @RequestMapping(value = "/networkgroup/edge/nodeandedge/search", method = {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView listEdgeFromNetworkgroupInfoForTravlerAndEdge(@ModelAttribute("searchCriteria") NodeInNetworkgroupSearchCriteria searchCriteria, final RedirectAttributes redirectAttributes) {
//        try {
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//            Locale locale = new Locale("en", "US");
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
//            long timeInMillis = Calendar.getInstance().getTimeInMillis();
//            String initialDeviceDate = simpleDateFormat.format(Long.valueOf(timeInMillis));
//            searchCriteria.setDateFrom(initialDeviceDate);
//            searchCriteria.setDateTo(initialDeviceDate);
//
//            model.addObject("searchCriteria", searchCriteria);
//            model.setViewName("edge_fornodeandedge_list");
//
//            NetworkgroupListResource networkgroupAllListResource = networkgroupService.readNetworkgroupResourceForUser();
//            model.addObject("networkgroupAllListResource", networkgroupAllListResource.getNetworkgroupResources());
//
//            return model;
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//
//    }
//
//    @RequestMapping(value = "/networkgroup/edge/nodeandedge/search/submit", method = {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView listEdgeFromNetworkgroupInfoForTravlerAndEdgeProcess(@ModelAttribute("searchCriteria") EdgeInNetworkgroupSearchCriteria searchCriteria, final RedirectAttributes redirectAttributes) {
//        try {
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//
//            searchCriteria.setLimitResult(maxRecordsToFetch);
//
//            EdgeInNetworkgroupListResource edgeInNetworkgroupListResource = null;
//            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            NodeInNetworkgroupListResource nodeInNetworkgroupListResource = null;
//            if (principal instanceof UserDetails) {
//                UserDetails details = (UserDetails) principal;
//                UserResource loggedUser = userService.readUserByUsername(details.getUsername());
//                if (loggedUser.getUserType().equalsIgnoreCase("TRAVEL_GUIDE")) {
//                    edgeInNetworkgroupListResource = networkgroupService.readEdgeInNetworkgroupResourceForEdgeBySearchCriteria(searchCriteria);
//                } else {
//                    edgeInNetworkgroupListResource = networkgroupService.readEdgeInNetworkgroupResourceForNodeBySearchCriteria(searchCriteria);
//                }
//            } else {
//                return null;
//            }
//
//            model.addObject("totalPages", edgeInNetworkgroupListResource.getTotalPages());
//            model.addObject("totalDocuments", edgeInNetworkgroupListResource.getTotalDocuments());
//            pageLink = "eventsearch";
//            model.addObject("pagelink", pageLink);
//            model.addObject("edgeInNetworkgroupListResource", edgeInNetworkgroupListResource);
//            model.addObject("searchCriteria", searchCriteria);
//            NetworkgroupListResource networkgroupAllListResource = networkgroupService.readNetworkgroupResourceForUser();
//            model.addObject("networkgroupAllListResource", networkgroupAllListResource.getNetworkgroupResources());
//            model.setViewName("edge_fornodeandedge_list");
//            return model;
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//    }
//
//    @RequestMapping(value = "/networkgroup/edge/nodeandedge/view", method = {RequestMethod.POST})
//    public ModelAndView editEdgeDisplay(@RequestParam(value = "id", required = true) Long id, final RedirectAttributes redirectAttributes) {
//        try {
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//
//            EdgeResource edgeResource = edgeService.readEdgeByID(id);
//            model.addObject("edgeResource", edgeResource);
//
//            if (edgeResource.getAccountExpiration().equalsIgnoreCase("Never Expires")) {
//                Locale locale = new Locale("en", "US");
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
//                long timeInMillis = Calendar.getInstance().getTimeInMillis();
//                String initialDeviceDate = simpleDateFormat.format(timeInMillis);
//                edgeResource.setAccountExpiration(initialDeviceDate);
//                edgeResource.setNeverExpire(true);
//            }
////node_fornodeandedge_list
//            model.setViewName("edge_fornodeandedge_view");
//            return model;
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//    }
//
//    @RequestMapping(value = "/networkgroup/travelitinerary/nodeandedge/search", method = {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView listNodeItineraryFromNetworkgroupInfoForTravlerAndEdge(@ModelAttribute("searchCriteria") GeoLocationInNetworkgroupSearchCriteria searchCriteria, final RedirectAttributes redirectAttributes) {
//        try {
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//            Locale locale = new Locale("en", "US");
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
//            long timeInMillis = Calendar.getInstance().getTimeInMillis();
//            String initialDeviceDate = simpleDateFormat.format(Long.valueOf(timeInMillis));
//            searchCriteria.setDateFrom(initialDeviceDate);
//            searchCriteria.setDateTo(initialDeviceDate);
//
//            model.addObject("searchCriteria", searchCriteria);
//            model.setViewName("nodeitinerary_fornodeandedge_list");
//
//            NetworkgroupListResource networkgroupAllListResource = networkgroupService.readNetworkgroupResourceForUser();
//            model.addObject("networkgroupAllListResource", networkgroupAllListResource.getNetworkgroupResources());
//
//            return model;
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//
//    }
//
//    @RequestMapping(value = "/networkgroup/travelitinerary/nodeandedge/search/submit", method = {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView listNodeItineraryFromNetworkgroupInfoForTravlerAndEdgeProcess(@ModelAttribute("searchCriteria") GeoLocationInNetworkgroupSearchCriteria searchCriteria, final RedirectAttributes redirectAttributes) {
//        try {
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//
//            searchCriteria.setLimitResult(maxRecordsToFetch);
//
//            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            GeoLocationInNetworkgroupListResource travelItineraryInNetworkgroupListResource = null;
//            if (principal instanceof UserDetails) {
//                UserDetails details = (UserDetails) principal;
//                UserResource loggedUser = userService.readUserByUsername(details.getUsername());
//                if (loggedUser.getUserType().equalsIgnoreCase("TRAVEL_GUIDE")) {
//                    travelItineraryInNetworkgroupListResource = networkgroupService.readTravelItineraryInNetworkgroupResourceForEdgeBySearchCriteria(searchCriteria);
//                } else {
//                    travelItineraryInNetworkgroupListResource = networkgroupService.readTravelItineraryInNetworkgroupResourceForNodeBySearchCriteria(searchCriteria);
//                }
//            } else {
//                return null;
//            }
//
////            travelItineraryInNetworkgroupListResource = networkgroupService.readTravelItineraryInNetworkgroupResourceForNodeBySearchCriteria(searchCriteria);
//            model.addObject("totalPages", travelItineraryInNetworkgroupListResource.getTotalPages());
//            model.addObject("totalDocuments", travelItineraryInNetworkgroupListResource.getTotalDocuments());
//            pageLink = "eventsearch";
//            model.addObject("pagelink", pageLink);
//            model.addObject("travelItineraryInNetworkgroupListResource", travelItineraryInNetworkgroupListResource);
//            model.addObject("searchCriteria", searchCriteria);
//            NetworkgroupListResource networkgroupAllListResource = networkgroupService.readNetworkgroupResourceForUser();
//            model.addObject("networkgroupAllListResource", networkgroupAllListResource.getNetworkgroupResources());
//            model.setViewName("nodeitinerary_fornodeandedge_list");
//            return model;
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//    }
}
