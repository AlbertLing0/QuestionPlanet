<script>
import {defineComponent, ref} from 'vue';
import PlanetBG from '~/components/planetBG.vue';
import NavBar from "~/components/navigation-bar.vue";

export default defineComponent({
  components: {PlanetBG, NavBar}
});
</script>

<template>
  <div id="app">
    <div id="BG">
      <planet-b-g/>
    </div>
    <div id="naviBar">
      <nav-bar/>
    </div>
  </div>
</template>

<style scoped>
#app {
  position: relative;
  width: 100%;
  height: 100vh;
  overflow: hidden;
}

#BG {
  position: absolute;
  width: 100%;
  height: 100vh;
  z-index: 1;
}

#naviBar {
  position: absolute;
  width: 100%;
  z-index: 10;
}
</style>
