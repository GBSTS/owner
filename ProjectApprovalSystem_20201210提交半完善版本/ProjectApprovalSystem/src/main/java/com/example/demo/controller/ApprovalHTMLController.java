package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.json.ResponseDataSet;
import com.example.demo.json.apply.ProvideAppliedItemData;
import com.example.demo.json.demand.OwnerItemSubmitData;
import com.example.demo.model.Company;
import com.example.demo.model.Item;
import com.example.demo.model.Submit;
import com.example.demo.service.CompanyService;
import com.example.demo.service.FileUploadService;
import com.example.demo.service.ItemService;
import com.example.demo.service.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ApprovalHTMLController {
    @Autowired
    CompanyService companyService;
    @Autowired
    ItemService itemService;
    @Autowired
    SubmitService submitService;
    @Autowired
    FileUploadService fileUploadService;

    @RequestMapping("/approval/mainshow")
    public String approval_mainshow(){
        return "/approval/apprmamainshow";
    }

    @RequestMapping("/approval/welcome")
    public String approval_welcome(){
        return "/approval/welcome";
    }

    @RequestMapping("/approval/submitItem")
    public String approval_submitItem(){
        return "/approval/submititem";
    }

    @PostMapping("/approval/receivedSubmit")
    @ResponseBody
    public String approval_receivedSubmit(HttpSession session,String itemName,String itemDeadline,String itemMoney,String submitIntroduction){
        Company company = companyService.getApplyByUserId((int)session.getAttribute("userId"));
        Item item = new Item(-1,itemName,-1,"",0,new Date(itemDeadline),submitIntroduction,Integer.parseInt(itemMoney));
        itemService.insertItem(item,company);
        return "项目提交成功";
    }

    @RequestMapping("/approval/viewSubmit")
    public String approval_viewSubmit(){
        return "/approval/viewsubmit";
    }

    @PostMapping("/approval/getItemSubmit")
    @ResponseBody
    public String approval_getItemSubmit(HttpSession session){
        Company company = companyService.getApplyByUserId((int)session.getAttribute("userId"));
        List<Submit> itemList;
        if(company == null)
            itemList = new ArrayList<>();
        else
            itemList=submitService.getSubmitByItemId(company.getCompanyId());
        if(itemList==null)
            itemList = new ArrayList<>();
        //将得到的数据进行封装获取JSON
        ResponseDataSet<OwnerItemSubmitData> responseDataSet = new ResponseDataSet<OwnerItemSubmitData>();
        responseDataSet.setCode(0);
        responseDataSet.setMsg("");
        responseDataSet.setCount(itemList.size());
        List<OwnerItemSubmitData> list=new ArrayList<>();
        for(int i=0;i<itemList.size();i++){
            Submit submit=itemList.get(i);
            OwnerItemSubmitData ownerItemSubmitData = new OwnerItemSubmitData();
            ownerItemSubmitData.setSubmitId(submit.getSubmitId());
            ownerItemSubmitData.setItemId(submit.getItemId());
            ownerItemSubmitData.setItemName(submit.getItemName());
            ownerItemSubmitData.setSubmitDate(submit.getSubmitDate());
            ownerItemSubmitData.setFileId(fileUploadService.getFileUploadByFileId(submit.getFileId()).getFilePath());
            ownerItemSubmitData.setSubmitIntroduction(submit.getSubmitIntroduction());
            list.add(ownerItemSubmitData);
        }
        responseDataSet.setListOfInfoData(list);
        //发送封装后的JSON数据
        return JSON.toJSONString(responseDataSet);
    }
}
