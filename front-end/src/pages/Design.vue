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
				@blur="ifTitleBlank()"
				@keyup.enter="ifTitleBlank()">
            </header>


			<div class="qu-content">
				<section class="qu-item"
				v-for="(item, index) in questions"
				:class="{nowEditing: curIndex === index && topicEditing, optEditing: curIndex === index}">
					<h3 @click="curIndex = index; curOptIndex=''; topicEditing = true">
						<span class="qu-num">{{`Q${index + 1}`}}</span>
						<span class="qu-topic">{{ item.topic }}</span>
						<input type="text"
						v-focus
						v-model="topic"
						@focus="_topic = topic"
						@blur="curIndex=''; topic=''"
						@keyup.esc="cancelTopicEdit()"
						@keyup.enter="doneTopicEdit(index)">
						<span v-if="item.isMandatory"> *</span>
					</h3>
					<template v-if="item.type === 'textarea'">
						<textarea rows="8" cols="80"></textarea>
						<label id="require-check">
							<input type="checkbox"
							v-model:="item.isMandatory">
							此题是否必填
						</label>
					</template>
                    <!-- <template v-if="item.type === 'textarea'">
                        <textarea rows="8" cols="80" v-model="item.value"></textarea>
                        <label id="require-check">
                             <input type="checkbox" v-model="item.isMandatory">
                                此题是否必填
                        </label>
                    </template> -->
					<ul v-else class="options-list" >
						<li v-for="(option, optIndex) in item.options"
							:class="{optionEditing: curOptIndex === optIndex}">
							<span class="optionText"
								  @click="curIndex=index; curOptIndex=optIndex; topicEditing = false">{{ option }}</span>
							<input type="text"
							v-focus
							v-model="optionText"
							@focus="_optionText = optionText"
							@blur="curIndex=''; optionText=''"
							@keyup.esc="cancelOptionEdit()"
							@keyup.enter="doneOptionEdit(index, optIndex)">

							<ul class="opt-ctrl">
								<li v-if="optIndex !== 0"
								@click="moveUp(optIndex, item.options)">上移</li>
								<li v-if="optIndex !== item.options.length - 1"
								@click="moveDown(optIndex, item.options)">下移</li>
								<li v-else @click="addOption(item.options)">添加</li>
								<li @click="delOption(optIndex, item.options)">删除</li>
							</ul>
						</li>
						<label id="require-check" v-if="item.type === 'radio'">
							此题为单选题
						</label>
						<label id="require-check" v-else>
							此题为多选题
						</label>
					</ul>
					<ul class="operat-list">
						<li v-if="index !== 0"
							@click="moveUp(index)">上移</li>
						<li v-if="index !== questions.length - 1"
							@click="moveDown(index)">下移</li>
						<li @click="reuse(index)">复用</li>
						<li @click="delQuestion(index)">删除</li>
					</ul>
					<div></div>
				</section>
				<div class="add-box">
					<p class="qu-type" :class="{expand: isAdding}">
						<span @click="addType('radio')">单选题</span>
						<span @click="addType('checkbox')">多选题</span>
						<span @click="addType('textarea')">文本题</span>
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
import PlanetBG from '~/components/planetBG.vue';
import request from '../utils/request';

export default {
	name: 'design',
	components: {
		'date-components': Datepicker
	},
    components: {
        PlanetBG
    },
	data() {
		return {
			index: '',
			quData: {},
			questions: [],
			questionTemplate:{},
			quList: Store.fetch(),
			date: '',
			title: 'default title',
			_title: '',
			topic: '',
			_topic: '',
			optionText: '',
			_optionText: '',
			curIndex: '',
			curOptIndex: '',
			promptText: '',
			iterator: {},
			isAdding: false,
			titleEditing: false,
			topicEditing: false,
			isShowPrompt: false,
			isShowDatepicker: false,
		}
	},

	beforeRouterEnter(to, from, next) {
		let id = to.params.id;
		let item = {};
		if (id !== 0) {
			let length = Store.fetch().length;
			if (id < 0 || id > length) {
				alert('非法路由');
				next('');
			}
			else {
				item = Store.fetch()[id - 1];
			}

			if (item.state === 0) {
				next();
			}
			else {
				alert('非法路由');
				next('/');
			}
		}
		else {
			next();
		}
	},

	created() {
		this.getData();
	},

	computed: {
		tempTitle() {
			return this.title /*|| this.quData.title*/;
		}
	},

	methods: {
		getData() {
			// let id = this.$route.params.id;

			// if (id === 0) {
				// let item = {};
				// item.id = this.quList.length + 1;
				// item.title = `问卷调查${item.id}`;
				// item.state = 0;
				// item.stateName = '未发布';
				// item.time = '2018-12-31';
				// item.questions = [];
				// this.quData = item;
			// }
			// else {
				//this.quData = this.quList[id - 1];
			// }

			this.date = '2018-12-31'/*this.quData.time*/;
			// this.title = this.quData.title;
			// this.index = this.quData.id - 1;
			this.questionTemplate = Data.template;
			// this.questions = [...this.quData.questions];
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

        ifTitleBlank() {
            if(this.title === ''){
                this.title = this._title;
            }
            this.titleEditing = false;
        },

		cancelTopicEdit() {
			this.topicEditing = false;
			this.topic = this._topic;
		},

		cancelOptionEdit() {
			this.curOptIndex = '';
			this.optionText = this._optionText;
		},

		doneTopicEdit(index) {
			this.topicEditing = false;
			if(this.topic === ''){
				this.topic = this._topic;
			}
			this.questions[index].topic = this.topic;
		},

		doneOptionEdit(index, optIndex) {
			this.curOptIndex = '';
			if(this.optionText === ''){
				this.optionText = this._optionText;
			}
			this.questions[index].options[optIndex] = this.optionText;
		},

		switchItem(index, array) {
			this.optIndex = '';
			this.curIndex = '';

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
			if(type === 'radio'){
				ques = {
					"type": "radio",
					"topic": "单选题",
					"options": ["选项1","选项2","选项3","选项4"]
				};
			}else if(type === 'checkbox'){
				ques = {
					"type": "checkbox",
					"topic": "多选题",
					"options": ["选项1","选项2","选项3","选项4"]
				};
			}else{
				ques = {
					"type": "textarea",
					"topic": "文本题",
					"isMandatory": false
				};
			}
			this.questions.push(ques);
		},

		saveData() {
			if (this.questions.length < 1) {
				this.errorPrompt(`每份问卷至少一个问题！`);
				return;
			}

			this.quData.title = this.title;
			this.quData.time = this.date;
			this.quData.questions = [...this.questions];

			if (this.index === this.quList.length) {
				this.quList.push(this.quData);
			}
			else {
				this.quList.splice(this.index, 1, this.quData);
			}
		},

		*backBtn() {
			yield this.showPrompt(`确认未保存回到主页吗？`);
			yield this.$router.push({path: '/'});
		},

		*saveBtn() {
			yield this.showPrompt(`确认要保存问卷？`);
			yield this.saveData();
			yield this.$router.push({path: '/'});
		},

		*releaseBtn() {
			yield this.showPrompt(`确认要保存并发布问卷？`);
			yield (() => {
				this.quData.state = 1;
				this.quData.stateName = '发布中';
				this.saveData();
			})();
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
	margin: 0  2rem 2rem 2rem;
	line-height: 5rem;
	text-align: center;

	span {
		position: absolute;
		top: 0;
		left: 0;
		cursor: pointer;
	}

	span:hover {
		color: orange;
	}

	p, input {
		width: 50rem;
		margin: 0 auto;
		font-size: 1.8rem;
		font-weight: 700;
	}

	p:hover {
		background-color: orange;
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

</style>
