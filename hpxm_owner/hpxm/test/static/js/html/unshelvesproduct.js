var vm = new Vue({
	el: "#selectParam",
	data: { //查询条件
		productId: '',
		productName: '',
		productValueStart: '',
		productValueEnd: '',
		productLocal: ''
	}
})
var pageNum = 6;
var vm_data = new Vue({
	el: "#endPage",
	data: {
		nowPage: 1,
		sumPage: 3,
		selectArr:[{ //数据库根据条件查询后回复的结果
			productId: 'a1',
			selectable:false
		}, {
			productId: 'a2',
			selectable:false
		}, {
			productId: 'a3',
			selectable:false
		}, {
			productId: 'a4',
			selectable:false
		}, {
			productId: 'a5',
			selectable:false
		}, {
			productId: 'a6',
			selectable:false
		}, {
			productId: 'a7',
			selectable:false
		}, {
			productId: 'a8',
			selectable:false
		}, {
			productId: 'a9',
			selectable:false
		}, {
			productId: '1',
			selectable:false
		}, {
			productId: '1',
			selectable:false
		}, {
			productId: '1',
			selectable:false
		}, {
			productId: '1',
			selectable:false
		}, {
			productId: '1',
			selectable:false
		}, {
			productId: '1',
			selectable:false
		}, {
			productId: '1',
			selectable:false
		}, {
			productId: '1',
			selectable:false
		}],
		allObjs: [{ //数据库根据条件查询后回复的结果
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a1',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',

			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a2',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',

			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a3',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',

			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a4',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',

			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a5',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',

			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a6',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',

			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a7',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',

			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a8',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',

			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a9',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',

			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',

			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',

			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',

			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',

			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',

			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',

			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',

			productIntroduction: 'sss'
		}, {
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a_end',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',

			productIntroduction: 'sss'
		}],
		nowObjs: [{ //当前分页的数据
			imgUrl: '../static/images/2034043.jpg',
			productId: 'a_start',
			productName: 'd',
			productLocal: 'c',
			productValue: 'e',
			expectedMatureDate: '2001-12-11',

			productIntroduction: 'sss'
		}]
	},
	watch: {
		allObjs: function() {
			this.sumPage = parseInt(this.allObjs.length / pageNum);
			if (this.allObjs.length % pageNum != 0)
				this.sumPage += 1;
			for(var i=0;i<this.allObjs.length;i++)
				this.selectArr.push({
					productId:this.allObjs[i].productId,
					selectable:false
				});
		},
		nowPage: function() {
			this.nowObjs = this.allObjs.slice((this.nowPage - 1) * pageNum, this.nowPage * pageNum);
		}
	},
	methods: {
		firstBtn: function() {
			this.nowPage = 1;
		},
		lastBtn: function() {
			this.nowPage = this.sumPage;
		},
		previousBtn: function() {
			if (this.nowPage != 1)
				this.nowPage -= 1;
		},
		nextBtn: function() {
			if (this.nowPage != this.sumPage)
				this.nowPage += 1;
		},
		selectAll:function(){
			for(var i=0;i<this.selectArr.length;i++)
			this.selectArr[i].selectable=true;
		},
		selectOppsite:function(){
			for(var i=0;i<this.selectArr.length;i++)
			this.selectArr[i].selectable=!this.selectArr[i].selectable;
		}
	}
})
// 需要发送selectArr数组给后端，后端分析数值，"true"的用insert语句,false的跳过。
