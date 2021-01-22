// function $(id) {
//     return document.getElementById(id)
// }
// window.onload = function() {
//     left = $("left"), right = $("right"), middle = $("middle");
//     var middleWidth=9;
//     middle.onmousedown = function(e) {
//         var disX = (e || event).clientX;
//         middle.left = middle.offsetLeft;
//         document.onmousemove = function(e) {
//             var iT = middle.left + ((e || event).clientX - disX);
//             var e=e||window.event,tarnameb=e.target||e.srcElement;
//             maxT=document.body.clientWidth;
//             iT < 0 && (iT = 0);
//             iT > maxT && (iT = maxT);
//             middle.style.left = left.style.width = iT + "px";
//             right.style.width = document.body.clientWidth - iT -middleWidth + "px";
//             right.style.left = iT+middleWidth+"px";
//             return false
//         };
//         document.onmouseup = function() {
//             document.onmousemove = null;
//             document.onmouseup = null;
//             middle.releaseCapture && middle.releaseCapture()
//         };
//         middle.setCapture && middle.setCapture();
//         return false
//     };
// };

$(function() {
    $( "#opsdrag" ).draggable();

});

//实验（手册）编号
var ceid=1;

var vm = new Vue({
    el: '#login-bg',
    data: {
        cname:" ",      //课程名称
        ceintro:" ",    //课程实验介绍
        mbtnshow:false  //控制手机端才显示的按钮
    },
    computed: {

    }
})




$(function(){
    //运行cexperintroshow方法，获取实验手册中的实验标题、实验内容和实验步骤要求
    cexperintroshow(ceid);
    // vm.cname="实验2 DIV+CSS+JS设计网页实验";


    if(_isMobile()){
        vm.mbtnshow=true;
    }



});


//cexperintroshow方法，获取实验手册中的实验标题、实验内容和实验步骤要求
//输入参数：id 实验手册表中的记录id，即该实验的编号
function cexperintroshow(id) {
    var data = {
        id: id
    };

    $.ajax({
        url: "/cexperintroshow",
        type: "Post",
        data:data,
        datatype: "html",
        success: function(data, status) {
            eval(data);
            //运行JS代码，完成实验标题（coursename）和实验内容步骤简介（cei）的赋值
        }
    });
}


function _isMobile() {
    var flag = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i)
    return flag;

}