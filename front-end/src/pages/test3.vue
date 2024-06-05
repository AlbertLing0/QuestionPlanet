<template>
  <div class="container">
    <div
        class="panel"
        v-for="(panel, index) in panels"
        :key="index"
        :class="{ active: activeIndex === index }"
        :style="{ backgroundImage: `url(${panel.image})`, '--animation-order': index }"
        @mouseover="setActive(index)"
        @mouseleave="setActive(null)"
    >
      <h3>{{ panel.title }}</h3>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activeIndex: null,  // 初始不设置任何激活面板
      panels: [
        {
          title: "Explore The World",
          image: "https://images.unsplash.com/photo-1558979158-65a1eaa08691?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80",
        },
        {
          title: "Wild Forest",
          image: "https://images.unsplash.com/photo-1572276596237-5db2c3e16c5d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80",
        },
        {
          title: "Sunny Beach",
          image: "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1353&q=80",
        },
        {
          title: "City on Winter",
          image: "https://images.unsplash.com/photo-1551009175-8a68da93d5f9?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1351&q=80",
        },
        {
          title: "Mountains - Clouds",
          image: "https://images.unsplash.com/photo-1549880338-65ddcdfd017b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80",
        },
      ],
    };
  },
  methods: {
    setActive(index) {
      this.activeIndex = index;
    },
  },
};
</script>

<style scoped>
* {
  box-sizing: border-box;
}
body {
  font-family: 'Muli', sans-serif;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  overflow: hidden;
  margin: 0;
}

.container {
  display: flex;
  width: 90vw;
}

.panel {
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  height: 80vh;
  border-radius: 50px;
  color: #fff;
  cursor: pointer;
  width: 80px;
  margin: 10px;
  position: relative;
  transition: all 300ms ease-in;
  transform: translateX(100%); /* 初始状态从右侧外面开始 */
  opacity: 0; /* 开始时不可见 */
  animation: slideInFromRight 0.5s ease-out forwards;
  animation-delay: calc(var(--animation-order) * 100ms); /* 使用CSS变量动态调整延迟 */

}

.panel h3 {
  font-size: 24px;
  position: absolute;
  bottom: 20px;
  left: 20px;
  margin: 0;
  opacity: 0;
}

.panel.active {
  //flex: 5;
  width: 400px;
}

.panel.active h3 {
  opacity: 1;
  transition: opacity 0.1s ease-in 0.2s;
}

@media (max-width: 480px) {
  .container {
    width: 100vw;
  }

  .panel:nth-of-type(4),
  .panel:nth-of-type(5) {
    display: none;
  }
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



</style>
