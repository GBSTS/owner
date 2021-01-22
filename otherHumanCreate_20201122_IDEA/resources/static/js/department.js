//JavaScript代码区域
layui.use(['element', 'carousel'], function() {
    var element = layui.element;
    var carousel = layui.carousel;
    //建造实例
    carousel.render({
        elem: '#test1',
        width: '100%' //设置容器宽度
        ,
        height: '240px ',
        arrow: 'always' //始终显示箭头
        //,anim: 'updown' //切换动画方式
    });
});