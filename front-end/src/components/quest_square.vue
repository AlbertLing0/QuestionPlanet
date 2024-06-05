<script>
import axios from "axios";
import {GET_ALL_QUES_PAPERS} from "~/utils/request.js";
import router from "~/router/index.js";

export default {
  name: "quest_square",
  props: {
    surveys: {
      type: Array,
      required: true
    },

  },
  data() {
    return {
      surveys: [

        // 更多问卷数据...
      ],
      activeIndex: null,  // 初始不设置任何激活面板
      images:[
        "https://images.unsplash.com/photo-1558979158-65a1eaa08691?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80",
        "https://images.unsplash.com/photo-1572276596237-5db2c3e16c5d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80",
        "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1353&q=80",
        "https://images.unsplash.com/photo-1551009175-8a68da93d5f9?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1351&q=80",
        "https://images.unsplash.com/photo-1549880338-65ddcdfd017b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80",
        "https://images.unsplash.com/photo-1558979158-65a1eaa08691?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80",
        "https://images.unsplash.com/photo-1572276596237-5db2c3e16c5d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80",
        "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1353&q=80",
        "https://images.unsplash.com/photo-1551009175-8a68da93d5f9?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1351&q=80",
        "https://images.unsplash.com/photo-1549880338-65ddcdfd017b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80",
      ],
    };
  },
  mounted() {
    this.getQuePaper();
  },

  methods: {
    async getQuePaper(){
      axios.get(GET_ALL_QUES_PAPERS).then(res => {
        // 假设返回的数据包含一个数组 quePapers
        const quePapersFromResponse = res.data['quePapers'];
        console.log(quePapersFromResponse.length);
        // 将返回的 quePapers 数组中的每个 quePaper 添加到现有的 surveys 数组中
        //只获取前10个数据
        quePapersFromResponse.forEach((quePaper, index) => {
          if (index < 10) { // 只处理前10个数据
            const newSurvey = {
              id: quePaper.id,
              name: quePaper.title,
              description: quePaper.description
            };
            this.surveys.push(newSurvey);
          }
        });

        console.log(res.data);

      }).catch(error => {
        // 请求失败，处理错误
        console.error('请求失败:', error);
        // 可以在这里添加错误处理逻辑，例如提示用户
        alert('请求失败，请稍后重试或联系管理员。');
      })
    },
    fillSurvey(id) {
      console.log('Fill survey with ID:', id);
      router.push(`/fillin/${id}`);
      // 这里可以添加填写问卷的逻辑，比如导航到填写问卷的页面
    },
    setActive(index) {
      this.activeIndex = index
    },

  }


}

</script>

<template>
  <div class="question-square">
    <div class="square-box">
      <div class="box-head">
            问卷广场 —— 探索问卷星球
      </div>
      <el-main>
          <div class="survey-list-container">
            <el-scrollbar>
              <div class="survey-list">
                <div
                    class="survey-item"
                    v-for="(survey, index) in surveys"
                    :key="index"
                    :class="{ active: activeIndex === index }"
                    :style="{ backgroundImage: `url(${images[index]})`, '--animation-order': index }"
                    @mouseover="setActive(index)"
                    @mouseleave="setActive(null)"
                >
                  <h2 class="survey-name">{{ survey.name }}</h2>
                  <h3 class="survey-description">{{survey.description}}</h3>
                  <button class="fill-button" @click="fillSurvey(survey.id)">填写</button>
                </div>
              </div>

            </el-scrollbar>
          </div>
      </el-main>

    </div>


  </div>
</template>

<style lang="scss" scoped>

.square-box{
  border-radius: 20px;
  //height: 400px;
  //width: auto;
  background: var(--pane-color);
  padding: 20px;

}

.box-head{
  font-size: 20px;
  color: var(--theme-info-text-color);
}
.survey-list-container {
  height: 50vh;
  width: 80vw;
  //border: 1px solid #ccc; /* 添加边框 */
  //align-items: center;
  //background: black;

}


el-scrollbar{
  direction: ltr;
}

.survey-list {

  display: flex;
  flex-direction: row;
  align-items: center;
  margin-top: 10px;


}

.survey-item {
  //display: flex;
  //justify-content: space-between; /* 两端对齐 */
  ////align-items: center;
  //padding: 10px;
  //border: 1px solid var(--border-color);
  //margin: 5px;
  //border-radius: 20px;
  //align-items: center;
  position: relative;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  height: 45vh;
  border-radius: 30px;
  color: #fff;
  cursor: pointer;
  min-width: 70px;
  flex:0.3;
  margin: 10px;
  position: relative;
  transition: all 300ms ease-in;
  transform: translateX(100%); /* 初始状态从右侧外面开始 */
  opacity: 0; /* 开始时不可见 */
  animation: slideInFromRight 0.5s ease-out forwards;
}
.survey-item.active{
    min-width: 350px;
    //flex:5;
    transform: translateX(-230px); // 向左移动，230px是宽度差(300px - 70px)

}

.survey-name {
  font-size: 20px;
  position: absolute;
  bottom: 80px;
  left: 30px;
  margin: 0;
  opacity: 0;

}
.survey-item h3{
  font-size: 16px;
  position: absolute;
  bottom: 50px;
  left: 30px;
  margin: 0;
  opacity: 0;
}
.survey-item.active h2  {
  opacity: 1;
  transition: opacity 0.2s ease-in 0.3s;
}
.survey-item.active h3{
  opacity: 1;
  transition: opacity 0.2s ease-in 0.3s;
}

@media (max-width: 480px) {
  .survey-list {
    width: 100vw;
  }

  .survey-item:nth-of-type(4),
  .survey-item:nth-of-type(5) {
    display: none;
  }
}
@keyframes slideInFromRight {
  0% {
    transform: translateX(100%);
    opacity: 0;
  }
  100% {
    transform: translateX(0);
    opacity: 1;
  }
}


.fill-button {
  position: absolute;
  background-color: var(--button-color);
  color: var(--theme-info-text-color);
  border: none;
  padding: 5px 10px;
  border-radius: 10px;
  cursor: pointer;
  left:30px;
  bottom: 10px;
  opacity: 0;
}

.fill-button:hover {
  background-color: var(--button-hover-color);
}

.survey-item.active button{
  opacity: 1;
  transition: opacity 0.2s ease-in 0.3s;
}
</style>