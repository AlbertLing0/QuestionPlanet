<template>
  <div
      class="menu-wrapper"
      :class="{ expanded: isExpanded }"
      @mouseenter="expandSideBar"
      @mouseleave="collapseSideBar"
  >
    <div class="content">
      <div class="person-info">
        <img :src="avatarUrl" alt="" />
        <div class="person-name">
          <div class="name">{{ nowUserName }}</div>
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
              <input class="check-ipt" type="checkbox" id="checks" @change="changeDark" :checked="isDarkMode"/>
              <label class="check-lable" for="checks"></label>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {inject, onMounted, ref} from 'vue';
import router from '~/router/index.js';
import axios from 'axios';
import { ElMessage } from 'element-plus';
import {GET_AVATAR} from "~/utils/request.js";

export default {
  name: "side-bar",
  setup() {
    const { isExpanded, expandSideBar, collapseSideBar } = inject('sideBarState');
    let nowUserName = inject('Username');
    const avatarUrl = ref('');
    const getAvatar = async (username) => {
      try {
        const response = await axios.get(GET_AVATAR, {
          params: {
            username: username
          }
        });

        if (response.data !== 'No avatar saved') {
          console.log(response.data);
          return response.data;
        } else {
          console.log(response.data);
          ElMessage.warning('No avatar saved for this user');
          return null;
        }
      } catch (error) {
        ElMessage.error('Failed to fetch avatar');
        console.error('Error fetching avatar:', error);
        return null;
      }
    };
    const fetchAvatar = async () => {
      const url = await getAvatar(localStorage.getItem("username"));
      if (url) {
        avatarUrl.value = url;
      }else{
        avatarUrl.value="https://avatar-1327017285.cos.ap-beijing.myqcloud.com/avatar/head_portrait.jpg";
      }
      console.log(avatarUrl.value)
    };
    onMounted(() => {
      fetchAvatar();
    });
    return {
      nowUserName,
      isExpanded,
      expandSideBar,
      collapseSideBar,
      avatarUrl
    };
  },

  data() {
    return {
      menuData: [
        { id: 1, menuName: "Profile", iconFont: "icon-caidan", action: this.showProfile },
        { id: 2, menuName: "My Questionnaires", iconFont: "icon-order-fill", action: this.showQuestionnaires },
        { id: 3, menuName: "Data Analysis", iconFont: "icon-bingtu", action: this.showDataAnalysis },
        { id: 4, menuName: "Settings", iconFont: "icon-shezhi", action: this.showSettings },
        { id: 5, menuName: "Logout", iconFont: "icon-jinru", action: this.logout }
      ],
      isDarkMode: localStorage.getItem('isDarkMode') === 'true' // 添加这一行
    };
  },

  mounted() {
    if (localStorage.getItem("username")) {
      this.nowUserName = localStorage.getItem("username");
    }
    const isDarkMode = localStorage.getItem('isDarkMode');
    if (isDarkMode !== null) {
      this.isDarkMode = isDarkMode === 'true';
    }

  },

  methods: {
    changeDark() {
      this.isDarkMode = event.target.checked;
      localStorage.setItem('isDarkMode', this.isDarkMode);
      this.$emit("toggle-theme");
    },
    showProfile() {
      console.log("Profile clicked");
      router.push('/about');
    },
    showQuestionnaires() {
      console.log("My Questionnaires clicked");
      router.push("/questionnaires");
    },
    showDataAnalysis() {
      console.log("Data Analysis clicked");
    },
    showSettings() {
      console.log("Settings clicked");
      router.push("/settings");
    },
    logout() {
      console.log("Logout clicked");
      this.nowUserName = null;
      localStorage.removeItem('username');
      router.push("/");
    }
  }
};
</script>

<style lang='scss' scoped>
@import url(../assets/iconfont/iconfont.css);

.menu-wrapper {
  margin-left: 25px;
  width: 70px;
  border-radius: 20px;
  background-color: var(--pane-color);
  padding: 10px;
  box-sizing: border-box;
  transition: width 0.6s;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}
.menu-wrapper.expanded {
  width: 220px;
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
    transition: opacity 0.6s;
    opacity: 0;
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
.menu-wrapper.expanded .content .person-info .person-name {
  opacity: 1;
}

.menu-content {
  border-top: 1px solid var(--theme-text-color);
  margin-top: 10px;
}

.menu-content .menu-list .menu-list-item {
  font-family: Marmelad_Regular, SansSerif;
  font-weight: 700;
  cursor: pointer;
  width: 100%;
  height: 50px;
  position: relative;
  border-radius: 10px;
  padding-left: 15px;
  white-space: nowrap;
  .iconfont {
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
    transition: opacity 0.6s;
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
.menu-wrapper.expanded .menu-content .menu-list .menu-list-item .item-name,
.menu-wrapper.expanded .menu-content .menu-list .menu-list-item .btn {
  opacity: 1;
}
</style>
