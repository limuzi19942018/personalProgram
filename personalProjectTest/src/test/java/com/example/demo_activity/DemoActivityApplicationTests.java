package com.example.demo_activity;

import com.example.demo_activity.test1.utils.ActivitiUtils;
import com.example.demo_activity.test1.utils.UploadFileUtils;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.api.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoActivityApplication.class)
@WebAppConfiguration
public   class DemoActivityApplicationTests {


	/**
	 * 工作流流程开启方式方式一：根据act_re_procdef表中的key字段查询流程启动
	 */
	@Test
	public void startProecessByKey() {
		String processKey="myProcess_1";
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("employeeName", "张三");
		variables.put("day",4);
		ActivitiUtils.startProcessByKey(processKey,variables);
	}

	/*工作流开启流程方式二：根据act_re_procdef表中的id字段查询流程启动
	* */
	@Test
	public void startProecessById(){
		ProcessDefinition myProcess_1 = ActivitiUtils.findProdfByKey("TwoAuditOff");
		ActivitiUtils.startProcessById(myProcess_1.getId());

	}

	@Test
	public void queryCurrentTask(){
		String processInstanceId="10001";
		//Task task = ActivitiUtils.queryCurrentTask(processInstanceId);
		//ActivitiUtils.completeEmployeeTask(task);
		//ActivitiUtils.completeEmployeeTask(null);
	}


	/*
	发起人发起申请--->下一步到签发（给签发设置三个人）
	* */
	@Test
	public void tijiaoTask(){
		String processInstanceId="32501";
		Map<String, Object> variables = new HashMap<String, Object>();
		ArrayList<String> userLists = new ArrayList<>();
		userLists.add("11102");
		userLists.add("22202");
		userLists.add("33303");
		variables.put("assigneeList1",userLists);
		Task task = ActivitiUtils.queryCurrentTask(processInstanceId);
		ActivitiUtils.completeEmployeeTask(task,variables);
	}


	/*
	签发人签发（准备退回到初审）
	* */
	@Test
	public void qianfaTask(){
		//用userId为111的人来执行签发任务
		Task task = ActivitiUtils.queryTaskByAssignee("22202", "32501");
		Map<String, Object> variables = new HashMap<String, Object>();
		//remarkState状态为1362退回到初审
		variables.put("remarkState","1362");
		ArrayList<String> userLists = new ArrayList<>();
		//444是初审人的id
		//设置两个人审核（串行）
		userLists.add("44403");
		userLists.add("55503");
		variables.put("assigneeList2",userLists);
		ActivitiUtils.completeEmployeeTask(task,variables);
	}

	/*
    初审人审核一
* */
	@Test
	public void chushenTask(){
		String processInstanceId="32501";
		//用userId为111的人来执行签发任务
		Task task = ActivitiUtils.queryCurrentTask(processInstanceId);
		Map<String, Object> variables = new HashMap<String, Object>();
		//remarkState状态为1356退回到发起人
		//variables.put("remarkState","1356");
		//初审通过
		variables.put("remarkState","1355");
		ActivitiUtils.completeEmployeeTask(task,variables);
	}

	/*
    初审人审核二
* */
	@Test
	public void chushenTask2(){
		String processInstanceId="32501";
		//用userId为111的人来执行签发任务
		Task task = ActivitiUtils.queryCurrentTask(processInstanceId);
		ActivitiUtils.completeEmployeeTask(task,null);
	}
	/*
初审人审核二
* */
	@Test
	public void chushenTask3(){
		String processInstanceId="32501";
		//用userId为111的人来执行签发任务
		Task task = ActivitiUtils.queryCurrentTask(processInstanceId);
		Map<String, Object> variables = new HashMap<String, Object>();
		ArrayList<String> userLists = new ArrayList<>();
		userLists.add("11104");
		userLists.add("22204");
		userLists.add("33304");
		//remarkState状态为1356退回到发起人
		//variables.put("remarkState","1356");
		//初审通过到签发
		variables.put("remarkState","1355");
		variables.put("assigneeList1",userLists);
		ActivitiUtils.completeEmployeeTask(task,variables);
	}

	@Test
	public void testUpload(){
		String filePath="C:\\Users\\Lenovo\\Desktop\\哈哈.txt";
		UploadFileUtils fileUtil=new UploadFileUtils();
		byte[] bytes = fileUtil.getBytes(filePath);
		fileUtil.httpPost(bytes,"哈哈.txt");
		//fileUtil.testSocekt(filePath);
	}
}
