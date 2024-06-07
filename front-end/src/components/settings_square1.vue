<script>
import avatarUploader from "~/components/avatar-uploader1.vue";
import displaybar from "~/components/display-bar.vue";
import axios from 'axios';
import {inject} from 'vue';
import {onMounted} from 'vue';
import {GET_USER_INFO, SAVE_USER_INFO} from "~/utils/request.js";

export default {
  name: "settings_square",
  components:{
    avatarUploader,
    displaybar
  },

  data() {
    return {
      settings:[
        {setting: "avatarUploader", displaymessage: "", inputtype: "", isdiabled: "", displayinfo: "", index: "1"},
        {setting: "display-bar", displaymessage: "test", inputtype: "text", isdisabled: false,displayinfo: "昵    称", index: "2"},
        {setting: "display-bar", displaymessage: "test", inputtype: "text", isdisabled: false,displayinfo: "签    名", index: "3"},
        {setting: "display-bar", displaymessage: "test", inputtype: "text", isdisabled: true,displayinfo: "用户名", index: "4"},
        {setting: "display-bar", displaymessage: "test", inputtype: "text", isdisabled: true,displayinfo: "邮    箱", index: "5"}
      ],
      settingsComponents: {
        "avatarUploader": avatarUploader,
        "display-bar": displaybar
      },
      activeIndex: null,  // 初始不设置任何激活面板
      globalUsername: inject('Username'),
      userinfo: {},
    };
  },
  onMounted() {
    console.log(1);
    this.getUserInfo();
    this.settings[1].displaymessage =  this.userinfo.nickname;
    this.settings[2].displaymessage =  this.userinfo.note;
    this.settings[3].displaymessage =  this.userinfo.username;
    this.settings[4].displaymessage =  this.userinfo.email;
  },

  methods: {
    setActive(index) {
      this.activeIndex = index;
    },
    async getUserInfo() {
      try {
        axios.get(GET_USER_INFO, {
          params: {
            username: this.globalUsername,
          }
        }).then(response => {
          this.userinfo = response;
        });
      } catch (error) {
        console.error('Get User Info Failed:', error);
      }

    }

  },

  


}

</script>

<template>
  <div class="settings-square">
    <div class="box-head">
          设置——修改个人资料
    </div>
    <el-main>
        <el-scrollbar>
          <div class="settings-list">
            <div
                class="settings-item"
                v-for="(settingItem, index) in settings"
                :key="index"
                :class="{ active: activeIndex === index }"
                @mouseover="setActive(index)"
                @mouseleave="setActive(null)"
            >
              <component :is = settingsComponents[settingItem.setting] 
                :displaymessage= settingItem.displaymessage
                :inputtype= settingItem.inputtype
                :isdisabled= settingItem.isdisabled
                :displayinfo= settingItem.displayinfo
              />
            </div>
          </div>
        </el-scrollbar>

    </el-main>




  </div>
</template>

<style lang="scss" scoped>

.settings-square{
  border-radius: 20px;
  height: auto;
  width: auto;
  background: var(--pane-color);
  padding: 20px;
  height: 50vh;

}

.box-head{
  font-size: 20px;
  color: var(--theme-info-text-color);
}


el-scrollbar{
  direction: ltr;
}

.settings-list {
  //height: 50vh;
  //width: 80vw;
  display: flex;
  flex-direction: row;
  align-items: center;
  flex-direction: column;
  align-items: flex-start;
  //margin-top: 10px;


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

.settings-item {
  display: flex;
  justify-content: space-between; /* 两端对齐 */
  align-items: center;
  //padding: 10px;
  //border: 1px solid var(--border-color);
  //margin: 5px;
  background-color: var(--theme-text-box-color);
  border-radius: 20px;
  align-items: center;
  position: relative;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  min-height: 5vh;
  height: auto;
  color: #000;
  cursor: pointer;
  min-width: 80%;
  margin: 10px;
  position: relative;
  transition: all 150ms ease-in;
  animation: slideInFromRight 0.1s ease-out forwards;
}

.settings-item.active{
    min-width: 83%;
    min-height: 5.5vh;
}
</style>