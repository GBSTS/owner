
$(function(){

    initFileInput("test1", "/uploadImg");

});


function showuploadimg(id) {
    var data={id:id};
    $.ajax({
        url: "/showuploadimg",
        type: "Post",
        data:data,
        datatype: "html",
        success: function(data, status) {
            eval(data);
        }
    });
}


function  removediv(id) {
    // if($(".file-drop-zone-title").length>0) $(".file-drop-zone-title").remove();
    $("#fkkf"+id).fadeOut(2000);
    $("#fkkf"+id).remove();
    if(!$.trim($('.file-preview-frame').html())){
        $(".file-drop-zone").prepend("<div class='file-drop-zone-title'>可以将图片拖放到这里 …支持文件上传<br>(或点击文件按钮选择文件)</div>");
    }
}