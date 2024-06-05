<template v-if="answerList && answerList.length">
    <div class="full-screen-container" v-if="!isLoading">
      <!-- 内容容器 -->
      <div class="content-wrapper">
        <!-- 现有的el-row和el-col布局 -->
        <div class="main-answer-list" :sm="24" :md="{span:18,offset:3}" :lg="{span:16,offset:4}"
                 :xl="{span:14,offset:5}">
  
        <el-card class="box-card">
          <div v-if="questionnaire.isBoxSelected===false">
            <h1>{{ questionnaire.questionnaireTitle }}</h1>
            <!-- <h3>{{ questionnaire.questionnaireDescription }}</h3> -->
            <!-- <h3 v-if="!showFullDescription">{{ questionnaire.questionnaireDescription | truncate }}</h3>
            <h3 v-else>{{ questionnaire.questionnaireDescription }}</h3>
            <el-button @click="toggleDescription" link>{{ showFullDescription ? '收起' : '显示全部' }}</el-button> -->
            <!-- <h3 v-if="!showFullDescription">{{ truncate(questionnaire.questionnaireDescription, 50) }}</h3> -->
            <h3 v-if="!showFullDescription">{{ truncate(questionnaire.questionnaireDescription, 50) }}</h3>
            <h3 v-else>
              <div v-for="(line, index) in descriptionLines" :key="index" class="description-line">
                {{ line }}
              </div>
            </h3>
  
            <el-button
              v-if="questionnaire.questionnaireDescription.length > 50"
              type="primary"
              plain
              @click="toggleDescription"
              class="my-toggle-button"
            >
              {{ showFullDescription ? '收起' : '显示全部' }}
            </el-button>
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
  
  
                      <template v-if="item.questionType === 3 && answerList && answerList.length" >
                          <!-- <textarea rows="8" cols="80" placeholder="请输入内容" v-model="answerList[index].answerText"></textarea> -->
                          <textarea  rows="8" v-model="answerList[index].answerText" placeholder="请输入内容" class="fixed-size-input"></textarea>
                      </template>
  
  
  
                      <template v-else-if="item.questionType === 4 && answerList && answerList.length">
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
  
  
                      <template v-else-if="answerList && answerList.length" >
                          <ul class="options-list">
  
                                <div v-if="item.questionType=== 1">
                                <el-radio-group class="q-opt1-group" v-model="answerList[index].answerSingleOption">   
                                    <el-radio  class="q-opt1" v-for="(option, opindex) in item.questionOptions" 
                                    :key="opindex" 
                                    :value="option"
                                    :label="opindex"                
                                    >
                                        {{option}}
                                    </el-radio>
                                </el-radio-group>
  
                                </div>
                  
  
                              <div v-if="item.questionType === 2">
                              <el-checkbox-group class="q-opt2-group" v-model="answerList[index].answerMultiOption"> 
                                  <el-checkbox class="q-opt2" v-for="(option, opindex) in item.questionOptions"
                                  :key="opindex" 
                                  :value="option"
                                  :label="opindex" >
                                      {{option}}
                                  </el-checkbox>
                              </el-checkbox-group>
                              </div>
  
  
                          </ul>
  
                      </template>
                      
                  </section>
  
        </div>
  
          </el-form>
  
    <div class="overlay" v-show="isShowPrompt">
    </div>

        </div>
  
    </div>
  </div>
  
  <!-- <div class="end"> end</div> -->
  </template>
  
  <script>
  import { ref, inject } from 'vue';
  import axios ,{ isCancel } from 'axios';
  import {GET_QUES_LIST_API, GET_QUES_PAPER_OUTLINE_API, GET_SUBMIT_ANS_API,GET_SAVED_ANS_API} from "~/utils/request.js";
  
  
      export default {
          name: "FillIn",
  
          setup(){
            const nowUserName = inject('Username') ;
            return {
              nowUserName
            }
          },
  
          data: function () {
              return {
                  questions:[ 
                    {
                      "questionType": 1,
                      "questionTitle": "单选题",
                      "questionOptions": ["选项1","选项2","选项3","选项4"],
                      "isMandatory": false
                  },{
                      "questionType": 2,
                      "questionTitle": "多选题",
                      "questionOptions": ["选项1","选项2","选项3","选项4"],
                      "isMandatory": false
                  },{
                      "questionType": 3,
                      "questionTitle": "文本题",
                      "isMandatory": true
                  },
                  {
                      "questionType": 4,
                      "questionTitle": "打分题(默认1-5分取整数)",
                      "isMandatory": false
                  }
                ],
  
  
                  answerList: [
                  {
                      "questionType": 1,
                      "questionTitle": "单选题",
                      "questionOptions": ["选项1","选项2","选项3","选项4"],
                      "answerSingleOption": "选项1",
                      "isMandatory": false
                  },{
                      "questionType": 2,
                      "questionTitle": "多选题",
                      "answerMultiOption": [],
                      "questionOptions": ["选项1","选项2","选项3","选项4"],
                      "isMandatory": false
                  },{
                      "questionType": 3,
                      "questionTitle": "文本题",
                      "answerText": "",
                      "isMandatory": true
                  },
                  {
                      "questionType": 4,
                      "questionTitle": "打分题(默认1-5分取整数)",
                      "answerCode": 0,
                      "isMandatory": false
                  }
                  ],
  
                  questionnaire: {
                      isBoxSelected: false,
                      questionnaireDescription: "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                      questionnaireTitle: "Title",
                      questionnaireId: this.$route.params.id,
                      paperType: 1,
  
                  },
  
                  submitVisible: false,
                  resetVisible: false,
                  // ip: null,
                  alreadySubmit: null,
                  cannotSubmit: null,
                  selectedAnswer: null,
                  selectedAnswers:[],
  
                  isDataLoaded: false,
                  isQuePaperAnswered:  false,
  
                  isLoading: true,
  
                  isConfirm: false,
                  isCancel: false,
  
                  // selectedAnswer = ref(null),
                  // selectedAnswers = ref([]),
  
                  
  
                  iterator: {},
                  isShowPrompt: true,
                  promptText: '',
                  canClickPrompt: false,
  
                  showFullDescription: true,
              }
          },
          methods: {
              async fetchData() {
  
                console.log("qqq");
  
                const response = await axios.get(GET_QUES_LIST_API, {
                  params: {
                    quePaperId: this.$route.params.id,
                  }
                }).then(response => {
                    // this.questions = response.data.questionList;
                    console.log(response.data);
                    const tempList = response.data['questionList'];
                    const resList = [];
                    const ansList = [];
                    for (const t of tempList) {
                      resList.push(t);
                      const oneAnswer = {
                          questionId: t['questionId'],
                          questionTitle: t['questionTitle'],
                          questionType: t['questionType'],
                          isMandatory: t['isMandatory'],
                          answerSingleOption: null,
                          answerMultiOption: [],
                          answerText: '',
                          answerCode: 0,
                      };
                      ansList.push(oneAnswer);
                    }
                    this.questions = resList;
                    this.answerList = ansList;
                    console.log("问卷已读取");
                    console.log(this.answerList);
  
                });
              
                console.log("111");
  
              },
  
              async fetchSavedData(){
                console.log("q22");
  
                const response = await axios.get(GET_SAVED_ANS_API, {
                  params: {
                    quePaperId: this.$route.params.id,
                    username: this.nowUserName
                  }
                }).then(response => {
                    this.answerList = response.data.answerList;
                    // this.questions = response.data.questionList;
                    console.log(response.data);
  
                    for (let i = 0; i < this.questions.length; i++) {
                      if( this.questions[i].questionType === 4)
                      this.questions[i].rating = this.answerList[i].answerCode;
                    }
                    // const tempList = response.data['questionList'];
                    // const resList = [];
                    // const ansList = [];
                    // for (const t of tempList) {
                    //   resList.push(t);
                    //   const oneAnswer = {
                    //       questionId: t['questionId'],
                    //       questionTitle: t['questionTitle'],
                    //       questionType: t['questionType'],
                    //       isMandatory: t['isMandatory'],
                    //       answerSingleOption: null,
                    //       answerMultiOption: [],
                    //       answerText: '',
                    //       answerCode: 0,
                    //   };
                    //   ansList.push(oneAnswer);
                    // }
                    // this.questions = resList;
                    // this.answerList = ansList;
                    // console.log("问卷已读取");
                    // console.log(this.answerList);
  
                });
              
                console.log("122");
              },
  
              async getQuePaper(){
                const res = await axios.get(GET_QUES_PAPER_OUTLINE_API, {
                    params: {
                        quePaperId: this.$route.params.id,
                        username: this.nowUserName
                    }
                });  //.then(res => {
                    const temp = {
                        isBoxSelected: false,
                        questionnaireDescription: res.data['quePaper']['description'],
                        questionnaireTitle: res.data['quePaper']['title'],
                        questionnaireId: res.data['quePaper']['id'],
                        paperType: res.data['quePaper']['paperType'],
                        status: res.data['quePaper']['status'],
                    };
                    this.isQuePaperAnswered = res.data['isQuePaperAnswered'];
  
                    console.log(res.data['isQuePaperAnswered']);
                    // if (res.data['quePaper']['status'] === 0 || res.data['quePaper']['status'] === 2) {
                    //     console.log("error！问卷已关闭！");
                    //     this.errorPrompt(`问卷已关闭！`);
                    //     setTimeout(() => {
                    //       // 隐藏提示框（如果提示框组件支持自动隐藏）
                    //       this.isShowPrompt = false;
  
                    //       // 执行页面跳转
                    //       this.$router.push({ path: '/' });
                    //     }, 3000); // 3000毫秒后执行上面的代码
  
                    //     this.cannotSubmit = true;
                    // }
                    this.questionnaire = temp;
  
                    // this.canClickPrompt = true;
                },
              // ).catch(error => {
              //     // 请求失败，处理错误
              //     console.error('请求失败:', error);
              //     // 可以在这里添加错误处理逻辑，例如提示用户
              //     alert('请求失败，请稍后重试或联系管理员。');
                  
                // // () => {
                // //     // this.$message({message: "error!问卷概况读取失败！", duration: 1000})
                // //     console.log("error!问卷概况读取失败！");
              //   })
              
              // },
              async submitAnswer() {
                  console.log(this.answerList);
  
                  axios.post(GET_SUBMIT_ANS_API,
                  {
                    answerList: this.answerList, // 将数组转换为字符串
                  }, 
                  {
                      params: {
                      quePaperId: this.$route.params.id,
                      username: this.nowUserName
                      }
  
                  }).then(() => {
                      this.submitVisible = false;
                      this.alreadySubmit = true;
                      this.cannotSubmit = true;
                      console.log(this.answerList);
                      // this.$message({message: "问卷已提交", duration: 1000});
                      console.log("aaa");
                  }).catch(error => {
                  // 请求失败，处理错误
                  console.error('请求失败:', error);
                      // this.$message({message: "error！提交失败！", duration: 1000})
                  });
              },
  
              async saveAnswer() {
                  console.log(this.answerList);
  
                  axios.post(GET_SUBMIT_ANS_API,
                  {
                    answerList: this.answerList, // 将数组转换为字符串
                  }, 
                  {
                      params: {
                      quePaperId: this.$route.params.id,
                      username: this.nowUserName
                      }
  
                  }).then(() => {
                      this.submitVisible = false;
                      this.alreadySubmit = true;
                      this.cannotSubmit = true;
                      console.log(this.answerList);
                      // this.$message({message: "问卷已提交", duration: 1000});
                      console.log("aaa");
                  }).catch(error => {
                  // 请求失败，处理错误
                  console.error('请求失败:', error);
                      // this.$message({message: "error！提交失败！", duration: 1000})
                  });
              },
              resetAnswer() {
                  this.fetchData();
                  this.resetVisible = false;
              },
              checkValidate() {
                  for (const oneAnswer of this.answerList) {
                      if (oneAnswer.isMandatory === false) {
                          console.log('nullable', oneAnswer);
                          continue;
                      } else {
                          console.log(oneAnswer.answerMultiOption.length === 0);
                          if (oneAnswer.questionType === 1 && oneAnswer.answerSingleOption === null ||
                              oneAnswer.questionType === 2 && oneAnswer.answerMultiOption.length === 0 ||
                              oneAnswer.questionType === 3 && oneAnswer.answerText === '' ||
                              oneAnswer.questionType === 4 && oneAnswer.answerCode === 0
                          ) {
                              console.log('error')
  
                              // this.errorPrompt(`每份问卷至少一个问题！`);
                              // console.log("90");
                              // return;
                              return false;
                          }
                      }
                  }
                  console.log("xixixi");
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
              *submitBtn() {
                  yield this.showPrompt(`确认要提交问卷？`);
                  console.log("hhh1");
                  if( this.checkValidate() === false){
                    this.errorPrompt(`注意！！存在未填写的必填题！！`);
                    return ;
                  }
  
                  let unwatchConfirm;
                  let unwatchCancel;
  
  
                  unwatchConfirm = this.$watch('isConfirm', (newValue) => {
                    if (newValue) {
                      this.submitAnswer();
                      this.isConfirm = false; // 重置标志
                      unwatchConfirm();
                    }
                  });
                  unwatchCancel = this.$watch('isCancel', (newValue) => {
                    if (newValue) {
                      this.isCancel = false; // 重置标志
                      unwatchCancel();
                    }
                  });
                  console.log("hhh2");
                  // this.isCancel = false;
                  // this.isConfirm = false;
                  // yield this.saveData();
                  yield this.$router.push({path: '/about'});
              },
  
              *saveBtn() {
                  yield this.showPrompt(`确认要暂存问卷？`);
  
                  this.isCancel = false;
                  this.isConfirm = false;
  
                  let unwatchConfirm;
                  let unwatchCancel;
  
  
                  unwatchConfirm = this.$watch('isConfirm', (newValue) => {
                    if (newValue) {
                      this.saveAnswer();
                      this.isConfirm = false; // 重置标志
                      unwatchConfirm();
                    }
                  });
                  unwatchCancel = this.$watch('isCancel', (newValue) => {
                    if (newValue) {
                      this.isCancel = false; // 重置标志
                      unwatchCancel();
                    }
                  });
  
                  // yield (() => {
                  //     this.quData.state = 1;
                  //     this.quData.stateName = '发布中';
                  //     this.saveData();
                  // })();
                  yield this.$router.push({path: '/about'});
              },
  
              *resetBtn() {
                  this.errorPrompt(`确认要重置问卷？`);
                  console.log("reset begin");
  
                  let unwatchConfirm;
                  let unwatchCancel;
  
  
                  unwatchConfirm = this.$watch('isConfirm', (newValue) => {
                    if (newValue) {
                      this.resetAnswer();
                      this.isConfirm = false; // 重置标志
                      unwatchConfirm();
                    }
                  });
                  unwatchCancel = this.$watch('isCancel', (newValue) => {
                    if (newValue) {
                      this.isCancel = false; // 重置标志
                      unwatchCancel();
                    }
                  });
  
                  // if( this.isConfirm === true ){
                  //   this.resetAnswer();
                  // }
                  // else if( this.isCancel === true ){
                  //   return ;
                  // }
                  console.log("reset end");
              },
  
              rate(item, star) {
                // 假设 item 有一个唯一的标识符 questionId
                const questionIndex = this.questions.findIndex(q => q.questionId === item.questionId);
                if (questionIndex !== -1) {
                  // 更新当前问题的 rating
                  item.rating = star;
                  // 更新 answerList 中的 answerCode
                  this.answerList[questionIndex].answerCode = star;
                }
              },
  
              handleChange(event) {
                // 处理事件
              },
  
              truncate(value, length = 10, clamp = '...') {
                length = length || 10;
                if (value.length > length) {
                  return value.slice(0, length) + clamp;
                } else {
                  return value;
                }
              },
  
              toggleDescription() {
                this.showFullDescription = !this.showFullDescription;
              },
  
              
          },
  
  
          async mounted() {
            if(localStorage.getItem("username")){
              this.nowUserName=localStorage.getItem("username");
            }
            console.log("hhh");
  
            if( this.nowUserName === null ){
              console.log("error！用户未登录！");
              this.errorPrompt(`用户未登录！`);
              setTimeout(() => {
                // 隐藏提示框（如果提示框组件支持自动隐藏）
                this.isShowPrompt = false;
  
                // 执行页面跳转
                this.$router.push({ path: '/login' });
              }, 3000); // 3000毫秒后执行上面的代码
            }
            else{
              await this.getQuePaper();
  
              if(this.isQuePaperAnswered === false){
                console.log("1122");
                this.fetchData();
              }else{
                console.log("2233");
                await this.fetchData();
                await this.fetchSavedData();
              }
  
              setTimeout(() => {
                // 设置 isLoading 为 false，这将触发页面渲染
                this.isLoading = false;
                this.canClickPrompt = true;
              }, 200);
            }
          },
  
          computed: {
            descriptionLines() {
              const words = this.questionnaire.questionnaireDescription.split('');
              const lineLength = 50; // 每行的字符数限制
              let lines = [''];
              
              words.forEach((char, index) => {
                // 如果当前行加上新字符不会超出长度限制，则添加到当前行
                if (lines[lines.length - 1].length < lineLength) {
                  lines[lines.length - 1] += char;
                } else {
                  // 否则，开始新的一行
                  lines.push(char);
                }
              });
              
              return lines.filter(line => line.length > 0); // 移除空行
            },
  
            aaa: function () {
                  return {
                      answerList: this.answerList,
                  }
              },
          },
  
          
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
  
  .description-line {
    display: block; /* 每个.description-line元素显示为块级元素，自动换行 */
  }
  
  .my-toggle-button {
    margin-top: 13px;
    background: none; /* 确保背景色为透明 */
    color: #409eff; /* 文字颜色为主色调 */
    border-color: transparent; /* 透明边框，如果需要可以设置颜色 */
    padding: 5px 12px; /* 根据需要调整内边距 */
    border-radius: 5px; /* 轻微的圆角 */
    border: 1px solid #409eff; /* 轻微的边框，颜色与文字颜色一致 */
    font-size: 11px; /* 字体大小 */
    transition: all 0.3s; /* 平滑过渡效果 */
  }
  
  .my-toggle-button:hover {
    background-color: #5dacfa; /* 鼠标悬浮或聚焦时的背景色 */
    color: white; /* 文字颜色变为白色 */
    border-color: #409eff; /* 边框颜色与背景色一致 */
  }
  
  /* 提示框样式 */
  .overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: transparent;
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
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
  
  // .options-list {
  //   display: flex;
  //   flex-direction: column; /* 垂直排列 */
  //   align-items: flex-start; /* 选项沿容器左侧排列 */
  // }
  
  .fixed-size-input {
    width: 100%; /* 固定宽度 */
    height: 100%; /* 固定高度 */
    // padding: 5px; /* 内边距，可根据需要调整 */
    // border: 1px solid #ccc; /* 边框样式 */
    // box-sizing: border-box; /* 边框计算在宽度和高度内 */
    resize: none; 
    margin-top: 20px;
    padding: 18px;
    font-size: 1.1em;
  }
  
  .q-opt1-group {
    display: flex;
    flex-direction: column;
    align-items: start; /* 根据需要调整对齐方式 */
  }
  
  .q-opt1 {
    margin-left: 20px;
    margin-top: 30px;
  }
  .q-opt1:first-child {
    margin-top: 30px;
  }
  
  /* 移除最后一个元素的 padding-bottom */
  .q-opt1:last-child {
    padding-bottom: 0;
  }
  
  .q-opt1 ::v-deep .el-radio__label {
    padding-left: 10px;
  }
  
  .q-opt2-group {
    display: flex;
    flex-direction: column;
    align-items: start; /* 根据需要调整对齐方式 */
  }
  
  .q-opt2 {
    margin-left: 20px;
    margin-top: 30px;
  }
  
  .q-opt2:first-child {
    margin-top: 30px;
  }
  
  /* 移除最后一个元素的 padding-bottom */
  .q-opt2:last-child {
    padding-bottom: 0;
  }
  
  .q-opt2 ::v-deep .el-checkbox__label {
    padding-left: 10px;
  }
  
  
  // .checkbox-wrapper:hover .q-opt2 {
  //   /* 自定义悬停时的样式 */
  //   color: #409eff; /* 文字颜色 */
  //   border-color: #c6e2ff; /* 边框颜色 */
  //   background-color: #ecf5ff; /* 背景颜色 */
  // }
  
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
    margin-right: 30px;
  }
  
  .ctrl-part .ctrl2:hover{
    background-color: #8ec0d4;
    color: #fff;
  }
  
  .ctrl-part .ctrl3{
    background-color: #f8d466;
    color: #fff;
    margin-left: 30px;
  }
  
  .ctrl-part .ctrl3:hover{
    background-color: #f4e2ac;
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