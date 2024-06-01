<template>
  <div class="full-screen-container">
    <!-- 内容容器 -->
    <div class="content-wrapper">
      <!-- 现有的el-row和el-col布局 -->
      <div class="main-answer-list" :sm="24" :md="{span:18,offset:3}" :lg="{span:16,offset:4}"
               :xl="{span:14,offset:5}">

      <el-card class="box-card">
        <div v-if="questionnaire.isBoxSelected===false">
          <h1>{{ questionnaire.questionnaireTitle }}</h1>
          <!-- <h3>{{ questionnaire.questionnaireDescription }}</h3> -->
          <h3 v-if="!showFullDescription">{{ questionnaire.questionnaireDescription | truncate }}</h3>
          <h3 v-else>{{ questionnaire.questionnaireDescription }}</h3>
          <el-button @click="toggleDescription" type="text">{{ showFullDescription ? '收起' : '显示全部' }}</el-button>
        </div>
      </el-card>
      <el-form :disabled="cannotSubmit">
        
        <div>
				<section class="qu-item" v-for="(item, index) in questions">
                    
					<h3>
						<span class="qu-num">{{`Q${index + 1}`}}</span>
						<span class="qu-topic">{{ item.questionTitle }}</span>
                        <span v-if="item.isMandatory" class="mandatory-indicator"> *</span>
                        <span class="require-check" v-if="item.questionType === 1">单选题</span>
                        <span class="require-check" v-if="item.questionType === 2">多选题</span>
                        <span class="require-check" v-if="item.questionType === 3">简答题</span>
                        <span class="require-check" v-if="item.questionType === 4">评分题</span>
                    </h3>


					<template v-if="item.questionType === 3">
                        <textarea rows="8" cols="80" placeholder="请输入内容"></textarea>
					</template>



                    <template v-else-if="item.questionType === 4">
                      <ul class="rating-stars">
                        <!-- v-for 循环生成星星 -->
                        <li 
                          v-for="star in 5" 
                          :key="star" 
                          class="star" 
                          :class="{ 'star-active': star <= item.rating }"
                          @click="rate(item, star)"
                        >
                          <!-- 使用 Font Awesome 的星星图标 -->
                          <i class="fa fa-star"></i>
                        </li>
                      </ul>
                    </template>


                    <template v-else >
                        <ul class="options-list">
                            <div v-if="item.questionType=== 1">
                                <el-radio  class="q-opt1" v-for="(option, opindex) in item.questionOption" 
                                :key="opindex" 
                                :value="opindex"
                                :label="option"
                                v-model="answerList[index].ans" 
                                style="margin-bottom: 5px; display: block;">
                                    {{option}}
                                </el-radio>
                              </div>


                            <div v-if="item.questionType === 2">
                                <el-checkbox class="q-opt2"
                                    v-for="(option, opindex) in item.questionOption"
                                    :key="opindex"
                                    :value="option"
                                    style="margin-bottom: 5px; display: block;">
                                    {{option}}
                                </el-checkbox>
                              </div>


                        </ul>

                    </template>
					
				</section>

      </div>

        </el-form>
        <div class="qu-wrap">
        <div class="ctrl-part">
            <span class="ctrl1" @click="iterator = saveBtn(); iterator.next()">提交</span>
            <span class="ctrl2" @click="iterator = releaseBtn(); iterator.next()">暂存</span>
        </div>
    </div>

  <div class="overlay" v-show="isShowPrompt">
      <div class="prompt-box">
          <header>
              <span>提示</span>
              <a href="javascript:;" @click="isShowPrompt = false  ">&times;</a>
          </header>
          <p>{{ promptText }}</p>
          <footer>
              <span @click="isShowPrompt = false; iterator && iterator.next()">确定</span>
              <span @click="isShowPrompt = false">取消</span>
          </footer>
      </div>
  </div>
      </div>

  </div>
</div>

<!-- <div class="end"> end</div> -->
</template>

<script>
import { ref } from 'vue';
import axios from 'axios';

    export default {
        name: "FillIn",

        // filters: {
        //   truncate: function(value, length = 10, clamp = '...') {
        //     length = length || 10;
        //     if (value.length > length) {
        //       return value.slice(0, length) + clamp;
        //     } else {
        //       return value;
        //     }
        //   }
        // },

        data: function () {
            return {
                questions:[ 
                //   {
                //     "questionType": 1,
                //     "questionTitle": "单选题",
                //     "questionOption": ["选项1","选项2","选项3","选项4"],
                //     "isMandatory": false
                // },{
                //     "questionType": 2,
                //     "questionTitle": "多选题",
                //     "questionOption": ["选项1","选项2","选项3","选项4"],
                //     "isMandatory": false
                // },{
                //     "questionType": 3,
                //     "questionTitle": "文本题",
                //     "isMandatory": true
                // },
                {
                    "questionType": 4,
                    "questionTitle": "打分题(默认1-5分取整数)",
                    "isMandatory": false
                }
              ],
                answerList: [],

                questionnaire: {
                    isBoxSelected: false,
                    questionnaireDescription: "Description",
                    questionnaireTitle: "Title",
                    questionnaireId: this.$route.params.id,
                },

                submitVisible: false,
                resetVisible: false,
                // ip: null,
                alreadySubmit: null,
                cannotSubmit: null,
                selectedAnswer: false,
                selectedAnswers:[],

                // selectedAnswer = ref(null),
                // selectedAnswers = ref([]),

                

                iterator: {},
                isShowPrompt: false,
                promptText: 'hh',

                showFullDescription: false,
            }
        },
        computed: {
            aaa: function () {
                return {
                    answerList: this.answerList,
                }
            }
        },
        methods: {
            async fetchData() {

              console.log("qqq");

              const formData = new FormData();
              formData.append("questionnaireId", 1);

              axios.get('http://localhost:1234/api/fillin/getQuestionList', {
                params: {
                  questionnaireId: this.$route.params.id,
                }
              }).then(response => {
                  this.questions = response.data.questionList;
                  console.log(response.data);
                  const tempList = response.data['questionList'];
                  const resList = [];
                  const ansList = [];
                  for (const t of tempList) {
                    // t['date'] = new Date(t['date']);
                    resList.push(t);
                    const oneAnswer = {
                        questionId: t['questionId'],
                        questionTitle: t['questionTitle'],
                        questionType: t['questionType'],
                        // questionNullable: t['questionNullable'],
                        isMandatory: t['isMandatory'],
                        answerSingleCheck: '',
                        answerMultiCheck: [],
                        answerText: '',
                        // answerNumber: t['defaultNumber'],
                        answerGrade: 0,
                        // answerDate: t['date'],
                    };
                    ansList.push(oneAnswer);
                  }
                  this.questionList = resList;
                  this.answerList = ansList;
                  // this.$message({message: "问卷已读取", duration: 1000});
                  console.log("问卷已读取");
                  console.log(this.answerList);
              });
            
              console.log("111");

              axios.get("http://localhost:1234/api/getQuePaperOutline", {
                  params: {
                      quePaperId: this.$route.params.id
                  }
              }).then(res => {
                  const temp = {
                      isBoxSelected: false,
                      // questionnaireDescription: res.data['quePaper']['description'],
                      questionnaireTitle: res.data['quePaper']['title'],
                      questionnaireId: res.data['quePaper']['id'],
                      // paperType: res.data[]
                  };
                  console.log(res.data);
                  // if (res.data['questionnaire']['status'] === 'closed') {
                  //     // this.$message.error({message: "error！问卷已关闭！", duration: 0});
                  //     console.log("error！问卷已关闭！");
                  //     this.cannotSubmit = true;
                  // }
                  this.questionnaire = temp;
              }).catch(error => {
                // 请求失败，处理错误
                console.error('请求失败:', error);
                // 可以在这里添加错误处理逻辑，例如提示用户
                // alert('请求失败，请稍后重试或联系管理员。');
                
              // // () => {
              // //     // this.$message({message: "error!问卷概况读取失败！", duration: 1000})
              // //     console.log("error!问卷概况读取失败！");
              })
            
            },
            async submitAnswer() {
                console.log(this.answerList)
                if (this.checkValidate()) {
                    axios.post("/api/fillin/submitAnswer?questionnaireId=" + this.$route.params.id, {
                        answerList: this.answerList,
                        // ip: this.ip
                    }).then(() => {
                        this.submitVisible = false;
                        this.alreadySubmit = true;
                        this.cannotSubmit = true;
                        // this.$message({message: "问卷已提交", duration: 1000});
                    }).catch(() => {
                        // this.$message({message: "error！提交失败！", duration: 1000})
                    });
                }
            },
            resetAnswer() {
                this.fetchData();
                this.resetVisible = false;
            },
            ifShowByCheckingFront(index) {
                const thisQuestion = this.questionList[index];
                if (thisQuestion.frontChoose === false)
                    return true;

                for (const oneFront of thisQuestion.frontOptions) {
                    const frontIndex = oneFront[0] - 1;
                    const frontValue = oneFront[1];
                    const frontQuestion = this.questionList[frontIndex];
                    let checkList = null;
                    if (frontQuestion.questionType === 'single_check') {
                        checkList = this.answerList[frontIndex].answerSingleCheck;
                        if (frontValue != checkList) return false;
                    } else if (frontQuestion.questionType === 'multi_check') {
                        checkList = this.answerList[frontIndex].answerMultiCheck;
                        for (const oneOfMulti of frontValue) {
                            if (checkList.indexOf(oneOfMulti) === -1)
                                return false;
                        }
                    }
                }
                return true;
            },
            checkValidate() {
                for (const oneAnswer of this.answerList) {
                    if (oneAnswer.isMandatory === true) {
                        console.log('nullable', oneAnswer);
                        continue;
                    } else {
                        console.log(oneAnswer.questionType === 'single_check')
                        if (oneAnswer.questionType === 'single_check' && oneAnswer.answerSingleCheck === '' ||
                            oneAnswer.questionType === 'multi_check' && oneAnswer.answerMultiCheck === '' ||
                            oneAnswer.questionType === 'single_line_text' && oneAnswer.answerText === '' ||
                            oneAnswer.questionType === 'multi_line_text' && oneAnswer.answerText === '' ||
                            oneAnswer.questionType === 'number' && oneAnswer.answerNumber == null ||
                            oneAnswer.questionType === 'grade' && oneAnswer.answerGrade === 0 ||
                            oneAnswer.questionType === 'date' && oneAnswer.answerGrade == null
                        ) {
                            console.log('error')
                            this.$message.error(oneAnswer.questionId % 1000 + 1 + ' ' + oneAnswer.questionTitle + ' 是必填字段！');
                            return false;
                        }
                    }
                }
                return true;
            },
            changeHandler(id,value) {
                console.log(id+ '改变之后的值是:' + value);
                // let pid=0;
                // for(let j=0;j<this.questions[id-1].options.length;j++){
                //   if(value===this.questions[id-1].options[j].title) pid=this.questions[id-1].options[j].id;
                // }
                // for(let i=id;i<this.questions.length;i++){
                //   if(this.questions[i].last_question===id&&this.questions[i].last_option===pid){
                //     this.questions[i].is_shown=true;
                //   }
                //   else if(this.questions[i].last_question===id){
                //     this.questions[i].is_shown=false;
                //   }
                // }
            },
            handleRadioChange(value) {
              console.log('当前选中的Radio label:', value);
              // 在这里处理选中事件
            },
            showPrompt(text) {
                this.promptText = text;
                this.isShowPrompt = true;
            },
            errorPrompt(text) {
                this.iterator = null;
                this.showPrompt(text);
            },
            *backBtn() {
                yield this.showPrompt(`确认未保存回到主页吗？`);
                yield this.$router.push({path: '/'});
            },
            *saveBtn() {
                yield this.showPrompt(`确认要保存问卷？`);
                // yield this.saveData();
                yield this.$router.push({path: '/'});
            },

            *releaseBtn() {
                yield this.showPrompt(`确认要保存并发布问卷？`);
                // yield (() => {
                //     this.quData.state = 1;
                //     this.quData.stateName = '发布中';
                //     this.saveData();
                // })();
                yield this.$router.push({path: '/'});
            },

            rate(item, starValue) {
              // 更新当前题目的评分
              item.rating = starValue;
            },
            toggleDescription() {
              this.showFullDescription = !this.showFullDescription;
            },
        },
        mounted() {
          console.log("hhh");
          this.fetchData();
          // ... 其他逻辑
        }

        
    }
</script>

<style scoped lang="scss">
/* 基础样式 */
body, html {
  font-family: 'Arial', sans-serif;
  margin: 0;
  padding: 0;
  background-color: #f4f4f4;
}
/* 全屏Flex容器 */
.full-screen-container {
  display: flex;
//  align-items: center;
  justify-content: center;
  min-height: 100vh;
  padding-top: 10px;
  // margin-top: 20px;
  background-image: url('../assets/preview_bk.png'); /* 修改后的路径 */
  background-size: cover; /* 背景图片覆盖整个元素 */
  background-position: center; /* 背景图片居中显示 */
  background-repeat: no-repeat; /* 防止背景图片平铺 */
  overflow: hidden;
}


/* 内容容器，限制最大宽度 */
.content-wrapper {
  width: 100%;
  max-width: 1200px; /* 根据需要调整 */
  padding: 20px;
  box-sizing: border-box; /* 边框计算在宽度内 */
}

/* 其他样式保持不变 */

/* 布局容器 */
.qu-wrap {
  padding: 20px;
}


/* 提示框样式 */
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
}

.prompt-box {
  background-color: #fff;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
  width: 300px;
}

.prompt-box header,
.prompt-box footer {
  text-align: center;
}


/* 问卷标题和描述样式 */

/* 居中并设置样式 */
.el-card {
  background-color: #fff;
  border-radius: 15px; /* 设置圆角大小 */
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.1); /* 添加阴影，增强立体感 */
  overflow: hidden; /* 确保内容不会超出圆角边框 */
}

.box-card {
  display: flex;
  flex-direction: column; /* 垂直排列子元素 */
  justify-content: center; /* 垂直居中 */
  align-items: center; /* 水平居中 */
  text-align: center; /* 文本居中 */
  padding: 20px; /* 内边距 */
  background-color: #fff; /* 背景色 */
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.1); /* 添加阴影 */
  border-radius: 15px; /* 圆角 */
  margin-bottom: 15px;
}

.box-card h1 {
  font-size: 2.5em; /* 标题字体大小 */
  color: #333; /* 标题颜色 */
  margin-bottom: 0.5em; /* 标题与描述之间的间距 */
}

.box-card h3 {
  font-size: 1.5em; /* 描述字体大小 */
  color: #666; /* 描述颜色 */
  margin-top: 0.5em; /* 描述上方无外边距 */
}

/* 问题列表样式 */
.qu-item {
//   display: flex;
  background-color: #fff;
  margin-bottom: 20px;
  padding: 50px;
  border-radius: 15px; /* 设置圆角大小 */
  overflow: hidden; /* 确保内容不会超出圆角边框 */
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.1); /* 添加阴影，增强立体感 */
  align-items: flex-start; /* 根据需要调整垂直对齐 */
}


// /* 问题标题样式 */
// .qu-item h3 {
//   font-size: 1.25em; /* 问题标题字体大小 */
//   color: #5439ad; /* 问题标题颜色 */
//   margin-bottom: 0.5em; /* 问题标题与选项之间的间隔 */
// }

/* 问题描述样式 */
.qu-item .qu-num {
  margin-right: 0.5em; /* 可以根据需要调整间隔大小 */
  font-size: 1.1em; /* 问题描述字体大小 */
  color: #000000; /* 问题描述颜色 */
  margin-bottom: 1.5em; /* 问题描述与选项之间的间隔 */
  margin-right: 0.5em; /* 可以根据需要调整间隔大小 */
}

.qu-item .qu-topic {
  margin-right: 0.5em; /* 可以根据需要调整间隔大小 */
  font-size: 1.1em; /* 问题描述字体大小 */
  color: #000000; /* 问题描述颜色 */
  margin-bottom: 1.5em; /* 问题描述与选项之间的间隔 */
  margin-right: 0.5em; /* 可以根据需要调整间隔大小 */
}

/* 必填标识样式 */
.qu-item .mandatory-indicator {
  color: #ff0000; /* 例如红色 */
}

.qu-item .require-check {
  float:right;
  font-size: 18px;
  color: rgb(39, 152, 218);
}


// /* 选项列表样式 */
// .options-list {
//   list-style-type: none; /* 去除列表项默认的标记 */
//   padding: 0;
//   font-size: 1em; /* 选项字体大小 */
// }

// .options-list .q-opt1, .options-list .q-opt2 {
//     /* 设置每个选项的外边距 */
//     margin-bottom: 20px; /* 可以根据需要调整这个值 */
//     padding:20px;
// }

.options-list {
  display: flex;
  flex-direction: column; /* 垂直排列 */
  align-items: flex-start; /* 选项沿容器左侧排列 */
}

.q-opt1 {
  margin-bottom: 20px; /* 选项间的垂直距离 */
  // font-size: 2.0em;
  padding: 20px;
}

.q-opt1 .el-radio__label {
  padding-left: 5px !important;
}

.q-opt2 {
  margin-bottom: 20px; /* 选项间的垂直距离 */
  // font-size: 2.0em;
  padding: 20px;
}

// /* 单选题选项样式 */
// .q-opt1 {
//   display: block; /* 使每个选项独占一行 */
//   margin-bottom: 5px; /* 选项之间的垂直间隔 */
//   font-size: 25px; /* 选项文字大小 */
// }

// .q-opt1 .el-radio {
//   margin-right: 5px; /* 单选按钮与文本之间的间隔 */
// }

/* 文本题样式 */
.text-answer {
  width: 100%; /* 占满父容器的宽度 */
  padding: 10px; /* 文本区域的内边距 */
  font-size: 16px; /* 文本区域文字大小 */
  border: 1px solid #ccc; /* 文本区域边框 */
  border-radius: 4px; /* 文本区域边框圆角 */
  resize: vertical; /* 允许垂直方向调整大小 */
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

.end {
  background-color: #f0f0f0; /* 设置背景*/
}

.ctrl-part {
  display: flex;
  justify-content: center; /* 水平居中 */
  // align-items: center; /* 垂直居中 */
  /* 其他样式 */
}

.ctrl-part span{
  cursor: pointer;
  padding: 10px 15px;
  font-size: 1.1em;
  border: 1px solid #ddd;
  border-radius: 18px;
}

.ctrl-part .ctrl1{
  background-color: #db5a6c;
  color: #fff;
  margin-right: 30px;
}

.ctrl-part .ctrl1:hover{
  background-color: #f0a1a8;
}

.ctrl-part .ctrl2{
  background-color: #5b9fba;
  color: #fff;
  margin-left: 30px;
}

.ctrl-part .ctrl2:hover{
  background-color: #abc6da;
  color: #fff;
}

// .star .fa-star {
//   color: #ccc; /* 灰色 */
// }

// /* 星星选中的样式 */
// .star-active .fa-star {
//   color: #ffcc00; /* 黄色 */
// }

.rating-stars {
  display: flex; /* 使用flex布局 */
  list-style-type: none; /* 移除列表项的默认标记 */
  padding: 0; /* 移除内边距 */
  margin: 1.5em; /* 移除外边距 */
}


.rating-stars .star {
  cursor: pointer;
  /* 增加字体大小来放大星星 */
  font-size: 24px; /* 根据需要调整字体大小 */
  /* 增加左右间隔 */
  padding: 0 6px; /* 可以根据需要调整间隔大小 */
}

.rating-stars .star .fa-star {
  color: #ccc; /* 灰色未选中状态 */
}

.rating-stars .star.star-active .fa-star {
  color: #ffcc00; /* 黄色选中状态 */
}
</style>