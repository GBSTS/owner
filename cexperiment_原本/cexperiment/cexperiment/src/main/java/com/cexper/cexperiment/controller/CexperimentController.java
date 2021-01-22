package com.cexper.cexperiment.controller;

import com.cexper.cexperiment.model.Cexperimentnode;
import com.cexper.cexperiment.model.Cexperimentsteps;
import com.cexper.cexperiment.service.CexperimentnodeService;
import com.cexper.cexperiment.service.CexperimentstepsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class CexperimentController {


    @Resource
    private CexperimentnodeService cexperimentnodeService;

    @Resource
    private CexperimentstepsService cexperimentstepsService;


    @RequestMapping("/getcexpnode")
    public String getcexpermentnode(int id){
        String retStr="";

//        Cexperimentsteps cen=cexperimentstepsService.findCexperimentstepsById(id);
//        if(cen !=null){
//            retStr=cen.getIntro();
//        }
        List<Cexperimentsteps> cen=cexperimentstepsService.findCexperimentstepsByCeid(id);
        if(cen !=null){
            retStr=cen.get(1).getIntro();
        }

        return retStr;
    }


    @RequestMapping("/cexperintroshow")
    public String cexperintroshow(int id){
        String retStr="";
        //通过id(实验手册表编号ID)在实验手册表中查找相应的记录
        Cexperimentnode cen=cexperimentnodeService.findCexperimentnodeById(id);
        if(cen !=null){
            //1  对Vue中实验标题数据变量cname进行赋值
            retStr=" vm.cname=\""+cen.getCename().replace("\"","'")+"\";";

            //2 对Vue中实验内容数据变量ceintro进行赋值
            retStr +="vm.ceintro=\""+cen.getIntro().replace("\"","'")+"\";";


            //在ceintro中追加实难步骤
            retStr +="vm.ceintro +=\"<br /><p><strong><span style='font-family: 黑体;font-size: 16px'><span style='font-family:黑体'>实验步骤：</span></span></strong></p>\";";

            //添加实验步骤的具体内容
            //获取实验手册编号为ID的全部实验步骤数据
            List<Cexperimentsteps> cesL=cexperimentstepsService.findCexperimentstepsByCeid(id);
            //判断步骤是否为空
            if(cesL.size()>0){
                for(Cexperimentsteps ces : cesL){ //分步将步骤的内容追加到页面的Vue对象里
                    retStr +="vm.ceintro +=\"<br /><p style='margin-left: 56px;line-height: 22px'> <span style='font-family: 宋体;font-size: 16px'>";
                    retStr +=ces.getNum()+"、&nbsp;</span><span style='font-family: 宋体;font-size: 16px' onclick='optcexperiment("+ces.getId()+")' >"+ces.getIntro()+"</span></p>"+"\";";
                }

            }

        }

        return retStr;
    }





}
