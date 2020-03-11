package com.example.demo_activity.test1.utils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.*;
import org.flowable.cmmn.model.ProcessTask;
import org.flowable.engine.*;
import org.flowable.engine.common.impl.identity.Authentication;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.flowable.validation.ProcessValidator;
import org.flowable.validation.ProcessValidatorFactory;
import org.flowable.validation.ValidationError;

import java.io.InputStream;
import java.util.*;

public class ActivitiUtils {
	
	/**
	 * 获取默认流程引擎实例，会自动读取activiti.cfg.xml文件
	 */
	private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	private static RuntimeService runtimeService = processEngine.getRuntimeService();
	  
	private static RepositoryService repositoryService = processEngine.getRepositoryService();
	  
	private static HistoryService historyService = processEngine.getHistoryService();
	
	private static TaskService taskService = processEngine.getTaskService();
	
	private static IdentityService identityService = processEngine.getIdentityService();
   
	/**
	 * 部署工作流
	 *
	 * @param bpmnModel 文件对象
	 * 
	 * @param fileName 文件名称
	 * 
	 * @param name 名称
	 * 
	 */
	public static Deployment deployProcess(BpmnModel bpmnModel, String fileName, String name) {
		Deployment deployment = repositoryService.createDeployment()
    		    .addBpmnModel(fileName + ".bpmn", bpmnModel).name(name)  
    		    .deploy();
		return deployment;
	}
	
	/**
	 * 部署工作流
	 *
	 * @param bpmnUrl 文件路径
	 * 
	 * @param pngUrl 文件路径
	 * 
	 * @param deploymentName 部署流程名称
	 * 
	 * @return deployment 部署实例
	 * 
	 */
	public static Deployment saveNewDeploye(String bpmnUrl, String pngUrl, String deploymentName) throws Exception {
		System.out.println("repositoryService的地址是"+repositoryService);
		InputStream inputStreamBpmn = ActivitiUtils.class.getResourceAsStream(bpmnUrl);
		System.out.println("inputStreamBpmn的地址是"+inputStreamBpmn);
		InputStream inputStreamPng = ActivitiUtils.class.getResourceAsStream(pngUrl);
		System.out.println("inputStreamPng的地址是"+inputStreamPng);
		Deployment deployment = repositoryService.createDeployment().
				name(deploymentName).addInputStream(bpmnUrl, inputStreamBpmn).
				addInputStream(pngUrl, inputStreamPng).deploy();
		return deployment;
	}
	
	/**
	 * 根据流程定义key获取流程定义（当流程发布很多次以后，会根据processKey模糊查询，返回一个list,但是用最新的发布流程）
	 *
	 * @param pdfKey 部署流程key
	 * 
	 */
	public static ProcessDefinition findProdfByKey(String pdfKey) {
		List<ProcessDefinition> listPdf = repositoryService
                .createProcessDefinitionQuery().processDefinitionKey(pdfKey)
                .orderByProcessDefinitionVersion().desc()//使用流程定义的版本降序排列
                .list();
		if (null!=listPdf&&listPdf.size()>0) {
			//最新的流程定义
			return listPdf.get(0);
		}
		return null;
	}
	
	/**
	 * 根据流程定义deploymentId流程定义
	 *
	 * @param deploymentId 部署ID
	 * 
	 */
	public static ProcessDefinition findProdfByDeploymentId(String deploymentId) {
		List<ProcessDefinition> listPdf = repositoryService
                .createProcessDefinitionQuery().deploymentId(deploymentId)//使用流程定义的版本降序排列
                .orderByProcessDefinitionVersion().desc()//使用流程定义的版本降序排列
                .list();
		if (listPdf!=null) {
			//最新的流程定义
			return listPdf.get(0);
		}
		return null;
	}
	
	/**
	 * 根据流程定义deploymentId流程定义
	 *
	 * @param deploymentId 部署ID
	 * 
	 */
	public static ProcessDefinition findProdfById(String id) {
		ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery().processDefinitionId(id).singleResult();
		return processDefinition;
	}
	
	/**
	 * 启动工作流
	 *
	 * @param processDefId 流程定义Id
	 * 
	 * @param objId 业务主键Id(与工作流建立关系)
	 * 
	 * @param variables 工作流需要的参数
	 * 
	 * @param userId 当前登录人
	 * 
	 * @return ProcessInstance 流程实例
	 * 
	 */
	public static ProcessInstance startProcessById(String processDefId) {
		// 设置流程发起人
		//identityService.setAuthenticatedUserId(userId);
		// 使用流程定义的key，启动流程实例，同时设置流程变量
		ProcessInstance pi = runtimeService.startProcessInstanceById(processDefId);
		return pi;
	}

	public static ProcessInstance startProcessByKey(String processDefKey,Map<String, Object> variables) {
		// 使用流程定义的key，启动流程实例，同时设置流程变量
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(processDefKey);
		String processInstanceId = pi.getId();
		System.out.println("流程开启成功.......实例流程id:"+processInstanceId);
		return pi;
	}

	public static Task queryCurrentTask(String processInstanceId){
		Task task = taskService.createTaskQuery()//创建查询对象
				.processInstanceId(processInstanceId)//通过流程实例id来查询当前任务
				.singleResult();//获取单个查询结果
		String name = task.getName();
		System.out.println("当前任务名称"+name);
		return task;
	}
	//根据taskId查询任务
	public static Task queryTaskById(String taskId){
		/*Task task = taskService.createTaskQuery()//创建查询对象
				.processInstanceId(processInstanceId)//通过流程实例id来查询当前任务
				.singleResult();//获取单个查询结果*/
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String name = task.getName();
		System.out.println("当前任务名称"+name);
		return task;
	}

	//根据Assignee查询任务
	public static Task queryTaskByAssignee(String assignee,String processInstanceId){
		/*Task task = taskService.createTaskQuery()//创建查询对象
				.processInstanceId(processInstanceId)//通过流程实例id来查询当前任务
				.singleResult();//获取单个查询结果*/
		Task task = taskService.createTaskQuery().taskAssignee(assignee).processInstanceId(processInstanceId).singleResult();
		String name = task.getName();
		System.out.println("当前任务名称"+name);
		return task;
	}

	//签发人审核
	public static void completeEmployeeTask(Task task,Map<String, Object> variables){
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
		if(processInstance==null){
			return;
		}
		//获取任务id
		String taskId = task.getId();
		//完成任务
		//Map<String, Object> variables = new HashMap<String, Object>();
		//variables.put("time", "4");
		//processEngine.getTaskService().complete(taskId);
		if(variables==null){
			processEngine.getTaskService().complete(taskId);
		}else {
			processEngine.getTaskService().complete(taskId,variables);
		}
		System.out.println("职员已经提交申请.......");
	}

	//职员提交申请
	public static void auditTask(Task task,Map<String, Object> variables){
		//获取任务id
		String taskId = task.getId();
		//完成任务
		//Map<String, Object> variables = new HashMap<String, Object>();
		//variables.put("time", "4");
		//processEngine.getTaskService().complete(taskId);
		//processEngine.getTaskService().complete(taskId,assignee);
		System.out.println("职员已经提交申请.......");
	}

	
	/**
	 * 执行当前任务
	 *
	 * @param taskId 任务Id
	 * 
	 * @param variables 工作流需要的参数
	 * 
	 */
	public static void saveMyPersonalTask(String taskId, Map<String, Object> variables) {
		taskService.complete(taskId, variables);
	}
	
	/**
	 * 执行当前任务并返回tasks
	 *
	 * @param taskId 任务Id
	 * 
	 * @param variables 工作流需要的参数
	 * 
	 */
	public static List<Task> doTaskRetNext(String taskId, Map<String, Object> variables, String processInstanceId) {
		taskService.complete(taskId, variables);
		List<Task> tasks = taskService.createTaskQuery().
				processInstanceId(processInstanceId).list();
		return tasks;
	}

	/**
	 * 根据登录人ID、流程实例ID 查询个人代办任务
	 *
	 * @param userID 登录人ID
	 * 
	 * @param processInstanceId 流程实例ID
	 * 
	 * @return task 任务实例对象
	 */
	public static Task findTaskByUsrIdAndPiId(String userId, String processInstanceId) {
		Task task = taskService.createTaskQuery().
				processInstanceId(processInstanceId).taskAssignee(userId).singleResult();
		return task;
	}

	/**
	 * 根据业务密钥获取流程实例id
	 * @param investorId
	 * @return
	 */
	public static List<ProcessInstance> findProcessInst(String investorId){
		List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(investorId).list();
		return list;
	};


	/**
	 * 根据任务节点、流程实例ID 查询个人待办
	 *
	 * @param userID 登录人ID
	 * 
	 * @param processInstanceId 流程实例ID
	 * 
	 * @return task 任务实例对象
	 */
	public static Task findTaskByTaskNodeAndPiId(String taskNode, String processInstanceId) {
		Task task = taskService.createTaskQuery().
				processInstanceId(processInstanceId).taskDefinitionKey(taskNode).singleResult();
		return task;
	}
	
	/**
	 * 根据任务节点、流程实例ID 查询个人待办
	 *
	 * @param userID 登录人ID
	 * 
	 * @param processInstanceId 流程实例ID
	 * 
	 * @return task 任务实例对象
	 */
	public static List<Task> findTaskByTaskNodeAndPiIdList(String taskNode, String processInstanceId) {
		List<Task> taskList = taskService.createTaskQuery().
				processInstanceId(processInstanceId).taskDefinitionKey(taskNode).list();

		return taskList;
	}
	
	/**
	 * 流程实例ID 查询代办任务
	 * 
	 * @param processInstanceId 流程实例ID
	 * 
	 * @return task 任务实例对象
	 */
	public static List<Task> findTasksByPiId(String processInstanceId) {
		List<Task> tasks = taskService.createTaskQuery().
				processInstanceId(processInstanceId).list();
		return tasks;
	}

	/**
	 * 流程实例ID 查询代办任务
	 *
	 * @param processInstanceId 流程实例ID
	 *
	 * @return task 任务实例对象
	 */
	public static List<Task> findTasksByPiId(String processInstanceId, String assignee) {
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee)
				.processInstanceId(processInstanceId).list();
		return tasks;
	}
	
	/**
	 * 流程实例ID 查询代办任务
	 * 
	 * @param processInstanceId 流程实例ID
	 * 
	 * @return task 任务实例对象
	 */
	public static List<Task> findTasksByPiIdBskAssg(String processInstanceId, String processInstanceBusinessKey, String assignee) {
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee)
				.processInstanceBusinessKey(processInstanceBusinessKey)
				.processInstanceId(processInstanceId).list();
		return tasks;
	}
	
	/**
	 * 根据taskId查找任务
	 *
	 * @param taskId 任务Id
	 * 
	 * @return task 任务实例对象
	 */
	public static Task findTaskById(String taskId) {
		Task task = taskService.createTaskQuery().
				taskId(taskId).singleResult();
		return task;
	}
	
	/**
	 * 添加备注信息
	 * 
	 * @param userId 登录人Id
	 *
	 * @param taskId 任务Id
	 * 
	 * @param processInstanceId 流程实例Id
	 * 
	 * @param variables 备注信息参数
	 * 
	 * @throws Exception 
	 */
	public static void saveRemarkByTask(String userId, String taskId, String processInstanceId, 
			Map<String, Object> variables) {
		//备注信息
		String remarkContent = (String) variables.get("remarkContent");
		if (StringUtils.isNotBlank(remarkContent)) {
			Authentication.setAuthenticatedUserId(userId);
			taskService.addComment(taskId, processInstanceId, remarkContent);
		}
		String remarkState = String.valueOf(variables.get("remarkState"));
		//设置taskId
		if (StringUtils.isNotBlank(remarkState)) {
			taskService.setVariableLocal(taskId, "remarkState", remarkState);
		}
		
	}
	
	/**
	 * 更新工作流节点任务ID
	 * 
	 * @param userId 登录人Id
	 *
	 * @param taskId 任务Id
	 * 
	 * @throws Exception 
	 */
	public static void updateAssignByTaskID(String userId, String taskId) {
		taskService.setAssignee(taskId, userId);
	}
	
	/**
	 * 根据流程实例Id获取历史批注
	 * 
	 * @param processInstanceId 流程实例Id
	 * 
	 */
	public static List<Comment> findCommentInfo(String processInstanceId) {
		List<Comment> commentList = taskService.getProcessInstanceComments(processInstanceId);
		return commentList;
	}
	
	/**
	 * 判断流程是否结束
	 * 
	 * @param processInstanceId 流程实例Id
	 */
	public static boolean isEndedProcess(String processInstanceId) {
//		   ProcessInstance rpi = runtimeService.createProcessInstanceQuery()//创建流程实例查询对象
//                   .processInstanceId(processInstanceId).singleResult();
		List<Task> tasks = taskService.createTaskQuery().
				processInstanceId(processInstanceId).list();
		   if (tasks == null ||tasks.size() ==0){
               return true;
           }
           return false;
		   }

	/**
	 * 撤销任务
	 * @param procInstId 流程实例Id
	 */
	public static Map<String, Object> executeTask(String procInstId){
		Map<String, Object> retMap = new HashMap<>();
		//取得当前任务
        List<HistoricTaskInstance> currTaskList = historyService.createHistoricTaskInstanceQuery()
        .processInstanceId(procInstId).finished().list();
		if(currTaskList!=null&&currTaskList.size()>1){
        	retMap.put("code", "1");
        	retMap.put("msg", "任务已执行不能撤回，请确认！");
        	return retMap;
        }
		/*runtimeService.createChangeActivityStateBuilder()
	      .processInstanceId(procInstId)
	      .cancelActivityId(cancelActivityId)// taskBefore subtask   taskAfter
	      .startActivityId(startActivityId)
	      .changeState();*/
        // 用户集合
        List<String> userIdList = new ArrayList<>();
		List<Task> nextTasks = taskService.createTaskQuery().processInstanceId(procInstId).list();
		for (Task nextTask : nextTasks) {
			userIdList.add(nextTask.getAssignee());
		    //删除任务
			TaskEntity currentTaskEntity = (TaskEntity) nextTask;
			currentTaskEntity.setExecutionId(null);
			taskService.saveTask(currentTaskEntity);
			taskService.deleteTask(currentTaskEntity.getId(), true);
            historyService.deleteHistoricTaskInstance(nextTask.getId());
		}
		retMap.put("deleteExecuteUser", userIdList);
		retMap.put("code", "2");
    	retMap.put("msg", "任务撤销成功");
        return retMap;
	}
	
	/**
	 * 查找流程实例
	 * @param procInstId 流程实例Id
	 */
	public static ProcessInstance findProInstanceById(String procInstId) {
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(procInstId)// 使用流程实例ID查询
				.singleResult();
		return pi;
	}
	
	/**
	 * 修改任务备注
	 * @param task 任务对象
	 * @param description 任务备注
	 */
	public static void updTaskDescrip(Task task, String description) {
		TaskEntity currentTaskEntity = (TaskEntity) task;
		currentTaskEntity.setDescription(description);
		taskService.saveTask(currentTaskEntity);
	}
	
	/**
	 * 任务转办
	 *
	 * @param userId 登录人ID
	 * 
	 * @param targetUserId 代办人ID
	 * 
	 * @return taskId 任务ID
	 */
	public static void turnTodoTaskById(String taskId, String userId, String targetUserId) {
		//更新工作流任务表执行人
		taskService.setOwner(taskId, userId);
		taskService.setAssignee(taskId, targetUserId);
	}
	
	/**
	 * 任务转办
	 *
	 * @param userId 登录人ID
	 * 
	 * @param targetUserId 代办人ID
	 * 
	 * @return taskId 任务ID
	 */
	public static Task turnTodoTask(String taskId, String userId, String targetUserId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if (task==null) {
			return null;
		}
		//更新工作流任务表执行人
		taskService.setOwner(taskId, userId);
		taskService.setAssignee(taskId, targetUserId);
		return task;
	}
	
	/**
	 * 判断节点是否结束
	 * 
	 * @param taskDefKey 任务key
	 * @param procInstId 流程实例Id
	 */
	public static boolean isEndedTask(String procInstId, String taskDefKey) {
		   boolean bol = true; 
		   List<Task> taskList = taskService.createTaskQuery().taskDefinitionKey(taskDefKey)
				   .processInstanceId(procInstId).list();
		   if (taskList!=null&&taskList.size()>0) {
			    return false;
		   }
		   return bol;
	}
	
	public static void jumpTaskNode(String procInstId, String cancelActivityId, String startActivityId) {
		List<Task> nextTasks = taskService.createTaskQuery().processInstanceId(procInstId).list();
		for (Task nextTask : nextTasks) {
			//删除任务
			TaskEntity currentTaskEntity = (TaskEntity) nextTask;
			currentTaskEntity.setExecutionId(null);
			taskService.saveTask(currentTaskEntity);
		    //taskService.deleteTask(currentTaskEntity.getId(), true);
			//historyService.deleteHistoricTaskInstance(nextTask.getId());
		}
		runtimeService.createChangeActivityStateBuilder()
	      .processInstanceId(procInstId)
	      .cancelActivityId(cancelActivityId)// taskBefore subtask   taskAfter
	      .startActivityId(startActivityId)
	      .changeState();
		//删除多余撤销数据
		List<Task> nextTasksDelt = taskService.createTaskQuery()
				.processInstanceId(procInstId).taskDefinitionKey(cancelActivityId)
				.list();
		for (Task nextTask : nextTasksDelt) {
			//删除任务
			TaskEntity currentTaskEntity = (TaskEntity) nextTask;
			currentTaskEntity.setExecutionId(null);
			taskService.saveTask(currentTaskEntity);
		    taskService.deleteTask(currentTaskEntity.getId(), true);
			historyService.deleteHistoricTaskInstance(nextTask.getId());
		}
	}
	
	public static HistoricTaskInstance findHisTask(String taskId) {
		HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId)
				.singleResult();
	    return historicTaskInstance;
	}
	
	/**
	 * 根据流程实例ID 查询个人代办任务
	 * 
	 * @param processInstanceId 流程实例ID
	 * 
	 * @return task 任务实例对象
	 */
	public static Task findTaskByPiId(String processInstanceId) {
		List<Task> taskList = taskService.createTaskQuery().
				processInstanceId(processInstanceId).list();
		Task task = null;
		if (taskList != null && taskList.size() >0) {
			task = taskList.get(0);
		}
		return task;
	}
	

	  

	  
	  /**
	   * 创建开始节点
	   * @author liJiangHuai
	   * @date 2017年2月6日 上午11:47:41
	   * @param id
	   * @param name
	   * @return
	   */
	  private static StartEvent createStartEvent(String id, String name){
	      StartEvent start = new StartEvent();
	      start.setId(id);
	      start.setName(name);
	      return start;
	  }
	  
	  /**
	   * 创建结束节点
	   * @author liJiangHuai
	   * @date 2017年2月6日 上午11:47:31
	   * @param id
	   * @param name
	   * @return
	   */
	  private static EndEvent createEndEvent(String id, String name){
	      EndEvent end = new EndEvent();
	      end.setId(id);
	      end.setName(name);
	      return end;
	  }
	  
	  /**
	   * 创建连接线
	   * @author liJiangHuai
	   * @date 2017年2月6日 上午11:47:48
	   * @param id
	   * @param name
	   * @return
	   */
	  private static SequenceFlow createFlow(String id, String name){
		   SequenceFlow flow = new SequenceFlow();
		   flow.setId(id);
		   flow.setName(name);
		   return flow;
	  }
	  

	  

	 
	 /**
	   * 验证bpmn绘制是否满足规范
	   * @param model
	   */
	  public static String validationBpmn(BpmnModel model){
		    StringBuilder sb = new StringBuilder();
		    ProcessValidatorFactory processValidatorFactory = new ProcessValidatorFactory();
		    ProcessValidator defaultProcessValidator = processValidatorFactory.createDefaultProcessValidator();
		    List<ValidationError> validate = defaultProcessValidator.validate(model);
		    if (validate != null && validate.size() > 0) {
		        for (ValidationError v : validate) {
		            sb.append(v.getProblem()).append(";");
		        }
		        //System.out.println(validate.get(0).getProblem());
		    }
		    return sb.toString();
	  }
	  


	 public static List<HistoricTaskInstance> findHistoricTaskInstList(String procInstId){
		 List<HistoricTaskInstance> list = ActivitiUtils.historyService
				 .createHistoricTaskInstanceQuery()
				 .processInstanceId(procInstId)
				 .orderByHistoricTaskInstanceStartTime().asc()
				 .list();
		 return list;
	 }
	 

	 



	/**
	 * 删除任务
	 */
	public static void currentNote(String processInstanceId){
		HistoricProcessInstance processInstance= ProcessEngines.getDefaultProcessEngine().
				getHistoryService().createHistoricProcessInstanceQuery().
				processInstanceId(processInstanceId).singleResult();
	}

	/**
	 * 根据流程实例id以及审核人id获取当前审核人的审核节点
	 * @param procInstId
	 * @param userId
	 * @return
	 */
	public static String getAuditTaskDefKey(String procInstId, Integer userId) {
		List<HistoricTaskInstance> list = historyService
				.createHistoricTaskInstanceQuery()
				.processInstanceId(procInstId)
				.taskAssignee(String.valueOf(userId))
				.orderByHistoricTaskInstanceStartTime()
				.asc()
				.list();
		if (list.size()>0){
			return list.get(list.size()-1).getTaskDefinitionKey();
		}
		return null;
//		return historicTaskInstance.getTaskDefinitionKey();
	}
}
