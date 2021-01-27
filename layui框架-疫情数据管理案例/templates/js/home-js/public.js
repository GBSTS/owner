
    function countUPUP(id,endVal,num)
    {

        var dom= $('#'+id+'');
        var html="<i class=\"ok-icon\">";//&#xe79c;</i><cite style="font-size: x-small">13</cite>";
        endVal=(endVal/10000).toFixed(2)+'万';
        if(num>=0)
        {
           if(num>1000000)
               num=(num/10000).toFixed(2)+'万';
            html+='&#xe79c;</i><cite style="font-size: x-small">'+num+'</cite>';
        }
        else
        {
            num=-num;
            html+='&#xe79b;</i><cite style="font-size: x-small">'+num+'</cite>';
        }
        dom.append(endVal+html)

    }
    function selectSixData(formData) {
        $.ajax({
            url: "../json/tongji/sixdata.json",
            data: formData,
            dataType: "json",
            type: "post",
            success: function (data) {
                if (data["code"] === 0) {
                    okLayx.notice({
                        title: "成功",
                        type: "success",
                        message: data["msg"]
                    });
                    return data;
                } else if (data["code"] === 300) {
                    okLayx.notice({
                        title: "错误",
                        type: "error",
                        message: data["msg"]
                    });
                } else {
                    okLayx.notice({
                        title: "错误",
                        type: "error",
                        message: data["msg"]
                    });
                }
            }
        })
    }
    function selectDate(formData) {
        $.ajax({
            url: "",
            data: formData,
            dataType: "json",
            type: "post",
            success: function (data) {
                if (data["code"] === 0) {
                    okLayx.notice({
                        title: "成功",
                        type: "success",
                        message: data["msg"]
                    });
                    return data;
                } else if (data["code"] === 300) {
                    okLayx.notice({
                        title: "错误",
                        type: "error",
                        message: data["msg"]
                    });
                } else {
                    okLayx.notice({
                        title: "错误",
                        type: "error",
                        message: data["msg"]
                    });
                }
            }
        })
    }
    function selectData(formData) {
        $.ajax({
            url: "",
            data: formData,
            dataType: "json",
            type: "post",
            success: function (data) {
                if (data["code"] === 0) {
                    okLayx.notice({
                        title: "成功",
                        type: "success",
                        message: data["msg"]
                    });
                    return data;
                } else if (data["code"] === 300) {
                    okLayx.notice({
                        title: "错误",
                        type: "error",
                        message: data["msg"]
                    });
                } else {
                    okLayx.notice({
                        title: "错误",
                        type: "error",
                        message: data["msg"]
                    });
                }
            }
        })
    }