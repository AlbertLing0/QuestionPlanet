<script>
import avatarUploader from "~/components/avatar-uploader1.vue";

export default {
  name: "settings_square",
  components:{
    avatarUploader
  },
  props: {
    surveys: {
      type: Array,
      required: true
    },

  },
  data() {
    return {
      settings:[
        {setting: "avatarUploader", index: "1"}
      ],
      settingsComponents: {
        "avatarUploader": avatarUploader
      },
      activeIndex: null,  // 初始不设置任何激活面板
    };
  },

  methods: {
    setActive(index) {
      this.activeIndex = index
    },

  }


}

</script>

<template>
  <div class="settings-square">
    <div class="box-head">
          设置
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
              <component :is = settingsComponents[settingItem.setting] />
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
  //margin-top: 10px;


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
  height: 5vh;
  color: #000;
  cursor: pointer;
  min-width: 20%;
  margin: 10px;
  position: relative;
  transition: all 200ms ease-in;
  transform: translateX(5%);
  animation: slideInFromRight 0.2s ease-out forwards;
}

.settings-item.active{
    min-width: 90%;

}
</style>