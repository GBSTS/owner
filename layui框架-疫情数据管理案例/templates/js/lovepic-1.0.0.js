/**
 * @author 大BUG
 * @version 0.0.1
 * @datetime 2019/03/25
 */
function LovePic() {
    var imgPageArr=[];
    var imgPageIndex=0;
    var imgHrefs=[];
    var pageSize;
    var page;
    var windowWidth;
    var windowHeight;
    var imgBoxMaxWidth;
    var imgBoxMaxHeight;
    var boxOffsetTop;
    var boxOffsetLeft;
    var dataType;
    var alpha;
    var shadeDom;
    var imgShowBoxDom;
    var showImgDom;
    var preListBox;
    var preImgBoxs=[];
    var chosenIndex=0;
    var imgShowIndexDom;
    var changeLastImgBtn;
    var changeNextImgBtn;
    var changeImgBtnOpacity=0;
    var preBoxPosition='left';
    var loveToast;
    var showImgAltDom;
    var changeOneImgBtnOpacity=0;
    function docUrlTips() {
        console.info('如果在使用LovePic插件过程中遇到什么困难，请仔细阅读官网文档');
        console.info('LovePic官网文档地址：http://www.jufanshare.com/content/74.html');
    }
    this.openPhoto=function (a,src) {
        docUrlTips();
        init();
        var aloneImgDom;
        var showOneImgAltDom;
        shadeDom=document.createElement('div');
        shadeDom.style.width=windowWidth+'px';
        shadeDom.style.height=windowHeight+'px';
        shadeDom.style.position='fixed';
        shadeDom.style.top=0;
        shadeDom.style.left=0;
        shadeDom.style.backgroundColor='rgba(0,0,0,'+a+')';
        shadeDom.style.zIndex=99;
        shadeDom.onclick=function (ev) {
            document.body.removeChild(aloneImgDom);
            document.body.removeChild(shadeDom);
            document.body.removeChild(showOneImgAltDom);
        }
        document.body.appendChild(shadeDom);

        var picPreObj=new Image();
        picPreObj.src="undefined" != typeof src.src?src.src:src;
        picPreObj.onload=function (ev) {
            aloneImgDom=document.createElement('img');
            aloneImgDom.src="undefined" != typeof src.src?src.src:src;
            aloneImgDom.style.maxWidth=windowWidth*0.9+'px';
            aloneImgDom.style.maxHeight=windowHeight*0.9+'px';
            aloneImgDom.style.boxShadow='0px 0px 24px 1px #555555';
            aloneImgDom.style.borderRadius='2px';
            aloneImgDom.style.position='fixed';
            aloneImgDom.style.zIndex='100';
            document.body.appendChild(aloneImgDom);
            aloneImgDom.style.left=(windowWidth-aloneImgDom.offsetWidth)/2+'px';
            aloneImgDom.style.top=(windowHeight-aloneImgDom.offsetHeight)/2+'px';

            showOneImgAltDom=document.createElement('div');
            showOneImgAltDom.style.width=aloneImgDom.offsetWidth+'px';
            showOneImgAltDom.style.height='40px';
            showOneImgAltDom.style.lineHeight='40px';
            showOneImgAltDom.style.color='#ffffff';
            showOneImgAltDom.style.fontSize='14px';
            showOneImgAltDom.style.fontWeight='bold';
            showOneImgAltDom.style.backgroundColor='rgba(0,0,0,0.6)';
            showOneImgAltDom.style.position='fixed';
            showOneImgAltDom.style.left=aloneImgDom.offsetLeft+'px';
            showOneImgAltDom.style.top=aloneImgDom.offsetTop+'px';
            showOneImgAltDom.style.textIndent='1em';
            showOneImgAltDom.style.overflow='hidden';
            showOneImgAltDom.style.whiteSpace='nowrap';
            showOneImgAltDom.style.textOverflow='ellipsis';
            showOneImgAltDom.style.zIndex='101';
            showOneImgAltDom.style.opacity='0';
            showOneImgAltDom.innerText="undefined" != typeof src.alt?src.alt:'没有图片相关信息';
            document.body.appendChild(showOneImgAltDom);

            var changeOneImgBtnAnimateShowTimer;
            var changeOneImgBtnAnimateHideTimer;
            aloneImgDom.onmouseover=function (ev) {
                clearInterval(changeOneImgBtnAnimateHideTimer);
                changeOneImgBtnAnimateShowTimer=setInterval(function () {
                    changeOneImgBtnOpacity+=0.02;
                    showOneImgAltDom.style.opacity=changeOneImgBtnOpacity+'';
                    if(changeOneImgBtnOpacity>=1){
                        clearInterval(changeOneImgBtnAnimateShowTimer);
                    }
                },10);
            }
            aloneImgDom.onmouseout=function (ev) {
                clearInterval(changeOneImgBtnAnimateShowTimer);
                changeOneImgBtnAnimateHideTimer=setInterval(function () {
                    changeOneImgBtnOpacity-=0.02;
                    showOneImgAltDom.style.opacity=changeOneImgBtnOpacity+'';
                    if(changeOneImgBtnOpacity<=0){
                        clearInterval(changeOneImgBtnAnimateHideTimer);
                    }
                },10);
            }
            var initToast=new LoveToast();
            initToast.toast({
                msg:'按ESC键退出',
                alpha:0.5,
                liveTime:1000,
                fontSize:32
            });
            window.onkeydown=function (ev) {
                if(ev.key=='Escape'){
                    document.body.removeChild(aloneImgDom);
                    document.body.removeChild(shadeDom);
                    document.body.removeChild(showOneImgAltDom);
                }
            }
        }
    }
    this.openPhotos=function (t,a,prePosition,imgHrefArr) {
        docUrlTips();
        dataType=t;
        alpha=a;
        preBoxPosition=prePosition;
        init();
        setImgs(imgHrefArr);
        creaetShadeBox();
        createImgShowBox();
        createPreList(true);
        createShowImg(imgHrefs[0]);
        loveToast=new LoveToast();
        new LoveToast().toast({
            msg:'按ESC键退出',
            alpha:0.5,
            liveTime:1000,
            fontSize:32
        });
        window.onkeydown=function (ev) {
            if(ev.key=='Escape'){
                closePic();
            }
        }
    }
    function setImgs(imgHrefArr) {
        var length = imgHrefArr.length;
        page=parseInt(imgHrefArr.length/pageSize);
        page=imgHrefArr.length%pageSize==0?page:page+1;
        for (var i = 0; i < page; i++) {
            var pageArrPush=imgHrefArr.slice(i*pageSize,i*pageSize+pageSize);
            imgPageArr.push(pageArrPush);
        }
        imgHrefs = imgPageArr[0];
    }
    function init() {
        windowWidth=window.innerWidth;
        windowHeight=window.innerHeight;
        pageSize=windowHeight<650?5:8;
        pageSize=windowHeight<800&&windowHeight>=650?6:pageSize;
        imgBoxMaxWidth=windowWidth*0.8;
        imgBoxMaxHeight=windowHeight*0.9;
        boxOffsetTop=windowHeight*0.05;
        boxOffsetLeft=windowWidth*0.1;
    }
    function preImgChosen(imgBoxItem) {
        for (var preImgBoxsKey in preImgBoxs) {
            preImgBoxs[preImgBoxsKey].style.border='none';
            chosenIndex=preImgBoxs.indexOf(imgBoxItem);
        }
        imgBoxItem.style.border='2px solid #ffffff';
        changeShowImg(imgHrefs[chosenIndex]);
    }
    function lastPageChange() {
        if(imgPageIndex!=0){
            chosenIndex=pageSize-1;
            imgPageIndex--;
            imgHrefs = imgPageArr[imgPageIndex];
            document.body.removeChild(preListBox);
            preImgBoxs=[];//清空当前页的每个图片Item
            createPreList(false);
            preImgChosen(preImgBoxs[chosenIndex]);
            imgShowIndexDom.innerText=(imgPageIndex+1)+'/'+imgPageArr.length;
        }else{
            new LoveToast().toast({
                msg:'这是第一页',
                alpha:0.5,
                liveTime:1000,
                fontSize:32
            });
        }
    }
    function nextPageChange() {
        if(imgPageIndex<imgPageArr.length-1){
            chosenIndex=0;
            imgPageIndex++;
            imgHrefs = imgPageArr[imgPageIndex];
            document.body.removeChild(preListBox);
            preImgBoxs=[];//清空当前页的每个图片Item
            createPreList(true);
            imgShowIndexDom.innerText=(imgPageIndex+1)+'/'+imgPageArr.length;
        }else {
            new LoveToast().toast({
                msg:'已经是最后一页',
                alpha:0.5,
                liveTime:1000,
                fontSize:32
            });
        }
    }
    function createPreList(isLastOrNextPage) {
        if(showImgDom!=null&&"undefined" != typeof showImgDom&&isLastOrNextPage){
            var prevImgObj=new Image();
            prevImgObj.src=dataType=='array'?imgHrefs[0]:imgHrefs[0].src;
            prevImgObj.onload=function (ev) {
                var naturalHeight = prevImgObj.naturalHeight;
                showImgDom.src=dataType=='array'?imgHrefs[0]:imgHrefs[0].src;
                showImgDom.style.margin=(imgBoxMaxHeight-showImgDom.offsetHeight)/2+'px auto';
                flushImgAltLocation(imgHrefs[0]);
            }
        }
        if(showImgDom!=null&&"undefined" != typeof showImgDom&&!isLastOrNextPage){

            var prevImgObj=new Image();
            prevImgObj.src=dataType=='array'?imgHrefs[imgHrefs.length-1]:imgHrefs[imgHrefs.length-1].src;
            prevImgObj.onload=function (ev) {
                var naturalHeight = prevImgObj.naturalHeight;
                showImgDom.src=dataType=='array'?imgHrefs[imgHrefs.length-1]:imgHrefs[imgHrefs.length-1].src;
                showImgDom.style.margin=(imgBoxMaxHeight-showImgDom.offsetHeight)/2+'px auto';
                flushImgAltLocation(imgHrefs[imgHrefs.length-1]);
            }
        }
        preListBox=document.createElement('div');
        preListBox.style.width=boxOffsetLeft+'px';
        preListBox.style.height='auto';
        preListBox.style.position='fixed';
        if(preBoxPosition=='right'){
            preListBox.style.right=(boxOffsetLeft*0.5)+'px';
        }else {
            preListBox.style.left=(boxOffsetLeft*0.5)+'px';
        }
        // preListBox.style.border='2px solid red';
        preListBox.style.zIndex=100;

        var lastBtn=document.createElement('div');
        lastBtn.style.width='86px';
        lastBtn.style.height='30px';
        lastBtn.style.lineHeight='30px';
        lastBtn.style.textAlign='center';
        lastBtn.style.color='#ffffff';
        lastBtn.style.cursor='pointer';
        lastBtn.style.margin='0px auto';
        lastBtn.style.backgroundColor='#464B54';
        lastBtn.style.fontSize='14px'
        lastBtn.innerText='上一页';
        lastBtn.onclick=function (ev) {
            lastPageChange();
        }
        preListBox.appendChild(lastBtn);

        for (var i = 0; i < imgHrefs.length; i++) {
            var preImgBox=document.createElement('div');
            preImgBox.style.width='80px';
            preImgBox.style.height='80px';
            preImgBox.style.cursor='pointer';
            preImgBox.style.margin='4px auto';
            preImgBox.style.backgroundColor='#464B54';
            preImgBox.style.overflow='hidden';
            if(i==0){
                preImgBox.style.border='2px solid #ffffff';
            }
            var preImgDom=document.createElement('img');
            preImgDom.src=dataType=='array'?imgHrefs[i]:imgHrefs[i].src;
            preImgDom.style.width='auto';
            preImgDom.style.minWidth='80px';
            preImgDom.style.minHeight='80px';
            preImgDom.style.height='100%';

            preImgBox.appendChild(preImgDom);
            preImgBox.onclick=function (ev) {
                preImgChosen(this);
            }
            preListBox.appendChild(preImgBox);
            preImgBoxs.push(preImgBox)
        }

        var nextBtn=document.createElement('div');
        nextBtn.style.width='86px';
        nextBtn.style.height='30px';
        nextBtn.style.lineHeight='30px';
        nextBtn.style.textAlign='center';
        nextBtn.style.color='#ffffff';
        nextBtn.style.cursor='pointer';
        nextBtn.style.margin='0px auto';
        nextBtn.style.backgroundColor='#464B54';
        nextBtn.style.fontSize='14px'
        nextBtn.innerText='下一页';
        nextBtn.onclick=function (ev) {
            nextPageChange();
        }
        preListBox.appendChild(nextBtn);

        imgShowIndexDom=document.createElement('div');
        imgShowIndexDom.style.width='86px';
        imgShowIndexDom.style.height='30px';
        imgShowIndexDom.innerText='1/'+imgPageArr.length;
        imgShowIndexDom.style.textAlign='center';
        imgShowIndexDom.style.lineHeight='30px';
        imgShowIndexDom.style.margin='6px auto';
        imgShowIndexDom.style.fontWeight='bold';
        imgShowIndexDom.style.color='#ffffff';
        imgShowIndexDom.style.fontSize='12px'
        imgShowIndexDom.style.backgroundColor='#464B54';
        // imgShowIndexDom.style.borderRadius='8px';
        preListBox.appendChild(imgShowIndexDom);

        document.body.appendChild(preListBox);
        preListBox.style.top=(windowHeight-preListBox.offsetHeight)/2+'px';
    }
    function changeShowImg(src) {
        var prevImgObj=new Image();
        prevImgObj.src=dataType=='array'?src:src.src;
        prevImgObj.onload=function (ev) {
            var naturalWidth = prevImgObj.naturalWidth;
            var naturalHeight = prevImgObj.naturalHeight;
            showImgDom.src=dataType=='array'?src:src.src;
            showImgDom.style.margin=(imgBoxMaxHeight-showImgDom.offsetHeight)/2+'px auto';
            flushImgAltLocation(src);
        }
    }
    function createShowImg(src) {
        var prevImgObj=new Image();
        prevImgObj.src=dataType=='array'?src:src.src;
        prevImgObj.onload=function (ev) {
            var naturalWidth = prevImgObj.naturalWidth;
            var naturalHeight = prevImgObj.naturalHeight;
            showImgDom=document.createElement('img');
            showImgDom.src=dataType=='array'?src:src.src;
            showImgDom.style.maxWidth='100%';
            showImgDom.style.maxHeight='100%';
            showImgDom.style.display='block';
            showImgDom.style.boxShadow='0px 0px 24px 1px #555555';
            showImgDom.style.borderRadius='2px';
            imgShowBoxDom.appendChild(showImgDom);
            showImgDom.style.margin=(imgBoxMaxHeight-showImgDom.offsetHeight)/2+'px auto';
            showImgAltDom=document.createElement('div');
            showImgAltDom.style.width=showImgDom.offsetWidth+'px';
            showImgAltDom.style.height='40px';
            showImgAltDom.style.lineHeight='40px';
            showImgAltDom.style.color='#ffffff';
            showImgAltDom.style.fontSize='14px';
            showImgAltDom.style.fontWeight='bold';
            showImgAltDom.style.backgroundColor='rgba(0,0,0,0.6)';
            showImgAltDom.style.position='absolute';
            showImgAltDom.style.left=showImgDom.offsetLeft+'px';
            showImgAltDom.style.top=showImgDom.offsetTop+'px';
            showImgAltDom.style.textIndent='1em';
            showImgAltDom.style.overflow='hidden';
            showImgAltDom.style.whiteSpace='nowrap';
            showImgAltDom.style.textOverflow='ellipsis';
            showImgAltDom.style.opacity='0';
            showImgAltDom.innerText=dataType=='array'?'没有图片相关信息':src.alt;
            imgShowBoxDom.appendChild(showImgAltDom);
        }
    }
    function flushImgAltLocation(src) {
        showImgAltDom.style.width=showImgDom.offsetWidth+'px';
        showImgAltDom.style.left=showImgDom.offsetLeft+'px';
        showImgAltDom.style.top=showImgDom.offsetTop+'px';
        showImgAltDom.innerText=dataType=='array'?'没有图片相关信息':src.alt;
    }
    function createChangeImgBtn() {
        changeLastImgBtn=document.createElement('div');
        changeLastImgBtn.style.width='300px';
        changeLastImgBtn.style.height='100px';
        changeLastImgBtn.style.borderRadius='16px';
        changeLastImgBtn.style.textAlign='center';
        changeLastImgBtn.style.lineHeight='100px';
        changeLastImgBtn.style.position='absolute';
        changeLastImgBtn.style.cursor='pointer';
        changeLastImgBtn.style.left=((imgBoxMaxWidth-300)/2)+'px';
        changeLastImgBtn.style.top='100px';
        changeLastImgBtn.style.backgroundColor='rgba(0,0,0,0.2)';
        changeLastImgBtn.style.fontWeight='bold';
        changeLastImgBtn.style.fontSize='24px';
        changeLastImgBtn.style.color='#ffffff';
        changeLastImgBtn.innerText='上一张'
        changeLastImgBtn.style.opacity='0';
        changeLastImgBtn.onclick=function (ev) {
            var lastImgShowBoxItemIndex=chosenIndex-1;
            if(lastImgShowBoxItemIndex>=0){
                preImgChosen(preImgBoxs[lastImgShowBoxItemIndex]);
            }else {
                lastPageChange();
            }
        }


        changeNextImgBtn=document.createElement('div');
        changeNextImgBtn.style.width='300px';
        changeNextImgBtn.style.height='100px';
        changeNextImgBtn.style.borderRadius='16px';
        changeNextImgBtn.style.textAlign='center';
        changeNextImgBtn.style.lineHeight='100px';
        changeNextImgBtn.style.position='absolute';
        changeNextImgBtn.style.cursor='pointer';
        changeNextImgBtn.style.left=((imgBoxMaxWidth-300)/2)+'px';
        changeNextImgBtn.style.bottom='100px';
        changeNextImgBtn.style.backgroundColor='rgba(0,0,0,0.2)';
        changeNextImgBtn.style.fontWeight='bold';
        changeNextImgBtn.style.fontSize='24px';
        changeNextImgBtn.style.color='#ffffff';
        changeNextImgBtn.innerText='下一张'
        changeNextImgBtn.style.opacity='0';
        changeNextImgBtn.onclick=function (ev) {
            var lastImgShowBoxItemIndex=chosenIndex+1;
            if(lastImgShowBoxItemIndex<preImgBoxs.length){
                preImgChosen(preImgBoxs[lastImgShowBoxItemIndex]);
            }else {
                nextPageChange();
            }
        }

        imgShowBoxDom.appendChild(changeLastImgBtn);
        imgShowBoxDom.appendChild(changeNextImgBtn);
    }
    function createImgShowBox() {
        imgShowBoxDom=document.createElement('div');
        imgShowBoxDom.style.width=imgBoxMaxWidth+'px';
        imgShowBoxDom.style.height=imgBoxMaxHeight+'px';
        imgShowBoxDom.style.position='fixed';
        imgShowBoxDom.style.top=boxOffsetTop+'px';
        if(preBoxPosition=='right'){
            imgShowBoxDom.style.left=(0.5*boxOffsetLeft)+'px';
        }else{
            imgShowBoxDom.style.left=(1.5*boxOffsetLeft)+'px';
        }
        // imgShowBoxDom.style.border='2px solid red';
        imgShowBoxDom.style.zIndex=100;
        createChangeImgBtn();
        var changeImgBtnAnimateShowTimer;
        var changeImgBtnAnimateHideTimer;
        imgShowBoxDom.onmouseover=function (ev) {
            clearInterval(changeImgBtnAnimateHideTimer);
            changeImgBtnAnimateShowTimer=setInterval(function () {
                changeImgBtnOpacity+=0.02;
                showImgAltDom.style.opacity=changeImgBtnOpacity+'';
                changeLastImgBtn.style.opacity=changeImgBtnOpacity+'';
                changeNextImgBtn.style.opacity=changeImgBtnOpacity+'';
                if(changeImgBtnOpacity>=1){
                    clearInterval(changeImgBtnAnimateShowTimer);
                }
            },5);

        }
        imgShowBoxDom.onmouseout=function (ev) {
            clearInterval(changeImgBtnAnimateShowTimer);
            changeImgBtnAnimateHideTimer=setInterval(function () {
                changeImgBtnOpacity-=0.02;
                showImgAltDom.style.opacity=changeImgBtnOpacity+'';
                changeLastImgBtn.style.opacity=changeImgBtnOpacity+'';
                changeNextImgBtn.style.opacity=changeImgBtnOpacity+'';
                if(changeImgBtnOpacity<=0){
                    clearInterval(changeImgBtnAnimateHideTimer);
                }
            },5);
        }
        document.body.appendChild(imgShowBoxDom);
    }
    function creaetShadeBox() {
        shadeDom=document.createElement('div');
        shadeDom.style.width=windowWidth+'px';
        shadeDom.style.height=windowHeight+'px';
        shadeDom.style.position='fixed';
        shadeDom.style.top=0;
        shadeDom.style.left=0;
        shadeDom.style.backgroundColor='rgba(0,0,0,'+alpha+')';
        shadeDom.style.zIndex=99;
        shadeDom.onclick=function (ev) {
            closePic();
        }
        document.body.appendChild(shadeDom);
    }
    function defineStyle() {
        var styleElement = document.createElement('style');

    }
    function closePic() {
        document.body.removeChild(preListBox);
        document.body.removeChild(imgShowBoxDom);
        document.body.removeChild(shadeDom);
    }
}
function LoveToast() {
    var windowWidth;
    var windowHeight;
    var message;
    var alpha;
    var time;
    var fontSize;
    var toastDom;
    this.toast=function (config) {
        message="undefined" != typeof config.msg?config.msg:'';
        alpha="undefined" != typeof config.alpha?config.alpha:1;
        time="undefined" != typeof config.liveTime?config.liveTime:3000;
        fontSize="undefined" != typeof config.fontSize?config.fontSize:12;
        init();
        create();
    }
    function create() {
        var maxWidth=300;
        var lineHeight=20;
        var paddingH=30;
        var paddingV=20;
        toastDom=document.createElement('div');
        toastDom.style.maxWidth=maxWidth+'px';
        toastDom.style.position='fixed';
        toastDom.style.left=(windowWidth-maxWidth-(2*paddingH))/2+'px';
        toastDom.style.top=windowHeight*0.4+'px';
        toastDom.style.padding=paddingV+'px '+paddingH+'px';
        toastDom.style.zIndex='1000';
        toastDom.style.lineHeight=lineHeight+'px';
        toastDom.style.fontSize=fontSize+'px';
        toastDom.style.borderRadius='4px';
        toastDom.style.backgroundColor='rgba(0,0,0,'+alpha+')';
        toastDom.style.color='#ffffff';
        toastDom.style.opacity='0';
        toastDom.innerText=message;
        document.body.appendChild(toastDom);
        var opaticy=0;
        var showToastTimer=setInterval(function () {
            opaticy+=0.02;
            toastDom.style.opacity=opaticy;
            if(opaticy>=1){
                clearInterval(showToastTimer);
                setTimeout(function () {
                    var hideOpacity=1;
                    var hideToastTimer=setInterval(function () {
                        hideOpacity-=0.02;
                        toastDom.style.opacity=hideOpacity;
                        if(hideOpacity<=0){
                            clearInterval(hideToastTimer);
                            document.body.removeChild(toastDom);
                        }
                    },10);
                },time);
            }
        },10);
    }
    function init() {
        windowWidth=window.innerWidth;
        windowHeight=window.innerHeight;
    }
}