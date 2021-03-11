/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;

import com.project.web.controller.NetworkManagement_CLIENT_MVC_Controller;
import java.util.List;

/**
 *
 * @author samri_g64pbd3
 */
public class ChartDataTwoMain {

        public ChartDataTwoMain() {

        }

        private List<ChartDataTwo> nodes;

        private List<ChartDataTwoEdge> edges;

        public List<ChartDataTwo> getNodes() {
            return nodes;
        }

        public void setNodes(List<ChartDataTwo> nodes) {
            this.nodes = nodes;
        }

        public List<ChartDataTwoEdge> getEdges() {
            return edges;
        }

        public void setEdges(List<ChartDataTwoEdge> edges) {
            this.edges = edges;
        }

    }