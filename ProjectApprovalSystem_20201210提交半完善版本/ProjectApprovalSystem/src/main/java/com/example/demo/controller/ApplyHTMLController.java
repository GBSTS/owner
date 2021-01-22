package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.json.ResponseDataSet;
import com.example.demo.json.apply.ApplyHistoryData;
import com.example.demo.json.apply.OwnerSubmitHistory;
import com.example.demo.json.apply.ProvideAppliedItemData;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.example.demo.model.Apply;
import com.example.demo.model.Item;
import com.example.demo.model.Submit;
import com.example.demo.model.User;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Controller
public class ApplyHTMLController {
    @Autowired
    ItemService itemService;
    @Autowired
    ApplyService applyService;
    @Autowired
    UserService userService;
    @Autowired
    FileUploadService fileUploadService;
    @Autowired
    SubmitService submitService;
    @Autowired
    CompanyService companyService;

    @RequestMapping("/apply/mainshow")
    public String apply_mamainshow(){
        return "/apply/mainshow";
    }

    @RequestMapping("/apply/welcome")
    public String apply_welcom(){
        return "/apply/welcome";
    }

    @RequestMapping("/apply/selectitem")
    public String apply_selectitem(){
        return "/apply/selectitem";
    }

    @PostMapping("/apply/getItemSelectionList")
    @ResponseBody
    public String apply_getItemSelectionList(int itemId,int companyId,int limit,int page){
        //获取当前可以申请的所有项目
        //通过itemId、companyId筛选数据
        List<Item> itemArr = itemService.getItemsByTwoCondition(itemId,companyId);
        //获取当前分页对应的数据
        List<Item> itemList=null;
        if(itemArr.size()>=page*limit){
            itemList = itemArr.subList((page-1)*limit,page*limit);
        }else{
            itemList = itemArr.subList((page-1)*limit,itemArr.size());
        }
        //将得到的数据进行封装获取JSON
        ResponseDataSet<ProvideAppliedItemData> responseDataSet = new ResponseDataSet<ProvideAppliedItemData>();
        responseDataSet.setCode(0);
        responseDataSet.setMsg("");
        responseDataSet.setCount(itemArr.size());
        List<ProvideAppliedItemData> list=new ArrayList<ProvideAppliedItemData>();
        for(int i=0;i<itemList.size();i++){
            Item item=itemList.get(i);
            ProvideAppliedItemData provideAppliedItemData = new ProvideAppliedItemData();
            provideAppliedItemData.setItemId(item.getItemId());
            provideAppliedItemData.setItemName(item.getItemName());
            provideAppliedItemData.setCompanyId(item.getCompanyId());
            provideAppliedItemData.setCompanyName(item.getCompanyName());
            provideAppliedItemData.setCurrentHumanNumber(item.getCurrentHumanNumber());
            provideAppliedItemData.setItemDeadline(item.getItemDeadline());
            provideAppliedItemData.setItemDetailed(item.getItemDetailed());
            list.add(provideAppliedItemData);
        }
        responseDataSet.setListOfInfoData(list);
        //发送封装后的JSON数据
        return JSON.toJSONString(responseDataSet);
    }

    @PostMapping("/apply/applyItem")
    @ResponseBody
    public String apply_applyItem(int itemId, HttpSession session){
        User user = userService.getUserByUserId((int)session.getAttribute("userId"));
        if(!applyService.canApply(user.getUserId(),itemId))
            return "无法申请，您已经通过申请或正在等待审批";
        Item item = itemService.getItemByItemId(itemId);
        Apply apply =new Apply(-1,item.getItemId(),item.getItemName(),item.getItemMoney(),
                item.getItemDetailed(),"等待审批","",user.getUserId(),user.getAccount(),"");
        applyService.submitApply(apply);
        return "申请提交成功";
    }

    @RequestMapping("/apply/selectapplyLog")
    public String apply_selectapplyLog(){
        return "/apply/selectapply";
    }

    @PostMapping("/apply/getItemApplyHistory")
    @ResponseBody
    public String apply_getItemApplyHistory(HttpSession session,int limit,int page){
        //获取当前可以申请的所有项目
        //通过itemId、companyId筛选数据
        List<Apply> applyArr;
        if(applyService.getApplyByUserId((int)session.getAttribute("userId"))==null)
            applyArr=new ArrayList<>();
        else
            applyArr = applyService.getApplyByUserId((int)session.getAttribute("userId"));
        //获取当前分页对应的数据
        List<Apply> applyList=null;
        if(applyArr.size()>=page*limit){
            applyList = applyArr.subList((page-1)*limit,page*limit);
        }else{
            applyList = applyArr.subList((page-1)*limit,applyArr.size());
        }
        //将得到的数据进行封装获取JSON
        ResponseDataSet<ApplyHistoryData> responseDataSet = new ResponseDataSet<ApplyHistoryData>();
        responseDataSet.setCount(applyArr.size());
        List<ApplyHistoryData> list=new ArrayList<ApplyHistoryData>();
        for(int i=0;i<applyList.size();i++){
            Apply apply=applyList.get(i);
            ApplyHistoryData applyHistoryData = new ApplyHistoryData(apply);
            list.add(applyHistoryData);
        }
        responseDataSet.setListOfInfoData(list);
        //发送封装后的JSON数据
        return JSON.toJSONString(responseDataSet);
    }

    @PostMapping("/apply/withdrawApply")
    @ResponseBody
    public String apply_withdrawApply(HttpSession session,int applyId){
        if(applyService.drwithdrawApply((int)session.getAttribute("userId"),applyId))
            return "撤回申请成功";
        else
            return "撤回申请失败";
    }

    @RequestMapping("/apply/submitPanel")
    public String apply_submitPanel(){
        return "/apply/submitpanel";
    }

    @PostMapping("/apply/getOwnerItemInfo")
    @ResponseBody
    public String apply_getOwnerItemInfo(HttpSession session){
        String str="vm.$data.itemIdArr=[";
        System.out.println((int)session.getAttribute("userId"));
        System.out.println(applyService.getOwnerItems((int)session.getAttribute("userId")).toString());

        List<Apply> applyList=applyService.getOwnerItems((int)session.getAttribute("userId"));
        for(int i=0 ;i<applyList.size();i++){
            if(i==0)
                str=str+"\""+applyList.get(i).getItemId()+"("+applyList.get(i).getItemName()+")\"";
            else
                str=str+",\""+applyList.get(i).getItemId()+"("+applyList.get(i).getItemName()+")\"";
        }
        str+="]";
        return str;
    }

    @PostMapping("/apply/receivedSubmit")
    public String apply_receivedSubmitFile(HttpSession session,HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("file");
        String itemIdName = request.getParameter("itemId");
        String submitDate = new Date().toLocaleString();
        String submitIntroduction = request.getParameter("submitIntroduction");;
        String filePath="";
        for(MultipartFile file:files) {
            if (!file.isEmpty()) {
                String path = "E:/files/";
                LocalDateTime now = LocalDateTimeUtil.now();
                int year = now.getYear();
                int month = now.getMonthValue();
                int day = now.getDayOfMonth();
                long milli = now.toInstant(ZoneOffset.of("+8")).toEpochMilli();
                path = path + year + month + day + "/";
                if(!new File(path).exists())
                    new File(path).mkdirs();
                String fileName = file.getOriginalFilename();
                File target = new File(path + milli + "_" + fileName);
                try {
                    file.transferTo(target);
                    filePath=filePath+"/files/"+year + month + day + "/"+milli + "_" + fileName+";";
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        filePath=filePath.substring(0,filePath.length()-1);
        int fileId = fileUploadService.insertFileUpload(filePath);
        int itemId = Integer.parseInt(itemIdName.substring(0,itemIdName.indexOf("(")));
        String itemName = itemIdName.substring(itemIdName.indexOf("("),itemIdName.length()-1);
        submitService.insertSubmit((int)session.getAttribute("userId"),itemId,itemName,submitDate,fileId,submitIntroduction,itemService.getCompanyIdByItemId(itemId));
        return "/apply/submitPanel";
    }

    @RequestMapping("/apply/submitHistory")
    public String apply_submitHistory(){
        return "/apply/submithistory";
    }
    @PostMapping("/apply/getSubmitHistory")
    @ResponseBody
    public String apply_getSubmitHistory(HttpSession session){
        List<Submit> submitList= submitService.getSubmitByUserId((int)session.getAttribute("userId"));
        //将得到的数据进行封装获取JSON
        ResponseDataSet<OwnerSubmitHistory> responseDataSet = new ResponseDataSet<OwnerSubmitHistory>();
        responseDataSet.setCode(0);
        responseDataSet.setMsg("");
        responseDataSet.setCount(submitList.size());
        List<OwnerSubmitHistory> list=new ArrayList<>();
        for(int i=0;i<submitList.size();i++){
            Submit item=submitList.get(i);
            OwnerSubmitHistory ownerSubmitHistory = new OwnerSubmitHistory();
            ownerSubmitHistory.setItemId(item.getItemId());
            ownerSubmitHistory.setItemName(item.getItemName());
            ownerSubmitHistory.setSubmitId(item.getSubmitId());
            ownerSubmitHistory.setSubmitDate(item.getSubmitDate());
            ownerSubmitHistory.setSubmitIntroduction(item.getSubmitIntroduction());
            list.add(ownerSubmitHistory);
        }
        responseDataSet.setListOfInfoData(list);
        //发送封装后的JSON数据
        return JSON.toJSONString(responseDataSet);
    }
}
