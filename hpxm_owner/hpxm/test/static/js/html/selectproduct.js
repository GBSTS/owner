var vm = new Vue({
	el: "#selectParam",
	data: { //查询条件
		productId: '',
		productName: '',
		productValueStart: '',
		productValueEnd: '',
		productLocal: '',
		shelvesDateStart: '2020-01-12',
		shelvesDateEnd: '2020-01-12',
		expectedMatureDateStart: '2020-01-12',
		expectedMatureDateEnd: '2020-01-31'
	}
})
var pageNum = 6;
var vm_data = new Vue({
	el: "#endPage",
	data: {
		nowPage: 1,
		sumPage: 3,
		allObjs: [{ //数据库根据条件查询后回复的结果
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a1',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',
			shelvesDate: '2002-01-10',
			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a2',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',
			shelvesDate: '2002-01-10',
			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a3',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',
			shelvesDate: '2002-01-10',
			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a4',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',
			shelvesDate: '2002-01-10',
			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a5',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',
			shelvesDate: '2002-01-10',
			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a6',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',
			shelvesDate: '2002-01-10',
			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a7',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',
			shelvesDate: '2002-01-10',
			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a8',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',
			shelvesDate: '2002-01-10',
			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a9',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',
			shelvesDate: '2002-01-10',
			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',
			shelvesDate: '2002-01-10',
			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',
			shelvesDate: '2002-01-10',
			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',
			shelvesDate: '2002-01-10',
			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',
			shelvesDate: '2002-01-10',
			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',
			shelvesDate: '2002-01-10',
			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',
			shelvesDate: '2002-01-10',
			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',
			shelvesDate: '2002-01-10',
			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a_end',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',
			shelvesDate: '2002-01-10',
			productIntroduction: 'sss'
		}],
		nowObjs: [{ //当前分页的数据
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a_start',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',
			shelvesDate: '2002-01-10',
			productIntroduction: 'sss'
		}]
	},
	watch: {
		allObjs: function() {
			this.sumPage=int(this.allObjs.length/pageNum);
			if(this.allObjs.length%pageNum!=0)
				this.sumPage+=1;
			this.nowPage=1;
		},
		nowPage:function(){
			this.nowObjs=this.allObjs.slice((this.nowPage-1)*pageNum,this.nowPage*pageNum);
		}
	},
	methods:{
		firstBtn:function(){
			this.nowPage=1;
		},
		lastBtn:function(){
			this.nowPage=this.sumPage;
		},
		previousBtn:function(){
			if(this.nowPage!=1)
				this.nowPage-=1;
		},
		nextBtn:function(){
			if(this.nowPage!=this.sumPage)
				this.nowPage+=1;
		}
	}
})
