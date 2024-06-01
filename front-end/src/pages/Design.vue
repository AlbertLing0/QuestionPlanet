<template>
	<div class="design">
		<div class="qu-wrap">
            <header>                
				<span @click="iterator = backBtn(); iterator.next()">&lt; 返回</span>
				<p v-show="!titleEditing" @click="titleEditing = true">{{ tempTitle }}</p>
				<input type="text"
				v-focus
				v-model="title"
				:class="{inlineShow: titleEditing}"
				@focus="_title = title"
				@keyup.esc="cancelTitleEdit()"
				@blur="doneTitleEdit()"
				@keyup.enter="doneTitleEdit()">
            </header>

			<header2>
				<p v-show="!descEditing" @click="descEditing = true">{{ description }}</p>
				<input type="text"
				v-focus
				v-model="description"
				:class="{inlineShow: descEditing}"
				@focus="_description = description"
				@keyup.esc="cancelDescEdit()"
				@blur="doneDescEdit()"
				@keyup.enter="doneDescEdit()">
			</header2>

			<div class="qu-content">
				<section class="qu-item"
				v-for="(item, index) in questions"
				:class="{nowEditing: curIndex === index && topicEditing, queEditing: curIndex2 === index && queDescEditing, optEditing: curIndex === index}">

					<h3 @click="curIndex = index; curOptIndex=''; topicEditing = true">
						<span class="qu-num">{{`Q${index + 1}`}}</span>
						<span class="qu-topic">{{ item.questionTitle }}</span>
						<input type="text"
						v-focus
						v-model="item.questionTitle"
						:class="{inlineShow: topicEditing}"
						@focus="_questionTitle = item.questionTitle"
						@blur="doneTopicEdit(index); curIndex=''; curIndex2=''"
						@keyup.esc="cancelTopicEdit(index)"
						@keyup.enter="doneTopicEdit(index)">
						<span v-if="item.questionIsMandatory"> *</span>
					</h3>

					<template v-if="item.questionType === 3">
						<textarea rows="8" cols="80"></textarea>
						<label id="require-check">
							<input type="checkbox"
							v-model:="item.questionIsMandatory">
							是否必填？
						</label>
						<label id="require-check" style="margin-top: 2rem; font-size: 18px; color: orange;">
							文本题
						</label>
					</template>

					<ul v-else-if="item.questionType === 4">
						<div style="margin: 0 0 1.5rem 0; font-size: 40px;" id="stars">☆☆☆☆☆</div>
						<label>
							<label id="require-check">
								<input type="checkbox"
								v-model:="item.questionIsMandatory">
								是否必填？
							</label>
							<label id="require-check" style="margin-top: 2rem; font-size: 18px; color: orange;">
								打分题
							</label>
						</label>
					</ul>

					<ul v-else class="options-list" >
						<li v-for="(option, optIndex) in item.questionOption"
							:class="{optionEditing: curOptIndex === optIndex}">
							<span class="optionText"
								  @click="curIndex=index; curIndex2=index; curOptIndex=optIndex; topicEditing = false; queDescEditing = false">{{ option }}</span>
							<input type="text"
							v-focus
							v-model="this.questions[index].questionOption[optIndex]"
							:class="{inlineShow: curOptIndex}"
							@focus="_optionText = this.questions[index].questionOption[optIndex]"
							@blur="doneOptionEdit(index, optIndex); curIndex=''; curIndex2=''"
							@keyup.esc="cancelOptionEdit(index, optIndex)"
							@keyup.enter="doneOptionEdit(index, optIndex)">

							<ul class="opt-ctrl">
								<li v-if="optIndex !== 0"
								@click="moveUp(optIndex, item.questionOption)">上移</li>
								<li v-if="optIndex !== item.questionOption.length - 1"
								@click="moveDown(optIndex, item.questionOption)">下移</li>
								<li v-else @click="addOption(item.questionOption)">添加</li>
								<li @click="delOption(optIndex, item.questionOption)">删除</li>
							</ul>
						</li>
						<label v-if="item.questionType === 1">
							<label id="require-check">
								<input type="checkbox"
								v-model:="item.questionIsMandatory">
								是否必填？
							</label>
							<label id="require-check" style="margin-top: 2rem; font-size: 18px; color: orange;">
								单选题
							</label>
						</label>
						<label v-else>
							<label id="require-check">
								<input type="checkbox"
								v-model:="item.questionIsMandatory">
								是否必填？
							</label>
							<label id="require-check" style="margin-top: 2rem; font-size: 18px; color: orange;">
								多选题
							</label>
						</label>
					</ul>

					<h3 style="margin: 0.8rem 0 0 0; color: grey;"
					@click="curIndex2 = index; curOptIndex=''; queDescEditing = false">
						<span class="qu-topic2">{{ item.questionDescription }}</span>
						<input2 type="text"
						v-focus
						v-model="item.questionDescription"
						:class="{inlineShow: queDescEditing}"
						@focus="_questionDescription = item.questionDescription"
						@blur="doneQueDescEdit(index); curIndex=''; curIndex2=''"
						@keyup.esc="cancelQueDescEdit(index)"
						@keyup.enter="doneQueDescEdit(index)"/>
					</h3>
					
					<ul class="operat-list">
						<li v-if="index !== 0"
							@click="moveUp(index, questions)">上移</li>
						<li v-if="index !== questions.length - 1"
							@click="moveDown(index, questions)">下移</li>
						<li @click="reuse(index)">复用</li>
						<li @click="delQuestion(index)">删除</li>
					</ul>

				</section>

				<div class="add-box">
					<p class="qu-type" :class="{expand: isAdding}">
						<span @click="addType('radio')">单选题</span>
						<span @click="addType('checkbox')">多选题</span>
						<span @click="addType('textarea')">文本题</span>
						<span @click="addType('rating')">打分题</span>
					</p>

					<p class="add-btn" @click="isAdding = !isAdding;">
						<span>+ 添加问题</span>
					</p>
				</div>
			</div>


			<footer>
				<div class="date-part">
					<label>问卷截止日期
						<input type="text"
						readonly="true"
						v-model="date"
						@click="isShowDatepicker = !isShowDatepicker">
					</label>
					<date-components id="date-picker"
						v-show="isShowDatepicker"
						@sendDate="changeDate">
					</date-components>
				</div>
				<div class="ctrl-part">
					<span @click="iterator = saveBtn(); iterator.next()">保存问卷</span>
					<span @click="iterator = releaseBtn(); iterator.next()">发布问卷</span>
				</div>
			</footer>
	</div>

	<div class="overlay" v-show="isShowPrompt">
		<div class="prompt-box">
			<header>
				<span>提示</span>
				<a href="javascript:;" @click="isShowPrompt = false">&times;</a>
			</header>
			<p>{{ promptText }}</p>
			<footer>
				<span @click="isShowPrompt = false; iterator && iterator.next()">确定</span>
				<span @click="isShowPrompt = false">取消</span>
			</footer>
		</div>
	</div>
</div>
</template>

<script>
import Data from '../components/designData/data.js';
import Store from '../components/designData/store.js';
import Datepicker from '../components/Datepicker.vue';
// import { defineComponent, ref } from 'vue';
import request from '../utils/request';
// import { ref } from 'vue';
// import { VueDraggable } from 'vue-draggable-plus';
import axios from 'axios'

export default {
	name: 'design',
	components: {
		'date-components': Datepicker
	},
	data() {
		return {
			id: 0,
			questions: [],
			questionTemplate:{},
			quList: Store.fetch(),
			date: '',
			title: '',
			_title: '',
			description: '',
			_description: '',
			questionTitle: '',
			_questionTitle: '',
			_questionDescription: '',
			optionText: '',
			_optionText: '',
			curIndex: '',
			curIndex2: '',
			curOptIndex: '',
			promptText: '',
			iterator: {},
			isAdding: false,
			titleEditing: false,
			descEditing: false,
			topicEditing: false,
			queDescEditing: false, 
			isShowPrompt: false,
			isShowDatepicker: false,
			requestIn: {},
			requestOut: {}
		}
	},

	// beforeRouterEnter(to, from, next) {
	// 	let id = to.params.id;
	// 	let item = {};
	// 	if (id !== 0) {
	// 		let length = Store.fetch().length;
	// 		if (id < 0 || id > length) {
	// 			alert('非法路由');
	// 			next('');
	// 		}
	// 		else {
	// 			item = Store.fetch()[id - 1];
	// 		}

	// 		if (item.state === 0) {
	// 			next();
	// 		}
	// 		else {
	// 			alert('非法路由');
	// 			next('/');
	// 		}
	// 	}
	// 	else {
	// 		next();
	// 	}
	// },

	created() {
		this.getData();
	},

	computed: {
		tempTitle() {
			return this.title;
		}
	},

	methods: {
		async getData() {
			this.id = this.$route.params.id;

			this.requestOut = JSON.parse(JSON.stringify(Data.test));

			await axios.get("http://localhost:1234/api/getQuePaper", {
                    params: { quePaperId: this.id, }
                }).then((res) => {
                    const In = res.data;
					this.title = In.quePaper.title;
					this.description = In.quePaper.description;
					if(In.quePaper.endTime === null || In.quePaper.endTime === ""){
						this.date = "2024-06-02";
					}else{
						this.date = In.quePaper.endTime;
					}
					this.requestOut.paperType = In.quePaper.paperType;
					this.requestOut.status = In.quePaper.status;
					this.questions = [...JSON.parse(JSON.stringify(In.questionList))];
					console.log(In);
					if (res.data.quePaper.status > 0) {
						// this.$message.error({message: "该问卷无法再进行编辑！", duration: 0, showClose: true});
						this.$route.push({path: '/'});
					}
                    // this.$message({message: "问卷已读取", duration: 1000});
                }).catch(() => {
                    // this.$message({message: "error！问卷读取失败！", duration: 1000});
                });

			if(this.questions.length === 0){
				let type = this.requestOut.paperType;
				if(type === 2){
					this.questions = [...JSON.parse(JSON.stringify(Data.quesTemps['voting']))];
				}else if(type === 3){
					this.questions = [...JSON.parse(JSON.stringify(Data.quesTemps['signup']))];
				}else{
					this.questions = [...JSON.parse(JSON.stringify(Data.quesTemps['normal']))];
				}
			}

			this.questionTemplate = Data.template;
			if(this.title === null || this.title === ""){
				this.title = "请在此输入标题";
			}

			this.requestOut.id = this.id;
			this.requestOut.paperType = this.requestIn.paperType;
			this.requestOut.status = this.requestIn.status;

			console.log(this.requestOut);
		},

		changeDate(date) {
			let nowTime = Date.now();
			let chioceTime = new Date(date.replace(/-/g, ','))*1;
			if (chioceTime < nowTime) {
				this.iterator = null;
				this.showPrompt(`选择的日期不能早于当前日期，请重新选择！`);
				return;
			}
			this.date = date;
		},

		cancelTitleEdit() {
			this.titleEditing = false;
			this.title = this._title;
		},

        doneTitleEdit() {
            if(this.title === ''){
                this.title = this._title;
            }
            this.titleEditing = false;
        },

		cancelDescEdit() {
			this.descEditing = false;
			this.description = this._descEditing;
		},

        doneDescEdit() {
            if(this.description === ''){
                this.description = this._description;
            }
            this.descEditing = false;
        },

		cancelTopicEdit(index) {
			this.questions[index].questionTitle = this._questionTitle;
			this.topicEditing = false;
		},

		cancelQueDescEdit(index) {
			this.questions[index].questionDescription = this._questionDescription;
			this.queDescEditing = false;
		},

		cancelOptionEdit(index, optIndex) {
			this.curOptIndex = '';
			this.questions[index].questionOption[optIndex] = this._optionText;
		},

		doneTopicEdit(index) {
			if(this.questions[index].questionTitle === ''){
				this.questions[index].questionTitle = this._questionTitle;
			}
			this.topicEditing = false;
		},

		doneQueDescEdit(index) {
			if(this.questions[index].questionDescription === ''){
				this.questions[index].questionDescription = this._questionDescription;
			}
			this.queDescEditing = false;
		},

		doneOptionEdit(index, optIndex) {
			if(this.questions[index].questionOption[optIndex] === ''){
				this.questions[index].questionOption[optIndex] = this._optionText;
			}
			this.curOptIndex = '';
		},

		switchItem(index, array) {
			this.optIndex = '';
			this.curIndex = '';
			this.curIndex2 = '';

			let arr = array.splice(index, 2);
			array.splice(index , 0, ...arr.reverse());
		},

		moveUp(index, array) {
			this.switchItem(index - 1, array);
		},

		moveDown(index, array) {
			this.switchItem(index, array);
		},

		errorPrompt(text) {
			this.iterator = null;
			this.showPrompt(text);
		},

		reuse(index) {
			if (this.questions.length === 20) {
				this.errorPrompt(`每份问卷至多20个问题！`);
				return;
			}
			let oldQuestion = this.questions[index];
			let newQuestion = JSON.parse(JSON.stringify(oldQuestion));
			this.questions.splice(index, 0, newQuestion);
		},

		delQuestion(index) {
			if (this.questions.length <= 1) {
				this.errorPrompt(`每份问卷至少一个问题！`);
				return;
			}
			this.questions.splice(index, 1);
		},

		delOption(index, options) {
			if (options.length <= 2) {
				this.errorPrompt(`每个问题至少两个选项`);
				return;
			}
			options.splice(index, 1)
		},

		addOption(options) {
			if (options.length === 10) {
				this.errorPrompt(`每个问题至多10个选项`);
				return;
			}
			options.push(`请编辑选项内容`)
		},

		showPrompt(text) {
			this.promptText = text;
			this.isShowPrompt = true;
		},

		addType(type) {
			if (this.questions.length === 20) {
				this.errorPrompt(`每份问卷至多20个问题！`);
				return;
			}
			let ques = {};
			ques = JSON.parse(JSON.stringify(this.questionTemplate[type]));
			this.questions.push(ques);
		},

		saveData() {
			if (this.questions.length < 1) {
				this.errorPrompt(`每份问卷至少一个问题！`);
				return;
			}

			this.requestOut.quePaper.title = this.title;
			this.requestOut.quePaper.description = this.description;
			this.requestOut.quePaper.endTime = this.date.toString();
			this.requestOut.questionList = [...this.questions];

			// 传回 this.requestOut
			axios.post("http://localhost:1234/api/saveQuePaper", this.requestOut)
				.then(() => {
                    this.$message({message: "问卷已编辑", duration: 1000});
                }).catch(() => {
                    this.$message({message: "error！保存编辑失败！", duration: 1000})
                });
		},

		*backBtn() {
			yield this.showPrompt(`确认未保存回到主页吗？`);
			yield this.$router.push({path: '/'});
		},

		*saveBtn() {
			yield this.showPrompt(`确认保存问卷并返回？`);
			this.saveData();
			yield this.$router.push({path: '/'});
		},

		*releaseBtn() {
			yield this.showPrompt(`确认保存并发布问卷？问卷链接将为http://localhost:5173/#/FillIn/`+this.id);
			this.requestOut.state = 1;
			this.saveData();
			yield this.$router.push({path: '/'});
		}
	},

	directives: {
		focus: {
			update: el => el.focus(),
		}
	},

	watch: {
		quList: {
			handler(list) {
				Store.save(list);
				this.iterator && this.iterator.next();
			}
		}
	}
}

</script>

<style scoped lang="scss">
.container {
	width: 100rem;
	margin: 6rem auto;
	color: #555;
}

.qu-wrap {
	box-sizing: border-box;
	padding: 2rem;
	// @include wrap-background;
}

.qu-wrap > header {
	position: relative;
	height: 5rem;
	margin: 0  2rem 0.5rem 2rem;
	line-height: 5rem;
	text-align: center;

	span {
		position: absolute;
		top: 0;
		left: 0;
		cursor: pointer;
	}

	span:hover {
		color: #ffdaa9;
	}

	p, input {
		width: 50rem;
		margin: 0 auto;
		font-size: 1.8rem;
		font-weight: 700;
	}

	p:hover {
		background-color: #ffdaa9;
	}

	input {
		display: none;
		height: 100%;
		text-align: center;
		border: none;
		outline: none;
		background-color: #ccc;

		&.inlineShow {
			display: inline-block;
		}

	}

}

.qu-wrap > header2 {
	position: relative;
	height: 3rem;
	margin: 0  0rem 1rem 0rem;
	line-height: 3rem;
	text-align: center;
	display: flex;

	span {
		position: absolute;
		top: 0;
		left: 0;
		cursor: pointer;
	}

	span:hover {
		color: #ffdaa9;
	}

	p, input {
		width: 50rem;
		margin: 0 auto;
		font-size: 1.4rem;
		font-weight: 700;
		color: grey;
	}

	p:hover {
		background-color: #ffdaa9;
	}

	input {
		display: none;
		height: 100%;
		text-align: center;
		border: none;
		outline: none;
		background-color: #ccc;

		&.inlineShow {
			display: inline-block;
		}

	}

}

.qu-content {
	padding: 2rem;

	border: {
		top: 2px solid #bbb;
		bottom: 2px solid #bbb;
	}

	.qu-item {
		position: relative;
		margin: 1rem 0;
		padding: 1rem 2rem;

		&:hover {
			background-color: #ffdaa9;

			.operat-list {
				display: flex;
			}

			div:last-of-type {
				display: none;
			}

			#require-check {
				display: block;
			}

			#stars {
				display: block;
			}

		}

		h3 {
			height: 2rem;
			font-size: 1.4rem;
			font-weight: 600;
			line-height: 2rem;

			& > input {
				display: none;
				width: 15rem;
				height: 2rem;
				font-size: 1.4rem;
				border: none;
				outline: none;
				background-color: #ccc;
			}

			& > input2 {
				display: none;
				width: 15rem;
				height: 2rem;
				font-size: 1rem;
				border: none;
				outline: none;
				background-color: #ccc;
				color: grey;
			}

		}

		div:last-of-type {
			height: 2rem;
		}

		.qu-num {
			display: inline-block;
			width: 2.5rem;
			margin-right: 1rem;
		}

		&.nowEditing {
			.qu-topic {
				display: none;
			}

			h3 > input {
				display: inline-block;
			}

		}

		&.queEditing {
			.qu-topic2 {
				display: none;
			}

			h3 > input2 {
				display: inline-block;
			}
		}

		&.optEditing .optionEditing {
			.optionText {
				display: none;
			}

			& > input {
				display: inline-block;
			}

		}

	}

	.options-list > li {
		height: 2rem;
		margin: 1rem 0 0 4rem;
		line-height: 2rem;

		&:hover {
			ul {
				display: inline-flex;
				margin-left: 2rem;
			}

			li {
				margin-left: 1rem;
				font-size: 1.2rem;
				cursor: pointer;
			}

			li:hover {
				color: orange;
			}

		}

		& > ul {
			display: none;
		}

		& > input {
			display: none;
			width: 15rem;
			height: 2rem;
			font-size: 1.4rem;
			border: none;
			outline: none;
			background-color: #ccc;
		}

	}

	textarea {
		margin: 1rem 0 0 4rem;
		resize: none;
	}

	.operat-list {
		display: none;
		height: 2rem;
		justify-content: flex-end;

		li {
			margin-right: 1rem;
			cursor: pointer;

			&:hover {
				color: orange;
			}

		}

	}

}

.add-box {
	border: 1px solid #ccc;


	.qu-type {
		overflow: hidden;
		height: 0;
		transition: height .5s;

		&.expand {
			height: 7rem;
		}

		span {
			margin: 0 1rem;
			padding: .5rem 1.5rem;
			cursor: pointer;
			border: 1px solid #ccc;
			background-color: #eee;

			&:hover {
				background-color: #ccc;
			}

		}

	}

	.add-btn {
		padding: 2rem 0;
		font-size: 2rem;
		cursor: pointer;
		background-color: #eee;

		&:hover {
			background-color: #ccc;
		}

	}

}

.qu-wrap > footer {
	display: flex;
	padding: 2rem 8rem;
	justify-content: space-between;

	.date-part {
		position: relative;

		input {
			display: inline-block;
			box-sizing: border-box;
			width: 24rem;
			margin-left: 1.5rem;
			padding: 1rem 2rem;
			font-size: 1.2rem;
			cursor: pointer;
			border: 1px solid #ccc;
			outline: none;
		}

	}

	.ctrl-part {
		// @include nomal-btn();

		span {
			margin: 0 1rem;

			&:nth-of-type(2) {
				color: #fff;
				border-color:orange;
				background-color: orange;
			}

			&:nth-of-type(1) {
				color: black;
				background-color: #fff;

				&:hover {
					border-color: #ccc;
					box-shadow: 0 0 8px #ccc;
				}

			}

		}

	}

}

#date-picker {
	position: absolute;
	right: 0;
	margin-top: .5rem;
}

#require-check {
	position: absolute;
	top: 1rem;
	right: 2rem;
	display: none;
}

.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.prompt-box {
  width: 300px;
  padding: 20px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
  display: flex;
  flex-direction: column;
  text-align: center;
}

header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
  text-align: center;
}

header span {
  margin-right: auto; /* 自动向右外边距，将文本推至中间 */
  color: orange; /* 文本颜色设置为橘色 */
  font-weight: bold; /* 文本加粗 */
}

header a {
  text-decoration: none;
  color: #333;
  font-size: 24px;
  cursor: pointer;
}
header a:hover {
  color: #777;
}

p {
  margin: 10px 0;
  text-align: center;
}

footer {
  display: flex;
  justify-content: space-around;
  margin-top: 10px;
}

footer span {
  cursor: pointer;
  padding: 5px 10px;
  border: 1px solid #ddd;
  border-radius: 3px;
}
footer span:hover {
  background-color: #f0f0f0;
}
</style>
