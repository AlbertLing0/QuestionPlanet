<script>
import {defineComponent, ref} from 'vue';

import NavBar from "~/components/navigation-bar.vue";

export default {
  components: { NavBar},
  name: 'Index',
};

</script>

<template>
  <div id="app">
    <div id="naviBar">
      <NavBar/>
    </div>
    <div class="vuetype-container">
      <vuetyped :strings="['Ask, Analyze, Act',
      'Revolutionize the way you collect and analyze feedback',
      'Your ultimate tool for creating and managing surveys',
      'Unlock the power of data-driven decisions']"
                :showCursor="true" :typeSpeed="80" :back-speed="20" :loop="true"
                class="vuetype">
        <span class="typing"></span>
      </vuetyped>
    </div>
  </div>
</template>

<style scoped>
@font-face {
  font-family: DancingScript_Regular;
  font-weight: normal;
  src: url(~/assets/font/DancingScript-Regular.ttf) format("truetype");
  text-rendering: optimizeLegibility;
}
#app {
  position: relative;
  width: 100%;
  height: 100vh;
  overflow: hidden;
}



#naviBar {
  position: absolute;
  width: 100%;
  z-index: 10;
}
.vuetype-container {
  height: 100vh; /* 视口高度 */
  display: flex;
  align-items: center;
  justify-content: center;
  flex: 1;
}
.vuetype{
  font-size: 60px; /* 根据需要调整字体大小 */
  font-family: DancingScript_Regular;
  color: ghostwhite; /* 根据需要调整字体颜色 */
}
</style>
