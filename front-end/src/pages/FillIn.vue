<template>
  <el-row>
    <el-col class="main-answer-list" :sm="24" :md="{span:18,offset:3}" :lg="{span:16,offset:4}"
           :xl="{span:14,offset:5}">
      <el-card class="box-card">
        <div v-if="questionnaire.isBoxSelected===false">
          <h1>{{ questionnaire.questionnaireTitle }}</h1>
          <h3>{{ questionnaire.questionnaireDescription }}</h3>
        </div>
      </el-card>
      <el-form :disabled="cannotSubmit">
        <!-- <div v-for="(item, index) in questionList" :key="index">
          <el-card class="box-card" shadow="hover" v-if="ifShowByCheckingFront(index)">
            <el-form-item>
              <div class="question-title-div">
                <div style="display: inline"><b>{{ index + 1 }}. {{ item.questionTitle }}</b></div>
                <div style="display: inline" v-if="item.isMandatory === false" class="nullable-star">
                  *
                </div>
              </div>
              <div class="description-div">{{ item.questionDescription }}</div>
            </el-form-item>
            <!-- //省略中间的表单输入组件
          </el-card> 
        </div>  -->

        <div>
				<section class="qu-item" v-for="(item, index) in questions">
					<h3>
						<span class="qu-num">{{`Q${index + 1}`}}</span>
						<span class="qu-topic">{{ item.topic }}</span>
						<span v-if="item.isMandatory">(必填)</span>
                    </h3>
					<template v-if="item.type === 3">
						<span id="require-check" style="margin-top: 2rem; font-size: 18px; color: orange;">
							文本题
                        </span>
                        
                        <textarea rows="8" cols="80"></textarea>
                        <!-- <input type="text"
						v-focus
						v-model="topic"
						@focus="topic = item.topic; _topic = topic"
						@blur="doneTopicEdit(index); curIndex=''"
						@keyup.esc="cancelTopicEdit()"
						@keyup.enter="doneTopicEdit(index)"> -->
					</template>
					<ul v-else-if="item.type === 4">
						<div style="font-size: 40px;" id="stars">☆☆☆☆☆</div>
						<label>
							<label id="require-check" style="margin-top: 2rem; font-size: 18px; color: orange;">
								打分题
							</label>
						</label>
					</ul>
					<ul v-else class="options-list" >
                        <div v-if="item.type=== 1">
                            <div class="q-opt" v-for="(option, index) in item.options" :key="index">
                                <el-radio v-model="selectedAnswer">
                                    {{ option }}
                                </el-radio>
                                <!-- <br><br>
                                    <input type="radio" id="codeA" value="A正确" v-model="picked">
                                    <label for="codeA">A: 公信部</label> -->
                            </div>
                        </div>

                        <el-checkbox-group class="q-opt" v-if="item.type === 2 " >
                            <el-checkbox v-for="(option, optionIndex) in item.options" :key="optionIndex" >
                                <!--@change="changeHandler(index,answers[index].ans)" v-model="answers[index].ans" :label="option"-->
                                {{ option }} <br>
                            </el-checkbox>
                        </el-checkbox-group>

						<!-- <li v-for="(option, optIndex) in item.options"
							:class="{optionEditing: curOptIndex === optIndex}">
							<span class="optionText"
								  @click="curIndex=index; curOptIndex=optIndex; topicEditing = false">{{ option }}</span>
							<input type="text"
							v-focus
							v-model="optionText"
							@focus="optionText = option; _optionText = optionText"
							@blur="doneOptionEdit(index, optIndex); curIndex=''"
							@keyup.esc="cancelOptionEdit()"
							@keyup.enter="doneOptionEdit(index, optIndex)">
						</li> -->
						<label v-if="item.type === 1">
							<label id="require-check" style="margin-top: 2rem; font-size: 18px; color: orange;">
								单选题
							</label>
						</label>
						<label v-else>
							<label id="require-check" style="margin-top: 2rem; font-size: 18px; color: orange;">
								多选题
							</label>
						</label>
					</ul>
				</section>
			</div>

        <el-form-item>
          <el-card class="box-card" style="width: 100%">
            <el-dialog
              width="70%"
              title="确认提交？"
              v-model="submitVisible"
              center
            >
              <!-- 对话框内容 -->
            </el-dialog>
            <el-dialog
              width="70%"
              title="确认重置？"
              v-model="resetVisible"
              center
            >
              <!-- 对话框内容 -->
            </el-dialog>
            <!-- <div>
              <el-button type="primary" @click="submitVisible = true">提 交</el-button>
              <el-button @click="resetVisible = true">重 置</el-button>
            </div> -->
          </el-card>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>

    <div class="qu-wrap">
    <footer>
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
                <a href="javascript:;" @click="isShowPrompt = false  ">&times;</a>
            </header>
            <p>{{ promptText }}</p>
            <footer>
                <span @click="isShowPrompt = false; iterator && iterator.next()">确定</span>
                <span @click="isShowPrompt = false">取消</span>
            </footer>
        </div>
    </div>
</template>

<script>
    export default {
        name: "FillIn",
        data: function () {
            return {
                questions:[ {
                    "type": 1,
                    "topic": "单选题",
                    "options": ["选项1","选项2","选项3","选项4"],
                    "isMandatory": false
                },{
                    "type": 2,
                    "topic": "多选题",
                    "options": ["选项1","选项2","选项3","选项4"],
                    "isMandatory": false
                },{
                    "type": 3,
                    "topic": "文本题",
                    "isMandatory": true
                },{
                    "type": 4,
                    "topic": "打分题(默认1-5分取整数)",
                    "isMandatory": false
                }],
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

                iterator: {},
                isShowPrompt: false,
                promptText: 'hh',
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
            fetchData() {
                this.axios.get("/api/fillin/getQuestionList", {
                    params: {
                        questionnaireId: this.$route.params.id,
                    }
                }).then((res) => {
                    const tempList = res.data['questionList'];
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
                    this.$message({message: "问卷已读取", duration: 1000});
                }).catch(() => {
                    this.$message({message: "error！问卷读取失败！", duration: 1000});
                })

                this.axios.get("/api/fillin/getQuestionnaireOutline", {
                    params: {
                        questionnaireId: this.$route.params.id
                    }
                }).then((res) => {
                    const temp = {
                        isBoxSelected: false,
                        questionnaireDescription: res.data['questionnaire']['description'],
                        questionnaireTitle: res.data['questionnaire']['title'],
                        questionnaireId: res.data['questionnaire']['questionnaireId'],
                    };
                    if (res.data['questionnaire']['status'] === 'closed') {
                        this.$message.error({message: "error！问卷已关闭！", duration: 0});
                        this.cannotSubmit = true;
                    }
                    this.questionnaire = temp;
                }).catch(() => {
                    this.$message({message: "error!问卷概况读取失败！", duration: 1000})
                })
            },
            submitAnswer() {
                console.log(this.answerList)
                if (this.checkValidate()) {
                    this.axios.post("/api/fillin/submitAnswer?questionnaireId=" + this.$route.params.id, {
                        answerList: this.answerList,
                        ip: this.ip
                    }).then(() => {
                        this.submitVisible = false;
                        this.alreadySubmit = true;
                        this.cannotSubmit = true;
                        this.$message({message: "问卷已提交", duration: 1000});
                    }).catch(() => {
                        this.$message({message: "error！提交失败！", duration: 1000})
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
            changeHandler() {
                console.log( id+ '改变之后的值是:' + value);
                let pid=0;
                for(let j=0;j<this.questions[id-1].options.length;j++){
                    if(value===this.questions[id-1].options[j].title) {
                        pid=this.questions[id-1].options[j].id;
                    }
                }
                for(let i=id;i<this.questions.length;i++){
                    if(this.questions[i].last_question===id && this.questions[i].last_option===pid){
                        this.questions[i].is_shown=true;
                    }
                    else if(this.questions[i].last_question===id){
                        this.questions[i].is_shown=false;
                    }
                }
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
        },
        mounted() {

            this.fetchData();
            this.ip = localStorage.getItem('Ip')


            this.axios.get("/api/fillin/checkAlreadySubmit", {
                params: {
                    questionnaireId: this.$route.params.id,
                    ip: this.ip
                }
            }).then((res) => {
                this.alreadySubmit = res['data']
                this.cannotSubmit = this.alreadySubmit;
                if (this.alreadySubmit) {
                    this.$message.error({message: "您已填写过该问卷！", duration: 0, showClose: true})
                }
            }).catch(() => {
                this.$message({message: "error！IP地址检测失败！", duration: 1000})
            });
        }
    }
</script>

<style scoped lang="scss">
    .main-answer-list {
        position: relative;
        text-align: left;
        /* box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); */
    }

    .box-card {
        padding-left: 5%;
        text-align: center;
    }

    .nullable-star {
        color: red;
    }
    
    .qu-wrap > footer {
	display: flex;
	padding: 2rem 8rem;
	justify-content: space-between;

	.ctrl-part {


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

/* 覆盖层样式 */
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

/* 提示框样式 */
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

/* 头部样式 */
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

/* 关闭按钮样式 */
header a {
  text-decoration: none;
  color: #333;
  font-size: 24px;
  cursor: pointer;
}
header a:hover {
  color: #777;
}

/* 正文段落样式 */
p {
  margin: 10px 0;
  text-align: center;
}

/* 底部样式 */
footer {
  display: flex;
  justify-content: space-around;
  margin-top: 10px;
}

/* 按钮样式 */
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