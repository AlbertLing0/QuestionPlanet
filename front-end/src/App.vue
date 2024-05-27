<template>
  <div class="app_sub">
    <div id="BG">
      <planet-b-g v-if="$route.meta.requiresParallax"/>
    </div>
    <router-view/>
  </div>
</template>

<script>
import PlanetBG from "~/components/planetBG.vue";

export default {
  name: 'App',
  components: {
    PlanetBG,
  },
};
</script>

<style>
#BG {
  position: absolute;
  width: 100%;
  height: 100vh;
  z-index: -10;
}
</style>
