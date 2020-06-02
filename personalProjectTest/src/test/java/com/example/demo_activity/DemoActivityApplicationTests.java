package com.example.demo_activity;

import com.example.demo_activity.test1.utils.ActivitiUtils;
import com.example.demo_activity.test1.utils.UploadFileUtils;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.api.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoActivityApplication.class)
@WebAppConfiguration
public   class DemoActivityApplicationTests {


	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;


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

	/*@Test
	public void testUpload(){
		String filePath="C:\\Users\\Lenovo\\Desktop\\哈哈.txt";
		UploadFileUtils fileUtil=new UploadFileUtils();
		byte[] bytes = fileUtil.getBytes(filePath);
		//fileUtil.httpPost(bytes,"哈哈.txt");
		//fileUtil.testSocekt(filePath);
	}*/

	@Test
	public void testRedis(){
		//存map,下面的key类似于map的名字，hashKey类似于map中的key值，value就是key对应的value值
		stringRedisTemplate.opsForHash().put("hello","hashkey2","iamachinese2");
		stringRedisTemplate.opsForHash().put("hello","hashkey1","iamachinese2");
	}


	@Test
	public void testByuseTemplateRedis(){
		//存map,下面的key类似于map的名字，hashKey类似于map中的key值，value就是key对应的value值
		redisTemplate.opsForHash().put("heihei","hashkey_11","chineselongyear1");
		redisTemplate.opsForHash().put("heihei","hashkey_22","chineselongyear2");
	}

	//获取指定变量中 的hashmap值（也就是说value值，直接越过key）
	@Test
	public void getHashMap(){
		List<Object> hashList = stringRedisTemplate.opsForHash().values("hello");
		if(hashList!=null && hashList.size()>0){
			for (Object obj : hashList) {
				System.out.println("通过values(H key)方法获取变量中的hashMap值:" + obj.toString());
			}
		}
	}
	@Test
	public void getmap(){
		//获取变量中的键值对（map对象）。
		//Map<Object,Object> map = stringRedisTemplate.opsForHash().entries("hello");
		Map<Object,Object> map =redisTemplate.opsForHash().entries("heihei");
		if(map!=null && map.size()>0){
			for (Object obj : map.keySet()) {
				System.out.println("通过entries(H key)方法获取变量中的键为:" + obj.toString());
				System.out.println("通过entries(H key)方法获取变量中的值为:" + map.get(obj).toString());
			}
		}
	}

	@Test
	public void getValueBykey(){
		//获取变量中的指定map键是否有值,如果存在该map键则获取值，没有则返回null。
		Object mapValue = redisTemplate.opsForHash().get("hello","hashkey2");
		System.out.println("通过get(H key, Object hashKey)方法获取map键的值:" + mapValue);
	}
}
