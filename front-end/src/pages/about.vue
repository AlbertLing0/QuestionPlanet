<template>
  <div :class="['component_root', themeDark && 'theme_dark']">
    <div id="top-bar">
      <topBar :themeDark="themeDark"/>
    </div>
    <div class="bottom-box">
      <div id="side-bar">
        <sideBar @toggle-theme="toggleTheme"/>
      </div>
      <div class="right-box">
        <div class="head-text">
          歡迎回来，{{ nowUserName || "<無名氏>" }}。
        </div>
        <div :class="['mgr_box_toggler_bar', on_ans_mgr && 'mgr_box_toggler_bar_of_ans']">
          <div class="mgr_box_toggler_pad"></div>
          <div class="mgr_box_toggler" @click="toggle_mgr_box">{{ on_ans_mgr ? "<<< Questionnaires" : "Answers >>>" }}</div>
        </div>
        <div class="mgr_box_view">
          <div :class="['mgr_box_container', on_ans_mgr && 'ans_mgr_in_container']">
            <que_mgr/>
            <ans_mgr/>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import sideBar from "~/components/side-bar.vue";
import topBar from "~/components/top-bar.vue";
import que_mgr from "~/components/que_mgr.vue";
import ans_mgr from "~/components/ans_mgr.vue";
import {inject} from "vue";
export default {
  name:"about",
  components: {sideBar, topBar, que_mgr, ans_mgr},
  setup(){
    const nowUserName = inject('Username') ;
    return {
      nowUserName
    }
  },
  methods: {
    /** @param {boolean} themeDark */
    setThemeDark(themeDark) {
      this.themeDark = themeDark;
      localStorage.setItem("theme-color", themeDark ? "dark" : "light");
    },
    toggleTheme() {
      this.setThemeDark(!this.themeDark);
    },
    toggle_mgr_box() {
      if (this.on_ans_mgr = !this.on_ans_mgr) {}
    }
  },
  data() {
    return {
      themeDark: false,
      on_ans_mgr: false
    };
  },
  mounted() {
    if (localStorage.getItem("theme-color") === "dark") { // Only when `dark` got, toggling is needed.
      this.setThemeDark(true);
    }
    if(localStorage.getItem("username")){
      this.nowUserName=localStorage.getItem("username");
    }
  }
}
</script>

<style lang="scss" scoped>
@import url("~/assets/style/theme_colors.css");

@font-face {
  font-family: Marmelad_Regular;
  font-weight: normal;
  src: url(~/assets/font/Marmelad-Regular.ttf) format("truetype");
  text-rendering: optimizeLegibility;
}

@font-face {
  font-family: yumin;
  src: url("~/assets/font/yumin.ttf") format(truetype);
}

body {
  margin: 0;
  padding: 0;
  /* background-size: 100vw 100vh; */


}
.component_root {
  background-color: var(--bg-color);
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  font-family: "yumin";
}

.bottom-box{
  display: flex;
  flex-direction: row;
  position: relative;
  #side-bar{
    position: relative;
    flex:0;
    margin-top: 5vh;
    /* top: 50%; */ /* 将顶部定位到页面垂直中點 */
    /* transform: translateY(-50%); */ /* 使用负的自身高度的一半来调整位置，實现垂直居中 */
    z-index: 10;
  }
  .right-box{
    flex:1;
    position: relative;
    margin-left:30px;
    margin-right: 40px;
    margin-top: 5vh;
    z-index:10;
    height: 80vh;
    background-color: var(--theme-text-box-color);
    border-radius: 20px;
    padding: 3% 5%;
    display: flex;
    flex-direction: column;
    .head-text{
      height: min-content;
      font-size: 30px;
      /* font-family: SansSerif; */
      color: var(--theme-info-text-color);
      align-items: flex-end;
    }
    .mgr_box_toggler_bar {
      display: flex;
      flex-direction: row;
      .mgr_box_toggler_pad {
        width: 100%;
        transition: width 0.125s ease-in;
      }
      .mgr_box_toggler {
        margin: 4px;
        padding: 4px;
        flex-shrink: 0;
        background-color: #dfdf9f;
        border: 2px solid #685840;
        border-radius: 16px;
        cursor: pointer;
      }
    }
    .mgr_box_toggler_bar_of_ans {
      .mgr_box_toggler_pad {
        width: 0;
      }
      .mgr_box_toggler {
        background-color: #9fdfdf;
        border-color: #405858;
      }
    }
    .mgr_box_view {
      width: 100%;
      flex-grow: 1;
      overflow: hidden;
    }
    .mgr_box_container {
      position: relative;
      left: 0%;
      width: 200%;
      height: 100%;
      display: flex;
      flex-direction: row;
      transition: left 0.125s ease-in;
    }
    .ans_mgr_in_container {
      left: -100%;
    }

  }
}

</style>
