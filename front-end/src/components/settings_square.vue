<template>
  <div class="settings-square">
    <div class="box-head">
      设置——修改个人资料
    </div>
    <el-main>
      <el-scrollbar>
        <div class="settings-list">
          <div
            v-for="(settingItem, index) in settings"
            :key="index"
            class="settings-item"
            :class="{ active: activeIndex === index }"
            @mouseover="setActive(index)"
            @mouseleave="setActive(null)"
          >
            <component 
              v-if="settingItem.setting === 'display-bar'"
              :is="settingsComponents[settingItem.setting]"
              :displaymessage="settingItem.displaymessage"
              :inputtype="settingItem.inputtype"
              :isdisabled="settingItem.isdisabled"
              :displayinfo="settingItem.displayinfo"
              @input-changed="handleInputChange" 
            />
            <component 
              v-else
              :is="settingsComponents[settingItem.setting]"
              :displaymessage="settingItem.displaymessage"
              :inputtype="settingItem.inputtype"
              :isdisabled="settingItem.isdisabled"
              :displayinfo="settingItem.displayinfo" 
            />
          </div>
          <div class="settings-item" 
            :key= "6"
            :class="{ active: activeIndex === 6 }"
            @mouseover="setActive(6)"
            @mouseleave="setActive(null)"
          >
            <div class= "submit-button" @click = "submit">提交</div>
          </div>
        </div>
      </el-scrollbar>
    </el-main>
  </div>
</template>

<script>
import { onMounted, ref, inject } from 'vue';
import avatarUploader from "~/components/avatar-uploader1.vue";
import displaybar from "~/components/display-bar.vue";
import axios from 'axios';
import { GET_USER_INFO, SAVE_USER_INFO } from "~/utils/request.js";

export default {
  name: "settings_square",
  components: {
    avatarUploader,
    displaybar
  },
  setup() {
    const isChanged = ref(false);
    const settings = ref([
      { setting: "avatarUploader", displaymessage: "", inputtype: "", isdisabled: "", displayinfo: ""},
      { setting: "display-bar", displaymessage: "写一条善意的信息吧~", inputtype: "text", isdisabled: false, displayinfo: "昵    称"},
      { setting: "display-bar", displaymessage: "写一条善意的信息吧~", inputtype: "text", isdisabled: false, displayinfo: "签    名"},
      { setting: "display-bar", displaymessage: "写一条善意的信息吧~", inputtype: "text", isdisabled: true, displayinfo: "用户名"},
      { setting: "display-bar", displaymessage: "写一条善意的信息吧~", inputtype: "text", isdisabled: true, displayinfo: "邮    箱"}
    ]);
    const password =ref('');
    const settingsComponents = {
      "avatarUploader": avatarUploader,
      "display-bar": displaybar
    };
    const activeIndex = ref(null);
    const globalUsername = inject('Username');
    const userinfo = ref({});

    const setActive = (index) => {
      activeIndex.value = index;
    };
    const handleInputChange = (value) => {
      switch (value.displayinfo) {
        case "昵    称":
          settings.value[1].displaymessage = value.inputvalue;
          break;
        case "签    名":
          settings.value[2].displaymessage = value.inputvalue;
          break;
      }
      isChanged.value = true;
    };

    const submit = () => {
      if (isChanged.value) {
        saveUserInfo();
      }
    }

    const getUserInfo = async () => {
      try {
        const response = await axios.get(GET_USER_INFO, {
          params: {
            username: globalUsername.value,
          }
        });
        userinfo.value = response.data;
        settings.value[1].displaymessage = userinfo.value.nickname;
        settings.value[2].displaymessage = userinfo.value.note;
        settings.value[3].displaymessage = userinfo.value.username;
        settings.value[4].displaymessage = userinfo.value.email;
        password.value = userinfo.value.password;
      } catch (error) {
        console.error('Get User Info Failed:', error);
      }
    };

    const saveUserInfo = async () => {
      try {
        const params = new URLSearchParams();
        params.append('username', globalUsername.value);
        params.append('nickname', settings.value[1].displaymessage);
        params.append('note', settings.value[2].displaymessage);
        params.append('password', password.value);
        const response = await axios.post(SAVE_USER_INFO, params);
        if (response.data === "success"){
          console.log("submit success");
          alert("保存成功");
        }
      } catch (error) {
        console.error('Get User Info Failed:', error);
      }
    };

    onMounted(() => {
      getUserInfo();
    });

    return {
      settings,
      settingsComponents,
      activeIndex,
      setActive,
      isChanged,
      handleInputChange,
      submit
    };
  }
};
</script>

<style lang="scss" scoped>

.settings-square{
  border-radius: 20px;
  height: auto;
  width: auto;
  background: var(--pane-color);
  padding: 20px;
  height: 68vh;

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

.settings-button {
}

.submit-button {
  margin-left:31.5%;
  border-radius: 10px;
  border: 2px solid var(--el-text-color-placeholder);
  height: 80%;
  width: 18em;
  font-size: 16;
  font-weight:bold;
  text-align: center;
  color: #8c939d;
}
</style>