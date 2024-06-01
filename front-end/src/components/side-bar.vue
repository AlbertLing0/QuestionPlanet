<template>
  <div class="menu-wrapper">
<!--    <div class="menu-title">-->
<!--      <img :src="logoSrc" alt="" class="logo">-->
<!--    </div>-->
    <div class="content">
      <div class="person-info">
        <img src="../assets/head_portrait.jpg" alt="" />
        <div class="person-name">
          <div class="name">{{nowUserName}}</div>
          <span class="detail">前端小白</span>
        </div>
      </div>
      <div class="menu-content">
        <div class="menu-list">
          <div class="menu-list-item" v-for="item in menuData" :key="item.id" @click="item.action">
            <div class="block"></div>
            <span class="iconfont" :class="item.iconFont"></span>
            <div class="item-name">{{ item.menuName }}</div>
          </div>
          <div class="menu-list-item">
            <div class="block"></div>
            <span class="iconfont icon-yueliang"></span>
            <div class="item-name">Dark Mode</div>
            <div class="btn">
              <input class="check-ipt" type="checkbox" id="checks" @change="changeDark" />
              <label class="check-lable" for="checks"></label>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {inject} from "vue";
import router from "~/router/index.js";

export default {
  name:"side-bar",
  setup(){
    let nowUserName = inject('Username') ;
    return {
      nowUserName
    }

  },

  data() {
    return {
      menuData: [
        { id: 1, menuName: "Profile", iconFont: "icon-caidan", action: this.showProfile },
        { id: 2, menuName: "My Questionnaires", iconFont: "icon-order-fill", action: this.showQuestionnaires },
        { id: 3, menuName: "Data Analysis", iconFont: "icon-bingtu", action: this.showDataAnalysis },
        { id: 4, menuName: "Settings", iconFont: "icon-shezhi", action: this.showSettings },
        { id: 5, menuName: "Logout", iconFont: "icon-jinru", action: this.logout }
      ]
    };
  },
  mounted() {
    if(localStorage.getItem("username")){
      this.nowUserName=localStorage.getItem("username");
    }
  },
  methods: {
    changeDark() {
      this.$emit("toggle-theme");
    },
    showProfile() {
      console.log("Profile clicked");
      // 实现显示 Profile 的逻辑
    },
    showQuestionnaires() {
      console.log("My Questionnaires clicked");
      // 实现显示 Questionnaires 的逻辑
    },
    showDataAnalysis() {
      console.log("Data Analysis clicked");
      // 实现显示 Data Analysis 的逻辑
    },
    showSettings() {
      console.log("Settings clicked");
      // 实现显示 Settings 的逻辑
    },
    logout() {
      console.log("Logout clicked");
      // 实现 Logout 的逻辑
      this.nowUserName=null;
      router.push("/");
    },

  }
};
</script>

<style lang='scss' scoped>
@import url(../assets/iconfont/iconfont.css);
@font-face {
  font-family: Marmelad_Regular;
  font-weight: normal;
  src: url(~/assets/font/Marmelad-Regular.ttf) format("truetype");
  text-rendering: optimizeLegibility;
}

.menu-wrapper {
  margin-left: 25px;
  width: 70px;
  border-radius: 20px;
  background-color: var(--pane-color);
  padding: 10px;
  box-sizing: border-box;
  transition: 0.6s;
  overflow: hidden;
  //position: absolute;
  display: flex;
  flex-direction: column;
}

.menu-title {
  padding-bottom: 0px;
  box-sizing: border-box;
  border-bottom: 1px solid rgb(229, 233, 236);
  margin-bottom: 20px;
  .title-text {
    margin-left: 10px;
    font-family: Marmelad_Regular;
    font-size: 30px;
    vertical-align: middle;
    opacity: 0;
    transition: 0.6s;
    color: var(--theme-info-text-color);
  }
  .icon-24gf-bag {
    font-size: 30px;
    padding-left: 10px;
    color: var(--theme-hover-color);
  }
}

.content .person-info {
  margin-top: 20px;
  white-space: nowrap;
  img {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    border: 2px solid #fff;
    box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, 0.2);
    vertical-align: middle;
  }
  .person-name {
    margin-left: 15px;
    vertical-align: middle;
    opacity: 0;
    transition: 0.6s;
    overflow: hidden;
    color: var(--theme-info-text-color);
    display: inline-block;
    .name {
      font-size: 20px;
      font-weight: 600;
    }
    .detail {
      font-size: 12px;
    }
  }
}
.menu-content{
  border-top: 1px solid var(--theme-text-color);
  margin-top: 10px;
}
.menu-content .menu-list .menu-list-item {
  font-family: Marmelad_Regular,SansSerif;
  font-weight: 700;
  cursor: pointer;
  width: 100%;
  height: 50px;
  position: relative;
  border-radius: 10px;
  padding-left: 15px;
  white-space: nowrap;
  //display: flex;
  .iconfont{
    //top:60%;
    //transform: translateY(-50%);
    font-size: 18px;
    color: var(--theme-item-color);
  }

  .block {
    width: 6px;
    height: 25px;
    background-color: var(--theme-hover-color);
    position: absolute;
    right: -10px;
    top: 13px;
    transition: 0.5s;
    border-top-left-radius: 4px;
    border-bottom-left-radius: 4px;
    opacity: 0;
  }
  .item-name {

    line-height: 50px;
    display: inline-block;
    margin-left: 10px;
    font-size: 15px;
    color: var(--theme-text-color);
    font-weight: 100;
    transition: 0.6s;
    opacity: 0;
  }
  &:hover {
    background-color: var(--theme-hover-menu-color);
    .item-name {
      color: var(--theme-hover-color);
    }
    .iconfont {
      color: var(--theme-hover-color);
    }
    .block {
      opacity: 1;
    }
  }
  .btn {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    display: inline-block;
    opacity: 0;
    margin-left: 20px;
    margin-top: 3px;
    .check-ipt {
      display: none;
    }
    .check-lable {
      display: inline-block;
      width: 40px;
      height: 20px;
      border-radius: 20px;
      background-color: var(--theme-hover-color);
      cursor: pointer;
      position: relative;
      overflow: hidden;
    }
    .check-lable::before {
      display: inline-block;
      content: "";
      width: 15px;
      height: 15px;
      border-radius: 50%;
      background: white;
      position: absolute;
      left: 3px;
      top: 50%;
      transform: translateY(-50%);
      transition: all 0.3s;
    }
    .check-ipt:checked + .check-lable::before {
      left: 20px;
      background: rgb(32, 30, 43);
    }
  }
}

.menu-wrapper:hover {
  width: 220px;
  .menu-title .title-text {
    opacity: 1;
  }
  .content .person-info .person-name {
    opacity: 1;
  }
  .menu-content .menu-list .menu-list-item .item-name,
  .menu-content .menu-list .menu-list-item .btn {
    opacity: 1;
  }
}
</style>
