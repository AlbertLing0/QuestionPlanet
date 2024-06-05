<template>
  <div id="app">
    <div id="BG">
      <planet-b-g v-if="$route.meta.requiresParallax"/>
      <gradient-background v-if="$route.meta.requiresGradient"/>
    </div>

    <router-view/>
  </div>
</template>

<script>
import PlanetBG from "~/components/planetBG2.vue";
import GradientBackground from "~/components/gradient-background.vue";
import blocker from "~/components/blocker.vue";

export default {
  name: 'App',
  components: {
    GradientBackground,
    PlanetBG,
    blocker
  },
};
</script>

<style>
#BG{
  position: absolute;
  width: 100%;
  height: 100vh;
  z-index: -10;
}

</style>