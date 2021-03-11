/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.web.controller;

import com.project.config.PropertiesConfig;
import com.project.dao.network.EdgeService;
import com.project.dao.network.NetworkGroupService;
import com.project.dao.network.NodeService;
import com.project.dao.user.UserService;
import com.project.exception.rest.BadRequestException;
import com.project.exception.rest.InternalServerException;
import com.project.exception.rest.UnAuthorizedException;
import com.project.model.network.EdgeInNetworkGroupResource;
import com.project.model.network.EdgeListResource;
import com.project.model.network.EdgeResource;
import com.project.model.network.NetworkGroupResource;
import com.project.model.network.NodeInNetworkGroupListResource;
import com.project.model.network.NodeInNetworkGroupResource;
import com.project.model.network.NodeListResource;
import com.project.model.network.NodeResource;
import com.project.util.ApplicationMessageEnum;
import com.project.util.JsonParsingTest;
import com.project.util.Log4jUtil;
import com.project.util.enums.NODE_TYPE;
import com.test.ChartDataTwo;
import java.util.Set;
import javax.annotation.Resource;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author SAM
 */
@Controller
@RequestMapping(value = "/networkman")
public class NetworkManagement_CLIENT_MVC_Controller extends Base_Controller {

    final int maxRecordsToFetch = 20;

    @Autowired
    private NodeService nodeService;

    @Autowired
    private EdgeService edgeService;

    @Autowired
    private UserService userService;

    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;

    @Autowired
    private NetworkGroupService networkgroupService;

    @RequestMapping(value = "/charttest/search", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView networkChartDisplayTest(final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            String jsonText = JsonParsingTest.readJSONText();

            System.out.println("Final --> " + jsonText);
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(jsonText);

            System.out.println("Final 2 --> " + json);

            model.addObject("chartData", JsonParsingTest.getChartData());

            model.setViewName("networkchart_test");
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

    @RequestMapping(value = "/charttest2/search", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView networkChartDisplayTest2(final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            String jsonText = JsonParsingTest.readJSONText();

            System.out.println("Final --> " + jsonText);
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(jsonText);

            System.out.println("Final 2 --> " + json);

            model.addObject("chartData", JsonParsingTest.getChartData());

//            String jsObject = "[ {id: 1, label: \"Node 1\", title: \"I have a popup!\"},"
//                    + "                                {id: 2, label: \"Node 2\", title: \"I have a popup!\"},"
//                    + "                               {id: 3, label: \"Node 3\", title: \"I have a popup!\"},"
//                    + "                               {id: 4, label: \"Node 4\", title: \"I have a popup4!\"},"
//                    + "                            {id: 99, label: \"Node 5\", title: \"I have a popup!\"},"
//                    + "                         ]";
            String jsObjectBody = "";

            String jsObjectStart = "[";
//            int id = 0;
            for (ChartDataTwo chartDataTwo : JsonParsingTest.getChartData().getNodes()) {
//                id++;
                jsObjectBody += " {id: " + chartDataTwo.getNumId() + ", label: \"" + chartDataTwo.getId() + "\\n" + chartDataTwo.getNumId() + "\", image: \"" + "https://awoiaf.westeros.org/images/thumb/d/d5/House_Lannister.svg/500px-House_Lannister.svg.png" + "\", shape: \"image\", title: \"" + chartDataTwo.getLink() + "\"},";
//                image: DIR + "Network-Pipe-icon.png",
            }

            String jsObjectEnd = "]";

            String jsObjectFull = jsObjectStart + jsObjectBody + jsObjectEnd;
            System.out.println(jsObjectFull);

            model.addObject("jsObject", jsObjectFull);
            model.setViewName("networkchart_test_2");
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

    @RequestMapping(value = "/dynamic_chart/display", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView networkChart2Display(final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            NodeListResource listResource = nodeService.readAllEnabledNodes();

            EdgeListResource edgeListResource = edgeService.readAllEnabledEdges();

//            String jsonText = JsonParsingTest.readJSONText();
//            System.out.println("Final --> " + jsonText);
//            JSONParser parser = new JSONParser();
//            JSONObject json = (JSONObject) parser.parse(jsonText);
//
//            System.out.println("Final 2 --> " + json);
//            model.addObject("chartData", JsonParsingTest.getChartData());
            model.addObject("tableData", listResource);

//            String jsObject = "[ {id: 1, label: \"Node 1\", title: \"I have a popup!\"},"
//                    + "                                {id: 2, label: \"Node 2\", title: \"I have a popup!\"},"
//                    + "                               {id: 3, label: \"Node 3\", title: \"I have a popup!\"},"
//                    + "                               {id: 4, label: \"Node 4\", title: \"I have a popup4!\"},"
//                    + "                            {id: 99, label: \"Node 5\", title: \"I have a popup!\"},"
//                    + "                         ]";
            String jsObjectNodeBody = "";

            String jsObjectStart = "[";
////            int id = 0;
//            for (ChartDataTwo chartDataTwo : JsonParsingTest.getChartData().getNodes()) {
////                id++;
//                jsObjectNodeBody += " {id: " + chartDataTwo.getNumId() + ", label: \"" + chartDataTwo.getId() +"\\n"+chartDataTwo.getNumId()+ "\", image: \"" + "https://awoiaf.westeros.org/images/thumb/d/d5/House_Lannister.svg/500px-House_Lannister.svg.png"+ "\", shape: \"image\", title: \""+chartDataTwo.getLink()+"\"},";
////                image: DIR + "Network-Pipe-icon.png",
//            }

            final String baseUrl
                    = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
//javax.swing.JOptionPane.showMessageDialog(null, baseUrl);

            String imagePath = baseUrl + "/resources/icons/";

            String fullImagePath = imagePath + "edit_32x32.png";

            for (NodeResource chartDataTwo : listResource.getNodeResources()) {

                if (chartDataTwo.getType().equalsIgnoreCase(NODE_TYPE.TYPE_ROUTER.type())) {
                    if (chartDataTwo.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "router-type.png";
                    } else {
                        fullImagePath = imagePath + "router-alert-type.png";
                    }

                } else if (chartDataTwo.getType().equalsIgnoreCase(NODE_TYPE.TYPE_SERVER.type())) {
//                    fullImagePath = imagePath + "server-type.png";
                    if (chartDataTwo.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "server-type.png";
                    } else {
                        fullImagePath = imagePath + "server-alert-type.png";
                    }

                } else if (chartDataTwo.getType().equalsIgnoreCase(NODE_TYPE.TYPE_NETWORKGROUP.type())) {
//                    fullImagePath = imagePath + "server-type.png";
                    if (chartDataTwo.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "networkgroup-type.png";
                    } else {
                        fullImagePath = imagePath + "networkgroup-alert-type.png";
                    }

                } else if (chartDataTwo.getType().equalsIgnoreCase(NODE_TYPE.TYPE_DATABASE.type())) {
                    if (chartDataTwo.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "database-type.png";
                    } else {
                        fullImagePath = imagePath + "database-alert-type.png";
                    }

                } else if (chartDataTwo.getType().equalsIgnoreCase(NODE_TYPE.TYPE_HUB.type())) {
                    if (chartDataTwo.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "hub-type.png";
                    } else {
                        fullImagePath = imagePath + "hub-alert-type.png";
                    }

                } else if (chartDataTwo.getType().equalsIgnoreCase(NODE_TYPE.TYPE_PRINTER.type())) {
                    if (chartDataTwo.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "printer-type.png";
                    } else {
                        fullImagePath = imagePath + "printer-alert-type.png";
                    }

                } else if (chartDataTwo.getType().equalsIgnoreCase(NODE_TYPE.TYPE_SWITCH.type())) {
                    if (chartDataTwo.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "switch-type.png";
                    } else {
                        fullImagePath = imagePath + "switch-alert-type.png";
                    }

                } else {
//                    fullImagePath = imagePath + "cmputer-type.png";
                    if (chartDataTwo.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "cmputer-type.png";
                    } else {
                        fullImagePath = imagePath + "cmputer-alert-type.png";
                    }
                }

                double x = chartDataTwo.getCanvasXValue();
                double y = chartDataTwo.getCanvasYValue();
                jsObjectNodeBody += " {id: " + chartDataTwo.getNodeID() + "," + " x: " + x + "," + " y: " + y + "," + " label: \"" + chartDataTwo.getLabel() + "\\n" + chartDataTwo.getDescription() + "\", image: \"" + fullImagePath + "\", shape: \"image\", title: \"" + chartDataTwo.getTitle() + "\"},";
//                jsObjectNodeBody += " {id: " + chartDataTwo.getNodeID() + ", label: \"" + chartDataTwo.getLabel() + "\\n" + chartDataTwo.getDescription() + "\", image: \"" + fullImagePath + "\", shape: \"image\", title: \"" + chartDataTwo.getTitle() + "\"},";
//                image: DIR + "Network-Pipe-icon.png",

//                int x = (int) (Math.random() * 800);
//                int y = (int) (Math.random() * 800);
//                jsObjectNodeBody += " {id: " + chartDataTwo.getNodeID() + "," + " x: " + x + "," + " y: " + y + "," + " label: \"" + chartDataTwo.getLabel() + "\\n" + chartDataTwo.getDescription() + "\", image: \"" + fullImagePath + "\", shape: \"image\", title: \"" + chartDataTwo.getTitle() + "\"},";
////                jsObjectNodeBody += " {id: " + chartDataTwo.getNodeID() + ", label: \"" + chartDataTwo.getLabel() + "\\n" + chartDataTwo.getDescription() + "\", image: \"" + fullImagePath + "\", shape: \"image\", title: \"" + chartDataTwo.getTitle() + "\"},";
////                image: DIR + "Network-Pipe-icon.png",
            }

            String jsObjectEnd = "]";

            String jsObjectFull = jsObjectStart + jsObjectNodeBody + jsObjectEnd;
            System.out.println(jsObjectFull);

            String jsObjectEdgeBody = "";

            String jsObjectEdgeStart = "[";

            for (EdgeResource chartDataTwo : edgeListResource.getEdgeResources()) {
//                id++;
                jsObjectEdgeBody += " {from: " + chartDataTwo.getFromNodeResource().getNodeID() + ", to: " + chartDataTwo.getToNodeResource().getNodeID() + ", length: " + chartDataTwo.getEdge_length() + ", dashes: " + chartDataTwo.isDashes() + ", title: \"" + chartDataTwo.getTitle() + "\"" + ", arrows: \"" + chartDataTwo.getArrows() + "\", label: \"" + chartDataTwo.getLabel() + "\"},";
//                image: DIR + "Network-Pipe-icon.png",
            }

            String jsObjectEdgeEnd = "]";

            String jsObjectEdgeFull = jsObjectEdgeStart + jsObjectEdgeBody + jsObjectEdgeEnd;
            System.out.println("NOOOOOONNNNNN :----------- " + jsObjectEdgeFull);

            model.addObject("jsObject", jsObjectFull);

            model.addObject("jsEdgeObject", jsObjectEdgeFull);
            model.setViewName("networkchart_dynamic");
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

    @RequestMapping(value = "/static_chart/display", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView networkChart1Display(final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

//            NodeListResource listResource = nodeService.readAllEnabledNodes();
//            EdgeListResource edgeListResource = edgeService.readAllEnabledEdges();
//            model.addObject("tableData", listResource);
//            //--------- START
//            String jsonText = JsonParsingTest.readJSONText();
//
//            System.out.println("Final --> " + jsonText);
//            JSONParser parser = new JSONParser();
//            JSONObject json = (JSONObject) parser.parse(jsonText);
//
//            System.out.println("Final 2 --> " + json);
//
//            model.addObject("chartData", JsonParsingTest.getChartData());
//
////            String jsObject = "[ {id: 1, label: \"Node 1\", title: \"I have a popup!\"},"
////                    + "                                {id: 2, label: \"Node 2\", title: \"I have a popup!\"},"
////                    + "                               {id: 3, label: \"Node 3\", title: \"I have a popup!\"},"
////                    + "                               {id: 4, label: \"Node 4\", title: \"I have a popup4!\"},"
////                    + "                            {id: 99, label: \"Node 5\", title: \"I have a popup!\"},"
////                    + "                         ]";
//            String jsObjectBody = "";
//
//            String jsObjectStart = "[";
////            int id = 0;
//            for (ChartDataTwo chartDataTwo : JsonParsingTest.getChartData().getNodes()) {
////                id++;
//                jsObjectBody += " {id: " + chartDataTwo.getNumId() + ", label: \"" + chartDataTwo.getId() + "\\n" + chartDataTwo.getNumId() + "\", image: \"" + "https://awoiaf.westeros.org/images/thumb/d/d5/House_Lannister.svg/500px-House_Lannister.svg.png" + "\", shape: \"image\", title: \"" + chartDataTwo.getLink() + "\"},";
//            }
//
//            String jsObjectEnd = "]";
//
//            String jsObjectFull = jsObjectStart + jsObjectBody + jsObjectEnd;
//            System.out.println(jsObjectFull);
//
//            model.addObject("jsObject", jsObjectFull);
            /// END
            model.setViewName("networkchart_static");
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
    @RequestMapping(value = "/dynamic_chart/display/submit", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView networkChart2DisplaySubmit(@ModelAttribute("nodeListResource") NodeListResource nodeListResource, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

//            if (nodeListResource.getNodeResources() != null) {
//                for (NodeResource nodeResource : nodeListResource.getNodeResources()) {
//                }
//
//            }
            nodeService.updateNodeCordinates(nodeListResource);

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

    @RequestMapping(value = "/dynamic_chart/networkgroup/{id}/display", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView networkGroupChartDisplay(@PathVariable Long id, final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            NetworkGroupResource readNetworkGroupResource = networkgroupService.readNetworkGroupByID(id);
            Set<NodeInNetworkGroupResource> nodeInNetworkGroupResources = readNetworkGroupResource.getNodeInNetworkGroupResources();
            Set<EdgeInNetworkGroupResource> edgeInNetworkGroupResources = readNetworkGroupResource.getEdgeInNetworkGroupResources();

//edgeInNetworkGroupResources
//            NodeListResource listResource = nodeService.readAllEnabledNodes();
//            EdgeListResource edgeListResource = edgeService.readAllEnabledEdges();
            model.addObject("tableData", nodeInNetworkGroupResources);

            String jsObjectNodeBody = "";
            String jsObjectStart = "[";

            final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

            String imagePath = baseUrl + "/resources/icons/";

            String fullImagePath = imagePath + "edit_32x32.png";

            for (NodeInNetworkGroupResource nodeInNetworkGroupResource : nodeInNetworkGroupResources) {

                if (nodeInNetworkGroupResource.getNodeResource().getType().equalsIgnoreCase(NODE_TYPE.TYPE_ROUTER.type())) {
                    if (nodeInNetworkGroupResource.getNodeResource().getAlert().getType() == 1) {
                        fullImagePath = imagePath + "router-type.png";
                    } else {
                        fullImagePath = imagePath + "router-alert-type.png";
                    }

                } else if (nodeInNetworkGroupResource.getNodeResource().getType().equalsIgnoreCase(NODE_TYPE.TYPE_SERVER.type())) {
//                    fullImagePath = imagePath + "server-type.png";
                    if (nodeInNetworkGroupResource.getNodeResource().getAlert().getType() == 1) {
                        fullImagePath = imagePath + "server-type.png";
                    } else {
                        fullImagePath = imagePath + "server-alert-type.png";
                    }

                } else if (nodeInNetworkGroupResource.getNodeResource().getType().equalsIgnoreCase(NODE_TYPE.TYPE_NETWORKGROUP.type())) {
//                    fullImagePath = imagePath + "server-type.png";
                    if (nodeInNetworkGroupResource.getNodeResource().getAlert().getType() == 1) {
                        fullImagePath = imagePath + "networkgroup-type.png";
                    } else {
                        fullImagePath = imagePath + "networkgroup-alert-type.png";
                    }

                } else if (nodeInNetworkGroupResource.getNodeResource().getType().equalsIgnoreCase(NODE_TYPE.TYPE_DATABASE.type())) {
                    if (nodeInNetworkGroupResource.getNodeResource().getAlert().getType() == 1) {
                        fullImagePath = imagePath + "database-type.png";
                    } else {
                        fullImagePath = imagePath + "database-alert-type.png";
                    }

                } else if (nodeInNetworkGroupResource.getNodeResource().getType().equalsIgnoreCase(NODE_TYPE.TYPE_HUB.type())) {
                    if (nodeInNetworkGroupResource.getNodeResource().getAlert().getType() == 1) {
                        fullImagePath = imagePath + "hub-type.png";
                    } else {
                        fullImagePath = imagePath + "hub-alert-type.png";
                    }

                } else if (nodeInNetworkGroupResource.getNodeResource().getType().equalsIgnoreCase(NODE_TYPE.TYPE_PRINTER.type())) {
                    if (nodeInNetworkGroupResource.getNodeResource().getAlert().getType() == 1) {
                        fullImagePath = imagePath + "printer-type.png";
                    } else {
                        fullImagePath = imagePath + "printer-alert-type.png";
                    }

                } else if (nodeInNetworkGroupResource.getNodeResource().getType().equalsIgnoreCase(NODE_TYPE.TYPE_SWITCH.type())) {
                    if (nodeInNetworkGroupResource.getNodeResource().getAlert().getType() == 1) {
                        fullImagePath = imagePath + "switch-type.png";
                    } else {
                        fullImagePath = imagePath + "switch-alert-type.png";
                    }

                } else if (nodeInNetworkGroupResource.getNodeResource().getType().equalsIgnoreCase(NODE_TYPE.TYPE_COMPUTER.type())) {
//                    fullImagePath = imagePath + "cmputer-type.png";
                    if (nodeInNetworkGroupResource.getNodeResource().getAlert().getType() == 1) {
                        fullImagePath = imagePath + "cmputer-type.png";
                    } else {
                        fullImagePath = imagePath + "cmputer-alert-type.png";
                    }
                } else {
//                    fullImagePath = imagePath + "cmputer-type.png";
                    if (nodeInNetworkGroupResource.getNodeResource().getAlert().getType() == 1) {
                        fullImagePath = imagePath + "cmputer-type.png";
                    } else {
                        fullImagePath = imagePath + "cmputer-alert-type.png";
                    }
                }

                double x = nodeInNetworkGroupResource.getCanvasXValue();
                double y = nodeInNetworkGroupResource.getCanvasYValue();
//                jsObjectNodeBody += " {id: " + nodeInNetworkGroupResource.getNodeResource().getNodeID() + "," + " x: " + x + "," + " y: " + y + "," + " label: \"" + nodeInNetworkGroupResource.getNodeResource().getLabel() + "\\n" + nodeInNetworkGroupResource.getDescription() + "\", image: \"" + fullImagePath + "\", shape: \"image\", title: \"" + nodeInNetworkGroupResource.getNodeResource().getTitle() + "\"},";
                jsObjectNodeBody += " {id: " + nodeInNetworkGroupResource.getNodeInNetworkGroupID() + "," + " x: " + x + "," + " y: " + y + "," + " label: \"" + nodeInNetworkGroupResource.getNodeResource().getLabel() + "\\n" + nodeInNetworkGroupResource.getNodeResource().getDescription() + "\", image: \"" + fullImagePath + "\", shape: \"image\", title: \"" + nodeInNetworkGroupResource.getNodeResource().getTitle() + "\"},";

            }

            String jsObjectEnd = "]";

            String jsObjectFull = jsObjectStart + jsObjectNodeBody + jsObjectEnd;
            System.out.println("nodes :- " + jsObjectFull);

            String jsObjectEdgeBody = "";

            String jsObjectEdgeStart = "[";

            for (EdgeInNetworkGroupResource edgeInNetworkGroupResource : edgeInNetworkGroupResources) {
//                jsObjectEdgeBody += " {from: " + edgeInNetworkGroupResource.getFromNodeInNetworkGroupResource().getNodeResource().getNodeID() + ", to: " + edgeInNetworkGroupResource.getToNodeInNetworkGroupResource().getNodeResource().getNodeID() + ", length: " + edgeInNetworkGroupResource.getEdgeResource().getEdge_length() + ", dashes: " + edgeInNetworkGroupResource.getEdgeResource().isDashes() + ", title: \"" + edgeInNetworkGroupResource.getEdgeResource().getTitle() + "\"" + ", arrows: \"" + edgeInNetworkGroupResource.getEdgeResource().getArrows() + "\", label: \"" + edgeInNetworkGroupResource.getEdgeResource().getLabel() + "\"},";

                jsObjectEdgeBody += " {from: " + edgeInNetworkGroupResource.getFromNodeInNetworkGroupResource().getNodeInNetworkGroupID() + ", to: " + edgeInNetworkGroupResource.getToNodeInNetworkGroupResource().getNodeInNetworkGroupID() + ", length: " + edgeInNetworkGroupResource.getEdge_length() + ", dashes: " + edgeInNetworkGroupResource.isDashes() + ", title: \"" + edgeInNetworkGroupResource.getTitle() + "\"" + ", arrows: \"" + edgeInNetworkGroupResource.getArrows() + "\", label: \"" + edgeInNetworkGroupResource.getLabel() + "\"},";

            }

            String jsObjectEdgeEnd = "]";

            String jsObjectEdgeFull = jsObjectEdgeStart + jsObjectEdgeBody + jsObjectEdgeEnd;
            System.out.println("NOOOOOONNNNNN :----------- " + jsObjectEdgeFull);

            model.addObject("jsObject", jsObjectFull);

            model.addObject("jsEdgeObject", jsObjectEdgeFull);

            model.addObject("networkGroupId", id);

            model.setViewName("networkchart_dynamic_networkgroup");
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
    @RequestMapping(value = "/dynamic_chart/networkgroup/update/nodes/cordinates/submit", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView networkGroupChartUpdateCoordinatesProcess(@RequestParam(value = "networkGroupId", required = true) Long networkGroupId, @ModelAttribute("nodeListResource") NodeInNetworkGroupListResource nodeListResource, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

//networkgroupService.
            networkgroupService.updateNodeInNetworkGroupResourcesForCordinates(networkGroupId, nodeListResource);

            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/networkman/dynamic_chart/networkgroup/" + networkGroupId + "/display");

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

    @RequestMapping(value = "/networkgroup/node/double_click/submit", method = {RequestMethod.POST})
    public ModelAndView processDoubleClickNodeProcess(@RequestParam(value = "nodeInNetworkGroupId", required = true) Long nodeInNetworkGroupId, final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            NodeInNetworkGroupResource readNodeInNetworkGroupResource = networkgroupService.readNodeInNetworkGroupResourceByID(nodeInNetworkGroupId);

            String nodeType = readNodeInNetworkGroupResource.getNodeResource().getType();

            if (nodeType != null && nodeType.equalsIgnoreCase("type-networkgroup")) {

//                NetworkGroupResource readCreatedResource = networkgroupService.readNetworkGroupByNodeInNetworkGroupResourceId(nodeInNetworkGroupId);
                NetworkGroupResource readCreatedResource = networkgroupService.readNetworkGroupResourceByCode(readNodeInNetworkGroupResource.getNodeResource().getLabel());
//                redirectAttributes.addAttribute("id", networkGroupID);
                redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_LOAD_RECORD.getMessage());
                return new ModelAndView("redirect:" + "/networkman/dynamic_chart/networkgroup/" + readCreatedResource.getNetworkGroupID() + "/display");
            } else {
                NetworkGroupResource readCreatedResource = networkgroupService.readNetworkGroupByID(402L);
//                redirectAttributes.addAttribute("id", networkGroupID);
                redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_LOAD_RECORD.getMessage());
//                return new ModelAndView("redirect:" + "/networkman/dynamic_chart/networkgroup/" + readCreatedResource.getNetworkGroupID() + "/display");
                return new ModelAndView("redirect:" + readNodeInNetworkGroupResource.getNodeResource().getRedirectingURLLink());
            }

        } catch (UnAuthorizedException e) {
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (InternalServerException e) {
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (BadRequestException e) {
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (Exception e) {
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }
}
