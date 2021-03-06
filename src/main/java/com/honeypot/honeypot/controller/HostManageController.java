package com.honeypot.honeypot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.honeypot.honeypot.entity.ModelUtil;
import com.honeypot.honeypot.entity.*;
import com.honeypot.honeypot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.honeypot.honeypot.entity.ModelUtil;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Controller
@CrossOrigin
@ResponseBody
public class HostManageController {
    @Autowired
    private HostManagementService hostManagementService;
    @Autowired
    private ServerService serverService;

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CityService cityService;

    @Autowired
    private PotService potService;
    @Autowired
    private ModelService modelService;
    /**
     * 获取不同状态值的数量
     * 没问题了
     * @return
     */
    @GetMapping(value = "/getStateCounts")
    public Map<String, String> getStateCounts(){
        Map<String, String> state = new HashMap<String, String>();
        state.put("monitor", String.valueOf(hostManagementService.getStateCount(1)));
        state.put("pause", String.valueOf(hostManagementService.getStateCount(3)));
        state.put("unknown", String.valueOf(hostManagementService.getStateCount(-1)));
        state.put("wait", String.valueOf(hostManagementService.getStateCount(2)));
        return state;
    }

    /**
     * 没问题
     * @return
     */
    @GetMapping("/getHostTypeCounts")
    public List<Map<String, String>> getHostTypeCounts(){
        List<HostType> hostTypes = hostManagementService.getHostTypes();
        List<Map<String, String>> count = new ArrayList<Map<String,String>>();
        for(HostType h:hostTypes){
            Map<String, String> host = new HashMap<String, String>();

            host.put("label", h.getTypeName());
            host.put("data", String.valueOf(hostManagementService.getHostTypeCount(h.getId())));
            count.add(host);
        }
        return count;

    }

    /**
     *获取省市
     * @return
     */
    @GetMapping("/getDepCounts")
    public List<Map<String, String>> getDepCounts(){

        List<Province> provinces = hostManagementService.getProvinces();
        List<Map<String, String>> modelMapcount = new ArrayList<Map<String,String>>();
        for(Province department : provinces){
            Map<String, String> dep = new HashMap<String, String>();
            dep.put("label", department.getName());
            dep.put("data", String.valueOf(hostManagementService.getDepTypeCount(department.getId())));
            modelMapcount.add(dep);
        }
        return  modelMapcount;
    }

    /**
     * 没问题了
     * @return
     */
    @GetMapping("/getOsCounts")
    public List<Map<String, String>> getOsCounts(){
        List<OsType> osTypes = hostManagementService.getOsTypes();
        List<Map<String, String>> modelMapcount = new ArrayList<Map<String,String>>();
        for(OsType osType : osTypes){
            Map<String, String> dep = new HashMap<String, String>();
            dep.put("label", osType.getTypeName());
            dep.put("data", String.valueOf(hostManagementService.getOsCount(osType.getId())));
            modelMapcount.add(dep);
        }

        return modelMapcount;
    }

    /**
     * 暂时没有问题
     * @return
     */
    @GetMapping("/getWarnings")
    public List<WarningCriterial> getWarnings(){
        List<WarningCriterial> warnings = hostManagementService.getLastWarning();
        return  warnings;
    }

    /**
     * 查找所有服务器
     * @return
     */
    @GetMapping("/getAllServer")
    public Map<String,Object> getAllServer(){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        List<Server> serverList = serverService.getAllServer();
        modelMap.put("serverList",serverList);
        if (serverList.size() > 0){
            modelMap.put("success",true);
        }else {
            modelMap.put("success",false);

        }
        return modelMap;
    }

    /**
     * 查找服务器,还有问题
     * @param server
     * @return
     */
    @GetMapping("/getServer")
    public Map<String,Object> getServer(@RequestParam(value = "server",required = false, defaultValue = "") Server server, HttpSession session){
        System.out.println("用户名是：：：：：" + session.getAttribute("username"));
        Map<String,Object> modelMap = new HashMap<String,Object>();
        Server server1 = server;
        List<Server> serverList = null;
        if (server1==null){
            serverList = serverService.getAllServer();
        }else {
            if (null != new Integer(server1.getId())){
                serverList = serverService.getServerByServerId(new Integer(server1.getId()));
            }else if (null != server1.getServer() && !server1.getServer().equals("")){
                serverList = serverService.getServerByServerName(server1.getServer());
            }else {
                modelMap.put("errMsg","操作错误");
            }

        }
        modelMap.put("serverList",serverList);
        if (serverList.size() > 0){
            modelMap.put("success",true);
        }else {
            modelMap.put("success",false);

        }
        return modelMap;
    }

    /**
     * 增加服务器
     * @param server
     * @return
     */
    @PostMapping("/addServer")
    public Map<String,Object> addServer(@RequestParam(value = "server") String server,@RequestParam(value = "serverIp")String serverIp){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        Server serverNew = new Server();
        serverNew.setServerIp(serverIp);
        serverNew.setServer(server);
        List<Server> serverList = null;
        if (serverNew != null){
            int success = serverService.addServer(serverNew);
            serverList = serverService.getAllServer();
            modelMap.put("success",true);
        }else {
            modelMap.put("success",false);
        }
        modelMap.put("serverList",serverList);
        return modelMap;
    }

    /**
     * 删除服务器
     * @param id
     * @return
     */
    @GetMapping("/delServer")
    public Map<String,Object> delServer(@RequestParam(value = "id") int id){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        Server server1 = new Server();
        server1.setId(id);
        int ifSuccess = 0;
        if (server1.getId() >0){
            ifSuccess = serverService.delServerByServerId(new Integer(server1.getId()));
        }else {
            modelMap.put("success",false);
        }
        if (ifSuccess == 0){
            modelMap.put("success",false);
        }else {

        }
        return modelMap;

    }

    /**
     * 应用蜜罐-应用蜜罐信息页面，，用于获取应用蜜罐信息
     * @return
     */
    @GetMapping("/getListHost")
    @ResponseBody
    public List<Pot> listHost() {
//
//        if(hostType != null && hostType == 0){
//            hostType = null;
//        }
//        if(osType != null && osType == 0){
//            osType = null;
//        }
//        if(state != null && state == 0){
//            state = null;
//        }
//        if(city != null && city == 0){
//            city = null;
//        }
//        if(province != null && province == 0){
//            province = null;
//        }
//        if(city != null && city == 0){
//            city = null;
//        }
//
//        if(macAddress != null && isIllegal(macAddress)){
//            return "wformat";
//        }
//        if(ip != null && isIllegal(ip)){
//            return "wformat";
//        }
//        if(username != null && isIllegal(username)){
//            return "wformat";
//        }
//        if(cpu != null && isIllegal(cpu)){
//            return "wformat";
//        }
//        if(memory != null && isIllegal(memory)){
//            return "wformat";
//        }
//        if(hostname != null && isIllegal(hostname)){
//            return "wformat";
//        }

//        Object[] values = new Object[]{macAddress, ip, hostname, username, hostType, osType, cpu, memory, state, company, city, province};
////        System.out.println("p:"+province);
////        String hql = null;
////        PageBean pageBean = houHostManagementService.queryForPage(pageSize, currentPage, hql, values);
        List<OsType> osTypes = hostManagementService.getOsTypes();
        List<HostType> hostTypes = hostManagementService.getHostTypes();
        List<Department> departments = departmentService.getAllDepartment();
        List<StateType> stateTypes = hostManagementService.getAllStateType();
        List<Province> provinces = hostManagementService.getProvinces();
        List<Pot> potList = potService.getPotByType("");   //暂时把type写死
//        requestAwareMap.put("page", pageBean);
        Map<String, Object> modelMap = new HashMap<String, Object>();
//        modelMap.put("osTypes", osTypes);
//        modelMap.put("hostTypes", hostTypes);
//        modelMap.put("departments", departments);
//        modelMap.put("stateTypes", stateTypes);
//        modelMap.put("provinces", provinces);
        modelMap.put("potList",potList);
        return potService.getPotByType("");
//        for (Province p1 : provinces) {
//            modelMap.put("osTypes", osTypes);
//            modelMap.put("hostTypes", hostTypes);
//            modelMap.put("departments", departments);
//            modelMap.put("stateTypes", stateTypes);
//            modelMap.put("provinces", provinces);
//            return modelMap;
//            modelMap.put("macAddress1", macAddress);
//            modelMap.put("ip1", ip);
//            modelMap.put("hostname1", hostname);
//            modelMap.put("username1", username);
//            modelMap.put("hostType1", hostType);
//            modelMap.put("osType1", osType);
//            modelMap.put("cpu1", cpu);
//            modelMap.put("memory1", memory);
//            modelMap.put("state1", state);
//            modelMap.put("departmentId1", departmentId);
//            System.out.println("ListHost done...");
//            System.out.println(p1.getName());
//        }
//        return "success";
//        }
    }

    /**
     * 通过ip获取Pot信息
     * @param ip
     * @return
     */
    @GetMapping("/getPotByIp")
    public List<Pot> getPotByIp(@RequestParam(value = "ip")String ip){
        return potService.getPotByIp(ip+"%");
    }

    /**
     * 获取模板列表
     * @return
     */
    @PostMapping("/getModelList")
    public List<ModelUtil> getModelList(){
        List<Model> modelList = new ArrayList<>();
        List<ModelUtil> modelUtilList = new ArrayList<>();
        modelList = modelService.getModelList();
        int i = 1;
        for(Model model : modelList){
            modelUtilList.add(new ModelUtil(i,model.getId(),model.getName(),model.getType(),
                    model.getIp(),model.getServer(),
                    model.getServerId(),model.getServerIp()));
            i++;
        }
        return modelUtilList;
    }

    /**
     * 增加模板方法
     *
     * @return
     */
    @PostMapping("/addModel")
    public Map<String,Object> addModel(@RequestBody JSONObject jsonObject){
        Map<String,Object> modelMap = new HashMap<>();
        Model model = new Model();
        model.setIp(jsonObject.getString("ip"));
        model.setName(jsonObject.getString("modelName"));
        model.setServerIp(jsonObject.getString("serverIp"));
        model.setType(jsonObject.getString("honeyType"));
        if (model != null && modelService.addModel(model) != 1){
            modelMap.put("success",true);
        }else{
            modelMap.put("success",false);
        }
        return modelMap;
    }
//    @PostMapping(value = "/delectModel1")
//    @ResponseBody
//    public JSONObject delectModel(@RequestBody JSONObject id){
//        JSONObject modelMap = new JSONObject();
//        System.out.println(id);
//        if (id == null &&modelService.delectModel(Integer.parseInt(String.valueOf(id))) != 1){
//            modelMap.put("success",false);
//        }else {
//            modelMap.put("success",true);
//        }
//        return modelMap;
//    }

    /**
     * 删除模板，批量删除功能暂时没有完成
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delectModel", method = RequestMethod.POST)
    public JSONObject addUser(@RequestBody JSONObject id){
        JSONObject modelMap = new JSONObject();
//        JSONArray modelUtils = id.getJSONArray("modelList");
//        JSON.parse(String.valueOf(id));
//        System.out.print(id.getJSONArray("modelList"));
//        System.out.print(JSON.parseObject(JSON.parseArray(id.getString("modelList")).get(0),ModelUtil));

//        for(Object modelUtil : modelUtils){
//
//        }
        int isSuccess = modelService.delectModel(id.getInteger("id"));
        if (id.getInteger("id") != null && isSuccess!= 1){
            modelMap.put("success",false);
        }else {
            modelMap.put("success",true);
        }
        return modelMap;
    }

}
