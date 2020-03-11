package com.example.demo_activity.test1.controller;

import com.example.demo_activity.test1.utils.ActivitiUtils;
import org.flowable.engine.repository.Deployment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yongl
 * @DATE: 2020/3/2 14:06
 */

@Controller
@RequestMapping("/activity")
public class ActivityController {

    /**
     * 请假流程
     * @return
     */
    @RequestMapping(value = "askForLeave")
    @ResponseBody
    public Object askForLeave() {
        String msg = "请假流程部署成功";
        String bpmnUrl = "/processes/testbpmn.bpmn";
        String pngUrl = "/processes/testbpmnbpmn.png";
        Deployment deployment = null;
        try {
            deployment = ActivitiUtils.saveNewDeploye(bpmnUrl, pngUrl, "请假流程");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", msg);
        map.put("id", deployment.getId());//流程部署Id
        map.put("name", deployment.getName());//流程部署名称
        return map;
    }


    /**
     * 审核流程
     * @return
     */
    @RequestMapping(value = "toExamine")
    @ResponseBody
    public Object toExamine() {
        String msg = "审核流程部署成功";
        String bpmnUrl = "/processes/TwoAuditOff.bpmn";
        String pngUrl = "/processes/TwoAuditOffbpmn.png";
        Deployment deployment = null;
        try {
            deployment = ActivitiUtils.saveNewDeploye(bpmnUrl, pngUrl, "审核流程");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", msg);
        map.put("id", deployment.getId());//流程部署Id
        map.put("name", deployment.getName());//流程部署名称
        return map;
    }
}
