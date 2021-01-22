var vm = new Vue({
	el: "#selectParam",
	data: { //查询条件
		productId: '',
		productName: '',
		productValueStart: '',
		productValueEnd: '',
		productLocal: '',
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
		realObjs:[{ //数据库根据条件查询后回复并且没有修改的结果
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
		allObjs: [{ //数据库根据条件查询后回复并且经过修改的结果
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
		realObjs: function(){
			this.allObjs=JSON.parse(JSON.stringify(this.realObjs));
		},
		allObjs: function() {
			this.sumPage = parseInt(this.allObjs.length / pageNum);
			if (this.allObjs.length % pageNum != 0)
				this.sumPage += 1;
		},
		nowPage: function() {
			this.nowObjs = this.allObjs.slice((this.nowPage - 1) * pageNum, this.nowPage * pageNum);
			this.allObjs.cop
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
		deleteElement:function(i){
			var j;
			for(j in this.allObjs){
				if(this.allObjs[j].productId==i){
					break;
				}
			}
			this.allObjs.splice(j,1);
			this.nowObjs = this.allObjs.slice((this.nowPage - 1) * pageNum, this.nowPage * pageNum);
		},
		reset:function(){
			this.allObjs=JSON.parse(JSON.stringify(this.realObjs));
			if(this.nowPage==1)
				this.nowObjs = this.allObjs.slice(0 * pageNum, 1 * pageNum);
			else
				this.nowPage=1;
		}
	}
})